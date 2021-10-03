package com.example.openweathersamplejava.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.openweathersamplejava.db.City;
import com.example.openweathersamplejava.db.CityDao;
import com.example.openweathersamplejava.db.CityDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CityViewModel extends AndroidViewModel {
    private final CityDao cityDao;
    private final ExecutorService executorService;
    private LiveData<List<City>> cities;

    public CityViewModel(@NonNull Application application) {
        super(application);
        cityDao = CityDatabase.getInstance(application).cityDao();
        executorService = Executors.newSingleThreadExecutor();

        cities = cityDao.findAll();
    }

    public LiveData<List<City>> getCities() {
        return cityDao.findAll();
    }

    public LiveData<List<City>> search(String query) {
        return cityDao.findSearchValue(query);
    }

    public void updateCity(City city) {
        executorService.execute(() -> cityDao.save(city));
    }
}
