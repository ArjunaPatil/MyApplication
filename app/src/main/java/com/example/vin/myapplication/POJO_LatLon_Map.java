package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_LatLon_Map extends RealmObject {

    /*"name": "obje0000005789",
            "jwf_with": null,
            "time_call": "08:26",
            "longitude": "74.2194565",
            "patch_id": "",
            "latitude": "16.6584273",
            "jwf_with2": null,
            "subject": "Obj: PLAN OF DAY : DCR \nPatch:UCHGAV DCR Agenda:Today I'll try to do cash card For Dr Shashikant Shende"*/
    @PrimaryKey
    private String name;
    private String subject;
    private String time_call;
    private String longitude;
    private String latitude;
    private String patch_id;
    private String jwf_with;
    private String jwf_with2;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTime_call() {
        return time_call;
    }

    public void setTime_call(String time_call) {
        this.time_call = time_call;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getlatitude() {
        return latitude;
    }

    public void setlatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPatch_id() {
        return patch_id;
    }

    public void setPatch_id(String patch_id) {
        this.patch_id = patch_id;
    }

    public String getJwf_with() {
        return jwf_with;
    }

    public void setJwf_with(String jwf_with) {
        this.jwf_with = jwf_with;
    }

    public String getJwf_with2() {
        return jwf_with2;
    }

    public void setJwf_with2(String jwf_with2) {
        this.jwf_with2 = jwf_with2;
    }

}
