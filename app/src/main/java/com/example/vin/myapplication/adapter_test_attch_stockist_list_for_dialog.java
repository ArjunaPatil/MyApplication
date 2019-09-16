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


public class adapter_test_attch_stockist_list_for_dialog extends ArrayAdapter<POJO_Stockiest> {
    public Context _context;

    public adapter_test_attch_stockist_list_for_dialog(Context context, int resource, List<POJO_Stockiest> POJO_Stockiest) {
        super(context, resource, POJO_Stockiest);
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
                v = inflater.inflate(R.layout.adapter_test_att_stockist_list_for_dialog, parent, false);
            }

            final POJO_Stockiest stockiest = getItem(position);

            if (stockiest != null) {

                TextView tvname = (TextView) v.findViewById(R.id.name);
                //TextView head = (TextView) v.findViewById(R.id.head);
                TextView sub = (TextView) v.findViewById(R.id.sub);
                TextView mark=(TextView)v.findViewById(R.id.mark) ;
                mark.setText(stockiest.getCustomer_name().trim().toString().substring(0,1));
                //head.setText(User.getFull_name());
                sub.setText(stockiest.getCustomer_name());

                tvname.setText(stockiest.getName());

            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
