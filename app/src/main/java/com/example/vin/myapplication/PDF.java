package com.example.vin.myapplication;

import android.os.Environment;
import android.util.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by vin on 30/08/2017.
 */

class PDF{
    private String name;
    private String content64;

    // (...)

    public byte[] content64AsByteArray(){ // Helps you convert string to byte formats :B
        return Base64.decode(content64,Base64.DEFAULT);
    }

    public String save(String path) {

        try {

            path = Environment.getExternalStorageDirectory() + "/" + path;

            File f1 = new File(path);

            OutputStream out = null;
            out = new FileOutputStream(path + "/" + this.name);
            out.write(this.content64AsByteArray());
            out.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path; // I like to return it because i can use it to start a "open file" intent
    }
}
