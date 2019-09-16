package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Doctor_Specialization_S extends RealmObject {
    @PrimaryKey
    private String name;
    private String specialization;
    private String modified;
//    public POJO_Doctor_Degree()
//    {
//
//    }
//
//    public POJO_Doctor_Degree(String name)
//    {
//        this.name=name;
//    }

   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
