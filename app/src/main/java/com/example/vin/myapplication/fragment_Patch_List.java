package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.lang.reflect.Type;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;
import static io.realm.Case.INSENSITIVE;

//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_Patch_List#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_Patch_List extends Fragment implements user_list_hierarchy_FragmentDialog.EditUserListHirarchyDialogListener {
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

    public fragment_Patch_List() {
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
    public static fragment_Patch_List newInstance(String param1, String param2) {
        fragment_Patch_List fragment = new fragment_Patch_List();
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
            designation = (app_preferences.getString("designation", "default"));

            mRealm = Realm.getDefaultInstance();
            POJO_User_Obj = mRealm.where(POJO_User_Hierarchy.class).findAll().sort("modified", Sort.DESCENDING);

            ((DashBord_main) getActivity()).setActionBarTitle("PATCH LIST");
            init_control();

            if (POJO_User_Obj.size() > 0) {
                //ll_user = mRealm.where(POJO_User_Hierarchy.class).contains("designation", "TBM", Case.INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);

                if (designation.equals("TBM") || designation.equals("KBM")) {
                    String name = app_preferences.getString("name", "default");
                    name_patch_of_employee.setText(name);
                }

                loadevents();

                pDialog  = new ProgressDialog(getContext());

                select_employee.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show_dialog_for_select_employee();
                    }
                });
                select_employee1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show_dialog_for_select_employee();
                    }
                });
                select_employee2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show_dialog_for_select_employee();
                    }
                });
                select_employee3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show_dialog_for_select_employee();
                    }
                });


            /*CHECK_ABM_RBM_ZBM_SM();
            bind_spinners();*/

                data_fetch();
            } else {
                alert_box();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patch__list, container, false);
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
    public void onFinishUserListHirarchyDialog(String id, String fullname) {
        try {
            TextView select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
            select_employee2.setText(fullname.toString());

            TextView name_doctor_of_employee = (TextView) getView().findViewById(R.id.name_patch_of_employee);
            name_doctor_of_employee.setText(id.toString());

            //bind_patch_employee_wise_listview(id.toString());
            //common_get();
            data_fetch();
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

    public void init_control() {
        try {
            select_employee = (LinearLayout) getView().findViewById(R.id.select_employee);
            select_employee1 = (LinearLayout) getView().findViewById(R.id.select_employee1);
            select_employee3 = (ImageButton) getView().findViewById(R.id.select_employee3);
            select_employee2 = (TextView) getView().findViewById(R.id.select_employee2);
            name_patch_of_employee = (TextView) getView().findViewById(R.id.name_patch_of_employee);

            vw_employee = getView().findViewById(R.id.vw_employee);

            select_employee.setVisibility(View.GONE);
            select_employee1.setVisibility(View.GONE);
            select_employee3.setVisibility(View.GONE);
            select_employee2.setVisibility(View.GONE);
            name_patch_of_employee.setVisibility(View.GONE);
            vw_employee.setVisibility(View.GONE);


            if (designation.equals("ABM") || designation.equals("RBM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("CRM") || (designation.equals("NBM")) || (designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager")) || (designation.equals("Administrator"))) {
                select_employee.setVisibility(View.VISIBLE);
                select_employee1.setVisibility(View.VISIBLE);
                select_employee3.setVisibility(View.VISIBLE);
                select_employee2.setVisibility(View.VISIBLE);
                name_patch_of_employee.setVisibility(View.VISIBLE);
                vw_employee.setVisibility(View.VISIBLE);
            } else {

            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void loadevents() {
        try {

            edit_search = (EditText) getView().findViewById(R.id.edit_search);

            ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        common_get();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });
            ImageButton btn_add_data = (ImageButton) getView().findViewById(R.id.btn_add_data);
            btn_add_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (designation.equals("TBM")) {
                            Fragment frag = new fragment_patch_master_insert();//fragment_doctor_master_insert

                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame, frag);

                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            ft.addToBackStack("patch_new_insert");

                            ft.commit();
                        } else {
                            Toast.makeText(getContext(), "Only TBM can Add/Update Patch", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });

            edit_search.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    try {
                        Searching_data_listview(s.toString());
                        //refreshScreen(s.toString());
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            ListView lv = (ListView) getView().findViewById(R.id.listView);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              try {
                                                  POJO_Patch_master clickedObj = (POJO_Patch_master) parent.getItemAtPosition(position);

                                                  final Bundle bundle = new Bundle();
                                                  bundle.putString("name", clickedObj.getName()); // use as per your need
                                                  bundle.putString("patch_name", clickedObj.getPatch_name());
                                                  bundle.putString("headquarter", clickedObj.getHeadquarter());
                                                  bundle.putString("user", clickedObj.getUser());
                                                  bundle.putString("user_name", clickedObj.getUser());
                                                  Fragment frag = new fragment_patch_master_insert();

                                                  FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                  ft.replace(R.id.content_frame, frag);
                                                  frag.setArguments(bundle);
                                                  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                                  ft.addToBackStack("patch_update");
                                                  ft.commit();
                                              } catch (Exception ex) {
                                                  Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                              }
                                          }
                                      }
            );

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void full_update_calc() {
        try {

            if ((bool_full_update == false)) {

                RealmResults<POJO_Patch_master> result_query1 = mRealm.where(POJO_Patch_master.class).findAll().sort("modified", Sort.DESCENDING);
                if (result_query1.size() > 0) {
                    last_POJO = result_query1.first();
                } else {
                    bool_full_update = true;
                }
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Check_class_user_null_or_not() {
        try {
            /*mRealm = Realm.getDefaultInstance();
            POJO_User_Obj = mRealm.where(POJO_User_Hierarchy.class).contains("designation", "TBM", INSENSITIVE).findAll();
            if (POJO_User_Obj.size() > 0) {
                for (POJO_User_Hierarchy contact : POJO_User_Obj) {
                    if (!contact.getName().equals("ALL")) {
                        User_ID_List_String = User_ID_List_String + "," + contact.getName();
                    }
                }
                User_ID_List_String = User_ID_List_String.substring(1);
                Load_Patch_List();
            } else {
                User_ID_List_String = " ";

                Dialog Box Code For Class HQ Null
                alert_box();
            }*/

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void Load_Patch_List() {
        try {

            ////TextView name_doctor_of_TBM = (TextView) getView().findViewById(R.id.name_doctor_of_TBM);
            //Thread.sleep(sleep_wait);

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String emp = app_preferences.getString("name", "default");
            String Designation = app_preferences.getString("designation", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            //jsonArray.put("creation");
            jsonArray.put("headquarter");
            //jsonArray.put("abm");
            //jsonArray.put("owner");
            //jsonArray.put("modified_by");
            //jsonArray.put("docstatus");
            //jsonArray.put("zbm");
            jsonArray.put("patch_name");
            jsonArray.put("patch_type");
            //jsonArray.put("idx");
            jsonArray.put("modified");
            jsonArray.put("km");
            //jsonArray.put("rbm");
            //jsonArray.put("sm");
            jsonArray.put("active");
            jsonArray.put("approve");
            jsonArray.put("user");
            jsonArray.put("user_name");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();

            Filter1.put("Patch master");
            Filter1.put("active");
            Filter1.put("=");
            Filter1.put(1);

            Filter2.put("Patch master");
            Filter2.put("user");
            Filter2.put("=");
            Filter2.put(name_patch_of_employee.getText().toString().trim());

            Filters.put(Filter1);
            Filters.put(Filter2);

            /*if (Designation.equals("TBM") || Designation.equals("KBM")) {

                Filter2.put("Patch master");
                Filter2.put("user");
                Filter2.put("=");
                Filter2.put(emp);

                Filters.put(Filter1);
                Filters.put(Filter2);
            } else if ((Designation.equals("ABM")) || (Designation.equals("RBM")) || (Designation.equals("CRM")) || (Designation.equals("ZBM")) || (Designation.equals("SM"))) {

                Filter2.put("Patch master");
                Filter2.put("user");
                Filter2.put("in");
                Filter2.put(User_ID_List_String);

                Filters.put(Filter1);
                Filters.put(Filter2);
            } else if ((Designation.equals("NBM")) || (Designation.equals("Head of Marketing and Sales")) || (Designation.equals("HR Manager")) || (Designation.equals("Administrator"))) {
                Filters.put(Filter1);
            }*/


            pDialog.show();

            restService.getService().getPatch_master(sid, limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Patch_master>>() {
                        }.getType();
                        List<POJO_Patch_master> POJO = gson.fromJson(j2, type);


                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();
                        mRealm.close();


                        if (POJO.size() == 0) {
                            datafull = true;
                            Bind_data_listview();
                            pDialog.hide();
                        } else {
                            limitstart = limitstart + pagesize;
                        }


                       /* if (bool_full_update == false) {
                            for (POJO_Patch_master pp : POJO) {

                                if (last_POJO == null) {
                                    datafull = false;
                                } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                    datafull = true;
                                    Bind_data_listview();
                                    pDialog.hide();
                                }
                            }
                        }*/

                        if (datafull == false) {
                            Load_Patch_List();
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

    public void data_fetch() {
        try {

            mRealm = Realm.getDefaultInstance();

            RealmResults<POJO_Patch_master> result_query1 = mRealm.where(POJO_Patch_master.class).equalTo("user", name_patch_of_employee.getText().toString().trim()).findAll().sort("modified", Sort.DESCENDING);
            if (result_query1.size() > 0) {
                Bind_data_listview();
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.GONE);
                //txt_loading.setText("Refreshing Data..");
            } else {
                //TextView txt_loading = (TextView) getView().findViewById(txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("Refreshing Data..");
                bool_full_update = false;
                // full_update_calc();
                limitstart = 0;
                datafull = false;

                if (designation.equals("TBM") || designation.equals("KBM")) {
                    final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    String name = app_preferences.getString("sid", "default");
                    select_employee2.setText(name);
                    Load_Patch_List();
                } else {
                    if (select_employee2.getText().toString().contains("Select") == false) {
                        Load_Patch_List();
                    } else {
                        Toast.makeText(getContext(), "Please Select Employee", Toast.LENGTH_LONG).show();
                    }
                }

                /*if (designation.equals("TBM") || designation.equals("KBM")) {
                    //pDialog.show();
                    Load_Patch_List();
                } else {
                    Check_class_user_null_or_not();
                }*/
                //pDialog.show();
                //();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void common_get() {
        try {
            restService = new RestService();
            edit_search.setText("");

                        /*mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.delete(POJO_Patch_master.class);
                        mRealm.commitTransaction();
                        mRealm.close();*/

                        /*if (designation.equals("TBM") || designation.equals("KBM")) {
                            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                            String sid = app_preferences.getString("sid", "default");
                            name_patch_of_employee.setText(sid);
                        }*/

            if (designation.equals("TBM") || designation.equals("KBM")) {
                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String name = app_preferences.getString("name", "default");
                name_patch_of_employee.setText(name);
            }
            mRealm = Realm.getDefaultInstance();

                        /*RealmResults<POJO_Doctor_Master> result_query1 = mRealm.where(POJO_Doctor_Master.class).equalTo("user",name_doctor_of_employee.toString().trim()).findAll().sort("modified", Sort.DESCENDING);*/
            RealmResults<POJO_Patch_master> result_query1 = mRealm.where(POJO_Patch_master.class).contains("user", name_patch_of_employee.getText().toString().trim(), INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);
            mRealm.beginTransaction();
            result_query1.deleteAllFromRealm();
            mRealm.commitTransaction();
            mRealm.close();

            //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            //txt_loading.setVisibility(View.VISIBLE);
            //txt_loading.setText("Refreshing Data..");
            bool_full_update = false;
            // full_update_calc();
            datafull = false;
            limitstart = 0;

                        /*if (designation.equals("TBM") || designation.equals("KBM")) {
                            //pDialog.show();
                            Load_Patch_List();
                        } else {
                            Check_class_user_null_or_not();
                        }*/

            if (designation.equals("TBM") || designation.equals("KBM")) {
                //pDialog.show();
                Load_Patch_List();
            } else {
                if (select_employee2.getText().toString().contains("Select") == false) {
                    Load_Patch_List();
                } else {
                    Toast.makeText(getContext(), "Please Select Employee", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Bind_data_listview() {

        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_Patch_master> result_query1 = mRealm.where(POJO_Patch_master.class).equalTo("user", name_patch_of_employee.getText().toString().trim()).findAll().sort("patch_name", Sort.ASCENDING);
            List<POJO_Patch_master> POJO = result_query1;
            result_query = result_query1;//This assign for check size
            ((DashBord_main) getActivity()).setActionBarTitle("PATCH LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_patch_list(getContext(), R.layout.adapter_patch_list, POJO);

            lv.setAdapter(customAdapter);
            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
                // Toast.makeText(getContext(), "NO PATCH FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Searching_data_listview(String search) {

        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_Patch_master> result_query1 = mRealm.where(POJO_Patch_master.class).equalTo("user", name_patch_of_employee.getText().toString().trim()).contains("patch_name", search, INSENSITIVE).findAll().sort("patch_name", Sort.ASCENDING);
            List<POJO_Patch_master> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("PATCH LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_patch_list(getContext(), R.layout.adapter_patch_list, POJO);

            lv.setAdapter(customAdapter);
            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
                //Toast.makeText(getContext(), "NO PATCH FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void bind_patch_employee_wise_listview(String search) {

        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);

            final RealmResults<POJO_Patch_master> result_query1;
            if (search.equals("ALL")) {
                result_query1 = mRealm.where(POJO_Patch_master.class).findAll().sort("modified", Sort.DESCENDING);
            } else {
                result_query1 = mRealm.where(POJO_Patch_master.class).contains("user", search, INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);
            }

            //final RealmResults<POJO_Patch_master> result_query1 = mRealm.where(POJO_Patch_master.class).contains("user", search, INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);
            List<POJO_Patch_master> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("PATCH LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_patch_list(getContext(), R.layout.adapter_patch_list, POJO);

            lv.setAdapter(customAdapter);
            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
                // Toast.makeText(getContext(), "NO PATCH FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_employee() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {

                user_list_hierarchy_FragmentDialog dialog = user_list_hierarchy_FragmentDialog.newInstance("Hello world");

                final Bundle bundle = new Bundle();
                bundle.putString("stcokiest", "N");
                //dialog.setView(layout);
                dialog.setArguments(bundle);

                dialog.setTargetFragment(fragment_Patch_List.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
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
                    ft.addToBackStack("patchjkjm");
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

    android.os.Handler handle = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pDialog.incrementProgressBy(1);
        }
    };

}
