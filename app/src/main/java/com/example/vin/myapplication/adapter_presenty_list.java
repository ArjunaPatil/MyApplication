package com.example.vin.myapplication;

import android.content.Context;
import android.text.Html;
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


public class adapter_presenty_list extends ArrayAdapter<POJO_Presenty> {
    public Context _context;

    public adapter_presenty_list(Context context, int resource, List<POJO_Presenty> POJO_Presenty) {
        super(context, resource, POJO_Presenty);
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
                v = inflater.inflate(R.layout.adapter_presenty_list, parent, false);
            }

            final POJO_Presenty POJO = getItem(position);

            if (POJO != null) {

                TextView tvdate = (TextView) v.findViewById(R.id.date);
                TextView tvemp = (TextView) v.findViewById(R.id.emp);
                TextView count = (TextView) v.findViewById(R.id.count);
                TextView obj = (TextView) v.findViewById(R.id.obj);
                obj.setVisibility(View.GONE);

                tvdate.setText("DATE :" + POJO.getSelect_date().toString());
                String upperString = POJO.getEmp_name().toString().trim().substring(0, 1).toUpperCase();
                TextView mark = (TextView) v.findViewById(R.id.mark);
                mark.setText(upperString);
                tvemp.setText("NAME :" + POJO.getEmp_name());

                //obj.setText("Objective :" + POJO.getObj());
                String ss=POJO.getPresenty().toString();
                if (POJO.getPresenty().toString().equals("P")) {
                    String s = "";
                    s += "<b>" + "DCR COUNT : " + "</b>" + POJO.getDrc() + " | " + "<b>" + "CHEMIST COUNT: " + "</b>" + POJO.getChec() + "<br />";
                    s += "<b>" + "CAMP BOOK COUNT: " + "</b>" + POJO.getCmp_bk();
                    count.setText(Html.fromHtml(s));
                } else {
                    count.setText("ABSENT");
                }
            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
