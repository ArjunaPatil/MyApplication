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

public class stockist_list_to_top_hierarchy_FragmentDialog extends android.support.v4.app.DialogFragment {

    private Realm mRealm;
    RestService restService;

    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    public boolean bool_full_update = false;

    public POJO_Stockist_List last_POJO;
    //public adapter_stockist_list_to_top_hierarchy_for_dialog adapter;
    public adapter_stockist_list_to_top_hierarchy_for_dialog customAdapter;
    Bundle bundle;
    public String ParameterStockiestForm = "";
    public String designation = "";
    public RealmResults<POJO_Stockist_List> result_query1;//For hierarchy...

    public interface UserNameListener {
        void onFinishUserDialog(String user);
    }

    // Empty constructor required for DialogFragment
    public stockist_list_to_top_hierarchy_FragmentDialog() {
    }

    public static stockist_list_to_top_hierarchy_FragmentDialog newInstance(String title) {

        stockist_list_to_top_hierarchy_FragmentDialog frag = new stockist_list_to_top_hierarchy_FragmentDialog();
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
        View view = inflater.inflate(R.layout.stockist_list_to_top_hierarchy_fragmentdialog, container);

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

            //final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            //designation = app_preferences.getString("designation", "default");

            bundle = this.getArguments();
            if (bundle != null) {
                ParameterStockiestForm = bundle.getString("stockist").toString();
            } else {
                ParameterStockiestForm = "";
            }

            ListView lv = (ListView) getView().findViewById(R.id.listView);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              try {
                                                  POJO_Stockist_List clickedObj = (POJO_Stockist_List) parent.getItemAtPosition(position);
                                                  sendBackStockistListHirarchyResult(clickedObj.getName(), clickedObj.getFull_name() + "(" + clickedObj.getTerritory() + ")");
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
                        mRealm.delete(POJO_Stockist_List.class);
                        mRealm.commitTransaction();
                        mRealm.close();

                        TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                        txt_loading.setVisibility(View.VISIBLE);
                        txt_loading.setText("Refreshing Data..");
                        ListView lv = (ListView) getView().findViewById(R.id.listView);
                        lv.setVisibility(View.GONE);
                        bool_full_update = false;
                        //full_update_calc(); arjun 09/07/2018
                        datafull = false;
                        limitstart = 0;
                        /*if ((designation.equals("TBM")) || (designation.equals("KBM"))) {
                        } else {*/
                        Load_Stockist_List();
                       /* }*/
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
                        /*if (ParameterStockiestForm.toString().trim().equals("top_down_jfw") == true) {
                            sendBackStockistListHirarchyResult("NONE", "NONE");
                        } else {*/
                        dismiss();
                        /*}*/
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {
                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


            /*TextView txt_all = (TextView) getView().findViewById(R.id.txt_all);
            txt_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        sendBackUserListHirarchyResult("ALL", "ALL", "DOC");
                    } catch (Exception ex) {
                        context = getActivity();
                        if (context != null) {

                            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            txt_all.setText("ME");

            if ((designation.equals("TBM")) || (designation.equals("KBM"))) {
                if (ParameterStockiestForm.toString().trim().equals("top_down_jfw") == true) {
                    Bind_data_listview_top_and_bottom();
                }
            } else {
                if (ParameterStockiestForm.toString().trim().equals("top_down_jfw") == true) {
                    Bind_data_listview_top_and_bottom();
                } else {
                    data_fetch();
                }
            }
            TextView lbl_your_name = (TextView) getView().findViewById(R.id.lbl_your_name);

            if (ParameterStockiestForm.toString().trim().equals("top_down_jfw") == true) {

                lbl_your_name.setText("SELECT JFW WITH");
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.GONE);
                btn_refresh_data.setVisibility(View.GONE);
            } else {
            }*/

            data_fetch();

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

            RealmResults<POJO_Stockist_List> result_query1 = mRealm.where(POJO_Stockist_List.class).findAll().sort("name", Sort.ASCENDING);
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
                //full_update_calc(); arjun 09/07/2018
                limitstart = 0;
                datafull = false;
                Load_Stockist_List();
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
            final RealmResults<POJO_Stockist_List> result_query1 = mRealm.where(POJO_Stockist_List.class).findAll().sort("name", Sort.DESCENDING);
            List<POJO_Stockist_List> POJO = result_query1;

            if (POJO.size() == 0) {
                lv.setVisibility(View.GONE);
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("NO RECORD FOUND...");
            } else {
                lv.setVisibility(View.VISIBLE);
                customAdapter = new adapter_stockist_list_to_top_hierarchy_for_dialog(getContext(), R.layout.adapter_stockist_list_to_top_hierarchy_for_dialog, POJO);
                lv.setAdapter(customAdapter);

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
            final RealmResults<POJO_Stockist_List> result_query1 = mRealm.where(POJO_Stockist_List.class).findAll().sort("name", Sort.DESCENDING);
            List<POJO_Stockist_List> POJO = result_query1;
            customAdapter = new adapter_stockist_list_to_top_hierarchy_for_dialog(getContext(), R.layout.adapter_stockist_list_to_top_hierarchy_for_dialog, POJO);
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

    public interface EditStockistListHirarchyDialogListener {
        void onFinishStockistListHirarchyDialog(String id, String fullname);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackStockistListHirarchyResult(String id, String name) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditStockistListHirarchyDialogListener listener = (EditStockistListHirarchyDialogListener) getTargetFragment();
        listener.onFinishStockistListHirarchyDialog(id, name);
        dismiss();

    }

    public void full_update_calc() {
        try {
            if ((bool_full_update == false)) {

                RealmResults<POJO_Stockist_List> result_query1 = mRealm.where(POJO_Stockist_List.class).findAll().sort("name", Sort.ASCENDING);
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

    public void Load_Stockist_List() {
        try {

            //Thread.sleep(sleep_wait);
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            final String emp = "'" + app_preferences.getString("name", "default") + "'";
            String designation = app_preferences.getString("designation", "default");
            String Branch = app_preferences.getString("branch", "default");

            //'amolrmohite@gmail.com'&designation=RBM&limit=10&offset=0 //"'amolrmohite@gmail.com'", "RBM", 10, 0
            restService.getService().getStockist_list_for_top_hierarchy(sid, emp, designation, pagesize, limitstart, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("message");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Stockist_List>>() {
                        }.getType();

                        List<POJO_Stockist_List> POJO = gson.fromJson(j2, type);
                        if (POJO != null) {
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
                                for (POJO_Stockist_List pp : POJO) {
                                    if (last_POJO == null) {
                                        datafull = false;
                                    } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getTerritory().equals(last_POJO.getTerritory()))) {
                                        datafull = true;
                                        Bind_data_listview();
                                    }
                                }
                            }

                            if (datafull == false) {
                                Load_Stockist_List();
                            }
                        } else {
                            datafull = true;
                            Bind_data_listview();
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