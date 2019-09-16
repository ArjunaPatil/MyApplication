package com.example.vin.myapplication;

import io.realm.RealmObject;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Miss_OBJ extends RealmObject {

    private String mobno;
    private String emp_name;
    private String email;
    private String designation;

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

}
