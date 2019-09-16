package com.example.vin.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
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
import static io.realm.Case.INSENSITIVE;

public class user_list_hierarchy_FragmentDialog extends android.support.v4.app.DialogFragment {

    private EditText mEditText;

    Context context;
    private Realm mRealm;
    RestService restService;

    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    public boolean bool_full_update = false;

    public POJO_User_S last_POJO;
    public adapter_user_list_hierarchy_for_dialog adapter;

    Bundle bundle;
    public String ParameterStockiestForm = "";
    public String designation = "";
    public RealmResults<POJO_User_S> result_query1;//For hierarchy...

    public interface UserNameListener {
        void onFinishUserDialog(String user);
    }

    // Empty constructor required for DialogFragment
    public user_list_hierarchy_FragmentDialog() {
    }

    public static user_list_hierarchy_FragmentDialog newInstance(String title) {
        user_list_hierarchy_FragmentDialog frag = new user_list_hierarchy_FragmentDialog();
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
        View view = inflater.inflate(R.layout.fragment_user_list_hirarchy_dialog, container);

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
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = app_preferences.getString("designation", "default");
            bundle = this.getArguments();
            if (bundle != null) {
                ParameterStockiestForm = bundle.getString("stcokiest").toString();
            } else {
                ParameterStockiestForm = "";
            }

            ListView lv = (ListView) getView().findViewById(R.id.listView);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              try {
                                                  POJO_User_S clickedObj = (POJO_User_S) parent.getItemAtPosition(position);
                                                  if (ParameterStockiestForm.contains("img") == true) {
                                                      sendBackUserListHirarchyResult(clickedObj.getEmployee_code(), clickedObj.getFirst_name() + " " + clickedObj.getLast_name() + "(" + clickedObj.getDesignation() + ")");
                                                  } else {
                                                      sendBackUserListHirarchyResult(clickedObj.getName(), clickedObj.getFirst_name() + " " + clickedObj.getLast_name() + "(" + clickedObj.getDesignation() + ")");
                                                  }
                                              } catch (Exception ex) {
                                                  context = getActivity();
                                                  if (context != null) {
                                                      Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                                  }
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
                        if ((designation.equals("TBM")) || (designation.equals("KBM"))) {

                        } else {
                            Load_User();
                        }
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {

                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            TextView txt_cancel = (TextView) getView().findViewById(R.id.txt_cancel);
            txt_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    //Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
                }
            });


            TextView txt_all = (TextView) getView().findViewById(R.id.txt_all);
            View view_all = getView().findViewById(R.id.view_all);
            if (ParameterStockiestForm.contains("OBJ") == true) {
                txt_all.setVisibility(View.VISIBLE);
                view_all.setVisibility(View.VISIBLE);
            } else if (ParameterStockiestForm.contains("img") == true) {
                txt_all.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                txt_all.setText("    MY GALLERY");
                txt_all.setVisibility(View.VISIBLE);
                view_all.setVisibility(View.VISIBLE);
                txt_all.setTextColor(Color.parseColor("#FF4081"));
            } else {
                txt_all.setVisibility(View.GONE);
                view_all.setVisibility(View.GONE);
            }
            txt_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        sendBackUserListHirarchyResult("ALL", "ALL");
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

            if ((designation.equals("TBM")) || (designation.equals("KBM"))) {

            } else {
                data_fetch();
            }

