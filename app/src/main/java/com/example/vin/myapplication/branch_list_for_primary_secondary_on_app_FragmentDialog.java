package com.example.vin.myapplication;

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
import static com.example.vin.myapplication.MoviesAdapter.context;

public class branch_list_for_primary_secondary_on_app_FragmentDialog extends android.support.v4.app.DialogFragment {
    private Realm mRealm;
    RestService restService;

    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    public boolean bool_full_update = false;

    public POJO_Branch last_POJO;
    public adapter_branch_list_primary_secondary_for_dialog adapter;
    public adapter_branch_list_primary_secondary_for_dialog customAdapter;
    Bundle bundle;
    public String ParameterStockiestForm = "";
    public String designation = "";
    public RealmResults<POJO_Branch> result_query1;//For hierarchy...

    // Empty constructor required for DialogFragment
    public branch_list_for_primary_secondary_on_app_FragmentDialog() {
    }

    public static branch_list_for_primary_secondary_on_app_FragmentDialog newInstance(String title) {

        branch_list_for_primary_secondary_on_app_FragmentDialog frag = new branch_list_for_primary_secondary_on_app_FragmentDialog();
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
        View view = inflater.inflate(R.layout.branch_list_for_primary_secondary_on_app_fragmentdialog, container);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            int width = getContext().getResources().getDisplayMetrics().widthPixels;
            TextView txt_test = (TextView) getView().findViewById(R.id.txt_test);
            txt_test.setWidth(width);
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
            super.onStart();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            designation = app_preferences.getString("designation", "default");
            bundle = this.getArguments();
            if (bundle != null) {
                ParameterStockiestForm = bundle.getString("branch").toString();
            } else {
                ParameterStockiestForm = "";
            }

            ListView lv = (ListView) getView().findViewById(R.id.listView);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              try {
                                                  POJO_Branch clickedObj = (POJO_Branch) parent.getItemAtPosition(position);
                                                  sendBackBranchListHirarchyResult(clickedObj.getName(), clickedObj.getBranch());
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
                        mRealm.delete(POJO_Branch.class);
                        mRealm.commitTransaction();
                        mRealm.close();

                        TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                        txt_loading.setVisibility(View.VISIBLE);
                        txt_loading.setText("Refreshing Data..");
                        ListView lv = (ListView) getView().findViewById(R.id.listView);
                        lv.setVisibility(View.GONE);
                        bool_full_update = false;
                        full_update_calc();
                        datafull = false;
                        limitstart = 0;
                        Load_Branch();

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
                    try {
                        if (ParameterStockiestForm.toString().trim().equals("top_down_jfw") == true) {
                            sendBackBranchListHirarchyResult("NONE", "NONE");
                        } else {
                            dismiss();
                        }

                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

           /* if ((designation.equals("TBM")) || (designation.equals("KBM"))) {
                if (ParameterStockiestForm.toString().trim().equals("top_down_jfw") == true) {
                    Bind_data_listview_top_and_bottom();
                }
            } else {
                if (ParameterStockiestForm.toString().trim().equals("top_down_jfw") == true) {
                    Bind_data_listview_top_and_bottom();
                } else {
                    data_fetch();
                }
            }*/
            if (ParameterStockiestForm.toString().trim().equals("branch") == true) {
                data_fetch();
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

            RealmResults<POJO_Branch> result_query1 = mRealm.where(POJO_Branch.class).equalTo("show_on_app", 1).findAll().sort("creation", Sort.ASCENDING);
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
                Load_Branch();
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

            if (mRealm == null) {
                mRealm = Realm.getDefaultInstance();
            }
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            result_query1 = mRealm.where(POJO_Branch.class).equalTo("show_on_app", 1).findAll().sort("creation", Sort.ASCENDING);
            List<POJO_Branch> mList = result_query1;

            if (mList.size() == 0) {
                lv.setVisibility(View.GONE);
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("NO RECORD FOUND...");
            } else {
                adapter = new adapter_branch_list_primary_secondary_for_dialog(getContext(), R.layout.adapter_branch_list_primary_secondary_for_dialog, mList);
                lv.setVisibility(View.VISIBLE);
                lv.setAdapter(adapter);

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

    public void Bind_data_listview_top_and_bottom() {
        try {
            if (mRealm == null) {
                mRealm = Realm.getDefaultInstance();
            }
            ListView lv = (ListView) getView().findViewById(R.id.listView);
            final RealmResults<POJO_Branch> result_query1 = mRealm.where(POJO_Branch.class).equalTo("show_on_app", 1).findAll().sort("creation", Sort.ASCENDING);
            List<POJO_Branch> POJO = result_query1;
            // ((DashBord_main) getActivity()).setActionBarTitle("EMPLOYEE LIST (" + POJO.size() + ")");
            customAdapter = new adapter_branch_list_primary_secondary_for_dialog(getContext(), R.layout.adapter_branch_list_primary_secondary_for_dialog, POJO);
            lv.setAdapter(customAdapter);
            if (POJO.size() == 0) {
                Toast.makeText(getContext(), "NO RECORD FOUND...", Toast.LENGTH_SHORT).show();
            } else {
            }
        } catch (Exception ex) {
            context = getActivity();
            if (context != null) {
                Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public interface EditBranchListHirarchyDialogListener {
        void onFinishBranchListHirarchyDialog(String id, String fullname);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackBranchListHirarchyResult(String id, String name) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditBranchListHirarchyDialogListener listener = (EditBranchListHirarchyDialogListener) getTargetFragment();
        listener.onFinishBranchListHirarchyDialog(id, name);
        dismiss();

/*     UserNameListener listener=(UserNameListener)getTargetFragment();
        listener.onFinishUserDialog("hiii");*/

    }

    public void full_update_calc() {
        try {
            if ((bool_full_update == false)) {
                RealmResults<POJO_Branch> result_query1 = mRealm.where(POJO_Branch.class).equalTo("show_on_app", 1).findAll().sort("creation", Sort.ASCENDING);
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

    public void Load_Branch() {
        try {

            //Thread.sleep(sleep_wait);
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("branch");
            jsonArray.put("show_on_app");
            jsonArray.put("modified_by");
            jsonArray.put("creation");
            jsonArray.put("modified");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();

            Filter1.put("Branch");
            Filter1.put("show_on_app");
            Filter1.put("=");
            Filter1.put(1);
            Filters.put(Filter1);


            restService.getService().getBranch_list(sid, "modified desc", limitstart, pagesize, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Branch>>() {
                        }.getType();

                        List<POJO_Branch> POJO = gson.fromJson(j2, type);
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
                            for (POJO_Branch pp : POJO) {
                                if (last_POJO == null) {
                                    datafull = false;
                                } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                    datafull = true;
                                    Bind_data_listview();
                                }
                            }
                        }

                        if (datafull == false) {
                            Load_Branch();
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