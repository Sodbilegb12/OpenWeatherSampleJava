package com.example.openweathersamplejava.ui.main.list;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.openweathersamplejava.databinding.ListItemCityBinding;

public class CityViewHolder extends RecyclerView.ViewHolder {
    public ListItemCityBinding binding;

    public CityViewHolder(@NonNull View view) {
        super(view);
        binding = ListItemCityBinding.bind(view);
    }
}
