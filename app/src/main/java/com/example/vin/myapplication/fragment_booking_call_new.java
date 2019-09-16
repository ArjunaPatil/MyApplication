package com.example.vin.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.InputType;
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
 * Use the {@link fragment_booking_call_new#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_booking_call_new extends Fragment implements
        DatePickerFragment.DateDialogListener,
        user_list_hierarchy_me_FragmentDialog.EditUserListHirarchyDialogListener,
        test_attch_chemist_FragmentDialog.EditTestChemistDialogListener,
        test_attch_stockist_FragmentDialog.EditTestStockistDialogListener,
        campaign_list_FragmentDialog.EditDesignationListHirarchyDialogListener,
        test_attch_doctor_FragmentDialog.EditTestDoctorDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int year, month, day;
    private Realm mRealm;
    RestService restService;
    /* LinearLayout chemist_of_TBM;
     LinearLayout chemist_of_TBM_1;

     ImageButton chemist_of_TBM_3;*/

    LinearLayout select_doctor;
    LinearLayout select_doctor1;
    TextView select_doctor2;
    ImageButton select_doctor3;
    TextView name_select_doctor;

    LinearLayout select_date;
    TextView select_date1;
    TextView select_date2;
    ImageButton select_date3;

    LinearLayout select_chemist;
    LinearLayout select_chemist1;
    TextView select_chemist2;
    ImageButton select_chemist3;
    TextView name_select_chemist;

    LinearLayout chemist_of_TBM;
    LinearLayout chemist_of_TBM_1;
    TextView chemist_of_TBM_2;
    ImageButton chemist_of_TBM_3;
    TextView name_chemist_of_TBM;

    ImageButton btn_add_chemist;


    LinearLayout select_camp1;
    TextView select_camp2;
    ImageButton select_camp3;
    /* TextView name_select_camp;
 */
    TextView camp_details;
    EditText edit_qty;
    EditText edit_f_qty;
    EditText edit_t_qty;
    EditText edit_t_amt;

    LinearLayout select_stockist;
    LinearLayout select_stockist1;
    TextView select_stockist2;
    ImageButton select_stockist3;
    TextView name_select_stockist;


    EditText edit_inv_num;
    EditText edit_comment;

    Button btn_submit;


    TextView txt_hq_name;

    TextView txt_id;
    TextView txt_dr_call_by_user_id;
    TextView edit_qty_lbl;
    TextView edit_f_qty_lbl;
    TextView edit_t_qty_lbl;
    TextView edit_t_amt_lbl;

    LinearLayout ll_chemist_layout;
    LinearLayout ll_doctor_layout;

    View view_select_tbm_of_doctor;
    private ProgressDialog pDialog;
    private long mLastClickTime = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public fragment_booking_call_new() {
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
    public static fragment_booking_call_new newInstance(String param1, String param2) {
        fragment_booking_call_new fragment = new fragment_booking_call_new();
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
        View view = inflater.inflate(R.layout.fragment_booking_call_new, container, false);
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
            final Bundle bundle = this.getArguments();
            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            ((DashBord_main) getActivity()).setActionBarTitle("NEW CAMPAIGN BOOKING");
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

            select_chemist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //select_chemist2.setText(" Select Chemist");
                    select_chemist2.setText(" Select");
                    Clear_POJO_CHEMIST();
                    if (!chemist_of_TBM_2.equals("default") && !chemist_of_TBM_2.equals("")) {
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
                    if (!chemist_of_TBM_2.equals("default") && !chemist_of_TBM_2.equals("")) {
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
                    if (!chemist_of_TBM_2.equals("default") && !chemist_of_TBM_2.equals("")) {
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
                    if (!chemist_of_TBM_2.equals("default") && !chemist_of_TBM_2.equals("")) {
                        show_dialog_for_select_chemist(name_chemist_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct Employee", Toast.LENGTH_LONG).show();
                    }

                }
            });

            select_doctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Clear_POJO_Doctor();
                    if (!chemist_of_TBM_2.equals(" My Name") && !chemist_of_TBM_2.equals("")) {
                        show_dialog_for_select_doctor(name_chemist_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct TBM ", Toast.LENGTH_LONG).show();
                    }
                }
            });
            select_doctor1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Clear_POJO_Doctor();
                    if (!chemist_of_TBM_2.equals(" My Name") && !chemist_of_TBM_2.equals("")) {
                        show_dialog_for_select_doctor(name_chemist_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct TBM ", Toast.LENGTH_LONG).show();
                    }
                }
            });
            select_doctor2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Clear_POJO_Doctor();
                    if (!chemist_of_TBM_2.equals(" My Name") && !chemist_of_TBM_2.equals("")) {
                        show_dialog_for_select_doctor(name_chemist_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct TBM ", Toast.LENGTH_LONG).show();
                    }
                }
            });
            select_doctor3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Clear_POJO_Doctor();
                    if (!chemist_of_TBM_2.equals(" My Name") && !chemist_of_TBM_2.equals("")) {
                        show_dialog_for_select_doctor(name_chemist_of_TBM.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Please Select Correct TBM ", Toast.LENGTH_LONG).show();
                    }
                }
            });

