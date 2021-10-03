package com.example.openweathersamplejava.retrofit;

import com.example.openweathersamplejava.response.DailyWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApiService {
    @GET("weather?units=metric")
    Call<DailyWeatherResponse> getWeather(
            @Query("q") String query,
            @Query("appid") String appId
    );

    @GET("forecast/daily")
    Call<DailyWeatherResponse> getForecast(
            @Query("q") String query,
            @Query("cnt") int cnt,
            @Query("appid") String appId
    );

}
