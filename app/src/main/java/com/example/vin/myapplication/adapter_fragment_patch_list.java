package com.example.vin.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.realm.Realm;

/**
 * Created by vin on 31/01/2017.
 */
public class adapter_fragment_patch_list extends ArrayAdapter<POJO_Patch_master> {
    private Realm mRealm;
    public Context _context;

    public adapter_fragment_patch_list(Context context, int resource, List<POJO_Patch_master> POJO_Patch_master) {
        super(context, resource, POJO_Patch_master);
        this._context=context;
        try {
        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        try {

            final SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String designation = (app_preferences.getString("designation", "default"));

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.adapter_patch_list, parent, false);
            }

            POJO_Patch_master Patch_master = getItem(position);

            if (Patch_master != null) {

                TextView patch_serial_no = (TextView) v.findViewById(R.id.patch_serial_no);
                TextView tvname = (TextView) v.findViewById(R.id.name);
                TextView patch_name = (TextView) v.findViewById(R.id.patch_name);
                TextView headquarter = (TextView) v.findViewById(R.id.tv_headquarter);

                TextView tv_user = (TextView) v.findViewById(R.id.tv_user);


                TextView mark = (TextView) v.findViewById(R.id.mark);
                String upperString = Patch_master.getPatch_name().trim().substring(0,1).toUpperCase() ;
                mark.setText(upperString);

                patch_name.setText(Patch_master.getPatch_name());
                headquarter.setText(Patch_master.getHeadquarter()==null?"-":Patch_master.getHeadquarter());
                tv_user.setText(Patch_master.getUser_name()==null?"-":Patch_master.getUser_name());

                tvname.setText(Patch_master.getName());
                patch_serial_no.setText((position + 1) + "." + "");
                //TextView doc_count = (TextView) v.findViewById(R.id.doc_count);

                //Realm mRealm = Realm.getDefaultInstance();
                //final RealmResults<POJO_Doctor_Master> result_query1 = mRealm.where(POJO_Doctor_Master.class).equalTo("patch", Patch_master.getName()).findAll();
                //doc_count.setText(String.valueOf(result_query1.size()));

            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
