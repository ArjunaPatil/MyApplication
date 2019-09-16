package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_doctor_master_insert#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_doctor_master_insert extends Fragment implements doctor_degree_FragmentDialog.EditDoctorDegreeDialogListener,
        doctor_specialization_FragmentDialog.EditDoctorSpecializationDialogListener,
        doctor_type_FragmentDialog.EditDoctorTypeDialogListener,
        doctor_patch_FragmentDialog.EditDoctorInsertPacthDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Realm mRealm;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView tv_user;
    TextView tv_doc_id;
    TextView tv_user_full_name;

    private OnFragmentInteractionListener mListener;
    RestService restService;
    Button btn_save_doctor;
    EditText txt_doctor_name;
    //TextView tv_patch_name;
    TextView tv_headquarter;//////////////////

    EditText txt_hospital_name;
    EditText txt_reg_no;
    EditText txt_address;
    EditText txt_city;
    EditText txt_pin_code;
    EditText txt_per_mobile;
    EditText txt_per_phone;
    EditText txt_email;


    LinearLayout select_patch;
    LinearLayout select_patch_1;
    TextView select_patch_2;
    TextView name_select_patch;
    ImageButton select_patch_3;

    LinearLayout select_degree;
    LinearLayout select_degree_1;
    TextView select_degree_2;
    TextView name_select_degree;
    ImageButton select_degree_3;


    LinearLayout select_doctor_specialization;
    LinearLayout select_doctor_specialization_1;
    TextView select_doctor_specialization_2;
    TextView name_select_doctor_specialization;
    ImageButton select_doctor_specialization_3;

    LinearLayout select_doctor_type;
    LinearLayout select_doctor_type_1;
    TextView select_doctor_type_2;
    TextView name_select_doctor_type;
    ImageButton select_doctor_type_3;


    /*tv_user;tv_doc_id
    EditText edit_doctor_name;
    TextView txt_patch_name;
    EditText edit_hospital_name;
    EditText edit_reg_no;
    EditText edit_address;
    EditText edit_city;
    EditText edit_pin_code;
    EditText edit_per_mobile;
    EditText edit_per_phone;
    EditText edit_email;*/

    CheckBox actirab_tab;
    CheckBox actirab_d_cap;
    CheckBox actirab_dv_cap;
    CheckBox actirab_l_cap;
    CheckBox empower_od_tab;
    CheckBox stand_sp_tab;
    CheckBox star_t_tab;
    CheckBox glucolyst_g1_tab;
    CheckBox lycorest_tab;
    CheckBox lycort_1ml_inj;
    CheckBox regain_xt_tab;
    CheckBox lycorest_60ml_susp;
    CheckBox lycolic_10ml_drops;
    CheckBox stand_mf_60ml_susp;
    CheckBox ten_n_30ml_syrup;
    CheckBox wego_gel_20_mg;
    CheckBox wego_gel_30_mg;
    CheckBox trygesic_tab;
    CheckBox itezone_200_cap;
    CheckBox nextvit_tab;
    /*new branch product 28/04/2018*/
    CheckBox trygesic_ptab;
    CheckBox cipgrow_syrup;
    CheckBox clavyten_625;
    CheckBox levocast_m;
    CheckBox altipan_dsr;
    CheckBox sangria_tonic;
    CheckBox onederm_cream;
    CheckBox actirest_ls;
    CheckBox actirest_dx;
    CheckBox korby_soap;
    /*End New*/
    BranchWiseProductList return_branch_wise_product;


    //Spinner spinner_degree;
    //Spinner spinner_doctor_specialization;
    //Spinner spinner_doctor_type;

    /*
    TextView txt_emp_code;
    TextView txt_emp_name;
    TextView txt_doc_id;
    Button btn_add_doc;
    Button btn_delete_patch;
    LinearLayout linear_layout_doc;
    Button btn_add_update_doc;*/
    Bundle bundle;

    private ProgressDialog pDialog;

    private long mLastClickTime = 0;

    public fragment_doctor_master_insert() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_insert_patch_master_1.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_doctor_master_insert newInstance(String param1, String param2) {
        fragment_doctor_master_insert fragment = new fragment_doctor_master_insert();
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
        return inflater.inflate(R.layout.fragment_doctor_master_insert, container, false);
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
    public void onFinishDoctorInsertPacthDialog(String id, String PatchName) {
        try {
            TextView select_patch_2 = (TextView) getView().findViewById(R.id.select_patch_2);
            select_patch_2.setText(PatchName.toString());

            TextView name_select_patch = (TextView) getView().findViewById(R.id.name_select_patch);
            name_select_patch.setText(id.toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishDoctorDegreeDialog(String id, String DoctorDegree) {
        try {
            TextView select_degree_2 = (TextView) getView().findViewById(R.id.select_degree_2);
            select_degree_2.setText(DoctorDegree.toString());

            TextView name_select_degree = (TextView) getView().findViewById(R.id.name_select_degree);
            name_select_degree.setText(id.toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishDoctorSpecializationDialog(String id, String DoctorSpecialization) {
        try {
            TextView select_doctor_specialization_2 = (TextView) getView().findViewById(R.id.select_doctor_specialization_2);
            select_doctor_specialization_2.setText(DoctorSpecialization.toString());

            TextView name_select_doctor_specialization = (TextView) getView().findViewById(R.id.name_select_doctor_specialization);
            name_select_doctor_specialization.setText(id.toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishDoctorTypeDialog(String id, String DoctorType) {
        try {
            TextView select_doctor_type_2 = (TextView) getView().findViewById(R.id.select_doctor_type_2);
            select_doctor_type_2.setText(DoctorType.toString());

            TextView name_select_doctor_type = (TextView) getView().findViewById(R.id.name_select_doctor_type);
            name_select_doctor_type.setText(id.toString());
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

    public void onStart() {
        try {
            super.onStart();
            pDialog = new ProgressDialog(getContext());

            init_controls();


            select_patch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_patch_2.setText(" Select Patch");
                    select_patch_2.setText(" Select");
                    Clear_POJO_PATCH();
                    show_dialog_for_select_patch();

                }
            });
            select_patch_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_patch_2.setText(" Select Patch");
                    select_patch_2.setText(" Select");
                    Clear_POJO_PATCH();
                    show_dialog_for_select_patch();

                }
            });
            select_patch_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_patch_2.setText(" Select Patch");
                    select_patch_2.setText(" Select");
                    Clear_POJO_PATCH();
                    show_dialog_for_select_patch();

                }
            });
            select_patch_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_patch_2.setText(" Select Patch");
                    select_patch_2.setText(" Select");
                    Clear_POJO_PATCH();
                    show_dialog_for_select_patch();

                }
            });

            /////////////////////
            select_degree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_degree();

                }
            });
            select_degree_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_degree();

                }
            });
            select_degree_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_degree();

                }
            });
            select_degree_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_degree();

                }
            });

            ///////////////////////////////////////
            select_doctor_specialization.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_doctor_specialization();

                }
            });
            select_doctor_specialization_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_doctor_specialization();

                }
            });
            select_doctor_specialization_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_doctor_specialization();

                }
            });
            select_doctor_specialization_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_doctor_specialization();

                }
            });

            /////////////////////////////////////////
            select_doctor_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_doctor_type();

                }
            });
            select_doctor_type_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_doctor_type();
                    //show_dialog_for_select_doctor_type();

                }
            });
            select_doctor_type_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_doctor_type();
                    //show_dialog_for_select_doctor_type();

                }
            });
            select_doctor_type_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_doctor_type();
                    //show_dialog_for_select_doctor_type();

                }
            });
            ///////////////////////////////////////////////////////


            CHECK_ABM_RBM_ZBM_SM();

            //bind_spinners();
            // btn_add_doc.setText("SAVE");

            bundle = this.getArguments();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String designation = app_preferences.getString("designation", "default");
            String branch = (app_preferences.getString("branch", ""));
            products_with_comma = (app_preferences.getString("branch_product", ""));
            if (designation.equals("TBM") == true) {
                if (!products_with_comma.equals("") || !products_with_comma.equals(null)) {
                    visibility_of_product();
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
                //get_branch_of_user(name);
                //return_branch_wise_product = new BranchWiseProductList(getContext());
                if (designation.equals("TBM") == false) {
                    get_branch_of_user(name);
                }
                ((DashBord_main) getActivity()).setActionBarTitle("UPDATE DOCTOR");
                btn_save_doctor.setText("UPDATE");
                ///////edit_fill();02/05/2018

            } else {
                //final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String prodcut_comma = app_preferences.getString("branch_product", "");
                //get_branch_of_user(app_preferences.getString("name", "-"));

                ((DashBord_main) getActivity()).setActionBarTitle("ADD DOCTOR");
                btn_save_doctor.setText("SAVE");

                tv_user.setText(app_preferences.getString("name", "default"));
                tv_user_full_name.setText(app_preferences.getString("first_name", "-") + " " + app_preferences.getString("middle_name", "-") + " " + app_preferences.getString("last_name", "-"));
                //tv_headquarter.setText(app_preferences.getString("headquarter", "default"));
            }

            btn_save_doctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        lock_check();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void init_controls() {
        try {

            select_degree = (LinearLayout) getView().findViewById(R.id.select_degree);
            select_degree_1 = (LinearLayout) getView().findViewById(R.id.select_degree_1);
            select_degree_3 = (ImageButton) getView().findViewById(R.id.select_degree_3);
            select_degree_2 = (TextView) getView().findViewById(R.id.select_degree_2);
            name_select_degree = (TextView) getView().findViewById(R.id.name_select_degree);

            select_doctor_specialization = (LinearLayout) getView().findViewById(R.id.select_doctor_specialization);
            select_doctor_specialization_1 = (LinearLayout) getView().findViewById(R.id.select_doctor_specialization_1);
            select_doctor_specialization_3 = (ImageButton) getView().findViewById(R.id.select_doctor_specialization_3);
            select_doctor_specialization_2 = (TextView) getView().findViewById(R.id.select_doctor_specialization_2);
            name_select_doctor_specialization = (TextView) getView().findViewById(R.id.name_select_doctor_specialization);

            select_doctor_type = (LinearLayout) getView().findViewById(R.id.select_doctor_type);
            select_doctor_type_1 = (LinearLayout) getView().findViewById(R.id.select_doctor_type_1);
            select_doctor_type_3 = (ImageButton) getView().findViewById(R.id.select_doctor_type_3);
            select_doctor_type_2 = (TextView) getView().findViewById(R.id.select_doctor_type_2);
            name_select_doctor_type = (TextView) getView().findViewById(R.id.name_select_doctor_type);


            select_patch = (LinearLayout) getView().findViewById(R.id.select_patch);
            select_patch_1 = (LinearLayout) getView().findViewById(R.id.select_patch_1);
            select_patch_3 = (ImageButton) getView().findViewById(R.id.select_patch_3);
            select_patch_2 = (TextView) getView().findViewById(R.id.select_patch_2);
            name_select_patch = (TextView) getView().findViewById(R.id.name_select_patch);


            //   linear_layout_doc = (LinearLayout) getView().findViewById(R.id.linear_layout_doc);
            txt_doctor_name = (EditText) getView().findViewById(R.id.txt_doctor_name);
            txt_doctor_name.setSingleLine(true);

            //tv_headquarter= (TextView) getView().findViewById(R.id.tv_headquarter);
            //tv_patch_name = (TextView) getView().findViewById(R.id.tv_patch_name);

            txt_hospital_name = (EditText) getView().findViewById(R.id.txt_hospital_name);
            txt_hospital_name.setSingleLine(true);
            txt_reg_no = (EditText) getView().findViewById(R.id.txt_reg_no);
            txt_reg_no.setSingleLine(true);
            txt_address = (EditText) getView().findViewById(R.id.txt_address);
            txt_address.setSingleLine(true);
            txt_city = (EditText) getView().findViewById(R.id.txt_city);
            txt_city.setSingleLine(true);
            txt_pin_code = (EditText) getView().findViewById(R.id.txt_pin_code);
            txt_pin_code.setSingleLine(true);
            txt_per_mobile = (EditText) getView().findViewById(R.id.txt_per_mobile);
            txt_per_mobile.setSingleLine(true);
            txt_per_phone = (EditText) getView().findViewById(R.id.txt_per_phone);
            txt_per_phone.setSingleLine(true);
            txt_email = (EditText) getView().findViewById(R.id.txt_email);
            txt_email.setSingleLine(true);

            actirab_tab = (CheckBox) getView().findViewById(R.id.actirab_tab);
            actirab_d_cap = (CheckBox) getView().findViewById(R.id.actirab_d_cap);
            actirab_dv_cap = (CheckBox) getView().findViewById(R.id.actirab_dv_cap);
            actirab_l_cap = (CheckBox) getView().findViewById(R.id.actirab_l_cap);
            empower_od_tab = (CheckBox) getView().findViewById(R.id.empower_od_tab);
            stand_sp_tab = (CheckBox) getView().findViewById(R.id.stand_sp_tab);
            star_t_tab = (CheckBox) getView().findViewById(R.id.star_t_tab);
            glucolyst_g1_tab = (CheckBox) getView().findViewById(R.id.glucolyst_g1_tab);
            lycorest_tab = (CheckBox) getView().findViewById(R.id.lycorest_tab);
            lycort_1ml_inj = (CheckBox) getView().findViewById(R.id.lycort_1ml_inj);
            regain_xt_tab = (CheckBox) getView().findViewById(R.id.regain_xt_tab);
            lycorest_60ml_susp = (CheckBox) getView().findViewById(R.id.lycorest_60ml_susp);
            lycolic_10ml_drops = (CheckBox) getView().findViewById(R.id.lycolic_10ml_drops);
            stand_mf_60ml_susp = (CheckBox) getView().findViewById(R.id.stand_mf_60ml_susp);
            ten_n_30ml_syrup = (CheckBox) getView().findViewById(R.id.ten_n_30ml_syrup);
            wego_gel_20_mg = (CheckBox) getView().findViewById(R.id.wego_gel_20_mg);
            wego_gel_30_mg = (CheckBox) getView().findViewById(R.id.wego_gel_30_mg);
            trygesic_tab = (CheckBox) getView().findViewById(R.id.trygesic_tab);
            /*--------------------------------------------*/
            itezone_200_cap = (CheckBox) getView().findViewById(R.id.itezone_200_cap);
            nextvit_tab = (CheckBox) getView().findViewById(R.id.nextvit_tab);

            /*new branch product 28/04/2018*/
            trygesic_ptab = (CheckBox) getView().findViewById(R.id.trygesic_ptab);
            cipgrow_syrup = (CheckBox) getView().findViewById(R.id.cipgrow_syrup);
            clavyten_625 = (CheckBox) getView().findViewById(R.id.clavyten_625);
            levocast_m = (CheckBox) getView().findViewById(R.id.levocast_m);
            altipan_dsr = (CheckBox) getView().findViewById(R.id.altipan_dsr);
            sangria_tonic = (CheckBox) getView().findViewById(R.id.sangria_tonic);
            onederm_cream = (CheckBox) getView().findViewById(R.id.onederm_cream);
            actirest_ls = (CheckBox) getView().findViewById(R.id.actirest_ls);
            actirest_dx = (CheckBox) getView().findViewById(R.id.actirest_dx);
            korby_soap = (CheckBox) getView().findViewById(R.id.korby_soap);
            /*End New*/

            btn_save_doctor = (Button) getView().findViewById(R.id.btn_save_doctor);

            tv_doc_id = (TextView) getView().findViewById(R.id.tv_doc_id);
            tv_user = (TextView) getView().findViewById(R.id.tv_user);
            tv_user_full_name = (TextView) getView().findViewById(R.id.tv_user_full_name);

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void edit_fill() {
        try {

            Bundle bundle = this.getArguments();

            String name = bundle.getString("name");
            tv_doc_id.setText(bundle.getString("name"));


            pDialog.show();
            //Thread.sleep(sleep_wait);
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("wego_gel_30_mg");
            jsonArray.put("stand_sp_tab");
            jsonArray.put("creation");
            jsonArray.put("doctor_name");
            jsonArray.put("patch");
            jsonArray.put("patch_name");
            jsonArray.put("actirab_l_cap");
            jsonArray.put("owner");
            jsonArray.put("hospital_name");

            jsonArray.put("empower_od_tab");
            jsonArray.put("city");
            jsonArray.put("lycorest_60ml_susp");
            jsonArray.put("modified_by");
            jsonArray.put("zone");
            jsonArray.put("area");
            jsonArray.put("stand_mf_60ml_susp");
            //jsonArray.put("employee_code");///
            jsonArray.put("reg_no");
            jsonArray.put("per_mobile");
            jsonArray.put("lycorest_tab");
            jsonArray.put("actirab_tab");
            jsonArray.put("lycort_1ml_inj");
            jsonArray.put("docstatus");
            jsonArray.put("doctor_specialization");
            jsonArray.put("email");
            jsonArray.put("doctor_type");
            jsonArray.put("per_phone");
            jsonArray.put("degree");
            jsonArray.put("lycolic_10ml_drops");
            jsonArray.put("hq");
            jsonArray.put("latitude");
            jsonArray.put("start_t_tab");
            jsonArray.put("regain_xt_tab");
//            jsonArray.put("employee_name");////
            jsonArray.put("actirab_d_cap");
            jsonArray.put("actirab_dv_cap");
            jsonArray.put("ten_on_30ml");
            jsonArray.put("pin_code");
            jsonArray.put("trygesic_tab");

            jsonArray.put("idx");
            jsonArray.put("region");
            jsonArray.put("modified");
            jsonArray.put("longitude");
            jsonArray.put("wego_gel_20_mg");
            jsonArray.put("glucolyst_g1_tab");

            jsonArray.put("itezone_200_cap");
            jsonArray.put("nextvit_tab");

            /////////////////////////////
            //trygesic_ptab,cipgrow_syrup,clavyten_625,levocast_m,altipan_dsr,sangria_tonic,onederm_cream,actirest_ls,actirest_dx,korby_soap,
            jsonArray.put("trygesic_ptab");
            jsonArray.put("cipgrow_syrup");
            jsonArray.put("onederm_cream");
            jsonArray.put("clavyten_625");
            jsonArray.put("levocast_m");
            jsonArray.put("sangria_tonic");
            jsonArray.put("korby_soap");
            jsonArray.put("actirest_dx");
            jsonArray.put("altipan_dsr");
            jsonArray.put("actirest_ls");
            ///////////////////////////

            jsonArray.put("address");
            jsonArray.put("active");
            jsonArray.put("user");
            jsonArray.put("user_name");


            //jsonArray.put("approve");////
            //jsonArray.put("approve_note");////
            //jsonArray.put("approve_by");/////
            //JSONObject ofilter= new JSONObject();

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();


            Filter1.put("Doctor Master");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(tv_doc_id.getText());

            Filters.put(Filter1);


            //"modified desc", 0, 1,

            restService.getService().getDoctor_Update_data(sid, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Doctor_Master>>() {
                        }.getType();
                        List<POJO_Doctor_Master> POJO = gson.fromJson(j2, type);

                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();
                        mRealm.close();
                        if (tv_doc_id.getText().toString().length() > 0) {
                            Bind__data(tv_doc_id.getText().toString());
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

    public void Bind__data(String name) {
        try {
            Realm mRealm = Realm.getDefaultInstance();

            final POJO_Doctor_Master POJO = mRealm.where(POJO_Doctor_Master.class).equalTo("name", name).findFirst();
            tv_doc_id.setText(POJO.getName());
            txt_doctor_name.setText(POJO.getDoctor_name());

            //name_select_patch.setText(POJO.getPatch() == null ? " SELECT PATCH" : POJO.getPatch());
            //select_patch_2.setText(POJO.getPatch_name() == null ? " SELECT PATCH" : POJO.getPatch_name());

            name_select_patch.setText(POJO.getPatch() == null ? " SELECT" : POJO.getPatch());
            select_patch_2.setText(POJO.getPatch_name() == null ? " SELECT" : POJO.getPatch_name());

            /*if (!(POJO.getPatch() == null)) {
                if (!(POJO.getPatch().toString() == "")) {
                    name_select_patch.setText(POJO.getPatch().toString());
                }
            }

            if (!(POJO.getPatch_name() == null)) {
                if (!(POJO.getPatch_name().toString() == "")) {
                    select_patch_2.setText(POJO.getPatch_name().toString());
                }
            }*/

            //TextView select_patch_2 = (TextView) getView().findViewById(R.id.select_patch_2);
            ////name_select_patch.setText(POJO.getPatch().toString());
            ////select_patch_2.setText(POJO.getPatch_name().toString());
            //tv_patch_name.setText(POJO.getPatch());

            txt_hospital_name.setText(POJO.getHospital_name() == null ? "" : POJO.getHospital_name());
            txt_reg_no.setText(POJO.getReg_no() == null ? "" : POJO.getReg_no());
            txt_address.setText(POJO.getAddress() == null ? "" : POJO.getAddress());
            txt_city.setText(POJO.getCity() == null ? "" : POJO.getCity());
            txt_pin_code.setText(POJO.getPin_code() == null ? "" : POJO.getPin_code());
            txt_per_mobile.setText(POJO.getPer_mobile() == null ? "" : POJO.getPer_mobile());
            txt_per_phone.setText(POJO.getPer_phone() == null ? "" : POJO.getPer_phone());
            txt_email.setText(POJO.getEmail() == null ? "" : POJO.getEmail());

            actirab_tab.setChecked(POJO.getActirab_tab().equals(1));
            actirab_d_cap.setChecked(POJO.getActirab_d_cap().equals(1));
            actirab_dv_cap.setChecked(POJO.getActirab_dv_cap().equals(1));
            actirab_l_cap.setChecked(POJO.getActirab_l_cap().equals(1));
            empower_od_tab.setChecked(POJO.getEmpower_od_tab().equals(1));
            stand_sp_tab.setChecked(POJO.getStand_sp_tab().equals(1));
            star_t_tab.setChecked(POJO.getStart_t_tab().equals(1));
            glucolyst_g1_tab.setChecked(POJO.getGlucolyst_g1_tab().equals(1));
            lycorest_tab.setChecked(POJO.getLycorest_tab().equals(1));
            lycort_1ml_inj.setChecked(POJO.getLycort_1ml_inj().equals(1));
            regain_xt_tab.setChecked(POJO.getRegain_xt_tab().equals(1));
            lycorest_60ml_susp.setChecked(POJO.getLycorest_60ml_susp().equals(1));
            lycolic_10ml_drops.setChecked(POJO.getLycolic_10ml_drops().equals(1));
            stand_mf_60ml_susp.setChecked(POJO.getStand_mf_60ml_susp().equals(1));
            ten_n_30ml_syrup.setChecked(POJO.getTen_on_30ml().equals(1));
            wego_gel_20_mg.setChecked(POJO.getWego_gel_20_mg().equals(1));
            wego_gel_30_mg.setChecked(POJO.getWego_gel_30_mg().equals(1));
            trygesic_tab.setChecked(POJO.getTrygesic_tab().equals(1));

            itezone_200_cap.setChecked(POJO.getItezone_200_cap().equals(1));
            nextvit_tab.setChecked(POJO.getNextvit_tab().equals(1));
            /*-------------------------------------------------------------------*/

            trygesic_ptab.setChecked(POJO.getTrygesic_ptab().equals(1));
            cipgrow_syrup.setChecked(POJO.getCipgrow_syrup().equals(1));
            clavyten_625.setChecked(POJO.getClavyten_625().equals(1));
            levocast_m.setChecked(POJO.getLevocast_m().equals(1));
            altipan_dsr.setChecked(POJO.getAltipan_dsr().equals(1));
            sangria_tonic.setChecked(POJO.getSangria_tonic().equals(1));
            onederm_cream.setChecked(POJO.getOnederm_cream().equals(1));
            actirest_ls.setChecked(POJO.getActirest_ls().equals(1));
            actirest_dx.setChecked(POJO.getActirest_dx().equals(1));
            korby_soap.setChecked(POJO.getKorby_soap().equals(1));

            /*-------------------------------------------------------------------*/

            //select_degree_2.setText(POJO.getDegree() == null ? " SELECT DEGREE" : POJO.getDegree());
            select_degree_2.setText(POJO.getDegree() == null ? " SELECT" : POJO.getDegree());

            /*if (!(POJO.getDegree() == null)) {
                if (!(POJO.getDegree().toString() == "")) {
                    select_degree_2.setText(POJO.getDegree().toString());
                }
            }*/

            //select_doctor_specialization_2.setText(POJO.getDoctor_specialization() == null ? " SELECT SPECIALIZATION" : POJO.getDoctor_specialization());
            select_doctor_specialization_2.setText(POJO.getDoctor_specialization() == null ? " SELECT" : POJO.getDoctor_specialization());

            /*if (!(POJO.getDoctor_specialization() == null)) {
                if (!(POJO.getDoctor_specialization().toString() == "")) {
                    select_doctor_specialization_2.setText(POJO.getDoctor_specialization().toString());
                }
            }*/

            //select_doctor_type_2.setText(POJO.getDoctor_type() == null ? " SELECT TYPE" : POJO.getDoctor_type());
            select_doctor_type_2.setText(POJO.getDoctor_type() == null ? " SELECT" : POJO.getDoctor_type());

            /*if (!(POJO.getDoctor_type() == null)) {
                if (!(POJO.getDoctor_type().toString() == "")) {
                    select_doctor_type_2.setText(POJO.getDoctor_type().toString());
                }
            }*/

            tv_user.setText(POJO.getUser());
            tv_user_full_name.setText(POJO.getUser_name());

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void update_doctor(POJO_Doctor_Master POJO) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String name = bundle.getString("name").toString();

            restService.getService().doctor_master_update(sid, POJO, name, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        update_doctor_master_class(jsonElement);
                        pDialog.hide();

                        Toast.makeText(getContext(), "DOCTOR UPDATE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        Fragment frag = new fragment_Doctor_List();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);
                        //frag.setArguments(bundle);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(null);

                        ft.commit();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
                            // onsession_failure();
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

    public void insert_doctor(POJO_Doctor_Master POJO) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            POJO.setLatitude("0");
            POJO.setLongitude("0");

            restService.getService().doctor_master_insert(sid, POJO, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        update_doctor_master_class(jsonElement);
                        pDialog.hide();

                        Toast.makeText(getContext(), "DOCTOR ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        Fragment frag = new fragment_Doctor_List();
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(null);
                        ft.commit();
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

    public void update_doctor_master_class(JsonElement jsonElement) {
        try {

            JsonObject j1 = jsonElement.getAsJsonObject();
            JsonElement j2 = j1.getAsJsonObject("data");
            Gson gson = new Gson();
            Type type = new TypeToken<POJO_Doctor_Master>() {
            }.getType();
            POJO_Doctor_Master POJO = gson.fromJson(j2, type);

            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(POJO);
            mRealm.commitTransaction();
            mRealm.close();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public Boolean Validation(POJO_Doctor_Master POJO) {
        try {
            if (POJO.getDoctor_name().toString().trim().length() < 5) {
                Toast.makeText(getContext(), "DOCTOR NAME IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 5", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getPatch_name().toString().contains("Select") == true) {
                Toast.makeText(getContext(), "PLEASE SELECT PATCH", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getHospital_name().toString().trim().length() < 3) {
                Toast.makeText(getContext(), "HOSPITAL NAME IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 3 ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getAddress().toString().trim().length() < 5) {
                Toast.makeText(getContext(), "ADDRESS IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 5 ", Toast.LENGTH_SHORT).show();
                return false;

            } else if (POJO.getCity().toString().trim().length() < 2) {
                Toast.makeText(getContext(), "CITY IS NOT EMPTY AND LENGHT MUST BE GRATER THAN 2 ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getPin_code().toString().trim().length() < 5) {
                Toast.makeText(getContext(), "PINCODE IS NOT EMPTY  ", Toast.LENGTH_SHORT).show();
                return false;
            } else if (POJO.getPer_mobile().toString().trim().length() < 9) {
                Toast.makeText(getContext(), "MOBILE NUMBER IS NOT EMPTY  AND LENGHT MUST BE GRATER THAN 9 ", Toast.LENGTH_SHORT).show();
                return false;

            } /*else if (POJO.getEmployee_code().toString().trim().length() < 1) {
                Toast.makeText(getContext(), "NO EMPLOYEE WITH THIS PATCH ..YOU ARE NOT ASSIGNED EMPLOYEE CODE YET" +
                        "PLEASE CONATACT LYSTEN GLOBAL IT SUPPORT ", Toast.LENGTH_SHORT).show();
                return false;
            }*/ else
                return true;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void CHECK_ABM_RBM_ZBM_SM() {
        try {
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String designation = (app_preferences.getString("designation", "default"));
            if (designation.equals("ABM") || designation.equals("RBM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("CRM")) {
                btn_save_doctor.setVisibility(View.INVISIBLE);
                /*btn_add_doc.setVisibility(View.INVISIBLE);
                btn_delete_patch.setVisibility(View.INVISIBLE);*/
            }
            if (designation.equals("TBM")) {
                btn_save_doctor.setVisibility(View.VISIBLE);
                /*btn_add_doc.setVisibility(View.VISIBLE);
                btn_delete_patch.setVisibility(View.VISIBLE);*/
            } else {
                btn_save_doctor.setVisibility(View.INVISIBLE);
                /*btn_add_doc.setVisibility(View.INVISIBLE);
                btn_delete_patch.setVisibility(View.INVISIBLE);*/
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    ////////////////////////////////////////////////////////////

    private void show_dialog_for_select_degree() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                doctor_degree_FragmentDialog dialog = doctor_degree_FragmentDialog.newInstance("Hello world");
                //dialog.setView(layout);
                dialog.setTargetFragment(fragment_doctor_master_insert.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_doctor_specialization() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                doctor_specialization_FragmentDialog dialog = doctor_specialization_FragmentDialog.newInstance("Hello world");
                //dialog.setView(layout);
                dialog.setTargetFragment(fragment_doctor_master_insert.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_doctor_type() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {

                doctor_type_FragmentDialog dialog = doctor_type_FragmentDialog.newInstance("Hello world");
                //dialog.setView(layout);
                dialog.setTargetFragment(fragment_doctor_master_insert.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (
                Exception ex)

        {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void show_dialog_for_select_patch() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                doctor_patch_FragmentDialog dialog = doctor_patch_FragmentDialog.newInstance("Hello world");
                //dialog.setView(layout);
                dialog.setTargetFragment(fragment_doctor_master_insert.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void Clear_POJO_PATCH() {
        try {
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_Patch_master_S.class);
            mRealm.commitTransaction();
            mRealm.close();
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

            restService.getService().getMasterFormLockOrNot(sid, "'" + name + "'", "doctor", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        String lock_flag = j2.get("lock_flag").getAsString();
                        String message = j2.get("message").getAsString();
                        if (lock_flag.equals("0")) {
                            save_doctor();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();//"Oops !!! Locked Profile..."
                        }


                        /*JsonObject j1 = jsonElement.getAsJsonObject();
                        //JsonObject j2 = j1.getAsJsonObject("message");

                        //String lock_flag = j2.get("message").getAsString();
                        String lock_flag = j1.get("message").getAsString();
                        if (lock_flag == "0") {
                            save_doctor();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), "Oops !!! Locked Doctor...", Toast.LENGTH_SHORT).show();
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

    private void save_doctor() {
        try {

            /*This code Cut From button Click Event*/
            //final Bundle bundle = this.getArguments();

            POJO_Doctor_Master POJO = new POJO_Doctor_Master();

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            //POJO.setPatch();
            POJO.setReg_no(txt_reg_no.getText().toString());
            POJO.setCity(txt_city.getText().toString());
            POJO.setPer_phone(txt_per_phone.getText().toString());
            POJO.setEmail(txt_email.getText().toString());
            POJO.setDoctor_name(txt_doctor_name.getText().toString());

            POJO.setPatch(name_select_patch.getText().toString());///
            POJO.setPatch_name(select_patch_2.getText().toString());///

            POJO.setHospital_name(txt_hospital_name.getText().toString());

            POJO.setAddress(txt_address.getText().toString());

            POJO.setPin_code(txt_pin_code.getText().toString());
            POJO.setPer_mobile(txt_per_mobile.getText().toString());

            POJO.setActirab_tab((actirab_tab.isChecked() == true && actirab_tab.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setActirab_d_cap((actirab_d_cap.isChecked() == true && actirab_d_cap.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setActirab_dv_cap((actirab_dv_cap.isChecked() == true && actirab_dv_cap.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setActirab_l_cap((actirab_l_cap.isChecked() == true && actirab_l_cap.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setEmpower_od_tab((empower_od_tab.isChecked() == true && empower_od_tab.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setStand_sp_tab((stand_sp_tab.isChecked() == true && stand_sp_tab.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setStart_t_tab((star_t_tab.isChecked() == true && star_t_tab.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setGlucolyst_g1_tab((glucolyst_g1_tab.isChecked() == true && glucolyst_g1_tab.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setLycorest_tab((lycorest_tab.isChecked() == true && lycorest_tab.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setLycort_1ml_inj((lycort_1ml_inj.isChecked() == true && lycort_1ml_inj.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setRegain_xt_tab((regain_xt_tab.isChecked() == true && regain_xt_tab.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setLycorest_60ml_susp((lycorest_60ml_susp.isChecked() == true && lycorest_60ml_susp.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setLycolic_10ml_drops((lycolic_10ml_drops.isChecked() == true && lycolic_10ml_drops.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setStand_mf_60ml_susp((stand_mf_60ml_susp.isChecked() == true && stand_mf_60ml_susp.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setTen_on_30ml((ten_n_30ml_syrup.isChecked() == true && ten_n_30ml_syrup.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setWego_gel_20_mg((wego_gel_20_mg.isChecked() == true && wego_gel_20_mg.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setWego_gel_30_mg((wego_gel_30_mg.isChecked() == true && wego_gel_30_mg.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setTrygesic_tab((trygesic_tab.isChecked() == true && trygesic_tab.getVisibility() == View.VISIBLE) ? 1 : 0);

            POJO.setItezone_200_cap((itezone_200_cap.isChecked() == true && itezone_200_cap.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setNextvit_tab((nextvit_tab.isChecked() == true && nextvit_tab.getVisibility() == View.VISIBLE) ? 1 : 0);

             /*-------------------------------------------------------------------*/

            POJO.setTrygesic_ptab((trygesic_ptab.isChecked() == true && trygesic_ptab.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setCipgrow_syrup((cipgrow_syrup.isChecked() == true && cipgrow_syrup.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setClavyten_625((clavyten_625.isChecked() == true && clavyten_625.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setLevocast_m((levocast_m.isChecked() == true && levocast_m.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setAltipan_dsr((altipan_dsr.isChecked() == true && altipan_dsr.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setSangria_tonic((sangria_tonic.isChecked() == true && sangria_tonic.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setOnederm_cream((onederm_cream.isChecked() == true && onederm_cream.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setActirest_ls((actirest_ls.isChecked() == true && actirest_ls.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setActirest_dx((actirest_dx.isChecked() == true && actirest_dx.getVisibility() == View.VISIBLE) ? 1 : 0);
            POJO.setKorby_soap((korby_soap.isChecked() == true && korby_soap.getVisibility() == View.VISIBLE) ? 1 : 0);


            /*-------------------------------------------------------------------*/

            POJO.setDegree(select_degree_2.getText().toString());
            POJO.setDoctor_specialization(select_doctor_specialization_2.getText().toString());
            POJO.setDoctor_type(select_doctor_type_2.getText().toString());
            POJO.setUser(app_preferences.getString("name", "default"));
            POJO.setUser_name(tv_user_full_name.getText().toString());
            POJO.setActive(1);


            if (Validation(POJO) == true) {
                if (bundle != null) {

                    // POJO.setName((tv_doc_id.getText().toString() == null) ? "" : tv_doc_id.getText().toString());
                    update_doctor(POJO);

                } else {

                    // POJO.setName("");
                    /* POJO.setZone(app_preferences.getString("zone", "default"));
                    POJO.setArea(app_preferences.getString("area", "default"));
                    POJO.setRegion(app_preferences.getString("region", "default"));
                    POJO.setHq(app_preferences.getString("headquarter", "default"));*/

                    insert_doctor(POJO);
                }
            } else {
                Toast.makeText(getContext(), "Validation Error", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    ///////////////////////////////////////////////
    //////////////////////////////////////////////
    /////////////////////////////////////////////
    String products_with_comma_for_branch = "";

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
                        if (!branch2.equals("")) {
                            if (branch1.equals(branch2)) {
                                //Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();
                                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                products_with_comma = app_preferences.getString("branch_product", "");
                                if (products_with_comma == "" || products_with_comma == null) {
                                    get_product_list_of_branch(branch2);
                                } else {
                                    visibility_of_product();
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

    String products_with_comma = "";

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
                        visibility_of_product();
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

    //array method start
    public List<String> return_in_array(String branch) {
        try {
            List<String> items = Arrays.asList(branch.split("\\s*,\\s*"));
            return items;
        } catch (Exception ex) {
            pDialog.hide();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    List<String> ss;

    public void call_hide_show_function(String product_lit) {
        try {
            //List<String> ss = return_in_array(product_lit);
            ss = return_in_array(product_lit);


            // String[] bob = { "this", "is", "a", "really", "silly", "list" };

            //if (Arrays.asList(ss).contains("GLUCOLYST - G1 TAB")) {
            /*if (arrayContains(ss, "GLUCOLYST") == true) {
                Toast.makeText(getContext(), ss.get(0).toString(), Toast.LENGTH_SHORT).show();
            }*/
            //Toast.makeText(getContext(), ss.get(0).toString() + "   " + ss.get(1).toString() + "   " + ss.get(2).toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            pDialog.hide();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean arrayContains(List<String> array, String value) {
        try {
            for (int i = 0; i < array.size(); i++) {
                if (((array.get(i) == null) && (value == null)) || array.get(i).contains(value)) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //array method end

    public void visibility_of_product() {
        try {

            //final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String prodcut_comma = products_with_comma;//app_preferences.getString("branch_product", "");

            if (!prodcut_comma.equals("") && !prodcut_comma.equals(null)) {
                if (prodcut_comma.contains("ACTIRAB TAB") == true) {
                    actirab_tab.setVisibility(View.VISIBLE);
                } else {
                    actirab_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ACTIRAB - D CAP") == true) {
                    actirab_d_cap.setVisibility(View.VISIBLE);
                } else {
                    actirab_d_cap.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ACTIRAB -DV Cap") == true) {
                    actirab_dv_cap.setVisibility(View.VISIBLE);
                } else {
                    actirab_dv_cap.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ACTIRAB - L CAP") == true) {
                    actirab_l_cap.setVisibility(View.VISIBLE);
                } else {
                    actirab_l_cap.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("EMPOWER - OD TAB") == true) {
                    empower_od_tab.setVisibility(View.VISIBLE);
                } else {
                    empower_od_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("STAND -SP TAB") == true) {
                    stand_sp_tab.setVisibility(View.VISIBLE);
                } else {
                    stand_sp_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("STAR T TAB") == true) {
                    star_t_tab.setVisibility(View.VISIBLE);
                } else {
                    star_t_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("GLUCOLYST -G1 TAB") == true) {
                    glucolyst_g1_tab.setVisibility(View.VISIBLE);
                } else {
                    glucolyst_g1_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("LYCOREST TAB") == true) {
                    lycorest_tab.setVisibility(View.VISIBLE);
                } else {
                    lycorest_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("LYCORT 1 ml INJ") == true) {
                    lycort_1ml_inj.setVisibility(View.VISIBLE);
                } else {
                    lycort_1ml_inj.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("REGAIN - XT TAB") == true) {
                    regain_xt_tab.setVisibility(View.VISIBLE);
                } else {
                    regain_xt_tab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("LYCOREST 60ml SUSP") == true) {
                    lycorest_60ml_susp.setVisibility(View.VISIBLE);
                } else {
                    lycorest_60ml_susp.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("LYCOLIC 10ml DROP") == true) {
                    lycolic_10ml_drops.setVisibility(View.VISIBLE);
                } else {
                    lycolic_10ml_drops.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("STAND - MF 60ml SUSP") == true) {
                    stand_mf_60ml_susp.setVisibility(View.VISIBLE);
                } else {
                    stand_mf_60ml_susp.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("TEN-ON 30 ml SYRUP") == true) {
                    ten_n_30ml_syrup.setVisibility(View.VISIBLE);
                } else {
                    ten_n_30ml_syrup.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("WEGO GEL 20mg") == true) {
                    wego_gel_20_mg.setVisibility(View.VISIBLE);
                } else {
                    wego_gel_20_mg.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("WEGO GEL 30mg") == true) {
                    wego_gel_30_mg.setVisibility(View.VISIBLE);
                } else {
                    wego_gel_30_mg.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("TRYGESIC TAB") == true) {
                    trygesic_tab.setVisibility(View.VISIBLE);
                } else {
                    trygesic_tab.setVisibility(View.GONE);
                }
                ////////////////////////
                if (prodcut_comma.contains("TRYGESIC-P") == true) {
                    trygesic_ptab.setVisibility(View.VISIBLE);
                } else {
                    trygesic_ptab.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("CIPGROW SYRUP") == true) {
                    cipgrow_syrup.setVisibility(View.VISIBLE);
                } else {
                    cipgrow_syrup.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("CLAVYTEN 625") == true) {
                    clavyten_625.setVisibility(View.VISIBLE);
                } else {
                    clavyten_625.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("LEVOCAST-M") == true) {
                    levocast_m.setVisibility(View.VISIBLE);
                } else {
                    levocast_m.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ALTIPAN DSR") == true) {
                    altipan_dsr.setVisibility(View.VISIBLE);
                } else {
                    altipan_dsr.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("SANGRIA TONIC") == true) {
                    sangria_tonic.setVisibility(View.VISIBLE);
                } else {
                    sangria_tonic.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ONEDERM CREAM") == true) {
                    onederm_cream.setVisibility(View.VISIBLE);
                } else {
                    onederm_cream.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ACTIREST-LS") == true) {
                    actirest_ls.setVisibility(View.VISIBLE);
                } else {
                    actirest_ls.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ACTIREST-DX") == true) {
                    actirest_dx.setVisibility(View.VISIBLE);
                } else {
                    actirest_dx.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("KORBY SOAP") == true) {
                    korby_soap.setVisibility(View.VISIBLE);
                } else {
                    korby_soap.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("ITEZONE 200 CAP") == true) {
                    itezone_200_cap.setVisibility(View.VISIBLE);
                } else {
                    itezone_200_cap.setVisibility(View.GONE);
                }
                if (prodcut_comma.contains("NEXTVIT TAB") == true) {
                    nextvit_tab.setVisibility(View.VISIBLE);
                } else {
                    nextvit_tab.setVisibility(View.GONE);
                }

            }
            if (bundle != null) {
                edit_fill();
            }
             /*if (ss.size() >= 1) {
               if (arrayContains(ss, "ACTIRAB TAB") == true) {
                    actirab_tab.setVisibility(View.VISIBLE);
                } else {
                    actirab_tab.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "ACTIRAB - D CAP") == true) {
                    actirab_d_cap.setVisibility(View.VISIBLE);
                } else {
                    actirab_d_cap.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "ACTIRAB -DV Cap") == true) {
                    actirab_dv_cap.setVisibility(View.VISIBLE);
                } else {
                    actirab_dv_cap.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "ACTIRAB - L CAP") == true) {
                    actirab_l_cap.setVisibility(View.VISIBLE);
                } else {
                    actirab_l_cap.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "EMPOWER - OD TAB") == true) {
                    empower_od_tab.setVisibility(View.VISIBLE);
                } else {
                    empower_od_tab.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "STAND -SP TAB") == true) {
                    stand_sp_tab.setVisibility(View.VISIBLE);
                } else {
                    stand_sp_tab.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "STAR T TAB") == true) {
                    star_t_tab.setVisibility(View.VISIBLE);
                } else {
                    star_t_tab.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "GLUCOLYST -G1 TAB") == true) {
                    glucolyst_g1_tab.setVisibility(View.VISIBLE);
                } else {
                    glucolyst_g1_tab.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "LYCOREST TAB") == true) {
                    lycorest_tab.setVisibility(View.VISIBLE);
                } else {
                    lycorest_tab.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "LYCORT 1 ml INJ") == true) {
                    lycort_1ml_inj.setVisibility(View.VISIBLE);
                } else {
                    lycort_1ml_inj.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "REGAIN - XT TAB") == true) {
                    regain_xt_tab.setVisibility(View.VISIBLE);
                } else {
                    regain_xt_tab.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "LYCOREST 60ml SUSP") == true) {
                    lycorest_60ml_susp.setVisibility(View.VISIBLE);
                } else {
                    lycorest_60ml_susp.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "LYCOLIC 10ml DROP") == true) {
                    lycolic_10ml_drops.setVisibility(View.VISIBLE);
                } else {
                    lycolic_10ml_drops.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "STAND - MF 60ml SUSP") == true) {
                    stand_mf_60ml_susp.setVisibility(View.VISIBLE);
                } else {
                    stand_mf_60ml_susp.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "TEN-ON 30 ml SYRUP") == true) {
                    ten_n_30ml_syrup.setVisibility(View.VISIBLE);
                } else {
                    ten_n_30ml_syrup.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "WEGO GEL 20mg") == true) {
                    wego_gel_20_mg.setVisibility(View.VISIBLE);
                } else {
                    wego_gel_20_mg.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "WEGO GEL 30mg") == true) {
                    wego_gel_30_mg.setVisibility(View.VISIBLE);
                } else {
                    wego_gel_30_mg.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "TRYGESIC TAB") == true) {
                    trygesic_tab.setVisibility(View.VISIBLE);
                } else {
                    trygesic_tab.setVisibility(View.GONE);
                }
                ////////////////////////////
                if (arrayContains(ss, "TRYGESIC-P") == true) {
                    trygesic_ptab.setVisibility(View.VISIBLE);
                } else {
                    trygesic_ptab.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "CIPGROW SYRUP") == true) {
                    cipgrow_syrup.setVisibility(View.VISIBLE);
                } else {
                    cipgrow_syrup.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "CLAVYTEN 625") == true) {
                    clavyten_625.setVisibility(View.VISIBLE);
                } else {
                    clavyten_625.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "LEVOCAST-M") == true) {
                    levocast_m.setVisibility(View.VISIBLE);
                } else {
                    levocast_m.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "ALTIPAN DSR") == true) {
                    altipan_dsr.setVisibility(View.VISIBLE);
                } else {
                    altipan_dsr.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "SANGRIA TONIC") == true) {
                    sangria_tonic.setVisibility(View.VISIBLE);
                } else {
                    sangria_tonic.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "ONEDERM CREAM") == true) {
                    onederm_cream.setVisibility(View.VISIBLE);
                } else {
                    onederm_cream.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "ACTIREST-LS") == true) {
                    actirest_ls.setVisibility(View.VISIBLE);
                } else {
                    actirest_ls.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "ACTIREST-DX") == true) {
                    actirest_dx.setVisibility(View.VISIBLE);
                } else {
                    actirest_dx.setVisibility(View.GONE);
                }
                if (arrayContains(ss, "KORBY SOAP") == true) {
                    korby_soap.setVisibility(View.VISIBLE);
                } else {
                    korby_soap.setVisibility(View.GONE);
                }
            }*/

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }



    /*-----------------------BRANCH HIDE SHOW CODE No Important--------------------------*/

    public void get_branch_of_user1(String user) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

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

                        String branch = "";
                        if (POJO.size() <= 1) {
                            for (POJO_User pp : POJO) {
                                branch = pp.getBranch();
                            }
                        }
                        if (!branch.equals("")) {
                            visibility_of_product_for_branch(branch);
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

    public void visibility_of_product_for_branch(String branch) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("wego_gel_30_mg");
            jsonArray.put("stand_sp_tab");
            jsonArray.put("actirab_l_cap");
            jsonArray.put("empower_od_tab");
            jsonArray.put("lycorest_60ml_susp");
            jsonArray.put("wego_gel_20_mg");
            jsonArray.put("start_t_tab");
            jsonArray.put("stand_mf_60ml_susp");
            jsonArray.put("lycorest_tab");
            jsonArray.put("actirab_tab");
            jsonArray.put("lycort_1ml_inj");
            jsonArray.put("actirab_dv_cap");
            jsonArray.put("lycolic_10ml_drops");
            jsonArray.put("regain_xt_tab");
            jsonArray.put("actirab_d_cap");
            jsonArray.put("ten_on_30ml");
            jsonArray.put("glucolyst_g1_tab");
            jsonArray.put("trygesic_tab");
            //itezone_200_cap, nextvit_tab
            jsonArray.put("itezone_200_cap");
            jsonArray.put("nextvit_tab");

            //trygesic_ptab,cipgrow_syrup,clavyten_625,levocast_m,altipan_dsr,sangria_tonic,onederm_cream,actirest_ls,actirest_dx,korby_soap,
            jsonArray.put("trygesic_ptab");
            jsonArray.put("cipgrow_syrup");
            jsonArray.put("onederm_cream");
            jsonArray.put("clavyten_625");
            jsonArray.put("levocast_m");
            jsonArray.put("sangria_tonic");
            jsonArray.put("korby_soap");
            jsonArray.put("actirest_dx");
            jsonArray.put("altipan_dsr");
            jsonArray.put("actirest_ls");

            jsonArray.put("branch");
            jsonArray.put("name");
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();

            Filter1.put("Products For Branch Doctors");
            Filter1.put("branch");
            Filter1.put("=");
            Filter1.put(branch);

            Filters.put(Filter1);

            //"modified desc", 0, 1,
            restService.getService().getProductOfBranch(sid, "modified desc", 0, 1, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Products_Of_Branch>>() {
                        }.getType();
                        List<POJO_Products_Of_Branch> POJO = gson.fromJson(j2, type);

                        set_visible_product(POJO);

                        /*mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();
                        mRealm.close();
                        if (tv_doc_id.getText().toString().length() > 0) {
                            Bind__data(tv_doc_id.getText().toString());
                        }*/
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

    public void set_visible_product(List<POJO_Products_Of_Branch> POJO) {
        try {
            if (POJO.size() <= 1) {
                for (POJO_Products_Of_Branch pp : POJO) {
                    if (pp.getActirab_tab() == 1) {
                        actirab_tab.setVisibility(View.VISIBLE);
                    } else {
                        actirab_tab.setVisibility(View.GONE);
                    }
                    if (pp.getActirab_d_cap() == 1) {
                        actirab_d_cap.setVisibility(View.VISIBLE);
                    } else {
                        actirab_d_cap.setVisibility(View.GONE);
                    }
                    if (pp.getActirab_dv_cap() == 1) {
                        actirab_dv_cap.setVisibility(View.VISIBLE);
                    } else {
                        actirab_dv_cap.setVisibility(View.GONE);
                    }
                    if (pp.getActirab_l_cap() == 1) {
                        actirab_l_cap.setVisibility(View.VISIBLE);
                    } else {
                        actirab_l_cap.setVisibility(View.GONE);
                    }
                    if (pp.getEmpower_od_tab() == 1) {
                        empower_od_tab.setVisibility(View.VISIBLE);
                    } else {
                        empower_od_tab.setVisibility(View.GONE);
                    }
                    if (pp.getStand_sp_tab() == 1) {
                        stand_sp_tab.setVisibility(View.VISIBLE);
                    } else {
                        stand_sp_tab.setVisibility(View.GONE);
                    }
                    if (pp.getStart_t_tab() == 1) {
                        star_t_tab.setVisibility(View.VISIBLE);
                    } else {
                        star_t_tab.setVisibility(View.GONE);
                    }
                    if (pp.getGlucolyst_g1_tab() == 1) {
                        glucolyst_g1_tab.setVisibility(View.VISIBLE);
                    } else {
                        glucolyst_g1_tab.setVisibility(View.GONE);
                    }
                    if (pp.getLycorest_tab() == 1) {
                        lycorest_tab.setVisibility(View.VISIBLE);
                    } else {
                        lycorest_tab.setVisibility(View.GONE);
                    }
                    if (pp.getLycort_1ml_inj() == 1) {
                        lycort_1ml_inj.setVisibility(View.VISIBLE);
                    } else {
                        lycort_1ml_inj.setVisibility(View.GONE);
                    }
                    if (pp.getRegain_xt_tab() == 1) {
                        regain_xt_tab.setVisibility(View.VISIBLE);
                    } else {
                        regain_xt_tab.setVisibility(View.GONE);
                    }
                    if (pp.getLycorest_60ml_susp() == 1) {
                        lycorest_60ml_susp.setVisibility(View.VISIBLE);
                    } else {
                        lycorest_60ml_susp.setVisibility(View.GONE);
                    }
                    if (pp.getLycolic_10ml_drops() == 1) {
                        lycolic_10ml_drops.setVisibility(View.VISIBLE);
                    } else {
                        lycolic_10ml_drops.setVisibility(View.GONE);
                    }
                    if (pp.getStand_mf_60ml_susp() == 1) {
                        stand_mf_60ml_susp.setVisibility(View.VISIBLE);
                    } else {
                        stand_mf_60ml_susp.setVisibility(View.GONE);
                    }
                    if (pp.getTen_on_30ml() == 1) {
                        ten_n_30ml_syrup.setVisibility(View.VISIBLE);
                    } else {
                        ten_n_30ml_syrup.setVisibility(View.GONE);
                    }
                    if (pp.getWego_gel_20_mg() == 1) {
                        wego_gel_20_mg.setVisibility(View.VISIBLE);
                    } else {
                        wego_gel_20_mg.setVisibility(View.GONE);
                    }
                    if (pp.getWego_gel_30_mg() == 1) {
                        wego_gel_30_mg.setVisibility(View.VISIBLE);
                    } else {
                        wego_gel_30_mg.setVisibility(View.GONE);
                    }
                    if (pp.getTrygesic_tab() == 1) {
                        trygesic_tab.setVisibility(View.VISIBLE);
                    } else {
                        trygesic_tab.setVisibility(View.GONE);
                    }


                    if (pp.getTrygesic_ptab() == 1) {
                        trygesic_ptab.setVisibility(View.VISIBLE);
                    } else {
                        trygesic_ptab.setVisibility(View.GONE);
                    }
                    if (pp.getCipgrow_syrup() == 1) {
                        cipgrow_syrup.setVisibility(View.VISIBLE);
                    } else {
                        cipgrow_syrup.setVisibility(View.GONE);
                    }
                    if (pp.getClavyten_625() == 1) {
                        clavyten_625.setVisibility(View.VISIBLE);
                    } else {
                        clavyten_625.setVisibility(View.GONE);
                    }
                    if (pp.getLevocast_m() == 1) {
                        levocast_m.setVisibility(View.VISIBLE);
                    } else {
                        levocast_m.setVisibility(View.GONE);
                    }
                    if (pp.getAltipan_dsr() == 1) {
                        altipan_dsr.setVisibility(View.VISIBLE);
                    } else {
                        altipan_dsr.setVisibility(View.GONE);
                    }
                    if (pp.getSangria_tonic() == 1) {
                        sangria_tonic.setVisibility(View.VISIBLE);
                    } else {
                        sangria_tonic.setVisibility(View.GONE);
                    }
                    if (pp.getOnederm_cream() == 1) {
                        onederm_cream.setVisibility(View.VISIBLE);
                    } else {
                        onederm_cream.setVisibility(View.GONE);
                    }
                    if (pp.getActirest_ls() == 1) {
                        actirest_ls.setVisibility(View.VISIBLE);
                    } else {
                        actirest_ls.setVisibility(View.GONE);
                    }
                    if (pp.getActirest_dx() == 1) {
                        actirest_dx.setVisibility(View.VISIBLE);
                    } else {
                        actirest_dx.setVisibility(View.GONE);
                    }
                    if (pp.getKorby_soap() == 1) {
                        korby_soap.setVisibility(View.VISIBLE);
                    } else {
                        korby_soap.setVisibility(View.GONE);
                    }
                }
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*-----------------------BRANCH HIDE SHOW CODE No Important--------------------------*/
}

