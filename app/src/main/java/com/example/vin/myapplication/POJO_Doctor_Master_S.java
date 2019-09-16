package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

import static com.example.vin.myapplication.R.id.actirab_l_cap;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Doctor_Master_S extends RealmObject {
    @PrimaryKey
    private String name;            //ok

    private String creation;
    private String doctor_name;
    private String doctype;
    private String owner;
    private String modified_by;
    private String employee_code;
    private String user;
    private String user_name;
    private String employee_name;
    private String patch;
    private String patch_name;
    private String modified;

    private Integer active;


    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public Integer getActirab_l_cap() {
        return actirab_l_cap;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPatch() {
        return patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }

    public String getPatch_name() {
        return patch_name;
    }

    public void setPatch_name(String patch_name) {
        this.patch_name = patch_name;
    }


    //private boolean active;
    private Integer inactive;
    private Integer campaign_book;

    /*public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }*/

    public Integer getInactive() {
        return inactive;
    }

    public void setInactive(Integer inactive) {
        this.inactive = inactive;
    }

    public Integer getCampaign() {
        return campaign_book;
    }

    public void setCampaign(Integer campaign_book) {
        this.campaign_book = campaign_book;
    }

}
