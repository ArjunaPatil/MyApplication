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
public class adapter_fragment_stockiest_list extends ArrayAdapter<POJO_Stockiest> {
    private Realm mRealm;
    public Context _context;

    public adapter_fragment_stockiest_list(Context context, int resource, List<POJO_Stockiest> POJO_Stockiest) {
        super(context, resource, POJO_Stockiest);
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
                v = inflater.inflate(R.layout.adapter_headquarterwise_stockiest_list, parent, false);
            }

            POJO_Stockiest POJO_Stockiest = getItem(position);
            //tv_chemist_name;tv_headquarter;tv_city;tv_contact_no;name
            if (POJO_Stockiest != null) {

                TextView stockiest_serial_no = (TextView) v.findViewById(R.id.stockiest_serial_no);
                TextView tvname = (TextView) v.findViewById(R.id.name);
                TextView tv_customer_name = (TextView) v.findViewById(R.id.tv_customer_name);
                TextView tv_territory = (TextView) v.findViewById(R.id.tv_territory);
                TextView tv_contact_no = (TextView) v.findViewById(R.id.tv_contact_no);
                TextView tv_email = (TextView) v.findViewById(R.id.tv_email);
                //TextView tvstockiest_address = (TextView) v.findViewById(R.id.tvstockiest_address);

                stockiest_serial_no.setText((position + 1) + "." + "Name: ");
                tv_customer_name.setText(POJO_Stockiest.getCustomer_name());
                tv_territory.setText(POJO_Stockiest.getTerritory());
                tv_contact_no.setText(POJO_Stockiest.getPhone1());
                tv_email.setText(POJO_Stockiest.getEmail());
               // tvstockiest_address.setText(POJO_Stockiest.getAddress()+","+POJO_Stockiest.getCity()+","+POJO_Stockiest.getDistrict()+","+POJO_Stockiest.getState()+", Pincode:"+POJO_Stockiest.getPincode());
                tvname.setText(POJO_Stockiest.getName());
            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }

    }
}
