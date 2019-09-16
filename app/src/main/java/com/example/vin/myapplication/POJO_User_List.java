package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */
    /*Generate For whitelist Method User List Unlock Time Date*/
public class POJO_User_List extends RealmObject {
    /////////////

    @PrimaryKey
    private String name;
    private String full_name;
    private String designation;
    private Integer enabled;
    private String modified;
    private String mobile_no1;
    private Integer trans_flag;
    private Integer mast_flag;

    public Integer getTrans_flag() {
        return trans_flag;
    }

    public void setTrans_flag(Integer trans_flag) {
        this.trans_flag = trans_flag;
    }

    public Integer getMast_flag() {
        return mast_flag;
    }

    public void setMast_flag(Integer mast_flag) {
        this.mast_flag = mast_flag;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
