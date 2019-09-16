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


public class adapter_test_attch_doctor_list_for_dialog extends ArrayAdapter<POJO_Doctor_Master_S> {
    public Context _context;

    public adapter_test_attch_doctor_list_for_dialog(Context context, int resource, List<POJO_Doctor_Master_S> POJO_Doctor_Master_S) {
        super(context, resource, POJO_Doctor_Master_S);
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
                v = inflater.inflate(R.layout.adapter_test_att_doctor_list_for_dialog, parent, false);
            }

            final POJO_Doctor_Master_S doctor_master_s = getItem(position);

            if (doctor_master_s != null) {

                TextView tvname = (TextView) v.findViewById(R.id.name);
                //TextView head = (TextView) v.findViewById(R.id.head);
                TextView sub = (TextView) v.findViewById(R.id.sub);
                TextView mark=(TextView)v.findViewById(R.id.mark) ;
                mark.setText(doctor_master_s.getDoctor_name().trim().toString().substring(0,1));
                //head.setText(User.getFull_name());
                sub.setText(doctor_master_s.getDoctor_name().toString().trim());

                tvname.setText(doctor_master_s.getName());

            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
