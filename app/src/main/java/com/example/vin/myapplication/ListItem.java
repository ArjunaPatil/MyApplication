package com.example.vin.myapplication;

/**
 * Created by vin on 28/07/2017.
 */

public class ListItem {


    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    private String head;
    private String desc;

    public ListItem(String head, String desc) {
        this.head = head;
        this.desc = desc;
    }
}
