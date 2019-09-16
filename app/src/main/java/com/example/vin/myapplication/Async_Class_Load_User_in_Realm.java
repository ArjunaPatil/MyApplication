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
import static com.example.vin.myapplication.DashBord_main.task_user;

/*import static com.example.vin.myapplication.DashBord_main.task2;
import static com.example.vin.myapplication.DashBord_main.task_Doctor_Master;
import static com.example.vin.myapplication.DashBord_main.task_Employee;
import static com.example.vin.myapplication.DashBord_main.task_Patch_Master;*/

/**
 * Created by VIN-PC$ on 07/02/2017.
 */

public class Async_Class_Load_User_in_Realm extends AsyncTask<Void, Void, Void> {
    private ProgressDialog dialog;
    private Realm mRealm;
    RestService restService;
    Context _context;
    int limitstart = 0;
    int pagesize = 20;
    boolean datafull = false;
    int sleep_wait = 0;
    boolean bool_loop_or_not = true;
    public static String status_async_user = "loading"; //  success,error,loading

    public static Integer User_master_count = 0; //  success,error,loading
    private ProgressDialog pDialog;
    public boolean Bool_Pdailog_show_or_not = false;
    public boolean bool_full_update = true;
    public POJO_User last_POJO;
    private fragment_User_Detail.FragmentCallback mFragmentCallback;

