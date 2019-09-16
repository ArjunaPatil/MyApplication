package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 10/06/2017.
 */

public class POJO_objective_S extends RealmObject {
    @PrimaryKey
    private String name;
    private String modified_by;
    private String creation;
    private String modified;
    private String select_date;
    private Integer idx;
    private String objective;
    private String user;
    private String doctype;
    private String owner;
    private Integer docstatus;
    private String user_name;


    ///////////////////////

    private String camp_jfw_with2;
    private String camp_jfw_with_name1;
    private String select_patch_name;
    private String camp_jfw_with1;
    private String doctor_name;
    private String dcr_jfw_with2_name;
    private String camp_agenda;
    private Integer leave_flag;
    private Integer leave_approval;
    private String doctor;
    private String plan_approved_by;
    private String meeting_with;
    private String dcr_jfw_with1;
    private String dcr_jfw_with2;
    private String dcr_jfw_with1_name;
    private String meeting_agenda;
    private String camp_jfw_with_name2;
    private Integer leave_type3;
    private Integer leave_type1;
    private Integer plan_approve;
    private String reason;
    private Integer meeting_flag;
    private Integer doctor_flag;
    private String call_agenda;
    private Integer leave_type2;
    private String leave_approved_by;
    private Integer camp_flag;
    private String place;
    private String select_patch;

    public String getCamp_jfw_with2() {
        return camp_jfw_with2;
    }

    public void setCamp_jfw_with2(String camp_jfw_with2) {
        this.camp_jfw_with2 = camp_jfw_with2;
    }

    public String getCamp_jfw_with_name1() {
        return camp_jfw_with_name1;
    }

    public void setCamp_jfw_with_name1(String camp_jfw_with_name1) {
        this.camp_jfw_with_name1 = camp_jfw_with_name1;
    }

    public String getSelect_patch_name() {
        return select_patch_name;
    }

    public void setSelect_patch_name(String select_patch_name) {
        this.select_patch_name = select_patch_name;
    }

    public String getCamp_jfw_with1() {
        return camp_jfw_with1;
    }

    public void setCamp_jfw_with1(String camp_jfw_with1) {
        this.camp_jfw_with1 = camp_jfw_with1;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDcr_jfw_with2_name() {
        return dcr_jfw_with2_name;
    }

    public void setDcr_jfw_with2_name(String dcr_jfw_with2_name) {
        this.dcr_jfw_with2_name = dcr_jfw_with2_name;
    }

    public String getCamp_agenda() {
        return camp_agenda;
    }

    public void setCamp_agenda(String camp_agenda) {
        this.camp_agenda = camp_agenda;
    }

    public Integer getLeave_flag() {
        return leave_flag;
    }

    public void setLeave_flag(Integer leave_flag) {
        this.leave_flag = leave_flag;
    }

    public Integer getLeave_approval() {
        return leave_approval;
    }

    public void setLeave_approval(Integer leave_approval) {
        this.leave_approval = leave_approval;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getPlan_approved_by() {
        return plan_approved_by;
    }

    public void setPlan_approved_by(String plan_approved_by) {
        this.plan_approved_by = plan_approved_by;
    }

    public String getMeeting_with() {
        return meeting_with;
    }

    public void setMeeting_with(String meeting_with) {
        this.meeting_with = meeting_with;
    }

    public String getDcr_jfw_with1() {
        return dcr_jfw_with1;
    }

    public void setDcr_jfw_with1(String dcr_jfw_with1) {
        this.dcr_jfw_with1 = dcr_jfw_with1;
    }

    public String getDcr_jfw_with2() {
        return dcr_jfw_with2;
    }

    public void setDcr_jfw_with2(String dcr_jfw_with2) {
        this.dcr_jfw_with2 = dcr_jfw_with2;
    }

    public String getDcr_jfw_with1_name() {
        return dcr_jfw_with1_name;
    }

    public void setDcr_jfw_with1_name(String dcr_jfw_with1_name) {
        this.dcr_jfw_with1_name = dcr_jfw_with1_name;
    }

    public String getMeeting_agenda() {
        return meeting_agenda;
    }

    public void setMeeting_agenda(String meeting_agenda) {
        this.meeting_agenda = meeting_agenda;
    }

    public String getCamp_jfw_with_name2() {
        return camp_jfw_with_name2;
    }

    public void setCamp_jfw_with_name2(String camp_jfw_with_name2) {
        this.camp_jfw_with_name2 = camp_jfw_with_name2;
    }

    public Integer getLeave_type3() {
        return leave_type3;
    }

    public void setLeave_type3(Integer leave_type3) {
        this.leave_type3 = leave_type3;
    }

    public Integer getLeave_type1() {
        return leave_type1;
    }

    public void setLeave_type1(Integer leave_type1) {
        this.leave_type1 = leave_type1;
    }

    public Integer getPlan_approve() {
        return plan_approve;
    }

    public void setPlan_approve(Integer plan_approve) {
        this.plan_approve = plan_approve;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getMeeting_flag() {
        return meeting_flag;
    }

    public void setMeeting_flag(Integer meeting_flag) {
        this.meeting_flag = meeting_flag;
    }

    public Integer getDoctor_flag() {
        return doctor_flag;
    }

    public void setDoctor_flag(Integer doctor_flag) {
        this.doctor_flag = doctor_flag;
    }

    public String getCall_agenda() {
        return call_agenda;
    }

    public void setCall_agenda(String call_agenda) {
        this.call_agenda = call_agenda;
    }

    public Integer getLeave_type2() {
        return leave_type2;
    }

    public void setLeave_type2(Integer leave_type2) {
        this.leave_type2 = leave_type2;
    }

    public String getLeave_approved_by() {
        return leave_approved_by;
    }

    public void setLeave_approved_by(String leave_approved_by) {
        this.leave_approved_by = leave_approved_by;
    }

    public Integer getCamp_flag() {
        return camp_flag;
    }

    public void setCamp_flag(Integer camp_flag) {
        this.camp_flag = camp_flag;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSelect_patch() {
        return select_patch;
    }

    public void setSelect_patch(String select_patch) {
        this.select_patch = select_patch;
    }


    ////////////////////////////////////---------------------------//////////////////////////


    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getSelect_date() {
        return select_date;
    }

    public void setSelect_date(String select_date) {
        this.select_date = select_date;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}
