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
public class adapter_fragment_hierarchy_headquarter_list extends ArrayAdapter<POJO_Territory_S> {
    private Realm mRealm;
    public Context _context;

    public adapter_fragment_hierarchy_headquarter_list(Context context, int resource, List<POJO_Territory_S> POJO_Territory_S) {
        super(context, resource, POJO_Territory_S);
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
                v = inflater.inflate(R.layout.adapter_hierarchy_headquarter_list, parent, false);
            }

            POJO_Territory_S POJO_Territory_S = getItem(position);
            //tv_chemist_name;tv_headquarter;tv_city;tv_contact_no;name
            if (POJO_Territory_S != null) {

                TextView headquarter_serial_no = (TextView) v.findViewById(R.id.headquarter_serial_no);
                TextView tv_headquarter_name = (TextView) v.findViewById(R.id.tv_headquarter_name);
                TextView tvname = (TextView) v.findViewById(R.id.name);
                
                headquarter_serial_no.setText((position + 1) + "." + "HeadQuarter: ");
                tv_headquarter_name.setText(POJO_Territory_S.getHeadquarter_name());

                tvname.setText(POJO_Territory_S.getHeadquarter_id());
            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }

    }
}
