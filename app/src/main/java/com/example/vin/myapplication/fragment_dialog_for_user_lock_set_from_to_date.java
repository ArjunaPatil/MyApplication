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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.MoviesAdapter.context;


public class fragment_dialog_for_user_lock_set_from_to_date extends android.support.v4.app.DialogFragment
        implements DatePickerFragment.DateDialogListener {

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

    LinearLayout select_from_date;//select_from_date
    TextView txt_plan_of_from_date;//txt_plan_of_from_date
    TextView select_from_date1;//select_from_date1
    ImageButton select_from_date3;//select_from_date3

    LinearLayout select_to_date;//select_to_date
    TextView txt_plan_of_to_date;//txt_plan_of_to_date
    TextView select_to_date1;//select_to_date1
    ImageButton select_to_date3;//select_to_date3

    Integer trans_obj = 0;
    Integer trans_doc = 0;
    Integer trans_chem = 0;
    Integer active_flag = 0;

    String am_pm = "";
    Integer date_flag = 0;

    String msg = "";

    public POJO_Doctor_Master last_POJO;


    // Empty constructor required for DialogFragment
    public fragment_dialog_for_user_lock_set_from_to_date() {
    }

    public static fragment_dialog_for_user_lock_set_from_to_date newInstance(String title) {
        try {
            fragment_dialog_for_user_lock_set_from_to_date frag = new fragment_dialog_for_user_lock_set_from_to_date();
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
            View view = inflater.inflate(R.layout.fragment_dialog_for_user_lock_set_from_to_date, container);
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
    public void onFinishDialog(Date date) {
        try {
            //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String hireDate = sdf.format(date);

            if (date_flag == 1) {
                txt_plan_of_from_date.setText(hireDate);
                date_flag = 0;
            }

            if (date_flag == 2) {
                txt_plan_of_to_date.setText(hireDate);
                date_flag = 0;
            }

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
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

            select_from_date = (LinearLayout) getView().findViewById(R.id.select_from_date);
            txt_plan_of_from_date = (TextView) getView().findViewById(R.id.txt_plan_of_from_date);
            select_from_date1 = (TextView) getView().findViewById(R.id.select_from_date1);
            select_from_date3 = (ImageButton) getView().findViewById(R.id.select_from_date3);

            select_to_date = (LinearLayout) getView().findViewById(R.id.select_to_date);
            txt_plan_of_to_date = (TextView) getView().findViewById(R.id.txt_plan_of_to_date);
            select_to_date1 = (TextView) getView().findViewById(R.id.select_to_date1);
            select_to_date3 = (ImageButton) getView().findViewById(R.id.select_to_date3);

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
                                if (!txt_plan_of_to_date.getText().equals("yyyy/mm/dd") && !txt_plan_of_from_date.getText().equals("yyyy/mm/dd")) {
                                    String from_date = "", to_date = "";
                                    from_date = txt_plan_of_from_date.getText().toString();
                                    to_date = txt_plan_of_to_date.getText().toString();

                                    Date frm_dt = new SimpleDateFormat("yyyy-MM-dd").parse(from_date);
                                    Date to_dt = new SimpleDateFormat("yyyy-MM-dd").parse(to_date);

                                    //&& frm_dt.after(to_dt)
                                    if (frm_dt.compareTo(to_dt) <= 0 ) {
                                        save_user_lock_details();
                                    } else {
                                        Toast.makeText(getContext(), "PLEASE SELECT FROM DATE LESS THAN TO DATE", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getContext(), "PLEASE SELECT FROM <-> TO DATE", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "PLEASE CHECK MINIMUM ONE FOR SET DATE", Toast.LENGTH_SHORT).show();
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
                            sendBackUserLockSetDateFromToResult("N");
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

            /*From Date*/
            select_from_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    date_flag = 1;
                    show_dialog_for_date_picker();
                }
            });
            txt_plan_of_from_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    date_flag = 1;
                    show_dialog_for_date_picker();
                }
            });
            select_from_date3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    date_flag = 1;
                    show_dialog_for_date_picker();
                }
            });

            /*To Date*/
            select_to_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    date_flag = 2;
                    show_dialog_for_date_picker();
                }
            });
            txt_plan_of_to_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    date_flag = 2;
                    show_dialog_for_date_picker();
                }
            });
            select_to_date3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    date_flag = 2;
                    show_dialog_for_date_picker();
                }
            });


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public interface ParamUserLockSetDateFromToListener {
        void onFinishParamUserLockSetDateFromTo(String flag);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackUserLockSetDateFromToResult(String flag) {
        try {
            // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
            ParamUserLockSetDateFromToListener listener = (ParamUserLockSetDateFromToListener) getTargetFragment();
            listener.onFinishParamUserLockSetDateFromTo(flag);
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

            if (switch_trans_obj_time.isChecked() == true) {
                POJO.setT_obj1(txt_plan_of_from_date.getText().toString());
                POJO.setT_obj2(txt_plan_of_to_date.getText().toString());
            }

            if (switch_trans_doc_time.isChecked() == true) {
                POJO.setT_drc1(txt_plan_of_from_date.getText().toString());
                POJO.setT_drc2(txt_plan_of_to_date.getText().toString());
            }

            if (switch_trans_che_time.isChecked() == true) {
                POJO.setT_chc1(txt_plan_of_from_date.getText().toString());
                POJO.setT_chc2(txt_plan_of_to_date.getText().toString());
            }

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
                        Toast.makeText(getContext(), "UPDATE USER'S FROM-TO DATE SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                        sendBackUserLockSetDateFromToResult("Y");
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

    private void show_dialog_for_date_picker() {
        try {
            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

            } else {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.setTargetFragment(fragment_dialog_for_user_lock_set_from_to_date.this, 300);
                dialog.show(getFragmentManager(), "date");
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
            /*jsonArray.put("m_pro");
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
            jsonArray.put("t_chc2");*/
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

                        }
                        loadevents();
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