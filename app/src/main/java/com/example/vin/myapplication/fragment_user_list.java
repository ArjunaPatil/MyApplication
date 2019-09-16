package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;
import static com.example.vin.myapplication.MoviesAdapter.context;

//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_user_list#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_user_list extends Fragment
        implements fragment_dialog_for_user_list_active_inactive.EditUserStatusDialogListener,
        fragment_dialog_for_user_list_filters.ParamUserListSetFilterListener {
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
    int pagesize = 10;//10
    boolean datafull = false;
    int sleep_wait = 0;

    public boolean bool_full_update = true;
    public POJO_User_List last_POJO;

    private OnFragmentInteractionListener mListener;
    ListView listView;
    public EditText edit_search;

    public Integer allow_user_for_user_form = 0;
    private ProgressDialog pDialog;

    RestService restService;
    public adapter_fragment_user_list customAdapter;

    public RealmResults<POJO_User_List> result_query;
    Bundle bundle;

    LinearLayout select_user_status;
    LinearLayout select_user_status1;
    TextView select_user_status2;
    TextView name_status_of_status;
    ImageButton select_user_status3;
    View vw_user_status;

    private long mLastClickTime = 0;

    /*reset all users parameter*/
    Integer profile_master = 0;
    Integer patch_master = 0;
    Integer doctor_master = 0;
    Integer chemist_master = 0;
    String objective_lock_time = "", doctor_start_time = "", chemist_start_time = "";

    public fragment_user_list() {
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
    public static fragment_user_list newInstance(String param1, String param2) {
        fragment_user_list fragment = new fragment_user_list();
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
            allow_user_for_user_form = Integer.valueOf(app_preferences.getString("allow_user_for_user_form", "0"));

            if (allow_user_for_user_form == 1) {

                ((DashBord_main) getActivity()).setActionBarTitle("USER LIST");
                init_control();
                loadevents();

                pDialog = new ProgressDialog(getContext());

                select_user_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show_dialog_for_select_status();
                    }
                });
                select_user_status1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show_dialog_for_select_status();
                    }
                });
                select_user_status2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show_dialog_for_select_status();
                    }
                });
                select_user_status3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show_dialog_for_select_status();
                    }
                });

                data_fetch();
                super.onStart();
            } else {
                Toast.makeText(getContext(), "RESTRICTED ROUT", Toast.LENGTH_LONG).show();
                /*FragmentManager fm = getActivity().getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }*/
                FragmentManager manager = getActivity().getSupportFragmentManager();
                if (manager.getBackStackEntryCount() > 0) {
                    FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - (manager.getBackStackEntryCount() - 1));
                    manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
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
        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_list, container, false);
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
    public void onFinishUserStatusDialog(String id, String status) {
        try {
            TextView select_user_status2 = (TextView) getView().findViewById(R.id.select_user_status2);
            select_user_status2.setText(status.toString());

            TextView name_doctor_of_employee = (TextView) getView().findViewById(R.id.name_status_of_status);
            name_doctor_of_employee.setText(id.toString());

            bind_status_wise_users_listview(status.toString());
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishParamUserListSetFilter(String filter_flag) {
        try {
            if (filter_flag.equals("Y")) {
                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String parameter = (app_preferences.getString("filter_for_user_list", "0"));
                bind_status_wise_users_listview(parameter);
            }

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
            select_user_status = (LinearLayout) getView().findViewById(R.id.select_user_status);
            select_user_status1 = (LinearLayout) getView().findViewById(R.id.select_user_status1);
            select_user_status3 = (ImageButton) getView().findViewById(R.id.select_user_status3);
            select_user_status2 = (TextView) getView().findViewById(R.id.select_user_status2);
            name_status_of_status = (TextView) getView().findViewById(R.id.name_status_of_status);

            vw_user_status = getView().findViewById(R.id.vw_user_status);

            /*  select_user_status.setVisibility(View.GONE);
            select_user_status1.setVisibility(View.GONE);
            select_user_status3.setVisibility(View.GONE);
            select_user_status2.setVisibility(View.GONE);
            name_status_of_status.setVisibility(View.GONE);
            vw_user_status.setVisibility(View.GONE);

            if (allow_user_for_user_form.equals(1)) {
                select_user_status.setVisibility(View.VISIBLE);
                select_user_status1.setVisibility(View.VISIBLE);
                select_user_status3.setVisibility(View.VISIBLE);
                select_user_status2.setVisibility(View.VISIBLE);
                name_status_of_status.setVisibility(View.VISIBLE);
                vw_user_status.setVisibility(View.VISIBLE);
            }*/
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
                        select_user_status2.setText("Select Status");

                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.delete(POJO_User_List.class);
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
                        Load_User_List();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });


            ImageButton btn_filter_data = (ImageButton) getView().findViewById(R.id.btn_filter_data);
            btn_filter_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        show_dialog_for_select_status();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }

                }
            });

            ImageButton btn_reset_data = (ImageButton) getView().findViewById(R.id.btn_reset_data);
            btn_reset_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        alert_box();
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

            ListView lv = (ListView) getView().findViewById(R.id.user_list_view);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              try {
                                                  POJO_User_List clickedObj = (POJO_User_List) parent.getItemAtPosition(position);

                                                  final Bundle bundle = new Bundle();
                                                  bundle.putString("user", clickedObj.getName()); // use as per your need
                                                  String user_name = clickedObj.getFull_name();
                                                  bundle.putString("user_name", user_name);
                                                  /*bundle.putString("patch_name", clickedObj.getPatch_name());
                                                  bundle.putString("headquarter", clickedObj.getHeadquarter());
                                                  bundle.putString("user", clickedObj.getUser());
                                                  bundle.putString("user_name", clickedObj.getUser());*/
                                                  Fragment frag = new fragment_user_lock();

                                                  FragmentTransaction ft = getFragmentManager().beginTransaction();
                                                  ft.replace(R.id.content_frame, frag);
                                                  frag.setArguments(bundle);
                                                  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                                  ft.addToBackStack("user_list");
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

                RealmResults<POJO_User_List> result_query1 = mRealm.where(POJO_User_List.class).findAll().sort("modified", Sort.DESCENDING);
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

    private void Load_User_List() {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

/*            JSONArray jsonArray = new JSONArray();
            jsonArray.put("name");
            jsonArray.put("username");
            jsonArray.put("email");
            jsonArray.put("employee_code");
            jsonArray.put("first_name");
            jsonArray.put("middle_name");
            jsonArray.put("last_name");
            jsonArray.put("designation");
            jsonArray.put("mobile_no1");
            jsonArray.put("enabled");
            jsonArray.put("modified");
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            Filter1.put("User");
            Filter1.put("designation");
            Filter1.put("in");
            Filter1.put("TBM,ABM,RBM,ZBM,SM,NBM,CRM");
            Filters.put(Filter1);*/

            pDialog.show();
            restService.getService().getUser_List_with_lock_flag(sid, pagesize, limitstart, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("message");
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_User_List>>() {
                        }.getType();
                        List<POJO_User_List> POJO = gson.fromJson(j2, type);

                        /*--------*/
                        if (POJO == null || POJO.size() == 0) {
                            datafull = true;
                            //Bind_data_listview();
                            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                            String parameter = (app_preferences.getString("filter_for_user_list", "0"));
                            bind_status_wise_users_listview(parameter);
                            pDialog.hide();
                        } else {
                            limitstart = limitstart + pagesize;
                            mRealm = Realm.getDefaultInstance();
                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(POJO);
                            mRealm.commitTransaction();
                            mRealm.close();

                            if (bool_full_update == false) {
                                for (POJO_User_List pp : POJO) {
                                    if (last_POJO == null) {
                                        datafull = false;
                                    } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                        datafull = true;
                                        //Bind_data_listview();
                                        final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                                        String parameter = (app_preferences.getString("filter_for_user_list", "0"));
                                        bind_status_wise_users_listview(parameter);
                                        pDialog.hide();
                                    }
                                }
                            }
                        }

                        if (datafull == false) {
                            Load_User_List();
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
                            Toast.makeText(getContext(), "ACCESS FORBIDDEN", Toast.LENGTH_SHORT).show();
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

            RealmResults<POJO_User_List> result_query1 = mRealm.where(POJO_User_List.class).findAll().sort("modified", Sort.DESCENDING);
            if (result_query1.size() > 0) {
                //Bind_data_listview();
                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String parameter = (app_preferences.getString("filter_for_user_list", "0"));
                bind_status_wise_users_listview(parameter);
            } else {
                bool_full_update = false;
                full_update_calc();
                limitstart = 0;
                datafull = false;

                Load_User_List();
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Bind_data_listview() {
        try {

            ListView lv = (ListView) getView().findViewById(R.id.user_list_view);
            final RealmResults<POJO_User_List> result_query1 = mRealm.where(POJO_User_List.class).findAll().sort("modified", Sort.DESCENDING);
            List<POJO_User_List> POJO = result_query1;
            ((DashBord_main) getActivity()).setActionBarTitle("USER LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_user_list(getContext(), R.layout.adapter_user_list, POJO);
            lv.setAdapter(customAdapter);
            if (POJO.size() == 0) {
                //Toast.makeText(getContext(), "NO EMPLOYEE FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {

            }

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Searching_data_listview(String search) {
        try {
            ListView lv = (ListView) getView().findViewById(R.id.user_list_view);
            final RealmResults<POJO_User_List> result_query1 = mRealm.where(POJO_User_List.class).contains("full_name", search, Case.INSENSITIVE).findAll().sort("modified", Sort.DESCENDING);//contains("middle_name", search, Case.INSENSITIVE).contains("last_name", search, Case.INSENSITIVE)
            List<POJO_User_List> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("USER LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_user_list(getContext(), R.layout.adapter_user_list, POJO);

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

    public void bind_status_wise_users_listview(String search) {
        try {
            ListView lv = (ListView) getView().findViewById(R.id.user_list_view);
            final RealmResults<POJO_User_List> result_query1;

            if (search.equals("1")) {
                result_query1 = mRealm.where(POJO_User_List.class).findAll().sort("modified", Sort.DESCENDING);//contains("middle_name", search, Case.INSENSITIVE).contains("last_name", search, Case.INSENSITIVE)
            } else if (search.equals("2")) {
                result_query1 = mRealm.where(POJO_User_List.class).equalTo("enabled", 1).findAll().sort("modified", Sort.DESCENDING);//contains("middle_name", search, Case.INSENSITIVE).contains("last_name", search, Case.INSENSITIVE)
            } else if (search.equals("3")) {
                result_query1 = mRealm.where(POJO_User_List.class).equalTo("mast_flag", 0).findAll().sort("modified", Sort.DESCENDING);//contains("middle_name", search, Case.INSENSITIVE).contains("last_name", search, Case.INSENSITIVE)
            } else if (search.equals("4")) {
                result_query1 = mRealm.where(POJO_User_List.class).equalTo("trans_flag", 0).findAll().sort("modified", Sort.DESCENDING);//contains("middle_name", search, Case.INSENSITIVE).contains("last_name", search, Case.INSENSITIVE)
            } else {
                result_query1 = mRealm.where(POJO_User_List.class).findAll().sort("modified", Sort.DESCENDING);//contains("middle_name", search, Case.INSENSITIVE).contains("last_name", search, Case.INSENSITIVE)
            }

            List<POJO_User_List> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("USER LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_user_list(getContext(), R.layout.adapter_user_list, POJO);
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

    private void show_dialog_for_select_status() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                /*fragment_dialog_for_user_list_active_inactive dialog = fragment_dialog_for_user_list_active_inactive.newInstance("Hello world");
                dialog.setTargetFragment(fragment_user_list.this, 300);
                dialog.show(getFragmentManager(), "fdf");*/

                fragment_dialog_for_user_list_filters dialog = fragment_dialog_for_user_list_filters.newInstance("Hello world");
                dialog.setTargetFragment(fragment_user_list.this, 300);
                dialog.show(getFragmentManager(), "fdf");

            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }


    /*Reset ALL Time And Date*/
//Api Call Ex. : http://139.59.63.181/api/method/team.user_lock.update_user_lock_time_and_date/?send_opr_flag=Y
    private void alert_box() {
        try {
            final CharSequence[] items = {" SET DEFAULT SETTING ", " RESET ALL LOCK ",};

            //// arraylist to keep the selected items
            //final ArrayList seletedItems = new ArrayList();

            AlertDialog dialog = new AlertDialog.Builder(getContext())
                    .setTitle("CHOOSE SETTING OPTION")
                    .setSingleChoiceItems(items, 0, null)
                    /*.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                            if (isChecked) {
                                // If the user checked the item, add it to the selected items
                                seletedItems.add(indexSelected);
                            } else if (seletedItems.contains(indexSelected)) {
                                // Else, if the item is already in the array, remove it
                                seletedItems.remove(Integer.valueOf(indexSelected));
                            }
                        }
                    })*/.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            int selectedPosition = ((AlertDialog) dialog).getListView().getCheckedItemPosition();
                            if (selectedPosition == 0) {
                                update_user_lock_time_date1();
                            } else if (selectedPosition == 1) {
                                reset_all_master_lock();
                            }
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //  Your code when user clicked on Cancel
                        }
                    }).create();
            dialog.show();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void alert_box1() {
        try {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Message</font>"));
            builder.setMessage("DO YOU WANT TO SET DEFAULT SETTING FOR ALL USER'S");

            //AGAIN TO DEAFULT TIME AND RESET FROM-TO DATES FOR ALL USER'S
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    //reset_all_user_time_date();
                    update_user_lock_time_date1();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void reset_all_user_time_date() {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("profile_master");
            jsonArray.put("patch_master");
            jsonArray.put("doctor_master");
            jsonArray.put("chemist_master");
            jsonArray.put("objective_lock_time");
            jsonArray.put("doctor_start_time");
            jsonArray.put("chemist_start_time");

            /*
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();

            Filter1.put("User");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(user_id);

            Filters.put(Filter1);
            */

            pDialog.show();
            restService.getService().getStd_Lock_time_date(sid, 0, 1, jsonArray, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        // pDialog.hide();
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Standard_Lock_Time>>() {
                        }.getType();
                        List<POJO_Standard_Lock_Time> POJO = gson.fromJson(j2, type);

                        for (POJO_Standard_Lock_Time pp : POJO) {
                            profile_master = pp.getProfile_master();
                            patch_master = pp.getPatch_master();
                            doctor_master = pp.getDoctor_master();
                            chemist_master = pp.getChemist_master();
                            objective_lock_time = pp.getObjective_lock_time();
                            doctor_start_time = pp.getDoctor_start_time();
                            chemist_start_time = pp.getChemist_start_time();
                        }

                        update_user_lock_time_date();
                        // pDialog.hide();
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
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void update_user_lock_time_date() {
        try {
            // pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            //String ss = "update `tabUser` set m_pro=" + profile_master + ",m_pat=" + patch_master + ",m_doc=" + doctor_master + ",m_che=" + chemist_master + ",t_obj_time=" + objective_lock_time + ",t_drc_s_time=" + doctor_start_time + ",t_chc_s_time=" + chemist_start_time + ",t_drc1=" + "" + ",t_drc2=" + "" + ",t_obj1=" + "" + ",t_obj2=" + "" + ",t_chc1=" + "" + ",t_chc2=" + "" + " where enabled=1 and designation in ('TBM','ABM','RBM','SM','NBM')";

            restService.getService().putUser_lock_time_date(sid, profile_master, patch_master, doctor_master, chemist_master, "'" + objective_lock_time + "'", "'" + doctor_start_time + "'", "'" + chemist_start_time + "'", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        Integer flag = Integer.valueOf(j2.get("flag").getAsString());
                        Toast.makeText(getContext(), "RESET ALL USERS SUCCESSFULLY", Toast.LENGTH_SHORT).show();
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
                            // onsession_failure();
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
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

    public void update_user_lock_time_date1() {
        try {
            // pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            //String ss = "update `tabUser` set m_pro=" + profile_master + ",m_pat=" + patch_master + ",m_doc=" + doctor_master + ",m_che=" + chemist_master + ",t_obj_time=" + objective_lock_time + ",t_drc_s_time=" + doctor_start_time + ",t_chc_s_time=" + chemist_start_time + ",t_drc1=" + "" + ",t_drc2=" + "" + ",t_obj1=" + "" + ",t_obj2=" + "" + ",t_chc1=" + "" + ",t_chc2=" + "" + " where enabled=1 and designation in ('TBM','ABM','RBM','SM','NBM')";

            restService.getService().putUser_lock_time_date1(sid, "Y", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        Integer flag = Integer.valueOf(j2.get("flag").getAsString());
                        if (flag == 1) {
                            Toast.makeText(getContext(), "SET DEFAULT LOCK SETTING FOR ALL USERS SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getContext(), "RESET ALL USERS SUCCESSFULLY", Toast.LENGTH_SHORT).show();
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
                            // onsession_failure();
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
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

    /*Unlock All Patch,Doctor,Chemist*/
    public void reset_all_master_lock() {
        try {
            // pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            restService.getService().putResetAllLockedMasters(sid, "Y", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        Integer flag = Integer.valueOf(j2.get("flag").getAsString());
                        if (flag == 1) {
                            Toast.makeText(getContext(), "RESET ALL MASTERS FOR ALL ACTIVE USERS SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getContext(), "RESET ALL USERS SUCCESSFULLY", Toast.LENGTH_SHORT).show();
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
                            // onsession_failure();
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
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

    android.os.Handler handle = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pDialog.incrementProgressBy(1);
        }
    };

}
//allow_for_app_user_change