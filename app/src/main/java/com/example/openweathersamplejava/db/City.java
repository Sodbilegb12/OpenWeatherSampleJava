package com.example.openweathersamplejava.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.openweathersamplejava.response.DailyWeatherResponse;

@Entity(tableName = "city")
public class City implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;

    @Embedded
    private DailyWeatherResponse weather;

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    protected City(Parcel in) {
        id = in.readInt();
        name = in.readString();
        weather = in.readParcelable(DailyWeatherResponse.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeParcelable(weather, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DailyWeatherResponse getWeather() {
        return weather;
    }

    public void setWeather(DailyWeatherResponse weather) {
        this.weather = weather;
    }
}
