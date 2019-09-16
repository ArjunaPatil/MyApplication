package com.example.vin.myapplication;

import io.realm.RealmObject;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Territory_S extends RealmObject {

    private String headquarter_id;
    private String headquarter_name;
    private String headquarter_parent;


    public String getHeadquarter_id() {
        return headquarter_id;
    }

    public void setHeadquarter_id(String headquarter_id) {
        this.headquarter_id = headquarter_id;
    }

    public String getHeadquarter_name() {
        return headquarter_name;
    }

    public void setHeadquarter_name(String headquarter_name) {
        this.headquarter_name = headquarter_name;
    }

    public String getHeadquarter_parent() {
        return headquarter_parent;
    }

    public void setHeadquarter_parent(String headquarter_parent) {
        this.headquarter_parent = headquarter_parent;
    }
}
