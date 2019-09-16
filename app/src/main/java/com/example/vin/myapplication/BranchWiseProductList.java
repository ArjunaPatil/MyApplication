package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit.Callback;
import retrofit.RetrofitError;

import static com.example.vin.myapplication.DashBord_main.task_app_evrsion;

/**
 * Created by VGIMPACT202 on 28/04/2018.
 */

public class BranchWiseProductList {
    // variable to hold context
    private Context context;
    RestService restService;
    private ProgressDialog pDialog;
    final value_set vv;

    public BranchWiseProductList(Context context) {
        this.context = context;
        pDialog = new ProgressDialog(context);
        vv = new value_set();

    }

    String products_with_comma_for_branch = "";

    public String get_branch_of_user(String user) {
        try {

            pDialog.show();
            restService = new RestService();
            final CountDownLatch latch = new CountDownLatch(1);
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String sid = app_preferences.getString("sid", "default");

            JSONArray jsonArray = new JSONArray();

            jsonArray.put("name");
            jsonArray.put("branch");
            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();

            Filter1.put("User");
            Filter1.put("name");
            Filter1.put("=");
            Filter1.put(user);
            Filters.put(Filter1);

            //"modified desc", 0, 1,
            restService.getService().getUser(sid, 0, 1, jsonArray, Filters, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, retrofit.client.Response response) {
                    try {
                        pDialog.hide();
                        // ListView lv = (ListView) findViewById(R.id.listView);
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonArray j2 = j1.getAsJsonArray("data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<POJO_User>>() {
                        }.getType();
                        List<POJO_User> POJO = gson.fromJson(j2, type);

                        String branch = "";
                        if (POJO.size() <= 1) {
                            for (POJO_User pp : POJO) {
                                branch = pp.getBranch();
                            }
                        }
                        if (!branch.equals("")) {
                            latch.await();
                            vv.setProducts_with_comma_for_branch(get_product_list_of_branch(branch));
                            //products_with_comma_for_branch = get_product_list_of_branch(branch);
                        }
                        latch.countDown();
                    } catch (Exception ex) {
                        Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show();
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(context, "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }
                    } catch (Exception ex) {
                        Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            latch.await();
            products_with_comma_for_branch = vv.getProducts_with_comma_for_branch();
            return products_with_comma_for_branch;
        } catch (Exception ex) {
            pDialog.hide();
            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return products_with_comma_for_branch;
        }
    }

    String products_with_comma = "";

    public String get_product_list_of_branch(String branch) {
        try {
            pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
            String sid = app_preferences.getString("sid", "default");

            restService.getService().getproduct_list(sid, "'" + branch + "'", new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, retrofit.client.Response response) {
                    try {
                        pDialog.hide();
                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        //String products_with_comma = j2.get("msg").getAsString();
                        products_with_comma = j2.get("msg").getAsString();
                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);    //status make 0 so checkoffline cannot work
                        SharedPreferences.Editor editor = app_preferences.edit();
                        editor.putString("branch_product", products_with_comma);
                        editor.commit();
                        vv.setProducts_with_comma(products_with_comma);
                    } catch (Exception ex) {
                        products_with_comma = "";
                        Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        products_with_comma = "";
                        pDialog.hide();

                        String msg = "";
                        if (error.getKind().toString().contains("HTTP")) {
                            msg = error.toString();
                        } else {
                            msg = "139.59.63.181";
                        }

                        if (msg.equals("403 FORBIDDEN")) {
                            Toast.makeText(context, "Error...", Toast.LENGTH_SHORT).show();
                        } else if (msg.contains("139.59.63.181")) {
                            Toast.makeText(context, "PLEASE CHECK INTERNET CONNECT", Toast.LENGTH_SHORT).show();
                        }

                        if (task_app_evrsion != null) {
                            task_app_evrsion.cancel(true);
                        }
                    } catch (Exception ex) {
                        products_with_comma = "";
                        Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return products_with_comma;

        } catch (Exception ex) {
            pDialog.hide();
            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    public List<String> return_in_array(String branch) {
        try {
            List<String> items = Arrays.asList(branch.split("\\s*,\\s*"));
            return items;
        } catch (Exception ex) {
            pDialog.hide();
            Toast.makeText(context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public class value_set {
        private String products_with_comma_for_branch;
        private String products_with_comma;

        public String getProducts_with_comma_for_branch() {
            return products_with_comma_for_branch;
        }

        public void setProducts_with_comma_for_branch(String products_with_comma_for_branch) {
            this.products_with_comma_for_branch = products_with_comma_for_branch;
        }

        public String getProducts_with_comma() {
            return products_with_comma;
        }

        public void setProducts_with_comma(String products_with_comma) {
            this.products_with_comma = products_with_comma;
        }

    }
}


//


//raw material for copy paste//

/*JSONArray jsonArray = new JSONArray();

            jsonArray.put("wego_gel_30_mg");
            jsonArray.put("stand_sp_tab");
            jsonArray.put("actirab_l_cap");
            jsonArray.put("empower_od_tab");
            jsonArray.put("lycorest_60ml_susp");
            jsonArray.put("wego_gel_20_mg");
            jsonArray.put("start_t_tab");
            jsonArray.put("stand_mf_60ml_susp");
            jsonArray.put("lycorest_tab");
            jsonArray.put("actirab_tab");
            jsonArray.put("lycort_1ml_inj");
            jsonArray.put("actirab_dv_cap");
            jsonArray.put("lycolic_10ml_drops");
            jsonArray.put("regain_xt_tab");
            jsonArray.put("actirab_d_cap");
            jsonArray.put("ten_on_30ml");
            jsonArray.put("glucolyst_g1_tab");
            jsonArray.put("trygesic_tab");
            //trygesic_ptab,cipgrow_syrup,clavyten_625,levocast_m,altipan_dsr,sangria_tonic,onederm_cream,actirest_ls,actirest_dx,korby_soap,
            jsonArray.put("trygesic_ptab");
            jsonArray.put("cipgrow_syrup");
            jsonArray.put("onederm_cream");
            jsonArray.put("clavyten_625");
            jsonArray.put("levocast_m");
            jsonArray.put("sangria_tonic");
            jsonArray.put("korby_soap");
            jsonArray.put("actirest_dx");
            jsonArray.put("altipan_dsr");
            jsonArray.put("actirest_ls");

            jsonArray.put("branch");
            jsonArray.put("name");


            jsonArray.put("item_name");
            jsonArray.put("item_group");
            jsonArray.put("branch");
            jsonArray.put("modified");

            JSONArray Filters = new JSONArray();
            JSONArray Filter1 = new JSONArray();

            Filter1.put("Item");
            Filter1.put("branch");
            Filter1.put("=");
            Filter1.put(branch);

            Filters.put(Filter1);*/

//"modified desc", 0, 1,
//restService.getService().getProductOfBranch(sid, "modified desc", 0, 1, jsonArray, Filters, new Callback<JsonElement>() {