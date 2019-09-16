package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

//https://www.androidhive.info/2013/09/android-fullscreen-image-slider-with-swipe-and-pinch-zoom-gestures/

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_upload_images_list1_selected_image#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_upload_images_list1_selected_image extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //// add for load doctors
    private Realm mRealm;
    int limitstart = 0;
    int pagesize = 10;//10
    boolean datafull = false;
    int sleep_wait = 0;

    public boolean bool_full_update = true;
    public POJO_Upload_Files last_POJO;


    private OnFragmentInteractionListener mListener;
    ListView listView;
    public EditText edit_search;

    private ProgressDialog pDialog;
    RestService restService;

    Bundle bundle;

    ImageView imageView;
    ImageButton share_image, download_image;

    public RealmResults<POJO_Upload_Files> POJO_Upload_Files;
    public RealmResults<POJO_Upload_Files_For_Selected_User> POJO_Upload_Files_For_Selected_User;
    public String designation = "";
    private long mLastClickTime = 0;

    public fragment_upload_images_list1_selected_image() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_Doctor_List.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_upload_images_list1_selected_image newInstance(String param1, String param2) {
        fragment_upload_images_list1_selected_image fragment = new fragment_upload_images_list1_selected_image();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        try {

            // final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            // designation = (app_preferences.getString("designation", "default"));

            // mRealm = Realm.getDefaultInstance();

            pDialog = new ProgressDialog(getContext());
            init_control();
            call_offline_gallery();

            ((DashBord_main) getActivity()).setActionBarTitle("GALLERY");
            final Bundle bundle = this.getArguments();
            if (bundle != null) {
                share_image.setVisibility(View.VISIBLE);
                download_image.setVisibility(View.VISIBLE);
                call_image_load_in_imageview();
            } else {
                share_image.setVisibility(View.GONE);
                download_image.setVisibility(View.GONE);
            }
               /*--------Share Image----------*/
            share_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (check_network() == true) {
                        if (bundle.getString("offline").toString().equals("1")) {
                            share_image_url(1);
                            /*Intent imageIntent = new Intent(Intent.ACTION_SEND);
                            Uri imageUri = Uri.parse("http://139.59.63.181" + bundle.getString("url").toString());
                            imageIntent.setType("image*//*");
                            imageIntent.putExtra(Intent.EXTRA_STREAM, String.valueOf(imageUri));
                            startActivity(imageIntent);*/
                        } else {

                            if (fileList.size() > 0) {
                                boolean temp = false;
                                String File_name = bundle.getString("file_name").toString();

                                for (File file : fileList) {
                                    if (file.getName().contains(File_name)) {

                                        String Path = file.getPath();
                                        Uri screenshotUri = Uri.parse(Path);

                                        final Intent intent = new Intent(Intent.ACTION_SEND);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);//Uri.fromFile()
                                        intent.setType("image*//**//*");
                                        startActivity(intent);
                                        temp = true;
                                        break;
                                    } else {
                                        temp = false;
                                    }
                                }

                                if (temp == false) {
                                    share_image_url(1);
                                }
                            } else {
                                share_image_url(1);
                            }

                            /*if (fileList.size() > 0) {
                                if (fileList.contains(bundle.getString("file_name").toString())) {
                                    Integer i = fileList.indexOf(bundle.getString("file_name").toString());
                                    String Path = fileList.get(i).getPath();

                                    //Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                    Uri screenshotUri = Uri.parse(Path);

                                    final Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);//Uri.fromFile()
                                    intent.setType("image*//**//**//**//*");
                                    startActivity(intent);

                                } else {

                                }
                            } else {
                                share_image_url(1);
                            }*/
                        }

                    } else {
                        Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                    }
                }
            });

                /*--------Download Image----------*/
            download_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (check_network() == true) {
                        ImageDownloader imageDownloader = new ImageDownloader(getContext(), "111");
                        imageDownloader.download("http://139.59.63.181" + bundle.getString("url"), imageView);
                        // downloadImage("http://139.59.63.181" + bundle.getString("url"));
                        check_file_after_download();
                    } else {
                        Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            super.onStart();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void share_image_url(Integer i) {
        try {
            if (check_network() == true) {

                bundle = this.getArguments();
                String path = bundle.getString("url").toString();
                ImageDownloader imageDownloader = new ImageDownloader(getContext(), "111");
                imageDownloader.download("http://139.59.63.181" + path, imageView);
                check_file_after_download();
                call_for_share();
            } else {
                Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void check_file_after_download() {
        try {
            //fileList = null;
            for (Integer j = 0; j < 2; j++) {
                call_offline_gallery();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void call_for_share() {
        try {
            if (fileList.size() > 0) {

                boolean temp = false;
                String File_name = bundle.getString("file_name").toString();

                for (File file : fileList) {
                    if (file.getName().contains(File_name)) {

                        String Path = file.getPath();
                        Uri screenshotUri = Uri.parse(Path);

                        final Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);//Uri.fromFile()
                        intent.setType("image*//**//*");
                        startActivity(intent);
                        temp = true;
                        break;
                    } else {
                        temp = false;
                    }
                }

                if (temp == false) {
                    Toast.makeText(getContext(), "Try Again Share", Toast.LENGTH_SHORT).show();
                }

                /*if (fileList.contains(bundle.getString("file_name").toString())) {
                    Integer i = fileList.indexOf(bundle.getString("file_name").toString());
                    String Path = fileList.get(i).getPath();

                    //Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    Uri screenshotUri = Uri.parse(Path);

                    final Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);//Uri.fromFile()
                    intent.setType("image*//**//**//**//*");
                    startActivity(intent);

                } else {
                    Toast.makeText(getContext(), "Try Again Share", Toast.LENGTH_SHORT).show();
                }*/
            } else {
                Toast.makeText(getContext(), "Try Again Share", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void call_image_load_in_imageview() {
        try {
            if (check_network() == true) {

                Bundle bundle = this.getArguments();

                if (bundle.getString("offline").equals("0")) {
                    share_image.setVisibility(View.VISIBLE);
                    download_image.setVisibility(View.GONE);
                } else {
                    share_image.setVisibility(View.VISIBLE);
                    download_image.setVisibility(View.VISIBLE);
                }

                Glide.with(getContext())
                        .load("http://139.59.63.181" + bundle.getString("url")) // Uri of the picture
                        .into(imageView);

                //ImageDownloader imageDownloader = new ImageDownloader(getContext(), "000");
                //imageDownloader.download("http://139.59.63.181" + bundle.getString("url"), imageView);

            } else {
                Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();

            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean check_network() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);//getSystemService
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }

    public void edit_fill() {
        try {
            Bundle bundle = this.getArguments();

            //txt_patch_id.setText(bundle.getString("name"));
            //txt_patch_name.setText(bundle.getString("patch_name"));

            //txt_hq_name.setText(bundle.getString("headquarter"));
            //btn_save_patch.setText("UPDATE");

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void init_control() {
        try {

            imageView = (ImageView) getView().findViewById(R.id.imageView);
            share_image = (ImageButton) getView().findViewById(R.id.share_image);
            download_image = (ImageButton) getView().findViewById(R.id.download_image);

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upload_images_list1_selected_image, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    android.os.Handler handle = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pDialog.incrementProgressBy(1);
        }
    };

    /***********-------download image------****************/

    //public Bitmap downloadImage(String url) {
    public void downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.
                    decodeStream(stream, null, bmOptions);
            stream.close();

            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String code_emp = app_preferences.getString("code_emp", "000");
            String emp_code = app_preferences.getString("employee_code", "111");
            emp_code = emp_code.replace('/', '_');

            //if (code_emp.equals(emp_code)) {
            File f = new File("" + url);
            saveImage(bitmap, f.getName());
            //}

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //return bitmap;
    }

    // Makes HttpURLConnection and returns InputStream
    private InputStream getHttpConnection(String urlString)
            throws IOException {

        final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
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
            ex.printStackTrace();
        }
        return stream;
    }

    /*---Save To External Device---*/
    private void saveImage(Bitmap imageToSave, String fileName) {
        final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
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
            Toast.makeText(getContext(), "Download Successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void call_offline_gallery() {
        try {
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String employee_code = app_preferences.getString("employee_code", "default");//employee_code,first_name,last_name
            employee_code = employee_code.replace('/', '_');

            root = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Android/" + employee_code + "/");
            getfile(root);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private File root;
    private ArrayList<File> fileList = new ArrayList<File>();
    private String[] filesNames;

    public ArrayList<File> getfile(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile != null && listFile.length > 0) {

            for (int i = 0; i < listFile.length; i++) {
                if (fileList.contains(listFile[i])) {
                } else {
                    if (listFile[i].isDirectory()) {
                        fileList.add(listFile[i]);
                        //filesNames[i] = listFile[i].getName();////=
                        getfile(listFile[i]);

                    } else {
                        if (listFile[i].getName().endsWith(".png")
                                || listFile[i].getName().endsWith(".jpg")
                                || listFile[i].getName().endsWith(".jpeg")
                                || listFile[i].getName().endsWith(".gif")) {
                            fileList.add(listFile[i]);
                            //filesNames[i] = listFile[i].getName();/////
                        }
                    }
                }
            }
        }
        return fileList;
    }

    //https://www.youtube.com/watch?v=5aLQZp4NxCU
    //https://www.androidhive.info/2013/06/android-working-with-xml-animations/
    //https://android-coffee.com/tutorial-how-to-zoom-photo-on-imageview-in-android-studio-1-5-1/
}