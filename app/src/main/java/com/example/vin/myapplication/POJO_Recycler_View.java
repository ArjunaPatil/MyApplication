package com.example.vin.myapplication;

import java.io.Serializable;

/**
 * Created by arj on 22/05/2018.
 */

public class POJO_Recycler_View implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;

    private String emailId;

    public POJO_Recycler_View() {

    }

    public POJO_Recycler_View(String name, String emailId) {
        this.name = name;
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