//////////////////////////////

            select_stockist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_stockist();

                }
            });
            select_stockist1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_stockist();

                }
            });
            select_stockist2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_stockist();

                }
            });
            select_stockist3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_stockist();

                }
            });

            select_camp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_camp();

                }
            });
            ;
            select_camp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_camp();

                }
            });
            ;
            select_camp3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_camp();
                }
            });
            ;

////////////////////////////

           /* LinearLayout ll_call_details = (LinearLayout) getView().findViewById(R.id.ll_call_details);
            ll_call_details.setVisibility(View.GONE);
            final Switch switch_booking = (Switch) getView().findViewById(R.id.switch_booking);
            switch_booking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout ll_call_details = (LinearLayout) getView().findViewById(R.id.ll_call_details);
                    if (switch_booking.isChecked() == true) {

                        ll_call_details.setVisibility(View.VISIBLE);
                    } else {
                        ll_call_details.setVisibility(View.GONE);
                    }

                }
            });*/

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if ((select_camp2.getText().toString().contains("SELECT") == true)
                                || (select_camp2.getText().toString().contains("select") == true)
                                || (select_camp2.getText().toString().contains("Select") == true)
                                || (select_camp2.getText().toString().contains("NONE") == true)) {

                            Toast.makeText(getContext(), "PLEASE SELECT CAMPAIGN FIRST", Toast.LENGTH_SHORT).show();

                        } else {
                            lock_check();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            btn_add_chemist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  popup_doctor_master_new_DialogFragment dialog = popup_doctor_master_new_DialogFragment.newInstance("Hello world");

                    //dialog.setView(layout);

                    dialog.setTargetFragment(fragment_doctor_call_new.this, 300);
                    dialog.show(getFragmentManager(), "fdf");*/
                }
            });


            btn_submit.setText("ADD");

            if (bundle != null) {
                edit_fill();


            } else {


                edit_qty.setText("0");
                edit_f_qty.setText("0");
                edit_t_qty.setText("0");
                edit_t_amt.setText("0");

                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());


                txt_dr_call_by_user_id.setText(app_preferences.getString("name", "default"));
                String designation = app_preferences.getString("designation", "default");


                if (designation.equals("TBM") == true) {
                    name_chemist_of_TBM.setText(app_preferences.getString("name", "default"));
                    chemist_of_TBM_2.setText(app_preferences.getString("full_name", "default"));

                }


            }


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
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

    private void show_dialog_for_select_camp() {
        try {
            campaign_list_FragmentDialog dialog = campaign_list_FragmentDialog.newInstance("Hello world");

            dialog.setTargetFragment(fragment_booking_call_new.this, 300);
            dialog.show(getFragmentManager(), "fdf");
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

    public void insert_camp_booking(POJO_campaign_booking POJO) {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().campaign_booking_insert(sid, POJO, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {

                    try {
                        pDialog.hide();

                    /*task_Patch_Master = new Async_Class_Load_Patch_Master_in_Realm(getActivity(), false);
                    task_Patch_Master.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/


                        Toast.makeText(getContext(), "CAMPAIGN BOOKING ADDED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        Fragment frag = new fragment_booking_call();
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
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void update_camp_booking(POJO_campaign_booking POJO) {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String name = txt_id.getText().toString();

            restService.getService().campaign_booking_update(sid, POJO, name, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();

                        Toast.makeText(getContext(), "CAMPAIGN BOOKING UPADTED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        Fragment frag = new fragment_booking_call();
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
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loaddoctor() {


    }

    private void show_dialog_for_chemist_of_TBM() {
        /*final Dialog dialog = new Dialog(getContext());

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

        final RealmResults<POJO_User> result_query1;
        result_query1 = mRealm.where(POJO_User.class).equalTo("enabled", 1).findAll().sort("modified", Sort.DESCENDING);
        List<POJO_User> POJO = result_query1;
        for (POJO_User obj : POJO) {
            // Log.i("Contact Details", contact.getName() + "-" + contact.getEmployee() + "-" + contact.getWork_time());
        }
        adapter_user_list_for_dialog customAdapter = new adapter_user_list_for_dialog(getContext(), adapter_patch_list_for_dialog, POJO);

        ListView list_patch_list = (ListView) dialog.findViewById(R.id.list_patch_list);
        list_patch_list.setAdapter(customAdapter);

        list_patch_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                   @Override
                                                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                       POJO_User clickedObj = (POJO_User) parent.getItemAtPosition(position);
                                                       chemist_of_TBM_2.setText(clickedObj.getFull_name());
                                                       // getPatchName_fromPatchName(clickedObj.getName(), null, total_dr_count);
                                                       // bundle.putString("patch1", clickedObj.getName());
                                                       dialog.dismiss();

                                                   }
                                               }


        );
        dialog.show();
*/

    }

    private void show_dialog_for_chemist() {
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

            chemist_of_TBM = (LinearLayout) getView().findViewById(R.id.chemist_of_TBM);
            chemist_of_TBM_1 = (LinearLayout) getView().findViewById(R.id.chemist_of_TBM_1);
            chemist_of_TBM_3 = (ImageButton) getView().findViewById(R.id.chemist_of_TBM_3);
            chemist_of_TBM_2 = (TextView) getView().findViewById(R.id.chemist_of_TBM_2);
            name_chemist_of_TBM = (TextView) getView().findViewById(R.id.name_chemist_of_TBM);

            btn_add_chemist = (ImageButton) getView().findViewById(R.id.btn_add_chemist);


            select_chemist = (LinearLayout) getView().findViewById(R.id.select_chemist);
            select_chemist1 = (LinearLayout) getView().findViewById(R.id.select_chemist1);
            select_chemist3 = (ImageButton) getView().findViewById(R.id.select_chemist3);
            select_chemist2 = (TextView) getView().findViewById(R.id.select_chemist2);
            name_select_chemist = (TextView) getView().findViewById(R.id.name_select_chemist);

            select_camp1 = (LinearLayout) getView().findViewById(R.id.select_camp1);
            select_camp2 = (TextView) getView().findViewById(R.id.select_camp2);
            select_camp3 = (ImageButton) getView().findViewById(R.id.select_camp3);

       /* name_select_camp = (TextView) getView().findViewById(name_select_camp);*/


            camp_details = (TextView) getView().findViewById(R.id.camp_details);

            edit_qty = (EditText) getView().findViewById(R.id.edit_qty);
            edit_f_qty = (EditText) getView().findViewById(R.id.edit_f_qty);
            edit_t_qty = (EditText) getView().findViewById(R.id.edit_t_qty);
            edit_t_amt = (EditText) getView().findViewById(R.id.edit_t_amt);

            select_stockist = (LinearLayout) getView().findViewById(R.id.select_stockist);
            select_stockist1 = (LinearLayout) getView().findViewById(R.id.select_stockist1);
            select_stockist2 = (TextView) getView().findViewById(R.id.select_stockist2);
            select_stockist3 = (ImageButton) getView().findViewById(R.id.select_stockist3);
            name_select_stockist = (TextView) getView().findViewById(R.id.name_select_stockist);

            edit_qty_lbl = (TextView) getView().findViewById(R.id.edit_qty_lbl);
            edit_f_qty_lbl = (TextView) getView().findViewById(R.id.edit_f_qty_lbl);
            edit_t_qty_lbl = (TextView) getView().findViewById(R.id.edit_t_qty_lbl);
            edit_t_amt_lbl = (TextView) getView().findViewById(R.id.edit_t_amt_lbl);

            edit_inv_num = (EditText) getView().findViewById(R.id.edit_inv_num);
            edit_comment = (EditText) getView().findViewById(R.id.edit_comment);

            btn_submit = (Button) getView().findViewById(R.id.btn_submit);


            txt_hq_name = (TextView) getView().findViewById(R.id.txt_hq_name);
            txt_id = (TextView) getView().findViewById(R.id.txt_id);
            txt_dr_call_by_user_id = (TextView) getView().findViewById(R.id.txt_dr_call_by_user_id);

            ll_chemist_layout = (LinearLayout) getView().findViewById(R.id.select_stockist);
            ll_doctor_layout = (LinearLayout) getView().findViewById(R.id.select_stockist);

            select_doctor = (LinearLayout) getView().findViewById(R.id.select_doctor);
            select_doctor1 = (LinearLayout) getView().findViewById(R.id.select_doctor1);
            select_doctor3 = (ImageButton) getView().findViewById(R.id.select_doctor3);
            select_doctor2 = (TextView) getView().findViewById(R.id.select_doctor2);
            name_select_doctor = (TextView) getView().findViewById(R.id.name_select_doctor);
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
            jsonArray.put("call_by_user_id");
            jsonArray.put("user_name");
            jsonArray.put("hq_name");
            jsonArray.put("chemist_id");
            jsonArray.put("chemist_name");
            jsonArray.put("campaign_id");
            jsonArray.put("campaign_name");
            jsonArray.put("stockist_id");
            jsonArray.put("stockist_name");
            jsonArray.put("doctor_id");
            jsonArray.put("doctor_name");


            jsonArray.put("qty");
            jsonArray.put("free");
            jsonArray.put("total_qty");
            jsonArray.put("total_amount");
            jsonArray.put("invoice_number");


            jsonArray.put("any_comment");


            //  JSONObject ofilter= new JSONObject();
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            //   JSONArray Filter2 = new JSONArray();
            JSONArray Filter3 = new JSONArray();


            Filter1.put("campaign_booking");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(txt_id.getText());


            //  ofilter.
            Filters.put(Filter1);
            //  Filters.put(Filter2);
            // Log.i("Success ","out:"+limitstart);


            restService.getService().get_campaign_booking(sid, "modified desc", 0, 1, jsonArray, Filters, new Callback<JsonElement>() {
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
                        Type type = new TypeToken<List<POJO_campaign_booking>>() {
                        }.getType();
                        List<POJO_campaign_booking> POJO = gson.fromJson(j2, type);


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
                            Toast.makeText(getContext(), "PLEASE TRY AGAIN", Toast.LENGTH_SHORT).show();
              /*          Intent k = new Intent(getContext(), Login.class); //got ot login activity
                        getContext().startActivity(k);*/
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

        } catch (Exception ex)

        {
            pDialog.hide();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public Boolean Validation(POJO_campaign_booking POJO) {
        try {
            //if (select_employee2.getText().toString().contains("Select") == false)
            if (POJO.getCampaign_name().toString().contains("Select") == true) {//" SELECT CAMPAIGN"
                Toast.makeText(getContext(), "PLEASE SELECT CAMPAIGN", Toast.LENGTH_SHORT).show();
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
            } else {

            }*/
            return true;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public void Bind__data(String name) {
        try {
            Realm mRealm = Realm.getDefaultInstance();


            //final RealmResults<POJO_Employee> puppies = mRealm.where(POJO_Employee.class).equalTo("select_date",fromDateEtxt.getText().toString()).findAll();
            final POJO_campaign_booking result_query = mRealm.where(POJO_campaign_booking.class).equalTo("name", name).findFirst();

            //POJO_Employee POJO = result_query;


            txt_id.setText(result_query.getName());

            select_date2.setText(result_query.getDate());
            chemist_of_TBM_2.setText(result_query.getUser_name());
            select_chemist2.setText(result_query.getChemist_name());
            select_camp2.setText(result_query.getCampaign_name());
            select_stockist2.setText(result_query.getStockist_name());
            txt_dr_call_by_user_id.setText(result_query.getCall_by_user_id());
            name_chemist_of_TBM.setText(result_query.getUser_id());
            name_select_chemist.setText(result_query.getChemist_id());
            name_select_stockist.setText(result_query.getStockist_id());
           /* name_select_camp.setText(result_query.getCampaign_id());
*/
            select_doctor2.setText(result_query.getDoctor_name());
            name_select_doctor.setText(result_query.getDoctor__id());
            txt_hq_name.setText(result_query.getHq_name());


            edit_qty.setText(result_query.getQty());
            edit_f_qty.setText(result_query.getFree());
            edit_t_qty.setText(result_query.getTotal_qty());
            edit_t_amt.setText(result_query.getTotal_amount());


            edit_inv_num.setText(result_query.getInvoice_number());


            edit_comment.setText(result_query.getAny_comment());
            btn_submit.setText("UPDATE CALL INFO");

            if (select_camp2.getText().toString().trim().toString().equals("Mission 1.50") == false) {


                edit_qty_lbl.setText("QTY");
                edit_f_qty_lbl.setText("FREE QTY");
                edit_t_qty_lbl.setText("TOTAL QTY");
                edit_t_amt_lbl.setText("TOTAL AMOUNT");

           /* ll_chemist_layout.setVisibility(View.VISIBLE);
            ll_doctor_layout.setVisibility(View.VISIBLE);*/
            } else {


                edit_qty_lbl.setText("GOLD COIN");
                edit_f_qty_lbl.setText("SHIRT");
                edit_t_qty_lbl.setText("GIFT CARD");
                edit_t_amt_lbl.setText("CASH");
          /*  ll_chemist_layout.setVisibility(View.GONE);
            ll_doctor_layout.setVisibility(View.VISIBLE);*/
            }


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onFinishUserListHirarchyDialog(String id, String fullname, String type) {


       /* try {
            //TextView doctor_of_TBM_2 = (TextView) getView().findViewById(R.id.chemist_of_TBM_2);
            chemist_of_TBM_2.setText(fullname.toString());

           // TextView name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_chemist_of_TBM);
            name_chemist_of_TBM.setText(id.toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
*/

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

    @Override
    public void onFinishTestStockistDialog(String id, String StockistName) {
        try {
            if (id.toString().trim().equals("stockist_null")) {
                alert_box();
            } else {
                TextView select_stockist_2 = (TextView) getView().findViewById(R.id.select_stockist2);
                select_stockist_2.setText(StockistName.toString());

                TextView name_select_stockist = (TextView) getView().findViewById(R.id.name_select_stockist);
                name_select_stockist.setText(id.toString());
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishDesignationListHirarchyDialog(String Designation) {
        try {
            if (Designation.trim().toString().contains("Mission 1.50") == true) {


                TextView select_camp2 = (TextView) getView().findViewById(R.id.select_camp2);
                select_camp2.setText(Designation.toString());
                edit_qty_lbl.setText("GOLD COIN");
                edit_f_qty_lbl.setText("SHIRT");
                edit_t_qty_lbl.setText("GIFT CARD");
                edit_t_amt_lbl.setText("CASH");

                edit_qty.setText("0");
                edit_f_qty.setText("0");
                edit_t_qty.setText("0");
                edit_t_amt.setText("0");


                edit_qty.setInputType(InputType.TYPE_CLASS_NUMBER);
                edit_f_qty.setInputType(InputType.TYPE_CLASS_NUMBER);
                edit_t_qty.setInputType(InputType.TYPE_CLASS_NUMBER);
                edit_t_amt.setInputType(InputType.TYPE_CLASS_NUMBER);

            } else if (Designation.trim().toString().contains("150+45") == true) {

                TextView select_camp2 = (TextView) getView().findViewById(R.id.select_camp2);
                select_camp2.setText(Designation.toString());

                edit_qty_lbl.setText("QTY");
                edit_f_qty_lbl.setText("FREE QTY");
                edit_t_qty_lbl.setText("TOTAL QTY");
                edit_t_amt_lbl.setText("VALUE");

                edit_qty.setText("150");
                edit_f_qty.setText("45");
                edit_t_qty.setText("195");
                edit_t_amt.setText("3390");

                edit_qty.setInputType(InputType.TYPE_NULL);
                edit_f_qty.setInputType(InputType.TYPE_NULL);
                edit_t_qty.setInputType(InputType.TYPE_NULL);
                edit_t_amt.setInputType(InputType.TYPE_NULL);

            } else if (Designation.trim().toString().contains("100+30") == true) {

                TextView select_camp2 = (TextView) getView().findViewById(R.id.select_camp2);
                select_camp2.setText(Designation.toString());

                edit_qty_lbl.setText("QTY");
                edit_f_qty_lbl.setText("FREE QTY");
                edit_t_qty_lbl.setText("TOTAL QTY");
                edit_t_amt_lbl.setText("VALUE");

                edit_qty.setText("100");
                edit_f_qty.setText("30");
                edit_t_qty.setText("130");
                edit_t_amt.setText("2260");

                edit_qty.setInputType(InputType.TYPE_NULL);
                edit_f_qty.setInputType(InputType.TYPE_NULL);
                edit_t_qty.setInputType(InputType.TYPE_NULL);
                edit_t_amt.setInputType(InputType.TYPE_NULL);


            } else if (Designation.trim().toString().contains("50+15") == true) {

                TextView select_camp2 = (TextView) getView().findViewById(R.id.select_camp2);
                select_camp2.setText(Designation.toString());

                edit_qty_lbl.setText("QTY");
                edit_f_qty_lbl.setText("FREE QTY");
                edit_t_qty_lbl.setText("TOTAL QTY");
                edit_t_amt_lbl.setText("VALUE");

                edit_qty.setText("50");
                edit_f_qty.setText("15");
                edit_t_qty.setText("65");
                edit_t_amt.setText("1130");

            } else if (Designation.trim().toString().contains("30+9") == true) {

                TextView select_camp2 = (TextView) getView().findViewById(R.id.select_camp2);
                select_camp2.setText(Designation.toString());

                edit_qty_lbl.setText("QTY");
                edit_f_qty_lbl.setText("FREE QTY");
                edit_t_qty_lbl.setText("TOTAL QTY");
                edit_t_amt_lbl.setText("VALUE");

                edit_qty.setText("30");
                edit_f_qty.setText("9");
                edit_t_qty.setText("39");
                edit_t_amt.setText("678");

            } else if (Designation.trim().toString().contains("20+6") == true) {

                TextView select_camp2 = (TextView) getView().findViewById(R.id.select_camp2);
                select_camp2.setText(Designation.toString());

                edit_qty_lbl.setText("QTY");
                edit_f_qty_lbl.setText("FREE QTY");
                edit_t_qty_lbl.setText("TOTAL QTY");
                edit_t_amt_lbl.setText("VALUE");

                edit_qty.setText("20");
                edit_f_qty.setText("6");
                edit_t_qty.setText("26");
                edit_t_amt.setText("452");

            }

            //TextView name_user_of_designation = (TextView) getView().findViewById(R.id.name_user_of_designation);
            //name_user_of_designation.setText(id.toString());

       /* bind_hierarchy_wise_users_listview(Designation.toString());*/
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_doctor_of_TBM() {

        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                user_list_hierarchy_me_FragmentDialog dialog = user_list_hierarchy_me_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "TBM_ONLY");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_booking_call_new.this, 300);
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
                dialog.setTargetFragment(fragment_booking_call_new.this, 300);
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

                dialog.setTargetFragment(fragment_booking_call_new.this, 300);
                dialog.show(getFragmentManager(), "fdf");
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

    private void alert_box() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Message</font>"));
            builder.setMessage("Not Load Fully Hierarchy. Please Load Hierarchy Fully...");

            builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    Fragment frag = new fragment_HeadquarterWise_Stockiest_List();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack("stockiest_dialog");
                    ft.commit();

                    dialog.dismiss();
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

    private void show_dialog_for_select_date() {

        try {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.setTargetFragment(fragment_booking_call_new.this, 300);
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


            restService.getService().getToday_server_and_app_version_Method(sid, "", "'" + "'" + versionName + "'", new Callback<JsonElement>() {
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
            view_select_tbm_of_doctor = (View) getView().findViewById(R.id.view_select_tbm_of_doctor);

            if (designation.equals("TBM") == false) {
                chemist_of_TBM.setVisibility(View.VISIBLE);
                chemist_of_TBM_2.setVisibility(View.VISIBLE);
                view_select_tbm_of_doctor.setVisibility(View.VISIBLE);

            } else {
                chemist_of_TBM.setVisibility(View.GONE);
                chemist_of_TBM_2.setVisibility(View.GONE);
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

    private void show_dialog_for_select_doctor(String PatchID) {
        try {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                Bundle bundle = new Bundle();
                bundle.putString("Patch_ID", PatchID);
                bundle.putString("doc_all", "ALLCAMP");


                test_attch_doctor_FragmentDialog dialog = test_attch_doctor_FragmentDialog.newInstance("Hello world");

                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_booking_call_new.this, 300);
                dialog.show(getFragmentManager(), "fdf");


            }
            mLastClickTime = SystemClock.elapsedRealtime();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishTestDoctorDialog(String id, String DoctorName) {
        try {
            pDialog.hide();
            select_doctor2 = (TextView) getView().findViewById(R.id.select_doctor2);
            select_doctor2.setText(DoctorName.toString());

            name_select_doctor = (TextView) getView().findViewById(R.id.name_select_doctor);
            name_select_doctor.setText(id.toString());
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

            restService.getService().getTransactionFormLockOrNot(sid, "'" + name + "'", "T_CmC", select_date2.getText().toString(), new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        String lock_flag = j2.get("lock_flag").getAsString();
                        String message = j2.get("message").getAsString();
                        if (lock_flag.equals("1")) {
                            save_Campaign_booking();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();//"Oops !!! Locked Profile..."
                        }

                        /*JsonObject j1 = jsonElement.getAsJsonObject();
                        //JsonObject j2 = j1.getAsJsonObject("message");

                        //String lock_flag = j2.get("message").getAsString();
                        String lock_flag = j1.get("message").getAsString();
                        if (lock_flag == "1") {
                            save_Campaign_booking();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), "Oops !!! Locked Campaign Booking...", Toast.LENGTH_SHORT).show();
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

    private void save_Campaign_booking() {
        try {

            /*This code Cut From button Click Event else part*/

            Bundle bundle = this.getArguments();
            POJO_campaign_booking POJO = new POJO_campaign_booking();
            // POJO.setName(txt_patch_id.getText().toString());

            POJO.setDate(select_date2.getText().toString());
            POJO.setUser_name(chemist_of_TBM_2.getText().toString());
            POJO.setChemist_name(select_chemist2.getText().toString());
            POJO.setCampaign_name(select_camp2.getText().toString());
            POJO.setStockist_name(select_stockist2.getText().toString());
            POJO.setCall_by_user_id(txt_dr_call_by_user_id.getText().toString());

            POJO.setUser_id(name_chemist_of_TBM.getText().toString());
            POJO.setChemist_id(name_select_chemist.getText().toString());
                   /* POJO.setCampaign_id(name_select_camp.getText().toString());*/
            POJO.setStockist_id(name_select_stockist.getText().toString());


            POJO.setHq_name(txt_hq_name.getText().toString());


            POJO.setQty(edit_qty.getText().toString());
            POJO.setFree(edit_f_qty.getText().toString());
            POJO.setTotal_qty(edit_t_qty.getText().toString());
            POJO.setTotal_amount(edit_t_amt.getText().toString());

            POJO.setInvoice_number(edit_inv_num.getText().toString());

            POJO.setAny_comment(edit_comment.getText().toString());
            POJO.setDoctor__id(name_select_doctor.getText().toString().trim());
            POJO.setDoctor_name(select_doctor2.getText().toString().trim());

            pDialog.show();
            if (Validation(POJO) == true) {
                if (bundle != null) {
                    update_camp_booking(POJO);
                } else {
                    insert_camp_booking(POJO);
                }
            } else {
                pDialog.hide();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
