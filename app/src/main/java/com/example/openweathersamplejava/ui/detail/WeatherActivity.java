package com.example.openweathersamplejava.ui.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.openweathersamplejava.R;
import com.example.openweathersamplejava.constants.Constants;
import com.example.openweathersamplejava.databinding.ActivityWeatherBinding;
import com.example.openweathersamplejava.db.City;
import com.example.openweathersamplejava.model.WeatherModel;
import com.example.openweathersamplejava.response.DailyWeatherResponse;
import com.example.openweathersamplejava.response.weather.CloudResponse;
import com.example.openweathersamplejava.response.weather.SunResponse;
import com.example.openweathersamplejava.response.weather.TemperatureResponse;
import com.example.openweathersamplejava.response.weather.WeatherResponse;
import com.example.openweathersamplejava.response.weather.WindResponse;
import com.example.openweathersamplejava.retrofit.OpenWeatherApiService;
import com.example.openweathersamplejava.retrofit.OpenWeatherServiceGenerator;
import com.example.openweathersamplejava.ui.custom.WeatherInfoView;
import com.example.openweathersamplejava.util.UrlHelper;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {
    private Context mContext = this;
    private City city;
    private ActivityWeatherBinding binding;

    static {
        System.loadLibrary("signature");
    }

    public static native String getAPIKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        city = getIntent().getParcelableExtra(Constants.INTENT_CITY);
        if (city == null) {
            Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initView();

        //TODO ENE NOHTSOLIIG SAIJRUULAH, 30min DOTROO DAHIJ DUUDAHGU ..ETC
        getWeather();
//        DailyWeatherResponse weather = city.getWeather();
//        if (weather != null) {
//            displayWeather(city.getWeather());
//        } else {
//            getWeather();
//        }
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(city.getName());
        }
    }

    private void getWeather() {
        OpenWeatherApiService apiService = OpenWeatherServiceGenerator.getApiService();
        Call<DailyWeatherResponse> call = apiService.getWeather(city.getName(), getAPIKey());
        call.enqueue(new Callback<DailyWeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<DailyWeatherResponse> call, @NonNull Response<DailyWeatherResponse> result) {
                DailyWeatherResponse response = result.body();

                if (response != null) {
                    displayWeather(response);

                    Intent intent = getIntent();
                    city.setWeather(response);
                    intent.putExtra(Constants.INTENT_CITY, city);
                    setResult(RESULT_OK, intent);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DailyWeatherResponse> call, @NonNull Throwable t) {
                call.cancel();
                binding.pbLoading.setVisibility(View.GONE);

                Toast.makeText(mContext, getString(R.string.error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void displayWeather(DailyWeatherResponse response) {
        binding.pbLoading.setVisibility(View.GONE);

        ArrayList<WeatherResponse> weathers = response.getWeather();
        if (weathers != null) {
            for (WeatherResponse weather : weathers) {
                WeatherInfoView infoView = new WeatherInfoView(mContext);
                infoView.setWeatherModel(new WeatherModel(UrlHelper.getFullImgUrl(weather.getIcon()), "Weather", weather.getDescription()));
                binding.layoutInfo.addView(infoView);
            }
        }

        //TODO REPLACE STATIC IMG URLS
        WindResponse wind = response.getWind();
        if (wind != null) {
            WeatherInfoView _windView = new WeatherInfoView(mContext);
            _windView.setWeatherModel(new WeatherModel("https://img.icons8.com/material/2x/windsock.png", "Wind:", String.format("Speed: %s", wind.getSpeed())));
            binding.layoutInfo.addView(_windView);
        }

        CloudResponse cloud = response.getCloud();
        if (cloud != null) {
            WeatherInfoView _cloudView = new WeatherInfoView(mContext);
            _cloudView.setWeatherModel(new WeatherModel("https://img.icons8.com/material/2x/barometer.png", "Clouds:", String.format("%s", cloud.getAll())));
            binding.layoutInfo.addView(_cloudView);
        }

        SunResponse sun = response.getSun();
        if (sun != null) {
            WeatherInfoView _sunRiseView = new WeatherInfoView(mContext);
            Calendar _calendar = Calendar.getInstance();
            _calendar.setTimeInMillis(sun.getSunrise());
            _sunRiseView.setWeatherModel(new WeatherModel("https://img.icons8.com/material/2x/do-not-expose-to-sunlight.png", "Sun rise in:", DateFormat.format("HH:mm", _calendar).toString()));
            binding.layoutInfo.addView(_sunRiseView);

            WeatherInfoView _sunSetView = new WeatherInfoView(mContext);
            _calendar.setTimeInMillis(sun.getSunset());
            _sunSetView.setWeatherModel(new WeatherModel("https://img.icons8.com/material/2x/do-not-expose-to-sunlight.png", "Sun set in: ", DateFormat.format("HH:mm", _calendar).toString()));
            binding.layoutInfo.addView(_sunSetView);
        }

        TemperatureResponse temperature = response.getTemperature();
        if (temperature != null) {
            WeatherInfoView _humidityView = new WeatherInfoView(mContext);
            _humidityView.setWeatherModel(new WeatherModel("https://img.icons8.com/material/2x/humidity.png", "Humidity: ", String.format("Humidity: %d%%", temperature.getHumidity())));
            binding.layoutInfo.addView(_humidityView);

            WeatherInfoView _temperatureView = new WeatherInfoView(mContext);
            _temperatureView.setWeatherModel(new WeatherModel("https://img.icons8.com/material/2x/temperature.png", "Temperature: ", String.format("Min: %.1f°C, Max: %.1f°C", temperature.getTempMin(), temperature.getTempMax())));
            binding.layoutInfo.addView(_temperatureView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}