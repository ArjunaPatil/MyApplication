package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
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

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

public class popup_todays_objective_new_DialogFragment extends android.support.v4.app.DialogFragment {


    private Realm mRealm;
    RestService restService;
    ProgressDialog pDialog;
    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;

    String Object_id = "";
    TextView tv_date;
    EditText txt_objective;
    //Button btn_submit;
    TextView btn_submit;
    Bundle bundle;

    // Empty constructor required for DialogFragment
    public popup_todays_objective_new_DialogFragment() {
    }

    public static popup_todays_objective_new_DialogFragment newInstance(String title) {
        popup_todays_objective_new_DialogFragment frag = new popup_todays_objective_new_DialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(true);
        View view = inflater.inflate(R.layout.popup_todays_objective_new_dialogfragment, container);

        /*tv_date = (TextView) view.findViewById(R.id.txt_date);
        txt_objective = (EditText) view.findViewById(R.id.txt_objective);

        Button btn_save = (Button) view.findViewById(R.id.btn_save);
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
            super.onStart();

            bundle = this.getArguments();

            int versionCode = BuildConfig.VERSION_CODE;
            String versionName = BuildConfig.VERSION_NAME;
            ((DashBord_main) getActivity()).setActionBarTitle("CHEMIST CALL");
            loadevents();


            pDialog = new ProgressDialog(getContext());
            mRealm.init(getContext());
            mRealm = Realm.getDefaultInstance();
            mRealm.beginTransaction();
            mRealm.delete(POJO_objective.class);
            mRealm.commitTransaction();
            mRealm.close();

            datafull = false;
            limitstart = 0;

            loaddate();


            ImageButton btn_refresh_data = (ImageButton) getView().findViewById(R.id.btn_refresh_data);
            btn_refresh_data.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        mRealm.init(getContext());
                        mRealm = Realm.getDefaultInstance();
                        mRealm.beginTransaction();
                        mRealm.delete(POJO_objective.class);
                        mRealm.commitTransaction();
                        mRealm.close();

                        TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                        txt_loading.setVisibility(View.VISIBLE);
                        txt_loading.setText("Refreshing Data..");
                        datafull = false;
                        limitstart = 0;
                        loaddate();
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //loadchemist();


        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    public interface Today_Obj_after_save {
        void onFinish_Today_Obj_Save(String FlagOpr, String Today_Obj);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackResult(String FlagOpr, String Today_Obj) {

        //EditText username = (EditText) getView().findViewById(R.id.username);
        Today_Obj_after_save listener = (Today_Obj_after_save) getTargetFragment();
        //listener.onFinishSave(username.getText().toString());
        listener.onFinish_Today_Obj_Save(FlagOpr, Today_Obj);
        dismiss();


    }

    public void loadevents() {
        try {

            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.VISIBLE);
            txt_loading.setText("");

            tv_date = (TextView) getView().findViewById(R.id.txt_date);
            txt_objective = (EditText) getView().findViewById(R.id.txt_objectve);
            txt_objective.setVisibility(View.VISIBLE);

            //btn_submit = (Button) getView().findViewById(R.id.btn_add_objective);
            btn_submit = (TextView) getView().findViewById(R.id.btn_add_objective);
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String select_date = tv_date.getText().toString();
                        String objective = txt_objective.getText().toString();

                        if (select_date != null && objective != null) {
                            lock_check();
                        } else {
                            Toast.makeText(getContext(), "PLEASE Enter Valid Data...", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loaddate() {

        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            tv_date.setText(sdf.format(date));
            get_todays_objective(sdf.format(date));

        } catch (Exception e) {

        }

    }

    public void get_todays_objective(String dd) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String user_id = app_preferences.getString("name", "default");

            JSONArray jsonArray = new JSONArray();

            /*jsonArray.put("name");
            jsonArray.put("select_date");
            jsonArray.put("objective");*/


            jsonArray.put("modified_by");
            jsonArray.put("name");
            jsonArray.put("creation");
            jsonArray.put("modified");
            jsonArray.put("select_date");
            jsonArray.put("idx");
            jsonArray.put("objective");
            jsonArray.put("user");
            jsonArray.put("owner");
            jsonArray.put("docstatus");
            jsonArray.put("user_name");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();

            /*Filter1.put("Objective");
            Filter1.put("owner");
            Filter1.put("=");
            Filter1.put(emp_email_id.getText());*/

            Filter1.put("Objective");
            Filter1.put("user");
            Filter1.put("=");
            Filter1.put(user_id);


            Filter2.put("Objective");
            Filter2.put("select_date");
            Filter2.put("=");
            Filter2.put(dd);


            Filters.put(Filter1);
            Filters.put(Filter2);

            pDialog.show();
            btn_submit.setVisibility(View.GONE);
            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.VISIBLE);
            txt_loading.setText("Refreshing Data..");
            restService.getService().getObjective(sid, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        pDialog.hide();
                        TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                        txt_loading.setVisibility(View.GONE);
                        btn_submit.setVisibility(View.VISIBLE);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j22 = j1.getAsJsonArray("data");

                        if (j22.size() > 0) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<POJO_objective>>() {
                            }.getType();
                            List<POJO_objective> POJO = gson.fromJson(j22, type);

                            mRealm = Realm.getDefaultInstance();
                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(POJO);
                            mRealm.commitTransaction();
                            mRealm.close();

                            if (POJO.size() > 0) {

                                POJO_objective firstA = POJO.get(0);
                                Object_id = firstA.getName();
                                tv_date.setText(firstA.getSelect_date());

                                txt_objective.setVisibility(View.VISIBLE);
                                txt_objective.setText(firstA.getObjective());

                                /*TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                                txt_loading.setText("");*/

                                btn_submit.setText("SUBMITTED");
                                //String name = firstA.getName();


                                //Bind__data(name);
                            }
                        } else {
                            txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setText("No Objective Updated for Today");
                            txt_loading.setText("");
                        }
                    /*if (j22.size() > 0) {
                        JsonElement j2 = j22.get(0);

                        if (j2.getAsJsonObject().get("select_date") != null) {
                            tv_date.setText(j2.getAsJsonObject().get("select_date").getAsString());
                        }
                        if (j2.getAsJsonObject().get("objective") != null) {
                            txt_objective.setText(j2.getAsJsonObject().get("objective").getAsString());
                            btn_submit.setText("SBMITTED");
                            btn_submit.setEnabled(false);
                        }
                        else {
                            btn_submit.setText("SUBMIT");
                            btn_submit.setEnabled(true);
                        }
                    } else {
                        btn_submit.setText("SUBMIT");
                        btn_submit.setEnabled(true);
                    }*/
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        pDialog.hide();
                        btn_submit.setVisibility(View.GONE);

                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }


                        txt_objective.setVisibility(View.GONE);

                        if (msg.equals("403 FORBIDDEN")) {

                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
                            txt_loading.setText("ERROR..");

                            //Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            //getContext().startActivity(k);

                        } else if (msg.contains("139.59.63.181")) {

                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
                            txt_loading.setText("PLEASE CHECK INTERNET CONNECT");
                            //Toast.makeText(getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();

                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }

                        if (task_user != null) {
                            task_user.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception ex) {

            pDialog.hide();
            btn_submit.setVisibility(View.GONE);
            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
            txt_loading.setVisibility(View.VISIBLE);
            txt_loading.setText("Check Internet Connection and Try Again..");
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*public void Bind__data(String name) {
        try {
            Realm mRealm = Realm.getDefaultInstance();

            //final RealmResults<POJO_Employee> puppies = mRealm.where(POJO_Employee.class).equalTo("select_date",fromDateEtxt.getText().toString()).findAll();
            final POJO_objective result_query = mRealm.where(POJO_objective.class).equalTo("name", name).findFirst();

            //POJO_Employee POJO = result_query;

            //txt_id.setText(result_query.getName());
            tv_date.setText(result_query.getSelect_date());
            txt_objective.setText(result_query.getObjective());
            btn_submit.setText("SBMITTED");

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }*/

    public void save_todays_objective(POJO_objective obj) {
        try {

            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String user_id = app_preferences.getString("name", "default");
            obj.setUser(user_id);
            obj.setUser_name(app_preferences.getString("first_name", "default") + " " + app_preferences.getString("middle_name", "default") + " " + app_preferences.getString("last_name", "default"));

            if (Object_id.equals("")) {

                pDialog.show();
                restService.getService().Objective_insert(sid, obj, new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        try {
                            pDialog.hide();
                            // ListView lv = (ListView) findViewById(R.id.listView);
                        /*JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j22 = j1.getAsJsonArray("data");

                        if(j22.size()>0) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<POJO_objective>>() {
                            }.getType();
                            List<POJO_objective> POJO = gson.fromJson(j22, type);

                            mRealm = Realm.getDefaultInstance();
                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(POJO);
                            mRealm.commitTransaction();
                            mRealm.close();

                            if (POJO.size() > 0) {
                                POJO_objective firstA = POJO.get(0);
                                Object_id=firstA.getName();*/

                            if (bundle != null) {
                                if (bundle.get("nav_bar").equals("dash")) {
                                    sendBackResult("Y", "-");
                                }

                            } else {

                            }
                            /*}
                        }
                        else {
                            sendBackResult("Y", "-");
                        }*/
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            String msg = "";
                            if (error.getKind().toString().contains("HTTP")) {
                                msg = error.toString();
                            } else {
                                msg = "139.59.63.181";
                            }


                            if (msg.equals("403 FORBIDDEN")) {
                                if (bundle != null) {
                                    if (bundle.get("nav_bar").equals("dash")) {
                                        sendBackResult("N", "-");
                                    }

                                }
                                //Intent k = new Intent(getContext(), Login.class); //got ot login activity
                                //getContext().startActivity(k);

                            } else if (msg.contains("139.59.63.181")) {
                                if (bundle != null) {
                                    if (bundle.get("nav_bar").equals("dash")) {
                                        sendBackResult("N", "-");
                                    }

                                }

                                //Toast.makeText( getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();

                            }

                            if (task_app_evrsion != null) {
                                task_app_evrsion.cancel(true);
                            }

                            if (task_user != null) {
                                task_user.cancel(true);
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else if (Object_id != null && Object_id != "") {
                restService.getService().updateobjective(sid, obj, Object_id, new Callback<JsonElement>() {
                    @Override
                    public void success(JsonElement jsonElement, Response response) {
                        try {
                            // ListView lv = (ListView) findViewById(R.id.listView);
                        /*JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j22 = j1.getAsJsonArray("data");

                        if(j22.size()>0) {
                            Gson gson = new Gson();
                            Type type = new TypeToken<List<POJO_objective>>() {
                            }.getType();
                            List<POJO_objective> POJO = gson.fromJson(j22, type);

                            mRealm = Realm.getDefaultInstance();
                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(POJO);
                            mRealm.commitTransaction();
                            mRealm.close();

                            if (POJO.size() > 0) {
                                POJO_objective firstA = POJO.get(0);
                                Object_id=firstA.getName();*/
                            if (bundle != null) {
                                if (bundle.get("nav_bar").equals("dash")) {
                                    sendBackResult("Y", "-");
                                }

                            }
                            /*}
                        }
                        else {
                            sendBackResult("Y", "-");
                        }*/
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void failure(RetrofitError error) {
                        try {
                            String msg = "";
                            if (error.getKind().toString().contains("HTTP")) {
                                msg = error.toString();
                            } else {
                                msg = "139.59.63.181";
                            }


                            if (msg.equals("403 FORBIDDEN")) {
                                if (bundle != null) {
                                    if (bundle.get("nav_bar").equals("dash")) {
                                        sendBackResult("N", "-");
                                    }

                                }
                                //Intent k = new Intent(getContext(), Login.class); //got ot login activity
                                //getContext().startActivity(k);

                            } else if (msg.contains("139.59.63.181")) {
                                if (bundle != null) {
                                    if (bundle.get("nav_bar").equals("dash")) {
                                        sendBackResult("N", "-");
                                    }

                                }
                                //Toast.makeText( getContext(), "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();

                            }

                            if (task_app_evrsion != null) {
                                task_app_evrsion.cancel(true);
                            }

                            if (task_user != null) {
                                task_user.cancel(true);
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (Exception e) {

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

            restService.getService().getTransactionFormLockOrNot(sid, "'" + name + "'", "T_Obj", tv_date.getText().toString(), new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        String lock_flag = j2.get("lock_flag").getAsString();
                        String message = j2.get("message").getAsString();
                        if (lock_flag.equals("1")) {
                            save_objective();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();//"Oops !!! Locked Profile..."
                        }

                        /*JsonObject j1 = jsonElement.getAsJsonObject();
                        //JsonObject j2 = j1.getAsJsonObject("message");
                        //String lock_flag = j2.get("message").getAsString();
                        String lock_flag = j1.get("message").getAsString();
                        if (lock_flag == "1") {
                            save_objective();
                        } else {
                            pDialog.hide();
                            Toast.makeText(getContext(), "Oops !!! Locked Objective...", Toast.LENGTH_SHORT).show();
                        }*/


                    } catch (Exception ex) {
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

    private void save_objective() {
        try {

            /*This code Cut From button Click Event*/

            String select_date = tv_date.getText().toString();
            String objective = txt_objective.getText().toString();

            if (select_date != null && objective != null) {
                POJO_objective obj = new POJO_objective();
                obj.setSelect_date(select_date);
                obj.setObjective(objective);

                save_todays_objective(obj);
            } else {
                Toast.makeText(getContext(), "PLEASE Enter Valid Data...", Toast.LENGTH_SHORT).show();
            }
            //sendBackResult();

        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
