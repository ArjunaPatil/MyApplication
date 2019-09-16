package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Doctor_Type_S extends RealmObject {
    @PrimaryKey
    private String name;
    private String type_name;
    private String modified;


   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
