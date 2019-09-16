package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;
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
 * Use the {@link fragment_Hierarchy_Headquarter_List#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_Hierarchy_Headquarter_List extends Fragment {
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
    public POJO_Territory_S last_POJO;
    /////

    private OnFragmentInteractionListener mListener;
    ListView listView;
    public EditText edit_search;
    public String designation = "";

    private ProgressDialog pDialog;

    RestService restService;
    public adapter_fragment_hierarchy_headquarter_list customAdapter;
    Bundle bundle;

    public fragment_Hierarchy_Headquarter_List() {
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
    public static fragment_Hierarchy_Headquarter_List newInstance(String param1, String param2) {
        fragment_Hierarchy_Headquarter_List fragment = new fragment_Hierarchy_Headquarter_List();
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

            ((DashBord_main) getActivity()).setActionBarTitle("HEADQUARTER LIST");

            loadevents();
            pDialog = new ProgressDialog(getContext());

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
        return inflater.inflate(R.layout.fragment_hierarchy_headquarter_list, container, false);
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

    public void loadevents() {
        try {

            edit_search = (EditText) getView().findViewById(R.id.edit_search);

            ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.delete(POJO_Territory_S.class);
                        mRealm.commitTransaction();
                        mRealm.close();

                        bool_full_update = false;
                        full_update_calc();
                        datafull = false;
                        limitstart = 0;

                        Load_bind_hierarchy_wise_hq_listview();

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
                    Searching_data_listview(s.toString());
                    //refreshScreen(s.toString());
                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void full_update_calc() {
        try {

            if ((bool_full_update == false)) {

                RealmResults<POJO_Territory_S> result_query1 = mRealm.where(POJO_Territory_S.class).findAll();
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

    public void data_fetch() {
        try {
            mRealm = Realm.getDefaultInstance();
            RealmResults<POJO_Territory_S> result_query1 = mRealm.where(POJO_Territory_S.class).findAll();
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

                Load_bind_hierarchy_wise_hq_listview();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*Original Method*/
    public void Load_bind_hierarchy_wise_hq_listview() {
        try {
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String territory = "";
            String Designation = app_preferences.getString("designation", "default");

            if (Designation.equals("ABM")) {
                territory = app_preferences.getString("area", "default");
            } else if (Designation.equals("RBM")) {
                territory = app_preferences.getString("region", "default");
            } else if (Designation.equals("ZBM")) {
                territory = app_preferences.getString("zone", "default");
            } else if (Designation.equals("SM")) {
                territory = app_preferences.getString("zone", "default");
            } else if (Designation.equals("CRM")) {
                territory = app_preferences.getString("headquarter", "default");
            } else if ((Designation.equals("NBM")) || (Designation.equals("Head of Marketing and Sales")) || (Designation.equals("HR Manager")) || (Designation.equals("Administrator"))) {
                territory = "All Territories";
            }

            pDialog.show();
            restService.getService().getTerritory_Method(sid, "'" + territory + "'", Designation, pagesize, limitstart, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("message");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Territory_S>>() {
                        }.getType();

                        List<POJO_Territory_S> POJO = new ArrayList<POJO_Territory_S>();
                        List<POJO_Territory_S> POJO1 = new ArrayList<POJO_Territory_S>();
                        List<POJO_Territory_S> POJO2 = new ArrayList<POJO_Territory_S>();
                        if (limitstart < 1 && j2.size() > 1) {
                            POJO_Territory_S ss = new POJO_Territory_S();

                            ss.setHeadquarter_id("ALL");
                            ss.setHeadquarter_name("ALL");
                            ss.setHeadquarter_parent("ALL");
                            POJO1.add(ss);
                            POJO.addAll(POJO1);
                        }
                        POJO2 = gson.fromJson(j2, type);

                        //////////////
                        if (POJO2 == null) {
                            datafull = true;
                            Bind_data_listview();
                            pDialog.hide();
                        } else {
                            POJO.addAll(POJO2);
                            limitstart = limitstart + pagesize;
                            mRealm = Realm.getDefaultInstance();
                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(POJO);
                            mRealm.commitTransaction();
                            mRealm.close();

                            if (bool_full_update == false) {
                                for (POJO_Territory_S pp : POJO) {
                                    if (last_POJO == null) {
                                        datafull = false;
                                    } else if ((pp.getHeadquarter_id().equals(last_POJO.getHeadquarter_id()))) {
                                        datafull = true;
                                        Bind_data_listview();
                                    }
                                }
                            }
                        }

                        if (datafull == false) {
                            Load_bind_hierarchy_wise_hq_listview();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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

                            //SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                            //SharedPreferences.Editor editor = app_preferences.edit();
                            //editor.putString("status", "0");
                            //editor.commit();

                            //Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            //getContext().startActivity(k);

                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
                            txt_loading.setText("ERROR..");
                        } else if (msg.contains("139.59.63.181")) {
                            //Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
                            txt_loading.setText("PLEASE CHECK INTERNET CONNECT");
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

    public void Bind_data_listview() {
        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_Territory_S> result_query1 = mRealm.where(POJO_Territory_S.class).findAll();
            List<POJO_Territory_S> POJO = result_query1;
            ((DashBord_main) getActivity()).setActionBarTitle("HeadQuarter LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_hierarchy_headquarter_list(getContext(), R.layout.adapter_hierarchy_headquarter_list, POJO);
            lv.setAdapter(customAdapter);
            if (POJO.size() == 0) {
                Toast.makeText(getContext(), "NO HeadQuarter FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
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
            final RealmResults<POJO_Territory_S> result_query1 = mRealm.where(POJO_Territory_S.class).contains("headquarter_name", search, Case.INSENSITIVE).findAll();
            List<POJO_Territory_S> POJO = result_query1;

            ((DashBord_main) getActivity()).setActionBarTitle("HEADQUARTER LIST (" + POJO.size() + ")");
            customAdapter = new adapter_fragment_hierarchy_headquarter_list(getContext(), R.layout.adapter_hierarchy_headquarter_list, POJO);

            lv.setAdapter(customAdapter);
            if (POJO.size() == 0) {
                //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                //txt_loading.setVisibility(View.VISIBLE);
                //txt_loading.setText("NO Doctor FOUND FOR SELECTED FILTER..");
                Toast.makeText(getContext(), "NO HeadQuarter FOUND FOR SELECTED FILTER..", Toast.LENGTH_SHORT).show();
            } else {
            /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("Refreshing Data..");*/
            }
        } catch (Exception e) {

            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