            super.onStart();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void data_fetch() {
        try {
            mRealm = Realm.getDefaultInstance();

            RealmResults<POJO_User_S> result_query1 = mRealm.where(POJO_User_S.class).equalTo("enabled", 1).findAll().sort("first_name", Sort.ASCENDING);
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
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Bind_data_listview() {
        try {
            ListView listView = (ListView) getView().findViewById(R.id.listView);
            //////

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String user_id = app_preferences.getString("name", "default");
            String designation = app_preferences.getString("designation", "default");
            String EmployeeName = "";
            Realm mRealm = Realm.getDefaultInstance();
            if (ParameterStockiestForm.equals("OBJ") || ParameterStockiestForm.equals("img")) {
                if (designation.equals("ABM")) {
                    result_query1 = mRealm.where(POJO_User_S.class).equalTo("abm", user_id).contains("designation", "TBM", INSENSITIVE).findAll().sort("first_name", Sort.ASCENDING);
                    ;//.contains("enabled", 1, INSENSITIVE)
                } else if (designation.equals("RBM")) {
                    result_query1 = mRealm.where(POJO_User_S.class).equalTo("rbm", user_id).findAll().sort("first_name", Sort.ASCENDING);
                } else if (designation.equals("SM")) {
                    result_query1 = mRealm.where(POJO_User_S.class).equalTo("sm", user_id).findAll().sort("first_name", Sort.ASCENDING);
                } else if (designation.equals("ZBM")) {
                    result_query1 = mRealm.where(POJO_User_S.class).equalTo("zbm", user_id).findAll().sort("first_name", Sort.ASCENDING);
                } else if (designation.equals("CRM")) {
                    result_query1 = mRealm.where(POJO_User_S.class).equalTo("crm", user_id).findAll().sort("first_name", Sort.ASCENDING);
                } else if (designation.equals("NBM")) {
                    result_query1 = mRealm.where(POJO_User_S.class).equalTo("nbm", user_id).findAll().sort("first_name", Sort.ASCENDING);
                } else if ((designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager") || (designation.equals("Administrator")))) {
                    result_query1 = mRealm.where(POJO_User_S.class).findAll().sort("first_name", Sort.ASCENDING);
                    //String emp = "EMP/712263";  //vinayak code-
                    //employee_filter = mRealm.where(POJO_User.class).equalTo("enabled", 1).contains("sm", user_id, INSENSITIVE).contains("designation", "TBM", INSENSITIVE).findAll();
                } else if (designation.equals("TBM")) {
                    //ll_spinner_tbm_patches.setVisibility(View.GONE);
                }
            } else {
                if (designation.equals("ABM")) {
                    result_query1 = mRealm.where(POJO_User_S.class).equalTo("abm", user_id).findAll().sort("first_name", Sort.ASCENDING);//.contains("enabled", 1, INSENSITIVE)
                } else if (designation.equals("RBM")) {
                    result_query1 = mRealm.where(POJO_User_S.class).equalTo("rbm", user_id).contains("designation", "TBM", INSENSITIVE).findAll().sort("first_name", Sort.ASCENDING);
                } else if (designation.equals("SM")) {
                    result_query1 = mRealm.where(POJO_User_S.class).equalTo("sm", user_id).contains("designation", "TBM", INSENSITIVE).findAll().sort("first_name", Sort.ASCENDING);
                } else if (designation.equals("ZBM")) {
                    result_query1 = mRealm.where(POJO_User_S.class).equalTo("zbm", user_id).contains("designation", "TBM", INSENSITIVE).findAll().sort("first_name", Sort.ASCENDING);
                } else if (designation.equals("CRM")) {
                    result_query1 = mRealm.where(POJO_User_S.class).equalTo("crm", user_id).contains("designation", "TBM", INSENSITIVE).findAll().sort("first_name", Sort.ASCENDING);
                } else if (designation.equals("NBM")) {
                    result_query1 = mRealm.where(POJO_User_S.class).equalTo("nbm", user_id).contains("designation", "TBM", INSENSITIVE).findAll().sort("first_name", Sort.ASCENDING);
                } else if ((designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager") || (designation.equals("Administrator")))) {
                    result_query1 = mRealm.where(POJO_User_S.class).contains("designation", "TBM", INSENSITIVE).findAll().sort("first_name", Sort.ASCENDING);
                    //String emp = "EMP/712263";  //vinayak code-
                    //employee_filter = mRealm.where(POJO_User.class).equalTo("enabled", 1).contains("sm", user_id, INSENSITIVE).contains("designation", "TBM", INSENSITIVE).findAll();
                } else if (designation.equals("TBM")) {
                    //ll_spinner_tbm_patches.setVisibility(View.GONE);
                }
            }
            ////
            //final RealmResults<POJO_User_S> result_query1 = mRealm.where(POJO_User_S.class).findAll().sort("modified", Sort.DESCENDING);
            List<POJO_User_S> mList = result_query1;

            adapter = new adapter_user_list_hierarchy_for_dialog(getContext(), R.layout.adapter_user_list_hirarchy_for_dialog, mList);

            ListView lv = (ListView) getView().findViewById(R.id.listView);

            lv.setAdapter(adapter);

            TextView txt_all = (TextView) getView().findViewById(R.id.txt_all);
            View view_all = getView().findViewById(R.id.view_all);
            if (mList.size() == 0) {
                txt_all.setVisibility(View.GONE);
                view_all.setVisibility(View.GONE);
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("NO EMPLOYEE FOUND..");
            } else {
                //txt_all.setVisibility(View.VISIBLE);
                //view_all.setVisibility(View.VISIBLE);
                // txt_all.setVisibility(View.GONE);
                //view_all.setVisibility(View.GONE);
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.GONE);
                txt_loading.setText("Refreshing Data..");
            }
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface EditUserListHirarchyDialogListener {
        void onFinishUserListHirarchyDialog(String id, String fullname);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackUserListHirarchyResult(String id, String name) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditUserListHirarchyDialogListener listener = (EditUserListHirarchyDialogListener) getTargetFragment();
        listener.onFinishUserListHirarchyDialog(id, name);
        dismiss();

/*     UserNameListener listener=(UserNameListener)getTargetFragment();
        listener.onFinishUserDialog("hiii");*/

    }

    public void full_update_calc() {
        try {
            if ((bool_full_update == false)) {

                RealmResults<POJO_User_S> result_query1 = mRealm.where(POJO_User_S.class).equalTo("enabled", 1).findAll().sort("first_name", Sort.ASCENDING);
                if (result_query1.size() > 0) {
                    //last_POJO = result_query1.first();27/07/2017
                } else {
                    bool_full_update = true;
                }
            }
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Load_User() {
        try {

            //Thread.sleep(sleep_wait);
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            final String emp = app_preferences.getString("name", "default");
            String Designation = app_preferences.getString("designation", "default");
            String Branch = app_preferences.getString("branch", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("employee_code");
            jsonArray.put("first_name");
            jsonArray.put("last_name");
            jsonArray.put("designation");
            jsonArray.put("abm");
            jsonArray.put("rbm");
            jsonArray.put("zbm");
            jsonArray.put("sm");
            jsonArray.put("crm");
            jsonArray.put("nbm");
            jsonArray.put("enabled");
            jsonArray.put("modified");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();
            JSONArray Filter3 = new JSONArray();


            /*Filter2.put("User");
            Filter2.put("abm");//rbm,sm,name,first_name,last_name
            Filter2.put("=");
            Filter2.put(1);*/


            /*Filter1.put("User");
            Filter1.put("enabled");
            Filter1.put("=");
            Filter1.put(1);
            Filter2.put("User");
            Filter2.put("nbm");//rbm,sm,name,first_name,last_name
            Filter2.put("=");
            Filter2.put(emp);
            Filters.put(Filter1);
            Filters.put(Filter2);*/

            if ((Designation.equals("TBM")) || (Designation.equals("KBM"))) {

            } else if (Designation.equals("ABM")) {
                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);
                Filter2.put("User");
                Filter2.put("abm");//rbm,sm,name,first_name,last_name
                Filter2.put("=");
                Filter2.put(emp);
                Filters.put(Filter1);
                Filters.put(Filter2);
            } else if (Designation.equals("RBM")) {
                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);
                Filter2.put("User");
                Filter2.put("rbm");//rbm,sm,name,first_name,last_name
                Filter2.put("=");
                Filter2.put(emp);
                Filters.put(Filter1);
                Filters.put(Filter2);
            } else if (Designation.equals("CRM")) {
                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);
                Filter2.put("User");
                Filter2.put("crm");//name,first_name,last_name
                Filter2.put("=");
                Filter2.put(emp);
                Filters.put(Filter1);
                Filters.put(Filter2);
            } else if (Designation.equals("ZBM")) {
                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);
                Filter2.put("User");
                Filter2.put("zbm");//name,first_name,last_name
                Filter2.put("=");
                Filter2.put(emp);
                Filters.put(Filter1);
                Filters.put(Filter2);
            } else if (Designation.equals("SM")) {
                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);
                Filter2.put("User");
                Filter2.put("sm");//name,first_name,last_name
                Filter2.put("=");
                Filter2.put(emp);
                Filters.put(Filter1);
                Filters.put(Filter2);
            } else if (Designation.equals("NBM")) {
                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);
                Filter2.put("User");
                Filter2.put("nbm");//rbm,sm,name,first_name,last_name
                Filter2.put("=");
                Filter2.put(emp);
                Filters.put(Filter1);
                Filters.put(Filter2);
            } else if (Designation.equals("Head of Marketing and Sales")) {
                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);
                Filter2.put("User");
                Filter2.put("branch");//rbm,sm,name,first_name,last_name
                Filter2.put("=");
                Filter2.put(Branch);
                Filters.put(Filter1);
                Filters.put(Filter2);
            } else if ((Designation.equals("HR Manager")) || (Designation.equals("Administrator"))) {
                Filter1.put("User");
                Filter1.put("enabled");
                Filter1.put("=");
                Filter1.put(1);
                Filters.put(Filter1);
            }

            if (ParameterStockiestForm.equals("Y")) {
                Filter3.put("User");
                Filter3.put("designation");//name,first_name,last_name
                Filter3.put("=");
                Filter3.put("Stockiest");
                Filters.put(Filter3);
            }


            restService.getService().getUser_S(sid, "modified desc", limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_User_S>>() {
                        }.getType();

                        List<POJO_User_S> POJO = gson.fromJson(j2, type);
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
                        context = getActivity();
                        if (context != null) {

                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
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

                            //SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());    //status make 0 so checkoffline cannot work
                            //SharedPreferences.Editor editor = app_preferences.edit();
                            //editor.putString("status", "0");
                            //editor.commit();

                            //Intent k = new Intent(getContext(), Login.class); //got ot login activity
                            //getContext().startActivity(k);

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

}