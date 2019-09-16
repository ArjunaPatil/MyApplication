package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.RetrofitError;

//import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link fragment_recycler1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_recycler1 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //https://stackoverflow.com/questions/32399751/android-how-to-load-more-items-for-recycleview-on-scroll
    //https://stackoverflow.com/questions/36994472/android-recyclerview-load-more-on-scrolltop


    ////add for load doctors
    private Realm mRealm;
    int limitstart = 0;
    int pagesize = 5;
    boolean datafull = false;
    int sleep_wait = 0;

    public boolean bool_full_update = false;
    public POJO_Chemist last_POJO;
    public RealmResults<POJO_User_Hierarchy> POJO_User_Obj;
    private OnFragmentInteractionListener mListener;

    ListView listView;
    public EditText edit_search;
    public String designation = "", employeename = "ALL", User_ID_List_String = "";

    private ProgressDialog pDialog;
    RestService restService;
    Bundle bundle;

    public fragment_recycler1() {
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
    public static fragment_recycler1 newInstance(String param1, String param2) {
        fragment_recycler1 fragment = new fragment_recycler1();
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
            ((DashBord_main) getActivity()).setActionBarTitle("Recycler View");
            init_control();
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
        return inflater.inflate(R.layout.fragment_view_recycler1, container, false);
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

    public void init_control() {
        try {

            studentList = new ArrayList<POJO_User_S>();
            restService = new RestService();
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_User_S.class);
            mRealm.commitTransaction();
            mRealm.close();

            Load_User();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void show_view() {
        try {

            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mAdapter = new RecyclerAdapter2(recyclerView, studentList, getContext());
            recyclerView.setAdapter(mAdapter);

            //set load more listener for the RecyclerView adapter
            mAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (datafull == false) {
                        studentList.add(null);
                        mAdapter.notifyItemInserted(studentList.size() - 1);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                studentList.remove(studentList.size() - 1);
                                mAdapter.notifyItemRemoved(studentList.size());

                                //Generating more data
                                int index = studentList.size();
                                limitstart = index;
                                loadMore();
                                 /*int end = index + 10;
                               for (int i = index; i < end; i++) {
                                    Contact contact = new Contact();
                                    contact.setPhone(phoneNumberGenerating());
                                    contact.setEmail("DevExchanges" + i + "@gmail.com");
                                    studentList.add(contact);
                                }
                                mAdapter.notifyDataSetChanged();
                                mAdapter.setLoaded();*/
                            }
                        }, 5000);
                    } else {
                        Toast.makeText(getContext(), "Loading data completed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    RecyclerAdapter2 mAdapter;

    private List<POJO_User_S> studentList;
    protected Handler handler;

    public void Load_User() {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("full_name");
            jsonArray.put("headquarter_name");
            jsonArray.put("modified");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            Filter1.put("User");
            Filter1.put("enabled");
            Filter1.put("=");
            Filter1.put("1");

            Filters.put(Filter1);

            restService.getService().getUser_S(sid, "modified desc", limitstart, pagesize, jsonArray, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, retrofit.client.Response response) {
                    try {
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_User_S>>() {
                        }.getType();
                        List<POJO_User_S> POJO = gson.fromJson(j2, type);

                        Log.i("Success ", "IN:" + limitstart + "size:" + POJO.size());

                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(POJO);
                        mRealm.commitTransaction();
                        mRealm.close();

                        if (POJO.size() == 0) {
                            datafull = true;
                        } else {
                            limitstart = limitstart + pagesize;
                            for (POJO_User_S pp : POJO) {
                                POJO_User_S listItem = new POJO_User_S(pp.getName(), pp.getFull_name());
                                studentList.add(listItem);
                            }
                            show_view();
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

                            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putString("status", "0");
                            editor.commit();

                            Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            getContext().startActivity(k);

                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
                            txt_loading.setText("ERROR..");
                        } else if (msg.contains("139.59.63.181")) {
                            //Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
                            txt_loading.setText("PLEASE CHECK INTERNET CONNECT");
                        }
                        /* if (task_app_evrsion != null) {
                        task_app_evrsion.cancel(true);
                        }*/
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void loadMore() {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("full_name");
            jsonArray.put("headquarter_name");
            jsonArray.put("modified");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            Filter1.put("User");
            Filter1.put("enabled");
            Filter1.put("=");
            Filter1.put("1");

            Filters.put(Filter1);

            restService.getService().getUser_S(sid, "modified desc", limitstart, pagesize, jsonArray, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, retrofit.client.Response response) {

                    JsonObject j1 = jsonElement.getAsJsonObject();
                    JsonArray j2 = j1.getAsJsonArray("data");

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<POJO_User_S>>() {
                    }.getType();
                    List<POJO_User_S> POJO = gson.fromJson(j2, type);

                    Log.i("Success ", "IN:" + limitstart + "size:" + POJO.size());

                    mRealm = Realm.getDefaultInstance();
                    mRealm.beginTransaction();
                    mRealm.copyToRealmOrUpdate(POJO);
                    mRealm.commitTransaction();
                    mRealm.close();

                    if (POJO.size() == 0) {
                        datafull = true;
                    } else {
                        limitstart = limitstart + pagesize;
                        for (POJO_User_S pp : POJO) {
                            POJO_User_S listItem = new POJO_User_S(pp.getName(), pp.getFull_name());
                            studentList.add(listItem);
                        }
                        mAdapter.notifyDataSetChanged();
                        mAdapter.setLoaded();
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                    String msg = "";
                    if (error.getKind().toString().contains("HTTP")) {
                        msg = error.toString();
                    } else {
                        msg = "139.59.63.181";
                    }

                    if (msg.equals("403 FORBIDDEN")) {

                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.putString("status", "0");
                        editor.commit();

                        Intent k = new Intent(getContext(), Login.class);
                        getContext().startActivity(k);

                        TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                        txt_loading.setVisibility(View.VISIBLE);
                        txt_loading.setText("ERROR..");
                    } else if (msg.contains("139.59.63.181")) {
                        TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                        txt_loading.setVisibility(View.VISIBLE);
                        txt_loading.setText("PLEASE CHECK INTERNET CONNECT");
                    }

                   /* if (task_app_evrsion != null) {
                        task_app_evrsion.cancel(true);
                    }*/

                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pDialog.incrementProgressBy(1);
        }
    };


}