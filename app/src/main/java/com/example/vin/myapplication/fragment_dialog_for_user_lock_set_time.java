package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
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

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.MoviesAdapter.context;

public class fragment_dialog_for_user_lock_set_time extends android.support.v4.app.DialogFragment
        implements fragment_dialog_for_user_lock_set_time_hour.EditLockTimeHourDialogListener,
        fragment_dialog_for_user_lock_set_time_minutes.EditLockTimeMinutesDialogListener,
        fragment_dialog_for_user_lock_set_time_am_pm.EditLockTimeAmPmDialogListener {

    RestService restService;
    Bundle bundle;
    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    public boolean bool_full_update = false;
    private long mLastClickTime = 0;

    private ProgressDialog pDialog;
    Button btn_edit;
    Button btn_back;

    TextView txt_username;

    Switch switch_trans_obj_time;
    Switch switch_trans_doc_time;
    Switch switch_trans_che_time;

    LinearLayout obj_hh_time1;
    TextView obj_hh_time2;
    TextView obj_hh_time_name;
    ImageButton obj_hh_time3;

    LinearLayout obj_min_time1;
    TextView obj_min_time2;
    TextView obj_min_time_name;
    ImageButton obj_min_time3;

    LinearLayout obj_am_pm1;
    TextView obj_am_pm2;
    TextView obj_am_pm_name;
    ImageButton obj_am_pm3;

    Integer trans_obj = 0;
    Integer trans_doc = 0;
    Integer trans_chem = 0;
    Integer active_flag = 0;

    String am_pm = "";
    Integer hour = 0, minute = 0;

    String msg = "";

    public POJO_Doctor_Master last_POJO;


    // Empty constructor required for DialogFragment
    public fragment_dialog_for_user_lock_set_time() {
    }

    public static fragment_dialog_for_user_lock_set_time newInstance(String title) {
        try {
            fragment_dialog_for_user_lock_set_time frag = new fragment_dialog_for_user_lock_set_time();
            Bundle args = new Bundle();
            args.putString("title", title);
            frag.setArguments(args);
            return frag;
        } catch (Exception ex) {
            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().setCanceledOnTouchOutside(true);
            View view = inflater.inflate(R.layout.fragment_dialog_for_user_lock_set_time, container);

            return view;
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }

    @Override
    public void onResume() {
        try {
            super.onResume();

            /* Screen LANDSCAPE and Portait*/
            Window window = getDialog().getWindow();
            Point size = new Point();

            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(size);

            int width = size.x;

            window.setLayout((int) (width * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);//75
            window.setGravity(Gravity.CENTER);

        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onFinishLockTimeHourDialog(String id, String hour) {
        try {

            obj_hh_time2 = (TextView) getView().findViewById(R.id.obj_hh_time2);
            obj_hh_time_name = (TextView) getView().findViewById(R.id.obj_hh_time_name);

            obj_hh_time2.setText(hour.toString());
            obj_hh_time_name.setText(id.toString());

            /*if (id == "ALL") {
                name_objective_of_employee = (TextView) getView().findViewById(name_objective_of_employee);
                name_objective_of_employee.setText(my_id);

                select_employee2 = (TextView) getView().findViewById(select_employee2);
                select_employee2.setText("MY DR. CALL");
            } else {
                select_employee2 = (TextView) getView().findViewById(select_employee2);
                select_employee2.setText(fullname.toString());

                name_objective_of_employee = (TextView) getView().findViewById(name_objective_of_employee);
                name_objective_of_employee.setText(id.toString());
            }*/

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishLockTimeMinutesDialog(String id, String minutes) {
        try {

            obj_min_time2 = (TextView) getView().findViewById(R.id.obj_min_time2);
            obj_min_time_name = (TextView) getView().findViewById(R.id.obj_min_time_name);

            obj_min_time2.setText(minutes.toString());
            obj_min_time_name.setText(id.toString());

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFinishLockTimeAmPmDialog(String id, String am_pm) {
        try {

            obj_am_pm2 = (TextView) getView().findViewById(R.id.obj_am_pm2);
            obj_am_pm_name = (TextView) getView().findViewById(R.id.obj_am_pm_name);

            obj_am_pm2.setText(am_pm.toString());
            obj_am_pm_name.setText(id.toString());

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        try {
            init_controls();
            bundle = this.getArguments();
            if (bundle != null) {

                //final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                //String user = app_preferences.getString("name", "default");

                /*if (!bundle.getString("user").toString().equals("")) {
                    txt_username.setText(bundle.getString("user").toString());
                }*/

                pDialog = new ProgressDialog(getContext());
                //if (user.equals(bundle.getString("user").toString())) {
                btn_edit.setVisibility(View.VISIBLE);
                //} else {
                //    btn_edit.setVisibility(View.GONE);
                //}

                //load_user_data(bundle.getString("user"));
                //load_user_trans_form_lock_time(bundle.getString("user"));
                load_user_trans_form_lock_time(bundle.getString("user"));
                load_user_data(bundle.getString("user"));

            }

            super.onStart();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void init_controls() {
        try {
            //bundle = this.getArguments();
            btn_edit = (Button) getView().findViewById(R.id.btn_edit);
            btn_back = (Button) getView().findViewById(R.id.btn_back);

            txt_username = (TextView) getView().findViewById(R.id.txt_username);

            switch_trans_obj_time = (Switch) getView().findViewById(R.id.switch_trans_obj_time);
            switch_trans_doc_time = (Switch) getView().findViewById(R.id.switch_trans_doc_time);
            switch_trans_che_time = (Switch) getView().findViewById(R.id.switch_trans_che_time);

            obj_hh_time1 = (LinearLayout) getView().findViewById(R.id.obj_hh_time1);
            obj_hh_time2 = (TextView) getView().findViewById(R.id.obj_hh_time2);
            obj_hh_time_name = (TextView) getView().findViewById(R.id.obj_hh_time_name);
            obj_hh_time3 = (ImageButton) getView().findViewById(R.id.obj_hh_time3);

            obj_min_time1 = (LinearLayout) getView().findViewById(R.id.obj_min_time1);
            obj_min_time2 = (TextView) getView().findViewById(R.id.obj_min_time2);
            obj_min_time_name = (TextView) getView().findViewById(R.id.obj_min_time_name);
            obj_min_time3 = (ImageButton) getView().findViewById(R.id.obj_min_time3);

            obj_am_pm1 = (LinearLayout) getView().findViewById(R.id.obj_am_pm1);
            obj_am_pm2 = (TextView) getView().findViewById(R.id.obj_am_pm2);
            obj_am_pm_name = (TextView) getView().findViewById(R.id.obj_am_pm_name);
            obj_am_pm3 = (ImageButton) getView().findViewById(R.id.obj_am_pm3);


        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadevents() {
        try {
            btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

                        } else {

                            if (switch_trans_obj_time.isChecked() == true || switch_trans_doc_time.isChecked() == true || switch_trans_che_time.isChecked() == true) {
                                if (!obj_hh_time2.getText().equals("HH") && !obj_min_time2.getText().equals("MM")) {
                                    save_user_lock_details();
                                } else {
                                    Toast.makeText(getContext(), "PLEASE SELECT TIME", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "PLEASE CHECK MINIMUM ONE FOR UNLOCK TIME", Toast.LENGTH_SHORT).show();
                            }
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

                        } else {
                            sendBackUserLockSetTimeResult("N");
                            pDialog.hide();
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

                /*Hours*/
            obj_hh_time1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_set_time_hour();
                }
            });
            obj_hh_time2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_set_time_hour();
                }
            });
            obj_hh_time3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_set_time_hour();
                }
            });

            /*Minutes*/
            obj_min_time1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_set_time_minute();
                }
            });
            obj_min_time2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_set_time_minute();
                }
            });
            obj_min_time3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_set_time_minute();
                }
            });

            /*AM-PM*/
            obj_am_pm1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_set_time_am_pm();
                }
            });
            obj_am_pm2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_set_time_am_pm();
                }
            });
            obj_am_pm3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    show_dialog_for_set_time_am_pm();
                }
            });

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public interface ParamUserLockSetTimeListener {
        void onFinishParamUserLockSetTime(String flag);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackUserLockSetTimeResult(String flag) {
        try {
            // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
            ParamUserLockSetTimeListener listener = (ParamUserLockSetTimeListener) getTargetFragment();
            listener.onFinishParamUserLockSetTime(flag);
            dismiss();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void save_user_lock_details() {
        try {
            POJO_User POJO = new POJO_User();
            String hh = "";
            hh = obj_hh_time2.getText().toString();
            if (obj_am_pm2.getText().equals("PM")) {
                hour = Integer.valueOf(obj_hh_time2.getText().toString());
                hour += 12;
                hh = String.valueOf(hour);
            }
            String time = "";
            time = String.valueOf(hh) + ":" + obj_min_time2.getText().toString() + ":00.000000";
            if (switch_trans_obj_time.isChecked() == true) {
                POJO.setT_obj_time(time);
            }

            if (switch_trans_doc_time.isChecked() == true) {
                POJO.setT_drc_s_time(time);
            }

            if (switch_trans_che_time.isChecked() == true) {
                POJO.setT_chc_s_time(time);
            }

            /*POJO.setM_pro((switch_trans_obj_time.isChecked() == true) ? 1 : 0);
            POJO.setM_pat((switch_trans_doc_time.isChecked() == true) ? 1 : 0);
            POJO.setM_doc((switch_trans_che_time.isChecked() == true) ? 1 : 0);*/

            if (bundle != null) {
                update_user_lock(POJO);
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void update_user_lock(POJO_User POJO) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String user = bundle.getString("user").toString();

            restService.getService().putUser(sid, POJO, user, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        Toast.makeText(getContext(), "UPDATE USER LOCK TIME SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        sendBackUserLockSetTimeResult("Y");
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();
                        //Toast.makeText(getContext(), "PLEASE ENTER VALID DATA", Toast.LENGTH_SHORT).show();
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

    private void load_user_trans_form_lock_time(final String user) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            final String name = "sunilkumbhar721@gmail.com";//app_preferences.getString("name", "default");

            restService.getService().getUserLockData(sid, name, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        trans_obj = Integer.valueOf(j2.get("trans_obj").getAsString());
                        trans_doc = Integer.valueOf(j2.get("trans_doc").getAsString());
                        trans_chem = Integer.valueOf(j2.get("trans_chem").getAsString());
                        active_flag = Integer.valueOf(j2.get("active_flag").getAsString());

                        init_controls();
                        loadevents();

                        pDialog.hide();

                       /* if (lock_flag.equals("1")) {
                            save_doctor_call();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();//"Oops !!! Locked Profile..."
                        }*/

                    } catch (Exception ex) {
                        pDialog.hide();
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();
                        Toast.makeText(getContext(), "PLEASE TRY AGAIN...", Toast.LENGTH_SHORT).show();
                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            //onsession_failure();
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

    private void show_dialog_for_set_time_hour() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                fragment_dialog_for_user_lock_set_time_hour dialog = fragment_dialog_for_user_lock_set_time_hour.newInstance("Hello world");
                //final Bundle bundle = new Bundle();
                //bundle.putString("stcokiest", "OBJ");
                //dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_dialog_for_user_lock_set_time.this, 300);
                dialog.show(getFragmentManager(), "hhh");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_set_time_minute() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                fragment_dialog_for_user_lock_set_time_minutes dialog = fragment_dialog_for_user_lock_set_time_minutes.newInstance("Hello world");
                //final Bundle bundle = new Bundle();
                //bundle.putString("stcokiest", "OBJ");
                //dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_dialog_for_user_lock_set_time.this, 300);
                dialog.show(getFragmentManager(), "mmm");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void show_dialog_for_set_time_am_pm() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                fragment_dialog_for_user_lock_set_time_am_pm dialog = fragment_dialog_for_user_lock_set_time_am_pm.newInstance("Hello world");
                //final Bundle bundle = new Bundle();
                //bundle.putString("stcokiest", "OBJ");
                //dialog.setArguments(bundle);
                dialog.setTargetFragment(fragment_dialog_for_user_lock_set_time.this, 300);
                dialog.show(getFragmentManager(), "pm_am");
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void load_user_data(final String user_id) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("email");
            jsonArray.put("employee_code");
            jsonArray.put("first_name");
            jsonArray.put("last_name");
            jsonArray.put("m_pro");
            jsonArray.put("m_pat");
            jsonArray.put("m_doc");
            jsonArray.put("m_che");
            jsonArray.put("t_obj_time");
            jsonArray.put("t_drc_s_time");
            jsonArray.put("t_chc_s_time");
            jsonArray.put("t_obj1");
            jsonArray.put("t_obj2");
            jsonArray.put("t_drc1");
            jsonArray.put("t_drc2");
            jsonArray.put("t_chc1");
            jsonArray.put("t_chc2");
            jsonArray.put("enabled");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();

            Filter1.put("User");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(user_id);

            Filters.put(Filter1);

            pDialog.show();
            restService.getService().getUser(sid, 0, 1, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_User>>() {
                        }.getType();
                        List<POJO_User> POJO = gson.fromJson(j2, type);

                        for (POJO_User pp : POJO) {
                            //pp.getName();
                            txt_username.setText(pp.getFirst_name() + " " + pp.getLast_name());

                            /*if (pp.getM_pro() == 1)
                                switch_trans_obj_time.setChecked(true);
                            else
                                switch_trans_obj_time.setChecked(false);

                            if (pp.getM_pat() == 1)
                                switch_trans_doc_time.setChecked(true);
                            else
                                switch_trans_doc_time.setChecked(false);

                            if (pp.getM_doc() == 1)
                                switch_trans_che_time.setChecked(true);
                            else
                                switch_trans_che_time.setChecked(false);*/

                        }

                        pDialog.hide();
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
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}

/*
{
    "data": {
      "name": "amolrmohite@gmail.com",
      "email": "amolrmohite@gmail.com",
      "employee_code": "EMP/0006",
      "first_name": "Amol",
      "last_name": "Mohite",
      "m_pro": 1,
      "m_pat": 0,
      "m_doc": 1,
      "m_che": 1,
      "t_obj_time": "9:30:00",
      "t_drc_s_time": "12:00:00",
      "t_chc_s_time": "0:00:00",
      "t_obj1": "2017-01-01",
      "t_obj2": "2017-01-01",
      "t_drc1": "2017-01-01",
      "t_drc2": "2017-01-01",
      "t_chc1": "2017-01-01",
      "t_chc2": "2017-01-01",
      "enabled": 1
    }
}


public class Data {

    private String name;
    private String email;
    private String employee_code;
    private String first_name;
    private String last_name;
    private Integer m_pro;
    private Integer m_pat;
    private Integer m_doc;
    private Integer m_che;
    private String t_obj_time;
    private String t_drc_s_time;
    private String t_chc_s_time;
    private String t_obj1;
    private String t_obj2;
    private String t_drc1;
    private String t_drc2;
    private String t_chc1;
    private String t_chc2;
    private Integer enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getM_pro() {
        return m_pro;
    }

    public void setM_pro(Integer m_pro) {
        this.m_pro = m_pro;
    }

    public Integer getM_pat() {
        return m_pat;
    }

    public void setM_pat(Integer m_pat) {
        this.m_pat = m_pat;
    }

    public Integer getM_doc() {
        return m_doc;
    }

    public void setM_doc(Integer m_doc) {
        this.m_doc = m_doc;
    }

    public Integer getM_che() {
        return m_che;
    }

    public void setM_che(Integer m_che) {
        this.m_che = m_che;
    }

    public String getT_obj_time() {
        return t_obj_time;
    }

    public void setT_obj_time(String t_obj_time) {
        this.t_obj_time = t_obj_time;
    }

    public String getT_drc_s_time() {
        return t_drc_s_time;
    }

    public void setT_drc_s_time(String t_drc_s_time) {
        this.t_drc_s_time = t_drc_s_time;
    }

    public String getT_chc_s_time() {
        return t_chc_s_time;
    }

    public void setT_chc_s_time(String t_chc_s_time) {
        this.t_chc_s_time = t_chc_s_time;
    }

    public String getT_obj1() {
        return t_obj1;
    }

    public void setT_obj1(String t_obj1) {
        this.t_obj1 = t_obj1;
    }

    public String getT_obj2() {
        return t_obj2;
    }

    public void setT_obj2(String t_obj2) {
        this.t_obj2 = t_obj2;
    }

    public String getT_drc1() {
        return t_drc1;
    }

    public void setT_drc1(String t_drc1) {
        this.t_drc1 = t_drc1;
    }

    public String getT_drc2() {
        return t_drc2;
    }

    public void setT_drc2(String t_drc2) {
        this.t_drc2 = t_drc2;
    }

    public String getT_chc1() {
        return t_chc1;
    }

    public void setT_chc1(String t_chc1) {
        this.t_chc1 = t_chc1;
    }

    public String getT_chc2() {
        return t_chc2;
    }

    public void setT_chc2(String t_chc2) {
        this.t_chc2 = t_chc2;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

}*/