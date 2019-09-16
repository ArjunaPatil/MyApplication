package com.example.vin.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ImageDownloader {
    private static final String LOG_TAG = "ImageDownloader";

    public enum Mode {NO_ASYNC_TASK, NO_DOWNLOADED_DRAWABLE, CORRECT}

    private Mode mode = Mode.CORRECT;
    Context context;
    String frag = "";

    public ImageDownloader(Context context, String frg) {
        this.context = context;
        frag = frg;
    }

    /**
     * Download the specified image from the Internet and binds it to the provided ImageView. The
     * binding is immediate if the image is found in the cache and will be done asynchronously
     * otherwise. A null bitmap will be associated to the ImageView if an error occurs.
     *
     * @param url       The URL of the image to download.
     * @param imageView The ImageView to bind the downloaded image to.
     */
    public void download(String url, ImageView imageView) {
        resetPurgeTimer();
        Bitmap bitmap = getBitmapFromCache(url);

        if (bitmap == null) {
            forceDownload(url, imageView);
        } else {
            cancelPotentialDownload(url, imageView);
            imageView.setImageBitmap(bitmap);
        }
        /*if (frag.equals("111")) {
            scaleImage(imageView);
        }*/
    }

    /*
     * Same as download but the image is always downloaded and the cache is not used.
     * Kept private at the moment as its interest is not clear.
       private void forceDownload(String url, ImageView view) {
          forceDownload(url, view, null);
       }
     */

    /**
     * Same as download but the image is always downloaded and the cache is not used.
     * Kept private at the moment as its interest is not clear.
     */
    private void forceDownload(String url, ImageView imageView) {
        try {
            // State sanity: url is guaranteed to never be null in DownloadedDrawable and cache keys.
            if (url == null) {
                imageView.setImageDrawable(null);
                return;
            }

            if (cancelPotentialDownload(url, imageView)) {
                switch (mode) {
                    case NO_ASYNC_TASK:
                        Bitmap bitmap = downloadImage(url);//downloadBitmap(url);
                        addBitmapToCache(url, bitmap);
                        imageView.setImageBitmap(bitmap);
                        break;

                    case NO_DOWNLOADED_DRAWABLE:
                        imageView.setMinimumHeight(156);
                        BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
                        task.execute(url);
                        break;

                    case CORRECT:
                        task = new BitmapDownloaderTask(imageView);
                        DownloadedDrawable downloadedDrawable = new DownloadedDrawable(task);
                        imageView.setImageDrawable(downloadedDrawable);
                        imageView.setMinimumHeight(156);
                        task.execute(url);
                        break;
                }
            }
        } catch (Exception ex) {
            //imageView.setImageResource(R.drawable.blank);
            ex.printStackTrace();
        }
    }

    /**
     * Returns true if the current download has been canceled or if there was no download in
     * progress on this image view.
     * Returns false if the download in progress deals with the same url. The download is not
     * stopped in that case.
     */
    private static boolean cancelPotentialDownload(String url, ImageView imageView) {
        BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);

        if (bitmapDownloaderTask != null) {
            String bitmapUrl = bitmapDownloaderTask.url;
            if ((bitmapUrl == null) || (!bitmapUrl.equals(url))) {
                bitmapDownloaderTask.cancel(true);
            } else {
                // The same URL is already being downloaded.
                return false;
            }
        }
        return true;
    }

    /**
     * @param imageView Any imageView
     * @return Retrieve the currently active download task (if any) associated with this imageView.
     * null if there is no such task.
     */
    private static BitmapDownloaderTask getBitmapDownloaderTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable) drawable;
                return downloadedDrawable.getBitmapDownloaderTask();
            }
        }
        return null;
    }

    /*Bitmap downloadBitmap(String url) {
        final int IO_BUFFER_SIZE = 4 * 1024;

        // AndroidHttpClient is not allowed to be used from the main thread
        final HttpClient client = (mode == Mode.NO_ASYNC_TASK) ? new DefaultHttpClient() :
                AndroidHttpClient.newInstance("Android");
        final HttpGet getRequest = new HttpGet(url);

        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode +
                        " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    // return BitmapFactory.decodeStream(inputStream);
                    // Bug on slow connections, fixed in future release.
                    return BitmapFactory.decodeStream(new FlushedInputStream(inputStream));
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (IOException e) {
            getRequest.abort();
            Log.w(LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
        } catch (IllegalStateException e) {
            getRequest.abort();
            Log.w(LOG_TAG, "Incorrect URL: " + url);
        } catch (Exception e) {
            getRequest.abort();
            Log.w(LOG_TAG, "Error while retrieving bitmap from " + url, e);
        } finally {
            if ((client instanceof AndroidHttpClient)) {
                ((AndroidHttpClient) client).close();
            }
        }
        return null;
    }*/

    /*private*/
    public Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.
                    decodeStream(stream, null, bmOptions);
            stream.close();

            if (frag.equals("111")) {
                SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String code_emp = app_preferences.getString("code_emp", "000");
                String emp_code = app_preferences.getString("employee_code", "111");
                emp_code = emp_code.replace('/', '_');

                //if (code_emp.equals(emp_code)) {
                File f = new File("" + url);
                saveImage(bitmap, f.getName());
                //}
            }

        } catch (IOException e1) {
            String ss = e1.getMessage().toString();
            e1.printStackTrace();
        }
        return bitmap;
    }


    // Makes HttpURLConnection and returns InputStream
    /*private InputStream getHttpConnection(String urlString)
            throws IOException {*/
    private InputStream getHttpConnection(String urlString) {
        try {
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            String sid = app_preferences.getString("sid", "default");
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            //connection.setRequestProperty("Cookie", sid);
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                //ex.printStackTrace();
            }
            return stream;
        } catch (final Exception ex) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            return null;
        }
    }


    /*
     * An InputStream that skips the exact number of bytes provided, unless it reaches EOF.
     */
    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public long skip(long n) throws IOException {
            long totalBytesSkipped = 0L;
            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip(n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read();
                    if (b < 0) {
                        break;  // we reached EOF
                    } else {
                        bytesSkipped = 1; // we read one byte
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }
            return totalBytesSkipped;
        }
    }

    /**
     * The actual AsyncTask that will asynchronously download the image.
     */
    class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private String url;
        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        /**
         * Actual download method.
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
            //return downloadBitmap(url);
            try {
                return downloadImage(url);
            } catch (Exception ex) {
                //Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.blank);//R.drawable.blank
                Drawable vectorDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.no_connection_512, null);
                Bitmap myLogo = ((BitmapDrawable) vectorDrawable).getBitmap();
                return myLogo;
            }
        }

        /**
         * Once the image is downloaded, associates it to the imageView
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            addBitmapToCache(url, bitmap);

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask(imageView);
                // Change bitmap only if this process is still associated with it
                // Or if we don't use any bitmap to task association (NO_DOWNLOADED_DRAWABLE mode)
                if ((this == bitmapDownloaderTask) || (mode != Mode.CORRECT)) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }


    /**
     * A fake Drawable that will be attached to the imageView while the download is in progress.
     * <p>
     * <p>Contains a reference to the actual download task, so that a download task can be stopped
     * if a new binding is required, and makes sure that only the last started download process can
     * bind its result, independently of the download finish order.</p>
     */
    static class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<BitmapDownloaderTask> bitmapDownloaderTaskReference;

        public DownloadedDrawable(BitmapDownloaderTask bitmapDownloaderTask) {
            super(Color.BLACK);
            bitmapDownloaderTaskReference =
                    new WeakReference<BitmapDownloaderTask>(bitmapDownloaderTask);
        }

        public BitmapDownloaderTask getBitmapDownloaderTask() {
            return bitmapDownloaderTaskReference.get();
        }
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        clearCache();
    }


    /*
     * Cache-related fields and methods.
     *
     * We use a hard and a soft cache. A soft reference cache is too aggressively cleared by the
     * Garbage Collector.
     */

    private static final int HARD_CACHE_CAPACITY = 10;
    private static final int DELAY_BEFORE_PURGE = 10 * 1000; // in milliseconds

    // Hard cache, with a fixed maximum capacity and a life duration
    private final HashMap<String, Bitmap> sHardBitmapCache =
            new LinkedHashMap<String, Bitmap>(HARD_CACHE_CAPACITY / 2, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(LinkedHashMap.Entry<String, Bitmap> eldest) {
                    if (size() > HARD_CACHE_CAPACITY) {
                        // Entries push-out of hard reference cache are transferred to soft reference cache
                        sSoftBitmapCache.put(eldest.getKey(), new SoftReference<Bitmap>(eldest.getValue()));
                        return true;
                    } else
                        return false;
                }
            };

    // Soft cache for bitmaps kicked out of hard cache
    private final static ConcurrentHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache =
            new ConcurrentHashMap<String, SoftReference<Bitmap>>(HARD_CACHE_CAPACITY / 2);

    private final Handler purgeHandler = new Handler();

    private final Runnable purger = new Runnable() {
        public void run() {
            clearCache();
        }
    };

    /**
     * Adds this bitmap to the cache.
     *
     * @param bitmap The newly downloaded bitmap.
     */
    private void addBitmapToCache(String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (sHardBitmapCache) {
                sHardBitmapCache.put(url, bitmap);
            }
        }
    }

    /**
     * @param url The URL of the image that will be retrieved from the cache.
     * @return The cached bitmap or null if it was not found.
     */
    private Bitmap getBitmapFromCache(String url) {
        // First try the hard reference cache
        synchronized (sHardBitmapCache) {
            final Bitmap bitmap = sHardBitmapCache.get(url);
            if (bitmap != null) {
                // Bitmap found in hard cache
                // Move element to first position, so that it is removed last
                sHardBitmapCache.remove(url);
                sHardBitmapCache.put(url, bitmap);
                return bitmap;
            }
        }

        // Then try the soft reference cache
        SoftReference<Bitmap> bitmapReference = sSoftBitmapCache.get(url);
        if (bitmapReference != null) {
            final Bitmap bitmap = bitmapReference.get();
            if (bitmap != null) {
                // Bitmap found in soft cache
                return bitmap;
            } else {
                // Soft reference has been Garbage Collected
                sSoftBitmapCache.remove(url);
            }
        }

        return null;
    }

    /**
     * Clears the image cache used internally to improve performance. Note that for memory
     * efficiency reasons, the cache will automatically be cleared after a certain inactivity delay.
     */
    public void clearCache() {
        try {
            sHardBitmapCache.clear();
            sSoftBitmapCache.clear();
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        }
    }

    /**
     * Allow a new delay before the automatic cache clear is done.
     */
    private void resetPurgeTimer() {
        try {
            purgeHandler.removeCallbacks(purger);
            purgeHandler.postDelayed(purger, DELAY_BEFORE_PURGE);
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        }
    }

    /*---Save To External Device---*/
    private void saveImage(Bitmap imageToSave, String fileName) {
        final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String employee_code = app_preferences.getString("employee_code", "default");
        employee_code = employee_code.replace('/', '_');
        // get the path to sdcard
        File sdcard = Environment.getExternalStorageDirectory();
        // to this path add a new directory path
        File dir = new File(sdcard.getAbsolutePath() + "/Android/" + employee_code + "/");
        // create this directory if not already created
        dir.mkdir();
        // create the file in which we will write the contents
        File file = new File(dir, fileName);//fileName
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            //e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////
    //////////////////////////////////////////////
    /////////////////////////////////////////////


    private void scaleImage(ImageView view) {
        Drawable drawing = view.getDrawable();
        if (drawing == null) {
            return;
        }
        Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int xBounding = ((View) view.getParent()).getWidth();//EXPECTED WIDTH
        int yBounding = ((View) view.getParent()).getHeight();//EXPECTED HEIGHT

        float xScale = ((float) xBounding) / width;
        float yScale = ((float) yBounding) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(xScale, yScale);

        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth();
        height = scaledBitmap.getHeight();
        BitmapDrawable result = new BitmapDrawable(context.getResources(), scaledBitmap);

        view.setImageDrawable(result);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }


}