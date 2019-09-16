package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Upload_Files_For_Selected_User extends RealmObject {
    /////////////
    @PrimaryKey
    private String name;
    private String file_name;//IMG-20171002-WA0016.jpg
    //private String doctype;//"File"
    private Integer lft;//imp
    private Integer file_size;//imp
    private String content_hash;//imp
    private Integer is_home_folder;
    private String folder;//imp-"home"
    private Integer rgt;//imp
    private Integer is_private;//1
    private String file_url;// '/private/files/user_name/file_name.extensions'

    private String attached_to_name;//no imp
    private String creation;//no imp
    private Integer is_attachments_folder;
    private Integer is_folder;
    private String modified;
    private String modified_by;
    private String old_parent;
    private String owner;
    private String attached_to_doctype;


    private Integer date_flag;
    private Integer is_present_in_off_line;

    public Integer getDate_flag() {
        return date_flag;
    }

    public void setDate_flag(Integer date_flag) {
        this.date_flag = date_flag;
    }

    public Integer getIs_present_in_off_line() {
        return is_present_in_off_line;
    }

    public void setIs_present_in_off_line(Integer is_present_in_off_line) {
        this.is_present_in_off_line = is_present_in_off_line;
    }



    public String getAttached_to_name() {
        return attached_to_name;
    }

    public void setAttached_to_name(String attached_to_name) {
        this.attached_to_name = attached_to_name;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getCreation() {
        return creation;
    }

    public void setCreation(String creation) {
        this.creation = creation;
    }


    public Integer getLft() {
        return lft;
    }

    public void setLft(Integer lft) {
        this.lft = lft;
    }

    public Integer getIs_attachments_folder() {
        return is_attachments_folder;
    }

    public void setIs_attachments_folder(Integer is_attachments_folder) {
        this.is_attachments_folder = is_attachments_folder;
    }

    public Integer getIs_folder() {
        return is_folder;
    }

    public void setIs_folder(Integer is_folder) {
        this.is_folder = is_folder;
    }

    public Integer getFile_size() {
        return file_size;
    }

    public void setFile_size(Integer file_size) {
        this.file_size = file_size;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent_hash() {
        return content_hash;
    }

    public void setContent_hash(String content_hash) {
        this.content_hash = content_hash;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public Integer getIs_home_folder() {
        return is_home_folder;
    }

    public void setIs_home_folder(Integer is_home_folder) {
        this.is_home_folder = is_home_folder;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getAttached_to_doctype() {
        return attached_to_doctype;
    }

    public void setAttached_to_doctype(String attached_to_doctype) {
        this.attached_to_doctype = attached_to_doctype;
    }

    public Integer getRgt() {
        return rgt;
    }

    public void setRgt(Integer rgt) {
        this.rgt = rgt;
    }

    public Integer getIs_private() {
        return is_private;
    }

    public void setIs_private(Integer is_private) {
        this.is_private = is_private;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getOld_parent() {
        return old_parent;
    }

    public void setOld_parent(String old_parent) {
        this.old_parent = old_parent;
    }

}
