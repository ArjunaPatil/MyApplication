package com.example.vin.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.vin.myapplication.R.id.imageView;

public class MainActivity extends Activity {

    Button GetImageFromGalleryButton, UploadImageOnServerButton;
    ImageView ShowSelectedImage;
    EditText GetImageName;
    Bitmap FixBitmap;
    String ImageTag = "image_tag";
    String ImageName = "image_data";
    String ServerUploadPath = "http://159.65.159.87";//"http://androidblog.esy.es/upload-image-server.php";
    ProgressDialog progressDialog;
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String ConvertImage;
    String GetImageNameFromEditText;
    HttpURLConnection httpURLConnection;
    OutputStream outputStream;
    BufferedWriter bufferedWriter;
    int RC;
    URL url;

    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    boolean check = true;

    private static final String[] CAMERA_PERMS = {
            Manifest.permission.CAMERA
    };

    private static final int INITIAL_REQUEST = 1337;
    private static final int CAMERA_REQUEST = INITIAL_REQUEST + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!canAccessCamera()) {
                requestPermissions(CAMERA_PERMS, CAMERA_REQUEST);
            }
        }

        if (hasPermissions()) {
        } else {
            requestPerms();

        }

        setContentView(R.layout.activity_main1);

        GetImageFromGalleryButton = (Button) findViewById(R.id.button);
        UploadImageOnServerButton = (Button) findViewById(R.id.button2);
        ShowSelectedImage = (ImageView) findViewById(imageView);
        GetImageName = (EditText) findViewById(R.id.editText);
        byteArrayOutputStream = new ByteArrayOutputStream();

       /*ALL Images Open On click*/
        GetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selectImage();
                alertThreeButtons();

                // Create an object for subclass of AsyncTask
                GetXMLTask task = new GetXMLTask();
                // Execute the task
                task.execute(new String[]{"http://139.59.63.181/private/files/ic_reset_icon.png"});

                /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(Intent.createChooser(intent, "Click Pick"), 0);*/


                /*Intent intent = new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);*/

            }
        });


        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetImageNameFromEditText = GetImageName.getText().toString();
                UploadImageToServer();
            }
        });

    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        /*switch (RC) {
            case 0:
                if (RQC == RESULT_OK) {
                    Uri uri = I.getData();
                    try {
                        FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ShowSelectedImage.setImageBitmap(FixBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case 1:
                if (RQC == RESULT_OK) {
                    Uri uri = I.getData();
                    try {
                        FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ShowSelectedImage.setImageBitmap(FixBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }*/

        if (RC == 0 && RQC == RESULT_OK) {
            try {
                //"http://139.59.63.181/private/files/ic_reset_icon.png"


                /*File file = new File(Environment.getExternalStorageDirectory().getPath(), "photo.jpg");
                Uri uri = Uri.fromFile(file);
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    bitmap = crupAndScale(bitmap, 300); // if you mind scaling
                    ShowSelectedImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }*/


                FixBitmap = (Bitmap) I.getExtras().get("data");
                ShowSelectedImage.setImageBitmap(FixBitmap);

                Uri aa = getImageUri(FixBitmap);
                InputStream is = getContentResolver().openInputStream(aa);
                uploadImage(getBytes(is));


            } catch (Exception e) {
                e.printStackTrace();
            }
            /*final Uri imageUri = I.getData();
            try {
                /*final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ShowSelectedImage.setImageBitmap(selectedImage);*/

               /* ShowSelectedImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Bitmap thumbnail = (Bitmap) I.getExtras().get("data");
                ShowSelectedImage.setImageBitmap(thumbnail);*/

            /*} catch (IOException e) {
                e.printStackTrace();
            }*/
        }

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {


            Uri uri = I.getData();
            try {


                FixBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ShowSelectedImage.setImageBitmap(FixBitmap);

                InputStream is = getContentResolver().openInputStream(I.getData());
                uploadImage(getBytes(is));

                /*File f = new File("" + uri);
                saveImage1(FixBitmap, f.getName() + ".jpeg");*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void UploadImageToServer() {

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                progressDialog = ProgressDialog.show(MainActivity.this, "Image is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, string1, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String, String> HashMapParams = new HashMap<String, String>();

                HashMapParams.put(ImageTag, GetImageNameFromEditText);

                HashMapParams.put(ImageName, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
                url = new URL(requestURL);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }

    }


    //.setIcon(R.drawable.ninja)
    public void alertThreeButtons() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("CHOOSE OPTIONS")
                .setMessage("TAKE IMAGE")
                .setPositiveButton("CAMERA",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                /*Uri uri = Uri.parse("file:///sdcard/photo.jpg");
                                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);*/

                                startActivityForResult(Intent.createChooser(intent, "Click Image"), 0);
                                dialog.cancel();
                            }
                        })
                .setNeutralButton("GALLERY",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {

                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

                                dialog.cancel();
                            }
                        })
                .setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            @TargetApi(11)
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
    }

    //refer python code
    /*https://stackoverflow.com/questions/32240690/uploading-image-from-android-to-python-server
    https://www.simplifiedcoding.net/upload-image-to-server/
    */
    /*-----------------------------*/
    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    RestService restService;

    private void uploadImage(byte[] imageBytes) {
        try {
            restService = new RestService();
           // RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
            //MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServerUploadPath)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService retrofitInterface = retrofit.create(ApiService.class);

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);

            MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
            Call<Response> call = retrofitInterface.uploadImage(body);
            //mProgressBar.setVisibility(View.VISIBLE);
            call.enqueue(new retrofit2.Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                    //mProgressBar.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        Response responseBody = response.body();
                        //mBtImageShow.setVisibility(View.VISIBLE);
                        //mImageUrl = URL + responseBody.getPath();
                        //Snackbar.make(findViewById(R.id.content), responseBody.getMessage(),Snackbar.LENGTH_SHORT).show();
                    } else {

                        ResponseBody errorBody = response.errorBody();
                        Gson gson = new Gson();
                        try {
                            Response errorResponse = gson.fromJson(errorBody.string(), Response.class);
                            //Snackbar.make(findViewById(R.id.content), errorResponse.getMessage(),Snackbar.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    //mProgressBar.setVisibility(View.GONE);
                    //Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*---Save To External Device---*/
    private void saveImage1(Bitmap imageToSave, String fileName) {
        // get the path to sdcard
        File sdcard = Environment.getExternalStorageDirectory();
        // to this path add a new directory path
        File dir = new File(sdcard.getAbsolutePath() + "/FOLDER_NAME/");
        // create this directory if not already created
        dir.mkdir();
        // create the file in which we will write the contents
        File file = new File(dir, fileName);//fileName
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*Bitmap bitmap;
        OutputStream output;
        // Retrieve the image from the res folder
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.wallpaper);
        // Find the SD Card path
        File filepath = Environment.getExternalStorageDirectory();
        // Create a new folder in SD Card
        File dir = new File(filepath.getAbsolutePath()+ "/Save Image Tutorial/");
        dir.mkdirs();
        // Create a name for the saved image
        File file = new File(dir, "myimage.png");
        // Show a toast message on successful save
        Toast.makeText(MainActivity.this, "Image Saved to SD Card",Toast.LENGTH_SHORT).show();
        try {
            output = new FileOutputStream(file);
            // Compress into png format image from 0% - 100%
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/


    }

    /*---Permission---*/
    private static final int PERMS_REQUEST_CODE = 123;

    private boolean hasPermission(String perm) {
        if (Build.VERSION.SDK_INT >= 23)//Build.VERSION_CODES.M
            return (PackageManager.PERMISSION_GRANTED == getApplicationContext().checkSelfPermission(perm));
        else
            return (PackageManager.PERMISSION_GRANTED == getApplicationContext().checkSelfPermission(perm));
    }

    private boolean canAccessCamera() {
        return (hasPermission(Manifest.permission.CAMERA));
    }

    private boolean hasPermissions() {
        int res = 0;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perms : permissions) {
            res = getApplicationContext().checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    private void requestPerms() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean allowed = true;
        switch (requestCode) {
            case PERMS_REQUEST_CODE:
                for (int res : grantResults) {
                    // if user granted all permissions.
                    allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                // if user not granted permissions.
                allowed = false;
                break;
        }

        /*if (allowed) {
            //user granted all permissions we can perform our task.
            makeFolder();
        } else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getContext(), "Storage Permissions denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }*/
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                inImage, "Title", null);
        return Uri.parse(path);
    }

    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            ShowSelectedImage.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
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
                ex.printStackTrace();
            }
            return stream;
        }
    }

    /*----Rough Work---*/

    // Select image from camera and gallery
    private void selectImage() {
        try {


//            PackageManager pm = getPackageManager();
//            int hasPerm = pm.checkPermission(Manifest.permission.CAMERA, getPackageName());
//
//
//            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
            final CharSequence[] options = {"Take Photo", "Choose From Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
            builder.setTitle("Select Option");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        dialog.dismiss();
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 0);
                    } else if (options[item].equals("Choose From Gallery")) {
                        dialog.dismiss();
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, 1);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
           /* } else
                Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();*/
        } catch (Exception e) {
            Toast.makeText(this, "Camera Permission error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    /*--------------*/


    /*8888888888888888888888888888888888888*/
    int serverResponseCode = 0;
    ProgressDialog dialog = null;

    //Integer i = uploadFile(ConvertImage);
    public int uploadFile(String sourceFileUri) {
        String upLoadServerUri = "http://159.65.159.87/home/frappe/temp/";//"http://10.0.2.2/upload_test/upload_media_test.php";
        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);
        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File Does not exist");
            return 0;
        }
        try { // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(upLoadServerUri);
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", fileName);
            dos = new DataOutputStream(conn.getOutputStream());

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();

            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            if (serverResponseCode == 200) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        //tv.setText("File Upload Completed.");
                        Toast.makeText(getApplicationContext(), "File Upload Complete.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            dialog.dismiss();
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), "MalformedURLException", Toast.LENGTH_SHORT).show();
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {
            dialog.dismiss();
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Exception : " + e.getMessage(), Toast.LENGTH_SHORT).show();
            //Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
        }
        dialog.dismiss();
        return serverResponseCode;
    }
    /*8888888888888888888888888888888888888*/

    /*https://medium.com/@antoinegrandiere/image-upload-and-moderation-with-python-and-flask-e7585f43828a
    http://flask.pocoo.org/docs/0.12/patterns/fileuploads/
    https://medium.com/@antoinegrandiere/image-upload-and-moderation-with-python-and-flask-e7585f43828a
    */


}