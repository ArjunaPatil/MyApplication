package com.example.vin.myapplication;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_Employee_Location {
    private String name;

    private String user;
    private String user_name;
    private String latlon_date;
    private String time;
    private double latitude;
    private double longitude;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLatlon_date() {
        return latlon_date;
    }

    public void setLatlon_date(String latlon_date) {
        this.latlon_date = latlon_date;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

}
