//com.example.POJOEmployeeDailyPlan.java-----------------------------------

//package com.example;
package com.example.vin.myapplication;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class POJO_Employee_Daily_Plan extends RealmObject {
    @PrimaryKey
    private String name;
    private String employee_code;
    private String employee_name;
    private String patch1;
    private String patch2;
    private String meeting_with;
    private String select_date;

    private String objective_of_the_day;
    private String approve_by;
    private String metting_note;
    private String camp_note;
    private String cme_note;
    private String leave_reason;

    private Integer meeting;
    private Integer cme;
    private Integer camp;
    private Integer dcr;
    private Integer lve;
    private Integer approve;
    private Integer active;
    private Integer lock_for_edit_and_delete;

    //not importatnt
    private String creation;
    private Integer docstatus;
    private String modified;
    private String modified_by;
    private String doctype;
    private String owner;
    private Integer idx;


    //private List<Object> patchRelation = null;
    /*private Map<String, Object> additionalProperties = new HashMap<String, Object>();*/

    public String getcme_note() {
        return cme_note;
    }

    public void setcme_note(String cme_note) {
        this.cme_note = cme_note;
    }

    public String getLeave_reason() {
        return leave_reason;
    }

    public void setLeave_reason(String leave_reason) {
        this.leave_reason = leave_reason;
    }

    public Integer getlock_for_edit_and_delete() {
        return lock_for_edit_and_delete;
    }

    public void setlock_for_edit_and_delete(Integer lock_for_edit_and_delete) {
        this.lock_for_edit_and_delete = lock_for_edit_and_delete;
    }

    /*public List<Object> getPatchRelation() {
        return patchRelation;
    }

    public void setPatchRelation(List<Object> patchRelation) {
        this.patchRelation = patchRelation;
    }
*/
    public String getpatch1() {
        return patch1;
    }

    public void setpatch1(String patch1) {
        this.patch1 = patch1;
    }

    public String getpatch2() {
        return patch2;
    }

    public void setpatch2(String patch2) {
        this.patch2 = patch2;
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

    public String getmodified_by() {
        return modified_by;
    }

    public void setmodified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getemployee_code() {
        return employee_code;
    }

    public void setemployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getmeeting_with() {
        return meeting_with;
    }

    public void setmeeting_with(String meeting_with) {
        this.meeting_with = meeting_with;
    }

    public String getselect_date() {
        return select_date;
    }

    public void setselect_date(String select_date) {
        this.select_date = select_date;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public Integer getMeeting() {
        return meeting;
    }

    public void setMeeting(Integer meeting) {
        this.meeting = meeting;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getemployee_name() {
        return employee_name;
    }

    public void setemployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getDcr() {
        return dcr;
    }

    public void setDcr(Integer dcr) {
        this.dcr = dcr;
    }

    public String getobjective_of_the_day() {
        return objective_of_the_day;
    }

    public void setobjective_of_the_day(String objective_of_the_day) {
        this.objective_of_the_day = objective_of_the_day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getapprove_by() {
        return approve_by;
    }

    public void setapprove_by(String approve_by) {
        this.approve_by = approve_by;
    }

    public String getmetting_note() {
        return metting_note;
    }

    public void setmetting_note(String metting_note) {
        this.metting_note = metting_note;
    }

    public Integer getCme() {
        return cme;
    }

    public void setCme(Integer cme) {
        this.cme = cme;
    }

    public Integer getCamp() {
        return camp;
    }

    public void setCamp(Integer camp) {
        this.camp = camp;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getcamp_note() {
        return camp_note;
    }

    public void setcamp_note(String camp_note) {
        this.camp_note = camp_note;
    }

    public Integer getlve() {
        return lve;
    }

    public void setlve(Integer lve) {
        this.lve = lve;
    }

    public Integer getapprove() {
        return approve;
    }

    public void setapprove(Integer approve) {
        this.approve = approve;
    }

    /*public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }*/

}