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


public class adapter_chemist_calls extends ArrayAdapter<POJO_chemist_Calls_S> {
    public Context _context;

    public adapter_chemist_calls(Context context, int resource, List<POJO_chemist_Calls_S> POJO_chemist_Calls_S) {
        super(context, resource, POJO_chemist_Calls_S);
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

            final POJO_chemist_Calls_S POJO = getItem(position);

            if (POJO != null) {

                TextView tvname = (TextView) v.findViewById(R.id.name);
                TextView head = (TextView) v.findViewById(R.id.head);
                TextView sub = (TextView) v.findViewById(R.id.sub);
                TextView sub_second = (TextView) v.findViewById(R.id.sub_second);
                TextView sub_second2 = (TextView) v.findViewById(R.id.sub_second2);
                TextView sub_time = (TextView) v.findViewById(R.id.sub_time);
                TextView mark = (TextView) v.findViewById(R.id.mark);
                // sub.setVisibility(View.GONE);

                //  String upperString = POJO.getChemist_name().substring(0,1).toUpperCase() ;
                String upperString = POJO.getChemist_name().trim().substring(0, 1).toUpperCase();
                mark.setText(upperString);
                String sub_second_head = "NO POB FOUND";

                int pcount = 0;

                float Total_amt = 0;

                if (POJO.getTqty_1().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_1());
                }

                if (POJO.getTqty_2().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_2());
                }

                if (POJO.getTqty_3().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_3());
                }

                if (POJO.getTqty_4().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_4());
                }

                if (POJO.getTqty_5().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_5());
                }

                if (POJO.getTqty_6().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_6());
                }

                if (POJO.getTqty_7().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_7());
                }

                if (POJO.getTqty_8().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_8());
                }

                if (POJO.getTqty_9().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_9());
                }

                if (POJO.getTqty_10().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_10());
                }

                if (POJO.getTqty_11().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_11());
                }

                if (POJO.getTqty_12().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_12());
                }

                if (POJO.getTqty_13().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_13());
                }

                if (POJO.getTqty_14().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_14());
                }

                if (POJO.getTqty_15().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_15());
                }

                if (POJO.getTqty_16().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_16());
                }

                if (POJO.getTqty_17().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_17());
                }

                if (POJO.getTqty_18().equals("0") == false) {
                    pcount = pcount + 1;
                    Total_amt = Total_amt + Float.valueOf(POJO.getTamt_18());
                }

                if (pcount > 0) {
                    sub_second_head = "POB Total Value: " + Total_amt + " for " + pcount + " products";
                } else {
                    sub_second_head = "NO POB FOUND";
                }


                head.setText(POJO.getChemist_name());
                //sub.setText("JFW WITH: " +POJO.getJwf_with());

             /*   String ss = "", s;
                ss += "JFW with1: " + POJO.getJwf_with() + "<br />";
                s = POJO.getJwf_with2();
                if (s == null) {
                    s = "Select JFW WITH";
                } else {
                    s = POJO.getJwf_with2();
                }
                ss += "JFW with2: " + s;

                sub.setText(Html.fromHtml(ss));*/

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
                        if (!POJO.getJwf_with().contains("Select") && !POJO.getJwf_with().contains("NONE")) {
                            sub_second2.setVisibility(View.VISIBLE);
                            //ss += "JFW with1: " + POJO.getJwf_with() + "<br />";
                            sub_second2.setText("JFW with2: " + POJO.getJwf_with2());
                        }
                    }
                } else {
                    sub_second2.setVisibility(View.GONE);
                }

                sub_time.setText("(TIME: " + POJO.getCreation().substring(11, 16) + ")");


                sub.setText(sub_second_head);

                tvname.setText(POJO.getName());

            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
