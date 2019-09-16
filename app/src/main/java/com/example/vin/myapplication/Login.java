package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;


public class Login extends AppCompatActivity {
    RestService restService;
    private Realm mRealm;
    EditText email;
    EditText password;
    Button btn_login;
    EditText edit_som;
    private ProgressDialog pDialog;
    // private ProgressDialog pDialog;
    private static final String TAG = "MyActivity";
    CookieManager manager = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
    Class_load_server_data load_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login2);

            CookieHandler.setDefault(manager);

      /*  SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString("email","null");
        editor.putString("password","null");
        editor.commit();*/


            // Intent intent = new Intent(this, HelloService.class);
            //startService(intent);
            pDialog = new ProgressDialog(this);
            Realm.init(this);
            RealmConfiguration config = new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build();
            Realm.setDefaultConfiguration(config);
            btn_login = (Button) findViewById(R.id.btn_login);

            //Realm.getDefaultInstance();
            email = (EditText) findViewById(R.id.txt_username);
            password = (EditText) findViewById(R.id.txt_password);
            // CheckOffline();

            // if( true== true)
            if (CheckOffline() == true) {
                // Toast.makeText(this, "Login Success / Offline Mode", Toast.LENGTH_LONG).show();

                btntoast("Login Succesfully..");

                //check_branch();

                //afterLogin(true);
                Intent k = new Intent(Login.this, DashBord_main.class);
                startActivity(k);
            } else {

                SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String txtemailid = app_preferences.getString("email", "");
                String txtpassword = app_preferences.getString("password", "");
                email.setText(txtemailid);
                password.setText(txtpassword);
                if ((txtemailid.toString().length() > 2) && (txtpassword.toString().length() > 2)) {
                    btn_login = (Button) findViewById(R.id.btn_login);
                    email.setEnabled(false);
                    password.setEnabled(false);
                    btn_login.setEnabled(false);
                    Login_Fun();
                } else {
                    btn_login = (Button) findViewById(R.id.btn_login);
                    email.setEnabled(true);
                    password.setEnabled(true);
                    btn_login.setEnabled(true);
                }


                //Login_Fun_volly();
            }


            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                /*Intent k = new Intent(Login.this,DashBoard.class);
                startActivity(k);*/
                    Context context = getApplicationContext();
                    SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
                    String old_email = app_preferences.getString("email", "Default");
                    SharedPreferences.Editor editor = app_preferences.edit();
                    editor.putString("email", email.getText().toString());
                    editor.putString("password", password.getText().toString());

                    editor.commit();
                    //checking classes from realm is Updated(true), If not then set it to false// if problem in loading then set error
                    //values are true,false,error


                    /*  vin old app code
                    if (app_preferences.getString("AppVesions", "default") != "true") {
                        editor.putString("AppVesions", "false");
                    }
                    if (app_preferences.getString("Live_Work", "default") != "true") {
                        editor.putString("Live_Work", "false");
                    }
                    if (app_preferences.getString("AppVesions", "default") != "true") {
                        editor.putString("AppVesions", "false");
                    }
                    if (app_preferences.getString("Doctor_Master", "default") != "true") {
                        editor.putString("Doctor_Master", "false");
                    }
                    if (app_preferences.getString("Employee_Master", "default") != "true") {
                        editor.putString("Employee_Master", "false");
                    }

                    if (old_email != email.getText().toString()) {
                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.deleteAll();
                        mRealm.commitTransaction();
                        mRealm.close();

                        editor.putBoolean("App_Versions_Full_Data", false);
                        editor.putBoolean("doctor_master_Full_Data", false);
                        editor.putBoolean("employee_Full_Data", false);
                        editor.putBoolean("live_work_Full_Data", false);
                        editor.putBoolean("patch_master_Full_Data", false);


                    }


                    editor.commit();
                   vin old app code*/

                    Login_Fun();
                    //Login_Fun_volly();
                }
            });
        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.i("Error is:", ex.getMessage().toString());
        }
    }

    public void Login_Fun() {
        try {
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
            final String txtemailid = app_preferences.getString("email", "default");
            String txtpassword = app_preferences.getString("password", "default");

            pDialog.show();

            restService = new RestService();

            restService.getService().login(txtemailid, txtpassword, new Callback<JsonElement>() {
                Context context = getApplicationContext();

                @Override
                public void success(JsonElement result, Response arg1) {
                    pDialog.hide();
                    System.out.println("success, result: " + result + "/" + arg1);
                    if (arg1.getStatus() == 200) {
                        //pDialog.dismiss();

                  /*  CharSequence text = "Login Succesfully";

                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();*/

                        btntoast("Login Succesfully");

                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
                        String strDate = mdformat.format(calendar.getTime());

                        String sid = "";
                        String user_id = "";
                        for (Header hhh : arg1.getHeaders()) {
                            if (hhh.getValue().contains("user_id=")) {
                                user_id = hhh.getValue();
                            }
                            if (hhh.getValue().contains("sid=")) {
                                sid = hhh.getValue();
                            }

                        }
                        /*String sid = arg1.getHeaders()
                        String user_id = arg1.getHeaders().get(7).getValue();*/
                        String fullname = result.getAsJsonObject().get("full_name").getAsString();


                  /*  List<retrofit.client.Header> headerList = arg1.getHeaders();
                    for(retrofit.client.Header header : headerList) {
                       // Log.d("hiiiii", header.getName() + " " + header.getValue());
                        if(header.getName())
                    }*/
                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.putString("status", "1");
                        editor.putString("sid", sid);
                        editor.putString("Last_login", strDate);
                        editor.putString("full_name", fullname);
                        editor.putString("user_id", user_id);
                        editor.commit();

                        afterLogin(true);


                    } else {
                   /* CharSequence text = "Login Failed";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();*/

                        btntoast("Login Failure Please Try Again");

                        Context context = getApplicationContext();
                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.putString("status", "0");
                        editor.commit();
                        afterLogin(false);
                    }


                }

                @Override
                public void failure(RetrofitError error) {
                    // System.out.println("failure, error: " + error);
                    pDialog.hide();
                    String msg = "";
                    if (error.getKind().toString().contains("HTTP")) {
                        msg = error.toString();
                    } else {
                        msg = "139.59.63.181";
                    }


                    if (msg.equals("403 FORBIDDEN")) {


                        btntoast("LOGIN FAIL..PLEASE CHECK EMAILID AND PASSWORD");
                    } else if (msg.contains("139.59.63.181")) {
                        btntoast("PLEASE CHECK INTERNET CONNECTIVITY");

                    } else if (msg.contains("401 UNAUTHORIZED")) {
                        btntoast("PLEASE CHECK EMAIL-ID AND PASSWORD");
                        email.setEnabled(true);
                        password.setEnabled(true);
                        btn_login.setEnabled(true);

                    }


                    // toast.show();
                }
            });
        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.i("Error is:", ex.getMessage().toString());
        }
    }


    public boolean CheckOffline() {
        try {
            boolean ret_flag = true;
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String status = app_preferences.getString("status", "default");
            String last_login_yyyymmdd = app_preferences.getString("Last_login", "2016-01-01");
            //last_login_yyyymmdd="2016-01-17";

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date dd = mdformat.parse(last_login_yyyymmdd, pos);
            Date today = new Date();

            long pdate = dd.getTime();
            long cdate = today.getTime();

            long diff = cdate - pdate;  //in Milli seconds
            long numOfDays = diff / (1000 * 60 * 60 * 24);
            //format.parse(last_login_yyyymmdd);
            Context context = getApplicationContext();
            if (numOfDays > 1) {
                ret_flag = false;
                // SharedPreferences app_preferences1 = PreferenceManager.getDefaultSharedPreferences(context);
           /* SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("email", "");
            editor.putString("password", "");
            editor.commit();*/
            }

            if (status.equals("0")) {
                ret_flag = false;
                // SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
         /*   SharedPreferences.Editor editor = app_preferences.edit();
            editor.putString("email", "");
            editor.putString("password", "");
            editor.commit();*/


            }
            if (ret_flag == false) {

                // Toast.makeText(context, "Login Failure Last Login On" + last_login_yyyymmdd.toString(), Toast.LENGTH_LONG).show();
                // btntoast( "Login Failure Last Login On" + last_login_yyyymmdd.toString());
                //btntoast( "Session Exipred..Please Login");
                btntoast("Session Exipred..Auto Re-Login Processing..Please Wait");
            }

            return ret_flag;
        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.i("Error is:", ex.getMessage().toString());
            return false;
        }
    }

    /*public void afterLogin(boolean login_status) {
        try {
            if (login_status == true) {


                SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);




                Intent k = new Intent(Login.this, DashBord_main.class);
                startActivity(k);


            }
        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.i("Error is:", ex.getMessage().toString());
        }
    }*/

    public void afterLogin(boolean login_status) {
        try {
            if (login_status == true) {

                SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
                final String txtemailid = app_preferences.getString("email", "default");


                String sid = app_preferences.getString("sid", "default");
                // txtemailid = "nitinjadhav555@gmail.com";
        /*   ArrayList<String> fields=new ArrayList<String>();

           fields.add("employee");
           fields.add("employee_name");
           fields.add("personal_email");
           fields.add("designation");
           fields.add("date_of_birth");
           fields.add("marital_status");
           fields.add("cell_number");
           fields.add("emergency_phone_number");

           fields.add("state");
           fields.add("district");
           fields.add("taluka");
           fields.add("city");
           fields.add("current_address");

           fields.add("bank_name");
           fields.add("bank_ac_no");
           fields.add("ifsccode");
           fields.add("panno");

           fields.add("zone_name");
           fields.add("region_name");
           fields.add("area_name");
           fields.add("headquarter");

           fields.add("status");*/

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
                jsonArray.put("rbm_name");
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
                jsonArray.put("sm_name");
                jsonArray.put("city");
                jsonArray.put("zbm");
                jsonArray.put("zbm_name");
                jsonArray.put("nbm");
                jsonArray.put("nbm_name");

                jsonArray.put("user_type");
                jsonArray.put("aadhar_card_no");
                jsonArray.put("address");
                jsonArray.put("pincode");
                jsonArray.put("login_after");

                jsonArray.put("send_welcome_email");
                /*jsonArray.put("doctype");*/
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
                jsonArray.put("crm_name");
                jsonArray.put("user_image");
                jsonArray.put("last_name");
                jsonArray.put("last_active");
                jsonArray.put("bank_name");
                jsonArray.put("headquarter");

                jsonArray.put("simultaneous_sessions");
                jsonArray.put("abm");
                jsonArray.put("abm_name");
                jsonArray.put("creation");
                jsonArray.put("send_password_update_notification");
                jsonArray.put("mute_sounds");
                jsonArray.put("middle_name");

                jsonArray.put("zone_name");
                jsonArray.put("region_name");
                jsonArray.put("area_name");
                jsonArray.put("headquarter_name");
                jsonArray.put("birth_date");
                jsonArray.put("branch");
                //jsonArray.put("stockiest");


                //  JSONObject ofilter= new JSONObject();
                JSONArray Filters = new JSONArray();
                JSONArray Filter1 = new JSONArray();
                JSONArray Filter2 = new JSONArray();

                Filter1.put("User");
                Filter1.put("name");
                Filter1.put("=");
                Filter1.put(txtemailid);
                //Filter1.put("riteshdiwan8@gmail.com");

                Filter2.put("User");
                Filter2.put("enabled");
                Filter2.put("=");
                Filter2.put(1);

                Filters.put(Filter1);
                Filters.put(Filter2);


                restService = new RestService();

                restService.getService().userdata(sid, Filters, jsonArray, new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        try {
                            System.out.println("success, result: " + jsonElement + "/" + response);

                            if (response.getStatus() == 200) {

                                JsonObject j1 = jsonElement.getAsJsonObject();
                                JsonArray j2 = j1.getAsJsonArray("data");
                                if (j2.size() == 0 && (txtemailid.toString().equals("Administrator"))) {
                                    btntoast("Your USER-EMPLOYEE Assign is Pending ..Please Contact LYSTEN GLOBAL -IT DEPT ");
                                    SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = app_preferences.edit();

                                    editor.putString("name", "");//id in User table
                                    editor.putString("employee_code", "");

                                    editor.putString("first_name", "");
                                    editor.putString("middle_name", "");
                                    editor.putString("last_name", "");

                                    editor.putString("email", "");
                                    editor.putString("designation", "");
                                    editor.putString("mobile_no1", "");

                                    editor.putString("state", "");
                                    editor.putString("district", "");
                                    // editor.putString("taluka", "");
                                    editor.putString("city", "");
                                    editor.putString("address", "");
                                    editor.putString("pincode", "");

                                    editor.putString("bank_name", "");
                                    editor.putString("ifsc_code", "");
                                    editor.putString("bank_account_no", "");
                                    editor.putString("aadhar_card_no", "");
                                    editor.putString("pan_no", "");

                                    editor.putString("zone", "");
                                    editor.putString("region", "");
                                    editor.putString("area", "");
                                    editor.putString("headquarter", "");

                                    editor.putString("zone_name", "");
                                    editor.putString("region_name", "");
                                    editor.putString("area_name", "");
                                    editor.putString("headquarter_name", "");

                                    editor.putString("nbm", "");//id
                                    editor.putString("sm", "");//id
                                    editor.putString("zbm", "");
                                    editor.putString("rbm", "");
                                    editor.putString("abm", "");
                                    editor.putString("crm", "");

                                    editor.putString("nbm_name", "");//name
                                    editor.putString("sm_name", "");//name
                                    editor.putString("zbm_name", "");
                                    editor.putString("rbm_name", "");
                                    editor.putString("abm_name", "");
                                    editor.putString("crm_name", "");
                                    editor.putString("birth_date", "");

                                    editor.putString("plan_date", "");//arjun added for plan date pass for back key pressed
                                    editor.putString("branch", "");//arjun added on 24-04-2018
                                    editor.putString("branch_product", "");
                                    editor.commit();

                                    return;
                                } else {

                                    JsonObject j3 = j2.get(0).getAsJsonObject();

                                    SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = app_preferences.edit();

                                    if (j3.get("name").isJsonNull() == true) {
                                        editor.putString("name", "");
                                    } else {
                                        editor.putString("name", j3.get("name").getAsString());
                                    }

                                    if (j3.get("employee_code").isJsonNull() == true) {
                                        editor.putString("employee_code", "");
                                    } else {
                                        editor.putString("employee_code", j3.get("employee_code").getAsString());
                                    }

                                    if (j3.get("first_name").isJsonNull() == true) {
                                        editor.putString("first_name", "");
                                    } else {
                                        editor.putString("first_name", j3.get("first_name").getAsString());
                                    }

                                    if (j3.get("middle_name").isJsonNull() == true) {
                                        editor.putString("middle_name", "");
                                    } else {
                                        editor.putString("middle_name", j3.get("middle_name").getAsString());
                                    }

                                    if (j3.get("last_name").isJsonNull() == true) {
                                        editor.putString("last_name", "");
                                    } else {
                                        editor.putString("last_name", j3.get("last_name").getAsString());
                                    }

                                    if (j3.get("email").isJsonNull() == true) {
                                        editor.putString("email", "");
                                    } else {
                                        editor.putString("email", j3.get("email").getAsString());
                                    }

                                    if (j3.get("designation").isJsonNull() == true) {
                                        editor.putString("designation", "");
                                    } else {
                                        editor.putString("designation", j3.get("designation").getAsString());
                                    }

                                    if (j3.get("mobile_no1").isJsonNull() == true) {
                                        editor.putString("mobile_no1", "");
                                    } else {
                                        editor.putString("mobile_no1", j3.get("mobile_no1").getAsString());
                                    }
                                    // editor.putString("emergency_phone_number",j3.get("emergency_phone_number").getAsString());


                                    if (j3.get("state").isJsonNull() == true) {
                                        editor.putString("state", "");
                                    } else {
                                        editor.putString("state", j3.get("state").getAsString());
                                    }

                                    if (j3.get("district").isJsonNull() == true) {
                                        editor.putString("district", "");
                                    } else {
                                        editor.putString("district", j3.get("district").getAsString());
                                    }

                                /*if (j3.get("taluka").isJsonNull() == true) {
                                    editor.putString("taluka", "");
                                } else {
                                    editor.putString("taluka", j3.get("taluka").getAsString());
                                }*/

                                    if (j3.get("city").isJsonNull() == true) {
                                        editor.putString("city", "");
                                    } else {
                                        editor.putString("city", j3.get("city").getAsString());
                                    }

                                    if (j3.get("address").isJsonNull() == true) {
                                        editor.putString("address", "");
                                    } else {
                                        editor.putString("address", j3.get("address").getAsString());
                                    }

                                    if (j3.get("pincode").isJsonNull() == true) {
                                        editor.putString("pincode", "");
                                    } else {
                                        editor.putString("pincode", j3.get("pincode").getAsString());
                                    }

                                    if (j3.get("bank_name").isJsonNull() == true) {
                                        editor.putString("bank_name", "");
                                    } else {
                                        editor.putString("bank_name", j3.get("bank_name").getAsString());
                                    }

                                    if (j3.get("bank_account_no").isJsonNull() == true) {
                                        editor.putString("bank_account_no", "");
                                    } else {
                                        editor.putString("bank_account_no", j3.get("bank_account_no").getAsString());
                                    }

                                    if (j3.get("ifsc_code").isJsonNull() == true) {
                                        editor.putString("ifsc_code", "");
                                    } else {
                                        editor.putString("ifsc_code", j3.get("ifsc_code").getAsString());
                                    }

                                    if (j3.get("pan_no").isJsonNull() == true) {
                                        editor.putString("pan_no", "");
                                    } else {
                                        editor.putString("pan_no", j3.get("pan_no").getAsString());
                                    }

                                    if (j3.get("aadhar_card_no").isJsonNull() == true) {
                                        editor.putString("aadhar_card_no", "");
                                    } else {
                                        editor.putString("aadhar_card_no", j3.get("aadhar_card_no").getAsString());
                                    }

                                    if (j3.get("zone").isJsonNull() == true) {
                                        editor.putString("zone", "");
                                    } else {
                                        editor.putString("zone", j3.get("zone").getAsString());
                                    }

                                    if (j3.get("zone_name").isJsonNull() == true) {
                                        editor.putString("zone_name", "");
                                    } else {
                                        editor.putString("zone_name", j3.get("zone_name").getAsString());
                                    }


                                    if (j3.get("region").isJsonNull() == true) {
                                        editor.putString("region", "");
                                    } else {
                                        editor.putString("region", j3.get("region").getAsString());
                                    }

                                    if (j3.get("region_name").isJsonNull() == true) {
                                        editor.putString("region_name", "");
                                    } else {
                                        editor.putString("region_name", j3.get("region_name").getAsString());
                                    }

                                    if (j3.get("area").isJsonNull() == true) {
                                        editor.putString("area", "");
                                    } else {
                                        editor.putString("area", j3.get("area").getAsString());
                                    }

                                    if (j3.get("area_name").isJsonNull() == true) {
                                        editor.putString("area_name", "");
                                    } else {
                                        editor.putString("area_name", j3.get("area_name").getAsString());
                                    }

                                    if (j3.get("headquarter").isJsonNull() == true) {
                                        editor.putString("headquarter", "");
                                    } else {
                                        editor.putString("headquarter", j3.get("headquarter").getAsString());
                                    }

                                    if (j3.get("headquarter_name").isJsonNull() == true) {
                                        editor.putString("headquarter_name", "");
                                    } else {
                                        editor.putString("headquarter_name", j3.get("headquarter_name").getAsString());
                                    }

                                    if (j3.get("nbm").isJsonNull() == true) {
                                        editor.putString("nbm", "");
                                    } else {
                                        editor.putString("nbm", j3.get("nbm").getAsString());
                                    }
                                    if (j3.get("nbm_name").isJsonNull() == true) {
                                        editor.putString("nbm_name", "");
                                    } else {
                                        editor.putString("nbm_name", j3.get("nbm_name").getAsString());
                                    }

                                    if (j3.get("sm").isJsonNull() == true) {
                                        editor.putString("sm", "");
                                    } else {
                                        editor.putString("sm", j3.get("sm").getAsString());
                                    }
                                    if (j3.get("sm_name").isJsonNull() == true) {
                                        editor.putString("sm_name", "");
                                    } else {
                                        editor.putString("sm_name", j3.get("sm_name").getAsString());
                                    }
                                    if (j3.get("zbm").isJsonNull() == true) {
                                        editor.putString("zbm", "");
                                    } else {
                                        editor.putString("zbm", j3.get("zbm").getAsString());
                                    }
                                    if (j3.get("zbm_name").isJsonNull() == true) {
                                        editor.putString("zbm_name", "");
                                    } else {
                                        editor.putString("zbm_name", j3.get("zbm_name").getAsString());
                                    }
                                    if (j3.get("crm").isJsonNull() == true) {
                                        editor.putString("crm", "");
                                    } else {
                                        editor.putString("crm", j3.get("crm").getAsString());
                                    }
                                    if (j3.get("crm_name").isJsonNull() == true) {
                                        editor.putString("crm_name", "");
                                    } else {
                                        editor.putString("crm_name", j3.get("crm_name").getAsString());
                                    }
                                    if (j3.get("rbm").isJsonNull() == true) {
                                        editor.putString("rbm", "");
                                    } else {
                                        editor.putString("rbm", j3.get("rbm").getAsString());
                                    }
                                    if (j3.get("rbm_name").isJsonNull() == true) {
                                        editor.putString("rbm_name", "");
                                    } else {
                                        editor.putString("rbm_name", j3.get("rbm_name").getAsString());
                                    }
                                    if (j3.get("abm").isJsonNull() == true) {
                                        editor.putString("abm", "");
                                    } else {
                                        editor.putString("abm", j3.get("abm").getAsString());
                                    }
                                    if (j3.get("abm_name").isJsonNull() == true) {
                                        editor.putString("abm_name", "");
                                    } else {
                                        editor.putString("abm_name", j3.get("abm_name").getAsString());
                                    }

                                    if (j3.get("birth_date").isJsonNull() == true) {
                                        editor.putString("birth_date", "");
                                    } else {
                                        editor.putString("birth_date", j3.get("birth_date").getAsString());
                                    }

                                    if (j3.get("branch").isJsonNull() == true) {
                                        editor.putString("branch", "");
                                    } else {
                                        editor.putString("branch", j3.get("branch").getAsString());
                                    }
                                    editor.putString("branch_product", "");
                                    editor.commit();
                                }

                                //check_branch();

                                Intent k = new Intent(Login.this, DashBord_main.class);
                                startActivity(k);
                            } else {

                            }
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError retrofvitError) {
                        //Toast toast = Toast.makeText(getApplicationContext(), "Login Failure Please Try Again", Toast.LENGTH_LONG);
                        btntoast("Login Failure Please Try Again");
                    }
                });

            } else {

            }
        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.i("Error is:", ex.getMessage().toString());
        }
    }

    public void btntoast(String message) {
        try {
            Context context = getApplicationContext();
            LayoutInflater inflater = getLayoutInflater();

            View customToastroot = inflater.inflate(R.layout.mycustom_toast_white, null);
            TextView txtmsg = (TextView) customToastroot.findViewById(R.id.textView2);
            txtmsg.setText(message);
            Toast customtoast = new Toast(context);

            customtoast.setView(customToastroot);
            customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
            customtoast.setDuration(Toast.LENGTH_SHORT);
            customtoast.show();
        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            Log.i("Error is:", ex.getMessage().toString());
        }
    }

    /*private void check_branch() {
        try {
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String employee_code = app_preferences.getString("employee_code", "-");
            String designation = app_preferences.getString("designation", "-");
            String branch = app_preferences.getString("branch", "-");
            if (employee_code.equals("-") && (designation.equals("TBM") || designation.equals("ABM") || designation.equals("RBM") || designation.equals("CRM") || designation.equals("ZBM") || designation.equals("SM") || designation.equals("NBM")) && branch.equals("-")) {
                btntoast("IF Employee Code,Branch is Empty or Null in Profile Then Contact With OFFICE.");
            }
        } catch (Exception ex) {
            Log.i("Error is:", ex.getMessage().toString());
        }
    }*/

}
