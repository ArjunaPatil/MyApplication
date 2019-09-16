package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Doctor_Master extends RealmObject {
    @PrimaryKey
    private String name;            //ok

    private String creation;
    private String doctor_name;
    private String doctype;
    private String owner;
    private String hospital_name;
    private String city;
    private String modified_by;
    private String zone;
    private String area;
    private String employee_code;
    private String reg_no;
    private String per_mobile;
    private String doctor_specialization;
    private String email;
    private String doctor_type;
    private String per_phone;
    private String degree;
    private String hq;
    private String latitude;
    private String user;
    private String user_name;
    private String employee_name;
    private String pin_code;
    private String region;
    private String modified;
    private String longitude;
    private String patch;
    private String patch_name;
    private String address;

    private Integer wego_gel_30_mg;
    private Integer stand_sp_tab;
    private Integer actirab_l_cap;
    private Integer core_of_tbm;
    private Integer empower_od_tab;
    private Integer lycorest_60ml_susp;
    private Integer stand_mf_60ml_susp;
    private Integer core_of_zbm;
    private Integer lycorest_tab;
    private Integer actirab_tab;
    private Integer lycort_1ml_inj;
    private Integer docstatus;
    private Integer lycolic_10ml_drops;
    private Integer core_of_abm;
    private Integer start_t_tab;
    private Integer regain_xt_tab;
    private Integer active;
    private Integer actirab_d_cap;
    private Integer actirab_dv_cap;
    private Integer ten_on_30ml;
    private Integer trygesic_tab;
    //private String name;
    private Integer idx;
    private Integer wego_gel_20_mg;
    private Integer glucolyst_g1_tab;

    /*------------------------------------------------------------------------*/
    private Integer trygesic_ptab;
    private Integer cipgrow_syrup;
    private Integer korby_soap;
    private Integer actirest_dx;
    private Integer altipan_dsr;
    private Integer actirest_ls;
    private Integer sangria_tonic;
    private Integer levocast_m;
    private Integer onederm_cream;
    private Integer clavyten_625;

    private Integer itezone_200_cap;
    private Integer nextvit_tab;

    public Integer getWego_gel_30_mg() {
        return wego_gel_30_mg;
    }

    public void setWego_gel_30_mg(Integer wego_gel_30_mg) {
        this.wego_gel_30_mg = wego_gel_30_mg;
    }

    public Integer getStand_sp_tab() {
        return stand_sp_tab;
    }

    public void setStand_sp_tab(Integer stand_sp_tab) {
        this.stand_sp_tab = stand_sp_tab;
    }

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

    public void setActirab_l_cap(Integer actirab_l_cap) {
        this.actirab_l_cap = actirab_l_cap;
    }

    public Integer getCore_of_tbm() {
        return core_of_tbm;
    }

    public void setCore_of_tbm(Integer core_of_tbm) {
        this.core_of_tbm = core_of_tbm;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public Integer getEmpower_od_tab() {
        return empower_od_tab;
    }

    public void setEmpower_od_tab(Integer empower_od_tab) {
        this.empower_od_tab = empower_od_tab;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getLycorest_60ml_susp() {
        return lycorest_60ml_susp;
    }

    public void setLycorest_60ml_susp(Integer lycorest_60ml_susp) {
        this.lycorest_60ml_susp = lycorest_60ml_susp;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getStand_mf_60ml_susp() {
        return stand_mf_60ml_susp;
    }

    public void setStand_mf_60ml_susp(Integer stand_mf_60ml_susp) {
        this.stand_mf_60ml_susp = stand_mf_60ml_susp;
    }

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getPer_mobile() {
        return per_mobile;
    }

    public void setPer_mobile(String per_mobile) {
        this.per_mobile = per_mobile;
    }

    public Integer getCore_of_zbm() {
        return core_of_zbm;
    }

    public void setCore_of_zbm(Integer core_of_zbm) {
        this.core_of_zbm = core_of_zbm;
    }

    public Integer getLycorest_tab() {
        return lycorest_tab;
    }

    public void setLycorest_tab(Integer lycorest_tab) {
        this.lycorest_tab = lycorest_tab;
    }

    public Integer getActirab_tab() {
        return actirab_tab;
    }

    public void setActirab_tab(Integer actirab_tab) {
        this.actirab_tab = actirab_tab;
    }

    public Integer getLycort_1ml_inj() {
        return lycort_1ml_inj;
    }

    public void setLycort_1ml_inj(Integer lycort_1ml_inj) {
        this.lycort_1ml_inj = lycort_1ml_inj;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getDoctor_specialization() {
        return doctor_specialization;
    }

    public void setDoctor_specialization(String doctor_specialization) {
        this.doctor_specialization = doctor_specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoctor_type() {
        return doctor_type;
    }

    public void setDoctor_type(String doctor_type) {
        this.doctor_type = doctor_type;
    }

    public String getPer_phone() {
        return per_phone;
    }

    public void setPer_phone(String per_phone) {
        this.per_phone = per_phone;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getLycolic_10ml_drops() {
        return lycolic_10ml_drops;
    }

    public void setLycolic_10ml_drops(Integer lycolic_10ml_drops) {
        this.lycolic_10ml_drops = lycolic_10ml_drops;
    }

    public String getHq() {
        return hq;
    }

    public void setHq(String hq) {
        this.hq = hq;
    }

    public Integer getCore_of_abm() {
        return core_of_abm;
    }

    public void setCore_of_abm(Integer core_of_abm) {
        this.core_of_abm = core_of_abm;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getStart_t_tab() {
        return start_t_tab;
    }

    public void setStart_t_tab(Integer start_t_tab) {
        this.start_t_tab = start_t_tab;
    }

    public Integer getRegain_xt_tab() {
        return regain_xt_tab;
    }

    public void setRegain_xt_tab(Integer regain_xt_tab) {
        this.regain_xt_tab = regain_xt_tab;
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

    public Integer getActirab_d_cap() {
        return actirab_d_cap;
    }

    public void setActirab_d_cap(Integer actirab_d_cap) {
        this.actirab_d_cap = actirab_d_cap;
    }

    public Integer getActirab_dv_cap() {
        return actirab_dv_cap;
    }

    public void setActirab_dv_cap(Integer actirab_dv_cap) {
        this.actirab_dv_cap = actirab_dv_cap;
    }

    public Integer getTen_on_30ml() {
        return ten_on_30ml;
    }

    public void setTen_on_30ml(Integer ten_on_30ml) {
        this.ten_on_30ml = ten_on_30ml;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public Integer getTrygesic_tab() {
        return trygesic_tab;
    }

    public void setTrygesic_tab(Integer trygesic_tab) {
        this.trygesic_tab = trygesic_tab;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public Integer getWego_gel_20_mg() {
        return wego_gel_20_mg;
    }

    public void setWego_gel_20_mg(Integer wego_gel_20_mg) {
        this.wego_gel_20_mg = wego_gel_20_mg;
    }

    public Integer getGlucolyst_g1_tab() {
        return glucolyst_g1_tab;
    }

    public void setGlucolyst_g1_tab(Integer glucolyst_g1_tab) {
        this.glucolyst_g1_tab = glucolyst_g1_tab;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    //http://139.59.63.181/api/resource/Item/?filters=[["Item", "branch", "=","Main"],["Item", "order by", "=","modified desc"]]
     /*------------------------------------------------------------------------*/

    public Integer getTrygesic_ptab() {
        return trygesic_ptab;
    }

    public void setTrygesic_ptab(Integer trygesic_ptab) {
        this.trygesic_ptab = trygesic_ptab;
    }

    public Integer getCipgrow_syrup() {
        return cipgrow_syrup;
    }

    public void setCipgrow_syrup(Integer cipgrow_syrup) {
        this.cipgrow_syrup = cipgrow_syrup;
    }

    public Integer getAltipan_dsr() {
        return altipan_dsr;
    }

    public void setAltipan_dsr(Integer altipan_dsr) {
        this.altipan_dsr = altipan_dsr;
    }

    public Integer getActirest_ls() {
        return actirest_ls;
    }

    public void setActirest_ls(Integer actirest_ls) {
        this.actirest_ls = actirest_ls;
    }

    public Integer getSangria_tonic() {
        return sangria_tonic;
    }

    public void setSangria_tonic(Integer sangria_tonic) {
        this.sangria_tonic = sangria_tonic;
    }

    public Integer getKorby_soap() {
        return korby_soap;
    }

    public void setKorby_soap(Integer korby_soap) {
        this.korby_soap = korby_soap;
    }

    public Integer getActirest_dx() {
        return actirest_dx;
    }

    public void setActirest_dx(Integer actirest_dx) {
        this.actirest_dx = actirest_dx;
    }

    public Integer getLevocast_m() {
        return levocast_m;
    }

    public void setLevocast_m(Integer levocast_m) {
        this.levocast_m = levocast_m;
    }

    public Integer getOnederm_cream() {
        return onederm_cream;
    }

    public void setOnederm_cream(Integer onederm_cream) {
        this.onederm_cream = onederm_cream;
    }

    public Integer getClavyten_625() {
        return clavyten_625;
    }

    public void setClavyten_625(Integer clavyten_625) {
        this.clavyten_625 = clavyten_625;
    }

    public Integer getItezone_200_cap() {
        return itezone_200_cap;
    }

    public void setItezone_200_cap(Integer itezone_200_cap) {
        this.itezone_200_cap = itezone_200_cap;
    }

    public Integer getNextvit_tab() {
        return nextvit_tab;
    }

    public void setNextvit_tab(Integer nextvit_tab) {
        this.nextvit_tab = nextvit_tab;
    }

    /*private Integer wego_gel_30_mg;
    private Integer stand_sp_tab;
    private String creation;
    private String doctor_name;     //ok
*//*    private String doctype;*//*
    private String patch;
    private Integer actirab_l_cap;
    private String owner;
    private String hospital_name;      //ok
    private Integer empower_od_tab;
    private String city;            //ok
    private Integer lycorest_60ml_susp;
    private String modified_by;
    private String zone;            //ok
    private String area;
    private Integer stand_mf_60ml_susp;
    private String employee_code;      //ok
    private String reg_no;          //ok
    private String per_mobile;         //ok
    private Integer lycorest_tab;
    private Integer actirab_tab;
    private Integer lycort_1ml_inj;
    private Integer docstatus;
    private String doctor_specialization;  //ok
    private String email;               //ok
    private String doctor_type;         //ok
    private String per_phone;       //ok
    private String degree;          //ok
    private Integer lycolic_10ml_drops;
    private String hq;
    private String latitude;
    private Integer start_t_tab;
    private Integer regain_xt_tab;
    private String employee_name;
    private Integer actirab_d_cap;
    private Integer ten_on_30ml;
    private String pin_code;        //ok
    private Integer trygesic_tab;
    private Integer idx;
    private String region;             //ok

    private String longitude;
    private Integer wego_gel_20_mg;
    private Integer glucolyst_g1_tab;
    private String address;             //ok
    private Integer active;
    private String modified;

    public Integer getWego_gel_30_mg() {
        return wego_gel_30_mg;
    }

    public void setWego_gel_30_mg(Integer wego_gel_30_mg) {
        this.wego_gel_30_mg = wego_gel_30_mg;
    }

    public Integer getStand_sp_tab() {
        return stand_sp_tab;
    }

    public void setStand_sp_tab(Integer stand_sp_tab) {
        this.stand_sp_tab = stand_sp_tab;
    }

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

    public String getPatch() {
        return patch;
    }

    public void setPatch(String patch) {
        this.patch = patch;
    }


   *//* public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }*//*

    public Integer getActirab_l_cap() {
        return actirab_l_cap;
    }

    public void setActirab_l_cap(Integer actirab_l_cap) {
        this.actirab_l_cap = actirab_l_cap;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public Integer getEmpower_od_tab() {
        return empower_od_tab;
    }

    public void setEmpower_od_tab(Integer empower_od_tab) {
        this.empower_od_tab = empower_od_tab;
    }

    public String getCity() {
        return  city;

    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getLycorest_60ml_susp() {
        return lycorest_60ml_susp;
    }

    public void setLycorest_60ml_susp(Integer lycorest_60ml_susp) {
        this.lycorest_60ml_susp = lycorest_60ml_susp;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getStand_mf_60ml_susp() {
        return stand_mf_60ml_susp;
    }

    public void setStand_mf_60ml_susp(Integer stand_mf_60ml_susp) {
        this.stand_mf_60ml_susp = stand_mf_60ml_susp;
    }

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public String getPer_mobile() {
        return per_mobile;
    }

    public void setPer_mobile(String per_mobile) {
        this.per_mobile = per_mobile;
    }

    public Integer getLycorest_tab() {
        return lycorest_tab;
    }

    public void setLycorest_tab(Integer lycorest_tab) {
        this.lycorest_tab = lycorest_tab;
    }

    public Integer getActirab_tab() {
        return actirab_tab;
    }

    public void setActirab_tab(Integer actirab_tab) {
        this.actirab_tab = actirab_tab;
    }

    public Integer getLycort_1ml_inj() {
        return lycort_1ml_inj;
    }

    public void setLycort_1ml_inj(Integer lycort_1ml_inj) {
        this.lycort_1ml_inj = lycort_1ml_inj;
    }

    public Integer getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(Integer docstatus) {
        this.docstatus = docstatus;
    }

    public String getDoctor_specialization() {
        return doctor_specialization;
    }

    public void setDoctor_specialization(String doctor_specialization) {
        this.doctor_specialization = doctor_specialization;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoctor_type() {
        return doctor_type;
    }

    public void setDoctor_type(String doctor_type) {
        this.doctor_type = doctor_type;
    }

    public String getPer_phone() {
        return per_phone;
    }

    public void setPer_phone(String per_phone) {
        this.per_phone = per_phone;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getLycolic_10ml_drops() {
        return lycolic_10ml_drops;
    }

    public void setLycolic_10ml_drops(Integer lycolic_10ml_drops) {
        this.lycolic_10ml_drops = lycolic_10ml_drops;
    }

    public String getHq() {
        return hq;
    }

    public void setHq(String hq) {
        this.hq = hq;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getStart_t_tab() {
        return start_t_tab;
    }

    public void setStart_t_tab(Integer start_t_tab) {
        this.start_t_tab = start_t_tab;
    }

    public Integer getRegain_xt_tab() {
        return regain_xt_tab;
    }

    public void setRegain_xt_tab(Integer regain_xt_tab) {
        this.regain_xt_tab = regain_xt_tab;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public Integer getActirab_d_cap() {
        return actirab_d_cap;
    }

    public void setActirab_d_cap(Integer actirab_d_cap) {
        this.actirab_d_cap = actirab_d_cap;
    }

    public Integer getTen_on_30ml() {
        return ten_on_30ml;
    }

    public void setTen_on_30ml(Integer ten_on_30ml) {
        this.ten_on_30ml = ten_on_30ml;
    }

    public String getPin_code() {
        return pin_code;
    }

    public void setPin_code(String pin_code) {
        this.pin_code = pin_code;
    }

    public Integer getTrygesic_tab() {
        return trygesic_tab;
    }

    public void setTrygesic_tab(Integer trygesic_tab) {
        this.trygesic_tab = trygesic_tab;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }



    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Integer getWego_gel_20_mg() {
        return wego_gel_20_mg;
    }

    public void setWego_gel_20_mg(Integer wego_gel_20_mg) {
        this.wego_gel_20_mg = wego_gel_20_mg;
    }

    public Integer getGlucolyst_g1_tab() {
        return glucolyst_g1_tab;
    }

    public void setGlucolyst_g1_tab(Integer glucolyst_g1_tab) {
        this.glucolyst_g1_tab = glucolyst_g1_tab;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }*/
}
