package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_SpinnerBind_EmployeeCodeName extends RealmObject {
    @PrimaryKey
    private String employee_code;
    private String employee_name;

    public POJO_SpinnerBind_EmployeeCodeName() {
    }

    public POJO_SpinnerBind_EmployeeCodeName(String employee_code, String employee_name) {
        this.employee_code = employee_code;
        this.employee_name = employee_name;
    }


    public String getemployee_code() {
        return employee_code;
    }

    public void setemployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getemployee_name() {
        return employee_name;
    }

    public void setemployee_name(String employee_name) {
        this.employee_name = employee_name;
    }


    //to display object as a string in spinner
    @Override
    public String toString() {
        return employee_name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof POJO_SpinnerBind_EmployeeCodeName) {
            POJO_SpinnerBind_EmployeeCodeName c = (POJO_SpinnerBind_EmployeeCodeName) obj;
            if (c.getemployee_name().equals(employee_name) && c.getemployee_code() == employee_code)
                return true;
        }

        return false;
    }
}
