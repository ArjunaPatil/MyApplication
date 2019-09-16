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


public class adapter_stockist_list_to_top_hierarchy_for_dialog extends ArrayAdapter<POJO_Stockist_List> {
    public Context _context;

    public adapter_stockist_list_to_top_hierarchy_for_dialog(Context context, int resource, List<POJO_Stockist_List> POJO_Stockist_List) {
        super(context, resource, POJO_Stockist_List);
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
                v = inflater.inflate(R.layout.adapter_stockist_list_to_top_hierarchy_for_dialog, parent, false);
            }

            final POJO_Stockist_List POJO_Stockist_List = getItem(position);

            if (POJO_Stockist_List != null) {

                TextView tvname = (TextView) v.findViewById(R.id.name);
                //TextView head = (TextView) v.findViewById(R.id.head);
                TextView sub = (TextView) v.findViewById(R.id.sub);
                TextView txt_territory = (TextView) v.findViewById(R.id.txt_territory);

                //head.setText(User.getFull_name());

                sub.setText(POJO_Stockist_List.getFull_name());
                TextView mark = (TextView) v.findViewById(R.id.mark);
                mark.setText(POJO_Stockist_List.getFull_name().trim().substring(0, 1));
                txt_territory.setText(POJO_Stockist_List.getTerritory());
                tvname.setText(POJO_Stockist_List.getName());
            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
