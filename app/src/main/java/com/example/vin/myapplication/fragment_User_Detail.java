package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.Async_Class_Load_User_in_Realm.status_async_user;
import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

//import static com.example.vin.myapplication.DashBord_main.task2;
//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;
//import static com.example.vin.myapplication.DashBord_main.task_Employee;
//import static com.example.vin.myapplication.DashBord_main.task_Patch_Master;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_User_Detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_User_Detail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tv_employee_code;
    TextView tv_first_name;
    TextView tv_middle_name;
    TextView tv_last_name;
    TextView tv_email;
    TextView tv_designation;
    TextView tv_branch;

    EditText txt_birthdate;
    EditText txt_address;
    EditText txt_state;
    EditText txt_district;
    EditText txt_city;
    EditText txt_taluka;
    EditText txt_pin_code;
    EditText txt_mobile_number1;
    EditText txt_mobile_number2;
    EditText txt_bank_name;
    EditText txt_ifsccode;
    EditText txt_bank_ac_no;
    EditText txt_panno;
    EditText txt_aadhar_card;

    TextView tv_zone_name;
    TextView tv_region_name;
    TextView tv_area_name;
    TextView tv_headquarter;
    TextView tv_sm_name;
    TextView tv_zbm_name;
    TextView tv_crm_name;
    TextView tv_rbm_name;
    TextView tv_abm_name;
    TextView tv_usertype;
    TextView tv_stockiest;

    TextView tv_lbl_sm_name;
    TextView tv_lbl_zbm_name;
    TextView tv_lbl_crm_name;
    TextView tv_lbl_rbm_name;
    TextView tv_lbl_abm_name;
    TextView tv_lbl_stockiest;

    Button btn_save;

    RestService restService;
    private Realm mRealm;
    private ProgressDialog pDialog;
    private OnFragmentInteractionListener mListener;
    public String name;
    Bundle bundle;

    public fragment_User_Detail() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_User_Detail newInstance(String param1, String param2) {
        fragment_User_Detail fragment = new fragment_User_Detail();
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
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        return view;
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
    public void onStart() {
        try {
            super.onStart();

            pDialog = new ProgressDialog(getContext());
            init_controls();
            Load_User();

            ((DashBord_main) getActivity()).setActionBarTitle("USER DETAILS");
            ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        pDialog.show();
                        Load_User();
                        methodThatStartsTheAsyncTask();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void init_controls() {
        try {

            tv_email = (TextView) getView().findViewById(R.id.tv_email);

            tv_email = (TextView) getView().findViewById(R.id.tv_email);

            tv_employee_code = (TextView) getView().findViewById(R.id.tv_employee_code);

            tv_first_name = (TextView) getView().findViewById(R.id.tv_first_name);

            tv_middle_name = (TextView) getView().findViewById(R.id.tv_middle_name);

            tv_last_name = (TextView) getView().findViewById(R.id.tv_last_name);

            tv_designation = (TextView) getView().findViewById(R.id.tv_designation);

            tv_branch = (TextView) getView().findViewById(R.id.tv_branch);

            tv_zone_name = (TextView) getView().findViewById(R.id.tv_zone_name);

            tv_region_name = (TextView) getView().findViewById(R.id.tv_region_name);

            tv_area_name = (TextView) getView().findViewById(R.id.tv_area_name);

            tv_headquarter = (TextView) getView().findViewById(R.id.tv_headquarter);


            tv_lbl_sm_name = (TextView) getView().findViewById(R.id.tv_lbl_sm_name);
            tv_lbl_zbm_name = (TextView) getView().findViewById(R.id.tv_lbl_zbm_name);
            tv_lbl_crm_name = (TextView) getView().findViewById(R.id.tv_lbl_crm_name);
            tv_lbl_rbm_name = (TextView) getView().findViewById(R.id.tv_lbl_rbm_name);
            tv_lbl_abm_name = (TextView) getView().findViewById(R.id.tv_lbl_abm_name);
            tv_lbl_stockiest = (TextView) getView().findViewById(R.id.tv_lbl_stockiest);

            tv_sm_name = (TextView) getView().findViewById(R.id.tv_sm_name);
            tv_zbm_name = (TextView) getView().findViewById(R.id.tv_zbm_name);
            tv_crm_name = (TextView) getView().findViewById(R.id.tv_crm_name);
            tv_rbm_name = (TextView) getView().findViewById(R.id.tv_rbm_name);
            tv_abm_name = (TextView) getView().findViewById(R.id.tv_abm_name);
            tv_usertype = (TextView) getView().findViewById(R.id.tv_usertype);
            tv_stockiest = (TextView) getView().findViewById(R.id.tv_stockiest);

            txt_birthdate = (EditText) getView().findViewById(R.id.txt_birthdate);
            // marital_status.setText( app_preferences.getString("marital_status", "default"));

            txt_address = (EditText) getView().findViewById(R.id.txt_address);

            txt_state = (EditText) getView().findViewById(R.id.txt_state);

            txt_district = (EditText) getView().findViewById(R.id.txt_district);

            txt_city = (EditText) getView().findViewById(R.id.txt_city);

            /*txt_taluka = (EditText) getView().findViewById(R.id.txt_taluka);
            txt_taluka.setText(result_query.getAddress());*/

            txt_pin_code = (EditText) getView().findViewById(R.id.txt_pin_code);

            txt_mobile_number1 = (EditText) getView().findViewById(R.id.txt_mobile_number1);

            txt_mobile_number2 = (EditText) getView().findViewById(R.id.txt_mobile_number2);

            txt_city = (EditText) getView().findViewById(R.id.txt_city);

            txt_bank_name = (EditText) getView().findViewById(R.id.txt_bank_name);

            txt_ifsccode = (EditText) getView().findViewById(R.id.txt_ifsccode);

            txt_bank_ac_no = (EditText) getView().findViewById(R.id.txt_bank_ac_no);

            txt_panno = (EditText) getView().findViewById(R.id.txt_panno);

            txt_aadhar_card = (EditText) getView().findViewById(R.id.txt_aadhar_card);

            btn_save = (Button) getView().findViewById(R.id.btn_save);

            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        /*Call Locked Check Method*/
                        lock_check();
                        /*Call Locked Check Method*/

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Load_User() {
        try {
            pDialog.show();
            //Thread.sleep(sleep_wait);
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            final String user_id = app_preferences.getString("name", "default");
            JSONArray jsonArray = new JSONArray();

            jsonArray.put("full_name");
            jsonArray.put("owner");
            jsonArray.put("last_known_versions");
            jsonArray.put("thread_notify");
            jsonArray.put("first_name");
            jsonArray.put("modified_by");
            jsonArray.put("background_style");
            jsonArray.put("district");
            jsonArray.put("area");
            jsonArray.put("employee_code");
            jsonArray.put("new_password");
            jsonArray.put("pan_no");
            jsonArray.put("rbm");
            jsonArray.put("rbm_name");
            jsonArray.put("designation");
            jsonArray.put("branch");
            jsonArray.put("ifsc_code");
            jsonArray.put("idx");
            jsonArray.put("state");
            jsonArray.put("last_login");
            jsonArray.put("unsubscribed");
            jsonArray.put("lock_enable");
            jsonArray.put("docstatus");
            jsonArray.put("zone");
            jsonArray.put("type");
            jsonArray.put("email");
            jsonArray.put("bank_account_no");
            jsonArray.put("username");
            jsonArray.put("last_ip");
            jsonArray.put("sm");
            jsonArray.put("sm_name");
            jsonArray.put("city");
            jsonArray.put("zbm");
            jsonArray.put("zbm_name");

            jsonArray.put("user_type");
            jsonArray.put("aadhar_card_no");
            jsonArray.put("address");
            jsonArray.put("pincode");
            jsonArray.put("login_after");

            jsonArray.put("send_welcome_email");
                /*jsonArray.put("doctype");*/
            jsonArray.put("name");
            jsonArray.put("language");
            jsonArray.put("gender");
            jsonArray.put("region");

            jsonArray.put("login_before");
            jsonArray.put("enabled");
            jsonArray.put("modified");
            jsonArray.put("mobile_no1");
            jsonArray.put("frappe_userid");
            jsonArray.put("mobile_no2");
            jsonArray.put("crm");
            jsonArray.put("crm_name");
            jsonArray.put("user_image");
            jsonArray.put("last_name");
            jsonArray.put("last_active");
            jsonArray.put("bank_name");
            jsonArray.put("headquarter");

            jsonArray.put("simultaneous_sessions");
            jsonArray.put("abm");
            jsonArray.put("abm_name");
            jsonArray.put("creation");
            jsonArray.put("send_password_update_notification");
            jsonArray.put("mute_sounds");
            jsonArray.put("middle_name");

            jsonArray.put("zone_name");
            jsonArray.put("region_name");
            jsonArray.put("area_name");
            jsonArray.put("headquarter_name");
            jsonArray.put("birth_date");
            //jsonArray.put("stockiest");

            //  JSONObject ofilter= new JSONObject();
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();
            //JSONArray Filter3 = new JSONArray();


            Filter1.put("User");
            Filter1.put("enabled");
            Filter1.put("=");
            Filter1.put(1);

            Filter2.put("User");
            Filter2.put("name");
            Filter2.put("=");
            Filter2.put(user_id);


            //  ofilter.
            Filters.put(Filter1);
            Filters.put(Filter2);
            // Log.i("Success ","out:"+limitstart);

       /*  restService.getService().getLIVE_WORK(sid,20,jsonArray,Filters, new Callback<JsonElement>() {
*/
            restService.getService().getUser(sid, 0, 1, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_User>>() {
                        }.getType();
                        List<POJO_User> POJO = gson.fromJson(j2, type);


                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();

                        mRealm.close();
                        if (user_id.toString().length() > 0) {
                            Bind__data(user_id);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        status_async_user = "error"; //  success,error,loading
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
                        if (task_user != null) {
                            task_user.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            pDialog.hide();
            status_async_user = "error"; //  success,error,loading
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Bind__data(String name) {
        try {
            Realm mRealm = Realm.getDefaultInstance();
            final POJO_User result_query = mRealm.where(POJO_User.class).equalTo("name", name).findFirst();

            //btn_save=(Button) getView().findViewById(R.id.btn_save);
            //tv_email = (TextView) getView().findViewById(R.id.tv_email);
            tv_email.setText(result_query.getEmail());

            //tv_employee_code = (TextView) getView().findViewById(R.id.tv_employee_code);
            tv_employee_code.setText(result_query.getEmployee_code());

            //tv_first_name = (TextView) getView().findViewById(R.id.tv_first_name);
            tv_first_name.setText(result_query.getFirst_name());

            //tv_middle_name = (TextView) getView().findViewById(R.id.tv_middle_name);
            tv_middle_name.setText(result_query.getMiddle_name());

            //tv_last_name = (TextView) getView().findViewById(R.id.tv_last_name);
            tv_last_name.setText(result_query.getLast_name());

            //tv_designation = (TextView) getView().findViewById(R.id.tv_designation);
            tv_designation.setText(result_query.getDesignation());

            tv_branch.setText(result_query.getBranch());

            //tv_zone_name = (TextView) getView().findViewById(R.id.tv_zone_name);
            tv_zone_name.setText(result_query.getZone_name() + " (" + result_query.getZone() + ")");

            //tv_region_name = (TextView) getView().findViewById(R.id.tv_region_name);
            tv_region_name.setText(result_query.getRegion_name() + " (" + result_query.getRegion() + ")");

            //tv_area_name = (TextView) getView().findViewById(R.id.tv_area_name);
            tv_area_name.setText(result_query.getArea_name() + " (" + result_query.getArea() + ")");

            //tv_headquarter = (TextView) getView().findViewById(R.id.tv_headquarter);
            tv_headquarter.setText(result_query.getHeadquarter_name() + " (" + result_query.getHeadquarter() + ")");


            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            final String designation = app_preferences.getString("designation", "default");
            SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("zone", result_query.getZone());
            editor.putString("region", result_query.getRegion());
            editor.putString("area", result_query.getArea());
            editor.putString("headquarter", result_query.getHeadquarter());

            editor.commit();

            if (designation.equals("Stockiest Boy") || designation.equals("Stockiest")) {
                tv_sm_name.setVisibility(View.GONE);
                tv_zbm_name.setVisibility(View.GONE);
                tv_crm_name.setVisibility(View.GONE);
                tv_rbm_name.setVisibility(View.GONE);
                tv_abm_name.setVisibility(View.GONE);

                tv_lbl_sm_name.setVisibility(View.GONE);
                tv_lbl_zbm_name.setVisibility(View.GONE);
                tv_lbl_crm_name.setVisibility(View.GONE);
                tv_lbl_rbm_name.setVisibility(View.GONE);
                tv_lbl_abm_name.setVisibility(View.GONE);

                //tv_stockiest = (TextView) getView().findViewById(R.id.tv_stockiest);
                tv_lbl_stockiest.setVisibility(View.VISIBLE);
                tv_stockiest.setVisibility(View.VISIBLE);
                tv_stockiest.setText(result_query.getStockiest());
            } else if (designation.equals("SM")) {
                tv_stockiest.setVisibility(View.GONE);
                tv_lbl_stockiest.setVisibility(View.GONE);

                tv_sm_name.setVisibility(View.GONE);
                tv_zbm_name.setVisibility(View.GONE);
                tv_crm_name.setVisibility(View.GONE);
                tv_rbm_name.setVisibility(View.GONE);
                tv_abm_name.setVisibility(View.GONE);

                tv_lbl_sm_name.setVisibility(View.GONE);
                tv_lbl_zbm_name.setVisibility(View.GONE);
                tv_lbl_crm_name.setVisibility(View.GONE);
                tv_lbl_rbm_name.setVisibility(View.GONE);
                tv_lbl_abm_name.setVisibility(View.GONE);

            } else if (designation.equals("ZONE")) {
                tv_stockiest.setVisibility(View.GONE);
                tv_lbl_stockiest.setVisibility(View.GONE);

                tv_sm_name.setVisibility(View.VISIBLE);
                tv_zbm_name.setVisibility(View.GONE);
                tv_crm_name.setVisibility(View.GONE);
                tv_rbm_name.setVisibility(View.GONE);
                tv_abm_name.setVisibility(View.GONE);

                tv_lbl_sm_name.setVisibility(View.VISIBLE);
                tv_lbl_zbm_name.setVisibility(View.GONE);
                tv_lbl_crm_name.setVisibility(View.GONE);
                tv_lbl_rbm_name.setVisibility(View.GONE);
                tv_lbl_abm_name.setVisibility(View.GONE);

                tv_sm_name.setText(result_query.getSm_name() + " (" + result_query.getSm() + ")");
            } else if (designation.equals("CRM")) {
                tv_stockiest.setVisibility(View.GONE);
                tv_lbl_stockiest.setVisibility(View.GONE);

                tv_sm_name.setVisibility(View.VISIBLE);
                tv_zbm_name.setVisibility(View.VISIBLE);
                tv_crm_name.setVisibility(View.GONE);
                tv_rbm_name.setVisibility(View.GONE);
                tv_abm_name.setVisibility(View.GONE);

                tv_lbl_sm_name.setVisibility(View.VISIBLE);
                tv_lbl_zbm_name.setVisibility(View.VISIBLE);
                tv_lbl_crm_name.setVisibility(View.GONE);
                tv_lbl_rbm_name.setVisibility(View.GONE);
                tv_lbl_abm_name.setVisibility(View.GONE);
                tv_sm_name.setText(result_query.getSm_name() + " (" + result_query.getSm() + ")");
                tv_zbm_name.setText(result_query.getZbm_name() + " (" + result_query.getZbm() + ")");
            } else if (designation.equals("RBM")) {
                tv_stockiest.setVisibility(View.GONE);
                tv_lbl_stockiest.setVisibility(View.GONE);

                tv_sm_name.setVisibility(View.VISIBLE);
                tv_zbm_name.setVisibility(View.VISIBLE);
                tv_crm_name.setVisibility(View.VISIBLE);
                tv_rbm_name.setVisibility(View.GONE);
                tv_abm_name.setVisibility(View.GONE);

                tv_lbl_sm_name.setVisibility(View.VISIBLE);
                tv_lbl_zbm_name.setVisibility(View.VISIBLE);
                tv_lbl_crm_name.setVisibility(View.VISIBLE);
                tv_lbl_rbm_name.setVisibility(View.GONE);
                tv_lbl_abm_name.setVisibility(View.GONE);

                tv_sm_name.setText(result_query.getSm_name() + " (" + result_query.getSm() + ")");
                tv_zbm_name.setText(result_query.getZbm_name() + " (" + result_query.getZbm() + ")");
                tv_crm_name.setText(result_query.getCrm_name() + " (" + result_query.getCrm() + ")");
            } else if (designation.equals("ABM")) {
                tv_stockiest.setVisibility(View.GONE);
                tv_lbl_stockiest.setVisibility(View.GONE);

                tv_sm_name.setVisibility(View.VISIBLE);
                tv_zbm_name.setVisibility(View.VISIBLE);
                tv_crm_name.setVisibility(View.VISIBLE);
                tv_rbm_name.setVisibility(View.VISIBLE);
                tv_abm_name.setVisibility(View.GONE);

                tv_lbl_sm_name.setVisibility(View.VISIBLE);
                tv_lbl_zbm_name.setVisibility(View.VISIBLE);
                tv_lbl_crm_name.setVisibility(View.VISIBLE);
                tv_lbl_rbm_name.setVisibility(View.VISIBLE);
                tv_lbl_abm_name.setVisibility(View.GONE);

                tv_sm_name.setText(result_query.getSm_name() + " (" + result_query.getSm() + ")");
                tv_zbm_name.setText(result_query.getZbm_name() + " (" + result_query.getZbm() + ")");
                tv_crm_name.setText(result_query.getCrm_name() + " (" + result_query.getCrm() + ")");
                tv_rbm_name.setText(result_query.getRbm_name() + " (" + result_query.getRbm() + ")");

            } else if (designation.equals("TBM") || designation.equals("KBM")) {
                tv_stockiest.setVisibility(View.GONE);
                tv_lbl_stockiest.setVisibility(View.GONE);

                tv_sm_name.setVisibility(View.VISIBLE);
                tv_zbm_name.setVisibility(View.VISIBLE);
                tv_crm_name.setVisibility(View.VISIBLE);
                tv_rbm_name.setVisibility(View.VISIBLE);
                tv_abm_name.setVisibility(View.VISIBLE);

                tv_lbl_sm_name.setVisibility(View.VISIBLE);
                tv_lbl_zbm_name.setVisibility(View.VISIBLE);
                tv_lbl_crm_name.setVisibility(View.VISIBLE);
                tv_lbl_rbm_name.setVisibility(View.VISIBLE);
                tv_lbl_abm_name.setVisibility(View.VISIBLE);

                //tv_sm_name = (TextView) getView().findViewById(R.id.tv_sm_name);
                tv_sm_name.setText(result_query.getSm_name() + " (" + result_query.getSm() + ")");

                //tv_zbm_name = (TextView) getView().findViewById(R.id.tv_zbm_name);
                tv_zbm_name.setText(result_query.getZbm_name() + " (" + result_query.getZbm() + ")");

                //tv_crm_name = (TextView) getView().findViewById(R.id.tv_crm_name);
                tv_crm_name.setText(result_query.getCrm_name() + " (" + result_query.getCrm() + ")");

                //tv_rbm_name = (TextView) getView().findViewById(R.id.tv_rbm_name);
                tv_rbm_name.setText(result_query.getRbm_name() + " (" + result_query.getRbm() + ")");

                //tv_abm_name = (TextView) getView().findViewById(R.id.tv_abm_name);
                tv_abm_name.setText(result_query.getAbm_name() + " (" + result_query.getAbm() + ")");
            }

            //tv_usertype = (TextView) getView().findViewById(R.id.tv_usertype);
            tv_usertype.setText(result_query.getUser_type());

            //tv_stockiest = (TextView) getView().findViewById(R.id.tv_stockiest);
            //tv_stockiest.setText(  result_query.getStockiest() );

            txt_birthdate.setText(result_query.getBirth_date());
            // marital_status.setText( app_preferences.getString("marital_status", "default"));

            //txt_address = (EditText) getView().findViewById(R.id.txt_address);
            txt_address.setText(result_query.getAddress());

            //txt_state = (EditText) getView().findViewById(R.id.txt_state);
            txt_state.setText(result_query.getState());

            //txt_district = (EditText) getView().findViewById(R.id.txt_district);
            txt_district.setText(result_query.getDistrict());

            //txt_city = (EditText) getView().findViewById(R.id.txt_city);
            txt_city.setText(result_query.getCity());

            /*txt_taluka = (EditText) getView().findViewById(R.id.txt_taluka);
            txt_taluka.setText(result_query.getAddress());*/

            //txt_pin_code = (EditText) getView().findViewById(R.id.txt_pin_code);
            txt_pin_code.setText(result_query.getPincode());

            //txt_mobile_number1 = (EditText) getView().findViewById(R.id.txt_mobile_number1);
            txt_mobile_number1.setText(result_query.getMobile_no1());

            //txt_mobile_number2 = (EditText) getView().findViewById(R.id.txt_mobile_number2);
            txt_mobile_number2.setText(result_query.getMobile_no2());

            //txt_city = (EditText) getView().findViewById(R.id.txt_city);
            txt_city.setText(result_query.getCity());

            //txt_bank_name = (EditText) getView().findViewById(R.id.txt_bank_name);
            txt_bank_name.setText(result_query.getBank_name());

            //txt_ifsccode = (EditText) getView().findViewById(R.id.txt_ifsccode);
            txt_ifsccode.setText(result_query.getIfsc_code());

            //txt_bank_ac_no = (EditText) getView().findViewById(R.id.txt_bank_ac_no);
            txt_bank_ac_no.setText(result_query.getBank_account_no());

            //txt_panno = (EditText) getView().findViewById(R.id.txt_panno);
            txt_panno.setText(result_query.getPan_no());

            //txt_aadhar_card = (EditText) getView().findViewById(R.id.txt_aadhar_card);
            txt_aadhar_card.setText(result_query.getAadhar_card_no());


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
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

    public void update_user(POJO_User POJO) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String name = app_preferences.getString("name", "default");

            restService.getService().putUser(sid, POJO, name, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        Load_User();
                        update_user_master_class(jsonElement);
                        pDialog.hide();
                        Toast.makeText(getContext(), "USER UPDATE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();
                        Toast.makeText(getContext(), "PLEASE ENTER VALID DATA", Toast.LENGTH_SHORT).show();
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

    public void update_user_master_class(JsonElement jsonElement) {
        try {
            JsonObject j1 = jsonElement.getAsJsonObject();
            //   JsonArray j2 = j1.getAsJsonArray("data");
            JsonElement j2 = j1.getAsJsonObject("data");
            Gson gson = new Gson();
            Type type = new TypeToken<POJO_User>() {
            }.getType();
            POJO_User POJO = gson.fromJson(j2, type);

            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(POJO);
            mRealm.commitTransaction();
            mRealm.close();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public Boolean Validation(POJO_User POJO) {
        try {
            if (POJO.getAddress().toString().trim().length() < 5) {
                Toast.makeText(getContext(), "ADDRESS IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 5", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getState().toString().trim().length() < 3) {
                Toast.makeText(getContext(), "STATE IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 5", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getDistrict().toString().trim().length() < 3) {
                Toast.makeText(getContext(), "DISTRICT IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 2 ", Toast.LENGTH_SHORT).show();
                return false;
            }
           /* else if (POJO.getTaluka().toString().trim().length() < 3) {
                Toast.makeText(getContext(), "Taluka IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 2 ", Toast.LENGTH_SHORT).show();
                return false;
            }*/
            else if (POJO.getCity().toString().trim().length() < 2) {
                Toast.makeText(getContext(), "CITY IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 2 ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getPincode().toString().trim().length() < 5) {
                Toast.makeText(getContext(), "PINCODE IS NOT EMPTY  ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getMobile_no1().toString().trim().length() < 9) {
                Toast.makeText(getContext(), "PHONE1 IS NOT EMPTY  AND LENGHT MUST BE GRATER THAN 9 ", Toast.LENGTH_SHORT).show();
                return false;

            } else if (POJO.getBank_name().toString().trim().length() < 3) {
                Toast.makeText(getContext(), "BANK NAME IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 2 ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getBank_account_no().toString().trim().length() < 5) {
                Toast.makeText(getContext(), "BANK ACCOUNT NO IS NOT EMPTY  ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getPan_no().toString().trim().length() < 9) {
                Toast.makeText(getContext(), "PAN NO IS NOT EMPTY  AND LENGHT MUST BE GRATER THAN 9 ", Toast.LENGTH_SHORT).show();
                return false;

            } else if (POJO.getAadhar_card_no().toString().trim().length() < 9) {
                Toast.makeText(getContext(), "AADHAR CARD IS NOT EMPTY  AND LENGHT MUST BE GRATER THAN 9 ", Toast.LENGTH_SHORT).show();
                return false;

            } /*else if (POJO.getEmployee_code().toString().trim().length() < 1) {
                Toast.makeText(getContext(), "NO EMPLOYEE CODE YET" +
                        "PLEASE CONATACT LYSTEN GLOBAL IT SUPPORT ", Toast.LENGTH_SHORT).show();
                return false;
            } */ else
                return true;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
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

            restService.getService().getMasterFormLockOrNot(sid, "'" + name + "'", "profile", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        JsonObject j1 = jsonElement.getAsJsonObject();/*-----*/
                        JsonObject j2 = j1.getAsJsonObject("message");

                        String lock_flag = j2.get("lock_flag").getAsString();
                        String message = j2.get("message").getAsString();
                        /*JsonObject j1 = jsonElement.getAsJsonObject();
                        String lock_flag = j1.get("message").getAsString();*/
                        if (lock_flag.equals("0")) {
                            save_profile();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();//"Oops !!! Locked Profile..."
                        }

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

    private void save_profile() {
        try {
            /*This code Cut From button Click Event*/
            Bundle bundle = new Bundle();

                    /*POJO.setDegree(spinner_degree.getSelectedItem().toString());
                    POJO.setDoctor_specialization(spinner_doctor_specialization.getSelectedItem().toString());
                    POJO.setDoctor_type(spinner_doctor_type.getSelectedItem().toString());*/

            bundle.putString("First_Name", tv_first_name.getText().toString());
            bundle.putString("Middle_Name", tv_middle_name.getText().toString());
            bundle.putString("Last_Name", tv_last_name.getText().toString());
            bundle.putString("Designation", tv_designation.getText().toString());
            bundle.putString("EmployeeCode", tv_employee_code.getText().toString());
            bundle.putString("UserType", tv_usertype.getText().toString());
            //bundle.putString("LockEnable", tv_first_name.getText().toString());
            bundle.putString("Email", tv_email.getText().toString());
            bundle.putString("Address", txt_address.getText().toString());
            bundle.putString("State", txt_state.getText().toString());
            bundle.putString("District", txt_district.getText().toString());
            bundle.putString("City", txt_city.getText().toString());
            bundle.putString("Pin_code", txt_pin_code.getText().toString());
            bundle.putString("mobile1", txt_mobile_number1.getText().toString());
            bundle.putString("mobile2", txt_mobile_number2.getText().toString());

            bundle.putString("BankName", txt_bank_name.getText().toString());
            bundle.putString("IFSCCode", txt_ifsccode.getText().toString());
            bundle.putString("BankAccNo", txt_bank_ac_no.getText().toString());
            bundle.putString("PanNo", txt_panno.getText().toString());
            bundle.putString("AadharCardNo", txt_aadhar_card.getText().toString());
            bundle.putString("Zone", tv_zone_name.getText().toString());
            bundle.putString("Region", tv_region_name.getText().toString());
            bundle.putString("Area", tv_area_name.getText().toString());
            bundle.putString("HQ", tv_headquarter.getText().toString());

            bundle.putString("SM", tv_sm_name.getText().toString());
            bundle.putString("ZBM", tv_zbm_name.getText().toString());
            bundle.putString("CRM", tv_crm_name.getText().toString());
            bundle.putString("RBM", tv_rbm_name.getText().toString());
            bundle.putString("ABM", tv_abm_name.getText().toString());

            //bundle.putString("Employee_name", txt_emp_name.getText().toString());


            POJO_User POJO = new POJO_User();


            // POJO.setName(txt_patch_id.getText().toString());

            ///POJO.setFirst_name(tv_first_name.getText().toString());
            ///POJO.setMiddle_name(tv_middle_name.getText().toString());
            ///POJO.setLast_name(tv_last_name.getText().toString());
            ///POJO.setEmail(tv_email.getText().toString());
            POJO.setBirth_date(txt_birthdate.getText().toString());
            POJO.setAddress(txt_address.getText().toString());
            POJO.setState(txt_state.getText().toString());
            POJO.setDistrict(txt_district.getText().toString());

            // no this field POJO.setTaluka(txt_taluka.getText().toString());

            POJO.setCity(txt_city.getText().toString());
            POJO.setPincode(txt_pin_code.getText().toString());
            POJO.setMobile_no1(txt_mobile_number1.getText().toString());
            POJO.setMobile_no2(txt_mobile_number2.getText().toString());

            POJO.setBank_name(txt_bank_name.getText().toString());
            POJO.setBank_account_no(txt_bank_ac_no.getText().toString());
            POJO.setIfsc_code(txt_ifsccode.getText().toString());
            POJO.setPan_no(txt_panno.getText().toString());
            POJO.setAadhar_card_no(txt_aadhar_card.getText().toString());
            POJO.setEmployee_code(tv_employee_code.getText().toString());

            ///POJO.setUser_type(tv_usertype.getText().toString());
            ///POJO.setDesignation(tv_designation.getText().toString());

            ///POJO.setZone(tv_zone_name.getText().toString());
            ///POJO.setRegion(tv_region_name.getText().toString());
            ///POJO.setArea(tv_area_name.getText().toString());
            ///POJO.setHeadquarter(tv_headquarter.getText().toString());

            ///POJO.setSm(tv_usertype.getText().toString());
            ///POJO.setZbm(tv_designation.getText().toString());
            ///POJO.setCrm(tv_zone_name.getText().toString());
            ///POJO.setRbm(tv_region_name.getText().toString());
            ///POJO.setAbm(tv_area_name.getText().toString());

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            final String designation = app_preferences.getString("designation", "default");


            if (designation == "Stockiest Boy") {
                ///POJO.setStockiest(tv_stockiest.getText().toString());
            }

            ///POJO.setStockiest(tv_stockiest.getText().toString());
            ///POJO.setLock_enable(0);//set 1
            //  pDialog.show();
            if (Validation(POJO) == true) {
                if (bundle != null) {
                    update_user(POJO);
                } else {
                    //  insert_doctor(POJO);
                    bundle.putString("name", "");
                }

            } else {
                //  pDialog.hide();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void methodThatStartsTheAsyncTask() {
        try {
            if (task_user == null) {
                task_user = new Async_Class_Load_User_in_Realm(new FragmentCallback() {

                    @Override
                    public void onTaskDone() {
                        methodThatDoesSomethingWhenTaskIsDone();

                    }
                }, getContext(), true, true, false);
                task_user.execute();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void methodThatDoesSomethingWhenTaskIsDone() {
        try {
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public interface FragmentCallback {
        public void onTaskDone();
    }

    //Transaction Form
    //http://139.59.63.181/api/method/team.masterlocking.lock_transaction_forms?employee='developer@gmail.com'&formname=T_Obj&date=2017-09-26

//Master Form
    //http://139.59.63.181/api/method/team.masterlocking.lock_master_forms?employee='developer@gmail.com'&formname=profile

}
