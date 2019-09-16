package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Stockiest_Users extends RealmObject
{
    @PrimaryKey
    private String name;
    private String last_name;
    private String stockiest_name;
    private String creation;
    private String user_type;
    private String doctype;
    private String owner;
    private String first_name;
    private String modified_by;
    private Integer docstatus;
    private String email;
    private String workflow_state;
    private String request_flag;
    private String address;
    private String middle_name;
    private Integer idx;
    private String modified;
    private String mobile_no1;
    private String stockiest_id;

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStockiest_name() {
        return stockiest_name;
    }

    public void setStockiest_name(String stockiest_name) {
        this.stockiest_name = stockiest_name;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkflow_state() {
        return workflow_state;
    }

    public void setWorkflow_state(String workflow_state) {
        this.workflow_state = workflow_state;
    }

    public String getRequest_flag() {
        return request_flag;
    }

    public void setRequest_flag(String request_flag) {
        this.request_flag = request_flag;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
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

    public String getMobile_no1() {
        return mobile_no1;
    }

    public void setMobile_no1(String mobile_no1) {
        this.mobile_no1 = mobile_no1;
    }

    public String getStockiest_id() {
        return stockiest_id;
    }

    public void setStockiest_id(String stockiest_id) {
        this.stockiest_id = stockiest_id;
    }
}
