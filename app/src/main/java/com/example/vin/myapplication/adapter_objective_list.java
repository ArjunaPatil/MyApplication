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


public class adapter_objective_list extends ArrayAdapter<POJO_objective_S> {
    public Context _context;

    public adapter_objective_list(Context context, int resource, List<POJO_objective_S> POJO_objective_S) {
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
                v = inflater.inflate(R.layout.adapter_objective_list, parent, false);
            }

            final POJO_objective_S POJO = getItem(position);

            if (POJO != null) {

                TextView tvname = (TextView) v.findViewById(R.id.name);
                TextView head = (TextView) v.findViewById(R.id.head);
                TextView sub = (TextView) v.findViewById(R.id.sub);
                TextView sub2 = (TextView) v.findViewById(R.id.sub2);
                //TextView sub_second = (TextView) v.findViewById(R.id.sub_second);


                String upperString = POJO.getUser_name().toString().trim().substring(0, 1).toUpperCase();
                TextView mark = (TextView) v.findViewById(R.id.mark);
                mark.setText(upperString);
                head.setText(POJO.getUser_name());
                //sub.setText("OBJECTIVE: " +POJO.getObjective());
                if (POJO.getObjective() != null) {
                    //Previous Objectives Before The app Update
                    if (POJO.getObjective().length() > 1) {
                        sub.setText(POJO.getObjective().toString());
                    } else {
                        sub.setText("No Objective Found For Selected Date");
                    }
                } else {
                    if (POJO.getDoctor_flag() == 1 && POJO.getCamp_flag() == 1 && POJO.getMeeting_flag() == 1 && POJO.getLeave_flag() == 0) {
                        String s = "";
                        s += "<b>" + "PLAN OF DAY :" + "</b>" + " DCR  |  CAMP BOOKING  |  MEETING <br /><br />";
                        s += "<b>" + "DCR Agenda: " + "</b>" + POJO.getCall_agenda().trim().substring(0, POJO.getCall_agenda().length() <= 40 ? POJO.getCall_agenda().length() - 1 : 40) + "<br />";
                        s += "<b>" + "CAMP Agenda: " + "</b>" + POJO.getCamp_agenda().trim().substring(0, POJO.getCamp_agenda().length() <= 40 ? POJO.getCamp_agenda().length() - 1 : 40) + "<br />";
                        s += "<b>" + "Meeting Agenda: " + "</b>" + POJO.getMeeting_agenda().trim().substring(0, POJO.getMeeting_agenda().length() <= 40 ? POJO.getMeeting_agenda().length() - 1 : 40);
                        sub.setText(Html.fromHtml(s));
                        //sub.setText("PLAN OF DAY : DCR  |  CAMP BOOKING  |  MEETING \n");
                    } else if (POJO.getDoctor_flag() == 1 && POJO.getCamp_flag() == 1 && POJO.getMeeting_flag() == 0 && POJO.getLeave_flag() == 0) {
                        String s = "";
                        s += "<b>" + "PLAN OF DAY :" + "</b>" + " DCR  |  CAMP BOOKING <br /><br />";
                        s += "<b>" + "DCR Agenda: " + "</b>" + POJO.getCall_agenda().trim().substring(0, POJO.getCall_agenda().length() <= 40 ? POJO.getCall_agenda().length() - 1 : 40) + "<br />";
                        s += "<b>" + "CAMP Agenda: " + "</b>" + POJO.getCamp_agenda().trim().substring(0, POJO.getCamp_agenda().length() <= 40 ? POJO.getCamp_agenda().length() - 1 : 40);
                        sub.setText(Html.fromHtml(s));
                        //sub.setText("PLAN OF DAY : DCR  |  CAMP BOOKING \n");
                    } else if (POJO.getDoctor_flag() == 1 && POJO.getCamp_flag() == 0 && POJO.getMeeting_flag() == 1 && POJO.getLeave_flag() == 0) {
                        String s = "";
                        s += "<b>" + "PLAN OF DAY :" + "</b>" + " DCR  |  MEETING <br /><br />";
                        s += "<b>" + "DCR Agenda: " + "</b>" + POJO.getCall_agenda().trim().substring(0, POJO.getCall_agenda().length() <= 40 ? POJO.getCall_agenda().length() - 1 : 40) + "<br />";
                        s += "<b>" + "Meeting Agenda: " + "</b>" + POJO.getMeeting_agenda().trim().substring(0, POJO.getMeeting_agenda().length() <= 40 ? POJO.getMeeting_agenda().length() - 1 : 40);
                        sub.setText(Html.fromHtml(s));
                        //sub.setText("PLAN OF DAY : DCR  |  MEETING");
                    } else if (POJO.getDoctor_flag() == 1 && POJO.getCamp_flag() == 0 && POJO.getMeeting_flag() == 0 && POJO.getLeave_flag() == 0) {
                        String s = "";
                        s += "<b>" + "PLAN OF DAY :" + "</b>" + " DCR <br /><br />";
                        s += "<b>" + "DCR Agenda: " + "</b>" + POJO.getCall_agenda().trim().substring(0, POJO.getCall_agenda().length() <= 40 ? POJO.getCall_agenda().length() - 1 : 40);
                        sub.setText(Html.fromHtml(s));
                        //sub.setText("PLAN OF DAY : DCR");
                    } else if (POJO.getDoctor_flag() == 0 && POJO.getCamp_flag() == 1 && POJO.getMeeting_flag() == 1 && POJO.getLeave_flag() == 0) {
                        String s = "";
                        s += "<b>" + "PLAN OF DAY :" + "</b>" + " CAMP BOOKING  |  MEETING <br /><br />";
                        s += "<b>" + "CAMP Agenda: " + "</b>" + POJO.getCamp_agenda().trim().substring(0, POJO.getCamp_agenda().length() <= 40 ? POJO.getCamp_agenda().length() - 1 : 40) + "<br />";
                        s += "<b>" + "Meeting Agenda: " + "</b>" + POJO.getMeeting_agenda().trim().substring(0, POJO.getMeeting_agenda().length() <= 40 ? POJO.getMeeting_agenda().length() - 1 : 40);
                        sub.setText(Html.fromHtml(s));
                        //sub.setText("PLAN OF DAY : CAMP BOOKING  |  MEETING");
                    } else if (POJO.getDoctor_flag() == 0 && POJO.getCamp_flag() == 1 && POJO.getMeeting_flag() == 0 && POJO.getLeave_flag() == 0) {
                        String s = "";
                        s += "<b>" + "PLAN OF DAY :" + "</b>" + " CAMP BOOKING <br /><br />";
                        s += "<b>" + "CAMP Agenda: " + "</b>" + POJO.getCamp_agenda().trim().substring(0, POJO.getCamp_agenda().length() <= 40 ? POJO.getCamp_agenda().length() - 1 : 40);
                        sub.setText(Html.fromHtml(s));
                        //sub.setText("PLAN OF DAY : CAMP BOOKING");
                    } else if (POJO.getDoctor_flag() == 0 && POJO.getCamp_flag() == 0 && POJO.getMeeting_flag() == 1 && POJO.getLeave_flag() == 0) {
                        String s = "";
                        s += "<b>" + "PLAN OF DAY :" + "</b>" + " MEETING <br /><br />";
                        s += "<b>" + "Meeting Agenda: " + "</b>" + POJO.getMeeting_agenda().trim().substring(0, POJO.getMeeting_agenda().length() <= 40 ? POJO.getMeeting_agenda().length() - 1 : 40);
                        sub.setText(Html.fromHtml(s));
                        //sub.setText("PLAN OF DAY : MEETING");
                    } else if (POJO.getDoctor_flag() == 0 && POJO.getCamp_flag() == 0 && POJO.getMeeting_flag() == 0 && POJO.getLeave_flag() == 1) {
                        String s = "";
                        s += "<b>" + "PLAN OF DAY : LEAVE" + "</b>";
                        sub.setText(Html.fromHtml(s));
                        //sub.setText("PLAN OF DAY : LEAVE");
                    } else {
                        String s = "";
                        s += "<b>" + "PLAN OF DAY : PLAN NOT CREATED" + "</b>";
                        sub.setText(Html.fromHtml(s));
                        //sub.setText("PLAN OF DAY : PLAN NOT CREATED");
                    }
                }
                //sub_second.setText("JFW with: " +POJO.getJwf_with());
                sub2.setText(" (POST ON: " + POJO.getCreation().substring(11, 16) + ")");
                tvname.setText(POJO.getUser());

            }

            return v;
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
            return v;
        }
    }
}
