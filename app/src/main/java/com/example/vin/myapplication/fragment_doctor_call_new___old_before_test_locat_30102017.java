package com.example.vin.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Switch;
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
 * Use the {@link fragment_doctor_call_new___old_before_test_locat_30102017#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_doctor_call_new___old_before_test_locat_30102017 extends Fragment
        implements popup_doctor_master_new_DialogFragment.Idoc_after_save,
        DatePickerFragment.DateDialogListener,
        user_list_hierarchy_me_FragmentDialog.EditUserListHirarchyDialogListener,
        test_attch_patch_FragmentDialog.EditTestPacthDialogListener,
        test_attch_doctor_FragmentDialog.EditTestDoctorDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int year, month, day;
    private Realm mRealm;
    RestService restService;

    LinearLayout select_date;
    TextView select_date1;
    TextView select_date2;
    ImageButton select_date3;

    TextView txt_doctor_of_TBM;
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
    TextView select_doctor2;

    TextView name_doctor_of_TBM;
    TextView name_select_doctor;
    TextView name_select_patch;

    Integer jfw = 0;
    LinearLayout jfw_of_all;
    LinearLayout jfw_of_all_1;
    TextView jfw_of_all_2;
    ImageButton jfw_of_all_3;

    LinearLayout jfw_of_all2;
    LinearLayout jfw_of_all_12;
    TextView jfw_of_all_22;
    ImageButton jfw_of_all_32;

    TextView txt_hq_name;
    EditText edit_jfw;
    View view_select_tbm_of_doctor;

    EditText PPD_1;
    EditText PPD_2;
    EditText PPD_3;
    EditText PPD_4;
    EditText PPD_5;
    EditText PPD_6;
    EditText PPD_7;
    EditText PPD_8;
    EditText PPD_9;
    EditText PPD_10;
    EditText PPD_11;
    EditText PPD_12;
    EditText PPD_13;
    EditText PPD_14;
    EditText PPD_15;
    EditText PPD_16;
    EditText PPD_17;
    EditText PPD_18;

    EditText SPL_1;
    EditText SPL_2;
    EditText SPL_3;
    EditText SPL_4;
    EditText SPL_5;
    EditText SPL_6;
    EditText SPL_7;
    EditText SPL_8;
    EditText SPL_9;
    EditText SPL_10;
    EditText SPL_11;
    EditText SPL_12;
    EditText SPL_13;
    EditText SPL_14;
    EditText SPL_15;
    EditText SPL_16;
    EditText SPL_17;
    EditText SPL_18;

    EditText LBL_1;
    EditText LBL_2;
    EditText LBL_3;
    EditText LBL_4;
    EditText LBL_5;
    EditText LBL_6;
    EditText LBL_7;
    EditText LBL_8;
    EditText LBL_9;
    EditText LBL_10;
    EditText LBL_11;
    EditText LBL_12;
    EditText LBL_13;
    EditText LBL_14;
    EditText LBL_15;
    EditText LBL_16;
    EditText LBL_17;
    EditText LBL_18;


    Switch DET_1;
    Switch DET_2;
    Switch DET_3;
    Switch DET_4;
    Switch DET_5;
    Switch DET_6;
    Switch DET_7;
    Switch DET_8;
    Switch DET_9;
    Switch DET_10;
    Switch DET_11;
    Switch DET_12;
    Switch DET_13;
    Switch DET_14;
    Switch DET_15;
    Switch DET_16;
    Switch DET_17;
    Switch DET_18;

    ImageButton btn_add_patch;
    ImageButton btn_add_doctor;
    Button btn_submit;


    TextView txt_id;
    TextView txt_dr_call_by_user_id;
    EditText edit_comment;

    private ProgressDialog pDialog;
    private long mLastClickTime = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fragment_doctor_call_new___old_before_test_locat_30102017() {
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
    public static fragment_doctor_call_new___old_before_test_locat_30102017 newInstance(String param1, String param2) {
        fragment_doctor_call_new___old_before_test_locat_30102017 fragment = new fragment_doctor_call_new___old_before_test_locat_30102017();
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
        View view = inflater.inflate(R.layout.fragment_doctor_call_new, container, false);
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


    @Override
    public void onFinishSave(String fullname) {
        Toast.makeText(getContext(), "Hi, " + fullname, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onStart() {
        try {
            super.onStart();
            pDialog = new ProgressDialog(getContext());
            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            ((DashBord_main) getActivity()).setActionBarTitle("DOCTOR CALL");
            loaddate();


            init_controls();
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

            doctor_of_TBM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_patch_2.setText(" Select Patch");
                    select_doctor2.setText(" Select Doctor");
                    Clear_POJO_PATCH_Doctor();
                    show_dialog_for_doctor_of_TBM();
                }
            });
            doctor_of_TBM_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_patch_2.setText(" Select Patch");
                    select_doctor2.setText(" Select Doctor");
                    Clear_POJO_PATCH_Doctor();
                    show_dialog_for_doctor_of_TBM();
                }
            });
            doctor_of_TBM_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_patch_2.setText(" Select Patch");
                    select_doctor2.setText(" Select Doctor");
                    Clear_POJO_PATCH_Doctor();
                    show_dialog_for_doctor_of_TBM();
                }
            });
            doctor_of_TBM_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_patch_2.setText(" Select Patch");
                    select_doctor2.setText(" Select Doctor");
                    Clear_POJO_PATCH_Doctor();
                    show_dialog_for_doctor_of_TBM();
                }
            });

/////////////////////////////////
            select_patch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_patch_2.setText(" Select Patch");
                    select_doctor2.setText(" Select Doctor");
                    Clear_POJO_Doctor();
                    if (!doctor_of_TBM_2.getText().toString().equals(" Select Doctor of TBM") && !doctor_of_TBM_2.getText().toString().equals("")) {
                        show_dialog_for_select_patch(name_doctor_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                    }
                }
            });
            select_patch_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_patch_2.setText(" Select Patch");
                    select_doctor2.setText(" Select Doctor");
                    Clear_POJO_Doctor();
                    if (!doctor_of_TBM_2.getText().toString().equals(" Select Doctor of TBM") && !doctor_of_TBM_2.getText().toString().equals("")) {
                        show_dialog_for_select_patch(name_doctor_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                    }
                }
            });
            select_patch_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_patch_2.setText(" Select Patch");
                    select_doctor2.setText(" Select Doctor");
                    Clear_POJO_Doctor();
                    if (!doctor_of_TBM_2.getText().toString().equals(" Select Doctor of TBM") && !doctor_of_TBM_2.getText().toString().equals("")) {
                        show_dialog_for_select_patch(name_doctor_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                    }
                }
            });
            select_patch_3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_patch_2.setText(" Select Patch");
                    select_doctor2.setText(" Select Doctor");
                    Clear_POJO_Doctor();
                    if (!doctor_of_TBM_2.getText().toString().equals(" Select Doctor of TBM") && !doctor_of_TBM_2.getText().toString().equals("")) {
                        show_dialog_for_select_patch(name_doctor_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                    }
                }
            });

