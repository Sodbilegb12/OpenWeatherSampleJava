package com.example.openweathersamplejava.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.openweathersamplejava.R;
import com.example.openweathersamplejava.databinding.CustomWeatherInfoViewBinding;
import com.example.openweathersamplejava.model.WeatherModel;

public class WeatherInfoView extends FrameLayout {
    private Context context;
    private CustomWeatherInfoViewBinding binding;

    public WeatherInfoView(@NonNull Context context) {
        super(context);

        this.context = context;
        init();
    }

    public WeatherInfoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        init();
    }

    public WeatherInfoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        init();
    }

    public WeatherInfoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        binding = CustomWeatherInfoViewBinding.inflate(inflater, this);
    }

    public void setWeatherModel(WeatherModel weatherModel) {
        binding.lblTitle.setText(weatherModel.getTitle());
        binding.lblBody.setText(weatherModel.getBody());
        Glide.with(context).load(weatherModel.getIcon())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(binding.ivStatus);
    }
}
