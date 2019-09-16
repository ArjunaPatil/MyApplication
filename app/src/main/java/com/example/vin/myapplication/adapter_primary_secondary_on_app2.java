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


public class adapter_primary_secondary_on_app2 extends ArrayAdapter<POJO_Primary_Secondary_On_App_Product_List> {
    public Context _context;

    public adapter_primary_secondary_on_app2(Context context, int resource, List<POJO_Primary_Secondary_On_App_Product_List> POJO_Primary_Secondary_On_App_Product_List) {
        super(context, resource, POJO_Primary_Secondary_On_App_Product_List);
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
                v = inflater.inflate(R.layout.adapter_primary_secondary_on_app2, parent, false);
            }

            final POJO_Primary_Secondary_On_App_Product_List POJO = getItem(position);

            if (POJO != null) {

                TextView tv_txt_product_name = (TextView) v.findViewById(R.id.tv_txt_product_name);
                TextView tv_txt_product_sale = (TextView) v.findViewById(R.id.tv_txt_product_sale);
                TextView tv_txt_product_return = (TextView) v.findViewById(R.id.tv_txt_product_return);
                TextView tv_txt_product_tot = (TextView) v.findViewById(R.id.tv_txt_product_tot);

                if (!POJO.getProduct().isEmpty()) {
                    tv_txt_product_name.setText(POJO.getProduct());
                }

                if (!POJO.getSaleValue().isEmpty() && !POJO.getSaleQty().isEmpty()) {
                    tv_txt_product_sale.setText(POJO.getSaleValue() + "(" + POJO.getSaleQty() + ")");
                }

                if (!POJO.getRetValue().isEmpty() && !POJO.getRetQty().isEmpty()) {
                    tv_txt_product_return.setText(POJO.getRetValue() + "(" + POJO.getRetQty() + ")");
                }

                if (!POJO.getSaleValue().isEmpty() && !POJO.getRetValue().isEmpty()) {
                    double sv = 0.0, rv = 0.0, tv = 0.0;
                    sv = Double.parseDouble(POJO.getSaleValue());
                    rv = Double.parseDouble(POJO.getRetValue());
                    tv = sv - rv;
                    //tv_txt_product_return.setText(POJO.getRetValue() + "(" + POJO.getRetQty() + ")");
                    tv_txt_product_tot.setText(String.valueOf(tv));
                }


            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
