package com.example.team.bundle_widget;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS on 5.08.2019.
 */

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static Retrofit retrofit2;
    private static final String BASE_URL = "https://finance.bundletheworld.com/v1/";
    private static final String BASE_URL2 = "https://weather.bundletheworld.com/api/";

    public static Retrofit getFinanceClient(){
        if(retrofit== null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();
            return retrofit;
        }
        return retrofit;
    }

    public static Retrofit getWeatherClient(){
        if(retrofit2== null){
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();
            return retrofit2;
        }
        return retrofit2;
    }
}
