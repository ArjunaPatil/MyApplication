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

public class fragment_dialog_for_user_activation_deactivation extends android.support.v4.app.DialogFragment {

    RestService restService;
    Bundle bundle;
    private long mLastClickTime = 0;

    private ProgressDialog pDialog;
    Button btn_edit;
    Button btn_back;

    TextView txt_username, tv_name;
    Switch switch_user_status;

    Integer active_flag = 0;
    String msg = "";

    // Empty constructor required for DialogFragment
    public fragment_dialog_for_user_activation_deactivation() {
    }

    public static fragment_dialog_for_user_activation_deactivation newInstance(String title) {
        try {
            fragment_dialog_for_user_activation_deactivation frag = new fragment_dialog_for_user_activation_deactivation();
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
            View view = inflater.inflate(R.layout.fragment_dialog_for_user_activation_deactivation, container);

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
                //load_user_trans_form_lock_time(bundle.getString("user"));

                init_controls();
                if (bundle.getString("user_name") == "" && bundle.getString("user") == "") {
                } else {
                    txt_username.setText(bundle.getString("user_name"));
                    //tv_name.setText(bundle.getString("user_name"));
                }

                loadevents();
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
            tv_name = (TextView) getView().findViewById(R.id.name);
            switch_user_status = (Switch) getView().findViewById(R.id.switch_user_status);

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
                            save_user_lock_details();
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
                            sendBackUserStatusResult("N");
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

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public interface ParamUserStatusListener {
        void onFinishParamUserStatus(String flag);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackUserStatusResult(String flag) {
        try {
            // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
            ParamUserStatusListener listener = (ParamUserStatusListener) getTargetFragment();
            listener.onFinishParamUserStatus(flag);
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

            POJO.setEnabled((switch_user_status.isChecked() == true) ? 1 : 0);

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
                        Toast.makeText(getContext(), "UPDATE SUCCESSFULLY STATUS OF USER", Toast.LENGTH_SHORT).show();
                        sendBackUserStatusResult("Y");
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), "Error...", Toast.LENGTH_SHORT).show();
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
                            //String nm = pp.getFirst_name() + " " + pp.getLast_name();
                            //txt_username.setText(nm);
                            /*switch_user_status.setChecked(Boolean.valueOf(String.valueOf(pp.getEnabled())));*/
                            if (pp.getEnabled() == 1)
                                switch_user_status.setChecked(true);
                            else
                                switch_user_status.setChecked(false);
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

                        active_flag = Integer.valueOf(j2.get("active_flag").getAsString());

                        init_controls();
                        loadevents();

                        pDialog.hide();

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

}
