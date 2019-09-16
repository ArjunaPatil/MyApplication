package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;
import static com.example.vin.myapplication.R.layout.adapter_patch_list_for_dialog;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_chemist_call_new#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_chemist_call_new extends Fragment
        implements DatePickerFragment.DateDialogListener,
        user_list_hierarchy_me_FragmentDialog.EditUserListHirarchyDialogListener,
        test_attch_chemist_FragmentDialog.EditTestChemistDialogListener, LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int year, month, day;

    private Realm mRealm;
    RestService restService;
    Context context;
    LinearLayout select_date;
    TextView select_date1;
    TextView select_date2;
    ImageButton select_date3;

    LinearLayout chemist_of_TBM;
    LinearLayout chemist_of_TBM_1;
    TextView chemist_of_TBM_2;
    ImageButton chemist_of_TBM_3;
    TextView name_chemist_of_TBM;


    LinearLayout select_chemist;
    LinearLayout select_chemist1;
    TextView select_chemist2;
    ImageButton select_chemist3;
    TextView name_select_chemist;

    Integer jfw = 0;
    LinearLayout jfw_of_all;
    LinearLayout jfw_of_all_1;
    TextView jfw_of_all_2;
    ImageButton jfw_of_all_3;

    LinearLayout jfw_of_all2;
    LinearLayout jfw_of_all_12;
    TextView jfw_of_all_22;
    ImageButton jfw_of_all_32;

    ImageButton btn_add_chemist;
    TextView txt_chemist_of_TBM;

    EditText edit_jwf_with;

    LinearLayout ll_1;
    LinearLayout ll_2;
    LinearLayout ll_3;
    LinearLayout ll_4;
    LinearLayout ll_5;
    LinearLayout ll_6;
    LinearLayout ll_7;
    LinearLayout ll_8;
    LinearLayout ll_9;
    LinearLayout ll_10;
    LinearLayout ll_11;
    LinearLayout ll_12;
    LinearLayout ll_13;
    LinearLayout ll_14;
    LinearLayout ll_15;
    LinearLayout ll_16;
    LinearLayout ll_17;
    LinearLayout ll_18;

    LinearLayout ll_19;
    LinearLayout ll_20;
    LinearLayout ll_21;
    LinearLayout ll_22;
    LinearLayout ll_23;
    LinearLayout ll_24;
    LinearLayout ll_25;
    LinearLayout ll_26;
    LinearLayout ll_27;
    LinearLayout ll_28;

    LinearLayout ll_29;
    LinearLayout ll_30;


    EditText q_1;
    EditText q_2;
    EditText q_3;
    EditText q_4;
    EditText q_5;
    EditText q_6;
    EditText q_7;
    EditText q_8;
    EditText q_9;
    EditText q_10;
    EditText q_11;
    EditText q_12;
    EditText q_13;
    EditText q_14;
    EditText q_15;
    EditText q_16;
    EditText q_17;
    EditText q_18;

    EditText q_19;
    EditText q_20;
    EditText q_21;
    EditText q_22;
    EditText q_23;
    EditText q_24;
    EditText q_25;
    EditText q_26;
    EditText q_27;
    EditText q_28;

    EditText q_29;
    EditText q_30;

    EditText f_1;
    EditText f_2;
    EditText f_3;
    EditText f_4;
    EditText f_5;
    EditText f_6;
    EditText f_7;
    EditText f_8;
    EditText f_9;
    EditText f_10;
    EditText f_11;
    EditText f_12;
    EditText f_13;
    EditText f_14;
    EditText f_15;
    EditText f_16;
    EditText f_17;
    EditText f_18;

    EditText f_19;
    EditText f_20;
    EditText f_21;
    EditText f_22;
    EditText f_23;
    EditText f_24;
    EditText f_25;
    EditText f_26;
    EditText f_27;
    EditText f_28;

    EditText f_29;
    EditText f_30;

    EditText t_1;
    EditText t_2;
    EditText t_3;
    EditText t_4;
    EditText t_5;
    EditText t_6;
    EditText t_7;
    EditText t_8;
    EditText t_9;
    EditText t_10;
    EditText t_11;
    EditText t_12;
    EditText t_13;
    EditText t_14;
    EditText t_15;
    EditText t_16;
    EditText t_17;
    EditText t_18;

    EditText t_19;
    EditText t_20;
    EditText t_21;
    EditText t_22;
    EditText t_23;
    EditText t_24;
    EditText t_25;
    EditText t_26;
    EditText t_27;
    EditText t_28;

    EditText t_29;
    EditText t_30;


    EditText ta_1;
    EditText ta_2;
    EditText ta_3;
    EditText ta_4;
    EditText ta_5;
    EditText ta_6;
    EditText ta_7;
    EditText ta_8;
    EditText ta_9;
    EditText ta_10;
    EditText ta_11;
    EditText ta_12;
    EditText ta_13;
    EditText ta_14;
    EditText ta_15;
    EditText ta_16;
    EditText ta_17;
    EditText ta_18;

    EditText ta_19;
    EditText ta_20;
    EditText ta_21;
    EditText ta_22;
    EditText ta_23;
    EditText ta_24;
    EditText ta_25;
    EditText ta_26;
    EditText ta_27;
    EditText ta_28;

    EditText ta_29;
    EditText ta_30;


    EditText edit_comment;
    TextView txt_hq_name;

    Button btn_submit;

    TextView txt_id;
    TextView txt_dr_call_by_user_id;

    View view_select_tbm_of_doctor;

    private ProgressDialog pDialog;

    private long mLastClickTime = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    double lat = 0.0, lon = 0.0;
    int gps_enable_flag = 0;
    LocationManager locationManager;
    int cnt_location = 0;

    private OnFragmentInteractionListener mListener;

    public fragment_chemist_call_new() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_DashBoard.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_chemist_call_new newInstance(String param1, String param2) {
        fragment_chemist_call_new fragment = new fragment_chemist_call_new();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chemist_call_new, container, false);
        //employee = (TextView) view.getView().findViewById(employee);
        //return inflater.inflate(R.layout.fragment_my_profile2, container, false);
        return view;

        //  return inflater.inflate(R.layout.fragment_fragment__dash_board, container, false);
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

    @Override
    public void onFinishDialog(Date date) {
        try {
            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String hireDate = sdf.format(date);

            select_date2.setText(hireDate);


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

   /* @Override
    public void onFinishEditDialog(String id, String fullname) {


        chemist_of_TBM_2.setText(fullname.toString());


        name_chemist_of_TBM.setText(id.toString());
    }*/

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

    @Override
    public void onStart() {
        try {
            super.onStart();

            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            ((DashBord_main) getActivity()).setActionBarTitle("CHEMIST NEW CALL");
            loaddate();

            init_controls();

            GPSTracker gps = new GPSTracker(getContext(), fragment_chemist_call_new.this, false);
            if (gps.canGetLocation()) {

                lat = gps.getLatitude();
                lon = gps.getLongitude();

            } else {
                gps.showSettingsAlert();
            }

            pDialog = new ProgressDialog(getContext());


            btn_submit.setVisibility(View.VISIBLE);
            select_date1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_date();
                }
            });
            select_date2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_date();
                }
            });
            select_date3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_date();
                }
            });


            chemist_of_TBM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_chemist2.setText(" Select Chemist");
                    select_chemist2.setText(" Select");
                    Clear_POJO_CHEMIST();
                    show_dialog_for_doctor_of_TBM();

                }
            });
            chemist_of_TBM_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_chemist2.setText(" Select Chemist");
                    select_chemist2.setText(" Select");
                    Clear_POJO_CHEMIST();
                    show_dialog_for_doctor_of_TBM();

                }
            });
            chemist_of_TBM_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_chemist2.setText(" Select Chemist");
                    select_chemist2.setText(" Select");
                    Clear_POJO_CHEMIST();
                    show_dialog_for_doctor_of_TBM();
                }
            });
            chemist_of_TBM_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_chemist2.setText(" Select Chemist");
                    select_chemist2.setText(" Select");
                    Clear_POJO_CHEMIST();
                    show_dialog_for_doctor_of_TBM();
                }
            });


            select_chemist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_chemist2.setText(" Select Chemist");
                    select_chemist2.setText(" Select");
                    Clear_POJO_CHEMIST();
                    //if (!chemist_of_TBM_2.getText().toString().equals(" Select Chemist of TBM") && !chemist_of_TBM_2.getText().toString().equals("")) {
                    if (!chemist_of_TBM_2.getText().toString().equals(" Select") && !chemist_of_TBM_2.getText().toString().equals("")) {
                        show_dialog_for_select_chemist(name_chemist_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                    }

                }
            });
            select_chemist1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_chemist2.setText(" Select Chemist");
                    select_chemist2.setText(" Select");
                    Clear_POJO_CHEMIST();
                    //if (!chemist_of_TBM_2.getText().toString().equals(" Select Chemist of TBM") && !chemist_of_TBM_2.getText().toString().equals("")) {
                    if (!chemist_of_TBM_2.getText().toString().equals(" Select") && !chemist_of_TBM_2.getText().toString().equals("")) {
                        show_dialog_for_select_chemist(name_chemist_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                    }
                }
            });
            select_chemist2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_chemist2.setText(" Select Chemist");
                    select_chemist2.setText(" Select");
                    Clear_POJO_CHEMIST();
                    //if (!chemist_of_TBM_2.getText().toString().equals(" Select Chemist of TBM") && !chemist_of_TBM_2.getText().toString().equals("")) {
                    if (!chemist_of_TBM_2.getText().toString().equals(" Select") && !chemist_of_TBM_2.getText().toString().equals("")) {
                        show_dialog_for_select_chemist(name_chemist_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                    }
                }
            });
            select_chemist3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_chemist2.setText(" Select Chemist");
                    select_chemist2.setText(" Select");
                    Clear_POJO_CHEMIST();
                    //if (!chemist_of_TBM_2.getText().toString().equals(" Select Chemist of TBM") && !chemist_of_TBM_2.getText().toString().equals("")) {
                    if (!chemist_of_TBM_2.getText().toString().equals(" Select") && !chemist_of_TBM_2.getText().toString().equals("")) {
                        show_dialog_for_select_chemist(name_chemist_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                    }
                }
            });

            //////////////////////////////
            jfw_of_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jfw = 1;
                    show_dialog_for_jwf_top_down_emp();
                }
            });
            jfw_of_all_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jfw = 1;
                    show_dialog_for_jwf_top_down_emp();
                }
            });
            jfw_of_all_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jfw = 1;
                    show_dialog_for_jwf_top_down_emp();
                }
            });
            jfw_of_all_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jfw = 1;
                    show_dialog_for_jwf_top_down_emp();
                }
            });

            ///////////////////////////
            //////////////////////////////
            jfw_of_all2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jfw = 2;
                    show_dialog_for_jwf_top_down_emp();
                }
            });
            jfw_of_all_12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jfw = 2;
                    show_dialog_for_jwf_top_down_emp();
                }
            });
            jfw_of_all_22.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jfw = 2;
                    show_dialog_for_jwf_top_down_emp();
                }
            });
            jfw_of_all_32.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jfw = 2;
                    show_dialog_for_jwf_top_down_emp();
                }
            });


            btn_submit.setText("ADD");
            final Bundle bundle = this.getArguments();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String designation = app_preferences.getString("designation", "default");
            String branch = (app_preferences.getString("branch", ""));
            String product_for_branch = (app_preferences.getString("branch_product", ""));
            if (designation.equals("TBM") == true) {
                if (!product_for_branch.equals("") || !product_for_branch.equals(null)) {
                    set_hide_show_brand_matrix(product_for_branch);
                } else {
                    if (branch.equals("") || branch.equals(null)) {
                        Toast.makeText(getContext(), "Your Branch Is Empty; Inform To Office For Fill Up... ", Toast.LENGTH_SHORT).show();
                    } else {
                        get_product_list_of_branch(branch);
                    }

                }
            }
            if (bundle != null) {

                String name = bundle.getString("user");
                if (designation.equals("TBM") == false) {
                    get_branch_of_user(name);
                }
                /*final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String designation = app_preferences.getString("designation", "");
                if (designation.equals("TBM") == true) {

                } else {

                }*/

                edit_fill();
            } else {
                CALL_CHECK_TBM_OR_NOT("");
                if (Get_today_date_and_check_app_version("") == false) {

                } else {

                    setALLEditText_to_0();
                    //final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());


                    txt_dr_call_by_user_id.setText(app_preferences.getString("name", "default"));
                    //String designation = app_preferences.getString("designation", "default");

                    if (designation.equals("TBM") == true) {
                        name_chemist_of_TBM.setText(app_preferences.getString("name", "default"));
                        chemist_of_TBM_2.setText(app_preferences.getString("full_name", "default"));
                    }
                }
            }

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if ((select_chemist2.getText().toString().contains("SELECT") == true)
                            || (select_chemist2.getText().toString().contains("select") == true)
                            || (select_chemist2.getText().toString().contains("Select") == true)
                            || (select_chemist2.getText().toString().contains("NONE") == true)) {

                        Toast.makeText(getContext(), "PLEASE SELECT CHEMIST FIRST", Toast.LENGTH_SHORT).show();

                    } else {
                        GPSTracker gps = new GPSTracker(getContext(), fragment_chemist_call_new.this, false);
                        if (gps.canGetLocation()) {

                            lat = gps.getLatitude();
                            lon = gps.getLongitude();
                            lock_check();

                        } else {
                            gps.showSettingsAlert();
                        }

                    }
                }
            });

            btn_add_chemist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* popup_doctor_master_new_DialogFragment dialog = popup_doctor_master_new_DialogFragment.newInstance("Hello world");

                    //dialog.setView(layout);

                    dialog.setTargetFragment(fragment_doctor_call_new.this, 300);
                    dialog.show(getFragmentManager(), "fdf");*/
                }
            });


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void show_dialog_for_chemist_of_TBM() {


        MyDialogFragment dialog = MyDialogFragment.newInstance("Hello world");

        //dialog.setView(layout);

        dialog.setTargetFragment(fragment_chemist_call_new.this, 300);
        dialog.show(getFragmentManager(), "fdf");


    }

    private void loaddate() {
        TextView txt_select_date = (TextView) getView().findViewById(R.id.select_date2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        txt_select_date.setText(sdf.format(date));
        txt_select_date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //  showDialog(999);
                /*Toast.makeText(getContext(), "ca",
                        Toast.LENGTH_SHORT)
                        .show();*/

                return false;
            }
        });

    }

    public void insert_chemist_calls(POJO_chemist_Calls POJO) {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().Chemist_Call_insert(sid, POJO, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {

                    pDialog.hide();
                    /*task_Patch_Master = new Async_Class_Load_Patch_Master_in_Realm(getActivity(), false);
                    task_Patch_Master.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/

                    Toast.makeText(getContext(), "CHEMIST CALL ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    Fragment frag = new fragment_chemist_call();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    //frag.setArguments(bundle);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.hide();

                    String msg = "";
                    if (error.getKind().toString().contains("HTTP")) {
                        msg = error.toString();
                    } else {
                        msg = "139.59.63.181";
                    }
                    if (msg.equals("403 FORBIDDEN")) {
                        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    } else if (msg.contains("139.59.63.181")) {
                        Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void update_chemist_calls(POJO_chemist_Calls POJO) {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String name = txt_id.getText().toString();

            restService.getService().Chemist_Call_update(sid, POJO, name, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    pDialog.hide();
                    Toast.makeText(getContext(), "CHEMIST CALL UPDATE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    Fragment frag = new fragment_chemist_call();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    //frag.setArguments(bundle);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }

                @Override
                public void failure(RetrofitError error) {
                    pDialog.hide();

                    String msg = "";
                    if (error.getKind().toString().contains("HTTP")) {
                        msg = error.toString();
                    } else {
                        msg = "139.59.63.181";
                    }
                    if (msg.equals("403 FORBIDDEN")) {
                        Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show();
                    } else if (msg.contains("139.59.63.181")) {
                        Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean Validation(POJO_chemist_Calls POJO) {
        try {
            if (POJO.getChemist_name().toString().contains("Select") == true) {
                Toast.makeText(getContext(), "PLEASE SELECT CHEMIST", Toast.LENGTH_SHORT).show();
                return false;
            } /*else if (POJO.getPatch_type().toString().trim().length() < 1) {
                Toast.makeText(getContext(), "SELECT ONE PATCH TYPE ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getKm() == null) {
                Toast.makeText(getContext(), "PLEASE ENTER PATCH KM ", Toast.LENGTH_SHORT).show();
                return false;

            } else if (POJO.getEmployee_code().toString().trim().length() < 1) {
                Toast.makeText(getContext(), "NO EMPLOYEE WITH THIS PATCH ..YOU ARE NOT ASSIGNED EMPLOYEE CODE YET" +
                        "PLEASE CONATACT LYSTEN GLOBAL IT SUPPORT ", Toast.LENGTH_SHORT).show();
                return false;
            } else*/
            return true;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void setALLEditText_to_0() {
        try {
            q_1.setText("0");
            q_2.setText("0");
            q_3.setText("0");
            q_4.setText("0");
            q_5.setText("0");
            q_6.setText("0");
            q_7.setText("0");
            q_8.setText("0");
            q_9.setText("0");
            q_10.setText("0");
            q_11.setText("0");
            q_12.setText("0");
            q_13.setText("0");
            q_14.setText("0");
            q_15.setText("0");
            q_16.setText("0");
            q_17.setText("0");
            q_18.setText("0");

            q_19.setText("0");
            q_20.setText("0");
            q_21.setText("0");
            q_22.setText("0");
            q_23.setText("0");
            q_24.setText("0");
            q_25.setText("0");
            q_26.setText("0");
            q_27.setText("0");
            q_28.setText("0");

            q_29.setText("0");
            q_30.setText("0");


            f_1.setText("0");
            f_2.setText("0");
            f_3.setText("0");
            f_4.setText("0");
            f_5.setText("0");
            f_6.setText("0");
            f_7.setText("0");
            f_8.setText("0");
            f_9.setText("0");
            f_10.setText("0");
            f_11.setText("0");
            f_12.setText("0");
            f_13.setText("0");
            f_14.setText("0");
            f_15.setText("0");
            f_16.setText("0");
            f_17.setText("0");
            f_18.setText("0");

            f_19.setText("0");
            f_20.setText("0");
            f_21.setText("0");
            f_22.setText("0");
            f_23.setText("0");
            f_24.setText("0");
            f_25.setText("0");
            f_26.setText("0");
            f_27.setText("0");
            f_28.setText("0");

            f_29.setText("0");
            f_30.setText("0");


            t_1.setText("0");
            t_2.setText("0");
            t_3.setText("0");
            t_4.setText("0");
            t_5.setText("0");
            t_6.setText("0");
            t_7.setText("0");
            t_8.setText("0");
            t_9.setText("0");
            t_10.setText("0");
            t_11.setText("0");
            t_12.setText("0");
            t_13.setText("0");
            t_14.setText("0");
            t_15.setText("0");
            t_16.setText("0");
            t_17.setText("0");
            t_18.setText("0");

            t_19.setText("0");
            t_20.setText("0");
            t_21.setText("0");
            t_22.setText("0");
            t_23.setText("0");
            t_24.setText("0");
            t_25.setText("0");
            t_26.setText("0");
            t_27.setText("0");
            t_28.setText("0");

            t_29.setText("0");
            t_30.setText("0");

            ta_1.setText("0");
            ta_2.setText("0");
            ta_3.setText("0");
            ta_4.setText("0");
            ta_5.setText("0");
            ta_6.setText("0");
            ta_7.setText("0");
            ta_8.setText("0");
            ta_9.setText("0");
            ta_10.setText("0");
            ta_11.setText("0");
            ta_12.setText("0");
            ta_13.setText("0");
            ta_14.setText("0");
            ta_15.setText("0");
            ta_16.setText("0");
            ta_17.setText("0");
            ta_18.setText("0");

            ta_19.setText("0");
            ta_20.setText("0");
            ta_21.setText("0");
            ta_22.setText("0");
            ta_23.setText("0");
            ta_24.setText("0");
            ta_25.setText("0");
            ta_26.setText("0");
            ta_27.setText("0");
            ta_28.setText("0");

            ta_29.setText("0");
            ta_30.setText("0");
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void edit_fill() {
        try {

            Bundle bundle = this.getArguments();

            String cc = bundle.getString("name");
            txt_id.setText(bundle.getString("name"));

            pDialog.show();
            //Thread.sleep(sleep_wait);
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("date");

            jsonArray.put("user_id");
            jsonArray.put("call_by_user_id");
            jsonArray.put("user_name");
            jsonArray.put("hq_name");
            jsonArray.put("chemist_id");
            jsonArray.put("chemist_name");

            jsonArray.put("jwf_with");
            jsonArray.put("jwf_with2");

            jsonArray.put("qty_1");
            jsonArray.put("qty_2");
            jsonArray.put("qty_3");
            jsonArray.put("qty_4");
            jsonArray.put("qty_5");
            jsonArray.put("qty_6");
            jsonArray.put("qty_7");
            jsonArray.put("qty_8");
            jsonArray.put("qty_9");
            jsonArray.put("qty_10");
            jsonArray.put("qty_11");
            jsonArray.put("qty_12");
            jsonArray.put("qty_13");
            jsonArray.put("qty_14");
            jsonArray.put("qty_15");
            jsonArray.put("qty_16");
            jsonArray.put("qty_17");
            jsonArray.put("qty_18");

            jsonArray.put("qty_19");
            jsonArray.put("qty_20");
            jsonArray.put("qty_21");
            jsonArray.put("qty_22");
            jsonArray.put("qty_23");
            jsonArray.put("qty_24");
            jsonArray.put("qty_25");
            jsonArray.put("qty_26");
            jsonArray.put("qty_27");
            jsonArray.put("qty_28");

            jsonArray.put("fqty_1");
            jsonArray.put("fqty_2");
            jsonArray.put("fqty_3");
            jsonArray.put("fqty_4");
            jsonArray.put("fqty_5");
            jsonArray.put("fqty_6");
            jsonArray.put("fqty_7");
            jsonArray.put("fqty_8");
            jsonArray.put("fqty_9");
            jsonArray.put("fqty_10");
            jsonArray.put("fqty_11");
            jsonArray.put("fqty_12");
            jsonArray.put("fqty_13");
            jsonArray.put("fqty_14");
            jsonArray.put("fqty_15");
            jsonArray.put("fqty_16");
            jsonArray.put("fqty_17");
            jsonArray.put("fqty_18");

            jsonArray.put("fqty_19");
            jsonArray.put("fqty_20");
            jsonArray.put("fqty_21");
            jsonArray.put("fqty_22");
            jsonArray.put("fqty_23");
            jsonArray.put("fqty_24");
            jsonArray.put("fqty_25");
            jsonArray.put("fqty_26");
            jsonArray.put("fqty_27");
            jsonArray.put("fqty_28");

            jsonArray.put("tqty_1");
            jsonArray.put("tqty_2");
            jsonArray.put("tqty_3");
            jsonArray.put("tqty_4");
            jsonArray.put("tqty_5");
            jsonArray.put("tqty_6");
            jsonArray.put("tqty_7");
            jsonArray.put("tqty_8");
            jsonArray.put("tqty_9");
            jsonArray.put("tqty_10");
            jsonArray.put("tqty_11");
            jsonArray.put("tqty_12");
            jsonArray.put("tqty_13");
            jsonArray.put("tqty_14");
            jsonArray.put("tqty_15");
            jsonArray.put("tqty_16");
            jsonArray.put("tqty_17");
            jsonArray.put("tqty_18");

            jsonArray.put("tqty_19");
            jsonArray.put("tqty_20");
            jsonArray.put("tqty_21");
            jsonArray.put("tqty_22");
            jsonArray.put("tqty_23");
            jsonArray.put("tqty_24");
            jsonArray.put("tqty_25");
            jsonArray.put("tqty_26");
            jsonArray.put("tqty_27");
            jsonArray.put("tqty_28");

            jsonArray.put("tamt_1");
            jsonArray.put("tamt_2");
            jsonArray.put("tamt_3");
            jsonArray.put("tamt_4");
            jsonArray.put("tamt_5");
            jsonArray.put("tamt_6");
            jsonArray.put("tamt_7");
            jsonArray.put("tamt_8");
            jsonArray.put("tamt_9");
            jsonArray.put("tamt_10");
            jsonArray.put("tamt_11");
            jsonArray.put("tamt_12");
            jsonArray.put("tamt_13");
            jsonArray.put("tamt_14");
            jsonArray.put("tamt_15");
            jsonArray.put("tamt_16");
            jsonArray.put("tamt_17");
            jsonArray.put("tamt_18");
            jsonArray.put("tamt_19");
            jsonArray.put("tamt_20");
            jsonArray.put("tamt_21");
            jsonArray.put("tamt_22");
            jsonArray.put("tamt_23");
            jsonArray.put("tamt_24");
            jsonArray.put("tamt_25");
            jsonArray.put("tamt_26");
            jsonArray.put("tamt_27");
            jsonArray.put("tamt_28");


            jsonArray.put("comment");

            //  JSONObject ofilter= new JSONObject();
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            //   JSONArray Filter2 = new JSONArray();
            JSONArray Filter3 = new JSONArray();

            Filter1.put("Chemist Call");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(txt_id.getText());

            //  ofilter.
            Filters.put(Filter1);
            //  Filters.put(Filter2);
            // Log.i("Success ","out:"+limitstart);

            restService.getService().getChemist_Call(sid, "modified desc", 0, 1, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    pDialog.hide();
                    // ListView lv = (ListView) findViewById(R.id.listView);
                    JsonObject j1 = jsonElement.getAsJsonObject();
                    JsonArray j2 = j1.getAsJsonArray("data");


                    //    List<LiveWork> LiveWorks=new ArrayList<LiveWork>();

                    // JSONArray jsonArray = new JSONArray(jsonArrayString);
                    //  List<String> list = new ArrayList<String>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<POJO_chemist_Calls>>() {
                    }.getType();
                    List<POJO_chemist_Calls> POJO = gson.fromJson(j2, type);


                    mRealm = Realm.getDefaultInstance();

                    mRealm.beginTransaction();

                    mRealm.copyToRealmOrUpdate(POJO);
                    mRealm.commitTransaction();


                    mRealm.close();
                    if (txt_id.getText().toString().length() > 0) {
                        Bind__data(txt_id.getText().toString());
                        CALL_CHECK_TBM_OR_NOT(txt_dr_call_by_user_id.getText().toString());

                        if (Get_today_date_and_check_app_version(select_date2.getText().toString().trim()) == false) {
                            btn_submit.setVisibility(View.GONE);
                        }
                    }


                }


                @Override
                public void failure(RetrofitError error) {


                    pDialog.hide();

                    String msg = "";
                    if (error.getKind().toString().contains("HTTP")) {
                        msg = error.toString();
                    } else {
                        msg = "139.59.63.181";
                    }

                    if (msg.equals("403 FORBIDDEN")) {


                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.putString("status", "0");
                        editor.commit();

                        Intent k = new Intent(getContext(), Login.class); //got ot login activity
                        getContext().startActivity(k);
                    } else if (msg.contains("139.59.63.181")) {
                        Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();

                    }


                    if (task_app_evrsion != null) {
                        task_app_evrsion.cancel(true);
                    }


                }
            });

        } catch (Exception ex)

        {
            pDialog.hide();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void Bind__data(String name) {
        try {
            Realm mRealm = Realm.getDefaultInstance();

            //final RealmResults<POJO_Employee> puppies = mRealm.where(POJO_Employee.class).equalTo("select_date",fromDateEtxt.getText().toString()).findAll();
            final POJO_chemist_Calls result_query = mRealm.where(POJO_chemist_Calls.class).equalTo("name", name).findFirst();

            //POJO_Employee POJO = result_query;

            txt_id.setText(result_query.getName());

            select_date2.setText(result_query.getDate());
            chemist_of_TBM_2.setText(result_query.getUser_name());
            select_chemist2.setText(result_query.getChemist_name());
            txt_dr_call_by_user_id.setText(result_query.getCall_by_user_id());

            name_chemist_of_TBM.setText(result_query.getUser_id());
            name_select_chemist.setText(result_query.getChemist_id());


            txt_hq_name.setText(result_query.getHq_name());
            //edit_jwf_with.setText(result_query.getJwf_with());
            jfw_of_all_2.setText(result_query.getJwf_with());
            //jfw_of_all_22.setText(result_query.getJwf_with2() == null ? " Select JFW WITH" : result_query.getJwf_with2());
            jfw_of_all_22.setText(result_query.getJwf_with2() == null ? " Select" : result_query.getJwf_with2());

            q_1.setText(result_query.getQty_1());
            q_2.setText(result_query.getQty_2());
            q_3.setText(result_query.getQty_3());
            q_4.setText(result_query.getQty_4());
            q_5.setText(result_query.getQty_5());
            q_6.setText(result_query.getQty_6());
            q_7.setText(result_query.getQty_7());
            q_8.setText(result_query.getQty_8());
            q_9.setText(result_query.getQty_9());
            q_10.setText(result_query.getQty_10());
            q_11.setText(result_query.getQty_11());
            q_12.setText(result_query.getQty_12());
            q_13.setText(result_query.getQty_13());
            q_14.setText(result_query.getQty_14());
            q_15.setText(result_query.getQty_15());
            q_16.setText(result_query.getQty_16());
            q_17.setText(result_query.getQty_17());
            q_18.setText(result_query.getQty_18());

            q_19.setText(result_query.getQty_19());
            q_20.setText(result_query.getQty_20());
            q_21.setText(result_query.getQty_21());
            q_22.setText(result_query.getQty_22());
            q_23.setText(result_query.getQty_23());
            q_24.setText(result_query.getQty_24());
            q_25.setText(result_query.getQty_25());
            q_26.setText(result_query.getQty_26());
            q_27.setText(result_query.getQty_27());
            q_28.setText(result_query.getQty_28());

            q_29.setText(result_query.getQty_29());
            q_30.setText(result_query.getQty_30());


            f_1.setText(result_query.getFqty_1());
            f_2.setText(result_query.getFqty_2());
            f_3.setText(result_query.getFqty_3());
            f_4.setText(result_query.getFqty_4());
            f_5.setText(result_query.getFqty_5());
            f_6.setText(result_query.getFqty_6());
            f_7.setText(result_query.getFqty_7());
            f_8.setText(result_query.getFqty_8());
            f_9.setText(result_query.getFqty_9());
            f_10.setText(result_query.getFqty_10());
            f_11.setText(result_query.getFqty_11());
            f_12.setText(result_query.getFqty_12());
            f_13.setText(result_query.getFqty_13());
            f_14.setText(result_query.getFqty_14());
            f_15.setText(result_query.getFqty_15());
            f_16.setText(result_query.getFqty_16());
            f_17.setText(result_query.getFqty_17());
            f_18.setText(result_query.getFqty_18());

            f_19.setText(result_query.getFqty_19());
            f_20.setText(result_query.getFqty_20());
            f_21.setText(result_query.getFqty_21());
            f_22.setText(result_query.getFqty_22());
            f_23.setText(result_query.getFqty_23());
            f_24.setText(result_query.getFqty_24());
            f_25.setText(result_query.getFqty_25());
            f_26.setText(result_query.getFqty_26());
            f_27.setText(result_query.getFqty_27());
            f_28.setText(result_query.getFqty_28());

            f_29.setText(result_query.getFqty_29());
            f_30.setText(result_query.getFqty_30());


            t_1.setText(result_query.getTqty_1());
            t_2.setText(result_query.getTqty_2());
            t_3.setText(result_query.getTqty_3());
            t_4.setText(result_query.getTqty_4());
            t_5.setText(result_query.getTqty_5());
            t_6.setText(result_query.getTqty_6());
            t_7.setText(result_query.getTqty_7());
            t_8.setText(result_query.getTqty_8());
            t_9.setText(result_query.getTqty_9());
            t_10.setText(result_query.getTqty_10());
            t_11.setText(result_query.getTqty_11());
            t_12.setText(result_query.getTqty_12());
            t_13.setText(result_query.getTqty_13());
            t_14.setText(result_query.getTqty_14());
            t_15.setText(result_query.getTqty_15());
            t_16.setText(result_query.getTqty_16());
            t_17.setText(result_query.getTqty_17());
            t_18.setText(result_query.getTqty_18());

            t_19.setText(result_query.getTqty_19());
            t_20.setText(result_query.getTqty_20());
            t_21.setText(result_query.getTqty_21());
            t_22.setText(result_query.getTqty_22());
            t_23.setText(result_query.getTqty_23());
            t_24.setText(result_query.getTqty_24());
            t_25.setText(result_query.getTqty_25());
            t_26.setText(result_query.getTqty_26());
            t_27.setText(result_query.getTqty_27());
            t_28.setText(result_query.getTqty_28());

            t_29.setText(result_query.getTqty_29());
            t_30.setText(result_query.getTqty_30());

            ta_1.setText(result_query.getTamt_1());
            ta_2.setText(result_query.getTamt_2());
            ta_3.setText(result_query.getTamt_3());
            ta_4.setText(result_query.getTamt_4());
            ta_5.setText(result_query.getTamt_5());
            ta_6.setText(result_query.getTamt_6());
            ta_7.setText(result_query.getTamt_7());
            ta_8.setText(result_query.getTamt_8());
            ta_9.setText(result_query.getTamt_9());
            ta_10.setText(result_query.getTamt_10());
            ta_11.setText(result_query.getTamt_11());
            ta_12.setText(result_query.getTamt_12());
            ta_13.setText(result_query.getTamt_13());
            ta_14.setText(result_query.getTamt_14());
            ta_15.setText(result_query.getTamt_15());
            ta_16.setText(result_query.getTamt_16());
            ta_17.setText(result_query.getTamt_17());
            ta_18.setText(result_query.getTamt_18());

            ta_19.setText(result_query.getTamt_19());
            ta_20.setText(result_query.getTamt_20());
            ta_21.setText(result_query.getTamt_21());
            ta_22.setText(result_query.getTamt_22());
            ta_23.setText(result_query.getTamt_23());
            ta_24.setText(result_query.getTamt_24());
            ta_25.setText(result_query.getTamt_25());
            ta_26.setText(result_query.getTamt_26());
            ta_27.setText(result_query.getTamt_27());
            ta_28.setText(result_query.getTamt_28());

            ta_29.setText(result_query.getTamt_29());
            ta_30.setText(result_query.getTamt_30());


            edit_comment.setText(result_query.getComment());
            btn_submit.setText("UPDATE CALL INFO");


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_chemist() {
        final Dialog dialog = new Dialog(getContext());

        //setting custom layout to dialog
        dialog.setContentView(R.layout.dialog_select_patch);
        // retrieve display dimensions
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

// inflate and adjust layout
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_select_patch, null);

        layout.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
        layout.setMinimumHeight((int) (displayRectangle.height() * 0.9f));

        //dialog.setView(layout);
        dialog.setContentView(layout);


        Realm mRealm = Realm.getDefaultInstance();

        final RealmResults<POJO_Chemist> result_query1;
        result_query1 = mRealm.where(POJO_Chemist.class).equalTo("active", 1).findAll().sort("modified", Sort.DESCENDING);
        List<POJO_Chemist> POJO = result_query1;
        for (POJO_Chemist obj : POJO) {
            // Log.i("Contact Details", contact.getName() + "-" + contact.getEmployee() + "-" + contact.getWork_time());
        }
        adapter_chemist_list_for_dialog customAdapter = new adapter_chemist_list_for_dialog(getContext(), adapter_patch_list_for_dialog, POJO);

        ListView list_patch_list = (ListView) dialog.findViewById(R.id.list_patch_list);
        list_patch_list.setAdapter(customAdapter);

        list_patch_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                   @Override
                                                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                       POJO_Chemist clickedObj = (POJO_Chemist) parent.getItemAtPosition(position);
                                                       select_chemist2.setText(clickedObj.getChemist_name());
                                                       //getPatchName_fromPatchName(clickedObj.getName(), null, total_dr_count);
                                                       // bundle.putString("patch1", clickedObj.getName());
                                                       dialog.dismiss();

                                                   }
                                               }


        );
        dialog.show();
    }

    public void init_controls() {
        try {

            ll_1 = (LinearLayout) getView().findViewById(R.id.ll_1);
            ll_2 = (LinearLayout) getView().findViewById(R.id.ll_2);
            ll_3 = (LinearLayout) getView().findViewById(R.id.ll_3);
            ll_4 = (LinearLayout) getView().findViewById(R.id.ll_4);
            ll_5 = (LinearLayout) getView().findViewById(R.id.ll_5);
            ll_6 = (LinearLayout) getView().findViewById(R.id.ll_6);
            ll_7 = (LinearLayout) getView().findViewById(R.id.ll_7);
            ll_8 = (LinearLayout) getView().findViewById(R.id.ll_8);
            ll_9 = (LinearLayout) getView().findViewById(R.id.ll_9);
            ll_10 = (LinearLayout) getView().findViewById(R.id.ll_10);
            ll_11 = (LinearLayout) getView().findViewById(R.id.ll_11);
            ll_12 = (LinearLayout) getView().findViewById(R.id.ll_12);
            ll_13 = (LinearLayout) getView().findViewById(R.id.ll_13);
            ll_14 = (LinearLayout) getView().findViewById(R.id.ll_14);
            ll_15 = (LinearLayout) getView().findViewById(R.id.ll_15);
            ll_16 = (LinearLayout) getView().findViewById(R.id.ll_16);
            ll_17 = (LinearLayout) getView().findViewById(R.id.ll_17);
            ll_18 = (LinearLayout) getView().findViewById(R.id.ll_18);

            ll_19 = (LinearLayout) getView().findViewById(R.id.ll_19);
            ll_20 = (LinearLayout) getView().findViewById(R.id.ll_20);
            ll_21 = (LinearLayout) getView().findViewById(R.id.ll_21);
            ll_22 = (LinearLayout) getView().findViewById(R.id.ll_22);
            ll_23 = (LinearLayout) getView().findViewById(R.id.ll_23);
            ll_24 = (LinearLayout) getView().findViewById(R.id.ll_24);
            ll_25 = (LinearLayout) getView().findViewById(R.id.ll_25);
            ll_26 = (LinearLayout) getView().findViewById(R.id.ll_26);
            ll_27 = (LinearLayout) getView().findViewById(R.id.ll_27);
            ll_28 = (LinearLayout) getView().findViewById(R.id.ll_28);

            ll_29 = (LinearLayout) getView().findViewById(R.id.ll_29);
            ll_30 = (LinearLayout) getView().findViewById(R.id.ll_30);

            chemist_of_TBM = (LinearLayout) getView().findViewById(R.id.chemist_of_TBM);
            chemist_of_TBM_1 = (LinearLayout) getView().findViewById(R.id.chemist_of_TBM_1);
            chemist_of_TBM_3 = (ImageButton) getView().findViewById(R.id.chemist_of_TBM_3);
            chemist_of_TBM_2 = (TextView) getView().findViewById(R.id.chemist_of_TBM_2);

            txt_dr_call_by_user_id = (TextView) getView().findViewById(R.id.txt_dr_call_by_user_id);

            select_chemist = (LinearLayout) getView().findViewById(R.id.select_chemist);
            select_chemist1 = (LinearLayout) getView().findViewById(R.id.select_chemist1);
            select_chemist3 = (ImageButton) getView().findViewById(R.id.select_chemist3);
            select_chemist2 = (TextView) getView().findViewById(R.id.select_chemist2);
            txt_chemist_of_TBM = (TextView) getView().findViewById(R.id.txt_chemist_of_TBM);

            jfw_of_all = (LinearLayout) getView().findViewById(R.id.jfw_of_all);
            jfw_of_all_1 = (LinearLayout) getView().findViewById(R.id.jfw_of_all_1);
            jfw_of_all_2 = (TextView) getView().findViewById(R.id.jfw_of_all_2);
            jfw_of_all_3 = (ImageButton) getView().findViewById(R.id.jfw_of_all_3);

            jfw_of_all2 = (LinearLayout) getView().findViewById(R.id.jfw_of_all2);
            jfw_of_all_12 = (LinearLayout) getView().findViewById(R.id.jfw_of_all_12);
            jfw_of_all_22 = (TextView) getView().findViewById(R.id.jfw_of_all_22);
            jfw_of_all_32 = (ImageButton) getView().findViewById(R.id.jfw_of_all_32);

            view_select_tbm_of_doctor = (View) getView().findViewById(R.id.view_select_tbm_of_doctor);
            q_1 = (EditText) getView().findViewById(R.id.q_1);
            q_2 = (EditText) getView().findViewById(R.id.q_2);
            q_3 = (EditText) getView().findViewById(R.id.q_3);
            q_4 = (EditText) getView().findViewById(R.id.q_4);
            q_5 = (EditText) getView().findViewById(R.id.q_5);
            q_6 = (EditText) getView().findViewById(R.id.q_6);
            q_7 = (EditText) getView().findViewById(R.id.q_7);
            q_8 = (EditText) getView().findViewById(R.id.q_8);
            q_9 = (EditText) getView().findViewById(R.id.q_9);
            q_10 = (EditText) getView().findViewById(R.id.q_10);
            q_11 = (EditText) getView().findViewById(R.id.q_11);
            q_12 = (EditText) getView().findViewById(R.id.q_12);
            q_13 = (EditText) getView().findViewById(R.id.q_13);
            q_14 = (EditText) getView().findViewById(R.id.q_14);
            q_15 = (EditText) getView().findViewById(R.id.q_15);
            q_16 = (EditText) getView().findViewById(R.id.q_16);
            q_17 = (EditText) getView().findViewById(R.id.q_17);
            q_18 = (EditText) getView().findViewById(R.id.q_18);

            q_19 = (EditText) getView().findViewById(R.id.q_19);
            q_20 = (EditText) getView().findViewById(R.id.q_20);
            q_21 = (EditText) getView().findViewById(R.id.q_21);
            q_22 = (EditText) getView().findViewById(R.id.q_22);
            q_23 = (EditText) getView().findViewById(R.id.q_23);
            q_24 = (EditText) getView().findViewById(R.id.q_24);
            q_25 = (EditText) getView().findViewById(R.id.q_25);
            q_26 = (EditText) getView().findViewById(R.id.q_26);
            q_27 = (EditText) getView().findViewById(R.id.q_27);
            q_28 = (EditText) getView().findViewById(R.id.q_28);

            q_29 = (EditText) getView().findViewById(R.id.q_29);
            q_30 = (EditText) getView().findViewById(R.id.q_30);

            f_1 = (EditText) getView().findViewById(R.id.f_1);
            f_2 = (EditText) getView().findViewById(R.id.f_2);
            f_3 = (EditText) getView().findViewById(R.id.f_3);
            f_4 = (EditText) getView().findViewById(R.id.f_4);
            f_5 = (EditText) getView().findViewById(R.id.f_5);
            f_6 = (EditText) getView().findViewById(R.id.f_6);
            f_7 = (EditText) getView().findViewById(R.id.f_7);
            f_8 = (EditText) getView().findViewById(R.id.f_8);
            f_9 = (EditText) getView().findViewById(R.id.f_9);
            f_10 = (EditText) getView().findViewById(R.id.f_10);
            f_11 = (EditText) getView().findViewById(R.id.f_11);
            f_12 = (EditText) getView().findViewById(R.id.f_12);
            f_13 = (EditText) getView().findViewById(R.id.f_13);
            f_14 = (EditText) getView().findViewById(R.id.f_14);
            f_15 = (EditText) getView().findViewById(R.id.f_15);
            f_16 = (EditText) getView().findViewById(R.id.f_16);
            f_17 = (EditText) getView().findViewById(R.id.f_17);
            f_18 = (EditText) getView().findViewById(R.id.f_18);

            f_19 = (EditText) getView().findViewById(R.id.f_19);
            f_20 = (EditText) getView().findViewById(R.id.f_20);
            f_21 = (EditText) getView().findViewById(R.id.f_21);
            f_22 = (EditText) getView().findViewById(R.id.f_22);
            f_23 = (EditText) getView().findViewById(R.id.f_23);
            f_24 = (EditText) getView().findViewById(R.id.f_24);
            f_25 = (EditText) getView().findViewById(R.id.f_25);
            f_26 = (EditText) getView().findViewById(R.id.f_26);
            f_27 = (EditText) getView().findViewById(R.id.f_27);
            f_28 = (EditText) getView().findViewById(R.id.f_28);

            f_29 = (EditText) getView().findViewById(R.id.f_29);
            f_30 = (EditText) getView().findViewById(R.id.f_30);


            t_1 = (EditText) getView().findViewById(R.id.t_1);
            t_2 = (EditText) getView().findViewById(R.id.t_2);
            t_3 = (EditText) getView().findViewById(R.id.t_3);
            t_4 = (EditText) getView().findViewById(R.id.t_4);
            t_5 = (EditText) getView().findViewById(R.id.t_5);
            t_6 = (EditText) getView().findViewById(R.id.t_6);
            t_7 = (EditText) getView().findViewById(R.id.t_7);
            t_8 = (EditText) getView().findViewById(R.id.t_8);
            t_9 = (EditText) getView().findViewById(R.id.t_9);
            t_10 = (EditText) getView().findViewById(R.id.t_10);
            t_11 = (EditText) getView().findViewById(R.id.t_11);
            t_12 = (EditText) getView().findViewById(R.id.t_12);
            t_13 = (EditText) getView().findViewById(R.id.t_13);
            t_14 = (EditText) getView().findViewById(R.id.t_14);
            t_15 = (EditText) getView().findViewById(R.id.t_15);
            t_16 = (EditText) getView().findViewById(R.id.t_16);
            t_17 = (EditText) getView().findViewById(R.id.t_17);
            t_18 = (EditText) getView().findViewById(R.id.t_18);

            t_19 = (EditText) getView().findViewById(R.id.t_19);
            t_20 = (EditText) getView().findViewById(R.id.t_20);
            t_21 = (EditText) getView().findViewById(R.id.t_21);
            t_22 = (EditText) getView().findViewById(R.id.t_22);
            t_23 = (EditText) getView().findViewById(R.id.t_23);
            t_24 = (EditText) getView().findViewById(R.id.t_24);
            t_25 = (EditText) getView().findViewById(R.id.t_25);
            t_26 = (EditText) getView().findViewById(R.id.t_26);
            t_27 = (EditText) getView().findViewById(R.id.t_27);
            t_28 = (EditText) getView().findViewById(R.id.t_28);

            t_29 = (EditText) getView().findViewById(R.id.t_29);
            t_30 = (EditText) getView().findViewById(R.id.t_30);


            ta_1 = (EditText) getView().findViewById(R.id.ta_1);
            ta_2 = (EditText) getView().findViewById(R.id.ta_2);
            ta_3 = (EditText) getView().findViewById(R.id.ta_3);
            ta_4 = (EditText) getView().findViewById(R.id.ta_4);
            ta_5 = (EditText) getView().findViewById(R.id.ta_5);
            ta_6 = (EditText) getView().findViewById(R.id.ta_6);
            ta_7 = (EditText) getView().findViewById(R.id.ta_7);
            ta_8 = (EditText) getView().findViewById(R.id.ta_8);
            ta_9 = (EditText) getView().findViewById(R.id.ta_9);
            ta_10 = (EditText) getView().findViewById(R.id.ta_10);
            ta_11 = (EditText) getView().findViewById(R.id.ta_11);
            ta_12 = (EditText) getView().findViewById(R.id.ta_12);
            ta_13 = (EditText) getView().findViewById(R.id.ta_13);
            ta_14 = (EditText) getView().findViewById(R.id.ta_14);
            ta_15 = (EditText) getView().findViewById(R.id.ta_15);
            ta_16 = (EditText) getView().findViewById(R.id.ta_16);
            ta_17 = (EditText) getView().findViewById(R.id.ta_17);
            ta_18 = (EditText) getView().findViewById(R.id.ta_18);

            ta_19 = (EditText) getView().findViewById(R.id.ta_19);
            ta_20 = (EditText) getView().findViewById(R.id.ta_20);
            ta_21 = (EditText) getView().findViewById(R.id.ta_21);
            ta_22 = (EditText) getView().findViewById(R.id.ta_22);
            ta_23 = (EditText) getView().findViewById(R.id.ta_23);
            ta_24 = (EditText) getView().findViewById(R.id.ta_24);
            ta_25 = (EditText) getView().findViewById(R.id.ta_25);
            ta_26 = (EditText) getView().findViewById(R.id.ta_26);
            ta_27 = (EditText) getView().findViewById(R.id.ta_27);
            ta_28 = (EditText) getView().findViewById(R.id.ta_28);

            ta_29 = (EditText) getView().findViewById(R.id.ta_29);
            ta_30 = (EditText) getView().findViewById(R.id.ta_30);


            edit_comment = (EditText) getView().findViewById(R.id.edit_comment);

            select_date1 = (TextView) getView().findViewById(R.id.select_date1);
            select_date2 = (TextView) getView().findViewById(R.id.select_date2);
            select_date3 = (ImageButton) getView().findViewById(R.id.select_date3);

            name_chemist_of_TBM = (TextView) getView().findViewById(R.id.name_chemist_of_TBM);
            name_select_chemist = (TextView) getView().findViewById(R.id.name_select_chemist);
            btn_add_chemist = (ImageButton) getView().findViewById(R.id.btn_add_chemist);
            //edit_jwf_with = (EditText) getView().findViewById(R.id.edit_jwf_with);
            txt_hq_name = (TextView) getView().findViewById(R.id.txt_hq_name);

            btn_submit = (Button) getView().findViewById(R.id.btn_submit);
            txt_id = (TextView) getView().findViewById(R.id.txt_id);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onFinishUserListHirarchyDialog(String id, String fullname, String type) {
        try {
            if (id.toString() != "NULL") {
                pDialog.hide();
                if (type.toString().trim().equals("EMP")) {
                    if (id.toString().trim().equals("NONE")) {
                        if (jfw == 1) {
                            TextView jfw_of_all_2 = (TextView) getView().findViewById(R.id.jfw_of_all_2);
                            //jfw_of_all_2.setText("Select JFW with");
                            jfw_of_all_2.setText("Select");
                        } else if (jfw == 2) {
                            TextView jfw_of_all_22 = (TextView) getView().findViewById(R.id.jfw_of_all_22);
                            //jfw_of_all_22.setText("Select JFW with");
                            jfw_of_all_22.setText("Select");
                        }
                    } else {
                        if (jfw == 1) {
                            TextView jfw_of_all_2 = (TextView) getView().findViewById(R.id.jfw_of_all_2);
                            jfw_of_all_2.setText(fullname.toString());
                        } else if (jfw == 2) {
                            TextView jfw_of_all_22 = (TextView) getView().findViewById(R.id.jfw_of_all_22);
                            jfw_of_all_22.setText(fullname.toString());
                        }
                    }
                } else if (type.toString().trim().equals("DOC")) {
                    chemist_of_TBM_2.setText(fullname.toString());
                    name_chemist_of_TBM.setText(id.toString());
                    get_branch_of_user(id.toString());
                }
            } else {
                if (type.toString().trim().equals("EMP")) {
                    if (jfw == 1) {
                        TextView jfw_of_all_2 = (TextView) getView().findViewById(R.id.jfw_of_all_2);
                        //jfw_of_all_2.setText("Select JFW with");
                        jfw_of_all_2.setText("Select");
                    } else if (jfw == 2) {
                        TextView jfw_of_all_22 = (TextView) getView().findViewById(R.id.jfw_of_all_22);
                        //jfw_of_all_22.setText("Select JFW with");
                        jfw_of_all_22.setText("Select");
                    }

                } else if (type.toString().trim().equals("DOC")) {

                    //chemist_of_TBM_2.setText("Select Chemist");
                    chemist_of_TBM_2.setText("Select");
                    name_chemist_of_TBM.setText("");

                }
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onFinishTestChemistDialog(String id, String ChemistName) {
        try {
            TextView select_chemist_2 = (TextView) getView().findViewById(R.id.select_chemist2);
            select_chemist_2.setText(ChemistName.toString());

            TextView name_select_chemist = (TextView) getView().findViewById(R.id.name_select_chemist);
            name_select_chemist.setText(id.toString());


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*-----------------------------------*/
    String products_with_comma = "";

    public void get_branch_of_user(String user) {
        try {

            pDialog.show();
            restService = new RestService();
            //final CountDownLatch latch = new CountDownLatch(1);
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            final String branch1 = app_preferences.getString("branch", "-");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("branch");
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();

            Filter1.put("User");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(user);
            Filters.put(Filter1);

            //"modified desc", 0, 1,
            restService.getService().getUser(sid, 0, 1, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, retrofit.client.Response response) {
                    try {
                        pDialog.hide();
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_User>>() {
                        }.getType();
                        List<POJO_User> POJO = gson.fromJson(j2, type);

                        String branch2 = "";
                        if (POJO.size() <= 1) {
                            for (POJO_User pp : POJO) {
                                if (pp.getBranch() != null)
                                    branch2 = pp.getBranch();
                            }
                        }
                        /*if (!branch2.equals("")) {
                            if (branch1.equals(branch2)) {
                                Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                products_with_comma = app_preferences.getString("branch_product", "");
                                set_hide_show_brand_matrix(products_with_comma);
                            } else {
                                get_product_list_of_branch(branch2);
                            }
                        } else {
                            Toast.makeText(getContext(), "NOT ASSIGN BRANCH FOR SELECTED USER", Toast.LENGTH_SHORT).show();
                        }*/
                        if (!branch2.equals("")) {
                            if (branch1.equals(branch2)) {
                                //Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                products_with_comma = app_preferences.getString("branch_product", "");
                                if (products_with_comma == "" || products_with_comma == null) {
                                    get_product_list_of_branch(branch2);
                                } else {
                                    set_hide_show_brand_matrix(products_with_comma);
                                }
                            } else {
                                get_product_list_of_branch(branch2);
                            }
                        } else {
                            Toast.makeText(getContext(), "NOT ASSIGN BRANCH FOR SELECTED USER", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            pDialog.hide();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void get_product_list_of_branch(String branch) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().getproduct_list(sid, "'" + branch + "'", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, retrofit.client.Response response) {
                    try {
                        pDialog.hide();
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        //String products_with_comma = j2.get("msg").getAsString();
                        products_with_comma = j2.get("msg").getAsString();
                        //call_hide_show_function(products_with_comma);

                        /*SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.putString("branch_product", products_with_comma);
                        editor.commit();*/

                        //////////////////////
                        set_hide_show_brand_matrix(products_with_comma);
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            pDialog.hide();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void set_hide_show_brand_matrix(String product_comma_seprated) {
        try {
            if (!product_comma_seprated.equals("") && !product_comma_seprated.equals(null)) {
                ll_1.setVisibility(product_comma_seprated.contains("ACTIRAB - D CAP") == true ? View.VISIBLE : View.GONE);
                ll_2.setVisibility(product_comma_seprated.contains("ACTIRAB - L CAP") == true ? View.VISIBLE : View.GONE);
                ll_3.setVisibility(product_comma_seprated.contains("ACTIRAB -DV Cap") == true ? View.VISIBLE : View.GONE);
                ll_4.setVisibility(product_comma_seprated.contains("ACTIRAB TAB") == true ? View.VISIBLE : View.GONE);
                ll_5.setVisibility(product_comma_seprated.contains("EMPOWER - OD TAB") == true ? View.VISIBLE : View.GONE);
                ll_6.setVisibility(product_comma_seprated.contains("GLUCOLYST -G1 TAB") == true ? View.VISIBLE : View.GONE);
                ll_7.setVisibility(product_comma_seprated.contains("LYCOLIC 10ml DROP") == true ? View.VISIBLE : View.GONE);
                ll_8.setVisibility(product_comma_seprated.contains("LYCOREST 60ml SUSP") == true ? View.VISIBLE : View.GONE);
                ll_9.setVisibility(product_comma_seprated.contains("LYCOREST TAB") == true ? View.VISIBLE : View.GONE);
                ll_10.setVisibility(product_comma_seprated.contains("LYCORT 1 ml INJ") == true ? View.VISIBLE : View.GONE);
                ll_11.setVisibility(product_comma_seprated.contains("REGAIN - XT TAB") == true ? View.VISIBLE : View.GONE);
                ll_12.setVisibility(product_comma_seprated.contains("STAND - MF 60ml SUSP") == true ? View.VISIBLE : View.GONE);
                ll_13.setVisibility(product_comma_seprated.contains("STAND -SP TAB") == true ? View.VISIBLE : View.GONE);
                ll_14.setVisibility(product_comma_seprated.contains("STAR T TAB") == true ? View.VISIBLE : View.GONE);
                ll_15.setVisibility(product_comma_seprated.contains("TEN-ON 30 ml SYRUP") == true ? View.VISIBLE : View.GONE);
                ll_16.setVisibility(product_comma_seprated.contains("TRYGESIC TAB") == true ? View.VISIBLE : View.GONE);
                ll_17.setVisibility(product_comma_seprated.contains("WEGO GEL 20mg") == true ? View.VISIBLE : View.GONE);
                ll_18.setVisibility(product_comma_seprated.contains("WEGO GEL 30mg") == true ? View.VISIBLE : View.GONE);

                ll_19.setVisibility(product_comma_seprated.contains("CIPGROW SYRUP") == true ? View.VISIBLE : View.GONE);
                ll_20.setVisibility(product_comma_seprated.contains("CLAVYTEN 625") == true ? View.VISIBLE : View.GONE);
                ll_21.setVisibility(product_comma_seprated.contains("LEVOCAST-M") == true ? View.VISIBLE : View.GONE);
                ll_22.setVisibility(product_comma_seprated.contains("ALTIPAN DSR") == true ? View.VISIBLE : View.GONE);
                ll_23.setVisibility(product_comma_seprated.contains("SANGRIA TONIC") == true ? View.VISIBLE : View.GONE);
                ll_24.setVisibility(product_comma_seprated.contains("ONEDERM CREAM") == true ? View.VISIBLE : View.GONE);
                ll_25.setVisibility(product_comma_seprated.contains("ACTIREST-LS") == true ? View.VISIBLE : View.GONE);
                ll_26.setVisibility(product_comma_seprated.contains("ACTIREST-DX") == true ? View.VISIBLE : View.GONE);
                ll_27.setVisibility(product_comma_seprated.contains("KORBY SOAP") == true ? View.VISIBLE : View.GONE);
                ll_28.setVisibility(product_comma_seprated.contains("TRYGESIC-P") == true ? View.VISIBLE : View.GONE);

                ll_29.setVisibility(product_comma_seprated.contains("ITEZONE 200 CAP") == true ? View.VISIBLE : View.GONE);
                ll_30.setVisibility(product_comma_seprated.contains("NEXTVIT TAB") == true ? View.VISIBLE : View.GONE);
            }

        } catch (Exception ex)

        {
            pDialog.hide();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    /*----------------------------------*/


    private void show_dialog_for_doctor_of_TBM() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {

                user_list_hierarchy_me_FragmentDialog dialog = user_list_hierarchy_me_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "TBM_ONLY");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_chemist_call_new.this, 300);
                dialog.show(getFragmentManager(), "fragment_doctor_call_newff");

            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }


    }

    private void show_dialog_for_select_chemist(String UserID) {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                Bundle bundle = new Bundle();
                bundle.putString("Emp_ID", UserID);

                test_attch_chemist_FragmentDialog dialog = test_attch_chemist_FragmentDialog.newInstance("Hello world");

                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_chemist_call_new.this, 300);
                dialog.show(getFragmentManager(), "fdf");

            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_jwf_top_down_emp() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                user_list_hierarchy_me_FragmentDialog dialog = user_list_hierarchy_me_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "top_down_jfw");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_chemist_call_new.this, 300);
                dialog.show(getFragmentManager(), "fragment_doctor_call_newff");

            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }


    }

    private void Clear_POJO_CHEMIST() {
        try {
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_Chemist_S.class);
            mRealm.commitTransaction();
            mRealm.close();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_date() {

        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.setTargetFragment(fragment_chemist_call_new.this, 300);
                dialog.show(getFragmentManager(), "fragment_chemist_call_newdf");

                //DatePickerFragment dialog = new DatePickerFragment();
                //dialog.show(getSupportFragmentManager(), DIALOG_DATE);

           /*user_list_hierarchy_FragmentDialog dialog = user_list_hierarchy_FragmentDialog.newInstance("Hello world");
            final Bundle bundle = new Bundle();
            bundle.putString("stcokiest", "N");
            dialog.setArguments(bundle);
            dialog.setTargetFragment(fragment_objective_list.this, 300);
            dialog.show(getFragmentManager(), "fdf");*/

            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean temp_flag = false;

    public boolean Get_today_date_and_check_app_version(final String edit_date) {

        String versionName = BuildConfig.VERSION_NAME;
        temp_flag = false;

        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            final String emp = app_preferences.getString("name", "default");
            final String designation = app_preferences.getString("designation", "default");


            restService.getService().getToday_server_and_app_version_Method(sid, "", "'" + versionName + "'", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message"); //JsonArray j2 = j1.getAsJsonArray("message");
                        // String aa=j1.getAsJsonObject("cnt_of_emp_dcr")
                        String aa = "";
                        JsonElement jj;
                     /*   if (j2.get("cnt_of_emp_dcr").isJsonNull() == true) {
                            aa = "";
                            // editor.putString("name", "");
                        } else {

                          //aa = j2.get("cnt_of_emp_dcr").getAsString();
                            //editor.putString("name", j3.get("name").getAsString());
                            txt_selected_date.setText(j2.get("cnt_of_emp_dcr").getAsString());
                        }*/
                        temp_flag = true;
                        String app_support = j2.get("app_ver_count").getAsString();
                        if (app_support == "0") {
                            App_old_redirect_to_play_store();
                            temp_flag = false;
                        }

                       /* String date = j2.get("today_date").getAsString().replace("'", "");
                        select_date2.setText(date);
*/
                        //this is check wherether user edit today doctor call or not
                       /* if (edit_date.trim().length() > 0) {
                            if (edit_date.trim().equals(date)) {
                                temp_flag = true;
                            } else {
                                temp_flag = false;

                            }
                        }*/


                        pDialog.hide();


                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                    try {
                        String msg = "";
                        temp_flag = true;

                        pDialog.hide();

                        // Toast.makeText(getContext(), error.getKind().toString()+">>"+error.getCause().getMessage(), Toast.LENGTH_LONG).show();

                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        //String msg = error.getMessage();

                        if (msg.contains("403 FORBIDDEN")) {
                            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putString("status", "0");
                            editor.commit();

                            Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            getContext().startActivity(k);


                            // Toast.makeText(getContext(), "ERROR..", Toast.LENGTH_SHORT).show();

                            Toast.makeText(getContext(), "PLEASE WAIT..REFRESHING..", Toast.LENGTH_SHORT).show();
                        } else if (msg.contains("139.59.63.181")) {

                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }


                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }

                        if (task_user != null) {
                            task_user.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void App_old_redirect_to_play_store() {
        try {
            Toast.makeText(getContext(), "PLEASE UPDATE APP..THIS VERSION IS NOT SUPPORTED FROM NOW", Toast.LENGTH_LONG).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {

                    final String appPackageName = getContext().getPackageName(); // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    System.exit(0);
                }
            }, 3000);
            //get.finish();


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void CALL_CHECK_TBM_OR_NOT(String original_call_by_user_user_id) {
        try {
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String designation = app_preferences.getString("designation", "default");
            final String emp = app_preferences.getString("name", "default");

            if (designation.equals("TBM") == false) {
                chemist_of_TBM.setVisibility(View.VISIBLE);
                txt_chemist_of_TBM.setVisibility(View.VISIBLE);
                view_select_tbm_of_doctor.setVisibility(View.VISIBLE);

            } else {
                chemist_of_TBM.setVisibility(View.GONE);
                txt_chemist_of_TBM.setVisibility(View.GONE);
                view_select_tbm_of_doctor.setVisibility(View.GONE);

            }
            if (original_call_by_user_user_id.toString().trim().length() > 0) {
                if (emp.equals(original_call_by_user_user_id) == true) {
                    btn_submit.setVisibility(View.VISIBLE);

                } else {
                    btn_submit.setVisibility(View.GONE);

                }
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    /*Check Form Lock Or Not*/
    public void lock_check() {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String name = app_preferences.getString("name", "default");

            restService.getService().getTransactionFormLockOrNot(sid, "'" + name + "'", "T_ChC", select_date2.getText().toString(), new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        String lock_flag = j2.get("lock_flag").getAsString();
                        String message = j2.get("message").getAsString();
                        if (lock_flag.equals("1")) {
                            save_chemist_call();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();//"Oops !!! Locked Profile..."
                        }

                        /*JsonObject j1 = jsonElement.getAsJsonObject();
                        //JsonObject j2 = j1.getAsJsonObject("message");

                        //String lock_flag = j2.get("message").getAsString();
                        String lock_flag = j1.get("message").getAsString();
                        if (lock_flag == "1") {
                            save_chemist_call();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), "Oops !!! Locked Chemist Call...", Toast.LENGTH_SHORT).show();
                        }*/

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();
                        Toast.makeText(getContext(), "PLEASE TRY AGAIN...", Toast.LENGTH_SHORT).show();
                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            //onsession_failure();
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

    private void save_chemist_call() {
        try {

            /*This code Cut From button Click Event else part*/

            Bundle bundle = this.getArguments();
            POJO_chemist_Calls POJO = new POJO_chemist_Calls();
            // POJO.setName(txt_patch_id.getText().toString());

            Boolean jfw = false;
            if (!jfw_of_all_22.getText().toString().toLowerCase().trim().contains("select") && !jfw_of_all_22.getText().toString().toLowerCase().trim().contains("none")) {
                if (!jfw_of_all_2.getText().toString().toLowerCase().trim().contains("select") && !jfw_of_all_2.getText().toString().toLowerCase().trim().contains("none")) {
                    //
                    if (!jfw_of_all_22.getText().toString().equals(jfw_of_all_2.getText().toString())) {
                        jfw = true;
                    } else {
                        Toast.makeText(getContext(), "YOU SHOULD NOT SELECT SAME NAME IN JFW1 & JFW2", Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                        return;
                    }
                } else {
                    Toast.makeText(getContext(), "IF JFW WITH ONLY ONE THEN SELECT ONLY JFW1 NOT JFW2...", Toast.LENGTH_SHORT).show();
                    pDialog.hide();
                    return;
                }
            }

            if ((lat > 0 && lon > 0) || cnt_location > 2) {
                cnt_location = 0;

                POJO.setDate(select_date2.getText().toString());
                POJO.setUser_name(chemist_of_TBM_2.getText().toString());
                POJO.setChemist_name(select_chemist2.getText().toString());


                POJO.setUser_id(name_chemist_of_TBM.getText().toString());
                POJO.setChemist_id(name_select_chemist.getText().toString());


                POJO.setHq_name(txt_hq_name.getText().toString());
                //POJO.setJwf_with(edit_jwf_with.getText().toString());
                POJO.setJwf_with(jfw_of_all_2.getText().toString());
                POJO.setJwf_with2(jfw_of_all_22.getText().toString());
                POJO.setCall_by_user_id(txt_dr_call_by_user_id.getText().toString());
                POJO.setQty_1(q_1.getText().toString());
                POJO.setQty_2(q_2.getText().toString());
                POJO.setQty_3(q_3.getText().toString());
                POJO.setQty_4(q_4.getText().toString());
                POJO.setQty_5(q_5.getText().toString());
                POJO.setQty_6(q_6.getText().toString());
                POJO.setQty_7(q_7.getText().toString());
                POJO.setQty_8(q_8.getText().toString());
                POJO.setQty_9(q_9.getText().toString());
                POJO.setQty_10(q_10.getText().toString());
                POJO.setQty_11(q_11.getText().toString());
                POJO.setQty_12(q_12.getText().toString());
                POJO.setQty_13(q_13.getText().toString());
                POJO.setQty_14(q_14.getText().toString());
                POJO.setQty_15(q_15.getText().toString());
                POJO.setQty_16(q_16.getText().toString());
                POJO.setQty_17(q_17.getText().toString());
                POJO.setQty_18(q_18.getText().toString());

                POJO.setQty_19(q_19.getText().toString());
                POJO.setQty_20(q_20.getText().toString());
                POJO.setQty_21(q_21.getText().toString());
                POJO.setQty_22(q_22.getText().toString());
                POJO.setQty_23(q_23.getText().toString());
                POJO.setQty_24(q_24.getText().toString());
                POJO.setQty_25(q_25.getText().toString());
                POJO.setQty_26(q_26.getText().toString());
                POJO.setQty_27(q_27.getText().toString());
                POJO.setQty_28(q_28.getText().toString());

                POJO.setQty_29(q_29.getText().toString());
                POJO.setQty_30(q_30.getText().toString());


                POJO.setFqty_1(f_1.getText().toString());
                POJO.setFqty_2(f_2.getText().toString());
                POJO.setFqty_3(f_3.getText().toString());
                POJO.setFqty_4(f_4.getText().toString());
                POJO.setFqty_5(f_5.getText().toString());
                POJO.setFqty_6(f_6.getText().toString());
                POJO.setFqty_7(f_7.getText().toString());
                POJO.setFqty_8(f_8.getText().toString());
                POJO.setFqty_9(f_9.getText().toString());
                POJO.setFqty_10(f_10.getText().toString());
                POJO.setFqty_11(f_11.getText().toString());
                POJO.setFqty_12(f_12.getText().toString());
                POJO.setFqty_13(f_13.getText().toString());
                POJO.setFqty_14(f_14.getText().toString());
                POJO.setFqty_15(f_15.getText().toString());
                POJO.setFqty_16(f_16.getText().toString());
                POJO.setFqty_17(f_17.getText().toString());
                POJO.setFqty_18(f_18.getText().toString());

                POJO.setFqty_19(f_19.getText().toString());
                POJO.setFqty_20(f_20.getText().toString());
                POJO.setFqty_21(f_21.getText().toString());
                POJO.setFqty_22(f_22.getText().toString());
                POJO.setFqty_23(f_23.getText().toString());
                POJO.setFqty_24(f_24.getText().toString());
                POJO.setFqty_25(f_25.getText().toString());
                POJO.setFqty_26(f_26.getText().toString());
                POJO.setFqty_27(f_27.getText().toString());
                POJO.setFqty_28(f_28.getText().toString());

                POJO.setFqty_29(f_29.getText().toString());
                POJO.setFqty_30(f_30.getText().toString());

                POJO.setTqty_1(t_1.getText().toString());
                POJO.setTqty_2(t_2.getText().toString());
                POJO.setTqty_3(t_3.getText().toString());
                POJO.setTqty_4(t_4.getText().toString());
                POJO.setTqty_5(t_5.getText().toString());
                POJO.setTqty_6(t_6.getText().toString());
                POJO.setTqty_7(t_7.getText().toString());
                POJO.setTqty_8(t_8.getText().toString());
                POJO.setTqty_9(t_9.getText().toString());
                POJO.setTqty_10(t_10.getText().toString());
                POJO.setTqty_11(t_11.getText().toString());
                POJO.setTqty_12(t_12.getText().toString());
                POJO.setTqty_13(t_13.getText().toString());
                POJO.setTqty_14(t_14.getText().toString());
                POJO.setTqty_15(t_15.getText().toString());
                POJO.setTqty_16(t_16.getText().toString());
                POJO.setTqty_17(t_17.getText().toString());
                POJO.setTqty_18(t_18.getText().toString());

                POJO.setTqty_19(t_19.getText().toString());
                POJO.setTqty_20(t_20.getText().toString());
                POJO.setTqty_21(t_21.getText().toString());
                POJO.setTqty_22(t_22.getText().toString());
                POJO.setTqty_23(t_23.getText().toString());
                POJO.setTqty_24(t_24.getText().toString());
                POJO.setTqty_25(t_25.getText().toString());
                POJO.setTqty_26(t_26.getText().toString());
                POJO.setTqty_27(t_27.getText().toString());
                POJO.setTqty_28(t_28.getText().toString());

                POJO.setTqty_29(t_29.getText().toString());
                POJO.setTqty_30(t_30.getText().toString());


                POJO.setTamt_1(ta_1.getText().toString());
                POJO.setTamt_2(ta_2.getText().toString());
                POJO.setTamt_3(ta_3.getText().toString());
                POJO.setTamt_4(ta_4.getText().toString());
                POJO.setTamt_5(ta_5.getText().toString());
                POJO.setTamt_6(ta_6.getText().toString());
                POJO.setTamt_7(ta_7.getText().toString());
                POJO.setTamt_8(ta_8.getText().toString());
                POJO.setTamt_9(ta_9.getText().toString());
                POJO.setTamt_10(ta_10.getText().toString());
                POJO.setTamt_11(ta_11.getText().toString());
                POJO.setTamt_12(ta_12.getText().toString());
                POJO.setTamt_13(ta_13.getText().toString());
                POJO.setTamt_14(ta_14.getText().toString());
                POJO.setTamt_15(ta_15.getText().toString());
                POJO.setTamt_16(ta_16.getText().toString());
                POJO.setTamt_17(ta_17.getText().toString());
                POJO.setTamt_18(ta_18.getText().toString());

                POJO.setTamt_19(ta_19.getText().toString());
                POJO.setTamt_20(ta_20.getText().toString());
                POJO.setTamt_21(ta_21.getText().toString());
                POJO.setTamt_22(ta_22.getText().toString());
                POJO.setTamt_23(ta_23.getText().toString());
                POJO.setTamt_24(ta_24.getText().toString());
                POJO.setTamt_25(ta_25.getText().toString());
                POJO.setTamt_26(ta_26.getText().toString());
                POJO.setTamt_27(ta_27.getText().toString());
                POJO.setTamt_28(ta_28.getText().toString());

                POJO.setTamt_29(ta_29.getText().toString());
                POJO.setTamt_30(ta_30.getText().toString());

                POJO.setComment(edit_comment.getText().toString());

                String s1 = String.valueOf(lat);
                String s2 = String.valueOf(lon);


                pDialog.show();
                if (Validation(POJO) == true) {
                    if (bundle != null) {
                        POJO.setUp_Latitude(s1);
                        POJO.setUp_Longitude(s2);
                        update_chemist_calls(POJO);
                    } else {
                        POJO.setLatitude(s1);
                        POJO.setLongitude(s2);
                        insert_chemist_calls(POJO);
                    }
                } else {
                    pDialog.hide();
                }
            } else {
                context = getActivity();
                if (context != null) {

                    GPSTracker gps = new GPSTracker(getContext(), fragment_chemist_call_new.this, false);
                    if (gps.canGetLocation()) {
                        lat = gps.getLatitude();
                        lon = gps.getLongitude();

                    } else {
                        gps.showSettingsAlert();
                    }

                    cnt_location++;
                    Toast.makeText(getContext(), "Current Location Not Found...Try Again For Save", Toast.LENGTH_SHORT).show();

                }
            }

        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*----------Trace Mobile Location Co-ordinate Start----------------*/

    void getLocation() {
        try {
            context = getActivity();
            //  Toast.makeText(getContext(), "Lat: " + lat + "\n Lon: " + lon, Toast.LENGTH_SHORT).show();
            if (context != null) {
                locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
            } else {
                Toast.makeText(getContext(), "Please Contact IT Support of Lysten Global Immediately(Issue-GPS error 9856)", Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
// locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
        try {
            lat = Double.valueOf(location.getLatitude());
            lon = Double.valueOf(location.getLongitude());
            //Toast.makeText(getContext(), "Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude(), Toast.LENGTH_SHORT).show();
            //try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            /*locationText.setText(locationText.getText() + "\n" + addresses.get(0).getAddressLine(0) + ", " +
                    addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2));*/
            //Toast.makeText(getContext(), "Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude() + "\n" + addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please Contact IT Support of Lysten Global Immediately(Issue-GPS error 1556)", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        //gps_enable_flag = 1;
        //Toast.makeText(getContext(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
        //alert_box();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //String ss = provider + status;

    }

    @Override
    public void onProviderEnabled(String provider) {
        //gps_enable_flag = 0;
        //getLocation();
    }

    /*------------Co-ordinate End--------------*/

    private void alert_box() {
        try {
            context = getActivity();
            if (context != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Message</font>"));
                builder.setMessage("Please Enable GPS and Internet");

                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
