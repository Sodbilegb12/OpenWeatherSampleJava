package com.example.openweathersamplejava.retrofit;

import com.example.openweathersamplejava.BuildConfig;
import com.example.openweathersamplejava.constants.Url;
import com.example.openweathersamplejava.util.UrlHelper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherServiceGenerator {

    private static Retrofit retrofit;

    public static OpenWeatherApiService getApiService() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

            if (BuildConfig.DEBUG) {
                interceptor.level(HttpLoggingInterceptor.Level.BODY);
            }

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES);

            OkHttpClient client = builder.addInterceptor(interceptor).build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(Url.OPEN_WEATHER_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(OpenWeatherApiService.class);
    }
}