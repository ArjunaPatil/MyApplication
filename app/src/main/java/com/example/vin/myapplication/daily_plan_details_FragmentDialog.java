package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.location.LocationManager;
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
import android.widget.LinearLayout;
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
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.MoviesAdapter.context;

public class daily_plan_details_FragmentDialog extends android.support.v4.app.DialogFragment {

    private Realm mRealm;
    RestService restService;
    Bundle bundle;
    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    public boolean bool_full_update = false;
    private long mLastClickTime = 0;

    private ProgressDialog pDialog;
    TextView txt_plan_of_date;
    TextView txt_employee_name;
    Button btn_edit;
    Button btn_back;


    //dcr section
    LinearLayout ll_dcr;
    LinearLayout ll_doc_call;
    TextView txt_dcr_patch;
    View vw_dcr_patch;
    TextView txt_dcr_jfw1;
    View vw_dcr_jfw1;
    TextView txt_dcr_jfw2;
    View vw_dcr_jfw2;
    TextView txt_dcr_agenda;
    View vw_dcr_agenda;

    //camp section
    LinearLayout ll_camp;
    LinearLayout ll_camp_call;
    TextView txt_camp_doctor;
    View vw_camp_doctor;
    TextView txt_camp_jfw1;
    View vw_camp_jfw1;
    TextView txt_camp_jfw2;
    View vw_camp_jfw2;
    TextView txt_camp_agenda;
    View vw_camp_agenda;

    //meeting section
    LinearLayout ll_meeting;
    LinearLayout ll_meeting_call;
    TextView txt_meeting_with;
    View vw_meeting_with;
    TextView txt_place;
    View vw_place;
    TextView txt_meeting_agenda;
    View vw_meeting_agenda;

    //leave section
    LinearLayout ll_leave;
    LinearLayout ll_leave_call;
    TextView txt_leave_type;
    View vw_leave_type;
    TextView txt_reason;
    View vw_reason;

    LocationManager locationManager;
    public POJO_objective last_POJO;


    // Empty constructor required for DialogFragment
    public daily_plan_details_FragmentDialog() {
    }