    public Async_Class_Load_User_in_Realm(fragment_User_Detail.FragmentCallback fragmentCallback, Context c, boolean loop_or_not, boolean Pdailog_show_or_not, boolean full_update) {
        try {
            //  _context=activity.getApplicationContext();
            _context = c;
            // dialog = new ProgressDialog(_context);
            bool_loop_or_not = loop_or_not;
            // dialog = new ProgressDialog(_context);
            Bool_Pdailog_show_or_not = Pdailog_show_or_not;
            if (fragmentCallback != null) {
                mFragmentCallback = fragmentCallback;
            }
            bool_full_update = full_update;
        } catch (Exception ex) {
            Toast.makeText(_context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPreExecute() {
        try {
       /* dialog.setMessage("Loading Live Works Please wait.");
        dialog.show();*/
            //Clear_Live_Work();
            limitstart = 0;
            sleep_wait = 0;
            pagesize = 10;
            datafull = false;
            status_async_user = "loading";
       /* SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(_context);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putBoolean("user_Full_Data", false);
        editor.commit();*/
            pDialog = new ProgressDialog(_context);
            if (Bool_Pdailog_show_or_not == true) {
                pDialog.show();
            }
            full_update_calc();
        } catch (Exception ex) {
            Toast.makeText(_context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void Clear_Live_Work() {
        try {
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_Employee.class);
            mRealm.commitTransaction();
            mRealm.close();
        } catch (Exception ex) {
            Toast.makeText(_context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void full_update_calc() {
        try {
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(_context);


            if ((bool_full_update == false)) {
                mRealm = Realm.getDefaultInstance();
                RealmResults<POJO_User> result_query1 = mRealm.where(POJO_User.class).findAll().sort("modified", Sort.DESCENDING);

                if (result_query1.size() > 0) {
                    last_POJO = result_query1.first();
                } else {
                    bool_full_update = true;
                }

            }
            if ((app_preferences.getBoolean("user_Full_Data", false) == false)) {
                bool_full_update = true;
            }
        } catch (Exception ex) {
            String exx = ex.getMessage();
            exx = ex.getMessage();
            Toast.makeText(_context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public void Load_User() {
        try {
/*
private String full_name;
    private String owner;
    private String last_known_versions;

    private Integer thread_notify;
    private String first_name;
    private String modified_by;
    private String background_style;
    private String district;
    private String area;
    private String employee_code;
    private String new_password;
    private String pan_no;
    private String rbm;
    private String designation;
    private String ifsc_code;
    private Integer idx;
    private String state;
    private String last_login;
    private Integer unsubscribed;
    private Integer lock_enable;

    private Integer docstatus;
    private String zone;
    private String type;
    private String email;
    private String bank_account_no;
    private String username;
    private String last_ip;
    private String sm;
    private String city;
    private String zbm;
    private String user_type;
    private String aadhar_card_no;

    private String address;
    private String pincode;
    private Integer login_after;
    private Integer send_welcome_email;
    private String doctype;
    private String name;
    private String language;
    private String gender;
    private String region;
    private Integer login_before;
    private Integer enabled;
    private String modified;
    private String mobile_no1;
    private String frappe_userid;
    private String mobile_no2;
    private String crm;

    private String user_image;
    private String last_name;
    private String last_active;
    private String bank_name;
    private String headquarter;
    private Integer simultaneous_sessions;
    private String abm;
    private String creation;
    private Integer send_password_update_notification;
    private Integer mute_sounds;
    private String middle_name;
    private String stockiest;
 */

            //Thread.sleep(sleep_wait);
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(_context);
            String sid = app_preferences.getString("sid", "default");
            String emp = app_preferences.getString("user", "default");
            JSONArray jsonArray = new JSONArray();

            jsonArray.put("full_name");
            jsonArray.put("owner");
            jsonArray.put("last_known_versions");
            jsonArray.put("thread_notify");
            jsonArray.put("first_name");
            jsonArray.put("modified_by");
            jsonArray.put("background_style");
            jsonArray.put("district");
            jsonArray.put("area");
            jsonArray.put("employee_code");
            jsonArray.put("new_password");
            jsonArray.put("pan_no");
            jsonArray.put("rbm");
            jsonArray.put("designation");
            jsonArray.put("ifsc_code");
            jsonArray.put("idx");
            jsonArray.put("state");
            jsonArray.put("last_login");
            jsonArray.put("unsubscribed");
            jsonArray.put("lock_enable");
            jsonArray.put("docstatus");
            jsonArray.put("zone");
            jsonArray.put("type");
            jsonArray.put("email");
            jsonArray.put("bank_account_no");
            jsonArray.put("username");
            jsonArray.put("last_ip");
            jsonArray.put("sm");
            jsonArray.put("city");
            jsonArray.put("zbm");
            jsonArray.put("user_type");
            jsonArray.put("aadhar_card_no");
            jsonArray.put("address");
            jsonArray.put("pincode");
            jsonArray.put("login_after");
            jsonArray.put("send_welcome_email");
            jsonArray.put("doctype");
            jsonArray.put("name");
            jsonArray.put("language");
            jsonArray.put("gender");
            jsonArray.put("region");
            jsonArray.put("login_before");
            jsonArray.put("enabled");
            jsonArray.put("modified");
            jsonArray.put("mobile_no1");
            jsonArray.put("frappe_userid");
            jsonArray.put("mobile_no2");
            jsonArray.put("crm");
            jsonArray.put("user_image");
            jsonArray.put("last_name");
            jsonArray.put("last_active");
            jsonArray.put("bank_name");
            jsonArray.put("headquarter");
            jsonArray.put("simultaneous_sessions");
            jsonArray.put("abm");
            jsonArray.put("creation");
            jsonArray.put("send_password_update_notification");
            jsonArray.put("mute_sounds");
            jsonArray.put("middle_name");
            jsonArray.put("stockiest");

            //  JSONObject ofilter= new JSONObject();
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();
            JSONArray Filter3 = new JSONArray();


            Filter1.put("User");
            Filter1.put("enabled");
            Filter1.put("=");
            Filter1.put(1);

        /* Filter2.put("Live Work");
         Filter2.put("select_date");
         Filter2.put("=");
         Filter2.put(fromDateEtxt.getText());*/

        /*Filter3.put("Live Work");
        Filter3.put("work_type");
        Filter3.put("=");
        Filter3.put("Doctor Call");
*/
     /*   Filter1.put("Live Work");
        Filter1.put("employee");
        Filter1.put("=");
        Filter1.put(emp);*/


            //  ofilter.
            //  Filters.put(Filter1);
            // Filters.put(Filter3);
            // Log.i("Success ","out:"+limitstart);

       /*  restService.getService().getLIVE_WORK(sid,20,jsonArray,Filters, new Callback<JsonElement>() {*/
            restService.getService().getUser(sid, limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {

                    // ListView lv = (ListView) findViewById(R.id.listView);
                    JsonObject j1 = jsonElement.getAsJsonObject();
                    JsonArray j2 = j1.getAsJsonArray("data");


                    //    List<LiveWork> LiveWorks=new ArrayList<LiveWork>();

                    // JSONArray jsonArray = new JSONArray(jsonArrayString);
                    //  List<String> list = new ArrayList<String>();
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<POJO_User>>() {
                    }.getType();
                    List<POJO_User> POJO = gson.fromJson(j2, type);
              /*  for (Class_Doctor_Master contact : LiveWorks){
                    // Log.i("Contact Details", contact.getname() + "-" + contact.getemployee() + "-" + contact.getwork_time());
                }*/
                    Log.i("Success ", "IN:" + limitstart + "size:" + POJO.size());
                    User_master_count = limitstart;
                    if (POJO.size() == 0) {
                        datafull = true;
                        // pDialog.dismiss();
                        status_async_user = "success"; //  success,error,loading

                    } else {
                        limitstart = limitstart + pagesize;
                        status_async_user = "loading"; //  success,error,loading
                    }


                    mRealm = Realm.getDefaultInstance();

                    mRealm.beginTransaction();

                    mRealm.copyToRealmOrUpdate(POJO);
                    mRealm.commitTransaction();


               /*  final RealmResults<Class_Doctor_Master> puppies = mRealm.where(POJO_Employee.class).findAll();
                 puppies.size();
                 Toast.makeText(_context, puppies.toString(), Toast.LENGTH_LONG).show();*/
                    mRealm.close();

                    if (bool_full_update == false) {
                        for (POJO_User pp : POJO) {
                       /*   String name=pp.getName();
                          String bb=pp.getModified();
                          String cc=last_POJO.getName();
                          String dd=last_POJO.getModified();*/
                            if (last_POJO == null) {
                                datafull = false;
                            } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                datafull = true;
                                // pDialog.dismiss();
                                status_async_user = "success"; //  success,error,loading
                            }
                        }
                    }

                    if (bool_loop_or_not == false) {
                        /*task_Employee.cancel(true);*/
                        return;

                    }
                    if (datafull == false) {
                        Load_User();
                    }

                }

                @Override
                public void failure(RetrofitError error) {
                    status_async_user = "error"; //  success,error,loading
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

                    if (task_user != null) {
                        task_user.cancel(true);
                    }
                }

            });
        } catch (Exception e) {
            status_async_user = "error"; //  success,error,loading
            Toast.makeText(_context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void Remove_Deleted_from_realm() {
       /* mRealm = Realm.getDefaultInstance();
        RealmResults<POJO_Employee> all_live_work = mRealm.where(POJO_Employee.class).findAll();


       // List<POJO_Employee> LiveWorks = puppies;
        for (POJO_Employee contact : all_live_work) {
            Log.i("Contact Details", contact.getName() + "-" + contact.getEmployee() + "-" + contact.getWork_time());
        }
        mRealm.close();*/

    }

    @Override
    protected void onPostExecute(Void result) {
        try {
            if (mFragmentCallback != null) {
                mFragmentCallback.onTaskDone();
            }
        } catch (Exception ex) {
            Toast.makeText(_context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            int i = 0;
            Load_User();

            while (i == 0) {
                if (status_async_user == "success") {

                   /* Handler handler =  new Handler(_context.getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            Toast.makeText(_context, "SUCCESS LIVE WORK DATA FETCHING",Toast.LENGTH_SHORT).show();
                        }
                    });*/
                    //Toast.makeText(getApplicationContext(),"SUCCESS LIVE WORK DATA FETCHING",Toast.LENGTH_SHORT);
                   /* Thread.sleep(10000);
                    limitstart = 0;
                    status_async_user="loading";
                    datafull = false;
                    sleep_wait=0;
                    pagesize=10;*/
                    SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(_context);
                    SharedPreferences.Editor editor = app_preferences.edit();
                    editor.putBoolean("user_Full_Data", true);
                    editor.commit();
                    i = 1;
                    User_master_count = 0;
                    pDialog.hide();
                    // Load_User();;


                    //Log.i("Contact Details", "appp");

                    // Remove_Deleted_from_realm();
                } else if (status_async_user == "error") {
                   /* Handler handler =  new Handler(_context.getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            Toast.makeText(_context, "ERROR IN LIVE WORK DATA FETCHING",Toast.LENGTH_SHORT).show();
                        }
                    });*/
                    // Toast.makeText(_context,"ERROR IN LIVE WORK DATA FETCHING",Toast.LENGTH_SHORT);
                    limitstart = 0;
                    status_async_user = "loading";
                    datafull = false;
                    Load_User();
                    pDialog.hide();
                    Thread.sleep(10000);
                    User_master_count = 0;

                } else if (status_async_user == "loading") {

                   /* Handler handler =  new Handler(_context.getMainLooper());
                    handler.post( new Runnable(){
                        public void run(){
                            Toast.makeText(_context, "LOADING LIVE WORK DATA FETCHING",Toast.LENGTH_SHORT).show();
                        }
                    });*/
                    // Thread.sleep(500);

                }
            }


            // Thread.sleep(1000);
            // Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(_context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        //  return null;

        return null;
    }
}
