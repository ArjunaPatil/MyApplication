package com.example.vin.myapplication;

import io.realm.RealmObject;

public class LiveWork extends RealmObject {


    private String name;
    private String select_date;
    private String joint_work_with;
    private String work_type;
    private String work_time;
    private String doctor_name;
    private String employee;
    private String select_doctor;
    private String meeting_note;


    public String getname() {  return this.name;   }
    public String getselect_date() {  return this.select_date;   }
    public String getjoint_work_with() {  return this.joint_work_with;   }
    public String getwork_type() {  return this.work_type;   }
    public String getwork_time() {  return this.work_time;   }
    public String getdoctor_name() {  return this.doctor_name;   }
    public String getemployee() {  return this.employee;   }
    public String getselect_doctor() {  return this.select_doctor;   }
    public String getmeeting_note() {  return this.meeting_note;   }









}