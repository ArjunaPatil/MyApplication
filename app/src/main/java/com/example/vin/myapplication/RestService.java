package com.example.vin.myapplication;

import android.util.Log;

/**
 * Created by vin on 09-11-2016.
 */
public class RestService {

    //You need to change the IP if you testing environment is not local machine
    //or you may have different URL than we have here
    // private static final String URL = "http://api.lystenglobal.com/api/";
    // private static final String URL = "http://54.90.65.139/api/";
    //private static final String URL = "http://lystenglobal.online/api/";
    private static final String URL = "http://139.59.63.181/api/";
    private static final String URL1 = "http://13.126.122.12/MYSQLConnTest/";
    private static final String URL2 = "http://139.59.63.181";
    private retrofit.RestAdapter restAdapter;
    private ApiService apiService;

    public RestService() {
        try {


            restAdapter = new retrofit.RestAdapter.Builder()
                    .setEndpoint(URL)
                    .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                    .build();

            apiService = restAdapter.create(ApiService.class);
        } catch (Exception ex) {
            Log.i("Error is:", ex.getMessage().toString());
        }
    }

    public RestService(String url) {
        try {
            restAdapter = new retrofit.RestAdapter.Builder()
                    .setEndpoint(URL1)
                    .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                    .build();

            apiService = restAdapter.create(ApiService.class);
        } catch (Exception ex) {
            Log.i("Error is:", ex.getMessage().toString());
        }
    }

    /*Write by arjun on 24-03-2018 for upload files to server */
    public RestService(String url1, String url2) {
        try {

            restAdapter = new retrofit.RestAdapter.Builder()
                    .setEndpoint(URL2)
                    .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                    .build();

            apiService = restAdapter.create(ApiService.class);

        } catch (Exception ex) {
            Log.i("Error is:", ex.getMessage().toString());
        }
    }

    public ApiService getService() {
        return apiService;
    }
}
