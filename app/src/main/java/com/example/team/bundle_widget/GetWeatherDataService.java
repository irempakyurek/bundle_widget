package com.example.team.bundle_widget;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ASUS on 8.08.2019.
 */

public interface GetWeatherDataService {

    @GET("get")
    Call<Weather> getWeatherData(@Query("CountryName") String CountryName, @Query("CityName") String CityName, @Query("ProvinceName") String ProvinceName);
}
