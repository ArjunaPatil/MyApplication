package com.example.vin.myapplication;

import io.realm.RealmObject;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Presenty extends RealmObject {

    private Integer count;
    private Integer cmp_bk;
    private String designation;
    private String emp_name;
    private Integer chec;
    private String select_date;
    private String obj;
    private Integer drc;
    private String presenty;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCmp_bk() {
        return cmp_bk;
    }

    public void setCmp_bk(Integer cmp_bk) {
        this.cmp_bk = cmp_bk;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public Integer getChec() {
        return chec;
    }

    public void setChec(Integer chec) {
        this.chec = chec;
    }

    public String getSelect_date() {
        return select_date;
    }

    public void setSelect_date(String select_date) {
        this.select_date = select_date;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public Integer getDrc() {
        return drc;
    }

    public void setDrc(Integer drc) {
        this.drc = drc;
    }

    public String getPresenty() {
        return presenty;
    }

    public void setPresenty(String presenty) {
        this.presenty = presenty;
    }

}
