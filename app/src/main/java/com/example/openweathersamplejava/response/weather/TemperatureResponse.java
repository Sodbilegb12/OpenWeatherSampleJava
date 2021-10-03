package com.example.openweathersamplejava.response.weather;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class TemperatureResponse implements Parcelable {
    @SerializedName("temp")
    @ColumnInfo(name = "temp_temp")
    private double temp;

    @SerializedName("temp_min")
    @ColumnInfo(name = "temp_min")
    private double tempMin;

    @SerializedName("temp_max")
    @ColumnInfo(name = "temp_max")
    private double tempMax;

    @SerializedName("pressure")
    @ColumnInfo(name = "temp_pressure")
    private double pressure;

    @SerializedName("humidity")
    @ColumnInfo(name = "temp_humidity")
    private int humidity;

    public TemperatureResponse() {
    }

    protected TemperatureResponse(Parcel in) {
        temp = in.readDouble();
        tempMin = in.readDouble();
        tempMax = in.readDouble();
        pressure = in.readDouble();
        humidity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(temp);
        dest.writeDouble(tempMin);
        dest.writeDouble(tempMax);
        dest.writeDouble(pressure);
        dest.writeInt(humidity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TemperatureResponse> CREATOR = new Creator<TemperatureResponse>() {
        @Override
        public TemperatureResponse createFromParcel(Parcel in) {
            return new TemperatureResponse(in);
        }

        @Override
        public TemperatureResponse[] newArray(int size) {
            return new TemperatureResponse[size];
        }
    };

    public double getTemp() {
        return temp;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public double getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
