package com.example.openweathersamplejava.response.weather;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class WindResponse implements Parcelable {
    @SerializedName("speed")
    @ColumnInfo(name = "wind_speed")
    private double speed;

    @SerializedName("degree")
    @ColumnInfo(name = "wind_degree")
    private double degree;

    public WindResponse() {
    }

    protected WindResponse(Parcel in) {
        speed = in.readDouble();
        degree = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(speed);
        dest.writeDouble(degree);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WindResponse> CREATOR = new Creator<WindResponse>() {
        @Override
        public WindResponse createFromParcel(Parcel in) {
            return new WindResponse(in);
        }

        @Override
        public WindResponse[] newArray(int size) {
            return new WindResponse[size];
        }
    };

    public double getSpeed() {
        return speed;
    }

    public double getDegree() {
        return degree;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDegree(double degree) {
        this.degree = degree;
    }
}
