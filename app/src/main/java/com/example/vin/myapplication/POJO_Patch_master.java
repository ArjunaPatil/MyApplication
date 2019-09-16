package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Patch_master extends RealmObject {
    @PrimaryKey
    private String name;
    private String creation;
    private String headquarter;
    private Integer abm;
    private String owner;
    private String modified_by;
    private String employee_code;
    private Integer docstatus;
    /* private String doctype;*/
    private Integer zbm;
    private String patch_name;
    private String employee_name;
    private String patch_type;

    private Integer idx;
    private String modified;
    private Double km;
    private Integer rbm;
    private Integer active;
    private String user;
    private String user_name;
    private Integer sm;

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

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(String headquarter) {
        this.headquarter = headquarter;
    }

    public Integer getAbm() {
        return abm;
    }

    public void setAbm(Integer abm) {
        this.abm = abm;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    /* public String getDoctype() {
         return doctype;
     }

     public void setDoctype(String doctype) {
         this.doctype = doctype;
     }
 */
    public Integer getZbm() {
        return zbm;
    }

    public void setZbm(Integer zbm) {
        this.zbm = zbm;
    }

    public String getPatch_name() {
        return patch_name;
    }

    public void setPatch_name(String patch_name) {
        this.patch_name = patch_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getPatch_type() {
        return patch_type;
    }

    public void setPatch_type(String patch_type) {
        this.patch_type = patch_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public Integer getRbm() {
        return rbm;
    }

    public void setRbm(Integer rbm) {
        this.rbm = rbm;
    }

    public Integer getSm() {
        return sm;
    }

    public void setSm(Integer sm) {
        this.sm = sm;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }
}
