package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_campaign_booking_S extends RealmObject {
    @PrimaryKey
    private String name;

    private String creation;
    private String campaign_id;
    private String qty;
    private String invoice_number;
    private String owner;
    private String campaign_name;
    private String total_qty;
    private String any_comment;
    private String modified_by;
    private String chemist_name;
    private Integer docstatus;
    private String user_name;
    private String status;
    private String stockist_id;
    private String hq_name;
    private String free;
    private String doctype;
    private String date;
    private String user_id;
    private String total_amount;
    private String stockist_name;

    private Integer idx;
    private String modified;
    private String chemist_id;
    private String call_by_user_id;
    private String doctor__id;
    private String doctor_name;

    public String getDoctor__id() {
        return doctor__id;
    }

    public void setDoctor__id(String doctor__id) {
        this.doctor__id = doctor__id;
    }



    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }



    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }

    public String getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(String campaign_id) {
        this.campaign_id = campaign_id;
    }
    public String getCall_by_user_id() {
        return call_by_user_id;
    }

    public void setCall_by_user_id(String call_by_user_id) {
        this.call_by_user_id = call_by_user_id;
    }
    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCampaign_name() {
        return campaign_name;
    }

    public void setCampaign_name(String campaign_name) {
        this.campaign_name = campaign_name;
    }

    public String getTotal_qty() {
        return total_qty;
    }

    public void setTotal_qty(String total_qty) {
        this.total_qty = total_qty;
    }

    public String getAny_comment() {
        return any_comment;
    }

    public void setAny_comment(String any_comment) {
        this.any_comment = any_comment;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getChemist_name() {
        return chemist_name;
    }

    public void setChemist_name(String chemist_name) {
        this.chemist_name = chemist_name;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStockist_id() {
        return stockist_id;
    }

    public void setStockist_id(String stockist_id) {
        this.stockist_id = stockist_id;
    }

    public String getHq_name() {
        return hq_name;
    }

    public void setHq_name(String hq_name) {
        this.hq_name = hq_name;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getStockist_name() {
        return stockist_name;
    }

    public void setStockist_name(String stockist_name) {
        this.stockist_name = stockist_name;
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

    public String getChemist_id() {
        return chemist_id;
    }

    public void setChemist_id(String chemist_id) {
        this.chemist_id = chemist_id;
    }




}
