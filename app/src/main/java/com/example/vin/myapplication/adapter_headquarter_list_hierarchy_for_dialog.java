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


public class adapter_headquarter_list_hierarchy_for_dialog extends ArrayAdapter<POJO_Territory_S> {
    public Context _context;
    public adapter_headquarter_list_hierarchy_for_dialog(Context context, int resource, List<POJO_Territory_S> POJO_Territory_S) {
        super(context, resource, POJO_Territory_S);
        this._context=context;
        try {
        } catch (Exception ex) {
            //Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
//((pp.getHeadquarter_id().equals(last_POJO.getHeadquarter_id()))) {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        try {

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.adapter_user_list_hirarchy_for_dialog, parent, false);
        }

        final POJO_Territory_S POJO_Territory_S = getItem(position);

            //TextView test = (TextView) v.findViewById(R.id.test);

        if (POJO_Territory_S != null) {
            //test.setVisibility(View.VISIBLE);
            TextView tvname = (TextView) v.findViewById(R.id.name);
            //TextView head = (TextView) v.findViewById(R.id.head);
            TextView sub = (TextView) v.findViewById(R.id.sub);
            TextView mark=(TextView)v.findViewById(R.id.mark) ;
            TextView txt_designation=(TextView)v.findViewById(R.id.txt_designation) ;
            txt_designation.setVisibility(View.GONE);
            //head.setText(User.getFull_name());
            sub.setText(POJO_Territory_S.getHeadquarter_name());
            mark.setText(POJO_Territory_S.getHeadquarter_name().trim().substring(0,1));

            tvname.setText(POJO_Territory_S.getHeadquarter_id());
        }

        return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
