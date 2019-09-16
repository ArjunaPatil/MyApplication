package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
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
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_Hierarchy_Users_List#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_Hierarchy_Users_List extends Fragment implements
        user_hierarchy_list_FragmentDialog.EditDesignationListHirarchyDialogListener {
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
    public POJO_User_Hierarchy last_POJO;
    /////

    private OnFragmentInteractionListener mListener;
    ListView listView;
    public EditText edit_search;
    public String designation = "", employeename = "ALL", employeecode = "";//employeename = "ALL",

    //public RealmResults<POJO_Employee> employee_filter;

    public RealmResults<POJO_User> employee_filter;//Filter By User For ABM/RBM/SM Employee
    private ProgressDialog pDialog;

    RestService restService;
    public adapter_fragment_hierarchy_users_list customAdapter;
    Bundle bundle;

    LinearLayout select_designation;
    LinearLayout select_designation1;
    TextView select_designation2;
    TextView name_user_of_designation;
    ImageButton select_designation3;
    View vw_designation;
    private long mLastClickTime = 0;

    public fragment_Hierarchy_Users_List() {
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
    public static fragment_Hierarchy_Users_List newInstance(String param1, String param2) {
        fragment_Hierarchy_Users_List fragment = new fragment_Hierarchy_Users_List();
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

            ((DashBord_main) getActivity()).setActionBarTitle("HIERARCHY LIST");

            init_control();
            loadevents();

            pDialog = new ProgressDialog(getContext());

            select_designation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_designation();
                }
            });
            select_designation1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_designation();
                }
            });
            select_designation2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_designation();
                }
            });
            select_designation3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_select_designation();
                }
            });

            /*CHECK_ABM_RBM_ZBM_SM();
            bind_spinners();*/
            /*if (designation.equals("TBM") || designation.equals("KBM")) {


            }
            else {
                data_fetch();
            }*/
            data_fetch();
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
        return inflater.inflate(R.layout.fragment_hierarchy_users_list, container, false);
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

    public void init_control() {
        try {
            select_designation = (LinearLayout) getView().findViewById(R.id.select_designation);
            select_designation1 = (LinearLayout) getView().findViewById(R.id.select_designation1);
            select_designation3 = (ImageButton) getView().findViewById(R.id.select_designation3);
            select_designation2 = (TextView) getView().findViewById(R.id.select_designation2);
            name_user_of_designation = (TextView) getView().findViewById(R.id.name_user_of_designation);

            vw_designation = getView().findViewById(R.id.vw_designation);

            /*select_designation.setVisibility(View.GONE);
            select_designation1.setVisibility(View.GONE);
            select_designation3.setVisibility(View.GONE);
            select_designation2.setVisibility(View.GONE);
            name_user_of_designation.setVisibility(View.GONE);
            vw_designation.setVisibility(View.GONE);

            if (designation.equals("ABM") || designation.equals("RBM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("CRM") || (designation.equals("NBM")) || (designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager"))) {
                select_designation.setVisibility(View.VISIBLE);
                select_designation1.setVisibility(View.VISIBLE);
                select_designation3.setVisibility(View.VISIBLE);
                select_designation2.setVisibility(View.VISIBLE);
                name_user_of_designation.setVisibility(View.VISIBLE);
                vw_designation.setVisibility(View.VISIBLE);
            }
            else {

            }*/

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onFinishDesignationListHirarchyDialog(String Designation) {
        try {
            TextView select_designation2 = (TextView) getView().findViewById(R.id.select_designation2);
            select_designation2.setText(Designation.toString());

            //TextView name_user_of_designation = (TextView) getView().findViewById(R.id.name_user_of_designation);
            //name_user_of_designation.setText(id.toString());

            bind_hierarchy_wise_users_listview(Designation.toString());
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

    public void loadevents() {
        try {

            edit_search = (EditText) getView().findViewById(R.id.edit_search);

            ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        select_designation2.setText("Select Designation");

                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.delete(POJO_User_Hierarchy.class);
                        mRealm.commitTransaction();
                        mRealm.close();

                        //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                        //txt_loading.setVisibility(View.VISIBLE);
                        //txt_loading.setText("Refreshing Data..");
                        bool_full_update = false;
                        full_update_calc();
                        datafull = false;
                        limitstart = 0;
                        /*
                        final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        String designation = app_preferences.getString("designation", "default");
                        String user = app_preferences.getString("name", "default");
                        Load_bind_hierarchy_wise_users_listview(designation,user);*/
                        Load_bind_hierarchy_wise_users_listview();

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });
            /*ImageButton btn_add_data = (ImageButton) getView().findViewById(R.id.btn_add_data);
            btn_add_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (designation.equals("Stockiest")) {
                        Fragment frag = new fragment_stockiest_users_master_insert();

                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, frag);

                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack("stockiest_new_user_insert");

                        ft.commit();
                    } else {
                        Toast.makeText(getContext(), "Only Stockiest can Add/Update Doctor", Toast.LENGTH_SHORT).show();
                    }


                }
            });*/

            edit_search.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    Searching_data_listview(s.toString());
                    //refreshScreen(s.toString());
                }
            });

           /* ListView lv = (ListView) getView().findViewById(R.id.listView);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              POJO_User_Hierarchy clickedObj = (POJO_User_Hierarchy) parent.getItemAtPosition(position);

                                              final Bundle bundle = new Bundle();
                                              bundle.putString("name", clickedObj.getName());
                                              Fragment frag = new fragment_stockiest_users_master_insert();

                                              FragmentTransaction ft = getFragmentManager().beginTransaction();
                                              ft.replace(R.id.content_frame, frag);
                                              frag.setArguments(bundle);
                                              ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                              ft.addToBackStack("stockiest_user_update");
                                              ft.commit();
                                          }
                                      }
            );*/

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void full_update_calc() {
        try {

            if ((bool_full_update == false)) {

                // if (mRealm.where(POJO_User_Hierarchy.class).equalTo("active", 1).findAll().sort("modified", Sort.DESCENDING).first() != null) {
                RealmResults<POJO_User_Hierarchy> result_query1 = mRealm.where(POJO_User_Hierarchy.class).findAll().sort("modified", Sort.DESCENDING);
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

    private void Load_bind_hierarchy_wise_users_listview() {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            final String emp = app_preferences.getString("name", "default");
            final String designation = app_preferences.getString("designation", "default");


            pDialog.show();

            restService.getService().getUser_Method(sid, "'" + emp + "'", designation, pagesize, limitstart, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("message");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_User_Hierarchy>>() {
                        }.getType();
                        List<POJO_User_Hierarchy> POJO = gson.fromJson(j2, type);

                        //////////////////////////////////////////

                        if (POJO != null) {
                            for (POJO_User_Hierarchy pp : POJO) {
                                if (pp != null) {
                                    if (pp.getDesignation() != null && pp.getDesignation() != "") {
                                        if (designation.equals("TBM")) {
                                            pp.setFlag("T");
                                        } else if (designation.equals("ABM")) {
                                            if (pp.getDesignation().equals("TBM")) {
                                                pp.setFlag("B");
                                            } else {
                                                pp.setFlag("T");
                                            }
                                        } else if (designation.equals("RBM")) {
                                            if (pp.getDesignation().equals("TBM") || pp.getDesignation().equals("ABM")) {
                                                pp.setFlag("B");
                                            } else {
                                                pp.setFlag("T");
                                            }
                                        } else if (designation.equals("ZBM")) {
                                            if (pp.getDesignation().equals("TBM") || pp.getDesignation().equals("ABM") || pp.getDesignation().equals("RBM")) {
                                                pp.setFlag("B");
                                            } else {
                                                pp.setFlag("T");
                                            }
                                        } else if (designation.equals("SM")) {
                                            if (pp.getDesignation().equals("TBM") || pp.getDesignation().equals("ABM") || pp.getDesignation().equals("RBM") || pp.getDesignation().equals("ZBM")) {
                                                pp.setFlag("B");
                                            } else {
                                                pp.setFlag("T");
                                            }
                                        } else if (designation.equals("CRM")) {
                                            if (pp.getDesignation().equals("TBM") || pp.getDesignation().equals("ABM") || pp.getDesignation().equals("RBM") || pp.getDesignation().equals("ZBM") || pp.getDesignation().equals("SM")) {
                                                pp.setFlag("B");
                                            } else {
                                                pp.setFlag("T");
                                            }
                                        } else if (designation.equals("NBM") || designation.equals("Head of Marketing and Sales") || designation.equals("HR Manager")) {
                                            pp.setFlag("B");
                                        }
                                    }
                                }
                            }
                        }
                        /////////////////////////////////////////////////////////////////////////


                        if (POJO == null) {
                            datafull = true;
                            Bind_data_listview();
                            pDialog.hide();
                        } else {
                            limitstart = limitstart + pagesize;
                            mRealm = Realm.getDefaultInstance();
                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(POJO);
                            mRealm.commitTransaction();
                            mRealm.close();

                            if (bool_full_update == false) {
                                for (POJO_User_Hierarchy pp : POJO) {
                                    if (last_POJO == null) {
                                        datafull = false;
                                    } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                        datafull = true;
                                        Bind_data_listview();
                                        pDialog.hide();
                                    }
                                }
                            }

                        }


                   /* //old code for api
                   if (POJO.size() == 0) {
                        datafull = true;
                        Bind_data_listview();


                    } else {
                        limitstart = limitstart + pagesize;

                    }

                    mRealm = Realm.getDefaultInstance();
                    mRealm.beginTransaction();
                    mRealm.copyToRealmOrUpdate(POJO);
                    mRealm.commitTransaction();
                    mRealm.close();

                    if (bool_full_update == false) {
                        for (POJO_User_Hierarchy pp : POJO) {

                            if (last_POJO == null) {
                                datafull = false;
                            } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                datafull = true;
                                Bind_data_listview();
                            }
                        }
                    }*/


                        if (datafull == false) {
                            Load_bind_hierarchy_wise_users_listview();
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

            RealmResults<POJO_User_Hierarchy> result_query1 = mRealm.where(POJO_User_Hierarchy.class).findAll().sort("modified", Sort.DESCENDING);
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
                full_update_calc();
                limitstart = 0;
                datafull = false;

            /*final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String user = app_preferences.getString("name", "default");
            String designation = app_preferences.getString("designation", "default");
            Load_bind_hierarchy_wise_users_listview(designation,user);*/
                Load_bind_hierarchy_wise_users_listview();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Bind_data_listview() {

        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_User_Hierarchy> result_query1 = mRealm.where(POJO_User_Hierarchy.class).findAll().sort("modified", Sort.DESCENDING);
            List<POJO_User_Hierarchy> POJO = result_query1;
            ((DashBord_main) getActivity()).setActionBarTitle("HIERARCHY LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_hierarchy_users_list(getContext(), R.layout.adapter_hierarchy_users_list, POJO);
            lv.setAdapter(customAdapter);
            if (POJO.size() == 0) {
             //   Toast.makeText(getContext(), "NO EMPLOYEE FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {

            }
            //Toast.makeText(getContext(), "PLEASE SELECT FIRST DESIGNATION...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Searching_data_listview(String search) {

        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_User_Hierarchy> result_query1 = mRealm.where(POJO_User_Hierarchy.class).contains("first_name", search, Case.INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);//contains("middle_name", search, Case.INSENSITIVE).contains("last_name", search, Case.INSENSITIVE)
            List<POJO_User_Hierarchy> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("HIERARCHY LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_hierarchy_users_list(getContext(), R.layout.adapter_hierarchy_users_list, POJO);

            lv.setAdapter(customAdapter);
            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
               // Toast.makeText(getContext(), "NO EMPLOYEE FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void bind_hierarchy_wise_users_listview(String search) {
        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_User_Hierarchy> result_query1;
            if (search.equals("ALL")) {
                result_query1 = mRealm.where(POJO_User_Hierarchy.class).findAll().sort("modified", Sort.DESCENDING);//contains("middle_name", search, Case.INSENSITIVE).contains("last_name", search, Case.INSENSITIVE)
            } else {
                result_query1 = mRealm.where(POJO_User_Hierarchy.class).contains("designation", search, Case.INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);//contains("middle_name", search, Case.INSENSITIVE).contains("last_name", search, Case.INSENSITIVE)
            }

            List<POJO_User_Hierarchy> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("HIERARCHY LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_hierarchy_users_list(getContext(), R.layout.adapter_hierarchy_users_list, POJO);
            lv.setAdapter(customAdapter);

            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
               // Toast.makeText(getContext(), "NO EMPLOYEE FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_select_designation() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                user_hierarchy_list_FragmentDialog dialog = user_hierarchy_list_FragmentDialog.newInstance("Hello world");

                dialog.setTargetFragment(fragment_Hierarchy_Users_List.this, 300);
                dialog.show(getFragmentManager(), "fdf");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
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





/*
    private void Load_bind_hierarchy_wise_users_listview(final String designation,final String user) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String emp = app_preferences.getString("user", "default");
            JSONArray jsonArray = new JSONArray();

            jsonArray.put("full_name");
            jsonArray.put("first_name");
            jsonArray.put("designation");
            jsonArray.put("email");
            jsonArray.put("username");
            jsonArray.put("name");
            jsonArray.put("modified");
            jsonArray.put("mobile_no1");
            jsonArray.put("last_name");
            jsonArray.put("middle_name");

            //  JSONObject ofilter= new JSONObject();
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();
            JSONArray Filter3 = new JSONArray();

            if (designation.equals("TBM")) {
                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);

                Filter2.put("User");
                Filter2.put("name");
                Filter2.put("in");

                String names = "";
                if (app_preferences.getString("abm", "default").toString() != null && app_preferences.getString("abm", "default").toString() != "") {
                    names = app_preferences.getString("abm", "default").toString();
                }
                if (app_preferences.getString("rbm", "default").toString() != null && app_preferences.getString("rbm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("rbm", "default").toString();
                    } else {
                        names = app_preferences.getString("rbm", "default").toString();
                    }
                }
                if (app_preferences.getString("zbm", "default").toString() != null && app_preferences.getString("zbm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("zbm", "default").toString();
                    } else {
                        names = app_preferences.getString("zbm", "default").toString();
                    }
                }
                if (app_preferences.getString("sm", "default").toString() != null && app_preferences.getString("sm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("sm", "default").toString();
                    } else {
                        names = app_preferences.getString("sm", "default").toString();
                    }
                }
                if (app_preferences.getString("crm", "default").toString() != null && app_preferences.getString("crm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("crm", "default").toString();
                    } else {
                        names = app_preferences.getString("crm", "default").toString();
                    }
                }
                if (app_preferences.getString("nbm", "default").toString() != null && app_preferences.getString("nbm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("nbm", "default").toString();
                    } else {
                        names = app_preferences.getString("nbm", "default").toString();
                    }
                }
                Filter2.put(names);

                Filters.put(Filter1);
                Filters.put(Filter2);

            } else if (designation.equals("ABM")) {

                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);

                Filter2.put("User");
                Filter2.put("abm");
                Filter2.put("=");
                Filter2.put(user);

                Filter3.put("User");
                Filter3.put("name");
                Filter3.put("in");

                String names = "";
                if (app_preferences.getString("rbm", "default").toString() != null && app_preferences.getString("rbm", "default").toString() != "") {
                    names =  app_preferences.getString("rbm", "default").toString();
                }
                if (app_preferences.getString("zbm", "default").toString() != null && app_preferences.getString("zbm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("zbm", "default").toString();
                    } else {
                        names = app_preferences.getString("zbm", "default").toString();
                    }
                }
                if (app_preferences.getString("sm", "default").toString() != null && app_preferences.getString("sm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("sm", "default").toString();
                    } else {
                        names = app_preferences.getString("sm", "default").toString();
                    }
                }
                if (app_preferences.getString("crm", "default").toString() != null && app_preferences.getString("crm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("crm", "default").toString();
                    } else {
                        names = app_preferences.getString("crm", "default").toString();
                    }
                }
                if (app_preferences.getString("nbm", "default").toString() != null && app_preferences.getString("nbm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("nbm", "default").toString();
                    } else {
                        names = app_preferences.getString("nbm", "default").toString();
                    }
                }
                Filter3.put(names);

                Filters.put(Filter1);
                Filters.put(Filter2);
                Filters.put(Filter3);

            } else if (designation.equals("RBM")) {

                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);

                Filter2.put("User");
                Filter2.put("rbm");
                Filter2.put("=");
                Filter2.put(user);

                Filter3.put("User");
                Filter3.put("name");
                Filter3.put("in");
                String names = "";

                if (app_preferences.getString("zbm", "default").toString() != null && app_preferences.getString("zbm", "default").toString() != "") {
                    names = app_preferences.getString("zbm", "default").toString();
                }
                if (app_preferences.getString("sm", "default").toString() != null && app_preferences.getString("sm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("sm", "default").toString();
                    } else {
                        names = app_preferences.getString("sm", "default").toString();
                    }
                }
                if (app_preferences.getString("crm", "default").toString() != null && app_preferences.getString("crm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("crm", "default").toString();
                    } else {
                        names = app_preferences.getString("crm", "default").toString();
                    }
                }
                if (app_preferences.getString("nbm", "default").toString() != null && app_preferences.getString("nbm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("nbm", "default").toString();
                    } else {
                        names = app_preferences.getString("nbm", "default").toString();
                    }
                }
                Filter3.put(names);

                Filters.put(Filter1);
                Filters.put(Filter2);
                Filters.put(Filter3);

            } else if (designation.equals("ZBM")) {

                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);

                Filter2.put("User");
                Filter2.put("zbm");
                Filter2.put("=");
                Filter2.put(user);

                Filter3.put("User");
                Filter3.put("name");
                Filter3.put("in");
                String names = "";

                if (app_preferences.getString("sm", "default").toString() != null && app_preferences.getString("sm", "default").toString() != "") {
                    names = app_preferences.getString("sm", "default").toString();
                }
                if (app_preferences.getString("crm", "default").toString() != null && app_preferences.getString("crm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("crm", "default").toString();
                    } else {
                        names = app_preferences.getString("crm", "default").toString();
                    }
                }
                if (app_preferences.getString("nbm", "default").toString() != null && app_preferences.getString("nbm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("nbm", "default").toString();
                    } else {
                        names = app_preferences.getString("nbm", "default").toString();
                    }
                }
                Filter3.put(names);

                Filters.put(Filter1);
                Filters.put(Filter2);
                Filters.put(Filter3);

            } else if (designation.equals("SM")) {

                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);

                Filter2.put("User");
                Filter2.put("sm");
                Filter2.put("=");
                Filter2.put(user);

                Filter3.put("User");
                Filter3.put("name");
                Filter3.put("in");
                String names = "";

                if (app_preferences.getString("crm", "default").toString() != null && app_preferences.getString("crm", "default").toString() != "") {
                    names = app_preferences.getString("crm", "default").toString();
                }
                if (app_preferences.getString("nbm", "default").toString() != null && app_preferences.getString("nbm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("nbm", "default").toString();
                    } else {
                        names = app_preferences.getString("nbm", "default").toString();
                    }
                }
                Filter3.put(names);

                Filters.put(Filter1);
                Filters.put(Filter2);
                Filters.put(Filter3);
            }
            else if (designation.equals("CRM")) {

                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);

                Filter2.put("User");
                Filter2.put("nbm");
                Filter2.put("=");
                Filter2.put(user);

                Filter3.put("User");
                Filter3.put("name");
                Filter3.put("in");
                String names = "";

                if (app_preferences.getString("nbm", "default").toString() != null && app_preferences.getString("nbm", "default").toString() != "") {
                    if (names != null) {
                        names += "," + app_preferences.getString("nbm", "default").toString();
                    } else {
                        names = app_preferences.getString("nbm", "default").toString();
                    }
                }
                Filter3.put(names);

                Filters.put(Filter1);
                Filters.put(Filter2);
                Filters.put(Filter3);
            }
            else if (designation.equals("NBM")||designation.equals("Head of Marketing and Sales")||designation.equals("HR Manager")) {

                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);
                Filters.put(Filter1);

            }

            pDialog.show();

            restService.getService().getUser(sid, limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    pDialog.hide();
                    // ListView lv = (ListView) findViewById(R.id.listView);
                    JsonObject j1 = jsonElement.getAsJsonObject();
                    JsonArray j2 = j1.getAsJsonArray("data");

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<POJO_User_Hierarchy>>() {
                    }.getType();
                    List<POJO_User_Hierarchy> POJO = gson.fromJson(j2, type);

                    //////////////////////////////////////////
                    for (POJO_User_Hierarchy pp : POJO) {
                        if (designation.equals("TBM")) {
                            pp.setFlag("T");
                        }
                        else if (designation.equals("ABM")) {
                            if(pp.getDesignation().equals("TBM")) {
                                pp.setFlag("B");
                            }
                            else
                            {
                                pp.setFlag("T");
                            }
                        }
                        else if (designation.equals("RBM")) {
                            if(pp.getDesignation().equals("TBM")||pp.getDesignation().equals("ABM")) {
                                pp.setFlag("B");
                            }
                            else
                            {
                                pp.setFlag("T");
                            }
                        }
                        else if (designation.equals("ZBM")) {
                            if(pp.getDesignation().equals("TBM")||pp.getDesignation().equals("ABM")||pp.getDesignation().equals("RBM")) {
                                pp.setFlag("B");
                            }
                            else
                            {
                                pp.setFlag("T");
                            }
                        }
                        else if (designation.equals("SM")) {
                            if(pp.getDesignation().equals("TBM")||pp.getDesignation().equals("ABM")||pp.getDesignation().equals("RBM")||pp.getDesignation().equals("ZBM")) {
                                pp.setFlag("B");
                            }
                            else
                            {
                                pp.setFlag("T");
                            }
                        }
                        else if (designation.equals("CRM")) {
                            if(pp.getDesignation().equals("TBM")||pp.getDesignation().equals("ABM")||pp.getDesignation().equals("RBM")||pp.getDesignation().equals("ZBM")||pp.getDesignation().equals("SM")) {
                                pp.setFlag("B");
                            }
                            else
                            {
                                pp.setFlag("T");
                            }
                        }
                        else if (designation.equals("NBM")||designation.equals("Head of Marketing and Sales")||designation.equals("HR Manager")) {
                            pp.setFlag("B");
                        }
                    }
                    /////////////////////////////////////////////////////////////////////////


                    if (POJO.size() == 0) {
                        datafull = true;
                        Bind_data_listview();


                    } else {
                        limitstart = limitstart + pagesize;

                    }

                    mRealm = Realm.getDefaultInstance();
                    mRealm.beginTransaction();
                    mRealm.copyToRealmOrUpdate(POJO);
                    mRealm.commitTransaction();
                    mRealm.close();

                    if (bool_full_update == false) {
                        for (POJO_User_Hierarchy pp : POJO) {

                            if (last_POJO == null) {
                                datafull = false;
                            } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                datafull = true;
                                Bind_data_listview();
                            }
                        }
                    }


                    if (datafull == false) {
                        Load_bind_hierarchy_wise_users_listview(designation,user);
                    }

                }


                @Override
                public void failure(RetrofitError error) {

                    pDialog.hide();
                    String msg = error.getMessage();

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

                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }*/
