package com.example.vin.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by vin on 31/01/2017.
 */


public class adapter_miss_obj_list extends ArrayAdapter<POJO_Miss_OBJ> {
    public Context _context;

    public adapter_miss_obj_list(Context context, int resource, List<POJO_Miss_OBJ> POJO_Miss_OBJ) {
        super(context, resource, POJO_Miss_OBJ);
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

            if (v == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.adapter_miss_obj_list, parent, false);
            }

            final POJO_Miss_OBJ POJO = getItem(position);

            if (POJO != null) {

                TextView emp = (TextView) v.findViewById(R.id.emp);
                TextView designation = (TextView) v.findViewById(R.id.designation);
                TextView mobno = (TextView) v.findViewById(R.id.mobno);
                TextView email = (TextView) v.findViewById(R.id.email);

                String upperString = POJO.getEmp_name().toString().trim().substring(0, 1).toUpperCase();
                TextView mark = (TextView) v.findViewById(R.id.mark);
                mark.setText(upperString);
                emp.setText("Name :" + POJO.getEmp_name());
                designation.setText("Designation :" + POJO.getDesignation());
                mobno.setText("MobNo :" + POJO.getMobno());
                email.setText("Email :" + POJO.getEmail());
            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
