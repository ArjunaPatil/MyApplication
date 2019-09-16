package com.example.vin.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;
import static com.example.vin.myapplication.DashBord_main.task_user;

public class headquarter_list_hierarchy_FragmentDialog extends android.support.v4.app.DialogFragment {

    private EditText mEditText;


    private Realm mRealm;
    RestService restService;

    int limitstart = 0;
    int pagesize = 10;
    boolean datafull = false;
    public boolean bool_full_update = false;

    public POJO_Territory_S last_POJO;
    public adapter_headquarter_list_hierarchy_for_dialog adapter;

    Bundle bundle;
    public String ParameterStockiestForm = "";
    public String designation = "";
    public RealmResults<POJO_Territory_S> result_query1;//For hierarchy...

    // Empty constructor required for DialogFragment
    public headquarter_list_hierarchy_FragmentDialog() {
    }

    public static headquarter_list_hierarchy_FragmentDialog newInstance(String title) {
        headquarter_list_hierarchy_FragmentDialog frag = new headquarter_list_hierarchy_FragmentDialog();
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
        View view = inflater.inflate(R.layout.fragment_hqwise_stockiest_list_hirarchy_dialog, container);

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

            //bundle = this.getArguments();
//        if (bundle != null) {
//            ParameterStockiestForm=bundle.getString("stcokiest").toString();
//        }
//        else
//        {
//            ParameterStockiestForm="";
//        }

            ListView lv = (ListView) getView().findViewById(R.id.listView);


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              try {
                                                  POJO_Territory_S clickedObj = (POJO_Territory_S) parent.getItemAtPosition(position);
                                                  sendBackHeadquarterListHirarchyResult(clickedObj.getHeadquarter_id(), clickedObj.getHeadquarter_name());
                                              } catch (Exception ex) {
                                                  Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                        mRealm.delete(POJO_Territory_S.class);
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
                            Load_Headquarter();
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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

            if ((designation.equals("TBM")) || (designation.equals("KBM"))) {

            } else {
                data_fetch();
            }

            super.onStart();
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void data_fetch() {
        try {
            mRealm = Realm.getDefaultInstance();

            RealmResults<POJO_Territory_S> result_query1 = mRealm.where(POJO_Territory_S.class).findAll();//.sort("modified", Sort.DESCENDING);
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
                Load_Headquarter();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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

            result_query1 = mRealm.where(POJO_Territory_S.class).findAll();//.sort("modified", Sort.DESCENDING);
        /*if (designation.equals("ABM")) {
            result_query1 = mRealm.where(POJO_Territory_S.class).equalTo("abm", user_id).findAll();//.contains("enabled", 1, INSENSITIVE)

        } else if (designation.equals("RBM")) {
            result_query1 = mRealm.where(POJO_Territory_S.class).equalTo("rbm", user_id).contains("designation", "TBM", INSENSITIVE).findAll();

        } else if (designation.equals("SM")) {
            result_query1 = mRealm.where(POJO_Territory_S.class).equalTo("sm", user_id).contains("designation", "TBM", INSENSITIVE).findAll();

        } else if (designation.equals("ZBM")) {
            result_query1 = mRealm.where(POJO_Territory_S.class).equalTo("zbm", user_id).contains("designation", "TBM", INSENSITIVE).findAll();

        } else if (designation.equals("CRM") ||designation.equals("NBM") || (designation.equals("Head of Marketing and Sales")) || (designation.equals("HR Manager"))) {
            result_query1 = mRealm.where(POJO_Territory_S.class).findAll().sort("modified", Sort.DESCENDING);

        } else if (designation.equals("TBM")) {
        }*/

            ////
            //final RealmResults<POJO_Territory_S> result_query1 = mRealm.where(POJO_Territory_S.class).findAll().sort("modified", Sort.DESCENDING);
            List<POJO_Territory_S> mList = result_query1;

            /*if (mList.size() > 0) {
                POJO_Territory_S ss = new POJO_Territory_S();

                ss.setHeadquarter_id("ALL");
                ss.setHeadquarter_name("ALL");
                ss.setHeadquarter_parent("ALL");
                List<POJO_Territory_S> dd = new ArrayList<POJO_Territory_S>();
                List<POJO_Territory_S> gg = new ArrayList<POJO_Territory_S>();
                dd.add(ss);

                gg.addAll(dd);
                gg.addAll(mList);

                adapter = new adapter_headquarter_list_hierarchy_for_dialog(getContext(), R.layout.adapter_user_list_hirarchy_for_dialog, gg);
                ListView lv = (ListView) getView().findViewById(R.id.listView);
                lv.setAdapter(adapter);

            }*/


            adapter = new adapter_headquarter_list_hierarchy_for_dialog(getContext(), R.layout.adapter_user_list_hirarchy_for_dialog, mList);

            ListView lv = (ListView) getView().findViewById(R.id.listView);

            lv.setAdapter(adapter);
            if (mList.size() == 0) {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.VISIBLE);
                txt_loading.setText("NO HeadQuarter FOUND..");
            } else {
                TextView txt_loading = (TextView) getView().findViewById(R.id.txt_loading);
                txt_loading.setVisibility(View.GONE);
                txt_loading.setText("Refreshing Data..");
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public interface EditHeadquarterListHirarchyDialogListener {
        void onFinishHeadquarterListHirarchyDialog(String id, String headquartername);
    }

    // Call this method to send the data back to the parent fragment
    public void sendBackHeadquarterListHirarchyResult(String id, String name) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditHeadquarterListHirarchyDialogListener listener = (EditHeadquarterListHirarchyDialogListener) getTargetFragment();
        listener.onFinishHeadquarterListHirarchyDialog(id, name);
        dismiss();

/*     UserNameListener listener=(UserNameListener)getTargetFragment();
        listener.onFinishUserDialog("hiii");*/

    }

    public void full_update_calc() {
        try {


            if ((bool_full_update == false)) {

                RealmResults<POJO_Territory_S> result_query1 = mRealm.where(POJO_Territory_S.class).findAll();//.sort("modified", Sort.DESCENDING);
                if (result_query1.size() > 0) {
                    //last_POJO = result_query1.first();27/07/2017
                } else {
                    bool_full_update = true;
                }

            }

        } catch (Exception ex) {
            String exx = ex.getMessage();
            exx = ex.getMessage();
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*Original Method*/
    public void Load_Headquarter() {
        try {

            //Thread.sleep(sleep_wait);
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            String territory = "";/*app_preferences.getString("name", "default");*/
            String Designation = app_preferences.getString("designation", "default");

            /*JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("modified_by");
            jsonArray.put("parent");
            jsonArray.put("lft");
            jsonArray.put("is_group");
            jsonArray.put("modified");
            jsonArray.put("territory_name");
            jsonArray.put("rgt");
            jsonArray.put("old_parent");
            jsonArray.put("docstatus");
            jsonArray.put("parent_territory");*/

            /*JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();*/


            /*comment Filter2.put("User");
            Filter2.put("abm");//rbm,sm,name,first_name,last_name
            Filter2.put("=");
            Filter2.put(1);*/



            /*comment if ((Designation.equals("TBM")) || (Designation.equals("KBM"))) {
                String hq = app_preferences.getString("headquarter", "default");
                Filter1.put("Territory");
                Filter1.put("territory_name");
                Filter1.put("=");
                Filter1.put(hq);

            } else*/

            /*if (Designation.equals("ABM")) {
                String area = app_preferences.getString("Area", "default");
                Filter1.put("Territory");
                Filter1.put("parent_territory");
                Filter1.put("=");
                Filter1.put(area);
            } else if (Designation.equals("RBM")) {
                String rigion = app_preferences.getString("rigion", "default");
                Filter1.put("Territory");
                Filter1.put("parent_territory");
                Filter1.put("=");
                Filter1.put(rigion);
            } else if (Designation.equals("ZBM")) {
                String zone = app_preferences.getString("zone", "default");
                Filter1.put("Territory");
                Filter1.put("parent_territory");
                Filter1.put("=");
                Filter1.put(zone);
            } else if (Designation.equals("SM")) {
                String area = app_preferences.getString("Area", "default");
                Filter1.put("Territory");
                Filter1.put("parent_territory");
                Filter1.put("=");
                Filter1.put(area);
            } else if (Designation.equals("CRM")) {
                String area = app_preferences.getString("Area", "default");
                Filter1.put("Territory");
                Filter1.put("parent_territory");
                Filter1.put("=");
                Filter1.put(area);
            } else if ((Designation.equals("NBM")) || (Designation.equals("Head of Marketing and Sales")) || (Designation.equals("HR Manager"))) {
                Filter1.put("User");
                Filter1.put("docstatus");
                Filter1.put("=");
                Filter1.put(0);
                Filters.put(Filter1);
            }*/

            /*if ((Designation.equals("TBM")) || (Designation.equals("KBM"))) {
                territory = app_preferences.getString("headquarter", "default");
            } else */

            if (Designation.equals("ABM")) {
                territory = app_preferences.getString("area", "default");
            } else if (Designation.equals("RBM")) {
                territory = app_preferences.getString("region", "default");
            } else if (Designation.equals("ZBM")) {
                territory = app_preferences.getString("zone", "default");
            } else if (Designation.equals("SM")) {
                territory = app_preferences.getString("zone", "default");
            } else if (Designation.equals("CRM")) {
                territory = app_preferences.getString("headquarter", "default");
            } else if ((Designation.equals("NBM")) || (Designation.equals("Head of Marketing and Sales")) || (Designation.equals("HR Manager")) || (Designation.equals("Administrator"))) {
                territory = "All Territories";
            }


            restService.getService().getTerritory_Method(sid, "'" + territory + "'", Designation, pagesize, limitstart, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("message");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_Territory_S>>() {
                        }.getType();

                        List<POJO_Territory_S> POJO = new ArrayList<POJO_Territory_S>();
                        List<POJO_Territory_S> POJO1 = new ArrayList<POJO_Territory_S>();
                        List<POJO_Territory_S> POJO2 = new ArrayList<POJO_Territory_S>();
                        if (limitstart < 1 && j2.size() > 1) {
                            POJO_Territory_S ss = new POJO_Territory_S();

                            ss.setHeadquarter_id("ALL");
                            ss.setHeadquarter_name("ALL");
                            ss.setHeadquarter_parent("ALL");
                            POJO1.add(ss);
                            POJO.addAll(POJO1);
                        }
                        POJO2 = gson.fromJson(j2, type);

                        //////////////
                        if (POJO2 == null) {
                            datafull = true;
                            Bind_data_listview();
                        } else {
                            POJO.addAll(POJO2);
                            limitstart = limitstart + pagesize;
                            mRealm = Realm.getDefaultInstance();
                            mRealm.beginTransaction();
                            mRealm.copyToRealmOrUpdate(POJO);
                            mRealm.commitTransaction();
                            mRealm.close();

                            if (bool_full_update == false) {
                                for (POJO_Territory_S pp : POJO) {
                                    if (last_POJO == null) {
                                        datafull = false;
                                    } else if ((pp.getHeadquarter_id().equals(last_POJO.getHeadquarter_id()))) {
                                        datafull = true;
                                        Bind_data_listview();
                                    }
                                }
                            }

                        }


                        ////////
                    /*if (POJO.size() == 0) {
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
                        for (POJO_Territory_S pp : POJO) {
                            if (last_POJO == null) {
                                datafull = false;
                            } else if ((pp.getName().equals(last_POJO.getName())) && (pp.getModified().equals(last_POJO.getModified()))) {
                                datafull = true;
                                Bind_data_listview();
                            }
                        }
                    }*/

                        if (datafull == false) {
                            Load_Headquarter();
                        }
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
                        if (task_user != null) {
                            task_user.cancel(true);
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
