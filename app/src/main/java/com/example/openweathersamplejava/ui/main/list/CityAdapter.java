package com.example.openweathersamplejava.ui.main.list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.openweathersamplejava.R;
import com.example.openweathersamplejava.db.City;
import com.example.openweathersamplejava.response.DailyWeatherResponse;
import com.example.openweathersamplejava.response.weather.TemperatureResponse;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityViewHolder> {
    private Context mContext;
    private List<City> cities = new ArrayList<>();
    private CityItemInterface cityItemInterface;

    @SuppressLint("NotifyDataSetChanged")
    public void setCities(List<City> cities) {
        this.cities = cities;
        notifyDataSetChanged();
    }

    public CityAdapter(Context mContext, CityItemInterface cityItemInterface) {
        this.mContext = mContext;
        this.cityItemInterface = cityItemInterface;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_city, parent, false);
        return new CityViewHolder(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City city = cities.get(position);
        holder.binding.lblName.setText(city.getName());

        if (city.getWeather() != null) {
            DailyWeatherResponse response = city.getWeather();
            TemperatureResponse temperature = response.getTemperature();
            if (temperature != null) {
                holder.binding.lblWeather.setVisibility(View.VISIBLE);
                holder.binding.lblWeather.setText(String.format("Min: %.1f°C, Max: %.1f°C Humidity: %d", temperature.getTempMin(), temperature.getTempMax(), temperature.getHumidity()));
            }
        } else {
            holder.binding.lblWeather.setVisibility(View.GONE);
        }

        holder.binding.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityItemInterface != null) {
                    cityItemInterface.onItemClicked(city);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities != null ? cities.size() : 0;
    }
}
