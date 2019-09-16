package com.example.vin.myapplication;

import io.realm.RealmObject;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Territory extends RealmObject {

    private String name;
    private String modified_by;
    private String parent;
    private Integer lft;
    private String creation;
    private Integer is_group;
    private String modified;
    private String territory_name;
    private Integer rgt;
    private String old_parent;
    private Integer idx;
    private String doctype;
    private String owner;
    private Integer docstatus;
    private String parent_territory;

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Integer getLft() {
        return lft;
    }

    public void setLft(Integer lft) {
        this.lft = lft;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public Integer getIs_group() {
        return is_group;
    }

    public void setIs_group(Integer is_group) {
        this.is_group = is_group;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getTerritory_name() {
        return territory_name;
    }

    public void setTerritory_name(String territory_name) {
        this.territory_name = territory_name;
    }

    public Integer getRgt() {
        return rgt;
    }

    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }

    public String getOld_parent() {
        return old_parent;
    }

    public void setOld_parent(String old_parent) {
        this.old_parent = old_parent;
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getParent_territory() {
        return parent_territory;
    }

    public void setParent_territory(String parent_territory) {
        this.parent_territory = parent_territory;
    }

}
