package com.example.vin.myapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Use the {@link fragment_primary_secondary_on_app#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_primary_secondary_on_app extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int year, month, day;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ///********
    //public RealmResults<POJO_Primary_Secondary_On_App> POJO_User_Obj;
    ///********

    private Realm mRealm;
    RestService restService;
    public String designation = "", employeename = "ALL", User_ID_List_String = "";

    int limitstart = 0;
    int pagesize = 10;
    public boolean bool_full_update = true;
    boolean datafull = false;
    ProgressDialog pDialog;

    public POJO_Primary_Secondary_On_App last_POJO;
    public adapter_primary_secondary_on_app1 adapter;
    public RecyclerAdapter_primary_secondary_app mAdapter;
    private OnFragmentInteractionListener mListener;

    private long mLastClickTime = 0;
    Bundle bundle;

    TextView tv_txt_from_date;
    TextView tv_txt_to_date;

    public fragment_primary_secondary_on_app() {
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
    public static fragment_primary_secondary_on_app newInstance(String param1, String param2) {
        fragment_primary_secondary_on_app fragment = new fragment_primary_secondary_on_app();
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

        View view = inflater.inflate(R.layout.fragment_primary_secondary_on_app, container, false);

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
            ((DashBord_main) getActivity()).setActionBarTitle("PRIMARY DATA");
            init_control();
            //loadevents();
            pDialog = new ProgressDialog(getContext());
            final Bundle bundle = this.getArguments();
            if (bundle != null) {
                //User,Stockist,FromDate,ToDate,Products
                /*String user = app_preferences.getString("user", "");
                String stockist = (app_preferences.getString("stockist", ""));
                String fromdate = (app_preferences.getString("fromdate", ""));
                String todate = (app_preferences.getString("todate", ""));
                String products = (app_preferences.getString("products", ""));*/

                String user = bundle.getString("user", "");
                String stockist = bundle.getString("stockist", "");
                String fromdate = bundle.getString("fromdate", "");
                String todate = bundle.getString("todate", "");
                String products = bundle.getString("products", "");
                String branch = bundle.getString("branch", "");
                String flag = bundle.getString("flag", "");

                tv_txt_from_date.setText(fromdate);//"2018-05-01");
                tv_txt_to_date.setText(todate);//"2018-05-31");
                //data_fetch("kasimmevekari@gmail.com", "Drug Distributor", "2018-05-01", "2018-05-31", "EMPOWER - OD TAB");
                data_fetch(user, stockist, fromdate, todate, "EMPOWER - OD TAB", branch, flag);
                //data_fetch(user, stockist, fromdate, todate, products);
            } else {

            }

            super.onStart();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void init_control() {
        try {
            tv_txt_from_date = (TextView) getView().findViewById(R.id.tv_txt_from_date);
            tv_txt_to_date = (TextView) getView().findViewById(R.id.tv_txt_to_date);

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void loadevents() {
        try {

            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.GONE);
            txt_loading.setText("");

            ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        restService = new RestService();
                        // edit_search.setText("");
                        /*mRealm = Realm.getDefaultInstance();
                        RealmResults<POJO_Primary_Secondary_On_App> result_query2 = mRealm.where(POJO_Primary_Secondary_On_App.class).findAll().sort("modified", Sort.DESCENDING);
                        mRealm.beginTransaction();
                        result_query2.deleteAllFromRealm();
                        mRealm.commitTransaction();
                        mRealm.close();*/

                        bool_full_update = false;
                        datafull = false;
                        limitstart = 0;

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void data_fetch(String user, String stockist, String fromdate, String todate, String products, String branch, String flag) {
        try {
            /*mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_Primary_Secondary_On_App.class);
            mRealm.commitTransaction();
            mRealm.close();
            limitstart = 0;*/
            if (flag.equals("E")) {
                Load_Primary_Secondary_On_App(user, stockist, fromdate, todate, products);
            } else if (flag.equals("S")) {
                Load_Primary_Secondary_On_App_for_stockist(user, stockist, fromdate, todate, products, branch);
            }else{
                Toast.makeText(getContext(),"Invalid Filter Choosen... Please Try Again...", Toast.LENGTH_SHORT).show();
            }
            //Check_class_user_null_or_not();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Check_class_user_null_or_not() {
        try {
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Load_Primary_Secondary_On_App(final String user, final String stockist, final String fromdate, final String todate, final String products) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            /*String emp = app_preferences.getString("name", "default");
            String Designation = app_preferences.getString("designation", "default");*/

            pDialog.show();
            restService.getService().getPrimary_Secondary_App(sid, user, stockist, fromdate, todate, products, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {

                    try {
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("message");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Primary_Secondary_On_App>>() {
                        }.getType();
                        List<POJO_Primary_Secondary_On_App> POJO = gson.fromJson(j2, type);

                        /*mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();
                        mRealm.close();*/
                        pDialog.hide();
                        Bind_data_listview(POJO);

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
                            Toast.makeText(getContext(), "ERROR...", Toast.LENGTH_SHORT).show();
                        } else if (msg.contains("139.59.63.181")) {
                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
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
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Load_Primary_Secondary_On_App_for_stockist(final String user, final String stockist, final String fromdate, final String todate, final String products, final String branch) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            /*String emp = app_preferences.getString("name", "default");
            String Designation = app_preferences.getString("designation", "default");*/

            pDialog.show();
            restService.getService().get_primary_data_of_stockist(sid, user, stockist, fromdate, todate, products, branch, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {

                    try {
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("message");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Primary_Secondary_On_App>>() {
                        }.getType();
                        List<POJO_Primary_Secondary_On_App> POJO = gson.fromJson(j2, type);

                        /*mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();
                        mRealm.close();*/
                        pDialog.hide();
                        Bind_data_listview(POJO);

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
                            Toast.makeText(getContext(), "ERROR...", Toast.LENGTH_SHORT).show();
                        } else if (msg.contains("139.59.63.181")) {
                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
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
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Bind_data_listview(final List<POJO_Primary_Secondary_On_App> mList) {
        try {

            ListView primary_secondary_on_app_list_1 = (ListView) getView().findViewById(R.id.primary_secondary_on_app_list_1);
            adapter = new adapter_primary_secondary_on_app1(getContext(), R.layout.adapter_primary_secondary_on_app1, mList);
            primary_secondary_on_app_list_1.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void alert_box() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(Html.fromHtml("<font color='#FF7F27'>Message</font>"));
            builder.setMessage("Not Load Fully Hierarchy. Please Load Hierarchy Fully...");
            builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    Fragment frag = new fragment_Hierarchy_Users_List();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, frag);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack("objective_list");
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

    public boolean onBackPressed() {
        return false;
    }

}
