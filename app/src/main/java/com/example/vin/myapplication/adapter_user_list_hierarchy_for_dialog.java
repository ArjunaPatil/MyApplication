package com.example.vin.myapplication;

import android.content.Context;
import android.graphics.Color;
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


public class adapter_user_list_hierarchy_for_dialog extends ArrayAdapter<POJO_User_S> {
    public Context _context;
    public adapter_user_list_hierarchy_for_dialog(Context context, int resource, List<POJO_User_S> POJO_User_S) {
        super(context, resource, POJO_User_S);
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
            v = inflater.inflate(R.layout.adapter_user_list_hirarchy_for_dialog, parent, false);
        }

        final POJO_User_S POJO_User_S = getItem(position);

        if (POJO_User_S != null) {

            TextView tvname = (TextView) v.findViewById(R.id.name);
            //TextView head = (TextView) v.findViewById(R.id.head);
            TextView sub = (TextView) v.findViewById(R.id.sub);
            TextView txt_designation=(TextView)v.findViewById(R.id.txt_designation) ;

            //head.setText(User.getFull_name());
            sub.setText(POJO_User_S.getFirst_name()+" "+POJO_User_S.getLast_name());
            TextView mark=(TextView)v.findViewById(R.id.mark) ;
            mark.setText(POJO_User_S.getFirst_name().trim().substring(0,1));
            if(POJO_User_S.getDesignation() != null) {
                txt_designation.setText(POJO_User_S.getDesignation());

                String des = POJO_User_S.getDesignation();


                if ((des.equals("TBM")) || (des.equals("KBM"))) {

                    txt_designation.setBackgroundColor(Color.RED);

                } else if (des.equals("ABM")) {
                    txt_designation.setBackgroundColor(Color.GRAY);

                } else if (des.equals("RBM")) {
                    txt_designation.setBackgroundColor(Color.GRAY);

                } else if (des.equals("CRM")) {
                    txt_designation.setBackgroundColor(Color.GRAY);

                } else if (des.equals("ZBM")) {
                    txt_designation.setBackgroundColor(Color.GRAY);

                } else if (des.equals("SM")) {
                    txt_designation.setBackgroundColor(Color.GRAY);

                } else if (des.equals("NBM")) {
                    txt_designation.setBackgroundColor(Color.GRAY);

                } else if ((des.equals("Head of Marketing and Sales")) || (des.equals("HR Manager")) || (des.equals("Administrator"))) {

                    txt_designation.setBackgroundColor(Color.GRAY);
                } else {
                    txt_designation.setBackgroundColor(Color.BLACK);
                }
            }
            else
            {
                txt_designation.setText("???");
                txt_designation.setBackgroundColor(Color.YELLOW);
                txt_designation.setTextColor(Color.BLACK);
            }

            tvname.setText(POJO_User_S.getName());
        }

        return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
