package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Employee extends RealmObject {
    @PrimaryKey
    private String name;
    private String code;
    private String company_email;
    private String personal_email;
    private String date_of_birth;
    private String employee;
    private String current_address;
    private String designation;
    private String gender;
    private String marital_status;
    private String taluka;
    private String prefered_email;
    private String state;
    private String employee_name;
    private String panno;
    private Integer idx;

    private Integer ctc;
    private String city;
    private String user_id;
    private String district;
    private String bank_name;
    private String bank_ac_no;
    private String cell_number;
    private String pin;
    private String permanent_address;
    private String maritalstatus;
    private String emergency_phone_number;
    private String salary_mode;
    private String ifsccode;
    private String department;
    private Integer docstatus;
    private String status;
    private String date_of_joining;
    private String sm;
    private String zbm;
    private String rbm;
    private String abm;
    private String zone;
    private String region;
    private String area;
    private String hq;

    private String sm_name;
    private String zbm_name;
    private String rbm_name;
    private String abm_name;
    private String zone_name;
    private String region_name;
    private String area_name;
    private String hq_name;
    private String modified;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCompany_email() {
        return company_email;
    }

    public void setCompany_email(String company_email) {
        this.company_email = company_email;
    }

    public String getPersonal_email() {
        return personal_email;
    }

    public void setPersonal_email(String personal_email) {
        this.personal_email = personal_email;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getCurrent_address() {
        return current_address;
    }

    public void setCurrent_address(String current_address) {
        this.current_address = current_address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getPrefered_email() {
        return prefered_email;
    }

    public void setPrefered_email(String prefered_email) {
        this.prefered_email = prefered_email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }



    public Integer getCtc() {
        return ctc;
    }

    public void setCtc(Integer ctc) {
        this.ctc = ctc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_ac_no() {
        return bank_ac_no;
    }

    public void setBank_ac_no(String bank_ac_no) {
        this.bank_ac_no = bank_ac_no;
    }

    public String getCell_number() {
        return cell_number;
    }

    public void setCell_number(String cell_number) {
        this.cell_number = cell_number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPermanent_address() {
        return permanent_address;
    }

    public void setPermanent_address(String permanent_address) {
        this.permanent_address = permanent_address;
    }

    public String getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(String maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getEmergency_phone_number() {
        return emergency_phone_number;
    }

    public void setEmergency_phone_number(String emergency_phone_number) {
        this.emergency_phone_number = emergency_phone_number;
    }

    public String getSalary_mode() {
        return salary_mode;
    }

    public void setSalary_mode(String salary_mode) {
        this.salary_mode = salary_mode;
    }

    public String getIfsccode() {
        return ifsccode;
    }

    public void setIfsccode(String ifsccode) {
        this.ifsccode = ifsccode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate_of_joining() {
        return date_of_joining;
    }

    public void setDate_of_joining(String date_of_joining) {
        this.date_of_joining = date_of_joining;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getZbm() {
        return zbm;
    }

    public void setZbm(String zbm) {
        this.zbm = zbm;
    }

    public String getRbm() {
        return rbm;
    }

    public void setRbm(String rbm) {
        this.rbm = rbm;
    }

    public String getAbm() {
        return abm;
    }

    public void setAbm(String abm) {
        this.abm = abm;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHq() {
        return hq;
    }

    public void setHq(String hq) {
        this.hq = hq;
    }



    public String getSm_name() {
        return sm_name;
    }

    public void setSm_name(String sm_name) {
        this.sm_name = sm_name;
    }

    public String getZbm_name() {
        return zbm_name;
    }

    public void setZbm_name(String zbm_name) {
        this.zbm_name = zbm_name;
    }

    public String getRbm_name() {
        return rbm_name;
    }

    public void setRbm_name(String rbm_name) {
        this.rbm_name = rbm_name;
    }

    public String getAbm_name() {
        return abm_name;
    }

    public void setAbm_name(String abm_name) {
        this.abm_name = abm_name;
    }

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getHq_name() {
        return hq_name;
    }

    public void setHq_name(String hq_name) {
        this.hq_name = hq_name;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

}
