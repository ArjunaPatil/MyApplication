package com.example.vin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DialogFragment_choose_doctor_in_dcr extends android.support.v4.app.DialogFragment {


    RecyclerView recyclerView;
    List<MovieModel> movies;
    MoviesAdapter adapter;
    //MoviesApi api;
    String TAG = "MainActivity - ";
    Context context;
    private Realm mRealm;
    RestService restService;

    int limitstart = 0;
    int pagesize = 3;
    boolean datafull = false;
    public boolean bool_full_update = false;

    public POJO_User_S last_POJO;

    // Empty constructor required for DialogFragment
    public DialogFragment_choose_doctor_in_dcr() {
    }

    public static DialogFragment_choose_doctor_in_dcr newInstance(String title) {
        DialogFragment_choose_doctor_in_dcr frag = new DialogFragment_choose_doctor_in_dcr();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        restService = new RestService();
        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
        mRealm.delete(POJO_User_S.class);
        mRealm.commitTransaction();
        mRealm.close();

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.dialogfragment_choose_doctor_in_dcr, container);


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        movies = new ArrayList<>();

        adapter = new MoviesAdapter(getContext(), movies);
        adapter.setLoadMoreListener(new MoviesAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = movies.size() - 1;
                        // loadMore(index);
                        limitstart = index;
                        loadMore();
                    }
                });
                //Calling loadMore function in Runnable to fix the
                // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalLineDecorator(2));
        recyclerView.setAdapter(adapter);

        // api = ServiceGenerator.createService(MoviesApi.class);
        limitstart = 0;
        Load_User();
        return view;


    }

    public void Load_User() {
        try {

            //Thread.sleep(sleep_wait);
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
                public void success(JsonElement jsonElement, Response response) {

                    // ListView lv = (ListView) findViewById(R.id.listView);
                    JsonObject j1 = jsonElement.getAsJsonObject();
                    JsonArray j2 = j1.getAsJsonArray("data");


                    //    List<LiveWork> LiveWorks=new ArrayList<LiveWork>();

                    // JSONArray jsonArray = new JSONArray(jsonArrayString);
                    //  List<String> list = new ArrayList<String>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<POJO_User_S>>() {
                    }.getType();
                    List<POJO_User_S> POJO = gson.fromJson(j2, type);
              /*  for (Class_Doctor_Master contact : LiveWorks){
                    // Log.i("Contact Details", contact.getname() + "-" + contact.getemployee() + "-" + contact.getwork_time());
                }*/
                    Log.i("Success ", "IN:" + limitstart + "size:" + POJO.size());

                    if (POJO.size() == 0) {
                        datafull = true;

                        // Bind_data_listview();


                    } else {
                        limitstart = limitstart + pagesize;

                    }


                    mRealm = Realm.getDefaultInstance();

                    mRealm.beginTransaction();

                    mRealm.copyToRealmOrUpdate(POJO);
                    mRealm.commitTransaction();


                    mRealm.close();

                    if (bool_full_update == false) {
                        for (POJO_User_S pp : POJO) {
                       /*   String name=pp.getName();
                          String bb=pp.getModified();
                          String cc=last_POJO.getName();
                          String dd=last_POJO.getModified();*/

                            MovieModel listItem = new MovieModel(
                                    pp.getFull_name(),
                                    "movie"
                            );

                            movies.add(listItem);


                            adapter.notifyDataSetChanged();

                            if (last_POJO == null) {
                                datafull = false;
                            } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                datafull = true;

                                //   Bind_data_listview();


                            }
                        }
                    }


                    if (datafull == false) {
                        //Load_User();

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


                }
            });
        } catch (Exception e) {

            // Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void loadMore() {
        try {
            movies.add(new MovieModel("load", ""));
            adapter.notifyItemInserted(movies.size() - 1);


            //Thread.sleep(sleep_wait);
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
                public void success(JsonElement jsonElement, Response response) {

                    // ListView lv = (ListView) findViewById(R.id.listView);
                    JsonObject j1 = jsonElement.getAsJsonObject();
                    JsonArray j2 = j1.getAsJsonArray("data");


                    //    List<LiveWork> LiveWorks=new ArrayList<LiveWork>();

                    // JSONArray jsonArray = new JSONArray(jsonArrayString);
                    //  List<String> list = new ArrayList<String>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<POJO_User_S>>() {
                    }.getType();
                    List<POJO_User_S> POJO = gson.fromJson(j2, type);
              /*  for (Class_Doctor_Master contact : LiveWorks){
                    // Log.i("Contact Details", contact.getname() + "-" + contact.getemployee() + "-" + contact.getwork_time());
                }*/
                    Log.i("Success ", "IN:" + limitstart + "size:" + POJO.size());

                    if (POJO.size() == 0) {
                        datafull = true;

                        // Bind_data_listview();
                        adapter.setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
                        Toast.makeText(context, "No More Data Available", Toast.LENGTH_LONG).show();

                    } else {
                        limitstart = limitstart + pagesize;

                    }


                    mRealm = Realm.getDefaultInstance();

                    mRealm.beginTransaction();

                    mRealm.copyToRealmOrUpdate(POJO);
                    mRealm.commitTransaction();


                    mRealm.close();

                    if (bool_full_update == false) {
                        for (POJO_User_S pp : POJO) {
                       /*   String name=pp.getName();
                          String bb=pp.getModified();
                          String cc=last_POJO.getName();
                          String dd=last_POJO.getModified();*/

                            movies.remove(movies.size() - 1);

                            MovieModel listItem = new MovieModel(
                                    pp.getFull_name(),
                                    "movie"
                            );
                            //if(listItem.size()>0){
                            //add loaded data
                            movies.add(listItem);
                            //}else{//result size 0 means there is no more data available at server
                            //    adapter.setMoreDataAvailable(false);
                            //telling adapter to stop calling load more as no more server data available
                            //     Toast.makeText(context,"No More Data Available",Toast.LENGTH_LONG).show();
                            // }
                            adapter.notifyDataChanged();

                            if (last_POJO == null) {
                                datafull = false;
                            } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                datafull = true;

                                adapter.setMoreDataAvailable(false);
                                //telling adapter to stop calling load more as no more server data available
                                // Toast.makeText(context, "No More Data Available", Toast.LENGTH_LONG).show();
                                //   Bind_data_listview();


                            }
                        }
                    }


                    if (datafull == false) {
                        //Load_User();

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


                }
            });
        } catch (Exception e) {

            // Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        int width = getContext().getResources().getDisplayMetrics().widthPixels;
        TextView txt_test = (TextView) getView().findViewById(R.id.txt_test);
        txt_test.setWidth(width);

    }


    @Override
    public void onStart() {


        super.onStart();
       /* recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getContext()));

         listItems=new ArrayList<>();
        adapter=new MyAdapter(listItems,getContext());
        recyclerView.setAdapter(adapter);
        *//*for (int i=0; i <=10 ; i++)
        {
            ListItem listItem=new ListItem(
                    "Heading" + (i+1),
                    "Description...."
            );

            listItems.add(listItem);

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            adapter.notifyDataSetChanged();

        }*//*



       //  Load_User();*/


    }

}