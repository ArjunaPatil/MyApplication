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


public class adapter_rpt_plan_detail_list extends ArrayAdapter<POJO_objective_S> {
    public Context _context;

    public adapter_rpt_plan_detail_list(Context context, int resource, List<POJO_objective_S> POJO_objective_S) {
        super(context, resource, POJO_objective_S);
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
                v = inflater.inflate(R.layout.adapter_rpt_plan_detail_list, parent, false);
            }

            final POJO_objective_S POJO = getItem(position);

            if (POJO != null) {

                TextView tvname = (TextView) v.findViewById(R.id.name);
                TextView head = (TextView) v.findViewById(R.id.head);
                //TextView sub = (TextView) v.findViewById(R.id.sub);
                TextView poston = (TextView) v.findViewById(R.id.poston);
                //TextView sub_second = (TextView) v.findViewById(R.id.sub_second);


                String upperString = POJO.getUser_name().toString().trim().substring(0, 1).toUpperCase();
                TextView mark = (TextView) v.findViewById(R.id.mark);
                mark.setText(upperString);
                head.setText(POJO.getUser_name());

                //DCR Section
                if (POJO.getDoctor_flag() == 1) {
                    String str_dcr = "";
                    TextView sub1 = (TextView) v.findViewById(R.id.sub1);
                    TextView dcr_detailing = (TextView) v.findViewById(R.id.dcr_detailing);

                    if (POJO.getSelect_patch() != null) {
                        if (!POJO.getSelect_patch().isEmpty()) {
                            str_dcr = "PATCH: " + POJO.getSelect_patch() + "  \n";
                        }
                    }
                    if (POJO.getDcr_jfw_with1_name() != null) {
                        if (!POJO.getDcr_jfw_with1_name().isEmpty()) {
                            str_dcr += "JFW1: " + POJO.getDcr_jfw_with1_name() + "  \n";
                        }
                    }

                    if (POJO.getDcr_jfw_with2_name() != null) {
                        if (!POJO.getDcr_jfw_with2_name().isEmpty()) {
                            str_dcr += "JFW2: " + POJO.getDcr_jfw_with2_name();
                        }
                    }
                    if (!str_dcr.isEmpty()) {
                        sub1.setVisibility(View.VISIBLE);
                        dcr_detailing.setVisibility(View.VISIBLE);
                        dcr_detailing.setText(str_dcr);
                    } else {
                        sub1.setVisibility(View.GONE);
                        dcr_detailing.setVisibility(View.GONE);
                    }
                }


                //CAMP Section
                if (POJO.getCamp_flag() == 1) {
                    String str_camp = "";
                    TextView sub2 = (TextView) v.findViewById(R.id.sub2);
                    TextView camp_detailing = (TextView) v.findViewById(R.id.camp_detailing);

                    if (POJO.getDoctor_name() != null) {
                        if (!POJO.getDoctor_name().isEmpty()) {
                            str_camp = "DOCTOR: " + POJO.getDoctor_name() + "  \n";
                        }
                    }
                    if (POJO.getCamp_jfw_with1() != null) {
                        if (!POJO.getCamp_jfw_with1().isEmpty()) {
                            str_camp += "JFW1: " + POJO.getCamp_jfw_with1() + "  \n";
                        }
                    }

                    if (POJO.getCamp_jfw_with2() != null) {
                        if (!POJO.getCamp_jfw_with2().isEmpty()) {
                            str_camp += "JFW2: " + POJO.getCamp_jfw_with2();
                        }
                    }
                    if (!str_camp.isEmpty()) {
                        sub2.setVisibility(View.VISIBLE);
                        camp_detailing.setVisibility(View.VISIBLE);
                        camp_detailing.setText(str_camp);
                    } else {
                        sub2.setVisibility(View.GONE);
                        camp_detailing.setVisibility(View.GONE);
                    }
                }


                //MEETING Section
                if (POJO.getMeeting_flag() == 1) {
                    String str_meet = "";
                    TextView sub3 = (TextView) v.findViewById(R.id.sub3);
                    TextView meeting_detailing = (TextView) v.findViewById(R.id.meeting_detailing);

                    if (POJO.getMeeting_with() != null) {
                        if (!POJO.getMeeting_with().isEmpty()) {
                            str_meet = "Meeting With: " + POJO.getMeeting_with() + "  \n";
                        }
                    }
                    if (POJO.getPlace() != null) {
                        if (!POJO.getPlace().isEmpty()) {
                            str_meet += "Place: " + POJO.getPlace();
                        }
                    }

                    if (!str_meet.isEmpty()) {
                        sub3.setVisibility(View.VISIBLE);
                        meeting_detailing.setVisibility(View.VISIBLE);
                        meeting_detailing.setText(str_meet);
                    } else {
                        sub3.setVisibility(View.GONE);
                        meeting_detailing.setVisibility(View.GONE);
                    }
                }

                //Leave Section
                if (POJO.getLeave_flag() == 1) {
                    String str_leave = "";
                    TextView sub4 = (TextView) v.findViewById(R.id.sub4);
                    TextView leave_detailing = (TextView) v.findViewById(R.id.leave_detailing);

                    if (POJO.getLeave_type1() != null) {
                        if (POJO.getLeave_type1() == 1) {
                            str_leave = "Type: Casual Leave ";
                        }
                    }
                    if (POJO.getLeave_type2() != null) {
                        if (POJO.getLeave_type2() == 1) {
                            str_leave = "Type: Privilege Leave ";
                        }
                    }
                    if (POJO.getLeave_type3() != null) {
                        if (POJO.getLeave_type3() == 1) {
                            str_leave = "Type: Sick Leave ";
                        }
                    }

                    if (!str_leave.isEmpty()) {
                        sub4.setVisibility(View.VISIBLE);
                        leave_detailing.setVisibility(View.VISIBLE);
                        leave_detailing.setText(str_leave);
                    } else {
                        sub4.setVisibility(View.GONE);
                        leave_detailing.setVisibility(View.GONE);
                    }
                }

                poston.setText(" (POST ON: " + POJO.getCreation().substring(11, 16) + ")");
                tvname.setText(POJO.getUser());

            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
