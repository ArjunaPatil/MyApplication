package com.example.vin.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
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

public class test_attch_doctor_FragmentDialog extends android.support.v4.app.DialogFragment {

    private Realm mRealm;
    RestService restService;
    Context context;
    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    public boolean bool_full_update = false;

    public POJO_Doctor_Master_S last_POJO;
    public adapter_test_attch_doctor_list_for_dialog adapter;

    Bundle bundle;
    String PatchID = "";
    String user_id = "";

    // Empty constructor required for DialogFragment
    public test_attch_doctor_FragmentDialog() {
    }

    public static test_attch_doctor_FragmentDialog newInstance(String title) {
        test_attch_doctor_FragmentDialog frag = new test_attch_doctor_FragmentDialog();
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
        View view = inflater.inflate(R.layout.test_att_doctor_fragment_dialog, container);

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

            bundle = this.getArguments();
            if (bundle != null) {
               /* String check_all_doc = "";
                check_all_doc = bundle.get("doc_all").toString();
                if(check_all_doc==null) {
                    check_all_doc="";
                }*/

               /* if (check_all_doc.toString().trim().equals("ALL")) {
                }*/

                PatchID = bundle.get("Patch_ID").toString();
                ListView lv = (ListView) getView().findViewById(R.id.listView);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                              @Override
                                              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                  try {
                                                      POJO_Doctor_Master_S clickedObj = (POJO_Doctor_Master_S) parent.getItemAtPosition(position);
                                                      sendBackTestDoctorResult(clickedObj.getName(), clickedObj.getDoctor_name());
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
                            mRealm.delete(POJO_Doctor_Master_S.class);
                            mRealm.commitTransaction();
                            mRealm.close();

                            TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                            txt_loading.setVisibility(View.VISIBLE);
                            txt_loading.setText("Refreshing Data..");
                            bool_full_update = false;
                            // full_update_calc();
                            datafull = false;
                            limitstart = 0;
                            Load_User();
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

                data_fetch();
                super.onStart();
            } else {
                Toast.makeText(getContext(), "Please Select ", Toast.LENGTH_SHORT).show();
            }
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

            RealmResults<POJO_Doctor_Master_S> result_query1 = mRealm.where(POJO_Doctor_Master_S.class).equalTo("active", 1).findAll().sort("doctor_name", Sort.ASCENDING);
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
            final RealmResults<POJO_Doctor_Master_S> result_query1 = mRealm.where(POJO_Doctor_Master_S.class).findAll().sort("doctor_name", Sort.ASCENDING);
            List<POJO_Doctor_Master_S> mList = result_query1;

            adapter = new adapter_test_attch_doctor_list_for_dialog(getContext(), R.layout.adapter_test_att_doctor_list_for_dialog, mList);

            ListView lv = (ListView) getView().findViewById(R.id.listView);

            lv.setAdapter(adapter);
            if (mList.size() == 0) {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("NO DOCTOR FOUND..");
            } else {
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

    public interface EditTestDoctorDialogListener {
        void onFinishTestDoctorDialog(String id, String fullname);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackTestDoctorResult(String id, String name) {
        try {
            // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
            EditTestDoctorDialogListener listener = (EditTestDoctorDialogListener) getTargetFragment();
            listener.onFinishTestDoctorDialog(id, name);
            dismiss();
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {

                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void full_update_calc() {
        try {
            if ((bool_full_update == false)) {

                RealmResults<POJO_Doctor_Master_S> result_query1 = mRealm.where(POJO_Doctor_Master_S.class).equalTo("active", 1).findAll().sort("modified", Sort.DESCENDING);
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
            //String emp = app_preferences.getString("name", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("doctor_name");
            jsonArray.put("patch");
            jsonArray.put("patch_name");
            jsonArray.put("active");
            jsonArray.put("inactive");
            jsonArray.put("campaign_book");
            jsonArray.put("modified");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();
            JSONArray Filter2 = new JSONArray();
            JSONArray Filter3 = new JSONArray();

            bundle = this.getArguments();
            if (bundle != null) {
                String check_all_doc = "";
                check_all_doc = bundle.get("doc_all").toString();
                if (check_all_doc == null) {
                    check_all_doc = "";
                }
                if (check_all_doc.toString().trim().equals("ALL")) {
                    Filter1.put("Doctor Master");
                    Filter1.put("user");
                    Filter1.put("=");
                    Filter1.put(PatchID);

                    Filter2.put("Doctor Master");
                    Filter2.put("active");
                    Filter2.put("=");
                    Filter2.put(1);

                    Filters.put(Filter1);
                    Filters.put(Filter2);
                } else if (check_all_doc.toString().trim().equals("ALLCAMP")) {
                    Filter1.put("Doctor Master");
                    Filter1.put("user");
                    Filter1.put("=");
                    Filter1.put(PatchID);

                    Filter2.put("Doctor Master");
                    Filter2.put("active");
                    Filter2.put("=");
                    Filter2.put(1);

                    Filter3.put("Doctor Master");
                    Filter3.put("campaign_book");
                    Filter3.put("=");
                    Filter3.put(1);

                    Filters.put(Filter1);
                    Filters.put(Filter2);
                    Filters.put(Filter3);
                } else {
                    Filter1.put("Doctor Master");
                    Filter1.put("patch");
                    Filter1.put("=");
                    Filter1.put(PatchID);


                    Filter2.put("Doctor Master");
                    Filter2.put("active");
                    Filter2.put("=");
                    Filter2.put(1);


                    Filters.put(Filter1);
                    Filters.put(Filter2);

                }
            }

            restService.getService().getDoctor_List_PatchWise(sid, "modified desc", limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Doctor_Master_S>>() {
                        }.getType();
                        List<POJO_Doctor_Master_S> POJO = gson.fromJson(j2, type);

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
                            for (POJO_Doctor_Master_S pp : POJO) {
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