package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Stockiest extends RealmObject {
    @PrimaryKey
    private String name;
    private String customer_short_name;
    private String food_license_no;
    private String creation;
    private String pincode;
    private String doctype;
    private Integer disabled;
    private String owner;
    private String customer_name;
    private String city;
    private String dlno21b;
    private String modified_by;
    private String district;
    private String vat_no;
    private String customer_type;
    private String state;
    private Integer credit_days;
    private Integer docstatus;
    private String territory;
    private String email;
    private String taluka;
    private String phone2;
    private Integer default_commission_rate;
    private String phone1;
    private String address;
    private String cst_no;
    private String dlno20b;
    private String excise_no;
    private Integer credit_limit;
    private Integer idx;
    private String credit_days_based_on;
    private String customer_group;
    private String modified;
    private Integer is_frozen;

    public String getCustomer_short_name() {
        return customer_short_name;
    }

    public void setCustomer_short_name(String customer_short_name) {
        this.customer_short_name = customer_short_name;
    }

    public String getFood_license_no() {
        return food_license_no;
    }

    public void setFood_license_no(String food_license_no) {
        this.food_license_no = food_license_no;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDlno21b() {
        return dlno21b;
    }

    public void setDlno21b(String dlno21b) {
        this.dlno21b = dlno21b;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getVat_no() {
        return vat_no;
    }

    public void setVat_no(String vat_no) {
        this.vat_no = vat_no;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getCredit_days() {
        return credit_days;
    }

    public void setCredit_days(Integer credit_days) {
        this.credit_days = credit_days;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public Integer getDefault_commission_rate() {
        return default_commission_rate;
    }

    public void setDefault_commission_rate(Integer default_commission_rate) {
        this.default_commission_rate = default_commission_rate;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCst_no() {
        return cst_no;
    }

    public void setCst_no(String cst_no) {
        this.cst_no = cst_no;
    }

    public String getDlno20b() {
        return dlno20b;
    }

    public void setDlno20b(String dlno20b) {
        this.dlno20b = dlno20b;
    }

    public String getExcise_no() {
        return excise_no;
    }

    public void setExcise_no(String excise_no) {
        this.excise_no = excise_no;
    }

    public Integer getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(Integer credit_limit) {
        this.credit_limit = credit_limit;
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

    public String getCredit_days_based_on() {
        return credit_days_based_on;
    }

    public void setCredit_days_based_on(String credit_days_based_on) {
        this.credit_days_based_on = credit_days_based_on;
    }

    public String getCustomer_group() {
        return customer_group;
    }

    public void setCustomer_group(String customer_group) {
        this.customer_group = customer_group;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Integer getIs_frozen() {
        return is_frozen;
    }

    public void setIs_frozen(Integer is_frozen) {
        this.is_frozen = is_frozen;
    }
}
