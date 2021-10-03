package com.example.openweathersamplejava.db;

import androidx.annotation.NonNull;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {City.class}, version = 1, exportSchema = false)
public abstract class CityDatabase extends RoomDatabase {

    private static CityDatabase INSTANCE;

    public abstract CityDao cityDao();

    private static final Object sLock = new Object();

    public static CityDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        CityDatabase.class, "city.db")
                        .allowMainThreadQueries()
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                Executors.newSingleThreadExecutor().execute(
                                        () -> {
                                            try {
                                                InputStream is = context.getAssets().open("cities.json");
                                                int size = is.available();
                                                byte[] buffer = new byte[size];
                                                is.read(buffer);
                                                is.close();
                                                String json = new String(buffer, StandardCharsets.UTF_8);

                                                List<City> cities = new ArrayList<>();
                                                JsonArray cityJson = new Gson().fromJson(json, JsonArray.class);
                                                for (JsonElement jsonElement : cityJson) {
                                                    JsonObject cityObject = jsonElement.getAsJsonObject();
                                                    cities.add(new City(cityObject.get("id").getAsInt(), cityObject.get("name").getAsString()));
                                                }

                                                getInstance(context).cityDao().saveAll(cities);
                                            } catch (IOException | JsonParseException ex) {
                                                ex.printStackTrace();
                                            }
                                        });
                            }
                        })
                        .build();
            }
            return INSTANCE;
        }
    }
}
