package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_User_S extends RealmObject {
    /////////////
    @PrimaryKey
    private String name;
    private String full_name;
    private String owner;
    private String last_known_versions;

    private Integer thread_notify;
    private String first_name;
    private String modified_by;
    private String background_style;
    private String district;
    private String area;
    private String area_name;
    private String employee_code;
    private String new_password;
    private String pan_no;
    private String rbm;
    private String rbm_name;
    private String designation;
    private String branch;
    private String ifsc_code;
    private Integer idx;
    private String state;
    private String last_login;
    private Integer unsubscribed;
    private Integer lock_enable;

    private Integer docstatus;
    private String zone;
    private String zone_name;
    private String type;
    private String email;
    private String bank_account_no;
    private String username;
    private String last_ip;
    private String sm;
    private String sm_name;
    private String nbm;
    private String nbm_name;
    private String city;
    private String zbm;
    private String zbm_name;
    private String user_type;
    private String aadhar_card_no;

    private String address;
    private String pincode;
    private Integer login_after;
    private Integer send_welcome_email;
    private String doctype;

    private String language;
    private String gender;
    private String region;
    private String region_name;
    private Integer login_before;
    private Integer enabled;
    private String modified;
    private String mobile_no1;
    private String frappe_userid;
    private String mobile_no2;
    private String crm;
    private String crm_name;

    private String user_image;
    private String last_name;
    private String last_active;
    private String bank_name;
    private String headquarter;
    private String headquarter_name;
    private Integer simultaneous_sessions;
    private String abm;
    private String abm_name;
    private String creation;
    private Integer send_password_update_notification;
    private Integer mute_sounds;
    private String middle_name;

    private String stockiest;

    private Integer allow_data_list_after_disable;

    public Integer getAllow_data_list_after_disable() {
        return allow_data_list_after_disable;
    }

    public void setAllow_data_list_after_disable(Integer allow_data_list_after_disable) {
        this.allow_data_list_after_disable = allow_data_list_after_disable;
    }


    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLast_known_versions() {
        return last_known_versions;
    }

    public void setLast_known_versions(String last_known_versions) {
        this.last_known_versions = last_known_versions;
    }


    public Integer getThread_notify() {
        return thread_notify;
    }

    public void setThread_notify(Integer thread_notify) {
        this.thread_notify = thread_notify;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getBackground_style() {
        return background_style;
    }

    public void setBackground_style(String background_style) {
        this.background_style = background_style;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getPan_no() {
        return pan_no;
    }

    public void setPan_no(String pan_no) {
        this.pan_no = pan_no;
    }

    public String getRbm() {
        return rbm;
    }

    public void setRbm(String rbm) {
        this.rbm = rbm;
    }

    public String getRbm_name() {
        return rbm_name;
    }

    public void setRbm_name(String rbm_name) {
        this.rbm_name = rbm_name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public Integer getUnsubscribed() {
        return unsubscribed;
    }

    public void setUnsubscribed(Integer unsubscribed) {
        this.unsubscribed = unsubscribed;
    }

    public Integer getLock_enable() {
        return lock_enable;
    }

    public void setLock_enable(Integer lock_enable) {
        this.lock_enable = lock_enable;
    }


    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getZone_name() {
        return zone_name;
    }

    public void setZone_name(String zone_name) {
        this.zone_name = zone_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBank_account_no() {
        return bank_account_no;
    }

    public void setBank_account_no(String bank_account_no) {
        this.bank_account_no = bank_account_no;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLast_ip() {
        return last_ip;
    }

    public void setLast_ip(String last_ip) {
        this.last_ip = last_ip;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }

    public String getSm_name() {
        return sm_name;
    }

    public void setSm_name(String sm_name) {
        this.sm_name = sm_name;
    }

    public String getNbm() {
        return nbm;
    }

    public void setNbm(String nbm) {
        this.nbm = nbm;
    }

    public String getNbm_name() {
        return nbm_name;
    }

    public void setNbm_name(String nbm_name) {
        this.nbm_name = nbm_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZbm() {
        return zbm;
    }

    public void setZbm(String zbm) {
        this.zbm = zbm;
    }

    public String getZbm_name() {
        return zbm_name;
    }

    public void setZbm_name(String zbm_name) {
        this.zbm_name = zbm_name;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getAadhar_card_no() {
        return aadhar_card_no;
    }

    public void setAadhar_card_no(String aadhar_card_no) {
        this.aadhar_card_no = aadhar_card_no;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public Integer getLogin_after() {
        return login_after;
    }

    public void setLogin_after(Integer login_after) {
        this.login_after = login_after;
    }

    public Integer getSend_welcome_email() {
        return send_welcome_email;
    }

    public void setSend_welcome_email(Integer send_welcome_email) {
        this.send_welcome_email = send_welcome_email;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public Integer getLogin_before() {
        return login_before;
    }

    public void setLogin_before(Integer login_before) {
        this.login_before = login_before;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getMobile_no1() {
        return mobile_no1;
    }

    public void setMobile_no1(String mobile_no1) {
        this.mobile_no1 = mobile_no1;
    }

    public String getFrappe_userid() {
        return frappe_userid;
    }

    public void setFrappe_userid(String frappe_userid) {
        this.frappe_userid = frappe_userid;
    }

    public String getMobile_no2() {
        return mobile_no2;
    }

    public void setMobile_no2(String mobile_no2) {
        this.mobile_no2 = mobile_no2;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public String getCrm_name() {
        return crm_name;
    }

    public void setCrm_name(String crm_name) {
        this.crm_name = crm_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLast_active() {
        return last_active;
    }

    public void setLast_active(String last_active) {
        this.last_active = last_active;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(String headquarter) {
        this.headquarter = headquarter;
    }

    public String getHeadquarter_name() {
        return headquarter_name;
    }

    public void setHeadquarter_name(String headquarter_name) {
        this.headquarter_name = headquarter_name;
    }


    public Integer getSimultaneous_sessions() {
        return simultaneous_sessions;
    }

    public void setSimultaneous_sessions(Integer simultaneous_sessions) {
        this.simultaneous_sessions = simultaneous_sessions;
    }

    public String getAbm() {
        return abm;
    }

    public void setAbm(String abm) {
        this.abm = abm;
    }

    public String getAbm_name() {
        return abm_name;
    }

    public void setAbm_name(String abm_name) {
        this.abm_name = abm_name;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public Integer getSend_password_update_notification() {
        return send_password_update_notification;
    }

    public void setSend_password_update_notification(Integer send_password_update_notification) {
        this.send_password_update_notification = send_password_update_notification;
    }

    public Integer getMute_sounds() {
        return mute_sounds;
    }

    public void setMute_sounds(Integer mute_sounds) {
        this.mute_sounds = mute_sounds;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getStockiest() {
        return stockiest;
    }

    public void setStockiest(String stockiest) {
        this.stockiest = stockiest;
    }


    ////////////////////
    public POJO_User_S() {

    }

    public POJO_User_S(String name, String full_name) {
        this.name = name;
        this.full_name = full_name;
    }


}
