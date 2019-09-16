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


public class adapter_doctor_calls extends ArrayAdapter<POJO_Doctor_Calls_S> {
    public Context _context;

    public adapter_doctor_calls(Context context, int resource, List<POJO_Doctor_Calls_S> POJO_Doctor_Calls_S) {
        super(context, resource, POJO_Doctor_Calls_S);
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
                    v = inflater.inflate(R.layout.adapter_common_list_three_line, parent, false);
                }

            final POJO_Doctor_Calls_S POJO = getItem(position);

            if (POJO != null) {

                TextView tvname = (TextView) v.findViewById(R.id.name);
                TextView head = (TextView) v.findViewById(R.id.head);
                TextView sub = (TextView) v.findViewById(R.id.sub);
                TextView sub_second = (TextView) v.findViewById(R.id.sub_second);
                TextView sub_second2 = (TextView) v.findViewById(R.id.sub_second2);
                TextView sub_time = (TextView) v.findViewById(R.id.sub_time);
                TextView mark = (TextView) v.findViewById(R.id.mark);
           /* CircularTextView circularTextView= (CircularTextView)v.findViewById(R.id.circularTextView);
            circularTextView.setStrokeWidth(1);
            circularTextView.setStrokeColor("#ffffff");
            circularTextView.setSolidColor("#000000");
*/

                String upperString = POJO.getDoctor_name().trim().substring(0, 1).toUpperCase();
                mark.setText(upperString);
                head.setText(POJO.getDoctor_name().trim());
                sub.setText("PATCH: " + POJO.getPatch_name().trim());

/*                String ss = "";
                ss = POJO.getJwf_with();
                if (ss != null) {
                    if (POJO.getJwf_with().contains("Select") || POJO.getJwf_with().contains("None")) {

                    } else {
                        ss += "JFW with1: " + POJO.getJwf_with() + "<br />";
                    }
                }
                String s = "";
                s = POJO.getJwf_with2();
                if (s != null) {
                    if (POJO.getJwf_with2().contains("Select") || POJO.getJwf_with2().contains("None")) {

                    } else {
                        s = POJO.getJwf_with2() + "<br />";
                        ss += "JFW with2: " + s;

                    }
                }


                ss += "(TIME: " + POJO.getCreation().substring(11, 16) + ")";

                sub_second.setText(Html.fromHtml(ss));
                */

                String ss = "";
                ss = POJO.getJwf_with();
                if (ss != null) {
                    if (POJO.getJwf_with().contains("Select") || POJO.getJwf_with().contains("NONE")) {
                        sub_second.setVisibility(View.GONE);
                    } else {
                        if (!POJO.getJwf_with().contains("Select") && !POJO.getJwf_with().contains("NONE")) {
                            sub_second.setVisibility(View.VISIBLE);
                            //ss += "JFW with1: " + POJO.getJwf_with() + "<br />";
                            sub_second.setText("JFW with1: " + POJO.getJwf_with());
                        }
                    }
                } else {
                    sub_second.setVisibility(View.GONE);
                }

                ss = "";
                ss = POJO.getJwf_with2();
                if (ss != null) {
                    if (POJO.getJwf_with2().contains("Select") || POJO.getJwf_with2().contains("NONE")) {
                        sub_second2.setVisibility(View.GONE);
                    } else {
                        if (!POJO.getJwf_with2().contains("Select") && !POJO.getJwf_with2().contains("NONE")) {
                            sub_second2.setVisibility(View.VISIBLE);
                            //ss += "JFW with1: " + POJO.getJwf_with() + "<br />";
                            sub_second2.setText("JFW with2: " + POJO.getJwf_with2());
                        }
                    }
                } else {
                    sub_second2.setVisibility(View.GONE);
                }

                sub_time.setText("(TIME: " + POJO.getCreation().substring(11, 16) + ")");


                tvname.setText(POJO.getName());
            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
