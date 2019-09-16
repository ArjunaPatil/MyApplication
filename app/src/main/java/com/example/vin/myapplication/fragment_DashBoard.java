package com.example.vin.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_DashBoard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_DashBoard extends Fragment implements popup_todays_objective_new_DialogFragment.Today_Obj_after_save,
        datewise_reporting_summary_FragmentDialog.EditFromDateToDateDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int year, month, day;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context;
    Double lat = 0.0, lon = 0.0;
    int gps_enable_flag = 0;
    LocationManager locationManager;

    private TextView txt_selected_date;
    private OnFragmentInteractionListener mListener;
    RestService restService;
    TextView tv_todays_objective;
    private ProgressDialog pDialog;
    TextView txt_cbooking_count;
    TextView txt_chm_count, txt_chm_count1, txt_chm_count2;
    TextView txt_dcr_count, txt_dcr_count1, txt_dcr_count2;
    private static final int PERMS_REQUEST_CODE = 123;
    //////////////////////////
    ///////////////////
    /////////////////////////
    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            // Manifest.permission.READ_CONTACTS
    };
    private static final String[] CAMERA_PERMS = {
            Manifest.permission.CAMERA
    };
    private static final String[] CONTACTS_PERMS = {
            Manifest.permission.READ_CONTACTS
    };
    private static final String[] LOCATION_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    private static final int INITIAL_REQUEST = 1337;
    private static final int CAMERA_REQUEST = INITIAL_REQUEST + 1;
    private static final int CONTACTS_REQUEST = INITIAL_REQUEST + 2;
    private static final int LOCATION_REQUEST = INITIAL_REQUEST + 3;


    public fragment_DashBoard() {
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
    public static fragment_DashBoard newInstance(String param1, String param2) {
        fragment_DashBoard fragment = new fragment_DashBoard();
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

        /////////////////
        ////////////////
        //////////////
        if (Build.VERSION.SDK_INT >= 23) {//Build.VERSION_CODES.M
            if (!canAccessLocation()) {
                requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment__dash_board_6, container, false);
        //employee = (TextView) view.getView().findViewById(employee);
        //return inflater.inflate(R.layout.fragment_my_profile2, container, false);

        /*if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(),
                        ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                            ACCESS_COARSE_LOCATION},
                    101);

        } else {

        }*/

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

    /*--------Code For Comparision Branch / EmpCode--------*/

    /*-------Code For Comparision Branch / EmpCode---------*/

    @Override
    public void onStart() {
        try {
            super.onStart();
            GPSTracker gps = new GPSTracker(getContext(), this, true);
            pDialog = new ProgressDialog(getContext());
            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            ((DashBord_main) getActivity()).setActionBarTitle("DASHBOARD");
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String designation = (app_preferences.getString("designation", "default"));
            String branch = (app_preferences.getString("branch", "default"));
            String product_for_branch = (app_preferences.getString("branch_product", ""));
            loaddashboard();
            if (designation.trim().toString().equals("TBM") == true || designation.trim().toString().equals("ABM") == true || designation.trim().toString().equals("RBM") == true || designation.trim().toString().equals("CRM") == true || designation.trim().toString().equals("ZBM") == true || designation.trim().toString().equals("SM") == true || designation.trim().toString().equals("NBM") == true) {
                check_branch();//
            }
            if (product_for_branch == "" || product_for_branch == null) {
                if (branch != "" || branch != null) {
                    if (designation.trim().toString().equals("TBM") == true || designation.trim().toString().equals("ABM") == true || designation.trim().toString().equals("RBM") == true || designation.trim().toString().equals("CRM") == true || designation.trim().toString().equals("ZBM") == true || designation.trim().toString().equals("SM") == true || designation.trim().toString().equals("NBM") == true || designation.trim().toString().equals("Head of Marketing and Sales")) {
                        get_product_list_of_branch();
                    }
                } else {
                    Toast.makeText(getContext(), "Your Branch Not Filled... Please Inform To Office... ", Toast.LENGTH_SHORT).show();
                }
            }
            load_events();
            pDialog.hide();

            if (designation.trim().toString().equals("TBM") == false) {
                CustomImageButton btn_book_call_new = (CustomImageButton) getView().findViewById(R.id.btn_book_call_new);
                btn_book_call_new.setVisibility(View.GONE);
            } else {
                CustomImageButton btn_book_call_new = (CustomImageButton) getView().findViewById(R.id.btn_book_call_new);
                btn_book_call_new.setVisibility(View.VISIBLE);
            }
            /*Comment by arjun because objective get through loaddashboard method
            if (txt_selected_date.getText().toString().contains("Refresh ->") == false) {
                get_todays_objective(txt_selected_date.getText().toString());
            }*/

        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            context = getActivity();
            if (context != null) {
                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loaddashboard() {
        //edit_selected_date = (EditText) getView().findViewById(R.id.edit_selected_date);
        String versionName = BuildConfig.VERSION_NAME;
        txt_selected_date = (TextView) getView().findViewById(R.id.txt_selected_date);

        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            final String emp = app_preferences.getString("name", "default");
            final String designation = app_preferences.getString("designation", "default");
            final String branch = app_preferences.getString("branch", "");
            TextView txt_app_ver = (TextView) getView().findViewById(R.id.txt_app_ver);
            txt_app_ver.setText("APP VERSION: " + versionName.toString());
            restService.getService().getDashboad_data_Method(sid, "'" + emp + "'", designation, "", "'" + versionName + "'", new Callback<JsonElement>() {
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

                        String app_support = j2.get("app_ver_count").getAsString();
                        if (app_support == "0") {
                            App_old_redirect_to_play_store();


                        }


                        TextView txt_selected_date = (TextView) getView().findViewById(R.id.txt_selected_date);
                        TextView tv_todays_objective = (TextView) getView().findViewById(R.id.tv_todays_objective);

                        TextView txt_dcr_count = (TextView) getView().findViewById(R.id.txt_dcr_count);
                        TextView txt_dcr_count1 = (TextView) getView().findViewById(R.id.txt_dcr_count1);
                        TextView txt_dcr_count2 = (TextView) getView().findViewById(R.id.txt_dcr_count2);
                        TextView txt_chm_count = (TextView) getView().findViewById(R.id.txt_chm_count);
                        TextView txt_chm_count1 = (TextView) getView().findViewById(R.id.txt_chm_count1);
                        TextView txt_chm_count2 = (TextView) getView().findViewById(R.id.txt_chm_count2);
                        TextView txt_cbooking_count = (TextView) getView().findViewById(R.id.txt_cbooking_count);

                        TextView obj_head = (TextView) getView().findViewById(R.id.obj_head);
                        TextView dcr_head = (TextView) getView().findViewById(R.id.dcr_head);
                        TextView dcr_per = (TextView) getView().findViewById(R.id.dcr_per);
                        TextView chem_head = (TextView) getView().findViewById(R.id.chem_head);
                        TextView chem_per = (TextView) getView().findViewById(R.id.chem_per);


                        obj_head.setVisibility(View.VISIBLE);
                        dcr_head.setVisibility(View.VISIBLE);
                        dcr_per.setVisibility(View.VISIBLE);
                        chem_head.setVisibility(View.VISIBLE);
                        chem_per.setVisibility(View.VISIBLE);
                        txt_dcr_count1.setVisibility(View.VISIBLE);
                        txt_dcr_count2.setVisibility(View.VISIBLE);
                        txt_chm_count1.setVisibility(View.VISIBLE);
                        txt_chm_count2.setVisibility(View.VISIBLE);


                        String date = j2.get("today_date").getAsString().replace("'", "");
                        txt_selected_date.setText(date);
                        tv_todays_objective.setText(j2.get("obj").getAsString());

                        String report_ip = j2.get("report_ip").getAsString();
                        String allow_user_for_user_form = j2.get("allow_user_for_user_form").getAsString();
                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.putString("report_ip", report_ip);
                        editor.putString("allow_user_for_user_form", allow_user_for_user_form);
                        editor.commit();

                        if (designation.equals("TBM") == false) {
                            txt_dcr_count.setText("TODAY'S TBM DCR CALL: " + j2.get("actual_dcr_call_tbm").getAsString() + " / EXPECTED: " + j2.get("expected_dcr_call_tbm").getAsString() + " ");
                            txt_dcr_count1.setText("DCR Percent of TBM: " + j2.get("percent_tbm_dcr_call").getAsString() + "%   / TBM:COUNT: " + j2.get("count_of_emp_only_TBM").getAsString());
                            txt_dcr_count2.setText("ALL DCR TODAY: " + j2.get("cnt_of_emp_dcr").getAsString());

                            txt_chm_count.setText("TODAY'S TBM CHEM CALL: " + j2.get("actual_chem_call_tbm").getAsString() + " / EXPECTED: " + j2.get("expected_chem_call_tbm").getAsString() + "");
                            txt_chm_count1.setText("CHEM CALL Percent of TBM:" + j2.get("percent_tbm_chem_call").getAsString() + "%  / TBM:COUNT: " + j2.get("count_of_emp_only_TBM").getAsString());
                            txt_chm_count2.setText("ALL CHEM CALL TODAY: " + j2.get("cnt_of_emp_chem").getAsString());


                            txt_cbooking_count.setText("ALL CAMP BOOKING TODAY: " + j2.get("cnt_of_emp_camp").getAsString());
                            Float per_obj = 0.0f;
                            if (Integer.parseInt(j2.get("cnt_emp").toString()) > 0) {
                                Float cnt_all = Float.parseFloat(j2.get("cnt_emp").toString());
                                Float obj_cnt = Float.parseFloat(j2.get("cnt_emp_objective").toString());
                                per_obj = 100 * (obj_cnt / cnt_all);
                            }
                            obj_head.setText("(" + per_obj.toString() + "%)");
                            // dcr_head.setText("
                            dcr_per.setText(j2.get("percent_tbm_dcr_call").getAsString() + "%");
                            // chem_head.setText("
                            chem_per.setText(j2.get("percent_tbm_chem_call").getAsString() + "%");

                        } else {
                            txt_dcr_count1.setVisibility(View.GONE);
                            txt_dcr_count2.setVisibility(View.GONE);
                            txt_chm_count1.setVisibility(View.GONE);
                            txt_chm_count2.setVisibility(View.GONE);
                            obj_head.setVisibility(View.GONE);
                            dcr_head.setVisibility(View.GONE);
                            dcr_per.setVisibility(View.GONE);
                            chem_head.setVisibility(View.GONE);
                            chem_per.setVisibility(View.GONE);
                            txt_dcr_count.setText("DCR COMPLETED TODAY: " + j2.get("cnt_of_emp_dcr").getAsString());
                            txt_chm_count.setText("CHEM CALL COMPLETED TODAY: " + j2.get("cnt_of_emp_chem").getAsString());
                            txt_cbooking_count.setText(" CAMP BOOKING TODAY: " + j2.get("cnt_of_emp_camp").getAsString());
                        }


                        if (designation.equals("NBM") || designation.equals("HR Manager") || designation.equals("Head of Marketing and Sales") || designation.equals("Admin")) {
                            LinearLayout ll_presenty = (LinearLayout) getView().findViewById(R.id.ll_presenty);
                            ll_presenty.setVisibility(View.VISIBLE);
                            TextView txt_presenty = (TextView) getView().findViewById(R.id.txt_presenty);
                            String s = "";
                            /*s += "<b>" + "Total Employee(NBM To TBM): " + "</b>" + j2.get("active_emp").getAsString() + "<br />";
                            s += "<b>" + "Total Objectives: " + "</b>" + j2.get("tot_obj").getAsString() + "<br />";
                            s += "<b>" + "Today Working Employee: " + "</b>" + j2.get("Present").getAsString() + "<br />";
                            s += "<b>" + "On Leave Employee: " + "</b>" + j2.get("Leave").getAsString() + "<br />";
                            s += "<b>" + "Missing Objetives: " + "</b>" + j2.get("miss_obj").getAsString();*/
                            if (branch.equals("Main")) {
                                s += "<b>" + "Total Employee: " + "</b> <b>MAIN:</b>" + j2.get("M_active_emp").getAsString() + "<br />";
                                s += "<b>" + "Total Objectives: " + "</b> <b>MAIN:</b>" + j2.get("M_tot_obj").getAsString() + "<br />";
                                s += "<b>" + "Today On Work: " + "</b> <b>MAIN:</b>" + j2.get("M_Present").getAsString() + "<br />";
                                s += "<b>" + "On Leave: " + "</b> <b>MAIN:</b>" + j2.get("M_Leave").getAsString() + "<br />";
                                s += "<b>" + "Missing Objetives: " + "</b> <b>MAIN:</b>" + j2.get("M_miss_obj").getAsString() + "<br />";
                                s += "<b>" + "Missing Branch: " + "</b>" + j2.get("miss_branch").getAsString() + "<br />";
                            } else if (branch.equals("Derby")) {
                                s += "<b>" + "Total Employee: " + "</b><b>DERBY:</b>" + j2.get("D_active_emp").getAsString() + "<br />";
                                s += "<b>" + "Total Objectives: " + "</b> <b>DERBY:</b>" + j2.get("D_tot_obj").getAsString() + "<br />";
                                s += "<b>" + "Today On Work: " + "</b> <b>DERBY:</b>" + j2.get("D_Present").getAsString() + "<br />";
                                s += "<b>" + "On Leave: " + "</b>  <b>DERBY:</b>" + j2.get("D_Leave").getAsString() + "<br />";
                                s += "<b>" + "Missing Objetives: " + "</b><b>DERBY:</b>" + j2.get("D_miss_obj").getAsString() + "<br />";
                                s += "<b>" + "Missing Branch: " + "</b>" + j2.get("miss_branch").getAsString() + "<br />";
                            } else {
                                s += "<b>" + "Total Employee: " + "</b> <b>MAIN:</b>" + j2.get("M_active_emp").getAsString() + " <b>DERBY:</b>" + j2.get("D_active_emp").getAsString() + "<br />";
                                s += "<b>" + "Total Objectives: " + "</b> <b>MAIN:</b>" + j2.get("M_tot_obj").getAsString() + " <b>DERBY:</b>" + j2.get("D_tot_obj").getAsString() + "<br />";
                                s += "<b>" + "Today On Work: " + "</b> <b>MAIN:</b>" + j2.get("M_Present").getAsString() + " <b>DERBY:</b>" + j2.get("D_Present").getAsString() + "<br />";
                                s += "<b>" + "On Leave: " + "</b> <b>MAIN:</b>" + j2.get("M_Leave").getAsString() + " <b>DERBY:</b>" + j2.get("D_Leave").getAsString() + "<br />";
                                s += "<b>" + "Missing Objetives: " + "</b> <b>MAIN:</b>" + j2.get("M_miss_obj").getAsString() + " <b>DERBY:</b>" + j2.get("D_miss_obj").getAsString() + "<br />";
                                s += "<b>" + "Missing Branch: " + "</b>" + j2.get("miss_branch").getAsString() + "<br />";
                            }
                            txt_presenty.setText(Html.fromHtml(s));
                        } else {
                            LinearLayout ll_presenty = (LinearLayout) getView().findViewById(R.id.ll_presenty);
                            ll_presenty.setVisibility(View.GONE);
                        }

                        pDialog.hide();


                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                    try {
                        String msg = "";
                        TextView txt_selected_date = (TextView) getView().findViewById(R.id.txt_selected_date);


                        TextView txt_dcr_count1 = (TextView) getView().findViewById(R.id.txt_dcr_count1);
                        TextView txt_dcr_count2 = (TextView) getView().findViewById(R.id.txt_dcr_count2);

                        TextView txt_chm_count1 = (TextView) getView().findViewById(R.id.txt_chm_count1);
                        TextView txt_chm_count2 = (TextView) getView().findViewById(R.id.txt_chm_count2);


                        TextView obj_head = (TextView) getView().findViewById(R.id.obj_head);
                        TextView dcr_head = (TextView) getView().findViewById(R.id.dcr_head);
                        TextView dcr_per = (TextView) getView().findViewById(R.id.dcr_per);
                        TextView chem_head = (TextView) getView().findViewById(R.id.chem_head);
                        TextView chem_per = (TextView) getView().findViewById(R.id.chem_per);


                        obj_head.setVisibility(View.GONE);
                        dcr_head.setVisibility(View.GONE);
                        dcr_per.setVisibility(View.GONE);
                        chem_head.setVisibility(View.GONE);
                        chem_per.setVisibility(View.GONE);
                        txt_dcr_count1.setVisibility(View.GONE);
                        txt_dcr_count2.setVisibility(View.GONE);
                        txt_chm_count1.setVisibility(View.GONE);
                        txt_chm_count2.setVisibility(View.GONE);


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
                        //import static com.example.vin.myapplication.DashBord_main.task_rpt_IP;
                        if (task_user != null) {
                            task_user.cancel(true);
                        }
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }


              /* SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        edit_selected_date.setText(sdf.format(date));
        edit_selected_date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //  showDialog(999);
          *//*      Toast.makeText(getContext(), "ca",
                        Toast.LENGTH_SHORT)
                        .show();

                return false;*//*
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datepick = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edit_selected_date.setText(year + "-" + month + "-" + dayOfMonth);

                        // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date(year, month, dayOfMonth);

                        edit_selected_date.setText(sdf.format(date));


                    }


                }, mYear, mMonth, mDay);
                datepick.setTitle("select date");
                datepick.show();
                return true;
            }
        });*/

    }

    /*-----------------------------------*/
    String products_with_comma = "";

    public void get_product_list_of_branch() {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String branch = app_preferences.getString("branch", "");

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

                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.putString("branch_product", products_with_comma);
                        editor.commit();

                        //////////////////////
                        //set_hide_show_brand_matrix(products_with_comma);
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
     /*-----------------------------------*/

    private void check_branch() {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String user_id = app_preferences.getString("name", "default");
            String employee = app_preferences.getString("employee_code", "default");
            restService.getService().getCheck_branch(sid, "'" + user_id + "'", "'" + employee + "'", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");
                        String veri_flag = j2.get("veri_flag").getAsString();
                        pDialog.hide();
                        if (veri_flag.equals("0")) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Message</font>"));
                            builder.setMessage(j2.get("message").getAsString());
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                            //Toast.makeText(getContext(), j2.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        String msg = "";
                        pDialog.hide();

                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.contains("403 FORBIDDEN")) {
                            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putString("status", "0");
                            editor.commit();
                            Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            getContext().startActivity(k);
                            Toast.makeText(getContext(), "PLEASE WAIT..REFRESHING..", Toast.LENGTH_SHORT).show();
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }
                        //import static com.example.vin.myapplication.DashBord_main.task_rpt_IP;
                        if (task_user != null) {
                            task_user.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception e) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }


              /* SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        edit_selected_date.setText(sdf.format(date));
        edit_selected_date.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //  showDialog(999);
          *//*      Toast.makeText(getContext(), "ca",
                        Toast.LENGTH_SHORT)
                        .show();

                return false;*//*
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datepick = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edit_selected_date.setText(year + "-" + month + "-" + dayOfMonth);

                        // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date(year, month, dayOfMonth);

                        edit_selected_date.setText(sdf.format(date));


                    }


                }, mYear, mMonth, mDay);
                datepick.setTitle("select date");
                datepick.show();
                return true;
            }
        });*/

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
                    getActivity().finish();
                }
            }, 3000);
            //get.finish();


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void load_events() {
        try {
            tv_todays_objective = (TextView) getView().findViewById(R.id.tv_todays_objective);

            ////Objective Section Start
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            final String designation = app_preferences.getString("designation", "default");
            final String branch = app_preferences.getString("branch", "");

            LinearLayout ll_presenty = (LinearLayout) getView().findViewById(R.id.ll_presenty);
            if (designation.equals("HR Manager") || designation.equals("NBM") || designation.equals("Head of Marketing and Sales") || designation.equals("Admin")) {

                ll_presenty.setVisibility(View.VISIBLE);
                CustomImageButton btn_presenty_call = (CustomImageButton) getView().findViewById(R.id.btn_presenty_call);
                btn_presenty_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment frag = new fragment_presenty_list();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack("presenty_list");
                        ft.commit();

                        //Toast.makeText(getContext(), "Work In Progress ...", Toast.LENGTH_SHORT).show();
                    }
                });
                CustomImageButton btn_miss_obj = (CustomImageButton) getView().findViewById(R.id.btn_miss_obj);
                btn_miss_obj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment frag = new fragment_miss_obj_list();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack("presenty_list");
                        ft.commit();

                        //Toast.makeText(getContext(), "Work In Progress ...", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                ll_presenty.setVisibility(View.GONE);
            }


            CustomImageButton btn_objective_add = (CustomImageButton) getView().findViewById(R.id.btn_objective_add);
            btn_objective_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_todays_objective();

                    /*Fragment frag = new fragment_daily_plan_insert_temp1();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag);

                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack("fragment_daily_plan");

                    ft.commit();*/
                }
            });

            CustomImageButton btn_objective_list = (CustomImageButton) getView().findViewById(R.id.btn_objective_list);
            btn_objective_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        String Designation = app_preferences.getString("designation", "default");

                        if ((Designation.equals("ABM")) || (Designation.equals("RBM")) || (Designation.equals("CRM")) || (Designation.equals("ZBM")) || (Designation.equals("SM")) || ((Designation.equals("NBM")) || (Designation.equals("Head of Marketing and Sales")) || (Designation.equals("HR Manager")) || (Designation.equals("Administrator")))) {

                            Fragment frag = new fragment_objective_list();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, frag);

                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack("fragment_objective_list");

                            ft.commit();

                        } else {
                            Toast.makeText(getContext(), "ACCESS DENIEDED...", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            CustomImageButton btn_doc_call_new = (CustomImageButton) getView().findViewById(R.id.btn_doc_call_new);
            btn_doc_call_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        GPSTracker gps = new GPSTracker(getContext(), fragment_DashBoard.this, false);
                        if (gps.canGetLocation()) {

                            lat = gps.getLatitude();
                            lon = gps.getLongitude();

                            Fragment frag = new fragment_doctor_call_new();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, frag);

                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack("fragment_doctor_call_new");

                            ft.commit();

                        } else {
                            gps.showSettingsAlert();
                        }


                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            CustomImageButton btn_doc_call = (CustomImageButton) getView().findViewById(R.id.btn_doc_call);
            btn_doc_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Fragment frag = new fragment_doctor_call();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);

                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack("fragment_doctor_call");

                        ft.commit();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            CustomImageButton btn_che_call_new = (CustomImageButton) getView().findViewById(R.id.btn_che_call_new);
            btn_che_call_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        GPSTracker gps = new GPSTracker(getContext(), fragment_DashBoard.this, false);
                        if (gps.canGetLocation()) {

                            lat = gps.getLatitude();
                            lon = gps.getLongitude();

                            Fragment frag = new fragment_chemist_call_new();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, frag);

                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack("fragment_chemist_call_new");

                            ft.commit();

                        } else {
                            gps.showSettingsAlert();
                        }

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            CustomImageButton btn_che_call = (CustomImageButton) getView().findViewById(R.id.btn_che_call);
            btn_che_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        Fragment frag = new fragment_chemist_call();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);

                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack("fragment_chemist_call");

                        ft.commit();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            CustomImageButton btn_book_call_new = (CustomImageButton) getView().findViewById(R.id.btn_book_call_new);
            btn_book_call_new.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Fragment frag = new fragment_booking_call_new();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);

                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack("fragment_chemist_call_new");

                        ft.commit();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            CustomImageButton btn_book_call = (CustomImageButton) getView().findViewById(R.id.btn_book_call);
            btn_book_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Fragment frag = new fragment_booking_call();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);

                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack("fragment_chemist_call");

                        ft.commit();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


            ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loaddashboard();
                    if (branch != "" || branch != null) {
                        if (designation.trim().toString().equals("TBM") == true || designation.trim().toString().equals("ABM") == true || designation.trim().toString().equals("RBM") == true || designation.trim().toString().equals("CRM") == true || designation.trim().toString().equals("ZBM") == true || designation.trim().toString().equals("SM") == true || designation.trim().toString().equals("NBM") == true || designation.trim().toString().equals("Head of Marketing and Sales")) {
                            get_product_list_of_branch();
                        }
                    } else {
                        Toast.makeText(getContext(), "Your Branch Not Filled... Please Inform To Office... ", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Button btn_report = (Button) getView().findViewById(R.id.btn_report);
            btn_report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

             /*   Fragment frag = new Fragment_Webview_test();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, frag);

                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack("fragment_webview_report");

                ft.commit();*/
                    final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    String designation = (app_preferences.getString("designation", "default"));
                    /*if ((designation.equals("TBM")) || (designation.equals("KBM"))) {
                        show_dialog_for_datewise_reporting_summary();
                    } else {*/
                    Fragment frag = new fragment_lysten_reports();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack("fragment_lysten");

                    ft.commit();

                    /*}*/
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }


   /* private void showDialog()
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);

    }*/

   /* private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

*/
    /*private void showDate(int year, int month, int day) {
        edit_selected_date = (EditText) getView().findViewById(edit_selected_date);
        edit_selected_date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }*/

    public boolean onBackPressed() {
        return false;
    }


    /*Section Start Objective Of the Date */
    @Override
    public void onFinish_Today_Obj_Save(String FlagOpr, String Today_Obj) {
        try {
            // Toast.makeText(getContext(), "Hi, " + inputText, Toast.LENGTH_SHORT).show();

            if (FlagOpr.equals("Y")) {
                loaddashboard();
                Toast.makeText(getContext(), "Save Successfully...", Toast.LENGTH_SHORT).show();
                //TextView tv_todays_objective = (TextView) getView().findViewById(tv_todays_objective);
                //  get_todays_objective(edit_selected_date.getText().toString());
                //tv_todays_objective.setText(Today_Obj.toString());
            } else {
                Toast.makeText(getContext(), "PLEASE Try Again...", Toast.LENGTH_SHORT).show();
            }

        /*TextView name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);
        name_doctor_of_TBM.setText(id.toString());*/
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_todays_objective() {
        try {
            GPSTracker gps = new GPSTracker(getContext(), fragment_DashBoard.this, false);
            if (gps.canGetLocation()) {

                lat = gps.getLatitude();
                lon = gps.getLongitude();

                Fragment frag = new fragment_daily_plan_insert_temp1();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack("plan_day");
                ft.commit();

            } else {
                gps.showSettingsAlert();
            }
            /*Bundle bundle = new Bundle();
            bundle.putString("nav_bar", "dash");

            popup_todays_objective_new_DialogFragment dialog = popup_todays_objective_new_DialogFragment.newInstance("Hello world");
            dialog.setArguments(bundle);
            //dialog.setView(layout);
            dialog.setTargetFragment(fragment_DashBoard.this, 300);
            dialog.show(getFragmentManager(), "fdf");*/
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFinishFromDateToDateDialog(String empid, String from_date, String to_date) {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String userID = app_preferences.getString("name", "default");
            String designation = app_preferences.getString("designation", "default");
            if (!userID.isEmpty() && userID != null && !designation.isEmpty() && designation != null && from_date != "" && from_date != null && to_date != "" && to_date != null) {
                Show_summary_report(userID, designation, from_date, to_date);
            } else {
                Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void show_dialog_for_datewise_reporting_summary() {
        try {
            datewise_reporting_summary_FragmentDialog dialog = datewise_reporting_summary_FragmentDialog.newInstance("Hello world");
            //dialog.setView(layout);
            final Bundle bundle = new Bundle();
            bundle.putString("rpt", "dash");
            //dialog.setView(layout);
            dialog.setArguments(bundle);
            dialog.setTargetFragment(fragment_DashBoard.this, 300);
            dialog.show(getFragmentManager(), "fdf");
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Show_summary_report(String userID, String designation, String from_date, String to_date) {

        try {
            View v = getView();
            pDialog = new ProgressDialog(getContext());

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);


            pDialog.show();
            ServerAPI api = ServerAPI.retrofit.create(ServerAPI.class);

            api.downlload("http://13.126.122.12/MYSQLConnTest/?user_id=" + userID + "&designation=" + designation + "&from_date=" + from_date + "&to_date=" + to_date + "&parameter=and").enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    try {


                        pDialog.hide();
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                        StrictMode.setThreadPolicy(policy);
                        if (hasPermissions()) {
                            // our app has permissions.
                            // makeFolder();
                            File path = Environment.getExternalStorageDirectory();
                            File file = new File(path, "lysten_summary.pdf");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            IOUtils.write(response.body().bytes(), fileOutputStream);
                            fileOutputStream.close();


                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            Intent intent1 = Intent.createChooser(intent, "Open With");
                            try {
                                startActivity(intent1);
                            } catch (ActivityNotFoundException ex) {
                                // Instruct the user to install a PDF reader here, or something
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }


                            /*WebView mWebView = (WebView) getView().findViewById(R.id.webview);
                            mWebView.getSettings().setJavaScriptEnabled(true);
                            String url = "http://docs.google.com/gview?embedded=true&url=file:///"+file.getAbsolutePath();
                            mWebView.loadUrl(url);*/

                        } else {
                            //our app doesn't have permissions, So i m requesting permissions.
                            requestPerms();
                        }


                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pDialog.hide();
                }
            });





           /* String myPdfUrl = "http://che.org.il/wp-content/uploads/2016/12/pdf-sample.pdf";

            String url = "http://docs.google.com/gview?embedded=true&url=" + myPdfUrl;
            // Log.i(TAG, "Opening PDF: " + url);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl(url);*/


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
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

        if (allowed) {
            //user granted all permissions we can perform our task.
            makeFolder();
        } else {
            // we will give warning to user that they haven't granted permissions.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getContext(), "Storage Permissions denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void makeFolder() {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "EDABlogs");

            if (!file.exists()) {
                Boolean ff = file.mkdir();
                if (ff) {
                    Toast.makeText(getContext(), "Folder created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to create folder", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getContext(), "Folder already exist", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*Section End Objective Of the Date */

    //////////////////
    ////////////////////
    ///////////////////

    private void updateTable() {
        String ss = String.valueOf(canAccessLocation());
        //ss = String.valueOf(canAccessCamera());
        //ss = String.valueOf(hasPermission(Manifest.permission.INTERNET));
        //ss = String.valueOf(canAccessContacts());
        //ss = String.valueOf(hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean canAccessLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean canAccessCamera() {
        return (hasPermission(Manifest.permission.CAMERA));
    }

    private boolean canAccessContacts() {
        return (hasPermission(Manifest.permission.READ_CONTACTS));
    }

    private boolean hasPermission(String perm) {
        if (Build.VERSION.SDK_INT >= 23)//Build.VERSION_CODES.M
            return (PackageManager.PERMISSION_GRANTED == getActivity().checkSelfPermission(perm));
        else
            return (PackageManager.PERMISSION_GRANTED == getActivity().checkSelfPermission(perm));
    }


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

    /*Error generated after locked mobile screen but Not important method objective allready get in loaddashboard()*/
    public void get_todays_objective(String dd) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String user_id = app_preferences.getString("name", "default");

            JSONArray jsonArray = new JSONArray();

            /*jsonArray.put("name");
            jsonArray.put("select_date");
            jsonArray.put("objective");*/


            //jsonArray.put("modified_by");
            //jsonArray.put("name");
            //jsonArray.put("creation");
            //jsonArray.put("modified");
            jsonArray.put("select_date");
            //jsonArray.put("idx");
            jsonArray.put("objective");
            jsonArray.put("user");
            //jsonArray.put("owner");
            //jsonArray.put("docstatus");
            jsonArray.put("user_name");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();

            /*Filter1.put("Objective");
            Filter1.put("owner");
            Filter1.put("=");
            Filter1.put(emp_email_id.getText());*/

            Filter1.put("Objective");
            Filter1.put("user");
            Filter1.put("=");
            Filter1.put(user_id);


            Filter2.put("Objective");
            Filter2.put("select_date");
            Filter2.put("=");
            Filter2.put(dd);

            Filters.put(Filter1);
            Filters.put(Filter2);

            /*Tour Plan Exclude Filter
            JSONArray Filter3 = new JSONArray();
            Filter3.put("Objective");
            Filter3.put("tp_flag");
            Filter3.put("=");
            Filter3.put(0);
            Filters.put(Filter3);*/

            restService.getService().getObjective(sid, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j22 = j1.getAsJsonArray("data");

                        if (j22.size() > 0) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<POJO_objective>>() {
                            }.getType();
                            List<POJO_objective> POJO = gson.fromJson(j22, type);

                            if (POJO.size() > 0) {

                                POJO_objective firstA = POJO.get(0);
                                tv_todays_objective = (TextView) getView().findViewById(R.id.tv_todays_objective);
                                String ss = firstA.getObjective().toString().trim();
                                tv_todays_objective.setText(firstA.getObjective().toString().trim());
                            }
                        } else {

                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
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
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*


    void getLocation() {
        try {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = Double.valueOf(location.getLatitude());
        lon = Double.valueOf(location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Toast.makeText(getContext(), "Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude() + "\n" + addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        gps_enable_flag = 1;
        Toast.makeText(getContext(), "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        String ss = provider + status;
    }

    @Override
    public void onProviderEnabled(String provider) {
        gps_enable_flag = 0;
        getLocation();
    }

    */


}
