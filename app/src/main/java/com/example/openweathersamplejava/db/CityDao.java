package com.example.openweathersamplejava.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAll(List<City> cities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(City city);

    @Query("SELECT * FROM city")
    LiveData<List<City>> findAll();

    @Query("SELECT * FROM city WHERE name LIKE '%' || :query || '%'")
    LiveData<List<City>> findSearchValue(String query);
}
