package com.example.team.bundle_widget;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ASUS on 5.08.2019.
 */


public interface GetFinanceDataService {

    @GET("market/latest/list/{base_symbol_code}/{symbol_code}")
    Call<List<Finance>> getFinanceData(@Path("base_symbol_code") String base_symbol_code, @Path("symbol_code") String symbol_code);
}
