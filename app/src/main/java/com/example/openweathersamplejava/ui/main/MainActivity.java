package com.example.openweathersamplejava.ui.main;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.openweathersamplejava.R;
import com.example.openweathersamplejava.constants.Constants;
import com.example.openweathersamplejava.databinding.ActivityMainBinding;
import com.example.openweathersamplejava.model.CityViewModel;
import com.example.openweathersamplejava.db.City;
import com.example.openweathersamplejava.ui.detail.WeatherActivity;
import com.example.openweathersamplejava.ui.main.list.CityAdapter;
import com.example.openweathersamplejava.ui.main.list.CityItemInterface;
import com.example.openweathersamplejava.ui.main.list.CityItemSpaceDecoration;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Context mContext = this;
    private ActivityMainBinding binding;
    private CityAdapter cityAdapter;

    private CityViewModel viewModel;

    ActivityResultLauncher<Intent> weatherActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            City updatedCity = data.getParcelableExtra(Constants.INTENT_CITY);
                            if (updatedCity != null) {
                                viewModel.updateCity(updatedCity);
                            }
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();

        viewModel = ViewModelProviders.of(this).get(CityViewModel.class);
        viewModel.getCities().observe(this, cities -> {
            String query = binding.etSearch.getText().toString();
            if (!query.isEmpty()) {
                search(query);
            } else {
                cityAdapter.setCities(cities);
            }
        });
    }

    private void initView() {
        binding.rvCity.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvCity.setNestedScrollingEnabled(false);
        binding.rvCity.setItemAnimator(new DefaultItemAnimator());
        binding.rvCity.addItemDecoration(
                new CityItemSpaceDecoration(getResources().getDimensionPixelSize(
                        R.dimen.marginSmall)));
        cityAdapter = new CityAdapter(mContext, new CityItemInterface() {
            @Override
            public void onItemClicked(City city) {
                Intent intent = new Intent(mContext, WeatherActivity.class);
                intent.putExtra(Constants.INTENT_CITY, city);
                weatherActivityResultLauncher.launch(intent);
            }
        });
        binding.rvCity.setAdapter(cityAdapter);

        binding.etSearch.addTextChangedListener(new TextWatcher() {
            private final long INTERVAL = 500L;
            private Timer timer;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (timer != null) {
                    timer.cancel();
                }

                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String query = binding.etSearch.getText().toString();
                                search(query);
                            }
                        });
                    }
                }, INTERVAL);
            }
        });
    }

    private void search(String query) {
        LiveData<List<City>> searchResult = viewModel.search(query);
        Observer<List<City>> observer = new Observer<List<City>>() {
            @Override
            public void onChanged(List<City> cities) {
                cityAdapter.setCities(cities);

                searchResult.removeObserver(this);
            }
        };
        searchResult.observe(this, observer);
    }
}