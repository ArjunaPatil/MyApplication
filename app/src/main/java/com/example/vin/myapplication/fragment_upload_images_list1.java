package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;
import static com.example.vin.myapplication.MoviesAdapter.context;

//https://www.androidhive.info/2013/09/android-fullscreen-image-slider-with-swipe-and-pinch-zoom-gestures/

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_upload_images_list1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_upload_images_list1 extends Fragment
        implements user_list_hierarchy_FragmentDialog.EditUserListHirarchyDialogListener, AbsListView.OnScrollListener {
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

    GridView gvImages = null;
    fragment_upload_images_list_adapter adapterImages;
    fragment_upload_images_list_adapter_for_selected_user1 adapterImages2;

    LinearLayout select_employee;
    LinearLayout select_employee1;
    TextView select_employee2;
    TextView name_file_of_employee;
    ImageButton select_employee3;
    View vw_employee;

    LinearLayout my_gallery1;
    TextView my_gallery2;

    public RealmResults<POJO_User_Hierarchy> POJO_User_Obj;
    public RealmResults<POJO_Upload_Files> POJO_Upload_Files;
    public RealmResults<POJO_Upload_Files_For_Selected_User> POJO_Upload_Files_For_Selected_User;
    public String designation = "";
    private long mLastClickTime = 0;

    public fragment_upload_images_list1() {
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
    public static fragment_upload_images_list1 newInstance(String param1, String param2) {
        fragment_upload_images_list1 fragment = new fragment_upload_images_list1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        try {

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = (app_preferences.getString("designation", "default"));

            mRealm = Realm.getDefaultInstance();
            POJO_User_Obj = mRealm.where(POJO_User_Hierarchy.class).findAll().sort("modified", Sort.DESCENDING);

            ((DashBord_main) getActivity()).setActionBarTitle("GALLERY");

            init_control();
            get_offline_file_list();

            if (POJO_User_Obj.size() > 0) {

                pDialog = new ProgressDialog(getContext());

                if (designation.equals("TBM") || designation.equals("KBM")) {
                    /*datafull = false;
                    call_my_gallery();*/

                    String employee_code = app_preferences.getString("employee_code", "-");//employee_code,first_name,last_name
                    employee_code = employee_code.replace('/', '_');
                    name_file_of_employee.setText(employee_code);
                    select_employee2.setText("MY GALLERY");

                    Check_class_selected_user_null_or_not();

                } else {
               /*--------Employee DropDown----------*/
                    select_employee1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clear_pojo();
                        }
                    });
                    select_employee2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clear_pojo();
                        }
                    });
                    select_employee3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clear_pojo();
                        }
                    });
                }

                /*--------My Gallery----------*/
               /* my_gallery1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datafull = false;
                        call_my_gallery();
                    }
                });
                my_gallery2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datafull = false;
                        call_my_gallery();
                    }
                });*/
                //my_gallery1,my_gallery2,

            } else {
                alert_box();
            }

            super.onStart();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    static final int REQUEST_PERMISSION_KEY = 1;

    public void init_control() {
        try {
            gvImages = (GridView) getView().findViewById(R.id.grid_products);

            int iDisplayWidth = getResources().getDisplayMetrics().widthPixels;
            Resources resources = getContext().getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float dp = iDisplayWidth / (metrics.densityDpi / 160f);

            if (dp < 360) {
                dp = (dp - 17) / 2;
                float px = convertDpToPixel(dp, getContext());
                gvImages.setColumnWidth(Math.round(px));
            }

            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE};
            if (!hasPermissions(getContext(), PERMISSIONS)) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_PERMISSION_KEY);
            }

            select_employee = (LinearLayout) getView().findViewById(R.id.select_employee);
            select_employee1 = (LinearLayout) getView().findViewById(R.id.select_employee1);
            select_employee3 = (ImageButton) getView().findViewById(R.id.select_employee3);
            select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
            name_file_of_employee = (TextView) getView().findViewById(R.id.name_file_of_employee);

            vw_employee = getView().findViewById(R.id.vw_employee);

            my_gallery1 = (LinearLayout) getView().findViewById(R.id.my_gallery1);
            my_gallery2 = (TextView) getView().findViewById(R.id.my_gallery2);

            select_employee.setVisibility(View.GONE);
            select_employee1.setVisibility(View.GONE);
            select_employee3.setVisibility(View.GONE);
            select_employee2.setVisibility(View.GONE);
            name_file_of_employee.setVisibility(View.GONE);

           /*vw_employee.setVisibility(View.GONE);

            my_gallery1.setVisibility(View.GONE);
            my_gallery2.setVisibility(View.GONE);*/

            if (designation.equals("ABM") || designation.equals("RBM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("CRM") || (designation.equals("NBM")) || (designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager")) || (designation.equals("Administrator"))) {
                select_employee.setVisibility(View.VISIBLE);
                select_employee1.setVisibility(View.VISIBLE);
                select_employee3.setVisibility(View.VISIBLE);
                select_employee2.setVisibility(View.VISIBLE);
                name_file_of_employee.setVisibility(View.VISIBLE);

               /* vw_employee.setVisibility(View.VISIBLE);
                my_gallery1.setVisibility(View.VISIBLE);
                my_gallery2.setVisibility(View.VISIBLE);*/
            } else {

            }

            /*------------Share Image Successfully Code---------------------------*/

            gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        final int position, long id) {
                    try {

                        POJO_Upload_Files_For_Selected_User clickedObj = (POJO_Upload_Files_For_Selected_User) parent.getItemAtPosition(position);

                        final Bundle bundle = new Bundle();
                        bundle.putString("name", clickedObj.getName()); // use as per your need
                        bundle.putString("url", clickedObj.getFile_url());
                        bundle.putString("file_name", clickedObj.getFile_name());
                        bundle.putString("offline", clickedObj.getIs_present_in_off_line().toString());
                        bundle.putString("size", clickedObj.getFile_size().toString());
                        bundle.putString("creation", clickedObj.getCreation());
                        bundle.putString("selected_user", "");
                        bundle.putString("user_code", "");

                        Fragment frag = new fragment_upload_images_list1_selected_image();

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);
                        frag.setArguments(bundle);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack("img_list");
                        ft.commit();

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            /*gvImages.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                               int index, long arg3) {

                    POJO_Upload_Files_For_Selected_User clickedObj = (POJO_Upload_Files_For_Selected_User) arg0.getItemAtPosition(index);
                    Integer off_flag = clickedObj.getIs_present_in_off_line();
                    if (off_flag == 0) {

                        String Path = fileList.get(index).getPath();

                        //Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        Uri screenshotUri = Uri.parse(Path);

                        final Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);//Uri.fromFile()
                        intent.setType("image*//**//*");
                        startActivity(intent);
                        return false;

                    } else {
                        Toast.makeText(getContext(), "First Download And Share", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            });*/
                    /*SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    String code_emp = app_preferences.getString("code_emp", "000");
                    String emp_code = app_preferences.getString("employee_code", "111");
                    emp_code = emp_code.replace('/', '_');

                    if (code_emp.equals(emp_code)) {
                        // write your code
                        String Path = fileList.get(index).getPath();

                        //Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        Uri screenshotUri = Uri.parse(Path);

                        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);//Uri.fromFile()
                        intent.setType("image*//*");
                        startActivity(intent);
                        return false;
                    } else {
                        // write your code
                        POJO_Upload_Files_For_Selected_User clickedObj = (POJO_Upload_Files_For_Selected_User) arg0.getItemAtPosition(index);
                        String Path = "http://139.59.63.181" + clickedObj.getFile_url();
                        //Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        Uri screenshotUri = Uri.parse(Path);

                        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);//Uri.fromFile()
                        intent.setType("image*//*");//png
                        startActivity(intent);
                        return false;
                    }*/




            /*------------Download Image Successfully Code---------------------------*/

           /* gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    try {

                                                        POJO_Upload_Files_For_Selected_User clickedObj = (POJO_Upload_Files_For_Selected_User) parent.getItemAtPosition(position);
                                                        final String Path = "http://139.59.63.181" + clickedObj.getFile_url();
                                                        Integer off_flag = clickedObj.getIs_present_in_off_line();

                                                        if (off_flag == 0) {
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                                            builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Message</font>"));
                                                            builder.setMessage("Do You Want Download This Image ?");

                                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    downloadImage(Path);
                                                                }
                                                            });
                                                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    dialog.dismiss();
                                                                }
                                                            });

                                                            AlertDialog alert = builder.create();
                                                            alert.show();

                                                        } else {
                                                            Toast.makeText(getContext(), "Downloaded Image", Toast.LENGTH_SHORT).show();
                                                        }

                                                    } catch (Exception ex) {
                                                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            }
            );*/


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_KEY: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(getContext(), "You must accept permissions.", Toast.LENGTH_LONG).show();
                }
            }
        }

    }


    public void clear_pojo() {
        try {
            /*datafull = false;
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_Upload_Files_For_Selected_User.class);
            mRealm.commitTransaction();
            mRealm.close();*/
            show_dialog_for_select_employee();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_employee() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {

                user_list_hierarchy_FragmentDialog dialog = user_list_hierarchy_FragmentDialog.newInstance("Hello world");

                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "img");
                //dialog.setView(layout);
                dialog.setArguments(bundle);

                dialog.setTargetFragment(fragment_upload_images_list1.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Check_class_selected_user_null_or_not() {
        try {
            gvImages.setAdapter(null);
            datafull = false;
            limitstart = 0;
            pagesize = 10;
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_Upload_Files_For_Selected_User.class);
            mRealm.commitTransaction();
            mRealm.close();

            mRealm = Realm.getDefaultInstance();
            POJO_Upload_Files_For_Selected_User = mRealm.where(POJO_Upload_Files_For_Selected_User.class).findAll();
            if (POJO_Upload_Files_For_Selected_User.size() <= 0) {
                Load_Uploded_Files_From_Server_for_selected_user();
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFinishUserListHirarchyDialog(String id, String fullname) {
        try {
            TextView select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
            TextView name_file_of_employee = (TextView) getView().findViewById(R.id.name_file_of_employee);

            if (fullname.equals("ALL")) {
                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String employee_code = app_preferences.getString("employee_code", "-");
                employee_code = employee_code.replace('/', '_');
                //fullname = app_preferences.getString("first_name", "-") + " " + app_preferences.getString("last_name", "-");
                select_employee2.setText("MY GALLERY");//fullname.toString()
                name_file_of_employee.setText(employee_code.toString());
            } else {
                select_employee2.setText(fullname.toString());
                id = id.replace('/', '_');
                name_file_of_employee.setText(id.toString());

            }

            /*Load_Uploded_Files_From_Server_for_selected_user();*/

            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("code_emp", name_file_of_employee.getText().toString());
            editor.commit();

            Check_class_selected_user_null_or_not();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void call_my_gallery() {
        try {
            call_gallery();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void get_offline_file_list() {
        try {
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String employee_code = app_preferences.getString("employee_code", "default");//employee_code,first_name,last_name
            employee_code = employee_code.replace('/', '_');
            name_file_of_employee.setText(employee_code);

            root = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Android/" + employee_code + "/");
            getfile(root);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void call_gallery() {
        try {
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            /*String employee_code = app_preferences.getString("employee_code", "default");
            employee_code = employee_code.replace('/', '_');
            if (employee_code.equals(name_file_of_employee.getText().toString())) {

            } else {
                mRealm = Realm.getDefaultInstance();
                mRealm.beginTransaction();
                mRealm.delete(POJO_Upload_Files.class);
                mRealm.commitTransaction();
                mRealm.close();
            }*/

            /*SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("code_emp", name_file_of_employee.getText().toString());
            editor.commit();

            Load_Uploded_Files_From_Server();*/

            String employee_code = app_preferences.getString("employee_code", "-");
            employee_code = employee_code.replace('/', '_');
            String fullname = app_preferences.getString("first_name", "-") + " " + app_preferences.getString("last_name", "-");
            select_employee2.setText(fullname.toString());

            TextView name_file_of_employee = (TextView) getView().findViewById(R.id.name_file_of_employee);
            name_file_of_employee.setText(employee_code.toString());

            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("code_emp", name_file_of_employee.getText().toString());
            editor.commit();

            Load_Uploded_Files_From_Server();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void alert_box() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Message</font>"));
            builder.setMessage("Not Load Fully Hierarchy. Please Load Hierarchy Fully...");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Fragment frag = new fragment_Hierarchy_Users_List();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack("file_list");
                    ft.commit();

                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void alert_box_for_offline() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Message</font>"));
            builder.setMessage("Do You Want See Offline Gallery ?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    offline_gallery();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
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
        return inflater.inflate(R.layout.fragment_upload_images_list, container, false);
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

    /********--------------------*************/

    private void Load_Uploded_Files_From_Server() {
        try {
            // http://139.59.63.181/api/resource/File/?fields=["name", "file_name","file_url"]&filters=[["File", "name", "=","a8306f51f3"]]
            //http://139.59.63.181/api/resource/File/?fields=["name", "file_name","file_url"]&filters=[["File", "file_url", "like","/private/files/%"],["File", "file_name", "like","%.png"]]

            ////TextView name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);
            //Thread.sleep(sleep_wait);

            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
            /*SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("code_emp", name_file_of_employee.getText().toString());
            editor.commit();*/

            restService = new RestService();
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

            /*Filter1.put("File");
            Filter1.put("name");
            Filter1.put("=");//52c50dac6a//a8306f51f3
            Filter1.put("0c14b0dc8a");
            Filters.put(Filter1);*/

            Filter1.put("File");
            Filter1.put("folder");
            Filter1.put("=");//52c50dac6a//a8306f51f3
            //Filter1.put("Home/files/Amol Mohite%");
            Filter1.put("Home/files/" + name_file_of_employee.getText().toString());//+ "%"
            Filters.put(Filter1);

            pDialog.show();//modified
//
            restService.getService().getUploadedFiles(sid, "creation desc", limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
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

                        /*---------------------------------------------------*/

                        if (POJO != null) {
                            String date_cmp_for_header = "";
                            for (POJO_Upload_Files pp : POJO) {

                                if (pp != null) {
                                    if (date_cmp_for_header.equals("")) {
                                        pp.setDate_flag(1);
                                        date_cmp_for_header = pp.getCreation().substring(0, 10);
                                    } else {
                                        if (date_cmp_for_header.equals(pp.getCreation().substring(0, 10))) {
                                            pp.setDate_flag(0);
                                        } else {
                                            pp.setDate_flag(1);
                                            date_cmp_for_header = pp.getCreation().substring(0, 10);
                                        }
                                    }

                                    /*if (fileList.size() > 0) {
                                        if (fileList.contains(pp.getFile_name().toString())) {
                                            pp.setIs_present_in_off_line(0);
                                        } else {
                                            pp.setIs_present_in_off_line(1);
                                        }
                                    } else {
                                        pp.setIs_present_in_off_line(1);
                                    }*/

                                    if (fileList.size() > 0) {
                                        String File_name = pp.getFile_name().toString();
                                        /*for (Integer i = 0; i < fileList.size(); i++) {
                                            String path = fileList.get(i).getPath();

                                            if (path.contains(File_name)) {
                                                pp.setIs_present_in_off_line(0);
                                            } else {
                                                pp.setIs_present_in_off_line(1);
                                            }
                                        }*/

                                        for (File file : fileList) {
                                            if (file.getName().contains(File_name)) {
                                                pp.setIs_present_in_off_line(0);
                                            } else {
                                                pp.setIs_present_in_off_line(1);
                                            }
                                        }
                                    } else {
                                        pp.setIs_present_in_off_line(1);
                                    }
                                }

                            }
                        }

                        /*--------------------------------------------------*/

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

                        /*-------------------------*/
                        //alert_box_for_offline();
                        /*------------------------*/

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void Load_Uploded_Files_From_Server_for_selected_user() {
        try {
            // http://139.59.63.181/api/resource/File/?fields=["name", "file_name","file_url"]&filters=[["File", "name", "=","a8306f51f3"]]
            //http://139.59.63.181/api/resource/File/?fields=["name", "file_name","file_url"]&filters=[["File", "file_url", "like","/private/files/%"],["File", "file_name", "like","%.png"]]

            ////TextView name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);
            //Thread.sleep(sleep_wait);

            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
            /*SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("code_emp", name_file_of_employee.getText().toString());
            editor.commit();*/

            restService = new RestService();
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

            /*Filter1.put("File");
            Filter1.put("name");
            Filter1.put("=");//52c50dac6a//a8306f51f3
            Filter1.put("0c14b0dc8a");
            Filters.put(Filter1);*/

            Filter1.put("File");
            Filter1.put("folder");
            Filter1.put("=");//52c50dac6a//a8306f51f3
            //Filter1.put("Home/files/Amol Mohite%");
            String ss = "Home/files/" + name_file_of_employee.getText().toString();
            Filter1.put(ss);//+ "%"
            Filters.put(Filter1);

            pDialog.show();//modified
//
            restService.getService().getUploadedFiles(sid, "creation desc", limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();

                        Type type = new TypeToken<List<POJO_Upload_Files_For_Selected_User>>() {
                        }.getType();
                        List<POJO_Upload_Files_For_Selected_User> POJO = gson.fromJson(j2, type);

                        /*--------------------------------------------*/

                        //////////////////////////////////////////
                        if (POJO != null) {
                            String date_cmp_for_header = "";
                            for (POJO_Upload_Files_For_Selected_User pp : POJO) {

                                if (pp != null) {
                                    if (date_cmp_for_header.equals("")) {
                                        pp.setDate_flag(1);
                                        date_cmp_for_header = pp.getCreation().substring(0, 10);
                                    } else {
                                        if (date_cmp_for_header.equals(pp.getCreation().substring(0, 10))) {
                                            pp.setDate_flag(0);
                                        } else {
                                            pp.setDate_flag(1);
                                            date_cmp_for_header = pp.getCreation().substring(0, 10);
                                        }
                                    }

                                    if (fileList.size() > 0) {
                                        String File_name = pp.getFile_name().toString();
                                        /*for (Integer i = 0; i < fileList.size(); i++) {
                                            String path = fileList.get(i).getPath();

                                            if (path.contains(File_name)) {
                                                pp.setIs_present_in_off_line(0);
                                            } else {
                                                pp.setIs_present_in_off_line(1);
                                            }
                                        }*/

                                        for (File file : fileList) {
                                            if (file.getName().contains(File_name)) {
                                                pp.setIs_present_in_off_line(0);
                                            } else {
                                                pp.setIs_present_in_off_line(1);
                                            }
                                        }
                                    } else {
                                        pp.setIs_present_in_off_line(1);
                                    }

                                }

                            }
                        }
                        /////////////////////////////////////////////////////////////////////////

                        /*-------------------------------------------*/

                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();
                        mRealm.close();


                        if (POJO.size() == 0) {
                            pDialog.hide();
                            datafull = true;
                            //Bind_data_listview();
                            geturl();

                        } else {
                            limitstart = limitstart + pagesize;
                        }

                        /*if (bool_full_update == false) {
                            for (POJO_Upload_Files_For_Selected_User pp : POJO) {
                                if (last_POJO == null) {
                                    datafull = false;
                                } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                    datafull = true;
                                    geturl();
                                    pDialog.hide();
                                }
                            }
                        }*/

                        if (datafull == false) {
                            Load_Uploded_Files_From_Server_for_selected_user();
                        }

                    } catch (Exception ex) {
                        pDialog.hide();
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

                        /*-------------------------*/
                        //alert_box_for_offline();
                        /*------------------------*/

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
            //ImageDownloader imageDownloader = new ImageDownloader(getContext());
            //imageDownloader.download("http://139.59.63.181" + product.getFile_url(), img);

            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String code_emp = app_preferences.getString("code_emp", "000");
            String emp_code = app_preferences.getString("employee_code", "111");
            emp_code = emp_code.replace('/', '_');

            mRealm = Realm.getDefaultInstance();
            final RealmResults<POJO_Upload_Files_For_Selected_User> result_query2 = mRealm.where(POJO_Upload_Files_For_Selected_User.class).findAll().sort("modified", Sort.ASCENDING);
            List<POJO_Upload_Files_For_Selected_User> POJO2 = result_query2;

            if (POJO2.size() > 0) {
                adapterImages2 = new fragment_upload_images_list_adapter_for_selected_user1(getContext(), POJO2);
                gvImages.setAdapter(adapterImages2);
            } else {
                gvImages.setAdapter(null);
                Toast.makeText(getContext(), "NO DATA FOUND", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void geturl_old() {
        try {
            String ss = "";
            //.equalTo("modified_by", name_patch_of_employee.getText().toString().trim())
            //ImageDownloader imageDownloader = new ImageDownloader(getContext());
            //imageDownloader.download("http://139.59.63.181" + product.getFile_url(), img);

            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String code_emp = app_preferences.getString("code_emp", "000");
            String emp_code = app_preferences.getString("employee_code", "111");
            emp_code = emp_code.replace('/', '_');

            if (code_emp.equals(emp_code)) {
                mRealm = Realm.getDefaultInstance();
                final RealmResults<POJO_Upload_Files> result_query1 = mRealm.where(POJO_Upload_Files.class).findAll().sort("modified", Sort.ASCENDING);
                List<POJO_Upload_Files> POJO = result_query1;

                /*if (POJO.size() > fileList.size()) {
                    for (POJO_Upload_Files pp : POJO) {
                        if (fileList.contains(pp.getFile_name().toString())) {
                        } else {
                            ImageView img = (ImageView) getView().findViewById(R.id.image);
                            ImageDownloader imageDownloader = new ImageDownloader(getContext());
                            imageDownloader.download("http://139.59.63.181" + pp.getFile_url(), img);
                        }
                    }
                    call_my_gallery();
                }


                if (fileList.size() > 0 && fileList.size() >= POJO.size()) {
                    gvImages.setAdapter(new PhotoAdapter(getContext(), fileList));
                } else if (POJO.size() > 0) {....continue*/

                if (POJO.size() > 0) {
                    adapterImages = new fragment_upload_images_list_adapter(getContext(), POJO);
                    gvImages.setAdapter(adapterImages);
                } else {
                    gvImages.setAdapter(null);
                    Toast.makeText(getContext(), "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                }
            } else {

                mRealm = Realm.getDefaultInstance();
                final RealmResults<POJO_Upload_Files_For_Selected_User> result_query2 = mRealm.where(POJO_Upload_Files_For_Selected_User.class).findAll().sort("modified", Sort.ASCENDING);
                List<POJO_Upload_Files_For_Selected_User> POJO2 = result_query2;

                if (POJO2.size() > 0) {
                    adapterImages2 = new fragment_upload_images_list_adapter_for_selected_user1(getContext(), POJO2);
                    gvImages.setAdapter(adapterImages2);
                } else {
                    gvImages.setAdapter(null);
                    Toast.makeText(getContext(), "NO DATA FOUND", Toast.LENGTH_SHORT).show();
                }
            }
           /* for (POJO_Upload_Files pp : POJO) {
                ss = pp.getFile_url().toString();
            }*/
            //"http://139.59.63.181"

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void offline_gallery() {
        try {
            if (fileList.size() > 0) {
                gvImages.setAdapter(new PhotoAdapter(getContext(), fileList));
            } else {
                gvImages.setAdapter(null);
                Toast.makeText(getContext(), "NO DATA FOUND IN OFFLINE MEDIA", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean lvBusy = false;

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                lvBusy = false;
                adapterImages.notifyDataSetChanged();
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                lvBusy = true;
                break;
            case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                lvBusy = true;
                break;
        }
    }

    public boolean isLvBusy() {
        return lvBusy;
    }

    android.os.Handler handle = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pDialog.incrementProgressBy(1);
        }
    };

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


    /*

   root = new File(Environment.getExternalStorageDirectory()
               .getAbsolutePath());
       getfile(root);

       for (int i = 0; i < fileList.size(); i++) {
           TextView textView = new TextView(this);
           textView.setText(fileList.get(i).getName());
           textView.setPadding(5, 5, 5, 5);

           System.out.println(fileList.get(i).getName());

           if (fileList.get(i).isDirectory()) {
               textView.setTextColor(Color.parseColor("#FF0000"));
           }
           view.addView(textView);
       }

    */

    /*public class PhotoAdapter extends BaseAdapter {
        private Context ctx;
        //private final String[] filesNames;
        private final ArrayList<File> filesPaths;

        public PhotoAdapter(Context ctx, String[] filesNames, ArrayList<File> filesPaths) {
            this.ctx = ctx;
            //this.filesNames = filesNames;
            this.filesPaths = filesPaths;
        }

        @Override
        public int getCount() {
            return 0;//filesNames.length;
        }

        @Override
        public Object getItem(int pos) {
            return pos;
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int p, View convertView, ViewGroup parent) {
            View grid;
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                grid = inflater.inflate(R.layout.grid_item, null);

                TextView textView = (TextView) grid.findViewById(R.id.title);
                ImageView imageView = (ImageView) grid.findViewById(R.id.image);
                //textView.setText(filesNames[p]);

                Bitmap bmp = BitmapFactory.decodeFile(filesPaths.get(p).toString());
                imageView.setImageBitmap(bmp);
            } else {
                grid = (View) convertView;
            }
            return grid;
        }
    }*/


    public Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    //////// this method share your image
    private void shareBitmap(Bitmap bitmap, String fileName) {
        try {
            File file = new File(getContext().getCacheDir(), fileName + ".png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


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

            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String code_emp = app_preferences.getString("code_emp", "000");
            String emp_code = app_preferences.getString("employee_code", "111");
            emp_code = emp_code.replace('/', '_');

            if (code_emp.equals(emp_code)) {
                File f = new File("" + url);
                saveImage(bitmap, f.getName());
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //return bitmap;
    }

    // Makes HttpURLConnection and returns InputStream
    private InputStream getHttpConnection(String urlString)
            throws IOException {

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
            ex.printStackTrace();
        }
        return stream;
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
            Toast.makeText(getContext(), "Download Successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}