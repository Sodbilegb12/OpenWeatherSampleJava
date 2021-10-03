package com.example.openweathersamplejava.response.weather;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class SunResponse implements Parcelable {
    @SerializedName("type")
    @ColumnInfo(name = "sun_type")
    private int type;

    @SerializedName("sunrise")
    @ColumnInfo(name = "sun_sunrise")
    private long sunrise;

    @SerializedName("sunset")
    @ColumnInfo(name = "sun_sunset")
    private long sunset;

    public SunResponse() {
    }

    protected SunResponse(Parcel in) {
        type = in.readInt();
        sunrise = in.readLong();
        sunset = in.readLong();
    }

    public static final Creator<SunResponse> CREATOR = new Creator<SunResponse>() {
        @Override
        public SunResponse createFromParcel(Parcel in) {
            return new SunResponse(in);
        }

        @Override
        public SunResponse[] newArray(int size) {
            return new SunResponse[size];
        }
    };

    public int getType() {
        return type;
    }

    public long getSunrise() {
        return sunrise * 1000L;
    }

    public long getSunset() {
        return sunset * 1000L;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeLong(sunrise);
        dest.writeLong(sunset);
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
