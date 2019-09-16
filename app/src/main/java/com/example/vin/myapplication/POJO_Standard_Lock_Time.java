package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Standard_Lock_Time extends RealmObject {

    @PrimaryKey
    private String name;
    private Integer profile_master;
    private Integer patch_master;
    private Integer doctor_master;
    private Integer chemist_master;
    private String objective_lock_time;
    private String modified;
    private String doctor_start_time;
    private String chemist_start_time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProfile_master() {
        return profile_master;
    }

    public void setProfile_master(Integer profile_master) {
        this.profile_master = profile_master;
    }

    public Integer getPatch_master() {
        return patch_master;
    }

    public void setPatch_master(Integer patch_master) {
        this.patch_master = patch_master;
    }

    public Integer getDoctor_master() {
        return doctor_master;
    }

    public void setDoctor_master(Integer doctor_master) {
        this.doctor_master = doctor_master;
    }

    public Integer getChemist_master() {
        return chemist_master;
    }

    public void setChemist_master(Integer chemist_master) {
        this.chemist_master = chemist_master;
    }

    public String getObjective_lock_time() {
        return objective_lock_time;
    }

    public void setObjective_lock_time(String objective_lock_time) {
        this.objective_lock_time = objective_lock_time;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getDoctor_start_time() {
        return doctor_start_time;
    }

    public void setDoctor_start_time(String doctor_start_time) {
        this.doctor_start_time = doctor_start_time;
    }

    public String getChemist_start_time() {
        return chemist_start_time;
    }

    public void setChemist_start_time(String chemist_start_time) {
        this.chemist_start_time = chemist_start_time;
    }

}

