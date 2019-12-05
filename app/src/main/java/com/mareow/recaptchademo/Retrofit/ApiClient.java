package com.mareow.recaptchademo.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://18.204.165.238:8080/mareow-api/api/";
    private static Retrofit retrofit = null;
    ///http://18.204.165.238:8080/mareow-api/api/
   /// http://mareow.in/mareow-api/api/

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }




}
