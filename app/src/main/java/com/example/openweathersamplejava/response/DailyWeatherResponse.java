package com.example.openweathersamplejava.response;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Embedded;
import androidx.room.Ignore;

import com.example.openweathersamplejava.response.weather.CloudResponse;
import com.example.openweathersamplejava.response.weather.SunResponse;
import com.example.openweathersamplejava.response.weather.TemperatureResponse;
import com.example.openweathersamplejava.response.weather.WeatherResponse;
import com.example.openweathersamplejava.response.weather.WindResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DailyWeatherResponse implements Parcelable {
    //TODO FIX - ArrayList toroltoi bol DB-ru hadgalahgu bga
    @SerializedName("weather")
    @Ignore
    private ArrayList<WeatherResponse> weather;

    @SerializedName("wind")
    @Embedded
    private WindResponse wind;

    @SerializedName("sys")
    @Embedded
    private SunResponse sun;

    @SerializedName("clouds")
    @Embedded
    private CloudResponse cloud;

    @SerializedName("main")
    @Embedded
    private TemperatureResponse temperature;

    public DailyWeatherResponse() {
    }

    protected DailyWeatherResponse(Parcel in) {
        weather = in.createTypedArrayList(WeatherResponse.CREATOR);
        wind = in.readParcelable(WindResponse.class.getClassLoader());
        sun = in.readParcelable(SunResponse.class.getClassLoader());
        cloud = in.readParcelable(CloudResponse.class.getClassLoader());
        temperature = in.readParcelable(TemperatureResponse.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(weather);
        dest.writeParcelable(wind, flags);
        dest.writeParcelable(sun, flags);
        dest.writeParcelable(cloud, flags);
        dest.writeParcelable(temperature, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DailyWeatherResponse> CREATOR = new Creator<DailyWeatherResponse>() {
        @Override
        public DailyWeatherResponse createFromParcel(Parcel in) {
            return new DailyWeatherResponse(in);
        }

        @Override
        public DailyWeatherResponse[] newArray(int size) {
            return new DailyWeatherResponse[size];
        }
    };

    public ArrayList<WeatherResponse> getWeather() {
        return weather;
    }

    public WindResponse getWind() {
        return wind;
    }

    public SunResponse getSun() {
        return sun;
    }

    public TemperatureResponse getTemperature() {
        return temperature;
    }

    public CloudResponse getCloud() {
        return cloud;
    }

    public void setWeather(ArrayList<WeatherResponse> weather) {
        this.weather = weather;
    }

    public void setWind(WindResponse wind) {
        this.wind = wind;
    }

    public void setSun(SunResponse sun) {
        this.sun = sun;
    }

    public void setCloud(CloudResponse cloud) {
        this.cloud = cloud;
    }

    public void setTemperature(TemperatureResponse temperature) {
        this.temperature = temperature;
    }
}