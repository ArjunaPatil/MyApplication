package com.example.vin.myapplication;

import android.app.ProgressDialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.MoviesAdapter.context;


public class FragmentDialog_for_doctor_active_campaign extends android.support.v4.app.DialogFragment {

    private Realm mRealm;
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
    TextView txt_doctorname;
    TextView txt_patch;
    TextView txt_active_count;
    TextView txt_camp_count;
    CheckBox chk_active;
    CheckBox chk_inactive;
    CheckBox chk_campaign;
    Integer flag_action;
    String msg = "";

    //active,inactive,campaign_book

    //dcr section
   /* LinearLayout ll_dcr;
    LinearLayout ll_doc_call;
    TextView txt_dcr_patch;
    View vw_dcr_patch;
    TextView txt_dcr_jfw1;
    View vw_dcr_jfw1;
    TextView txt_dcr_jfw2;
    View vw_dcr_jfw2;
    TextView txt_dcr_agenda;
    View vw_dcr_agenda;
*/

    public POJO_Doctor_Master last_POJO;


    // Empty constructor required for DialogFragment
    public FragmentDialog_for_doctor_active_campaign() {
    }

    public static FragmentDialog_for_doctor_active_campaign newInstance(String title) {
        try {
            FragmentDialog_for_doctor_active_campaign frag = new FragmentDialog_for_doctor_active_campaign();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().setCanceledOnTouchOutside(true);
            View view = inflater.inflate(R.layout.fragment_dialog_doctor_active_compaign, container);

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



            /*int width = (int) (getContext().getResources().getDisplayMetrics().widthPixels);
            //int width = getContext().getResources().getDisplayMetrics().widthPixels;
            TextView txt_test = (TextView) getView().findViewById(R.id.txt_test);
            txt_test.setWidth(width);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                txt_test.setWidth(width);
            }*/

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

                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String user = app_preferences.getString("name", "default");

                if (!bundle.getString("doctor_name").toString().equals("")) {
                    txt_doctorname.setText(bundle.getString("doctor_name").toString());
                }

                if (!bundle.getString("patch_name").toString().equals("")) {
                    txt_patch.setText(bundle.getString("patch_name").toString());
                }

                if (bundle.getString("active").toString().equals("1")) {
                    chk_active.setChecked(true);
                } else {
                    chk_active.setChecked(false);
                }

                if (bundle.getString("campaign").toString().equals("1")) {
                    chk_campaign.setChecked(true);
                } else {
                    chk_campaign.setChecked(false);
                }

                if (bundle.getString("inactive").toString().equals("1")) {
                    chk_inactive.setChecked(true);
                } else {
                    chk_inactive.setChecked(false);
                }

                pDialog = new ProgressDialog(getContext());
                if (user.equals(bundle.getString("user").toString())) {
                    btn_edit.setVisibility(View.VISIBLE);
                } else {
                    btn_edit.setVisibility(View.GONE);
                }

                get_doctor_details(bundle.getString("user"));

                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

                            } else {
                                lock_check();
                                //sendBackDoctorInsertPatchResult("Y", bundle.getString("name"), bundle.getString("select_user"), bundle.getString("select_date"), bundle.getString("dcr"), bundle.getString("camp"), bundle.getString("meeting"), bundle.getString("lve"));
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
                                sendBackDoctorInsertPatchResult("N", "", "", "", "", "", "", "");
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
            txt_doctorname = (TextView) getView().findViewById(R.id.txt_doctorname);
            txt_patch = (TextView) getView().findViewById(R.id.txt_patch);
            txt_active_count = (TextView) getView().findViewById(R.id.txt_active_count);
            txt_camp_count = (TextView) getView().findViewById(R.id.txt_camp_count);
            chk_active = (CheckBox) getView().findViewById(R.id.chk_active);
            chk_inactive = (CheckBox) getView().findViewById(R.id.chk_inactive);
            chk_campaign = (CheckBox) getView().findViewById(R.id.chk_campaign);

            chk_active.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
            chk_inactive.setOnCheckedChangeListener(new myCheckBoxChnageClicker());
            chk_campaign.setOnCheckedChangeListener(new myCheckBoxChnageClicker());

        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    class myCheckBoxChnageClicker implements CheckBox.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            // Toast.makeText(CheckBoxCheckedDemo.this, &quot;Checked =&gt; &quot;+isChecked, Toast.LENGTH_SHORT).show();
            try {
                if (isChecked) {
                    if (buttonView == chk_active) {
                        Remove_inactive();
                        //showTextNotification("chkBoxDCR");
                    }

                    if (buttonView == chk_inactive) {
                        Remove_active();
                        //showTextNotification("chkBoxcme");
                    }
                }
            } catch (Exception ex) {
                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Remove_active() {
        try {
            chk_active.setChecked(false);
            chk_campaign.setChecked(false);
            chk_campaign.setVisibility(View.GONE);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Remove_inactive() {
        try {
            chk_inactive.setChecked(false);
            chk_campaign.setVisibility(View.VISIBLE);
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public interface ParamDailyPlanListener {
        void onFinishParamDailyPlan(String flag, String name, String select_user, String select_date, String dcr, String camp, String meeting, String lve);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackDoctorInsertPatchResult(String flag, String name, String select_user, String select_date, String dcr, String camp, String meeting, String lve) {
        try {
            // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
            ParamDailyPlanListener listener = (ParamDailyPlanListener) getTargetFragment();
            listener.onFinishParamDailyPlan(flag, name, select_user, select_date, dcr, camp, meeting, lve);
            dismiss();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    /*Check Form Lock Or Not*/
    public void lock_check() {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String name = app_preferences.getString("name", "default");

            restService.getService().getMasterFormLockOrNot(sid, "'" + name + "'", "doctor", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        String lock_flag = j2.get("lock_flag").getAsString();
                        String message = j2.get("message").getAsString();
                        if (lock_flag.equals("0")) {
                            save_doctor();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();//"Oops !!! Locked Profile..."
                        }


                        /*JsonObject j1 = jsonElement.getAsJsonObject();
                        //JsonObject j2 = j1.getAsJsonObject("message");

                        //String lock_flag = j2.get("message").getAsString();
                        String lock_flag = j1.get("message").getAsString();
                        if (lock_flag == "0") {
                            save_doctor();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), "Oops !!! Locked Doctor...", Toast.LENGTH_SHORT).show();
                        }*/

                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();
                        //Toast.makeText(getContext(), "PLEASE TRY AGAIN...", Toast.LENGTH_SHORT).show();
                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            //onsession_failure();
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
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

    private void save_doctor() {
        try {

            /*This code Cut From button Click Event*/

            //final Bundle bundle = this.getArguments();

            POJO_Doctor_Master POJO = new POJO_Doctor_Master();

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            //POJO.setPatch();

            POJO.setActive((chk_active.isChecked() == true) ? 1 : 0);
            POJO.setInactive((chk_inactive.isChecked() == true) ? 1 : 0);
            POJO.setCampaign((chk_campaign.isChecked() == true) ? 1 : 0);


            if (Validation(POJO) == true) {
                if (bundle != null) {

                    // POJO.setName((tv_doc_id.getText().toString() == null) ? "" : tv_doc_id.getText().toString());
                    //Check Count 80 125 and 40 camp
                    //Check Count 80 125 and 40 camp
                    //Check Count 80 125 and 40 camp
                    if (chk_inactive.isChecked() == true) {
                        update_doctor(POJO);
                    } else if (flag_action.equals(1)) {
                        update_doctor(POJO);

                    } else if (flag_action.equals(2)) {
                        //ACTIVE DOCTOR COMPLETE ONLY REMAIN CAMPAIGN DOCTORS SO CHECK DOCTOR FLAG MOSTLY IS ACTIVE  NOT INACTIVE
                        //bundle.getString("active").toString().equals("1")
                        //bundle.getString("campaign").toString().equals("1")
                        //bundle.getString("inactive").toString().equals("1")
                        if (bundle.getString("active").toString().equals("1")) {
                            update_doctor(POJO);
                        } else {
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    } else if (flag_action.equals(3)) {
                        if (chk_campaign.isChecked() == false) {
                            update_doctor(POJO);
                        } else {
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getContext(), "Validation Error", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean Validation(POJO_Doctor_Master POJO) {
        try {
            Boolean a = false;
            if (POJO.getActive() == 0 && POJO.getInactive() == 0) {
                Toast.makeText(getContext(), "DOCTOR SHOULD BE SELECT ACTIVE/INACTIVE", Toast.LENGTH_SHORT).show();
                a = false;
            } else {
                a = true;
            }
            return a;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void update_doctor(POJO_Doctor_Master POJO) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String name = bundle.getString("name").toString();

            restService.getService().doctor_master_update(sid, POJO, name, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        update_doctor_master_class(jsonElement);
                        /*pDialog.hide();
                        Toast.makeText(getContext(), "DOCTOR UPDATE SUCCESSFULLY", Toast.LENGTH_SHORT).show();*/

                        sendBackDoctorInsertPatchResult("Y", "", "", "", "", "", "", "");
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
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void update_doctor_master_class(JsonElement jsonElement) {
        try {
            JsonObject j1 = jsonElement.getAsJsonObject();

            JsonElement j2 = j1.getAsJsonObject("data");

            Gson gson = new Gson();
            Type type = new TypeToken<POJO_Doctor_Master>() {
            }.getType();
            POJO_Doctor_Master POJO = gson.fromJson(j2, type);

            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(POJO);
            mRealm.commitTransaction();
            mRealm.close();

            pDialog.hide();
            Toast.makeText(getContext(), "DOCTOR UPDATE SUCCESSFULLY", Toast.LENGTH_SHORT).show();

            sendBackDoctorInsertPatchResult("Y", "", "", "", "", "", "", "");

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    //public void get_todays_objective(String dd) {
    public void get_doctor_details(String emp_id) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String user_id = app_preferences.getString("name", "default");

            pDialog.show();
            restService.getService().getDoctor_cnt_active_camp(sid, "'" + emp_id + "'", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");
                        if (j2 != null) {
                            //j2.get("tot_doc").getAsString();
                            //j2.get("inactive_doc").getAsString();
                            txt_active_count.setText(j2.get("active_doc").getAsString());
                            txt_camp_count.setText(j2.get("camp_doc").getAsString());
                            flag_action = Integer.parseInt(j2.get("flag_action").getAsString());
                            msg = j2.get("msg").getAsString();

                            /*Gson gson = new Gson();
                            Type type = new TypeToken<List<POJO_Presenty>>() {
                            }.getType();*/


                        } else {
                            Toast.makeText(getContext(), "NO RECORD FOUND", Toast.LENGTH_LONG).show();
                            pDialog.hide();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                        pDialog.hide();
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
                            //Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                            //TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            //txt_loading.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                }
            });
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            pDialog.hide();
        }
    }


}
