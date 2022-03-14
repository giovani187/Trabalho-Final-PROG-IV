package com.example.trabprogiv_giovanichagas;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SunriseService {
        @GET("json")
        Call<SunriseSunset> carregar(@Query("lat") double lat, @Query("lng") double lng);
}