////////////////////////////////////////
            select_doctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_doctor2.setText(" Select Doctor");
                    Clear_POJO_Doctor();
                    if (!doctor_of_TBM_2.getText().toString().equals(" Select Doctor of TBM") && !doctor_of_TBM_2.getText().toString().equals("") && !select_patch_2.getText().toString().equals(" Select Patch") && !select_patch_2.getText().toString().equals("")) {
                        show_dialog_for_select_doctor(name_select_patch.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct TBM & PATCH", Toast.LENGTH_LONG).show();
                    }
                }
            });
            select_doctor1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_doctor2.setText(" Select Doctor");
                    Clear_POJO_Doctor();
                    if (!doctor_of_TBM_2.getText().toString().equals(" Select Doctor of TBM") && !doctor_of_TBM_2.getText().toString().equals("") && !select_patch_2.getText().toString().equals(" Select Patch") && !select_patch_2.getText().toString().equals("")) {
                        show_dialog_for_select_doctor(name_select_patch.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct TBM & PATCH", Toast.LENGTH_LONG).show();
                    }
                }
            });
            select_doctor2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_doctor2.setText(" Select Doctor");
                    Clear_POJO_Doctor();
                    if (!doctor_of_TBM_2.getText().toString().equals(" Select Doctor of TBM") && !doctor_of_TBM_2.getText().toString().equals("") && !select_patch_2.getText().toString().equals(" Select Patch") && !select_patch_2.getText().toString().equals("")) {
                        show_dialog_for_select_doctor(name_select_patch.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct TBM & PATCH", Toast.LENGTH_LONG).show();
                    }
                }
            });
            select_doctor3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select_doctor2.setText(" Select Doctor");
                    Clear_POJO_Doctor();
                    if (!doctor_of_TBM_2.getText().toString().equals(" Select Doctor of TBM") && !doctor_of_TBM_2.getText().toString().equals("") && !select_patch_2.getText().toString().equals(" Select Patch") && !select_patch_2.getText().toString().equals("")) {
                        show_dialog_for_select_doctor(name_select_patch.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct TBM & PATCH", Toast.LENGTH_LONG).show();
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
///////////////////////

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
////////////////////////


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

            btn_submit.setText("ADD");
            final Bundle bundle = this.getArguments();
            if (bundle != null) {
                edit_fill();


            } else {
                CALL_CHECK_TBM_OR_NOT("");
                if (Get_today_date_and_check_app_version("") == false) {
                    LinearLayout ll_below_date = (LinearLayout) getView().findViewById(R.id.ll_below_date);
                    ll_below_date.setVisibility(View.GONE);

                } else {
                    LinearLayout ll_below_date = (LinearLayout) getView().findViewById(R.id.ll_below_date);
                    ll_below_date.setVisibility(View.VISIBLE);


                    setALLEditText_to_0();
                    final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());


                    //arjun work
               /* doctor_of_TBM_2.setText(app_preferences.getString("employee", "default"));
                name_doctor_of_TBM.setText(app_preferences.getString("employee_name", "default"));*/
                    txt_dr_call_by_user_id.setText(app_preferences.getString("name", "default"));
                    String designation = app_preferences.getString("designation", "default");


                    if (designation.equals("TBM") == true) {
                        name_doctor_of_TBM.setText(app_preferences.getString("name", "default"));
                        doctor_of_TBM_2.setText(app_preferences.getString("full_name", "default"));

                    }


               /* if ((app_preferences.getString("hq_name", "default").length() == 0)) {
                    Toast.makeText(getContext(), "HQ Not Assigned ..Please Contact IT Support(Lysten Global)", Toast.LENGTH_LONG).show();
                    Fragment frag = new fragment_DashBoard();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    //frag.setArguments(bundle);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);

                    ft.commit();

                }*/
                    // txt_hq_name.setText(app_preferences.getString("hq_name", "default"));
                }

            }


            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        if ((select_doctor2.getText().toString().contains("SELECT") == true)
                                || (select_doctor2.getText().toString().contains("select") == true)
                                || (select_doctor2.getText().toString().contains("Select") == true)
                                || (select_doctor2.getText().toString().contains("NONE") == true)) {

                            Toast.makeText(getContext(), "PLEASE SELECT DOCTOR FIRST", Toast.LENGTH_SHORT).show();

                        } else {
                            lock_check();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }


            });

      /*  btn_add_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_doctor_master_new_DialogFragment dialog = popup_doctor_master_new_DialogFragment.newInstance("Hello world");

                //dialog.setView(layout);

                dialog.setTargetFragment(fragment_doctor_call_new.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
        });

        btn_add_patch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup_doctor_master_new_DialogFragment dialog = popup_doctor_master_new_DialogFragment.newInstance("Hello world");

                //dialog.setView(layout);

                dialog.setTargetFragment(fragment_doctor_call_new.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
        });*/


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void insert_doctor_calls(POJO_Doctor_Calls POJO) {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().Doctor_Calls_insert(sid, POJO, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {


                    pDialog.hide();

                    /*task_Patch_Master = new Async_Class_Load_Patch_Master_in_Realm(getActivity(), false);
                    task_Patch_Master.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/


                    Toast.makeText(getContext(), "DOCTOR CALL ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    Fragment frag = new fragment_doctor_call();
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

                    //String msg = error.getMessage();
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

    public void update_doctor_calls(POJO_Doctor_Calls POJO) {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String name = txt_id.getText().toString();

            restService.getService().Doctor_Calls_update(sid, POJO, name, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {

                    pDialog.hide();

                    Toast.makeText(getContext(), "DOCTOR CALL UPDATE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                    Fragment frag = new fragment_doctor_call();
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


    public Boolean Validation(POJO_Doctor_Calls POJO) {
        try {
            if (POJO.getDoctor_name().toString().contains("Select") == true) {
                Toast.makeText(getContext(), "PLEASE SELECT DOCTOR", Toast.LENGTH_SHORT).show();
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
            PPD_1.setText("0");
            PPD_2.setText("0");
            PPD_3.setText("0");
            PPD_4.setText("0");
            PPD_5.setText("0");
            PPD_6.setText("0");
            PPD_7.setText("0");
            PPD_8.setText("0");
            PPD_9.setText("0");
            PPD_10.setText("0");
            PPD_11.setText("0");
            PPD_12.setText("0");
            PPD_13.setText("0");
            PPD_14.setText("0");
            PPD_15.setText("0");
            PPD_16.setText("0");
            PPD_17.setText("0");
            PPD_18.setText("0");

            SPL_1.setText("0");
            SPL_2.setText("0");
            SPL_3.setText("0");
            SPL_4.setText("0");
            SPL_5.setText("0");
            SPL_6.setText("0");
            SPL_7.setText("0");
            SPL_8.setText("0");
            SPL_9.setText("0");
            SPL_10.setText("0");
            SPL_11.setText("0");
            SPL_12.setText("0");
            SPL_13.setText("0");
            SPL_14.setText("0");
            SPL_15.setText("0");
            SPL_16.setText("0");
            SPL_17.setText("0");
            SPL_18.setText("0");

            LBL_1.setText("0");
            LBL_2.setText("0");
            LBL_3.setText("0");
            LBL_4.setText("0");
            LBL_5.setText("0");
            LBL_6.setText("0");
            LBL_7.setText("0");
            LBL_8.setText("0");
            LBL_9.setText("0");
            LBL_10.setText("0");
            LBL_11.setText("0");
            LBL_12.setText("0");
            LBL_13.setText("0");
            LBL_14.setText("0");
            LBL_15.setText("0");
            LBL_16.setText("0");
            LBL_17.setText("0");
            LBL_18.setText("0");


            DET_1.setChecked(false);
            DET_2.setChecked(false);
            DET_3.setChecked(false);
            DET_4.setChecked(false);
            DET_5.setChecked(false);
            DET_6.setChecked(false);
            DET_7.setChecked(false);
            DET_8.setChecked(false);
            DET_9.setChecked(false);
            DET_10.setChecked(false);
            DET_11.setChecked(false);
            DET_12.setChecked(false);
            DET_13.setChecked(false);
            DET_14.setChecked(false);
            DET_15.setChecked(false);
            DET_16.setChecked(false);
            DET_17.setChecked(false);
            DET_18.setChecked(false);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loaddate() {
        try {
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
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    private void show_dialog_for_select_doctor() {
        try {
            DialogFragment_choose_doctor_in_dcr dialog = DialogFragment_choose_doctor_in_dcr.newInstance("Hello world");

            //dialog.setView(layout);

            dialog.setTargetFragment(fragment_doctor_call_new___old_before_test_locat_30102017.this, 300);
            dialog.show(getFragmentManager(), "fdf");
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_patch() {
        try {
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

            final RealmResults<POJO_Patch_master> result_query1;
            result_query1 = mRealm.where(POJO_Patch_master.class).equalTo("active", 1).findAll().sort("modified", Sort.DESCENDING);
            List<POJO_Patch_master> POJO = result_query1;
            for (POJO_Patch_master obj : POJO) {
                // Log.i("Contact Details", contact.getName() + "-" + contact.getEmployee() + "-" + contact.getWork_time());
            }
            adapter_patch_list_for_dialog customAdapter = new adapter_patch_list_for_dialog(getContext(), adapter_patch_list_for_dialog, POJO);

            ListView list_patch_list = (ListView) dialog.findViewById(R.id.list_patch_list);
            list_patch_list.setAdapter(customAdapter);

            list_patch_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                       @Override
                                                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                           try {
                                                               POJO_Patch_master clickedObj = (POJO_Patch_master) parent.getItemAtPosition(position);
                                                               select_patch_2.setText(clickedObj.getPatch_name());
                                                               // getPatchName_fromPatchName(clickedObj.getName(), null, total_dr_count);
                                                               // bundle.putString("patch1", clickedObj.getName());
                                                               dialog.dismiss();
                                                           } catch (Exception ex) {
                                                               Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                           }
                                                       }
                                                   }


            );
            dialog.show();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void edit_fill() {
        try {
            String Patch_type = "";
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
            jsonArray.put("dr_call_by_user_id");
            jsonArray.put("user_name");
            jsonArray.put("hq_name");
            jsonArray.put("doctor_id");
            jsonArray.put("doctor_name");
            jsonArray.put("patch_id");
            jsonArray.put("patch_name");
            jsonArray.put("jwf_with");
            jsonArray.put("jwf_with2");


            jsonArray.put("ppd_1");
            jsonArray.put("ppd_2");
            jsonArray.put("ppd_3");
            jsonArray.put("ppd_4");
            jsonArray.put("ppd_5");
            jsonArray.put("ppd_6");
            jsonArray.put("ppd_7");
            jsonArray.put("ppd_8");
            jsonArray.put("ppd_9");
            jsonArray.put("ppd_10");
            jsonArray.put("ppd_11");
            jsonArray.put("ppd_12");
            jsonArray.put("ppd_13");
            jsonArray.put("ppd_14");
            jsonArray.put("ppd_15");
            jsonArray.put("ppd_16");
            jsonArray.put("ppd_17");
            jsonArray.put("ppd_18");

            jsonArray.put("lbl_1");
            jsonArray.put("lbl_2");
            jsonArray.put("lbl_3");
            jsonArray.put("lbl_4");
            jsonArray.put("lbl_5");
            jsonArray.put("lbl_6");
            jsonArray.put("lbl_7");
            jsonArray.put("lbl_8");
            jsonArray.put("lbl_9");
            jsonArray.put("lbl_10");
            jsonArray.put("lbl_11");
            jsonArray.put("lbl_12");
            jsonArray.put("lbl_13");
            jsonArray.put("lbl_14");
            jsonArray.put("lbl_15");
            jsonArray.put("lbl_16");
            jsonArray.put("lbl_17");
            jsonArray.put("lbl_18");


            jsonArray.put("spl_1");
            jsonArray.put("spl_2");
            jsonArray.put("spl_3");
            jsonArray.put("spl_4");
            jsonArray.put("spl_5");
            jsonArray.put("spl_6");
            jsonArray.put("spl_7");
            jsonArray.put("spl_8");
            jsonArray.put("spl_9");
            jsonArray.put("spl_10");
            jsonArray.put("spl_11");
            jsonArray.put("spl_12");
            jsonArray.put("spl_13");
            jsonArray.put("spl_14");
            jsonArray.put("spl_15");
            jsonArray.put("spl_16");
            jsonArray.put("spl_17");
            jsonArray.put("spl_18");

            jsonArray.put("det_1");
            jsonArray.put("det_2");
            jsonArray.put("det_3");
            jsonArray.put("det_4");
            jsonArray.put("det_5");
            jsonArray.put("det_6");
            jsonArray.put("det_7");
            jsonArray.put("det_8");
            jsonArray.put("det_9");
            jsonArray.put("det_10");
            jsonArray.put("det_11");
            jsonArray.put("det_12");
            jsonArray.put("det_13");
            jsonArray.put("det_14");
            jsonArray.put("det_15");
            jsonArray.put("det_16");
            jsonArray.put("det_17");
            jsonArray.put("det_18");


            jsonArray.put("comment");


            //  JSONObject ofilter= new JSONObject();
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            //   JSONArray Filter2 = new JSONArray();
            JSONArray Filter3 = new JSONArray();


            Filter1.put("Doctor Calls");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(txt_id.getText());


            //  ofilter.
            Filters.put(Filter1);
            //  Filters.put(Filter2);
            // Log.i("Success ","out:"+limitstart);


            restService.getService().getDoctor_Calls(sid, "modified desc", 0, 1, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");


                        //    List<LiveWork> LiveWorks=new ArrayList<LiveWork>();

                        // JSONArray jsonArray = new JSONArray(jsonArrayString);
                        //  List<String> list = new ArrayList<String>();
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Doctor_Calls>>() {
                        }.getType();
                        List<POJO_Doctor_Calls> POJO = gson.fromJson(j2, type);


                        mRealm = Realm.getDefaultInstance();

                        mRealm.beginTransaction();

                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();


               /*  final RealmResults<Class_Doctor_Master> puppies = mRealm.where(POJO_Employee.class).findAll();
                 puppies.size();
                 Toast.makeText(_context, puppies.toString(), Toast.LENGTH_LONG).show();*/
                        mRealm.close();
                        if (txt_id.getText().toString().length() > 0) {
                            Bind__data(txt_id.getText().toString());
                            CALL_CHECK_TBM_OR_NOT(txt_dr_call_by_user_id.getText().toString());

                            if (Get_today_date_and_check_app_version(select_date2.getText().toString().trim()) == false) {
                                btn_submit.setVisibility(View.GONE);
                            }
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


                            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putString("status", "0");
                            editor.commit();

                            Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            getContext().startActivity(k);
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();

                            Fragment frag = new fragment_doctor_call();
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, frag);

                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack("fragment_doctor_call_if_not_edit_load");

                            ft.commit();

                        }


                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
            final POJO_Doctor_Calls result_query = mRealm.where(POJO_Doctor_Calls.class).equalTo("name", name).findFirst();

            //POJO_Employee POJO = result_query;


            txt_id.setText(result_query.getName());

            select_date2.setText(result_query.getDate());
            doctor_of_TBM_2.setText(result_query.getUser_name());
            select_doctor2.setText(result_query.getDoctor_name());
            select_patch_2.setText(result_query.getPatch_name());

            name_doctor_of_TBM.setText(result_query.getUser_id());
            txt_dr_call_by_user_id.setText(result_query.getDr_call_by_user_id());
            name_select_doctor.setText(result_query.getDoctor_id());
            name_select_patch.setText(result_query.getPatch_id());


            txt_hq_name.setText(result_query.getHq_name());
            //edit_jfw.setText(result_query.getJwf_with());
            jfw_of_all_2.setText(result_query.getJwf_with());
            jfw_of_all_22.setText(result_query.getJwf_with2() == null ? " Select JFW with" : result_query.getJwf_with2());

            PPD_1.setText(result_query.getPpd_1());
            PPD_2.setText(result_query.getPpd_2());
            PPD_3.setText(result_query.getPpd_3());
            PPD_4.setText(result_query.getPpd_4());
            PPD_5.setText(result_query.getPpd_5());
            PPD_6.setText(result_query.getPpd_6());
            PPD_7.setText(result_query.getPpd_7());
            PPD_8.setText(result_query.getPpd_8());
            PPD_9.setText(result_query.getPpd_9());
            PPD_10.setText(result_query.getPpd_10());
            PPD_11.setText(result_query.getPpd_11());
            PPD_12.setText(result_query.getPpd_12());
            PPD_13.setText(result_query.getPpd_13());
            PPD_14.setText(result_query.getPpd_14());
            PPD_15.setText(result_query.getPpd_15());
            PPD_16.setText(result_query.getPpd_16());
            PPD_17.setText(result_query.getPpd_17());
            PPD_18.setText(result_query.getPpd_18());


            SPL_1.setText(result_query.getSpl_1());
            SPL_2.setText(result_query.getSpl_2());
            SPL_3.setText(result_query.getSpl_3());
            SPL_4.setText(result_query.getSpl_4());
            SPL_5.setText(result_query.getSpl_5());
            SPL_6.setText(result_query.getSpl_6());
            SPL_7.setText(result_query.getSpl_7());
            SPL_8.setText(result_query.getSpl_8());
            SPL_9.setText(result_query.getSpl_9());
            SPL_10.setText(result_query.getSpl_10());
            SPL_11.setText(result_query.getSpl_11());
            SPL_12.setText(result_query.getSpl_12());
            SPL_13.setText(result_query.getSpl_13());
            SPL_14.setText(result_query.getSpl_14());
            SPL_15.setText(result_query.getSpl_15());
            SPL_16.setText(result_query.getSpl_16());
            SPL_17.setText(result_query.getSpl_17());
            SPL_18.setText(result_query.getSpl_18());

            LBL_1.setText(result_query.getLbl_1());
            LBL_2.setText(result_query.getLbl_2());
            LBL_3.setText(result_query.getLbl_3());
            LBL_4.setText(result_query.getLbl_4());
            LBL_5.setText(result_query.getLbl_5());
            LBL_6.setText(result_query.getLbl_6());
            LBL_7.setText(result_query.getLbl_7());
            LBL_8.setText(result_query.getLbl_8());
            LBL_9.setText(result_query.getLbl_9());
            LBL_10.setText(result_query.getLbl_10());
            LBL_11.setText(result_query.getLbl_11());
            LBL_12.setText(result_query.getLbl_12());
            LBL_13.setText(result_query.getLbl_13());
            LBL_14.setText(result_query.getLbl_14());
            LBL_15.setText(result_query.getLbl_15());
            LBL_16.setText(result_query.getLbl_16());
            LBL_17.setText(result_query.getLbl_17());
            LBL_18.setText(result_query.getLbl_18());


            DET_1.setChecked(Boolean.valueOf(result_query.getDet_1()));
            DET_2.setChecked(Boolean.valueOf(result_query.getDet_2()));
            DET_3.setChecked(Boolean.valueOf(result_query.getDet_3()));
            DET_4.setChecked(Boolean.valueOf(result_query.getDet_4()));
            DET_5.setChecked(Boolean.valueOf(result_query.getDet_5()));
            DET_6.setChecked(Boolean.valueOf(result_query.getDet_6()));
            DET_7.setChecked(Boolean.valueOf(result_query.getDet_7()));
            DET_8.setChecked(Boolean.valueOf(result_query.getDet_8()));
            DET_9.setChecked(Boolean.valueOf(result_query.getDet_9()));
            DET_10.setChecked(Boolean.valueOf(result_query.getDet_10()));
            DET_11.setChecked(Boolean.valueOf(result_query.getDet_11()));
            DET_12.setChecked(Boolean.valueOf(result_query.getDet_12()));
            DET_13.setChecked(Boolean.valueOf(result_query.getDet_13()));
            DET_14.setChecked(Boolean.valueOf(result_query.getDet_14()));
            DET_15.setChecked(Boolean.valueOf(result_query.getDet_15()));
            DET_16.setChecked(Boolean.valueOf(result_query.getDet_16()));
            DET_17.setChecked(Boolean.valueOf(result_query.getDet_17()));
            DET_18.setChecked(Boolean.valueOf(result_query.getDet_18()));


            edit_comment.setText(result_query.getComment());
            btn_submit.setText("UPDATE CALL INFO");


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void init_controls() {
        try {
            select_date = (LinearLayout) getView().findViewById(R.id.select_date);
            select_date1 = (TextView) getView().findViewById(R.id.select_date1);
            select_date2 = (TextView) getView().findViewById(R.id.select_date2);
            select_date3 = (ImageButton) getView().findViewById(R.id.select_date3);

            txt_doctor_of_TBM = (TextView) getView().findViewById(R.id.txt_doctor_of_TBM);
            doctor_of_TBM = (LinearLayout) getView().findViewById(R.id.doctor_of_TBM);
            doctor_of_TBM_1 = (LinearLayout) getView().findViewById(R.id.doctor_of_TBM_1);
            doctor_of_TBM_3 = (ImageButton) getView().findViewById(R.id.doctor_of_TBM_3);
            doctor_of_TBM_2 = (TextView) getView().findViewById(R.id.doctor_of_TBM_2);


            select_doctor = (LinearLayout) getView().findViewById(R.id.select_doctor);
            select_doctor1 = (LinearLayout) getView().findViewById(R.id.select_doctor1);
            select_doctor3 = (ImageButton) getView().findViewById(R.id.select_doctor3);
            select_doctor2 = (TextView) getView().findViewById(R.id.select_doctor2);


            select_patch = (LinearLayout) getView().findViewById(R.id.select_patch);
            select_patch_1 = (LinearLayout) getView().findViewById(R.id.select_patch_1);
            select_patch_3 = (ImageButton) getView().findViewById(R.id.select_patch_3);
            select_patch_2 = (TextView) getView().findViewById(R.id.select_patch_2);


            jfw_of_all = (LinearLayout) getView().findViewById(R.id.jfw_of_all);
            jfw_of_all_1 = (LinearLayout) getView().findViewById(R.id.jfw_of_all_1);
            jfw_of_all_2 = (TextView) getView().findViewById(R.id.jfw_of_all_2);
            jfw_of_all_3 = (ImageButton) getView().findViewById(R.id.jfw_of_all_3);

            jfw_of_all2 = (LinearLayout) getView().findViewById(R.id.jfw_of_all2);
            jfw_of_all_12 = (LinearLayout) getView().findViewById(R.id.jfw_of_all_12);
            jfw_of_all_22 = (TextView) getView().findViewById(R.id.jfw_of_all_22);
            jfw_of_all_32 = (ImageButton) getView().findViewById(R.id.jfw_of_all_32);

            txt_hq_name = (TextView) getView().findViewById(R.id.txt_hq_name);
            //edit_jfw = (EditText) getView().findViewById(R.id.edit_jfw);
            view_select_tbm_of_doctor = (View) getView().findViewById(R.id.view_select_tbm_of_doctor);

            PPD_1 = (EditText) getView().findViewById(R.id.PPD_1);
            PPD_2 = (EditText) getView().findViewById(R.id.PPD_2);
            PPD_3 = (EditText) getView().findViewById(R.id.PPD_3);
            PPD_4 = (EditText) getView().findViewById(R.id.PPD_4);
            PPD_5 = (EditText) getView().findViewById(R.id.PPD_5);
            PPD_6 = (EditText) getView().findViewById(R.id.PPD_6);
            PPD_7 = (EditText) getView().findViewById(R.id.PPD_7);
            PPD_8 = (EditText) getView().findViewById(R.id.PPD_8);
            PPD_9 = (EditText) getView().findViewById(R.id.PPD_9);
            PPD_10 = (EditText) getView().findViewById(R.id.PPD_10);
            PPD_11 = (EditText) getView().findViewById(R.id.PPD_11);
            PPD_12 = (EditText) getView().findViewById(R.id.PPD_12);
            PPD_13 = (EditText) getView().findViewById(R.id.PPD_13);
            PPD_14 = (EditText) getView().findViewById(R.id.PPD_14);
            PPD_15 = (EditText) getView().findViewById(R.id.PPD_15);
            PPD_16 = (EditText) getView().findViewById(R.id.PPD_16);
            PPD_17 = (EditText) getView().findViewById(R.id.PPD_17);
            PPD_18 = (EditText) getView().findViewById(R.id.PPD_18);

            SPL_1 = (EditText) getView().findViewById(R.id.SPL_1);
            SPL_2 = (EditText) getView().findViewById(R.id.SPL_2);
            SPL_3 = (EditText) getView().findViewById(R.id.SPL_3);
            SPL_4 = (EditText) getView().findViewById(R.id.SPL_4);
            SPL_5 = (EditText) getView().findViewById(R.id.SPL_5);
            SPL_6 = (EditText) getView().findViewById(R.id.SPL_6);
            SPL_7 = (EditText) getView().findViewById(R.id.SPL_7);
            SPL_8 = (EditText) getView().findViewById(R.id.SPL_8);
            SPL_9 = (EditText) getView().findViewById(R.id.SPL_9);
            SPL_10 = (EditText) getView().findViewById(R.id.SPL_10);
            SPL_11 = (EditText) getView().findViewById(R.id.SPL_11);
            SPL_12 = (EditText) getView().findViewById(R.id.SPL_12);
            SPL_13 = (EditText) getView().findViewById(R.id.SPL_13);
            SPL_14 = (EditText) getView().findViewById(R.id.SPL_14);
            SPL_15 = (EditText) getView().findViewById(R.id.SPL_15);
            SPL_16 = (EditText) getView().findViewById(R.id.SPL_16);
            SPL_17 = (EditText) getView().findViewById(R.id.SPL_17);
            SPL_18 = (EditText) getView().findViewById(R.id.SPL_18);

            LBL_1 = (EditText) getView().findViewById(R.id.LBL_1);
            LBL_2 = (EditText) getView().findViewById(R.id.LBL_2);
            LBL_3 = (EditText) getView().findViewById(R.id.LBL_3);
            LBL_4 = (EditText) getView().findViewById(R.id.LBL_4);
            LBL_5 = (EditText) getView().findViewById(R.id.LBL_5);
            LBL_6 = (EditText) getView().findViewById(R.id.LBL_6);
            LBL_7 = (EditText) getView().findViewById(R.id.LBL_7);
            LBL_8 = (EditText) getView().findViewById(R.id.LBL_8);
            LBL_9 = (EditText) getView().findViewById(R.id.LBL_9);
            LBL_10 = (EditText) getView().findViewById(R.id.LBL_10);
            LBL_11 = (EditText) getView().findViewById(R.id.LBL_11);
            LBL_12 = (EditText) getView().findViewById(R.id.LBL_12);
            LBL_13 = (EditText) getView().findViewById(R.id.LBL_13);
            LBL_14 = (EditText) getView().findViewById(R.id.LBL_14);
            LBL_15 = (EditText) getView().findViewById(R.id.LBL_15);
            LBL_16 = (EditText) getView().findViewById(R.id.LBL_16);
            LBL_17 = (EditText) getView().findViewById(R.id.LBL_17);
            LBL_18 = (EditText) getView().findViewById(R.id.LBL_18);


            DET_1 = (Switch) getView().findViewById(R.id.DET_1);
            DET_2 = (Switch) getView().findViewById(R.id.DET_2);
            DET_3 = (Switch) getView().findViewById(R.id.DET_3);
            DET_4 = (Switch) getView().findViewById(R.id.DET_4);
            DET_5 = (Switch) getView().findViewById(R.id.DET_5);
            DET_6 = (Switch) getView().findViewById(R.id.DET_6);
            DET_7 = (Switch) getView().findViewById(R.id.DET_7);
            DET_8 = (Switch) getView().findViewById(R.id.DET_8);
            DET_9 = (Switch) getView().findViewById(R.id.DET_9);
            DET_10 = (Switch) getView().findViewById(R.id.DET_10);
            DET_11 = (Switch) getView().findViewById(R.id.DET_11);
            DET_12 = (Switch) getView().findViewById(R.id.DET_12);
            DET_13 = (Switch) getView().findViewById(R.id.DET_13);
            DET_14 = (Switch) getView().findViewById(R.id.DET_14);
            DET_15 = (Switch) getView().findViewById(R.id.DET_15);
            DET_16 = (Switch) getView().findViewById(R.id.DET_16);
            DET_17 = (Switch) getView().findViewById(R.id.DET_17);
            DET_18 = (Switch) getView().findViewById(R.id.DET_18);

            txt_id = (TextView) getView().findViewById(R.id.txt_id);
            txt_dr_call_by_user_id = (TextView) getView().findViewById(R.id.txt_dr_call_by_user_id);
            edit_comment = (EditText) getView().findViewById(R.id.edit_comment);

            name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);
            name_select_doctor = (TextView) getView().findViewById(R.id.name_select_doctor);
            name_select_patch = (TextView) getView().findViewById(R.id.name_select_patch);

    /*    btn_add_patch = (ImageButton) getView().findViewById(R.id.btn_add_patch);
        btn_add_doctor = (ImageButton) getView().findViewById(R.id.btn_add_doctor);*/
            btn_submit = (Button) getView().findViewById(R.id.btn_submit);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_date() {

        try {

            DatePickerFragment dialog = new DatePickerFragment();
            dialog.setTargetFragment(fragment_doctor_call_new___old_before_test_locat_30102017.this, 300);
            dialog.show(getFragmentManager(), "fragment_doctor_call_new_date");

            //DatePickerFragment dialog = new DatePickerFragment();
            //dialog.show(getSupportFragmentManager(), DIALOG_DATE);

           /*user_list_hierarchy_FragmentDialog dialog = user_list_hierarchy_FragmentDialog.newInstance("Hello world");
            final Bundle bundle = new Bundle();
            bundle.putString("stcokiest", "N");
            dialog.setArguments(bundle);
            dialog.setTargetFragment(fragment_objective_list.this, 300);
            dialog.show(getFragmentManager(), "fdf");*/
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onBackPressed() {
        pDialog.hide();
        return false;
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
        final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String designation = app_preferences.getString("designation", "default");
        final String emp = app_preferences.getString("name", "default");

        if (designation.equals("TBM") == false) {
            doctor_of_TBM.setVisibility(View.VISIBLE);
            txt_doctor_of_TBM.setVisibility(View.VISIBLE);
            view_select_tbm_of_doctor.setVisibility(View.VISIBLE);

        } else {
            doctor_of_TBM.setVisibility(View.GONE);
            txt_doctor_of_TBM.setVisibility(View.GONE);
            view_select_tbm_of_doctor.setVisibility(View.GONE);

        }
        if (original_call_by_user_user_id.toString().trim().length() > 0) {
            if (emp.equals(original_call_by_user_user_id) == true) {
                btn_submit.setVisibility(View.VISIBLE);

            } else {
                btn_submit.setVisibility(View.GONE);

            }
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

            restService.getService().getTransactionFormLockOrNot(sid, "'" + name + "'", "T_DrC", select_date2.getText().toString(), new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        String lock_flag = j2.get("lock_flag").getAsString();
                        String message = j2.get("message").getAsString();
                        if (lock_flag.equals("1")) {
                            save_doctor_call();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();//"Oops !!! Locked Profile..."
                        }

                        /*JsonObject j1 = jsonElement.getAsJsonObject();
                        //JsonObject j2 = j1.getAsJsonObject("message");

                        //String lock_flag = j2.get("message").getAsString();
                        String lock_flag = j1.get("message").getAsString();
                        if (lock_flag == "1") {
                            save_doctor_call();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), "Oops !!! Locked Doctor Call...", Toast.LENGTH_SHORT).show();
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

    private void save_doctor_call() {
        try {

            /*This code Cut From button Click Event else part*/

            Bundle bundle = this.getArguments();
            POJO_Doctor_Calls POJO = new POJO_Doctor_Calls();
            // POJO.setName(txt_patch_id.getText().toString());


            POJO.setDate(select_date2.getText().toString());
            POJO.setUser_name(doctor_of_TBM_2.getText().toString());
            POJO.setDoctor_name(select_doctor2.getText().toString());
            POJO.setPatch_name(select_patch_2.getText().toString());

            POJO.setUser_id(name_doctor_of_TBM.getText().toString());
            POJO.setDoctor_id(name_select_doctor.getText().toString());
            POJO.setPatch_id(name_select_patch.getText().toString());

            POJO.setDr_call_by_user_id(txt_dr_call_by_user_id.getText().toString());

            POJO.setHq_name(txt_hq_name.getText().toString());
            //POJO.setJwf_with(edit_jfw.getText().toString());
            POJO.setJwf_with(jfw_of_all_2.getText().toString());
            POJO.setJwf_with2(jfw_of_all_22.getText().toString());

            POJO.setPpd_1(PPD_1.getText().toString());
            POJO.setPpd_2(PPD_2.getText().toString());
            POJO.setPpd_3(PPD_3.getText().toString());
            POJO.setPpd_4(PPD_4.getText().toString());
            POJO.setPpd_5(PPD_5.getText().toString());
            POJO.setPpd_6(PPD_6.getText().toString());
            POJO.setPpd_7(PPD_7.getText().toString());
            POJO.setPpd_8(PPD_8.getText().toString());
            POJO.setPpd_9(PPD_9.getText().toString());
            POJO.setPpd_10(PPD_10.getText().toString());
            POJO.setPpd_12(PPD_11.getText().toString());
            POJO.setPpd_12(PPD_12.getText().toString());
            POJO.setPpd_13(PPD_13.getText().toString());
            POJO.setPpd_14(PPD_14.getText().toString());
            POJO.setPpd_15(PPD_15.getText().toString());
            POJO.setPpd_16(PPD_16.getText().toString());
            POJO.setPpd_17(PPD_17.getText().toString());
            POJO.setPpd_18(PPD_18.getText().toString());

            POJO.setSpl_1(SPL_1.getText().toString());
            POJO.setSpl_2(SPL_2.getText().toString());
            POJO.setSpl_3(SPL_3.getText().toString());
            POJO.setSpl_4(SPL_4.getText().toString());
            POJO.setSpl_5(SPL_5.getText().toString());
            POJO.setSpl_6(SPL_6.getText().toString());
            POJO.setSpl_7(SPL_7.getText().toString());
            POJO.setSpl_8(SPL_8.getText().toString());
            POJO.setSpl_9(SPL_9.getText().toString());
            POJO.setSpl_10(SPL_10.getText().toString());
            POJO.setSpl_11(SPL_11.getText().toString());
            POJO.setSpl_12(SPL_12.getText().toString());
            POJO.setSpl_13(SPL_13.getText().toString());
            POJO.setSpl_14(SPL_14.getText().toString());
            POJO.setSpl_15(SPL_15.getText().toString());
            POJO.setSpl_16(SPL_16.getText().toString());
            POJO.setSpl_17(SPL_17.getText().toString());
            POJO.setSpl_18(SPL_18.getText().toString());

            POJO.setLbl_1(LBL_1.getText().toString());
            POJO.setLbl_2(LBL_2.getText().toString());
            POJO.setLbl_3(LBL_3.getText().toString());
            POJO.setLbl_4(LBL_4.getText().toString());
            POJO.setLbl_5(LBL_5.getText().toString());
            POJO.setLbl_6(LBL_6.getText().toString());
            POJO.setLbl_7(LBL_7.getText().toString());
            POJO.setLbl_8(LBL_8.getText().toString());
            POJO.setLbl_9(LBL_9.getText().toString());
            POJO.setLbl_10(LBL_10.getText().toString());
            POJO.setLbl_11(LBL_11.getText().toString());
            POJO.setLbl_12(LBL_12.getText().toString());
            POJO.setLbl_13(LBL_13.getText().toString());
            POJO.setLbl_14(LBL_14.getText().toString());
            POJO.setLbl_15(LBL_15.getText().toString());
            POJO.setLbl_16(LBL_16.getText().toString());
            POJO.setLbl_17(LBL_17.getText().toString());
            POJO.setLbl_18(LBL_18.getText().toString());

            POJO.setDet_1(String.valueOf(DET_1.isChecked()));
            POJO.setDet_2(String.valueOf(DET_2.isChecked()));
            POJO.setDet_3(String.valueOf(DET_3.isChecked()));
            POJO.setDet_4(String.valueOf(DET_4.isChecked()));
            POJO.setDet_5(String.valueOf(DET_5.isChecked()));
            POJO.setDet_6(String.valueOf(DET_6.isChecked()));
            POJO.setDet_7(String.valueOf(DET_7.isChecked()));
            POJO.setDet_8(String.valueOf(DET_8.isChecked()));
            POJO.setDet_9(String.valueOf(DET_9.isChecked()));
            POJO.setDet_10(String.valueOf(DET_10.isChecked()));
            POJO.setDet_11(String.valueOf(DET_11.isChecked()));
            POJO.setDet_12(String.valueOf(DET_12.isChecked()));
            POJO.setDet_13(String.valueOf(DET_13.isChecked()));
            POJO.setDet_14(String.valueOf(DET_14.isChecked()));
            POJO.setDet_15(String.valueOf(DET_15.isChecked()));
            POJO.setDet_16(String.valueOf(DET_16.isChecked()));
            POJO.setDet_17(String.valueOf(DET_17.isChecked()));
            POJO.setDet_18(String.valueOf(DET_18.isChecked()));

            POJO.setComment(edit_comment.getText().toString());

            pDialog.show();
            if (Validation(POJO) == true) {
                if (bundle != null) {
                    update_doctor_calls(POJO);
                } else {
                    insert_doctor_calls(POJO);
                }
            } else {
                pDialog.hide();
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*
-----------------------

Interface Implement Methods With return Value Start

-----------------------
 */

    @Override
    public void onFinishUserListHirarchyDialog(String id, String fullname, String type) {


        try {
            if (id.toString() != "NULL") {
                pDialog.hide();
                if (type.toString().trim().equals("EMP")) {
                    if (jfw == 1) {
                        TextView jfw_of_all_2 = (TextView) getView().findViewById(R.id.jfw_of_all_2);
                        jfw_of_all_2.setText(fullname.toString());
                    } else if (jfw == 2) {
                        TextView jfw_of_all_22 = (TextView) getView().findViewById(R.id.jfw_of_all_22);
                        jfw_of_all_22.setText(fullname.toString());
                    }

                } else if (type.toString().trim().equals("DOC")) {

                    TextView doctor_of_TBM_2 = (TextView) getView().findViewById(R.id.doctor_of_TBM_2);
                    doctor_of_TBM_2.setText(fullname.toString());

                    TextView name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);
                    name_doctor_of_TBM.setText(id.toString());

                }
            } else {
                if (type.toString().trim().equals("EMP")) {
                    if (jfw == 1) {
                        TextView jfw_of_all_2 = (TextView) getView().findViewById(R.id.jfw_of_all_2);
                        jfw_of_all_2.setText("Select JFW with");
                    } else if (jfw == 2) {
                        TextView jfw_of_all_22 = (TextView) getView().findViewById(R.id.jfw_of_all_22);
                        jfw_of_all_22.setText("Select JFW with");
                    }

                } else if (type.toString().trim().equals("DOC")) {

                    TextView doctor_of_TBM_2 = (TextView) getView().findViewById(R.id.doctor_of_TBM_2);
                    doctor_of_TBM_2.setText("Select Doctor");

                    TextView name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);
                    name_doctor_of_TBM.setText("");

                }
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onFinishTestPacthDialog(String id, String PatchName) {
        try {
            pDialog.hide();
            TextView select_patch_2 = (TextView) getView().findViewById(R.id.select_patch_2);
            select_patch_2.setText(PatchName.toString());

            TextView name_select_patch = (TextView) getView().findViewById(R.id.name_select_patch);
            name_select_patch.setText(id.toString());
            //PatchID = id.toString();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishTestDoctorDialog(String id, String DoctorName) {
        try {
            pDialog.hide();
            TextView select_doctor_2 = (TextView) getView().findViewById(R.id.select_doctor2);
            select_doctor_2.setText(DoctorName.toString());

            TextView name_select_doctor = (TextView) getView().findViewById(R.id.name_select_doctor);
            name_select_doctor.setText(id.toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*@Override
    public void onFinishTestChemistDialog(String id, String ChemistName) {
        try {
            TextView select_chemist_2 = (TextView) getView().findViewById(R.id.select_chemist_2);
            select_chemist_2.setText(ChemistName.toString());

            TextView name_select_chemist = (TextView) getView().findViewById(R.id.name_select_chemist);
            name_select_chemist.setText(id.toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishTestStockistDialog(String id, String StockistName) {
        try {
            TextView select_stockist_2 = (TextView) getView().findViewById(R.id.select_stockist_2);
            select_stockist_2.setText(StockistName.toString());

            TextView name_select_stockist = (TextView) getView().findViewById(R.id.name_select_stockist);
            name_select_stockist.setText(id.toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }*/


/*
-----------------------

Interface Implement Methods With return Value Start

-----------------------
 */



/*
-----------------------

Call Dialog Fragment Start

-----------------------
*/

    private void show_dialog_for_doctor_of_TBM() {


        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                user_list_hierarchy_me_FragmentDialog dialog = user_list_hierarchy_me_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "TBM_ONLY");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_doctor_call_new___old_before_test_locat_30102017.this, 300);
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
                dialog.setTargetFragment(fragment_doctor_call_new___old_before_test_locat_30102017.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_doctor(String PatchID) {

        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                if (select_patch_2.getText().toString().contains("Select P") == false) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Patch_ID", PatchID);
                    bundle.putString("doc_all", "pp");
                    test_attch_doctor_FragmentDialog dialog = test_attch_doctor_FragmentDialog.newInstance("Hello world");

                    dialog.setArguments(bundle);
                    dialog.setTargetFragment(fragment_doctor_call_new___old_before_test_locat_30102017.this, 300);
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


    private void show_dialog_for_jwf_top_down_emp() {


        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                user_list_hierarchy_me_FragmentDialog dialog = user_list_hierarchy_me_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "top_down_jfw");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_doctor_call_new___old_before_test_locat_30102017.this, 300);
                dialog.show(getFragmentManager(), "fragment_doctor_call_newff");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }


    }

    ////////////Next Code

    private void show_dialog_for_select_chemist(String UserID) {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                Bundle bundle = new Bundle();
                bundle.putString("Emp_ID", UserID);

                test_attch_chemist_FragmentDialog dialog = test_attch_chemist_FragmentDialog.newInstance("Hello world");

                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_doctor_call_new___old_before_test_locat_30102017.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_stockist() {

        try {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                test_attch_stockist_FragmentDialog dialog = test_attch_stockist_FragmentDialog.newInstance("Hello world");

                dialog.setTargetFragment(fragment_doctor_call_new___old_before_test_locat_30102017.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

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
/*
-----------------------

Call Dialog Fragment Start

-----------------------
*/


}
