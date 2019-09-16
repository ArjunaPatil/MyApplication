package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */
    /*Generate For whitelist Method User List Unlock Time Date*/
public class POJO_Stockist_List extends RealmObject {
    /////////////

    @PrimaryKey
    private String name;
    private String full_name;
    private String territory;


    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

}
