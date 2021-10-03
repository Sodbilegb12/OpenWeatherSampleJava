package com.example.openweathersamplejava.response.weather;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class CloudResponse implements Parcelable {
    @SerializedName("all")
    @ColumnInfo(name = "cloud_all")
    private int all;

    public CloudResponse() {
    }

    protected CloudResponse(Parcel in) {
        all = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(all);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CloudResponse> CREATOR = new Creator<CloudResponse>() {
        @Override
        public CloudResponse createFromParcel(Parcel in) {
            return new CloudResponse(in);
        }

        @Override
        public CloudResponse[] newArray(int size) {
            return new CloudResponse[size];
        }
    };

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
