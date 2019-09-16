package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Doctor_Specialization extends RealmObject {
    @PrimaryKey
    private String name;
    private String degree_name;
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

    public String getDegree_Name() {
        return degree_name;
    }

    public void setDegree_name(String degree_name) {
        this.degree_name = degree_name;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
