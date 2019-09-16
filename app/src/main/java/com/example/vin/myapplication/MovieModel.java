package com.example.vin.myapplication;

import java.io.Serializable;

/**
 * Created by sab99r
 */
public class MovieModel implements Serializable{

    String title;
    String rating;
    String type;

    public MovieModel(String title,String type) {
        this.type = type;
        this.title = title;
    }



}
