package com.example.vin.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
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

public class MyDialogFragment extends android.support.v4.app.DialogFragment {

    private EditText mEditText;


    private Realm mRealm;
    RestService restService;

    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    public boolean bool_full_update = false;

    public POJO_User_S last_POJO;
    public adapter_user_list_for_dialog adapter;

    public interface UserNameListener {
        void onFinishUserDialog(String user);
    }

    // Empty constructor required for DialogFragment
    public MyDialogFragment() {
    }

    public static MyDialogFragment newInstance(String title) {
        MyDialogFragment frag = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

   /*     getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.fragment_username, container);

       *//* getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("Select TBM");

      *//**//*  Button btn_save = (Button) view.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBackResult();
            }
        });*//*



        data_fetch();

        return view;*/


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.fragment_username, container);

       /* getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("Select TBM");

      *//*  Button btn_save = (Button) view.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendBackResult();
            }
        });*/


        return view;


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
        try {
            ListView lv = (ListView) getView().findViewById(R.id.listView);
      /*  lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //POJO_User_S clickedObj = (POJO_User_S) parent.getItemAtPosition(position);
               // sendBackResult(clickedObj.getName(),clickedObj.getFull_name());
                sendBackResult();
                return true;
            }
        });*/

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              try {
                                                  POJO_User_S clickedObj = (POJO_User_S) parent.getItemAtPosition(position);
                                                  sendBackResult(clickedObj.getName(), clickedObj.getFull_name());

                                              } catch (Exception ex) {
                                                  Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                              }
                                          }
                                      }


            );
            ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.delete(POJO_User_S.class);
                        mRealm.commitTransaction();
                        mRealm.close();

                        TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                        txt_loading.setVisibility(View.VISIBLE);
                        txt_loading.setText("Refreshing Data..");
                        bool_full_update = false;
                        full_update_calc();
                        datafull = false;
                        limitstart = 0;
                        Load_User();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            TextView txt_cancel = (TextView) getView().findViewById(R.id.txt_cancel);
            txt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
                }
            });

            data_fetch();
            super.onStart();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void data_fetch() {
        try {
            mRealm = Realm.getDefaultInstance();

            RealmResults<POJO_User_S> result_query1 = mRealm.where(POJO_User_S.class).findAll().sort("modified", Sort.DESCENDING);
            if (result_query1.size() > 0) {
                Bind_data_listview();
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.GONE);
                txt_loading.setText("Refreshing Data..");
            } else {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("Refreshing Data..");
                bool_full_update = false;
                full_update_calc();
                limitstart = 0;
                datafull = false;
                Load_User();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Bind_data_listview() {
        try {
            ListView listView = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_User_S> result_query1 = mRealm.where(POJO_User_S.class).findAll().sort("modified", Sort.DESCENDING);
            List<POJO_User_S> mList = result_query1;
            // mList = result_query1;
                /*txtSearch = (AutoCompleteTextView) getView().findViewById(R.id.txt_search);
                txtSearch.setThreshold(1);*/
            adapter = new adapter_user_list_for_dialog(getContext(), R.layout.adapter_common_list_for_dialog, mList);
            // txtSearch.setAdapter(adapter);
            ListView lv = (ListView) getView().findViewById(R.id.listView);

            lv.setAdapter(adapter);
            if (mList.size() == 0) {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("NO TBM FOUND..");
            } else {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.GONE);
                txt_loading.setText("Refreshing Data..");
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public interface EditNameDialogListener {
        void onFinishEditDialog(String id, String fullname);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult(String id, String name) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditNameDialogListener listener = (EditNameDialogListener) getTargetFragment();
        listener.onFinishEditDialog(id, name);
        dismiss();

/*     UserNameListener listener=(UserNameListener)getTargetFragment();
        listener.onFinishUserDialog("hiii");*/

    }

    public void full_update_calc() {
        try {


            if ((bool_full_update == false)) {

                // if (mRealm.where(POJO_Patch_master.class).equalTo("active", 1).findAll().sort("modified", Sort.DESCENDING).first() != null) {
                RealmResults<POJO_User_S> result_query1 = mRealm.where(POJO_User_S.class).findAll().sort("modified", Sort.DESCENDING);
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
                    try {
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
                            for (POJO_User_S pp : POJO) {
                       /*   String name=pp.getName();
                          String bb=pp.getModified();
                          String cc=last_POJO.getName();
                          String dd=last_POJO.getModified();*/
                                if (last_POJO == null) {
                                    datafull = false;
                                } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                    datafull = true;

                                    Bind_data_listview();


                                }
                            }
                        }


                        if (datafull == false) {
                            Load_User();

                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void failure(RetrofitError error) {
                    try {

                        String msg = error.getMessage();

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


                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
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


}