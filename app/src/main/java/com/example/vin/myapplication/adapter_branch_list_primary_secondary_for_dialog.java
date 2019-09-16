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


public class adapter_branch_list_primary_secondary_for_dialog extends ArrayAdapter<POJO_Branch> {
    public Context _context;

    public adapter_branch_list_primary_secondary_for_dialog(Context context, int resource, List<POJO_Branch> POJO_Branch) {
        super(context, resource, POJO_Branch);
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
                v = inflater.inflate(R.layout.adapter_branch_list_primary_secondary_for_dialog, parent, false);
            }

            final POJO_Branch POJO_Branch = getItem(position);

            if (POJO_Branch != null) {
                TextView tvname = (TextView) v.findViewById(R.id.name);
                TextView sub = (TextView) v.findViewById(R.id.sub);

                sub.setText(POJO_Branch.getBranch());
                TextView mark = (TextView) v.findViewById(R.id.mark);
                mark.setText(POJO_Branch.getBranch().trim().substring(0, 1));
                tvname.setText(POJO_Branch.getName());
            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
