package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
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

/**
 * Created by VIN-PC$ on 07/02/2017.
 */

public class Async_Class_Load_AppVersions_in_Realm extends AsyncTask<Void, Void, Void> {
    // private ProgressDialog dialog;
    private Realm mRealm;
    RestService restService;
    Context _context;
    int limitstart = 0;
    int pagesize = 3;
    boolean datafull = false;
    int sleep_wait = 0;
    public static String status_async_App_Versions = "loading"; //  success,error,loading

    boolean bool_loop_or_not = true;
    public static Integer App_Versions_count = 0; //  success,error,loading
    private ProgressDialog pDialog;
    public boolean Bool_Pdailog_show_or_not = false;
    public boolean bool_full_update = true;
    public POJO_AppVersions last_POJO;
    // private fragment_Patch_Master.FragmentCallback mFragmentCallback;

    public Async_Class_Load_AppVersions_in_Realm(Context c, boolean loop_or_not, boolean Pdailog_show_or_not, boolean full_update) {

        try {

            //  _context=activity.getApplicationContext();
            _context = c;
            //  dialog = new ProgressDialog(_context);
            bool_loop_or_not = loop_or_not;
            // dialog = new ProgressDialog(_context);
            Bool_Pdailog_show_or_not = Pdailog_show_or_not;
            bool_full_update = full_update;
        } catch (Exception ex) {
            Toast.makeText(c, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPreExecute() {
        try {
            limitstart = 0;
            sleep_wait = 0;
            pagesize = 5;
            datafull = false;
            status_async_App_Versions = "loading";
       /* SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putBoolean("App_Versions_Full_Data", false);
        editor.commit();*/
            pDialog = new ProgressDialog(_context);
            if (Bool_Pdailog_show_or_not == true) {
                pDialog.show();
            }
            full_update_calc();
        } catch (Exception ex) {
            status_async_App_Versions = "error";
        }
    }

    public void full_update_calc() {
        try {
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(_context);

            app_preferences.getBoolean("App_Versions_Full_Data", false);

            if ((bool_full_update == false)) {
                mRealm = Realm.getDefaultInstance();
                // if (mRealm.where(POJO_Patch_master.class).equalTo("active", 1).findAll().sort("modified", Sort.DESCENDING).first() != null) {
                //comment on 26/04/2017//RealmResults<POJO_AppVersions> result_query1 = mRealm.where(POJO_AppVersions.class).equalTo("active", 1).findAll().sort("modified", Sort.DESCENDING);
                RealmResults<POJO_AppVersions> result_query1 = mRealm.where(POJO_AppVersions.class).equalTo("supported", 1).findAll().sort("modified", Sort.DESCENDING);
                if (result_query1.size() > 0) {
                    last_POJO = result_query1.first();
                } else {
                    bool_full_update = true;
                }

            }
            if ((app_preferences.getBoolean("App_Versions_Full_Data", false) == false)) {
                bool_full_update = true;
            }
        } catch (Exception ex) {
            String exx = ex.getMessage();
            exx = ex.getMessage();
            Toast.makeText(_context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Load_AppVersions() {
        try {
            Thread.sleep(sleep_wait);
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(_context);
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("supported");
            jsonArray.put("modified_by");
            jsonArray.put("description");
            jsonArray.put("versioncode");
            jsonArray.put("versionname");
            jsonArray.put("docstatus");
            jsonArray.put("modified");


            Log.i("Success ", "out:" + limitstart);

       /*  restService.getService().getLIVE_WORK(sid,20,jsonArray,Filters, new Callback<JsonElement>() {
*/
            restService.getService().getAppVersions(sid, limitstart, pagesize, jsonArray, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {

                    // ListView lv = (ListView) findViewById(R.id.listView);
                    JsonObject j1 = jsonElement.getAsJsonObject();
                    JsonArray j2 = j1.getAsJsonArray("data");


                    //    List<LiveWork> LiveWorks=new ArrayList<LiveWork>();

                    // JSONArray jsonArray = new JSONArray(jsonArrayString);
                    //  List<String> list = new ArrayList<String>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<POJO_AppVersions>>() {
                    }.getType();
                    List<POJO_AppVersions> AppVersions = gson.fromJson(j2, type);
              /*  for (Class_Doctor_Master contact : LiveWorks){
                    // Log.i("Contact Details", contact.getname() + "-" + contact.getemployee() + "-" + contact.getwork_time());
                }*/
                    Log.i("Success ", "IN:" + limitstart + "size:" + AppVersions.size());
                    App_Versions_count = limitstart;
                    if (AppVersions.size() == 0) {
                        datafull = true;
                        // pDialog.dismiss();
                        status_async_App_Versions = "success"; //  success,error,loading

                    } else {
                        limitstart = limitstart + pagesize;
                        status_async_App_Versions = "loading"; //  success,error,loading
                    }


                    mRealm = Realm.getDefaultInstance();

                    mRealm.beginTransaction();

                    mRealm.copyToRealmOrUpdate(AppVersions);
                    mRealm.commitTransaction();


               /*  final RealmResults<Class_Doctor_Master> puppies = mRealm.where(POJO_Live_Work.class).findAll();
                 puppies.size();
                 Toast.makeText(_context, puppies.toString(), Toast.LENGTH_LONG).show();*/
                    mRealm.close();

                    if (bool_full_update == false) {
                        for (POJO_AppVersions pp : AppVersions) {
                            if (last_POJO == null) {
                                datafull = false;
                            } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                datafull = true;

                                status_async_App_Versions = "success"; //  success,error,loading
                            }
                        }
                    }

                    if (bool_loop_or_not == false) {
                        task_app_evrsion.cancel(true);
                        return;

                    }
                    if (datafull == false) {
                        Load_AppVersions();
                    }

                }


                @Override
                public void failure(RetrofitError error) {
                    status_async_App_Versions = "error"; //  success,error,loading
                    String msg = "";
                    if (error.getKind().toString().contains("HTTP")) {
                        msg = error.toString();
                    } else {
                        msg = "139.59.63.181";
                    }


                    if (msg.equals("403 FORBIDDEN")) {


                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(_context);    //status make 0 so checkoffline cannot work
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.putString("status", "0");
                        editor.commit();

                        Intent k = new Intent(_context, Login.class); //got ot login activity
                        _context.startActivity(k);
                    } else if (msg.contains("139.59.63.181")) {
                        Toast.makeText(_context, "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();

                    }

                    if (task_app_evrsion != null) {
                        task_app_evrsion.cancel(true);
                    }

                }
            });
        } catch (Exception e) {
            status_async_App_Versions = "error"; //  success,error,loading
        }
    }


    @Override
    protected void onPostExecute(Void result) {
       /* if (dialog.isShowing()) {
            dialog.dismiss();
        }*/
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            int i = 0;
            Load_AppVersions();

            while (i == 0) {
                if (status_async_App_Versions == "success") {

                   /* Handler handler =  new Handler(_context.getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            Toast.makeText(_context, "SUCCESS LIVE WORK DATA FETCHING",Toast.LENGTH_SHORT).show();
                        }
                    });*/
                    //Toast.makeText(getApplicationContext(),"SUCCESS LIVE WORK DATA FETCHING",Toast.LENGTH_SHORT);
                    SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(_context);
                    SharedPreferences.Editor editor = app_preferences.edit();
                    editor.putBoolean("App_Versions_Full_Data", true);
                    editor.commit();
                    i = 1;
                    App_Versions_count = 0;
                    pDialog.hide();
                    //    Load_AppVersions();


                    //Log.i("Contact Details", "appp");


                } else if (status_async_App_Versions == "error") {
                  /*  Handler handler =  new Handler(_context.getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            Toast.makeText(_context, "ERROR IN LIVE WORK DATA FETCHING",Toast.LENGTH_SHORT).show();
                        }
                    });*/
                    // Toast.makeText(_context,"ERROR IN LIVE WORK DATA FETCHING",Toast.LENGTH_SHORT);
                    limitstart = 0;
                    status_async_App_Versions = "loading";
                    datafull = false;
                    Load_AppVersions();
                    pDialog.hide();
                    Thread.sleep(10000);
                    App_Versions_count = 0;


                } else if (status_async_App_Versions == "loading") {

                   /* Handler handler =  new Handler(_context.getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            Toast.makeText(_context, "LOADING LIVE WORK DATA FETCHING",Toast.LENGTH_SHORT).show();
                        }
                    });*/
                    //Thread.sleep(20000);
                }
            }


            // Thread.sleep(1000);
            // Thread.sleep(1000);
        } catch (Exception e) {
            //e.printStackTrace();
            Toast.makeText(_context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        //  return null;
        return null;
    }
}


