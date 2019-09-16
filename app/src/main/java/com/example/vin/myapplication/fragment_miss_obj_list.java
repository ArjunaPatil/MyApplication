package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import static io.realm.Case.INSENSITIVE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_miss_obj_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_miss_obj_list extends Fragment implements
        DatePickerFragment.DateDialogListener,
        datewise_reporting_summary_FragmentDialog.EditFromDateToDateDialogListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int year, month, day;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ///********
    public RealmResults<POJO_User_Hierarchy> POJO_User_Obj;
    ///********

    private Realm mRealm;
    RestService restService;
    public String designation = "", employeename = "ALL", User_ID_List_String = "";

    int limitstart = 0;
    int pagesize = 10;
    public boolean bool_full_update = true;
    boolean datafull = false;
    ProgressDialog pDialog;

    public POJO_Miss_OBJ last_POJO;
    public adapter_miss_obj_list adapter;

    private OnFragmentInteractionListener mListener;
    EditText edit_search;
    //TextView select_date2;

    LinearLayout select_employee;
    LinearLayout select_employee1;
    TextView select_employee2;
    TextView name_objective_of_employee;
    ImageButton select_employee3;

    LinearLayout select_date;
    LinearLayout select_date1;
    TextView select_date2;
    TextView name_date_of_objective;
    ImageButton select_date3;

    View vw_employee;

    private long mLastClickTime = 0;
    Bundle bundle;
    int call_size = 0, cnt = 0;

    public fragment_miss_obj_list() {
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
    public static fragment_miss_obj_list newInstance(String param1, String param2) {
        fragment_miss_obj_list fragment = new fragment_miss_obj_list();
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
        View view = inflater.inflate(R.layout.fragment_presenty_list, container, false);
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

            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = (app_preferences.getString("designation", "default"));
            if (designation.equals("NBM") || designation.equals("HR Manager") || designation.equals("Head of Marketing and Sales") || designation.equals("Admin")) {
                ((DashBord_main) getActivity()).setActionBarTitle("MISS OBJ DETAIL");
                init_control();
                if (select_date2.getText().equals("dd/mm/yyyy")) {
                    loaddate();
                }

                loadevents();

                pDialog = new ProgressDialog(getContext());

                select_date1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //show_dialog_for_from_to_date();
                        show_dialog_for_select_date();
                    }
                });
                select_date2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //show_dialog_for_from_to_date();
                        show_dialog_for_select_date();
                    }
                });
                select_date3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //show_dialog_for_from_to_date();
                        show_dialog_for_select_date();
                    }
                });

                data_fetch();
                super.onStart();

            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void data_fetch() {
        try {

            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_Miss_OBJ.class);
            mRealm.commitTransaction();
            mRealm.close();


            limitstart = 0;
            Check_class_user_null_or_not();


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void balnk_listview() {

        final RealmResults<POJO_Miss_OBJ> result_query1;

        result_query1 = mRealm.where(POJO_Miss_OBJ.class).equalTo("emp_name", "tivtiv").findAll().sort("designation", Sort.DESCENDING);

        List<POJO_Miss_OBJ> mList = result_query1;

        adapter = new adapter_miss_obj_list(getContext(), R.layout.adapter_miss_obj_list, mList);
        ListView lv = (ListView) getView().findViewById(R.id.listView);
        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    public void full_update_calc() {
        try {

            if ((bool_full_update == false)) {

                // if (mRealm.where(POJO_Patch_master.class).equalTo("active", 1).findAll().sort("modified", Sort.DESCENDING).first() != null) {
                RealmResults<POJO_Miss_OBJ> result_query1 = mRealm.where(POJO_Miss_OBJ.class).findAll().sort("designation", Sort.DESCENDING);
                if (result_query1.size() > 0) {
                    last_POJO = result_query1.first();
                } else {
                    bool_full_update = true;
                }
            }

        } catch (Exception ex) {
            String exx = ex.getMessage();
            exx = ex.getMessage();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void init_control() {
        try {

            //employee spinner
            /*select_employee = (LinearLayout) getView().findViewById(R.id.select_employee);
            select_employee1 = (LinearLayout) getView().findViewById(R.id.select_employee1);
            select_employee3 = (ImageButton) getView().findViewById(R.id.select_employee3);
            select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
            name_objective_of_employee = (TextView) getView().findViewById(R.id.name_objective_of_employee);

            select_employee.setVisibility(View.GONE);
            select_employee1.setVisibility(View.GONE);
            select_employee3.setVisibility(View.GONE);
            select_employee2.setVisibility(View.GONE);
            name_objective_of_employee.setVisibility(View.GONE);*/

            //date spinner
            /*select_date = (LinearLayout) getView().findViewById(R.id.select_date);*/
            select_date1 = (LinearLayout) getView().findViewById(R.id.select_date1);
            select_date3 = (ImageButton) getView().findViewById(R.id.select_date3);
            select_date2 = (TextView) getView().findViewById(R.id.select_date2);
            name_date_of_objective = (TextView) getView().findViewById(R.id.name_date_of_objective);

            /*select_date.setVisibility(View.GONE);*/
            select_date1.setVisibility(View.GONE);
            select_date3.setVisibility(View.GONE);
            select_date2.setVisibility(View.GONE);
            name_date_of_objective.setVisibility(View.GONE);

            //view (line)
            vw_employee = getView().findViewById(R.id.vw_employee);
            vw_employee.setVisibility(View.GONE);


            if (designation.equals("ABM") || designation.equals("RBM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("CRM") || (designation.equals("NBM")) || (designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager"))) {
                /*select_employee.setVisibility(View.VISIBLE);
                select_employee1.setVisibility(View.VISIBLE);
                select_employee3.setVisibility(View.VISIBLE);
                select_employee2.setVisibility(View.VISIBLE);
                name_objective_of_employee.setVisibility(View.VISIBLE);*/

                /*select_date.setVisibility(View.VISIBLE);*/
                select_date1.setVisibility(View.VISIBLE);
                select_date3.setVisibility(View.VISIBLE);
                select_date2.setVisibility(View.VISIBLE);
                name_date_of_objective.setVisibility(View.VISIBLE);

                vw_employee.setVisibility(View.VISIBLE);
            } else {

            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void loadevents() {
        try {
            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("");

            edit_search = (EditText) getView().findViewById(R.id.edit_search);

            edit_search.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    Searching_data_listview(s.toString());
                }
            });


            ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        restService = new RestService();
                        edit_search.setText("");

                        mRealm = Realm.getDefaultInstance();

                        RealmResults<POJO_Miss_OBJ> result_query2 = mRealm.where(POJO_Miss_OBJ.class).findAll().sort("designation", Sort.DESCENDING);
                        mRealm.beginTransaction();
                        result_query2.deleteAllFromRealm();
                        mRealm.commitTransaction();
                        mRealm.close();

                        bool_full_update = false;
                        datafull = false;
                        limitstart = 0;

                        if (select_date2.getText().toString().contains("dd/mm/yyyy") == false) {
                            if (select_date2.getText().toString().contains("/") == true) {
                                String frm_date = select_date2.getText().toString().trim().substring(0, 10);
                                String to_date = select_date2.getText().toString().trim().substring(13, 22);
                                balnk_listview();
                                Load_Objective(frm_date, to_date);
                            } else {
                                balnk_listview();
                                Load_Objective(select_date2.getText().toString(), select_date2.getText().toString());
                            }
                        } else {
                            Toast.makeText(getContext(), "PLEASE SELECT DATE...", Toast.LENGTH_LONG).show();
                        }


                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }

    }

    private void loaddate() {
        try {
            select_date2 = (TextView) getView().findViewById(R.id.select_date2);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            select_date2.setText(sdf.format(date));
           /*select_date2.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //  showDialog(999);
                    Toast.makeText(getContext(), "ca",
                            Toast.LENGTH_SHORT)
                            .show();

                    return false;
                }
            });*/
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Check_class_user_null_or_not() {
        try {
            mRealm = Realm.getDefaultInstance();
            /*POJO_User_Obj = mRealm.where(POJO_User_Hierarchy.class).contains("flag", "B", INSENSITIVE).findAll();
            if (POJO_User_Obj.size() > 0) {
                for (POJO_User_Hierarchy contact : POJO_User_Obj) {
                    if (!contact.getName().equals("ALL")) {
                        User_ID_List_String += contact.getName() + ",";
                    }
                }
                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String name = app_preferences.getString("name", "default");
                User_ID_List_String += name;*/

            balnk_listview();
            Load_Objective(select_date2.getText().toString(), select_date2.getText().toString());
            /*} else {
                User_ID_List_String = " ";
                alert_box();
            }*/

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Load_Objective(final String frm_date, final String to_date) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String emp = app_preferences.getString("name", "default");
            String Designation = app_preferences.getString("designation", "default");
            String branch = app_preferences.getString("branch", "");
            //String EmployeeHierarchyId = app_preferences.getString("EmployeeUnderHierarchy", "default");

            pDialog.show();
            restService.getService().getMissOBJ_data_Method(sid, frm_date, Designation, branch, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("message");
                        if (j2 != null) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<POJO_Miss_OBJ>>() {
                            }.getType();
                            List<POJO_Miss_OBJ> POJO = gson.fromJson(j2, type);

                            if (POJO.size() == 0) {
                                datafull = true;
                                //Bind_data_listview();
                                //pDialog.hide();
                            } else {
                                if (call_size < 1) {
                                    call_size = POJO.size();
                                }
                                if (call_size == POJO.size()) {
                                    cnt++;
                                    if (cnt > 0) {
                                        datafull = true;
                                        call_size = 0;
                                        cnt = 0;
                                    }
                                }
                                limitstart = limitstart + pagesize;
                            }

                            mRealm = Realm.getDefaultInstance();
                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(POJO);
                            mRealm.commitTransaction();
                            mRealm.close();


                            if (datafull == false) {
                                Load_Objective(frm_date, to_date);

                            } else {
                                Bind_data_listview();
                                pDialog.hide();
                            }
                        } else {
                            Toast.makeText(getContext(), "NO RECORD FOUND FOR SELECTED DATE", Toast.LENGTH_LONG).show();
                            pDialog.hide();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                        pDialog.hide();
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
                            //Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            pDialog.hide();
        }
    }

    public void Bind_data_listview() {
        try {

            final RealmResults<POJO_Miss_OBJ> result_query1;

            /*if (select_employee2.getText().toString().contains("SELECT") == false && select_employee2.getText().toString().contains("ALL") == false) {
                result_query1 = mRealm.where(POJO_objective_S.class).equalTo("user", name_objective_of_employee.getText().toString().trim()).findAll().sort("creation", Sort.DESCENDING);
            } else {*/
            result_query1 = mRealm.where(POJO_Miss_OBJ.class).findAll().sort("designation", Sort.DESCENDING);
            /*}*/

            List<POJO_Miss_OBJ> mList = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("MISS OBJ DETAIL (" + mList.size() + ")");

            if (mList.size() == 0) {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("NO ACTIVITY FOUND FOR SELECTED FILTER..");
            } else {
                adapter = new adapter_miss_obj_list(getContext(), R.layout.adapter_miss_obj_list, mList);
                ListView lv = (ListView) getView().findViewById(R.id.listView);
                lv.setAdapter(adapter);

                adapter.notifyDataSetChanged();
                ///

                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.GONE);
                txt_loading.setText("Refreshing Data..");
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Searching_data_listview(String search) {
        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_Miss_OBJ> result_query1 = mRealm.where(POJO_Miss_OBJ.class).contains("emp_name", search, INSENSITIVE).findAll();//.sort("modified", Sort.DESCENDING);
            List<POJO_Miss_OBJ> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("MISS OBJ DETAIL (" + POJO.size() + ")");

            adapter = new adapter_miss_obj_list(getContext(), R.layout.adapter_miss_obj_list, POJO);

            lv.setAdapter(adapter);
            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
                Toast.makeText(getContext(), "NO ACTIVITY FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_from_to_date() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 500) {

            } else {
                datewise_reporting_summary_FragmentDialog dialog = datewise_reporting_summary_FragmentDialog.newInstance("Hello world");
                final Bundle bundle = new Bundle();
                bundle.putString("rpt", "all");
                //dialog.setView(layout);
                dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_miss_obj_list.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
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
            //Toast.makeText(this, "Selected Date :"+ formatDate(date), Toast.LENGTH_SHORT).show();
            select_date2.setText(hireDate);
            //Load_Objective(select_date2.getText().toString());

            restService = new RestService();
            edit_search.setText("");

            mRealm = Realm.getDefaultInstance();
            RealmResults<POJO_objective_S> result_query1;

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String userID = "";//app_preferences.getString("name", "default");
            String designation = app_preferences.getString("designation", "default");
            String param = select_date2.getText().toString();

            if (designation != "" && designation != null && param != "" && param != null) {
                //select_date2.setText(from_date + " / " + to_date);
                abc(param, param);
            } else {
                Toast.makeText(getContext(), "PLEASE SELECT FROM/TO DATE...", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void show_dialog_for_select_date() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.setTargetFragment(fragment_miss_obj_list.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishFromDateToDateDialog(String empid, String from_date, String to_date) {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String userID = "";//app_preferences.getString("name", "default");
            String designation = app_preferences.getString("designation", "default");
            String param = "";

            if (designation != "" && designation != null && from_date != "" && from_date != null && to_date != "" && to_date != null) {

                select_date2.setText(from_date + " / " + to_date);

                abc(from_date, to_date);

                //Show_summary_report(userID, designation, from_date, to_date, param);
            } else {
                Toast.makeText(getContext(), "PLEASE SELECT FROM/TO DATE...", Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void abc(String from_date, String to_date) {

        restService = new RestService();
        edit_search.setText("");

        mRealm = Realm.getDefaultInstance();


    /*RealmResults<POJO_Doctor_Master> result_query1 = mRealm.where(POJO_Doctor_Master.class).equalTo("user",name_doctor_of_employee.toString().trim()).findAll().sort("modified", Sort.DESCENDING);*/
        RealmResults<POJO_Miss_OBJ> result_query2 = mRealm.where(POJO_Miss_OBJ.class).findAll().sort("designation", Sort.DESCENDING);
        mRealm.beginTransaction();
        result_query2.deleteAllFromRealm();
        mRealm.commitTransaction();
        mRealm.close();

        bool_full_update = false;
        datafull = false;
        limitstart = 0;


        if (select_date2.getText().toString().contains("dd/mm/yyyy") == false) {
            balnk_listview();
            Load_Objective(from_date, to_date);
        } else {
            Toast.makeText(getContext(), "PLEASE SELECT DATE", Toast.LENGTH_LONG).show();
        }
    }


    /*@Override
    public void onFinishDialog(Date date) {
        try {
            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String hireDate = sdf.format(date);
            //Toast.makeText(this, "Selected Date :"+ formatDate(date), Toast.LENGTH_SHORT).show();
            select_date2.setText(hireDate);
            //Load_Objective(select_date2.getText().toString());

            restService = new RestService();
            edit_search.setText("");

            mRealm = Realm.getDefaultInstance();
            RealmResults<POJO_objective_S> result_query1;

            if (select_employee2.getText().toString().contains("SELECT") == false && select_employee2.getText().toString().contains("ALL") == false) {
                result_query1 = mRealm.where(POJO_objective_S.class).contains("user", name_objective_of_employee.getText().toString().trim(), INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);
            } else {
                result_query1 = mRealm.where(POJO_objective_S.class).findAll().sort("modified", Sort.DESCENDING);
            }

            mRealm = Realm.getDefaultInstance();

                        *//*RealmResults<POJO_Doctor_Master> result_query1 = mRealm.where(POJO_Doctor_Master.class).equalTo("user",name_doctor_of_employee.toString().trim()).findAll().sort("modified", Sort.DESCENDING);*//*
            RealmResults<POJO_objective_S> result_query2 = mRealm.where(POJO_objective_S.class).findAll().sort("modified", Sort.DESCENDING);
            mRealm.beginTransaction();
            result_query2.deleteAllFromRealm();
            mRealm.commitTransaction();
            mRealm.close();

            bool_full_update = false;
            datafull = false;
            limitstart = 0;

            if (select_employee2.getText().toString().contains("SELECT") == false && select_employee2.getText().toString().contains("ALL") == false) {
                if (select_date2.getText().toString().contains("dd/mm/yyyy") == false) {
                    balnk_listview();
                    Load_Objective(select_date2.getText().toString());
                } else {
                    Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                }
            } else {
                if (select_date2.getText().toString().contains("dd/mm/yyyy") == false) {
                    Check_class_user_null_or_not();
                } else {
                    Toast.makeText(getContext(), "Please Select Date", Toast.LENGTH_LONG).show();
                }

            }
            //Toast.makeText(getContext(),"Selected Date :"+ hireDate, Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }*/

    public boolean onBackPressed() {
        return false;
    }


}
