package com.example.vin.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;

/**
 * Created by vin on 31/01/2017.
 */
public class adapter_fragment_user_list extends ArrayAdapter<POJO_User_List> {

    private Realm mRealm;
    public Context _context;
    RestService restService;

    public adapter_fragment_user_list(Context context, int resource, List<POJO_User_List> POJO_User) {
        super(context, resource, POJO_User);
        this._context = context;
        try {
        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        try {
            //pDialog = new ProgressDialog(getContext());
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.adapter_user_list, parent, false);
            }

            POJO_User_List POJO_User = getItem(position);

            if (POJO_User != null) {

                TextView tvname = (TextView) v.findViewById(R.id.name);
                TextView user_serial_no = (TextView) v.findViewById(R.id.user_serial_no);
                TextView user_name = (TextView) v.findViewById(R.id.user_name);
                TextView tv_mob = (TextView) v.findViewById(R.id.tv_mob);

                TextView mark = (TextView) v.findViewById(R.id.mark);
                String upperString = POJO_User.getFull_name().trim().substring(0, 1).toUpperCase();
                mark.setText(upperString);

                user_name.setText(POJO_User.getFull_name());
                tv_mob.setText(POJO_User.getMobile_no1());

                ImageButton img_lock_mast_form = (ImageButton) v.findViewById(R.id.img_lock_mast_form);
                ImageButton img_open_mast_form = (ImageButton) v.findViewById(R.id.img_open_mast_form);
                TextView tv_master_form = (TextView) v.findViewById(R.id.tv_master_form);
                ImageButton img_lock_trans_form = (ImageButton) v.findViewById(R.id.img_lock_trans_form);
                ImageButton img_open_trans_form = (ImageButton) v.findViewById(R.id.img_open_trans_form);
                TextView tv_trans_form = (TextView) v.findViewById(R.id.tv_trans_form);

                ImageButton img_active_user = (ImageButton) v.findViewById(R.id.img_active_user);
                ImageButton img_block_user = (ImageButton) v.findViewById(R.id.img_block_user);

                img_lock_mast_form.setFocusable(false);
                img_lock_mast_form.setFocusableInTouchMode(false);

                img_open_mast_form.setFocusable(false);
                img_open_mast_form.setFocusableInTouchMode(false);

                img_lock_trans_form.setFocusable(false);
                img_lock_trans_form.setFocusableInTouchMode(false);

                img_open_trans_form.setFocusable(false);
                img_open_trans_form.setFocusableInTouchMode(false);

                img_active_user.setFocusable(false);
                img_active_user.setFocusableInTouchMode(false);
                img_block_user.setFocusable(false);
                img_block_user.setFocusableInTouchMode(false);


                if (POJO_User.getMast_flag() == 1) {
                    img_lock_mast_form.setVisibility(View.VISIBLE);
                    img_open_mast_form.setVisibility(View.GONE);
                    tv_master_form.setVisibility(View.VISIBLE);
                    tv_master_form.setTextColor(Color.parseColor("#f44336"));
                } else {
                    img_lock_mast_form.setVisibility(View.GONE);
                    img_open_mast_form.setVisibility(View.VISIBLE);
                    tv_master_form.setVisibility(View.VISIBLE);
                    tv_master_form.setTextColor(Color.parseColor("#00c853"));
                }

                if (POJO_User.getTrans_flag() == 1) {
                    img_lock_trans_form.setVisibility(View.VISIBLE);
                    img_open_trans_form.setVisibility(View.GONE);
                    tv_trans_form.setVisibility(View.VISIBLE);
                    tv_trans_form.setTextColor(Color.parseColor("#f44336"));
                } else {
                    img_lock_trans_form.setVisibility(View.GONE);
                    img_open_trans_form.setVisibility(View.VISIBLE);
                    tv_trans_form.setVisibility(View.VISIBLE);
                    tv_trans_form.setTextColor(Color.parseColor("#00c853"));
                }

                if (POJO_User.getEnabled() == 1) {
                    img_active_user.setVisibility(View.VISIBLE);
                    img_block_user.setVisibility(View.GONE);
                } else {
                    img_active_user.setVisibility(View.GONE);
                    img_block_user.setVisibility(View.VISIBLE);
                }

                tvname.setText(POJO_User.getName());
                user_serial_no.setText((position + 1) + "." + "");

                /*
                    Integer mast_prof = 0;
    Integer mast_patch = 0;
    Integer mast_doc = 0;
    Integer mast_chem = 0;
    Integer trans_obj = 0;
    Integer trans_doc = 0;
    Integer trans_chem = 0;
    Integer active_flag = 0;
    ProgressDialog pDialog;
                load_user_data(POJO_User.getName(), v);
                ImageButton img_lock_mast_form = (ImageButton) v.findViewById(R.id.img_lock_mast_form);
                ImageButton img_open_mast_form = (ImageButton) v.findViewById(R.id.img_open_mast_form);
                TextView tv_master_form = (TextView) v.findViewById(R.id.tv_master_form);
                ImageButton img_lock_trans_form = (ImageButton) v.findViewById(R.id.img_lock_trans_form);
                ImageButton img_open_trans_form = (ImageButton) v.findViewById(R.id.img_open_trans_form);
                TextView tv_trans_form = (TextView) v.findViewById(R.id.tv_trans_form);
                ImageButton img_active_user = (ImageButton) v.findViewById(R.id.img_active_user);
                ImageButton img_block_user = (ImageButton) v.findViewById(R.id.img_block_user);*/
                /*------*/

            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }

    /*private void load_user_data(final String name, final View v) {
        try {
            //pDialog.show();
            restService = new RestService();
            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sid = app_preferences.getString("sid", "default");
            //final String name = POJO_User.getName();//app_preferences.getString("name", "default");

            restService.getService().getUserLockData(sid, name, new Callback<JsonElement>() {
                @Override
                public void success(JsonElement jsonElement, Response response) {
                    try {

                        JsonObject j1 = jsonElement.getAsJsonObject();
                        JsonObject j2 = j1.getAsJsonObject("message");

                        mast_prof = Integer.valueOf(j2.get("lock_prof").getAsString());
                        mast_patch = Integer.valueOf(j2.get("lock_patch").getAsString());
                        mast_doc = Integer.valueOf(j2.get("lock_doc").getAsString());
                        mast_chem = Integer.valueOf(j2.get("lock_chem").getAsString());
                        trans_obj = Integer.valueOf(j2.get("trans_obj").getAsString());
                        trans_doc = Integer.valueOf(j2.get("trans_doc").getAsString());
                        trans_chem = Integer.valueOf(j2.get("trans_chem").getAsString());
                        active_flag = Integer.valueOf(j2.get("active_flag").getAsString());


                        ImageButton img_lock_mast_form = (ImageButton) v.findViewById(R.id.img_lock_mast_form);
                        ImageButton img_open_mast_form = (ImageButton) v.findViewById(R.id.img_open_mast_form);
                        TextView tv_master_form = (TextView) v.findViewById(R.id.tv_master_form);
                        ImageButton img_lock_trans_form = (ImageButton) v.findViewById(R.id.img_lock_trans_form);
                        ImageButton img_open_trans_form = (ImageButton) v.findViewById(R.id.img_open_trans_form);
                        TextView tv_trans_form = (TextView) v.findViewById(R.id.tv_trans_form);

                        ImageButton img_active_user = (ImageButton) v.findViewById(R.id.img_active_user);
                        ImageButton img_block_user = (ImageButton) v.findViewById(R.id.img_block_user);

                        if (mast_prof == 1 && mast_patch == 1 && mast_doc == 1 && mast_chem == 1) {
                            img_lock_mast_form.setVisibility(View.VISIBLE);
                            img_open_mast_form.setVisibility(View.GONE);
                            tv_master_form.setVisibility(View.VISIBLE);
                            tv_master_form.setTextColor(Color.parseColor("#f44336"));
                        } else {
                            img_lock_mast_form.setVisibility(View.GONE);
                            img_open_mast_form.setVisibility(View.VISIBLE);
                            tv_master_form.setVisibility(View.VISIBLE);
                            tv_master_form.setTextColor(Color.parseColor("#00c853"));
                        }

                        if (trans_obj == 1 && trans_doc == 1 && trans_chem == 1) {
                            img_lock_trans_form.setVisibility(View.VISIBLE);
                            img_open_trans_form.setVisibility(View.GONE);
                            tv_trans_form.setVisibility(View.VISIBLE);
                            tv_trans_form.setTextColor(Color.parseColor("#f44336"));
                        } else {
                            img_lock_trans_form.setVisibility(View.GONE);
                            img_open_trans_form.setVisibility(View.VISIBLE);
                            tv_trans_form.setVisibility(View.VISIBLE);
                            tv_trans_form.setTextColor(Color.parseColor("#00c853"));
                        }

                        if (active_flag == 1) {
                            img_active_user.setVisibility(View.VISIBLE);
                            img_block_user.setVisibility(View.GONE);
                        } else {
                            img_active_user.setVisibility(View.GONE);
                            img_block_user.setVisibility(View.VISIBLE);
                        }

                        //pDialog.hide();
                    } catch (Exception ex) {
                        //pDialog.hide();
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        //pDialog.hide();
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
    }*/
}
