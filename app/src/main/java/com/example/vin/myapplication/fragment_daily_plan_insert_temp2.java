package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;
//import static com.example.vin.myapplication.R.layout.fragment_daily_plan_insert_temp1;
//import static com.example.vin.myapplication.R.layout.fragment_daily_plan_insert_tempp;

//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_daily_plan_insert_temp2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_daily_plan_insert_temp2 extends Fragment implements
        user_list_hierarchy_me_FragmentDialog.EditUserListHirarchyDialogListener,
        test_attch_patch_FragmentDialog.EditTestPacthDialogListener,
        test_attch_doctor_FragmentDialog.EditTestDoctorDialogListener, LocationListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Context context;
    ProgressDialog pDialog;
    String Object_id = "";
    RestService restService;
    Realm mRealm;

    Bundle bundle = null;
    LinearLayout ll_dcr;
    LinearLayout ll_cme;
    LinearLayout ll_camp;
    LinearLayout ll_meeting;
    LinearLayout ll_leave;

    LinearLayout ll_patch1_dr_count;
    LinearLayout ll_patch2_dr_count;

    Spinner spinner_patch1;
    Spinner spinner_patch2;

    TextView txt_plan_of_date;

    EditText edit_cme_note;
    EditText edit_camp_note;
    EditText edit_meeting_with;
    EditText edit_meeting_memo;
    EditText edit_leave_reason;

    EditText edit_dcr_agenda, edit_camp_agenda, edit_meeting_place;

    Button btn_add;

    CheckBox chk_causual_leave;
    CheckBox chk_privilege_leave;
    CheckBox chk_sick_leave;

    /*-----------DCR Start-------------*/
    private long mLastClickTime = 0;

    LinearLayout doctor_of_TBM;
    LinearLayout doctor_of_TBM_1;
    ImageButton doctor_of_TBM_3;


    LinearLayout select_doctor;
    LinearLayout select_doctor1;
    ImageButton select_doctor3;


    LinearLayout select_patch;
    LinearLayout select_patch_1;
    ImageButton select_patch_3;


    TextView doctor_of_TBM_2;
    TextView select_patch_2;
    //TextView select_doctor2;

    TextView name_doctor_of_TBM;
    TextView name_select_doctor;
    TextView name_select_patch;


    LinearLayout jfw_of_all1;
    LinearLayout jfw_of_all_11;
    TextView jfw_of_all_21;
    ImageButton jfw_of_all_31;

    LinearLayout jfw_of_all2;
    LinearLayout jfw_of_all_12;
    TextView jfw_of_all_22;
    ImageButton jfw_of_all_32;
    int jfw = 0, tbm_emp = 0, pacth = 0;
    /*-----------DCR End-------------*/

    /*-----------CAMP Start-------------*/
    LinearLayout doctor_of_TBM2;
    LinearLayout doctor_of_TBM_12;
    ImageButton doctor_of_TBM_32;
    TextView doctor_of_TBM_22;
    TextView name_doctor_of_TBM2;


    LinearLayout select_patch2;
    LinearLayout select_patch_12;
    ImageButton select_patch_32;
    TextView select_patch_22;
    TextView name_select_patch2;

    LinearLayout select_doctor2;
    LinearLayout select_doctor12;
    ImageButton select_doctor32;
    TextView select_doctor22;
    TextView name_select_doctor2;


    LinearLayout jfw_of_all3;
    LinearLayout jfw_of_all_13;
    TextView jfw_of_all_23;
    ImageButton jfw_of_all_33;

    LinearLayout jfw_of_all4;
    LinearLayout jfw_of_all_14;
    TextView jfw_of_all_24;
    ImageButton jfw_of_all_34;
    /*-----------DCR End-------------*/

    Double lat = 0.0, lon = 0.0;
    int cnt_location = 0;
    int gps_enable_flag = 0;
    LocationManager locationManager;

    private OnFragmentInteractionListener mListener;

    public fragment_daily_plan_insert_temp2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_daily_plan_insert_tempp.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_daily_plan_insert_temp2 newInstance(String param1, String param2) {
        fragment_daily_plan_insert_temp2 fragment = new fragment_daily_plan_insert_temp2();
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
        return inflater.inflate(R.layout.fragment_daily_plan_insert_tempp, container, false);
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

            init_controls();

            GPSTracker gps = new GPSTracker(getContext(), fragment_daily_plan_insert_temp2.this, false);
            if (gps.canGetLocation()) {

                lat = gps.getLatitude();
                lon = gps.getLongitude();

            } else {
                gps.showSettingsAlert();
            }

            pDialog = new ProgressDialog(getContext());

            ((DashBord_main) getActivity()).setActionBarTitle("PLAN");

            final Bundle bundle = this.getArguments();
            if (bundle != null) {
                listview_show_hide();

                /*if ((bundle.getString("dcr").equals("0") && bundle.getString("camp").equals("0") && bundle.getString("meeting").equals("0")) && (bundle.getString("lve").equals("0"))) {
                    btn_add.setVisibility(View.GONE);
                } else {
                    btn_add.setVisibility(View.VISIBLE);
                }*/

                if (bundle.getString("name").equals("0")) {
                    btn_add.setText("CREATE");
                    loaddate();
                } else {
                    btn_add.setText("UPDATE");
                    Object_id = bundle.getString("name");
                    if (bundle.getString("btn_enable_flag") == "1") {
                        btn_add.setVisibility(View.GONE);
                    } else {
                        btn_add.setVisibility(View.VISIBLE);
                    }

                    get_todays_objective(bundle.getString("name"));
                    //edit_fill();
                }
                /*----------------------------Only For TBM Designation Start----------------------------*/
                CALL_CHECK_TBM_OR_NOT();
                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String designation = app_preferences.getString("designation", "default");
                if (designation.equals("TBM") == true) {
                    if (bundle.getString("dcr").equals("1")) {
                        name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);

                        name_doctor_of_TBM.setText(app_preferences.getString("name", "default"));
                        doctor_of_TBM_2.setText(app_preferences.getString("full_name", "default"));
                    }
                    if (bundle.getString("camp").equals("1")) {
                        name_doctor_of_TBM2 = (TextView) getView().findViewById(R.id.name_doctor_of_TBM2);

                        name_doctor_of_TBM2.setText(app_preferences.getString("name", "default"));
                        doctor_of_TBM_22.setText(app_preferences.getString("full_name", "default"));
                    }
                }
                /*----------------------------Only For TBM Designation End----------------------------*/

                if (bundle.getString("dcr").equals("1")) {

                    /*-------------------DCR Section Start--------------------------*/
                    doctor_of_TBM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                tbm_emp = 1;
                                //select_patch_2.setText(" Select Patch");
                                select_patch_2.setText(" Select");
                                ////select_doctor2.setText(" Select Doctor");
                                Clear_POJO_PATCH_Doctor();
                                show_dialog_for_doctor_of_TBM();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    doctor_of_TBM_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                tbm_emp = 1;
                                //select_patch_2.setText(" Select Patch");
                                select_patch_2.setText(" Select");
                                ////select_doctor2.setText(" Select Doctor");
                                Clear_POJO_PATCH_Doctor();
                                show_dialog_for_doctor_of_TBM();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    doctor_of_TBM_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                tbm_emp = 1;
                                //select_patch_2.setText(" Select Patch");
                                select_patch_2.setText(" Select");
                                ////select_doctor2.setText(" Select Doctor");
                                Clear_POJO_PATCH_Doctor();
                                show_dialog_for_doctor_of_TBM();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    doctor_of_TBM_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                tbm_emp = 1;
                                //select_patch_2.setText(" Select Patch");
                                select_patch_2.setText(" Select");
                                ////select_doctor2.setText(" Select Doctor");
                                Clear_POJO_PATCH_Doctor();
                                show_dialog_for_doctor_of_TBM();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /////////////////////////////////
                    select_patch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                pacth = 1;
                                //select_patch_2.setText(" Select Patch");
                                select_patch_2.setText(" Select");
                                ////select_doctor2.setText(" Select Doctor");
                                ////Clear_POJO_Doctor();
                                //if (!doctor_of_TBM_2.getText().toString().equals(" Select PATCH of TBM") && !doctor_of_TBM_2.getText().toString().equals("")) {
                                if (!doctor_of_TBM_2.getText().toString().equals(" Select") && !doctor_of_TBM_2.getText().toString().equals("")) {
                                    String ss = name_doctor_of_TBM.getText().toString();
                                    show_dialog_for_select_patch(name_doctor_of_TBM.getText().toString());
                                } else {
                                    Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    select_patch_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                pacth = 1;
                                //select_patch_2.setText(" Select Patch");
                                select_patch_2.setText(" Select");
                                ////select_doctor2.setText(" Select Doctor");
                                ////Clear_POJO_Doctor();
                                //if (!doctor_of_TBM_2.getText().toString().equals(" Select PATCH of TBM") && !doctor_of_TBM_2.getText().toString().equals("")) {
                                if (!doctor_of_TBM_2.getText().toString().equals(" Select") && !doctor_of_TBM_2.getText().toString().equals("")) {
                                    String ss = name_doctor_of_TBM.getText().toString();
                                    show_dialog_for_select_patch(name_doctor_of_TBM.getText().toString());
                                } else {
                                    Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    select_patch_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                pacth = 1;
                                //select_patch_2.setText(" Select Patch");
                                select_patch_2.setText(" Select");
                                ////select_doctor2.setText(" Select Doctor");
                                ////Clear_POJO_Doctor();
                                //if (!doctor_of_TBM_2.getText().toString().equals(" Select PATCH of TBM") && !doctor_of_TBM_2.getText().toString().equals("")) {
                                if (!doctor_of_TBM_2.getText().toString().equals(" Select") && !doctor_of_TBM_2.getText().toString().equals("")) {
                                    String ss = name_doctor_of_TBM.getText().toString();
                                    show_dialog_for_select_patch(name_doctor_of_TBM.getText().toString());
                                } else {
                                    Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    select_patch_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                pacth = 1;
                                //select_patch_2.setText(" Select Patch");
                                select_patch_2.setText(" Select");
                                ////select_doctor2.setText(" Select Doctor");
                                ////Clear_POJO_Doctor();
                                //if (!doctor_of_TBM_2.getText().toString().equals(" Select PATCH of TBM") && !doctor_of_TBM_2.getText().toString().equals("")) {
                                if (!doctor_of_TBM_2.getText().toString().equals(" Select") && !doctor_of_TBM_2.getText().toString().equals("")) {
                                    String ss = name_doctor_of_TBM.getText().toString();
                                    show_dialog_for_select_patch(name_doctor_of_TBM.getText().toString());
                                } else {
                                    Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    jfw_of_all1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 1;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    jfw_of_all_11.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 1;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    jfw_of_all_21.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 1;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    jfw_of_all_31.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 1;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /////////////////////////////////
                    jfw_of_all2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 2;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    jfw_of_all_12.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 2;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    jfw_of_all_22.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 2;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    jfw_of_all_32.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 2;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /*-------------------DCR Section End--------------------------*/

                }

                if (bundle.getString("camp").equals("1")) {

                    /*-------------------CAMP Section Start--------------------------*/
                    doctor_of_TBM2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                tbm_emp = 2;
                                //select_patch_22.setText(" Select Patch");
                                select_patch_22.setText(" Select");
                                //select_doctor22.setText(" Select Doctor");
                                select_doctor22.setText(" Select");
                                Clear_POJO_PATCH_Doctor();
                                show_dialog_for_doctor_of_TBM();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    doctor_of_TBM_12.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                tbm_emp = 2;
                                //select_patch_22.setText(" Select Patch");
                                select_patch_22.setText(" Select");
                                //select_doctor22.setText(" Select Doctor");
                                select_doctor22.setText(" Select");
                                Clear_POJO_PATCH_Doctor();
                                show_dialog_for_doctor_of_TBM();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    doctor_of_TBM_22.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                tbm_emp = 2;
                                //select_patch_22.setText(" Select Patch");
                                select_patch_22.setText(" Select");
                                //select_doctor22.setText(" Select Doctor");
                                select_doctor22.setText(" Select");
                                Clear_POJO_PATCH_Doctor();
                                show_dialog_for_doctor_of_TBM();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    doctor_of_TBM_32.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                tbm_emp = 2;
                                //select_patch_22.setText(" Select Patch");
                                select_patch_22.setText(" Select");
                                //select_doctor22.setText(" Select Doctor");
                                select_doctor22.setText(" Select");
                                Clear_POJO_PATCH_Doctor();
                                show_dialog_for_doctor_of_TBM();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /////////////////////////////////
                    select_patch2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                pacth = 2;
                                //select_patch_22.setText(" Select Patch");
                                select_patch_22.setText(" Select");
                                //select_doctor22.setText(" Select Doctor");
                                select_doctor22.setText(" Select");
                                Clear_POJO_Doctor();
                                //if (!doctor_of_TBM_2.getText().toString().equals(" Select PATCH of TBM") && !doctor_of_TBM_2.getText().toString().equals("")) {
                                if (!doctor_of_TBM_2.getText().toString().equals(" Select") && !doctor_of_TBM_2.getText().toString().equals("")) {
                                    String ss = name_doctor_of_TBM2.getText().toString();
                                    show_dialog_for_select_patch(name_doctor_of_TBM2.getText().toString());
                                } else {
                                    Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    select_patch_12.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                pacth = 2;
                                //select_patch_22.setText(" Select Patch");
                                select_patch_22.setText(" Select");
                                //select_doctor22.setText(" Select Doctor");
                                select_doctor22.setText(" Select");
                                Clear_POJO_Doctor();
                                //if (!doctor_of_TBM_22.getText().toString().equals(" Select PATCH of TBM") && !doctor_of_TBM_22.getText().toString().equals("")) {
                                if (!doctor_of_TBM_22.getText().toString().equals(" Select") && !doctor_of_TBM_22.getText().toString().equals("")) {
                                    String ss = name_doctor_of_TBM2.getText().toString();
                                    show_dialog_for_select_patch(name_doctor_of_TBM2.getText().toString());
                                } else {
                                    Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    select_patch_22.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                pacth = 2;
                                //select_patch_22.setText(" Select Patch");
                                select_patch_22.setText(" Select");
                                //select_doctor22.setText(" Select Doctor");
                                select_doctor22.setText(" Select");
                                Clear_POJO_Doctor();
                                //if (!doctor_of_TBM_22.getText().toString().equals(" Select PATCH of TBM") && !doctor_of_TBM_22.getText().toString().equals("")) {
                                if (!doctor_of_TBM_22.getText().toString().equals(" Select") && !doctor_of_TBM_22.getText().toString().equals("")) {
                                    String ss = name_doctor_of_TBM2.getText().toString();
                                    show_dialog_for_select_patch(name_doctor_of_TBM2.getText().toString());
                                } else {
                                    Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    select_patch_32.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                pacth = 2;
                                //select_patch_22.setText(" Select Patch");
                                select_patch_22.setText(" Select");
                                //select_doctor22.setText(" Select Doctor");
                                select_doctor22.setText(" Select");
                                Clear_POJO_Doctor();
                                //if (!doctor_of_TBM_22.getText().toString().equals(" Select PATCH of TBM") && !doctor_of_TBM_22.getText().toString().equals("")) {
                                if (!doctor_of_TBM_22.getText().toString().equals(" Select") && !doctor_of_TBM_22.getText().toString().equals("")) {
                                    String ss = name_doctor_of_TBM2.getText().toString();
                                    show_dialog_for_select_patch(name_doctor_of_TBM2.getText().toString());
                                } else {
                                    Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /////////////////////////////////
                    select_doctor22.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                //select_doctor22.setText(" Select Doctor");
                                select_doctor22.setText(" Select");
                                Clear_POJO_Doctor();
                                //if (!doctor_of_TBM_2.getText().toString().equals(" Select Doctor of TBM") && !doctor_of_TBM_2.getText().toString().equals("") && !select_patch_2.getText().toString().equals(" Select Patch") && !select_patch_2.getText().toString().equals("")) {
                                if (!doctor_of_TBM_2.getText().toString().equals(" Select") && !doctor_of_TBM_2.getText().toString().equals("") && !select_patch_2.getText().toString().equals(" Select") && !select_patch_2.getText().toString().equals("")) {
                                    show_dialog_for_select_doctor(name_select_patch2.getText().toString());
                                } else {
                                    Toast.makeText(getContext(), "Please Select Correct TBM & PATCH", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    select_doctor12.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                //select_doctor22.setText(" Select Doctor");
                                select_doctor22.setText(" Select");
                                Clear_POJO_Doctor();
                                //if (!doctor_of_TBM_22.getText().toString().equals(" Select Doctor of TBM") && !doctor_of_TBM_22.getText().toString().equals("") && !select_patch_22.getText().toString().equals(" Select Patch") && !select_patch_22.getText().toString().equals("")) {
                                if (!doctor_of_TBM_22.getText().toString().equals(" Select") && !doctor_of_TBM_22.getText().toString().equals("") && !select_patch_22.getText().toString().equals(" Select") && !select_patch_22.getText().toString().equals("")) {
                                    show_dialog_for_select_doctor(name_select_patch2.getText().toString());
                                } else {
                                    Toast.makeText(getContext(), "Please Select Correct TBM & PATCH", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    select_doctor22.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                //select_doctor22.setText(" Select Doctor");
                                select_doctor22.setText(" Select");
                                Clear_POJO_Doctor();
                                //if (!doctor_of_TBM_22.getText().toString().equals(" Select Doctor of TBM") && !doctor_of_TBM_22.getText().toString().equals("") && !select_patch_22.getText().toString().equals(" Select Patch") && !select_patch_22.getText().toString().equals("")) {
                                if (!doctor_of_TBM_22.getText().toString().equals(" Select") && !doctor_of_TBM_22.getText().toString().equals("") && !select_patch_22.getText().toString().equals(" Select") && !select_patch_22.getText().toString().equals("")) {
                                    show_dialog_for_select_doctor(name_select_patch2.getText().toString());
                                } else {
                                    Toast.makeText(getContext(), "Please Select Correct TBM & PATCH", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    select_doctor32.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                //select_doctor22.setText(" Select Doctor");
                                select_doctor22.setText(" Select");
                                Clear_POJO_Doctor();
                                //if (!doctor_of_TBM_22.getText().toString().equals(" Select Doctor of TBM") && !doctor_of_TBM_22.getText().toString().equals("") && !select_patch_22.getText().toString().equals(" Select Patch") && !select_patch_22.getText().toString().equals("")) {
                                if (!doctor_of_TBM_22.getText().toString().equals(" Select") && !doctor_of_TBM_22.getText().toString().equals("") && !select_patch_22.getText().toString().equals(" Select") && !select_patch_22.getText().toString().equals("")) {
                                    show_dialog_for_select_doctor(name_select_patch2.getText().toString());
                                } else {
                                    Toast.makeText(getContext(), "Please Select Correct TBM & PATCH", Toast.LENGTH_LONG).show();
                                }
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /////////////////////////////////
                    jfw_of_all3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 3;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    jfw_of_all_13.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 3;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    jfw_of_all_23.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 3;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    jfw_of_all_33.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 3;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /////////////////////////////////
                    jfw_of_all4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 4;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    jfw_of_all_14.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 4;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    jfw_of_all_24.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 4;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    jfw_of_all_34.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                jfw = 4;
                                show_dialog_for_jwf_top_down_emp();
                            } catch (Exception ex) {
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /*-------------------CAMP Section End--------------------------*/

                }

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

                            } else {
                                GPSTracker gps = new GPSTracker(getContext(), fragment_daily_plan_insert_temp2.this, false);
                                if (gps.canGetLocation()) {

                                    lat = gps.getLatitude();
                                    lon = gps.getLongitude();

                                    //////////
                                    String select_date = txt_plan_of_date.getText().toString();
                                    if (select_date != null || !txt_plan_of_date.getText().toString().isEmpty()) {
                                        lock_check();
                                    } else {
                                        Toast.makeText(getContext(), "Please Enter Valid Data...", Toast.LENGTH_SHORT).show();
                                    }
                                    ////////
                                } else {
                                    gps.showSettingsAlert();
                                }
                            }
                            mLastClickTime = SystemClock.elapsedRealtime();
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                /*btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            setClasPojoObjective();

                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

            } else {

            }


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loaddate() {
        try {
            txt_plan_of_date = (TextView) getView().findViewById(R.id.txt_plan_of_date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            txt_plan_of_date.setText(sdf.format(date));

            Date date1 = sdf.parse(bundle.getString("pl_date"));

            if (date1.compareTo(date) > 0) {
                Toast.makeText(getContext(), "You Cann't Add Next Days Objectives", Toast.LENGTH_LONG).show();
                txt_plan_of_date.setText(bundle.getString("pl_date"));
                btn_add.setVisibility(View.GONE);
            } else {
                txt_plan_of_date.setText(bundle.getString("pl_date"));
                btn_add.setVisibility(View.VISIBLE);
            }

            /*if (bundle.getString("btn_enable_flag") == "1") {
                btn_add.setVisibility(View.GONE);
            } else {
                btn_add.setVisibility(View.VISIBLE);
            }*/

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void init_controls() {
        try {

            bundle = this.getArguments();

            if (bundle.getString("dcr").equals("1")) {

                /*----------------------------------DCR SECTION START--------------------------------------------*/
            /*txt_doctor_of_TBM = (TextView) getView().findViewById(R.id.txt_doctor_of_TBM);*/
                doctor_of_TBM = (LinearLayout) getView().findViewById(R.id.doctor_of_TBM);
                doctor_of_TBM_1 = (LinearLayout) getView().findViewById(R.id.doctor_of_TBM_1);
                doctor_of_TBM_3 = (ImageButton) getView().findViewById(R.id.doctor_of_TBM_3);
                doctor_of_TBM_2 = (TextView) getView().findViewById(R.id.doctor_of_TBM_2);

                select_patch = (LinearLayout) getView().findViewById(R.id.select_patch);
                select_patch_1 = (LinearLayout) getView().findViewById(R.id.select_patch_1);
                select_patch_3 = (ImageButton) getView().findViewById(R.id.select_patch_3);
                select_patch_2 = (TextView) getView().findViewById(R.id.select_patch_2);


                jfw_of_all1 = (LinearLayout) getView().findViewById(R.id.jfw_of_all1);
                jfw_of_all_11 = (LinearLayout) getView().findViewById(R.id.jfw_of_all_11);
                jfw_of_all_21 = (TextView) getView().findViewById(R.id.jfw_of_all_21);
                jfw_of_all_31 = (ImageButton) getView().findViewById(R.id.jfw_of_all_31);

                jfw_of_all2 = (LinearLayout) getView().findViewById(R.id.jfw_of_all2);
                jfw_of_all_12 = (LinearLayout) getView().findViewById(R.id.jfw_of_all_12);
                jfw_of_all_22 = (TextView) getView().findViewById(R.id.jfw_of_all_22);
                jfw_of_all_32 = (ImageButton) getView().findViewById(R.id.jfw_of_all_32);

                edit_dcr_agenda = (EditText) getView().findViewById(R.id.edit_dcr_agenda);
            /*----------------------------------DCR SECTION END--------------------------------------------*/

            }

            if (bundle.getString("camp").equals("1")) {

                /*----------------------------------CAMP SECTION START--------------------------------------------*/
                /*txt_doctor_of_TBM2 = (TextView) getView().findViewById(R.id.txt_doctor_of_TBM2);*/
                doctor_of_TBM2 = (LinearLayout) getView().findViewById(R.id.doctor_of_TBM2);
                doctor_of_TBM_12 = (LinearLayout) getView().findViewById(R.id.doctor_of_TBM_12);
                doctor_of_TBM_32 = (ImageButton) getView().findViewById(R.id.doctor_of_TBM_32);
                doctor_of_TBM_22 = (TextView) getView().findViewById(R.id.doctor_of_TBM_22);


                select_doctor2 = (LinearLayout) getView().findViewById(R.id.select_doctor2);
                select_doctor12 = (LinearLayout) getView().findViewById(R.id.select_doctor12);
                select_doctor32 = (ImageButton) getView().findViewById(R.id.select_doctor32);
                select_doctor22 = (TextView) getView().findViewById(R.id.select_doctor22);

                select_patch2 = (LinearLayout) getView().findViewById(R.id.select_patch2);
                select_patch_12 = (LinearLayout) getView().findViewById(R.id.select_patch_12);
                select_patch_32 = (ImageButton) getView().findViewById(R.id.select_patch_32);
                select_patch_22 = (TextView) getView().findViewById(R.id.select_patch_22);
                name_select_patch2 = (TextView) getView().findViewById(R.id.name_select_patch2);//

                jfw_of_all3 = (LinearLayout) getView().findViewById(R.id.jfw_of_all3);
                jfw_of_all_13 = (LinearLayout) getView().findViewById(R.id.jfw_of_all_13);
                jfw_of_all_23 = (TextView) getView().findViewById(R.id.jfw_of_all_23);
                jfw_of_all_33 = (ImageButton) getView().findViewById(R.id.jfw_of_all_33);

                jfw_of_all4 = (LinearLayout) getView().findViewById(R.id.jfw_of_all4);
                jfw_of_all_14 = (LinearLayout) getView().findViewById(R.id.jfw_of_all_14);
                jfw_of_all_24 = (TextView) getView().findViewById(R.id.jfw_of_all_24);
                jfw_of_all_34 = (ImageButton) getView().findViewById(R.id.jfw_of_all_34);

                edit_camp_agenda = (EditText) getView().findViewById(R.id.edit_camp_agenda);
            /*----------------------------------CAMP SECTION END--------------------------------------------*/

            }

            if (bundle.getString("meeting").equals("1")) {

                /*----------------------------------MEETING SECTION START--------------------------------------------*/
                edit_meeting_place = (EditText) getView().findViewById(R.id.edit_meeting_place);
                edit_meeting_with = (EditText) getView().findViewById(R.id.edit_meeting_with);
                edit_meeting_memo = (EditText) getView().findViewById(R.id.edit_meeting_memo);
                /*----------------------------------MEETING SECTION END--------------------------------------------*/

            }

            if (bundle.getString("lve").equals("1")) {

                /*----------------------------------Leave SECTION START--------------------------------------------*/

                chk_causual_leave = (CheckBox) getView().findViewById(R.id.chk_causual_leave);
                chk_privilege_leave = (CheckBox) getView().findViewById(R.id.chk_privilege_leave);
                chk_sick_leave = (CheckBox) getView().findViewById(R.id.chk_sick_leave);

                chk_causual_leave.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
                chk_privilege_leave.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
                chk_sick_leave.setOnCheckedChangeListener(new myCheckBoxChnageClicker());

                edit_leave_reason = (EditText) getView().findViewById(R.id.edit_leave_reason);

            /*----------------------------------Leave SECTION END--------------------------------------------*/

            }


            ll_dcr = (LinearLayout) getView().findViewById(R.id.ll_dcr);
            ////ll_dcr_seocond_patch = (LinearLayout) getView().findViewById(R.id.ll_dcr_seocond_patch);
            ll_cme = (LinearLayout) getView().findViewById(R.id.ll_cme);
            ll_camp = (LinearLayout) getView().findViewById(R.id.ll_camp);
            ll_meeting = (LinearLayout) getView().findViewById(R.id.ll_meeting);
            ll_leave = (LinearLayout) getView().findViewById(R.id.ll_leave);

            txt_plan_of_date = (TextView) getView().findViewById(R.id.txt_plan_of_date);


            btn_add = (Button) getView().findViewById(R.id.btn_add);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void setClasPojoObjective() {
        try {

            boolean val = false;
            String valmsg = "PLEASE SELECT OR ENTER DATA";

            POJO_objective obj = new POJO_objective();
            bundle = this.getArguments();

            obj.setSelect_date(txt_plan_of_date.getText().toString());
            if (bundle.getString("dcr").equals("1")) {
                /*----------------------------------DCR SECTION START--------------------------------------------*/
                if (!select_patch_2.getText().toString().contains("Select") && edit_dcr_agenda.getText().toString().length() > 9) {
                    obj.setDcr_tbm_name(doctor_of_TBM_2.getText().toString());
                    obj.setDcr_tbm(name_doctor_of_TBM.getText().toString());
                    obj.setSelect_patch_name(name_select_patch.getText().toString());
                    obj.setSelect_patch(select_patch_2.getText().toString());

                    Boolean jfw = false;
                    if (!jfw_of_all_22.getText().toString().toLowerCase().trim().contains("select") && !jfw_of_all_22.getText().toString().toLowerCase().trim().contains("none")) {
                        if (!jfw_of_all_21.getText().toString().toLowerCase().trim().contains("select") && !jfw_of_all_21.getText().toString().toLowerCase().trim().contains("none")) {
                            //
                            if (!jfw_of_all_22.getText().toString().equals(jfw_of_all_21.getText().toString())) {
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

                    obj.setDcr_jfw_with1_name(jfw_of_all_21.getText().toString().contains("Select") == true ? "" : jfw_of_all_21.getText().toString());
                    obj.setDcr_jfw_with2_name(jfw_of_all_22.getText().toString().contains("Select") == true ? "" : jfw_of_all_22.getText().toString());
                    obj.setCall_agenda(edit_dcr_agenda.getText().toString());
                    obj.setDoctor_flag(1);
                    val = true;
                } else {
                    val = false;
                    pDialog.hide();
                    if (!select_patch_2.getText().toString().contains("Select") && edit_dcr_agenda.getText().toString().length() < 9) {
                        Toast.makeText(getContext(), "AGENDA Must Be More Than 10 Character's...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "PLEASE SELECT PATCH,ENTER AGENDA", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                obj.setDcr_tbm_name("");
                obj.setDcr_tbm("");
                obj.setSelect_patch_name("");
                obj.setSelect_patch("");
                obj.setDcr_jfw_with1_name("");
                obj.setDcr_jfw_with2_name("");
                obj.setCall_agenda("");
                obj.setDoctor_flag(0);
                /*----------------------------------DCR SECTION END--------------------------------------------*/
            }

            if (bundle.getString("camp").equals("1")) {
                /*----------------------------------CAMP SECTION START--------------------------------------------*/
                if (!select_doctor22.getText().toString().contains("Select") && edit_camp_agenda.getText().toString().length() > 9) {
                    obj.setCamp_tbm_name(doctor_of_TBM_22.getText().toString());
                    obj.setCamp_tbm(name_doctor_of_TBM2.getText().toString());

                    obj.setCamp_patch_name(select_patch_22.getText().toString());
                    obj.setCamp_patch(name_select_patch2.getText().toString());

                    obj.setDoctor(name_select_doctor2.getText().toString());
                    obj.setDoctor_name(select_doctor22.getText().toString());

                    Boolean jfw = false;
                    if (!jfw_of_all_24.getText().toString().toLowerCase().trim().contains("select") && !jfw_of_all_24.getText().toString().toLowerCase().trim().contains("none")) {
                        if (!jfw_of_all_23.getText().toString().toLowerCase().trim().contains("select") && !jfw_of_all_23.getText().toString().toLowerCase().trim().contains("none")) {
                            //
                            if (!jfw_of_all_24.getText().toString().equals(jfw_of_all_23.getText().toString())) {
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

                    obj.setCamp_jfw_with_name1(jfw_of_all_23.getText().toString().contains("Select") == true ? "" : jfw_of_all_23.getText().toString());
                    obj.setCamp_jfw_with_name2(jfw_of_all_24.getText().toString().contains("Select") == true ? "" : jfw_of_all_24.getText().toString());
                    obj.setCamp_agenda(edit_camp_agenda.getText().toString());
                    obj.setCamp_flag(1);
                    val = true;
                } else {
                    val = false;
                    pDialog.hide();
                    if (!select_doctor22.getText().toString().contains("Select") && edit_camp_agenda.getText().toString().length() < 9) {
                        Toast.makeText(getContext(), "AGENDA Must Be More Than 10 Character's...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "PLEASE SELECT DOCTOR,ENTER AGENDA", Toast.LENGTH_SHORT).show();
                    }
                }
                /*----------------------------------CAMP SECTION END--------------------------------------------*/
            } else {
                obj.setCamp_tbm_name("");
                obj.setCamp_tbm("");
                obj.setCamp_patch_name("");
                obj.setCamp_patch("");

                obj.setDoctor("");
                obj.setDoctor_name("");
                obj.setCamp_jfw_with_name1("");
                obj.setCamp_jfw_with_name2("");
                obj.setCamp_agenda("");
                obj.setCamp_flag(0);
            }

            if (bundle.getString("meeting").equals("1")) {

                /*----------------------------------MEETING SECTION START--------------------------------------------*/
                if (!edit_meeting_with.getText().toString().isEmpty() && !edit_meeting_place.getText().toString().isEmpty() && edit_meeting_memo.getText().toString().length() > 9) {
                    obj.setPlace(edit_meeting_place.getText().toString());
                    obj.setMeeting_with(edit_meeting_with.getText().toString());
                    obj.setMeeting_agenda(edit_meeting_memo.getText().toString());
                    obj.setMeeting_flag(1);
                    val = true;
                } else {
                    val = false;
                    pDialog.hide();
                    if (!edit_meeting_with.getText().toString().isEmpty() && !edit_meeting_place.getText().toString().isEmpty() && edit_meeting_memo.getText().toString().length() < 9) {
                        Toast.makeText(getContext(), "AGENDA Must Be More Than 10 Character's...", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "PLEASE ENTER MEETING WITH,PLACE AND AGENDA", Toast.LENGTH_SHORT).show();
                    }
                }
                /*----------------------------------MEETING SECTION END--------------------------------------------*/
            } else {
                obj.setPlace("");
                obj.setMeeting_with("");
                obj.setMeeting_agenda("");
                obj.setMeeting_flag(0);
            }

            if (bundle.getString("lve").equals("1")) {

                /*----------------------------------Leave SECTION START--------------------------------------------*/
                if ((chk_causual_leave.isChecked() == true || chk_privilege_leave.isChecked() == true || chk_sick_leave.isChecked() == true) && (edit_leave_reason.getText().toString().length() > 9)) {
                    obj.setLeave_type1(chk_causual_leave.isChecked() == true ? 1 : 0);
                    obj.setLeave_type2(chk_privilege_leave.isChecked() == true ? 1 : 0);
                    obj.setLeave_type3(chk_sick_leave.isChecked() == true ? 1 : 0);
                    obj.setReason(edit_leave_reason.getText().toString());
                    obj.setLeave_flag(1);
                    val = true;
                } else {
                    val = false;
                    pDialog.hide();
                    Toast.makeText(getContext(), "PLEASE SELECT LEAVE TYPE,ENTER REASON(More Than 10 Character's)", Toast.LENGTH_SHORT).show();
                }
                /*----------------------------------Leave SECTION END--------------------------------------------*/

            } else {
                obj.setLeave_type1(0);
                obj.setLeave_type2(0);
                obj.setLeave_type3(0);
                obj.setReason("");
                obj.setLeave_flag(0);
            }
            if (val == true) {
                if ((lat > 0 && lon > 0) || cnt_location > 2) {
                    cnt_location = 0;
                    String s1 = lat.toString();
                    String s2 = lon.toString();

                    save_todays_objective(obj, s1, s2);

                } else {
                    context = getActivity();
                    if (context != null) {

                        GPSTracker gps = new GPSTracker(getContext(), fragment_daily_plan_insert_temp2.this, false);
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
            } else {
                //Toast.makeText(getContext(), valmsg, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    class myCheckBoxChnageClicker implements CheckBox.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();
            try {
                if (isChecked) {

                    if (buttonView == chk_causual_leave) {
                        chk_privilege_leave.setChecked(false);
                        chk_sick_leave.setChecked(false);
                    }

                    if (buttonView == chk_privilege_leave) {
                        chk_causual_leave.setChecked(false);
                        chk_sick_leave.setChecked(false);
                    }

                    if (buttonView == chk_sick_leave) {
                        chk_causual_leave.setChecked(false);
                        chk_privilege_leave.setChecked(false);
                    }
                }
            } catch (Exception ex) {
                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    /*-------Show/Hidden Layout----------*/
    public void listview_show_hide() {
        try {
            bundle = this.getArguments();

            if (bundle.getString("dcr").equals("1")) {
                ll_dcr.setVisibility(View.VISIBLE);
            } else {
                ll_dcr.setVisibility(View.GONE);
            }


            if (bundle.getString("cme").equals("1")) {
                ll_cme.setVisibility(View.VISIBLE);
            } else {
                ll_cme.setVisibility(View.GONE);
            }


            if (bundle.getString("camp").equals("1")) {
                ll_camp.setVisibility(View.VISIBLE);
            } else {
                ll_camp.setVisibility(View.GONE);
            }


            if (bundle.getString("meeting").equals("1")) {
                ll_meeting.setVisibility(View.VISIBLE);
            } else {
                ll_meeting.setVisibility(View.GONE);
            }

            if (bundle.getString("lve").equals("1")) {
                ll_leave.setVisibility(View.VISIBLE);
            } else {
                ll_leave.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*----------------------Interface Override Method---------------------------------------*/
    @Override
    public void onFinishUserListHirarchyDialog(String id, String fullname, String type) {
        try {
            if (id.toString() != "NULL") {
                //////pDialog.hide();
                if (type.toString().trim().equals("EMP")) {
                    //DCR Section//
                    if (jfw == 1) {
                        jfw_of_all_21 = (TextView) getView().findViewById(R.id.jfw_of_all_21);
                        //jfw_of_all_21.setText(fullname.toString() == "NONE" ? "Select JFW with" : fullname.toString());
                        jfw_of_all_21.setText(fullname.toString() == "NONE" ? "Select" : fullname.toString());
                    } else if (jfw == 2) {
                        jfw_of_all_22 = (TextView) getView().findViewById(R.id.jfw_of_all_22);
                        //jfw_of_all_22.setText(fullname.toString() == "NONE" ? "Select JFW with" : fullname.toString());
                        jfw_of_all_22.setText(fullname.toString() == "NONE" ? "Select" : fullname.toString());
                    } else if (jfw == 3) {
                        //CAMP Section//
                        jfw_of_all_23 = (TextView) getView().findViewById(R.id.jfw_of_all_23);
                        //jfw_of_all_23.setText(fullname.toString() == "NONE" ? "Select JFW with" : fullname.toString());
                        jfw_of_all_23.setText(fullname.toString() == "NONE" ? "Select" : fullname.toString());
                    } else if (jfw == 4) {
                        jfw_of_all_24 = (TextView) getView().findViewById(R.id.jfw_of_all_24);
                        //jfw_of_all_24.setText(fullname.toString() == "NONE" ? "Select JFW with" : fullname.toString());
                        jfw_of_all_24.setText(fullname.toString() == "NONE" ? "Select" : fullname.toString());
                    }

                } else if (type.toString().trim().equals("DOC")) {
                    //DCR Section//
                    if (tbm_emp == 1) {
                        doctor_of_TBM_2 = (TextView) getView().findViewById(R.id.doctor_of_TBM_2);
                        doctor_of_TBM_2.setText(fullname.toString());

                        name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);
                        name_doctor_of_TBM.setText(id.toString());
                    } else if (tbm_emp == 2) {
                        //CAMP Section//
                        doctor_of_TBM_22 = (TextView) getView().findViewById(R.id.doctor_of_TBM_22);
                        doctor_of_TBM_22.setText(fullname.toString());

                        name_doctor_of_TBM2 = (TextView) getView().findViewById(R.id.name_doctor_of_TBM2);
                        name_doctor_of_TBM2.setText(id.toString());
                    }
                }
            } else {
                if (type.toString().trim().equals("EMP")) {
                    //DCR Section//
                    if (jfw == 1) {
                        jfw_of_all_21 = (TextView) getView().findViewById(R.id.jfw_of_all_21);
                        //jfw_of_all_21.setText("Select JFW with");
                        jfw_of_all_21.setText("Select");
                    } else if (jfw == 2) {
                        jfw_of_all_22 = (TextView) getView().findViewById(R.id.jfw_of_all_22);
                        //jfw_of_all_22.setText("Select JFW with");
                        jfw_of_all_22.setText("Select");
                    } else if (jfw == 3) {
                        //CAMP Section//
                        jfw_of_all_23 = (TextView) getView().findViewById(R.id.jfw_of_all_23);
                        //jfw_of_all_23.setText("Select JFW with");
                        jfw_of_all_23.setText("Select");
                    } else if (jfw == 4) {
                        jfw_of_all_24 = (TextView) getView().findViewById(R.id.jfw_of_all_24);
                        //jfw_of_all_24.setText("Select JFW with");
                        jfw_of_all_24.setText("Select");
                    }

                } else if (type.toString().trim().equals("DOC")) {

                    if (tbm_emp == 1) {
                        //DCR Section//
                        doctor_of_TBM_2 = (TextView) getView().findViewById(R.id.doctor_of_TBM_2);
                        //doctor_of_TBM_2.setText("Select Patch of TBM");
                        doctor_of_TBM_2.setText("Select");

                        name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);
                        name_doctor_of_TBM.setText("");
                    } else if (tbm_emp == 2) {
                        //CAMP Section//
                        doctor_of_TBM_22 = (TextView) getView().findViewById(R.id.doctor_of_TBM_22);
                        //doctor_of_TBM_22.setText("Select Doctor of TBM");
                        doctor_of_TBM_22.setText("Select");

                        name_doctor_of_TBM2 = (TextView) getView().findViewById(R.id.name_doctor_of_TBM2);
                        name_doctor_of_TBM2.setText("");
                    }
                }
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFinishTestPacthDialog(String id, String PatchName) {
        try {
            //////pDialog.hide();
            //DCR Section//
            if (pacth == 1) {
                select_patch_2 = (TextView) getView().findViewById(R.id.select_patch_2);
                select_patch_2.setText(PatchName.toString());

                name_select_patch = (TextView) getView().findViewById(R.id.name_select_patch);
                name_select_patch.setText(id.toString());
            }
            //CAMP Section//
            else if (pacth == 2) {
                select_patch_22 = (TextView) getView().findViewById(R.id.select_patch_22);
                select_patch_22.setText(PatchName.toString());

                name_select_patch2 = (TextView) getView().findViewById(R.id.name_select_patch2);
                name_select_patch2.setText(id.toString());
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*-----------DOCTOR LIST FOR CAMP---------------*/
    @Override
    public void onFinishTestDoctorDialog(String id, String DoctorName) {
        try {
            //pDialog.hide();
            select_doctor22 = (TextView) getView().findViewById(R.id.select_doctor22);
            select_doctor22.setText(DoctorName.toString());

            name_select_doctor2 = (TextView) getView().findViewById(R.id.name_select_doctor2);
            name_select_doctor2.setText(id.toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

     /*----------------------Fragment Dialog Popup Method---------------------------------------*/

    private void show_dialog_for_doctor_of_TBM() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                user_list_hierarchy_me_FragmentDialog dialog = user_list_hierarchy_me_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "TBM_ONLY");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_daily_plan_insert_temp2.this, 300);
                dialog.show(getFragmentManager(), "fragment_doctor_call_newff");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_patch(String UserID) {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                Bundle bundle = new Bundle();
                bundle.putString("Emp_ID", UserID);

                test_attch_patch_FragmentDialog dialog = test_attch_patch_FragmentDialog.newInstance("Hello world");

                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_daily_plan_insert_temp2.this, 300);
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
                dialog.setTargetFragment(fragment_daily_plan_insert_temp2.this, 300);
                dialog.show(getFragmentManager(), "fragment_doctor_call_newff");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*-----------DOCTOR LIST FOR CAMP---------------*/
    private void show_dialog_for_select_doctor(String PatchID) {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                //select_patch_2.getText().toString().contains("Select P") == false ||
                if (select_patch_22.getText().toString().contains("Select P") == false) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Patch_ID", PatchID);
                    bundle.putString("doc_all", "pp");
                    test_attch_doctor_FragmentDialog dialog = test_attch_doctor_FragmentDialog.newInstance("Hello world");

                    dialog.setArguments(bundle);
                    dialog.setTargetFragment(fragment_daily_plan_insert_temp2.this, 300);
                    dialog.show(getFragmentManager(), "fdf");

                } else {
                    Toast.makeText(getContext(), "PLEASE SELECT PATCH FIRST", Toast.LENGTH_SHORT).show();
                }
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (
                Exception ex)

        {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    /*-----------Clear Pojo For Selected Index Changed---------------*/

    private void Clear_POJO_PATCH_Doctor() {
        try {
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_Patch_master_S.class);
            mRealm.delete(POJO_Doctor_Master_S.class);
            mRealm.commitTransaction();
            mRealm.close();

            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_Doctor_Master_S.class);
            mRealm.commitTransaction();
            mRealm.close();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void Clear_POJO_Doctor() {
        try {
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_Doctor_Master_S.class);
            mRealm.commitTransaction();
            mRealm.close();
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

    //public void get_todays_objective(String dd) {
    public void get_todays_objective(String name) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String user_id = app_preferences.getString("name", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("select_date");
            jsonArray.put("objective");
            jsonArray.put("user");
            jsonArray.put("user_name");
            jsonArray.put("creation");
            jsonArray.put("modified");
            jsonArray.put("modified_by");
            jsonArray.put("owner");
            jsonArray.put("docstatus");
            jsonArray.put("doctor_flag");
            jsonArray.put("meeting_flag");
            jsonArray.put("camp_flag");
            jsonArray.put("leave_flag");

            jsonArray.put("dcr_tbm_name");
            jsonArray.put("dcr_tbm");
            jsonArray.put("select_patch_name");
            jsonArray.put("select_patch");
            jsonArray.put("dcr_jfw_with1_name");
            jsonArray.put("dcr_jfw_with2_name");
            jsonArray.put("call_agenda");

            jsonArray.put("meeting_with");
            jsonArray.put("place");
            jsonArray.put("meeting_agenda");

            jsonArray.put("camp_tbm_name");
            jsonArray.put("camp_tbm");
            jsonArray.put("camp_patch_name");
            jsonArray.put("camp_patch");
            jsonArray.put("doctor");
            jsonArray.put("doctor_name");
            jsonArray.put("doctor_flag");
            jsonArray.put("meeting_flag");
            jsonArray.put("camp_jfw_with_name1");
            jsonArray.put("camp_jfw_with_name2");
            jsonArray.put("camp_agenda");

            jsonArray.put("leave_type1");
            jsonArray.put("leave_type2");
            jsonArray.put("leave_type3");
            jsonArray.put("reason");

            jsonArray.put("leave_approval");
            jsonArray.put("leave_approved_by");
            jsonArray.put("plan_approve");
            jsonArray.put("plan_approved_by");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();

            //name,select_date,user,user_name,select_patch_name,select_patch,dcr_jfw_with1,dcr_jfw_with1_name,dcr_jfw_with2,
            // dcr_jfw_with2_name,
            //call_agenda,doctor_flag,meeting_with,place,meeting_agenda,meeting_flag,doctor,doctor_name,camp_jfw_with1,
            // camp_jfw_with_name1,
            //camp_jfw_with2,camp_jfw_with_name2,camp_agenda,camp_flag,leave_type1,leave_type2,leave_type3,reason,
            // leave_flag,leave_approval,
            //leave_approved_by,plan_approve,plan_approved_by



            /*Filter1.put("Objective");
            Filter1.put("user");
            Filter1.put("=");
            //Filter1.put(user_id);
            Filter1.put("GOKULBORADE11@GMAIL.COM");


            Filter2.put("Objective");
            Filter2.put("select_date");
            Filter2.put("=");
            Filter2.put(dd);


            Filters.put(Filter1);
            Filters.put(Filter2);*/

            Filter1.put("Objective");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(name);

            /*Tour Plan Exclude Filter
            JSONArray Filter2 = new JSONArray();
            Filter2.put("Objective");
            Filter2.put("tp_flag");
            Filter2.put("=");
            Filter2.put(0);
            Filters.put(Filter2);*/

            Filters.put(Filter1);

            pDialog.show();
            restService.getService().getObjective(sid, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j22 = j1.getAsJsonArray("data");

                        if (j22.size() > 0) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<POJO_objective>>() {
                            }.getType();
                            List<POJO_objective> POJO = gson.fromJson(j22, type);


                            if (POJO.size() > 0) {

                                POJO_objective firstA = POJO.get(0);
                                txt_plan_of_date.setText(firstA.getSelect_date());
                                if (bundle.getString("dcr").equals("1")) {
                                    if (firstA.getDoctor_flag() == 1) {

                                        name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);
                                        name_doctor_of_TBM.setText(firstA.getDcr_tbm());

                                        if (firstA.getDcr_tbm_name() != null) {
                                            //doctor_of_TBM_2.setText((firstA.getDcr_tbm_name().isEmpty()) == true ? " Select PATCH of TBM" : firstA.getDcr_tbm_name().toString());//firstA.getSelect_patch() == null
                                            doctor_of_TBM_2.setText((firstA.getDcr_tbm_name().isEmpty()) == true ? " Select" : firstA.getDcr_tbm_name().toString());//firstA.getSelect_patch() == null
                                        } else {
                                            //doctor_of_TBM_2.setText(" Select PATCH of TBM");
                                            doctor_of_TBM_2.setText(" Select");
                                        }


                                        name_select_patch = (TextView) getView().findViewById(R.id.name_select_patch);
                                        name_select_patch.setText(firstA.getSelect_patch_name());
                                        if (firstA.getSelect_patch() != null) {
                                            //select_patch_2.setText((firstA.getSelect_patch().isEmpty()) == true ? " Select Patch" : firstA.getSelect_patch().toString());
                                            select_patch_2.setText((firstA.getSelect_patch().isEmpty()) == true ? " Select" : firstA.getSelect_patch().toString());
                                        } else {
                                            //select_patch_2.setText(" Select Patch");
                                            select_patch_2.setText(" Select");
                                        }
                                        if (firstA.getDcr_jfw_with1_name() != null) {
                                            //jfw_of_all_21.setText((firstA.getDcr_jfw_with1_name().isEmpty()) == true ? " Select JFW with" : firstA.getDcr_jfw_with1_name().toString());
                                            jfw_of_all_21.setText((firstA.getDcr_jfw_with1_name().isEmpty()) == true ? " Select" : firstA.getDcr_jfw_with1_name().toString());
                                        } else {
                                            //jfw_of_all_21.setText(" Select JFW with");
                                            jfw_of_all_21.setText(" Select");
                                        }
                                        if (firstA.getDcr_jfw_with2_name() != null) {
                                            //jfw_of_all_22.setText((firstA.getDcr_jfw_with2_name().isEmpty()) == true ? " Select JFW with" : firstA.getDcr_jfw_with2_name().toString());
                                            jfw_of_all_22.setText((firstA.getDcr_jfw_with2_name().isEmpty()) == true ? " Select" : firstA.getDcr_jfw_with2_name().toString());
                                        } else {
                                            //jfw_of_all_22.setText(" Select JFW with");
                                            jfw_of_all_22.setText(" Select");
                                        }
                                        edit_dcr_agenda.setText(firstA.getCall_agenda());
                                    }
                                }
                                if (bundle.getString("camp").equals("1")) {
                                    if (firstA.getCamp_flag() == 1) {

                                        name_doctor_of_TBM2 = (TextView) getView().findViewById(R.id.name_doctor_of_TBM2);
                                        name_doctor_of_TBM2.setText(firstA.getCamp_tbm());

                                        if (firstA.getCamp_tbm_name() != null) {
                                            //doctor_of_TBM_22.setText((firstA.getCamp_tbm_name().isEmpty()) == true ? " Select Doctor of TBM" : firstA.getCamp_tbm_name().toString());
                                            doctor_of_TBM_22.setText((firstA.getCamp_tbm_name().isEmpty()) == true ? " Select" : firstA.getCamp_tbm_name().toString());
                                        } else {
                                            //doctor_of_TBM_22.setText(" Select Doctor of TBM");
                                            doctor_of_TBM_22.setText(" Select");
                                        }


                                        name_select_patch2 = (TextView) getView().findViewById(R.id.name_select_patch2);
                                        name_select_patch2.setText(firstA.getCamp_patch());

                                        if (firstA.getCamp_patch_name() != null) {
                                            //select_patch_22.setText((firstA.getCamp_patch_name().isEmpty()) == true ? " Select Patch" : firstA.getCamp_patch_name().toString());
                                            select_patch_22.setText((firstA.getCamp_patch_name().isEmpty()) == true ? " Select" : firstA.getCamp_patch_name().toString());
                                        } else {
                                            //select_patch_22.setText(" Select Patch");
                                            select_patch_22.setText(" Select");
                                        }


                                        name_select_doctor2 = (TextView) getView().findViewById(R.id.name_select_doctor2);
                                        name_select_doctor2.setText(firstA.getDoctor());

                                        if (firstA.getDoctor_name() != null) {
                                            //select_doctor22.setText((firstA.getDoctor_name().isEmpty()) == true ? " Select Doctor" : firstA.getDoctor_name().toString());
                                            select_doctor22.setText((firstA.getDoctor_name().isEmpty()) == true ? " Select" : firstA.getDoctor_name().toString());
                                        } else {
                                            //select_doctor22.setText(" Select Doctor");
                                            select_doctor22.setText(" Select");
                                        }
                                        if (firstA.getCamp_jfw_with_name1() != null) {
                                            //jfw_of_all_23.setText((firstA.getCamp_jfw_with_name1().isEmpty()) == true ? " Select JFW with" : firstA.getCamp_jfw_with_name1().toString());
                                            jfw_of_all_23.setText((firstA.getCamp_jfw_with_name1().isEmpty()) == true ? " Select" : firstA.getCamp_jfw_with_name1().toString());
                                        } else {
                                            //jfw_of_all_23.setText(" Select JFW with");
                                            jfw_of_all_23.setText(" Select");
                                        }
                                        if (firstA.getCamp_jfw_with_name2() != null) {
                                            //jfw_of_all_24.setText((firstA.getCamp_jfw_with_name2().isEmpty()) == true ? " Select JFW with" : firstA.getCamp_jfw_with_name2().toString());
                                            jfw_of_all_24.setText((firstA.getCamp_jfw_with_name2().isEmpty()) == true ? " Select" : firstA.getCamp_jfw_with_name2().toString());
                                        } else {
                                            //jfw_of_all_24.setText(" Select JFW with");
                                            jfw_of_all_24.setText(" Select");
                                        }
                                        edit_camp_agenda.setText(firstA.getCamp_agenda());
                                    }
                                }
                                if (bundle.getString("meeting").equals("1")) {
                                    if (firstA.getMeeting_flag() == 1) {
                                        edit_meeting_with.setText(firstA.getMeeting_with());
                                        edit_meeting_place.setText(firstA.getPlace());
                                        edit_meeting_memo.setText(firstA.getMeeting_agenda());
                                    }
                                }
                                if (bundle.getString("lve").equals("1")) {
                                    if (firstA.getLeave_flag() == 1) {
                                        chk_causual_leave.setChecked(firstA.getLeave_type1().equals(1));
                                        chk_privilege_leave.setChecked(firstA.getLeave_type2().equals(1));
                                        chk_sick_leave.setChecked(firstA.getLeave_type3().equals(1));
                                        edit_leave_reason.setText(firstA.getReason());
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Record Not Found", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), "ERROR...", Toast.LENGTH_SHORT).show();
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Check Internet Connection and Try Again..", Toast.LENGTH_SHORT).show();
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

            restService.getService().getTransactionFormLockOrNot(sid, "'" + name + "'", "T_Obj", txt_plan_of_date.getText().toString(), new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        String lock_flag = j2.get("lock_flag").getAsString();
                        String message = j2.get("message").getAsString();
                        if (lock_flag.equals("1")) {
                            //save_objective();
                            setClasPojoObjective();
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
                        //Toast.makeText(getContext(), "PLEASE TRY AGAIN...", Toast.LENGTH_SHORT).show();
                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            Toast.makeText(getContext(), "ERROR...", Toast.LENGTH_SHORT).show();
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

    public void save_todays_objective(POJO_objective obj, String s1, String s2) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String user_id = app_preferences.getString("name", "default");
            obj.setUser(user_id);
            obj.setUser_name(app_preferences.getString("first_name", "default") + " " + app_preferences.getString("middle_name", "default") + " " + app_preferences.getString("last_name", "default"));

            if (Object_id.equals("")) {
                pDialog.show();
                obj.setLatitude(s1);
                obj.setLongitude(s2);
                restService.getService().Objective_insert(sid, obj, new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        try {
                            pDialog.hide();
                            Toast.makeText(getContext(), "PLAN SAVED SUCCESSFULLY...", Toast.LENGTH_SHORT).show();

                            Fragment frag = new fragment_DashBoard();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, frag);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack("plan_sav");
                            ft.commit();

                        } catch (Exception ex) {
                            pDialog.hide();
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
                                Toast.makeText(getContext(), "ERROR...", Toast.LENGTH_SHORT).show();
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
                            pDialog.hide();
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (Object_id != null && Object_id != "") {
                pDialog.show();
                obj.setUp_Latitude(s1);
                obj.setUp_Longitude(s2);
                restService.getService().updateobjective(sid, obj, Object_id, new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        try {
                            pDialog.hide();
                            //update_objective(jsonElement);
                            Toast.makeText(getContext(), "PLAN UPDATED SUCCESSFULLY...", Toast.LENGTH_SHORT).show();

                            Fragment frag = new fragment_DashBoard();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, frag);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack("plan_sav");
                            ft.commit();

                        } catch (Exception ex) {
                            pDialog.hide();
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
                                Toast.makeText(getContext(), "ERROR...", Toast.LENGTH_SHORT).show();
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
                            pDialog.hide();
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (Exception ex) {
            pDialog.hide();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void CALL_CHECK_TBM_OR_NOT() {
        try {
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String designation = app_preferences.getString("designation", "default");

            LinearLayout ll_doc_call = (LinearLayout) getView().findViewById(R.id.ll_doc_call);
            LinearLayout ll_camp_call = (LinearLayout) getView().findViewById(R.id.ll_camp_call);
            View vw_doc_call = (View) getView().findViewById(R.id.vw_doc_call);
            View vw_camp_call = (View) getView().findViewById(R.id.vw_camp_call);

            if (designation.equals("TBM") == true) {
                ll_doc_call.setVisibility(View.GONE);
                ll_camp_call.setVisibility(View.GONE);
                vw_doc_call.setVisibility(View.GONE);
                vw_camp_call.setVisibility(View.GONE);
            } else {
                ll_doc_call.setVisibility(View.VISIBLE);
                ll_camp_call.setVisibility(View.VISIBLE);
                vw_doc_call.setVisibility(View.VISIBLE);
                vw_camp_call.setVisibility(View.VISIBLE);

            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void update_objective(JsonElement jsonElement) {
        try {
            JsonObject j1 = jsonElement.getAsJsonObject();
            //   JsonArray j2 = j1.getAsJsonArray("data");
            JsonElement j2 = j1.getAsJsonObject("data");
            Gson gson = new Gson();
            Type type = new TypeToken<POJO_objective_S>() {
            }.getType();
            POJO_objective_S POJO = gson.fromJson(j2, type);

            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(POJO);
            mRealm.commitTransaction();
            mRealm.close();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
        alert_box();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //String ss = provider + status;

    }

    @Override
    public void onProviderEnabled(String provider) {
        gps_enable_flag = 0;
        getLocation();
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