    public static daily_plan_details_FragmentDialog newInstance(String title) {
        try {
            daily_plan_details_FragmentDialog frag = new daily_plan_details_FragmentDialog();
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
            View view = inflater.inflate(R.layout.fragment_dialog_daily_plan_details, container);

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
                listview_show_hide();

                final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                String emp = app_preferences.getString("name", "default");
                if (emp.equals(bundle.getString("select_user")) == true) {
                    btn_edit.setVisibility(View.VISIBLE);
                } else {
                    btn_edit.setVisibility(View.GONE);
                }

                pDialog = new ProgressDialog(getContext());
                txt_plan_of_date.setText(bundle.getString("select_date"));
                get_todays_objective(bundle.getString("name"));

                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {

                            } else {
                                GPSTracker gps = new GPSTracker(getContext(), daily_plan_details_FragmentDialog.this, false);
                                if (gps.canGetLocation()) {
                                    //////////
                                    sendBackDoctorInsertPatchResult("Y", bundle.getString("name"), bundle.getString("select_user"), bundle.getString("select_date"), bundle.getString("dcr"), bundle.getString("camp"), bundle.getString("meeting"), bundle.getString("lve"));
                                    ////////
                                } else {
                                    gps.showSettingsAlert();
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
                                sendBackDoctorInsertPatchResult("N", "", "", "", "", "", "", "");
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

            bundle = this.getArguments();

            if (bundle.getString("dcr").equals("1")) {

                /*----------------------------------DCR SECTION START--------------------------------------------*/

                ll_doc_call = (LinearLayout) getView().findViewById(R.id.ll_doc_call);
                txt_dcr_patch = (TextView) getView().findViewById(R.id.txt_dcr_patch);
                vw_dcr_patch = getView().findViewById(R.id.vw_dcr_patch);
                txt_dcr_jfw1 = (TextView) getView().findViewById(R.id.txt_dcr_jfw1);
                vw_dcr_jfw1 = getView().findViewById(R.id.vw_dcr_jfw1);
                txt_dcr_jfw2 = (TextView) getView().findViewById(R.id.txt_dcr_jfw2);
                vw_dcr_jfw2 = getView().findViewById(R.id.vw_dcr_jfw2);
                txt_dcr_agenda = (TextView) getView().findViewById(R.id.txt_dcr_agenda);
                vw_dcr_agenda = getView().findViewById(R.id.vw_dcr_agenda);


            /*----------------------------------DCR SECTION END--------------------------------------------*/

            }

            if (bundle.getString("camp").equals("1")) {

                /*----------------------------------CAMP SECTION START--------------------------------------------*/

                //camp section
                ll_camp_call = (LinearLayout) getView().findViewById(R.id.ll_camp_call);
                txt_camp_doctor = (TextView) getView().findViewById(R.id.txt_camp_doctor);
                vw_camp_doctor = getView().findViewById(R.id.vw_camp_doctor);
                txt_camp_jfw1 = (TextView) getView().findViewById(R.id.txt_camp_jfw1);
                vw_camp_jfw1 = getView().findViewById(R.id.vw_camp_jfw1);
                txt_camp_jfw2 = (TextView) getView().findViewById(R.id.txt_camp_jfw2);
                vw_camp_jfw2 = getView().findViewById(R.id.vw_camp_jfw2);
                txt_camp_agenda = (TextView) getView().findViewById(R.id.txt_camp_agenda);
                vw_camp_agenda = getView().findViewById(R.id.vw_camp_agenda);

            /*----------------------------------CAMP SECTION END--------------------------------------------*/

            }

            if (bundle.getString("meeting").equals("1")) {

                /*----------------------------------MEETING SECTION START--------------------------------------------*/

                //meeting section
                ll_meeting_call = (LinearLayout) getView().findViewById(R.id.ll_meeting_call);
                txt_meeting_with = (TextView) getView().findViewById(R.id.txt_meeting_with);
                vw_meeting_with = getView().findViewById(R.id.vw_meeting_with);
                txt_place = (TextView) getView().findViewById(R.id.txt_place);
                vw_place = getView().findViewById(R.id.vw_place);
                txt_meeting_agenda = (TextView) getView().findViewById(R.id.txt_meeting_agenda);
                vw_meeting_agenda = getView().findViewById(R.id.vw_meeting_agenda);

                /*----------------------------------MEETING SECTION END--------------------------------------------*/

            }

            if (bundle.getString("lve").equals("1")) {

                /*----------------------------------Leave SECTION START--------------------------------------------*/

                //leave section
                ll_leave_call = (LinearLayout) getView().findViewById(R.id.ll_leave_call);
                txt_leave_type = (TextView) getView().findViewById(R.id.txt_leave_type);
                vw_leave_type = getView().findViewById(R.id.vw_leave_type);
                txt_reason = (TextView) getView().findViewById(R.id.txt_reason);
                vw_reason = getView().findViewById(R.id.vw_reason);

            /*----------------------------------Leave SECTION END--------------------------------------------*/

            }


            ll_dcr = (LinearLayout) getView().findViewById(R.id.ll_dcr);
            ll_camp = (LinearLayout) getView().findViewById(R.id.ll_camp);
            ll_meeting = (LinearLayout) getView().findViewById(R.id.ll_meeting);
            ll_leave = (LinearLayout) getView().findViewById(R.id.ll_leave);

            txt_plan_of_date = (TextView) getView().findViewById(R.id.txt_plan_of_date);
            txt_employee_name = (TextView) getView().findViewById(R.id.txt_employee_name);

            btn_edit = (Button) getView().findViewById(R.id.btn_edit);
            btn_back = (Button) getView().findViewById(R.id.btn_back);

        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
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


    //public void get_todays_objective(String dd) {
    public void get_todays_objective(String name) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String user_id = app_preferences.getString("name", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("select_date");
            jsonArray.put("objective");
            jsonArray.put("user");
            jsonArray.put("user_name");
            jsonArray.put("creation");
            jsonArray.put("modified");
            jsonArray.put("modified_by");
            jsonArray.put("owner");
            jsonArray.put("docstatus");
            jsonArray.put("doctor_flag");
            jsonArray.put("meeting_flag");
            jsonArray.put("camp_flag");
            jsonArray.put("leave_flag");

            jsonArray.put("select_patch_name");
            jsonArray.put("select_patch");
            jsonArray.put("dcr_jfw_with1_name");
            jsonArray.put("dcr_jfw_with2_name");
            jsonArray.put("call_agenda");

            jsonArray.put("meeting_with");
            jsonArray.put("place");
            jsonArray.put("meeting_agenda");

            jsonArray.put("doctor");
            jsonArray.put("doctor_name");
            jsonArray.put("doctor_flag");
            jsonArray.put("meeting_flag");
            jsonArray.put("camp_jfw_with_name1");
            jsonArray.put("camp_jfw_with_name2");
            jsonArray.put("camp_agenda");

            jsonArray.put("leave_type1");
            jsonArray.put("leave_type2");
            jsonArray.put("leave_type3");
            jsonArray.put("reason");

            jsonArray.put("leave_approval");
            jsonArray.put("leave_approved_by");
            jsonArray.put("plan_approve");
            jsonArray.put("plan_approved_by");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();


            Filter1.put("Objective");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(name);

            Filters.put(Filter1);

            pDialog.show();
            restService.getService().getObjective(sid, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j22 = j1.getAsJsonArray("data");

                        if (j22.size() > 0) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<POJO_objective>>() {
                            }.getType();
                            List<POJO_objective> POJO = gson.fromJson(j22, type);


                            if (POJO.size() > 0) {

                                POJO_objective firstA = POJO.get(0);
                                //txt_plan_of_date.setText(firstA.getSelect_date());
                                txt_employee_name.setText(firstA.getUser_name());
                                if (bundle.getString("dcr").equals("1")) {
                                    if (firstA.getDoctor_flag() == 1) {

                                        if (firstA.getSelect_patch() != null) {
                                            txt_dcr_patch.setText((firstA.getSelect_patch().isEmpty()) == true ? "-" : firstA.getSelect_patch().toString());
                                        } else {
                                            vw_dcr_patch = getView().findViewById(R.id.vw_dcr_patch);
                                            txt_dcr_patch.setVisibility(View.GONE);
                                            vw_dcr_patch.setVisibility(View.GONE);
                                        }

                                        if (firstA.getDcr_jfw_with1_name() != null) {
                                            txt_dcr_jfw1.setText((firstA.getDcr_jfw_with1_name().isEmpty()) == true ? "-" : firstA.getDcr_jfw_with1_name().toString());
                                        } else {
                                            txt_dcr_jfw1.setText("-");
                                            /*vw_dcr_jfw1 = getView().findViewById(R.id.vw_dcr_jfw1);
                                            txt_dcr_jfw1.setVisibility(View.GONE);
                                            vw_dcr_jfw1.setVisibility(View.GONE);*/
                                        }

                                        if (firstA.getDcr_jfw_with2_name() != null) {
                                            txt_dcr_jfw2.setText((firstA.getDcr_jfw_with2_name().isEmpty()) == true ? "-" : firstA.getDcr_jfw_with2_name().toString());
                                        } else {
                                            txt_dcr_jfw2.setText("-");
                                            /*vw_dcr_jfw2 = getView().findViewById(R.id.vw_dcr_jfw2);
                                            txt_dcr_jfw2.setVisibility(View.GONE);
                                            vw_dcr_jfw2.setVisibility(View.GONE);*/
                                        }

                                        if (firstA.getCall_agenda() != null) {
                                            txt_dcr_agenda.setText((firstA.getCall_agenda().isEmpty()) == true ? "-" : firstA.getCall_agenda().toString());
                                        } else {
                                            vw_dcr_agenda = getView().findViewById(R.id.vw_dcr_agenda);
                                            txt_dcr_agenda.setVisibility(View.GONE);
                                            vw_dcr_agenda.setVisibility(View.GONE);
                                        }
                                    }
                                }

                                if (bundle.getString("camp").equals("1")) {
                                    if (firstA.getCamp_flag() == 1) {

                                        if (firstA.getDoctor_name() != null) {
                                            txt_camp_doctor.setText((firstA.getDoctor_name().isEmpty()) == true ? "-" : firstA.getDoctor_name().toString());
                                        } else {
                                            vw_camp_doctor = getView().findViewById(R.id.vw_camp_doctor);
                                            txt_camp_doctor.setVisibility(View.GONE);
                                            vw_camp_doctor.setVisibility(View.GONE);
                                        }

                                        if (firstA.getCamp_jfw_with_name1() != null) {
                                            txt_camp_jfw1.setText((firstA.getCamp_jfw_with_name1().isEmpty()) == true ? "-" : firstA.getCamp_jfw_with_name1().toString());
                                        } else {
                                            txt_camp_jfw1.setText("-");
                                            /*vw_camp_jfw1 = getView().findViewById(R.id.vw_camp_jfw1);
                                            txt_camp_jfw1.setVisibility(View.GONE);
                                            vw_camp_jfw1.setVisibility(View.GONE);*/
                                        }

                                        if (firstA.getCamp_jfw_with_name2() != null) {
                                            txt_camp_jfw2.setText((firstA.getCamp_jfw_with_name2().isEmpty()) == true ? "-" : firstA.getCamp_jfw_with_name2().toString());
                                        } else {
                                            txt_camp_jfw2.setText("-");
                                            /*vw_camp_jfw2 = getView().findViewById(R.id.vw_camp_jfw2);
                                            txt_camp_jfw2.setVisibility(View.GONE);
                                            vw_camp_jfw2.setVisibility(View.GONE);*/
                                        }

                                        if (firstA.getCamp_agenda() != null) {
                                            txt_camp_agenda.setText((firstA.getCamp_agenda().isEmpty()) == true ? "-" : firstA.getCamp_agenda().toString());
                                        } else {
                                            vw_camp_agenda = getView().findViewById(R.id.vw_camp_agenda);
                                            txt_camp_agenda.setVisibility(View.GONE);
                                            vw_camp_agenda.setVisibility(View.GONE);
                                        }
                                    }
                                }

                                if (bundle.getString("meeting").equals("1")) {
                                    if (firstA.getMeeting_flag() == 1) {

                                        if (firstA.getMeeting_with() != null) {
                                            txt_meeting_with.setText((firstA.getMeeting_with().isEmpty()) == true ? "-" : firstA.getMeeting_with().toString());
                                        } else {
                                            vw_meeting_with = getView().findViewById(R.id.vw_meeting_with);
                                            txt_meeting_with.setVisibility(View.GONE);
                                            vw_meeting_with.setVisibility(View.GONE);
                                        }

                                        if (firstA.getPlace() != null) {
                                            txt_place.setText((firstA.getPlace().isEmpty()) == true ? "-" : firstA.getPlace().toString());
                                        } else {
                                            vw_place = getView().findViewById(R.id.vw_place);
                                            txt_place.setVisibility(View.GONE);
                                            vw_place.setVisibility(View.GONE);
                                        }

                                        if (firstA.getMeeting_agenda() != null) {
                                            txt_meeting_agenda.setText((firstA.getMeeting_agenda().isEmpty()) == true ? "-" : firstA.getMeeting_agenda().toString());
                                        } else {
                                            vw_meeting_agenda = getView().findViewById(R.id.vw_meeting_agenda);
                                            txt_meeting_agenda.setVisibility(View.GONE);
                                            vw_meeting_agenda.setVisibility(View.GONE);
                                        }
                                    }
                                }
                                if (bundle.getString("lve").equals("1")) {
                                    if (firstA.getLeave_flag() == 1) {

                                        if (firstA.getLeave_type1().equals(1) == true) {
                                            txt_leave_type.setText("CAUSUAL LEAVE");
                                        } else if (firstA.getLeave_type2().equals(1) == true) {
                                            txt_leave_type.setText("PRIVILEGE LEAVE");
                                        } else if (firstA.getLeave_type3().equals(1) == true) {
                                            txt_leave_type.setText("SICK LEAVE");
                                        } else {
                                            vw_leave_type = getView().findViewById(R.id.vw_leave_type);
                                            txt_leave_type.setVisibility(View.GONE);
                                            vw_leave_type.setVisibility(View.GONE);
                                        }
                                        if (firstA.getReason() != null) {
                                            txt_reason.setText((firstA.getReason().isEmpty()) == true ? "-" : firstA.getReason().toString());
                                        } else {
                                            txt_reason.setText("-");
                                            /*vw_reason = getView().findViewById(R.id.vw_reason);
                                            txt_reason.setVisibility(View.GONE);
                                            vw_reason.setVisibility(View.GONE);*/
                                        }
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), "Record Not Found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
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
                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
                            txt_loading.setText("ERROR..");

                        } else if (msg.contains("139.59.63.181")) {

                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
                            txt_loading.setText("PLEASE CHECK INTERNET CONNECT");
                            //Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }

                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*-------Show/Hidden Layout----------*/
    public void listview_show_hide() {
        try {
            bundle = this.getArguments();

            if (bundle.getString("dcr").equals("1")) {
                ll_dcr.setVisibility(View.VISIBLE);
            } else {
                ll_dcr.setVisibility(View.GONE);
            }


            /*if (bundle.getString("cme").equals("1")) {
                ll_cme.setVisibility(View.VISIBLE);
            } else {
                ll_cme.setVisibility(View.GONE);
            }*/


            if (bundle.getString("camp").equals("1")) {
                ll_camp.setVisibility(View.VISIBLE);
            } else {
                ll_camp.setVisibility(View.GONE);
            }


            if (bundle.getString("meeting").equals("1")) {
                ll_meeting.setVisibility(View.VISIBLE);
            } else {
                ll_meeting.setVisibility(View.GONE);
            }

            if (bundle.getString("lve").equals("1")) {
                ll_leave.setVisibility(View.VISIBLE);
            } else {
                ll_leave.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    ////////////////////////////
    /*public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(context, "landscape", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            // for example the width of a layout
            int width = 300;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            WebView childLayout = (WebView) findViewById(R.id.webview);
            childLayout.setLayoutParams(new LayoutParams(width, height));
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(context, "portrait", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }*/

}
