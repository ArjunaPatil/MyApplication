package com.example.vin.myapplication;

import android.content.Context;
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
public class adapter_fragment_hierarchy_users_list extends ArrayAdapter<POJO_User_Hierarchy> {
    private Realm mRealm;
    public Context _context;

    public adapter_fragment_hierarchy_users_list(Context context, int resource, List<POJO_User_Hierarchy> POJO_User_Hierarchy) {
        super(context, resource, POJO_User_Hierarchy);
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

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.adapter_hierarchy_users_list, parent, false);
            }

            POJO_User_Hierarchy POJO_User_Hierarchy = getItem(position);
            //tv_chemist_name;tv_headquarter;tv_city;tv_contact_no;name
            if (POJO_User_Hierarchy != null) {

                TextView users_serial_no = (TextView) v.findViewById(R.id.users_serial_no);
                TextView tvname = (TextView) v.findViewById(R.id.name);
                TextView tv_user_name = (TextView) v.findViewById(R.id.tv_user_name);
                TextView tv_designation = (TextView) v.findViewById(R.id.tv_designation);
                TextView tv_contact_no = (TextView) v.findViewById(R.id.tv_contact_no);
                TextView tv_email = (TextView) v.findViewById(R.id.tv_email);
                //TextView tvstockiest_address = (TextView) v.findViewById(R.id.tvstockiest_address);
                String upperString = POJO_User_Hierarchy.getFirst_name().toString().trim().substring(0,1).toUpperCase() ;
                TextView mark = (TextView) v.findViewById(R.id.mark);
                mark.setText(upperString);
                users_serial_no.setText((position + 1) + "." + " ");
                //tv_user_name.setText(POJO_User_Hierarchy.getFirst_name()+" "+POJO_User_Hierarchy.getMiddle_name()+" "+POJO_User_Hierarchy.getLast_name());
                tv_user_name.setText(POJO_User_Hierarchy.getFirst_name()+" "+POJO_User_Hierarchy.getLast_name());
                tv_designation.setText(POJO_User_Hierarchy.getDesignation());
                tv_contact_no.setText(POJO_User_Hierarchy.getMobile_no1());
                tv_email.setText(POJO_User_Hierarchy.getEmail());
               // tvstockiest_address.setText(POJO_Stockiest.getAddress()+","+POJO_Stockiest.getCity()+","+POJO_Stockiest.getDistrict()+","+POJO_Stockiest.getState()+", Pincode:"+POJO_Stockiest.getPincode());
                tvname.setText(POJO_User_Hierarchy.getName());
            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }

    }
}
