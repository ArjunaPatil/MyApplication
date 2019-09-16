package com.example.vin.myapplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by vin on 30/08/2017.
 */

public interface ServerAPI {
    @GET
    Call<ResponseBody> downlload(@Url String fileUrl);

    Retrofit retrofit =
            new Retrofit.Builder()
                    .baseUrl("http://13.126.122.12/MYSQLConnTest/") // REMEMBER TO END with /
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
}
