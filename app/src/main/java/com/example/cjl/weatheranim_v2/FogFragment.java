package com.example.cjl.weatheranim_v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.mlog.weather.anim.WeatherAnimView;
import com.mlog.weather.anim.v2.drawable.FogDrawable;

/**
 * 雾霾沙尘暴
 * Created by cjl on 2015/12/11.
 */
public class FogFragment extends Fragment {
    WeatherAnimView animView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fog_weather_view, container, false);

        animView = (WeatherAnimView) view.findViewById(R.id.weather_img);

        animView.setImageDrawable(new FogDrawable(getContext()));

        return view;
    }


}
