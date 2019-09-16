package com.example.vin.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import io.realm.Realm;

/**
 * Created by vin on 03/02/2017.
 */

public class Class_load_server_data {
    private Realm mRealm;
    RestService restService;
    Context _context;
    int limitstart = 0;
    int pagesize = 1;
    boolean datafull = false;
    private ProgressDialog pDialog;

    public void Class_load_server_data(Context c) {
        try {
            _context = c;
        }
        catch(Exception ex)
        {
            Toast.makeText(_context, ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }





}
