package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by vin on 03/02/2017.
 */

public class Class_Doctor_Master extends RealmObject {

    @Required
    private String name;
    private String doctor_name;

    public String getName() {
        return name;
    }

    public void setName(final String title) {
        this.name = name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(final String doctor_name) {
        this.doctor_name = doctor_name;
    }
}
