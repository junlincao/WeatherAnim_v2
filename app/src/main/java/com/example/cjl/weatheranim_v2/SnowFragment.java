package com.example.cjl.weatheranim_v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.mlog.weather.anim.WeatherAnimView;
import com.mlog.weather.anim.v2.drawable.RainDrawable;
import com.mlog.weather.anim.v2.drawable.SnowDrawable;

/**
 * 雪
 * Created by cjl on 2015/12/11.
 */
public class SnowFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    WeatherAnimView animView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.snow_weather_view, container, false);

        animView = (WeatherAnimView) view.findViewById(R.id.weather_img);

        RadioGroup rg = (RadioGroup) view.findViewById(R.id.type);
        rg.setOnCheckedChangeListener(this);

        onCheckedChanged(rg, R.id.middle);

        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.storm) {
            animView.setImageDrawable(new SnowDrawable(SnowDrawable.TYPE_STORM));
        } else if (checkedId == R.id.middle) {
            animView.setImageDrawable(new SnowDrawable(SnowDrawable.TYPE_MIDDLE));
        }else if (checkedId == R.id.light) {
            animView.setImageDrawable(new SnowDrawable(SnowDrawable.TYPE_LIGHT));
        } else if(checkedId == R.id.with_rain){
            animView.setImageDrawable(new SnowDrawable(SnowDrawable.TYPE_WITH_RAIN));
        }
    }
}
