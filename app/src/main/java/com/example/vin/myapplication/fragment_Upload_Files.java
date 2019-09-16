package com.example.vin.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.app.Activity.RESULT_OK;
import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

/////////////////
//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_Upload_Files#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_Upload_Files extends Fragment {
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
    int pagesize = 10;
    boolean datafull = false;
    int sleep_wait = 0;

    public boolean bool_full_update = true;
    public POJO_Patch_master last_POJO;//POJO_Patch_master
    //https://github.com/kosalgeek/PhotoUtil/tree/master/PhotoUtil/app/src/main/java/com/kosalgeek/android/photoutil
    ///********
    public RealmResults<POJO_User_Hierarchy> POJO_User_Obj;
    ///********

    private OnFragmentInteractionListener mListener;

    //public POJO_SpinnerBind_EmployeeCodeName tbmEmployee;
    ListView listView;
    public EditText edit_search;
    Spinner spinner_tbm_doctors;
    public String designation = "", employeename = "ALL", User_ID_List_String = "";

    private ProgressDialog pDialog;

    RestService restService;
    public adapter_fragment_patch_list customAdapter;

    public RealmResults<POJO_Patch_master> result_query;
    Bundle bundle;

    LinearLayout select_employee;
    LinearLayout select_employee1;
    TextView select_employee2;
    TextView name_patch_of_employee;
    ImageButton select_employee3;
    View vw_employee;

    private long mLastClickTime = 0;

    public fragment_Upload_Files() {
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
    public static fragment_Upload_Files newInstance(String param1, String param2) {
        fragment_Upload_Files fragment = new fragment_Upload_Files();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        try {

            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

            mRealm = Realm.getDefaultInstance();
            POJO_User_Obj = mRealm.where(POJO_User_Hierarchy.class).findAll().sort("modified", Sort.DESCENDING);

            ((DashBord_main) getActivity()).setActionBarTitle("UPLOAD IMAGE");

            if (POJO_User_Obj.size() > 0) {
                // data_fetch();
            } else {
                //alert_box();
            }
            super.onStart();

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
    }


    Button btnupload, UploadImageOnServerButton;
    ImageView imgcover;
    Bitmap FixBitmap;

    //***********************************************//
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String ConvertImage;
    int serverResponseCode = 0;
    HashMap<String, String> HashMapParams;
    /*HashMap<String, String> HashMapParams1;
    HashMap<String, Integer> HashMapParams2;
    HashMap<String, String> HashMapParams3;
    HashMap<String, String> HashMapParams4;
    HashMap<String, String> HashMapParams5;
    HashMap<String, Integer> HashMapParams6;
    HashMap<String, Integer> HashMapParams7;*/

    private static final String[] CAMERA_PERMS = {
            Manifest.permission.CAMERA
    };

    private static final int INITIAL_REQUEST = 1337;
    private static final int CAMERA_REQUEST = INITIAL_REQUEST + 1;
    /*---Permission---*/
    private static final int PERMS_REQUEST_CODE = 123;

    private boolean hasPermission(String perm) {
        if (Build.VERSION.SDK_INT >= 23)//Build.VERSION_CODES.M
            return (PackageManager.PERMISSION_GRANTED == getContext().checkSelfPermission(perm));
        else
            return (PackageManager.PERMISSION_GRANTED == getContext().checkSelfPermission(perm));
    }

    private boolean canAccessCamera() {
        return (hasPermission(Manifest.permission.CAMERA));
    }

    private boolean hasPermissions() {
        int res = 0;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perms : permissions) {
            res = getContext().checkCallingOrSelfPermission(perms);
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

    ///////////////External Storage Permission Start//////////////

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    ///////////////External Storage Permission End//////////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_patch__list, container, false);

        //////////////////////////
        //////////////////////////

        final View view = inflater.inflate(R.layout.fragment_upload_files, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!canAccessCamera()) {
                requestPermissions(CAMERA_PERMS, CAMERA_REQUEST);
            }
        }

        if (hasPermissions()) {
        } else {
            requestPerms();

        }
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Toast.makeText(getContext(), "Ext. Storage Permission Denied", Toast.LENGTH_LONG).show();
        }

        UploadImageOnServerButton = (Button) view.findViewById(R.id.button2);

        imgcover = (ImageView) view.findViewById(R.id.imageView);
        btnupload = (Button) view.findViewById(R.id.button);
        pDialog = new ProgressDialog(getContext());
        byteArrayOutputStream = new ByteArrayOutputStream();
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //Load_Uploded_Files_From_Server();

                ////Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(intent, RESULT_LOAD_IMAGE);
                ////startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
                /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image*//*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);*/
                selectImage();
                //multiple Choose Image
                //http://www.androhub.com/select-and-share-multiple-images/

            }
        });
        UploadImageOnServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                /*http://www.devexchanges.info/2016/01/gridview-with-multiple-selection-in.html
                http://mylearnandroid.blogspot.in/2014/02/multiple-choose-custom-gallery.html*/
                if (HashMapParams.get("filedata") == "" || HashMapParams.get("filedata") == null) {
                    FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    ConvertImage = encode(FixBitmap);

                /*HashMap<String, String> HashMapParams = new HashMap<String, String>();
                HashMapParams.put("cmd", "uploadfile");
                HashMapParams.put("file_size", "74");*/
                    HashMapParams.put("filedata", ConvertImage);
               /* HashMapParams.put("filename", "1514266063_baahubali-2.jpg");
                HashMapParams.put("folder", "Home/files/Amol Mohite");
                HashMapParams.put("from_form", "1");
                HashMapParams.put("is_private", "1");*/


                    //upload_files_to_server1("uploadfile", 74, ConvertImage, "1514266063_baahubali-2.jpg", "Home/files/Amol Mohite", 1, 1);
                }
                UploadImageToServer();//ok work good

                /*HashMapParams3 = new HashMap<String, String>();
                HashMapParams3.put("filedata", ConvertImage);
                upload_files_to_server3(HashMapParams1, HashMapParams2, HashMapParams3, HashMapParams4, HashMapParams5, HashMapParams6, HashMapParams7);*/
            }
        });
        return view;
    }


    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {
                    /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);*/

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(Intent.createChooser(intent, "Click Image"), 0);


                } else if (options[item].equals("Choose from Gallery"))

                {
                    /*Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);*/

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);

                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }


    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);

        if (RC == 0 && RQC == RESULT_OK) {
            try {

                FixBitmap = (Bitmap) I.getExtras().get("data");
                imgcover.setImageBitmap(FixBitmap);
                Uri aa = getImageUri(FixBitmap);

                /////////////////////

                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String name = app_preferences.getString("name", "default");
                String first_name = app_preferences.getString("first_name", "default");
                String last_name = app_preferences.getString("last_name", "default");

                String employee_code = app_preferences.getString("employee_code", "default");
                employee_code = employee_code.replace('/', '_');

                File f = new File(getRealPathFromURI(aa));
                int file_size = Integer.parseInt(String.valueOf(f.length() / 1024));

                String cmd = "uploadfile";
                String filename = f.getName();
                String folder = "Home/files/" + employee_code;//+ first_name + " " + last_name;//+ f.getName()
                Integer from_form = 1;
                Integer is_private = 1;

                HashMapParams = new HashMap<String, String>();
                HashMapParams.put("cmd", "uploadfile");
                HashMapParams.put("file_size", String.valueOf(file_size));
                HashMapParams.put("filename", filename);
                HashMapParams.put("folder", folder);
                HashMapParams.put("from_form", "1");
                HashMapParams.put("is_private", "1");


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();
            try {

                //FixBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                //imgcover.setImageBitmap(FixBitmap);
                FixBitmap = loadImage(getRealPathFromURI(uri), 712, 712);//512,512
                imgcover.setImageBitmap(FixBitmap);
                /*String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                imgcover.setImageBitmap(BitmapFactory.decodeFile(picturePath));*/


                //InputStream is = getActivity().getContentResolver().openInputStream(I.getData());
                //uploadImage(getBytes(is));

                /*File f = new File("" + uri);
                saveImage1(FixBitmap, f.getName() + ".jpeg");*/

                //*********************************************//

                /*FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);*/


                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String name = app_preferences.getString("name", "default");
                String first_name = app_preferences.getString("first_name", "default");
                String last_name = app_preferences.getString("last_name", "default");

                String employee_code = app_preferences.getString("employee_code", "default");
                employee_code = employee_code.replace('/', '_');

                /*ConvertImage = encode(FixBitmap);*/

                //Post http://139.59.63.181
                //cmd=uploadfile
                //file_size=6039
                //filedata=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
                //filename=ic_reset_icon.png
                //folder=Home    or  Home/files/Amol Mohite
                //from_form=1
                //is_private=1

                //response:
                /*{
                    "message": {
                    "comment": {},
                    "file_name": "ic_reset_icon.png",
                    "file_url": "/private/files/ic_reset_icon.png",
                    "name": "a60e77e0e2",
                    "is_private": 1
                }
                }*/


                /*POJO_Upload_Files pojo_save_jpg = new POJO_Upload_Files();*/
                File f = new File(getRealPathFromURI(uri));
                //File f = new File("" + uri);
                Long ll = f.length();
                int file_size = Integer.parseInt(String.valueOf(f.length() / 1024));
                //GetImageNameFromEditText = f.getName();

                String cmd = "uploadfile";
                //Integer file_size=0;
                //String filedata = ConvertImage;
                String filename = f.getName();
                String folder = "Home/files/" + employee_code;//+ first_name + " " + last_name;//+ f.getName()
                Integer from_form = 1;
                Integer is_private = 1;

                /*HashMapParams1 = new HashMap<String, String>();
                HashMapParams2 = new HashMap<String, Integer>();
                HashMapParams4 = new HashMap<String, String>();
                HashMapParams5 = new HashMap<String, String>();
                HashMapParams6 = new HashMap<String, Integer>();
                HashMapParams7 = new HashMap<String, Integer>();

                HashMapParams1.put("cmd", "uploadfile");
                HashMapParams2.put("file_size", file_size);
                HashMapParams4.put("filename", filename);
                HashMapParams5.put("folder", folder);
                HashMapParams6.put("from_form", 1);
                HashMapParams7.put("is_private", 1);*/


                HashMapParams = new HashMap<String, String>();
                HashMapParams.put("cmd", "uploadfile");
                HashMapParams.put("file_size", String.valueOf(file_size));
                HashMapParams.put("filename", filename);
                HashMapParams.put("folder", folder);
                HashMapParams.put("from_form", "1");
                HashMapParams.put("is_private", "1");

                //upload_files_to_server1(cmd, file_size, filedata, filename, folder, from_form, is_private);



                /*POJO_Upload_Files pojo_save_jpg = new POJO_Upload_Files();
                pojo_save_jpg.setcmd(cmd);
                pojo_save_jpg.setfile_size(file_size);
                pojo_save_jpg.setfiledata(filedata);
                pojo_save_jpg.setfilename(filename);
                pojo_save_jpg.setfolder(folder);
                pojo_save_jpg.setfrom_form(from_form);
                pojo_save_jpg.setis_private(is_private);
                upload_files_to_server1(pojo_save_jpg);*/

/*
                "lft":735,
                        "content_hash":"a0cc4b891ea82664ae0f8cb9b81a17ca",
                        "rgt":736,
*/

                //https://discuss.erpnext.com/t/programmatically-upload-files-to-customers/21857
                //https://discuss.erpnext.com/t/file-upload-using-rest-api-not-working/32753/3
/*pojo_save_jpg.setFile_name(f.getName() + ".jpeg");
                pojo_save_jpg.setIs_attachments_folder(0);
                pojo_save_jpg.setIs_folder(0);
                pojo_save_jpg.setIs_home_folder(0);
                pojo_save_jpg.setContent_hash(ConvertImage);
                pojo_save_jpg.setFile_size(file_size);
                pojo_save_jpg.setOwner(name);//
                pojo_save_jpg.setModified_by(name);//
                pojo_save_jpg.setFolder(first_name + " " + last_name);
                pojo_save_jpg.setIs_private(0);
                pojo_save_jpg.setFile_url("/private/files/" + first_name + " " + last_name + "/" + f.getName() + ".jpeg");
               upload_files_to_server(pojo_save_jpg);*/


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContext().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public void decodeUri(Uri uri) {
        ParcelFileDescriptor parcelFD = null;
        try {

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            ////String myCookie = "sid=igbrown";
            //urlConn.setRequestProperty("Cookie", sid);

            parcelFD = getActivity().getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor imageSource = parcelFD.getFileDescriptor();

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(imageSource, null, o);

            // the new size we want to scale to
            final int REQUIRED_SIZE = 1024;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);

            imgcover.setImageBitmap(bitmap);

        } catch (FileNotFoundException e) {
            // handle errors
        } /*catch (IOException e) {
            // handle errors
        }*/ finally {
            if (parcelFD != null)
                try {
                    parcelFD.close();
                } catch (IOException e) {
                    // ignored
                }
        }
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),
                inImage, "Title", null);
        return Uri.parse(path);
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

    private void Load_Uploded_Files_From_Server() {
        try {
            // http://139.59.63.181/api/resource/File/?fields=["name", "file_name","file_url"]&filters=[["File", "name", "=","a8306f51f3"]]
            //http://139.59.63.181/api/resource/File/?fields=["name", "file_name","file_url"]&filters=[["File", "file_url", "like","/private/files/%"],["File", "file_name", "like","%.png"]]

            ////TextView name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);
            //Thread.sleep(sleep_wait);

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("file_name");//: "ic_reset_icon.png",
            jsonArray.put("creation");//: "2018-03-22 17:31:42.907329",
            //jsonArray.put("doctype");//: "File",
            jsonArray.put("lft");//: 735,
            jsonArray.put("is_attachments_folder");//: 0,
            jsonArray.put("is_folder");//: 0,
            jsonArray.put("file_size");//: 6039,
            jsonArray.put("owner");//: "Administrator",
            jsonArray.put("content_hash");//: "a0cc4b891ea82664ae0f8cb9b81a17ca",
            jsonArray.put("modified_by");//: "Administrator",
            jsonArray.put("is_home_folder");//: 0,
            jsonArray.put("folder");//: "Home/Report JS File",
            jsonArray.put("rgt");//: 736,
            jsonArray.put("is_private");//: 1,
            jsonArray.put("name");//: "52c50dac6a",
            jsonArray.put("file_url");//: "/private/files/ic_reset_icon.png",
            jsonArray.put("modified");//: "2018-03-22 17:31:42.959852",
            jsonArray.put("old_parent");//: "Home/Report JS File"

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();

            Filter1.put("File");
            Filter1.put("name");
            Filter1.put("=");//52c50dac6a//a8306f51f3
            Filter1.put("0c14b0dc8a");
            Filters.put(Filter1);

            pDialog.show();
//
            restService.getService().getUploadedFiles(sid, "modified desc", limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Upload_Files>>() {
                        }.getType();
                        List<POJO_Upload_Files> POJO = gson.fromJson(j2, type);


                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();
                        mRealm.close();


                        if (POJO.size() == 0) {
                            datafull = true;
                            //Bind_data_listview();
                            geturl();
                            pDialog.hide();
                        } else {
                            limitstart = limitstart + pagesize;
                        }


                        /*if (bool_full_update == false) {
                            for (POJO_Upload_Files pp : POJO) {
                                if (last_POJO == null) {
                                    datafull = false;
                                } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                    datafull = true;
                                    geturl(pp.getFile_url());
                                    pDialog.hide();
                                }
                            }
                        }*/

                        if (datafull == false) {
                            Load_Uploded_Files_From_Server();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }


                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();
                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }


                        if (msg.equals("403 FORBIDDEN")) {

                            //Comment code Direct Goes To Login Fragment
                            //SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                            //SharedPreferences.Editor editor = app_preferences.edit();
                            //editor.putString("status", "0");
                            //editor.commit();

                            //Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            //getContext().startActivity(k);

                            Toast.makeText(getContext(), "ERROR..", Toast.LENGTH_SHORT).show();
                        } else if (msg.contains("139.59.63.181")) {
                            //Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                            //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            //txt_loading.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }

                        if (task_user != null) {
                            task_user.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void geturl() {
        try {
            String ss = "";
            //.equalTo("modified_by", name_patch_of_employee.getText().toString().trim())
            final RealmResults<POJO_Upload_Files> result_query1 = mRealm.where(POJO_Upload_Files.class).findAll().sort("modified", Sort.ASCENDING);
            List<POJO_Upload_Files> POJO = result_query1;

            for (POJO_Upload_Files pp : POJO) {
                ss = pp.getFile_url().toString();
            }

            //Toast.makeText(getContext(), ss.toString(), Toast.LENGTH_SHORT).show();

            GetXMLTask task = new GetXMLTask();
            // Execute the task
            //task.execute(new String[]{"http://139.59.63.181/private/files/ic_reset_icon.png"});
            task.execute(new String[]{"http://139.59.63.181" + ss});

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    //ok done show successfully
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
            imgcover.setImageBitmap(result);
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

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Cookie", sid);
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

    android.os.Handler handle = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pDialog.incrementProgressBy(1);
        }
    };

    //////////////////////////////////////////
    /////////////////////////////////////////
    ////////////////////////////////////////

    public static String encode(Bitmap bitmap) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        int quality = 100; //100: compress nothing
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bao);

        if (bitmap != null && !bitmap.isRecycled()) {//important! prevent out of memory
            bitmap.recycle();
            bitmap = null;
        }

        byte[] ba = bao.toByteArray();
        String encodedImage = Base64.encodeToString(ba, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap decode(String encodedImage) {
        final String pureBase64Encoded = encodedImage.substring(encodedImage.indexOf(",") + 1);
        byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.URL_SAFE);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return bitmap;
    }


    /*&&&&&&&&&&&&&&&&&&&&&&&&&&& Done Upload Files To Server &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&*/
    String ImageTag = "image_tag";
    String ImageName = "image_data";
    String ServerUploadPath = "http://139.59.63.181";
    String GetImageNameFromEditText;
    HttpURLConnection httpURLConnection;
    OutputStream outputStream;
    BufferedWriter bufferedWriter;
    int RC;
    URL url;

    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    boolean check = true;
    Integer call_UploadImageToServer = 0;

    public void UploadImageToServer() {

        //FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        //byteArray = byteArrayOutputStream.toByteArray();
        //ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        /*FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        ConvertImage = encode(FixBitmap);*/

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                pDialog = ProgressDialog.show(getContext(), "Image is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {
//{"exc":"[\"Traceback (most recent call last):\\n  File \\\"/home/frappe/frappe-bench/apps/frappe/frappe/handler.py\\\", line 108, in uploadfile\\n    ret = frappe.utils.file_manager.upload()\\n  File \\\"/home/frappe/frappe-bench/apps/frappe/frappe/utils/file_manager.py\\\", line 36, in upload\\n    filedata = save_uploaded(dt, dn, folder, is_private)\\n  File \\\"/home/frappe/frappe-bench/apps/frappe/frappe/utils/file_manager.py\\\", line 60, in save_uploaded\\n    return save_file(fname, content, dt, dn, folder, is_private=is_private);\\n  File \\\"/home/frappe/frappe-bench/apps/frappe/frappe/utils/file_manager.py\\\", line 182, in save_file\\n    f.insert()\\n  File \\\"/home/frappe/frappe-bench/apps/frappe/frappe/model/document.py\\\", line 193, in insert\\n    self._validate()\\n  File \\\"/home/frappe/frappe-bench/apps/frappe/frappe/model/document.py\\\", line 400, in _validate\\n    self._validate_links()\\n  File \\\"/home/frappe/frappe-bench/apps/frappe/frappe/model/document.py\\\", line 635, in _validate_links\\n    frappe.LinkValidationError)\\n  File \\\"/home/frappe/frappe-bench/apps/frappe/frappe/__init__.py\\\", line 320, in throw\\n    msgprint(msg, raise_exception=exc, title=title, indicator='red')\\n  File \\\"/home/frappe/frappe-bench/apps/frappe/frappe/__init__.py\\\", line 310, in msgprint\\n    _raise_exception()\\n  File \\\"/home/frappe/frappe-bench/apps/frappe/frappe/__init__.py\\\", line 283, in _raise_exception\\n    raise raise_exception(encode(msg))\\nLinkValidationError: Could not find Folder: Home/files/EMP_0006\\n\"]","_server_messages":"[\"{\\\"message\\\": \\\"Could not find Folder: Home/files/EMP_0006\\\", \\\"indicator\\\": \\\"red\\\"}\"]"}
                super.onPostExecute(string1);
                pDialog.dismiss();

                if (string1.contains("File Not Found")) {
                    call_UploadImageToServer = 1;
                    UploadImageToServer();
                } else if (string1.equals("") || string1.contains("Traceback")) {
                    Toast.makeText(getContext(), "Choose Again Image And Try Again To Upload Image", Toast.LENGTH_LONG).show();
                } else if (string1.contains("name") || string1.contains("file")) {
                    //Toast.makeText(getContext(), string1, Toast.LENGTH_LONG).show();
                    Toast.makeText(getContext(), "Image Uploading Successfully !!!", Toast.LENGTH_LONG).show();
                } else if (string1.equals("{}")) {
                    //Toast.makeText(getContext(), string1, Toast.LENGTH_LONG).show();
                    UploadImageToServer();
                }
            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                /*HashMap<String, String> HashMapParams = new HashMap<String, String>();*/

                /*HashMapParams.put(ImageTag, GetImageNameFromEditText);
                HashMapParams.put(ImageName, ConvertImage);*/

               /* HashMapParams.put("cmd", "uploadfile");
                HashMapParams.put("file_size", "74");
                HashMapParams.put("filedata", ConvertImage);
                HashMapParams.put("filename", "1514266063_baahubali-2.jpg");
                HashMapParams.put("folder", "Home/files/Amol Mohite");
                HashMapParams.put("from_form", "1");
                HashMapParams.put("is_private", "1");*/

                //String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);
                //return FinalData;

                String FinalData = "";
                if (call_UploadImageToServer == 1) {
                    call_UploadImageToServer = 0;
                    final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    String employee_code = app_preferences.getString("employee_code", "default");
                    employee_code = employee_code.replace('/', '_');

                    HashMap<String, String> HashMapParams1 = new HashMap<String, String>();
                    HashMapParams1.put("cmd", "frappe.core.doctype.file.file.create_new_folder");
                    HashMapParams1.put("folder", "Home/files");
                    HashMapParams1.put("file_name", employee_code);

                    FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams1);
                    return FinalData;
                } else {
                    FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);
                    return FinalData;
                }
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

                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String sid = app_preferences.getString("sid", "default");
                httpURLConnection.setRequestProperty("Cookie", sid);

                /*httpURLConnection.setReadTimeout(20000);
                httpURLConnection.setConnectTimeout(20000);*/

                httpURLConnection.setRequestMethod("POST");
                String boundary = "*****";
                String crlf = "\r\n";
                String twoHyphens = "--";
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
                httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                httpURLConnection.setRequestProperty("Accept", "application/json");
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
                if (RC == HttpsURLConnection.HTTP_INTERNAL_ERROR) {
                    return "File Not Found";
                }

            } catch (Exception e) {
                if (e.getMessage().contains("FileNot"))
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


    /*********************No Important Data*****************************/


    public void upload_files_to_server(POJO_Upload_Files POJO) {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().UploadedFilesToServer(sid, POJO, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        //update_patch_master_class(jsonElement);
                        pDialog.hide();
                        Toast.makeText(getContext(), "UPLOAD SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();

                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                            //onsession_failure();

                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void upload_files_to_server1(String cmd, Integer file_size, String filedata, String filename, String folder, Integer from_form, Integer is_private) {
        //public void upload_files_to_server1(POJO_Upload_Files POJO) {
        try {
            restService = new RestService("", "");//call only url address not include api
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().UploadedFilesToServer1(sid, cmd, file_size, filedata, filename, folder, from_form, is_private, new Callback<JsonElement>() {
                //restService.getService().UploadedFilesToServer1(sid, POJO, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        //update_patch_master_class(jsonElement);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");
                        /*Json Data:"file_name": "ic_reset_icon.png",
                          "file_url": "/private/files/ic_reset_icon.png",
                          "name": "a957884065",
                          "is_private": 1*/

                        //String app_support = j2.get("app_ver_count").getAsString();
                        pDialog.hide();
                        Toast.makeText(getContext(), "UPLOAD SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();

                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                            //onsession_failure();

                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void upload_files_to_server2(Map<String, String> params) {
        try {
            restService = new RestService("", "");//call only url address not include api
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().UploadedFilesToServer2(sid, params, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        pDialog.hide();
                        Toast.makeText(getContext(), "UPLOAD SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();

                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                            //onsession_failure();

                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void upload_files_to_server3(Map<String, String> params1, Map<String, Integer> params2, Map<String, String> params3, Map<String, String> params4, Map<String, String> params5, Map<String, Integer> params6, Map<String, Integer> params7) {
        try {
            restService = new RestService("", "");//call only url address not include api
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().UploadedFilesToServer3(sid, params1, params2, params3, params4, params5, params6, params7, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        //update_patch_master_class(jsonElement);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");
                        /*Json Data:"file_name": "ic_reset_icon.png",
                          "file_url": "/private/files/ic_reset_icon.png",
                          "name": "a957884065",
                          "is_private": 1*/

                        //String app_support = j2.get("app_ver_count").getAsString();
                        pDialog.hide();
                        Toast.makeText(getContext(), "UPLOAD SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();

                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                            //onsession_failure();

                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    /*ImageProcessClass2 imageProcessClass = new ImageProcessClass2();
    JSONObject post_dict = new JSONObject();
                try {
        post_dict.put("cmd", HashMapParams.get("cmd"));
        post_dict.put("file_size", HashMapParams.get("file_size"));
        post_dict.put("filedata", HashMapParams.get("filedata"));
        post_dict.put("filename", HashMapParams.get("filename"));
        post_dict.put("folder", HashMapParams.get("folder"));
        post_dict.put("from_form", HashMapParams.get("from_form"));
        post_dict.put("is_private", HashMapParams.get("is_private"));
    } catch (JSONException e) {
        e.printStackTrace();
    }
    String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, String.valueOf(post_dict));
    public class ImageProcessClass2 {

        public String ImageHttpRequest(String requestURL, String params) {

            String JsonResponse = null;
            String JsonDATA = params;
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(requestURL);
                urlConnection = (HttpURLConnection) url.openConnection();

                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String sid = app_preferences.getString("sid", "default");
                urlConnection.setRequestProperty("X-Frappe-CSRF-Token", sid);

                urlConnection.setDoOutput(true);
                // is output buffer writter
                urlConnection.setRequestMethod("POST");
                *//*urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");*//*
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                urlConnection.setRequestProperty("charset", "utf-8");

//set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);
// json data
                writer.close();
                InputStream inputStream = urlConnection.getInputStream();
//input stream
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                JsonResponse = buffer.toString();
//response data
                //Log.i(TAG,JsonResponse);
                try {
//send to post execute
                    return JsonResponse;
                } catch (JsonIOException e) {
                    e.printStackTrace();
                }
                return null;


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        //Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
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

    }*/


    /*----------------Create New Folder To Server Side-------------------------*/
    /*
    cmd:	frappe.core.doctype.file.file.create_new_folder
    file_name:	aaaaa
    folder:	Home/files
     */

    public static Bitmap loadImage(String imagePath, int reqWidth, int reqHeight) {
        float imageViewWidth = reqWidth;
        float imageViewHeight = reqHeight;


        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        float scaleFactor;
        scaleFactor = Math.max(photoW / imageViewWidth, photoH / imageViewHeight);


        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = (int) Math.ceil(scaleFactor);


        bmOptions.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);

        try {
            ExifInterface exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            Matrix matrix = new Matrix();
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e) {

        }
        return bitmap;
    }

}
