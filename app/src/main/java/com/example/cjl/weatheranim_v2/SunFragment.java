package com.example.cjl.weatheranim_v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlog.weather.anim.WeatherAnimView;
import com.mlog.weather.anim.drawable.SunDrawable;

/**
 * Created by cjl on 2015/12/11.
 */
public class SunFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_view, container, false);

        WeatherAnimView animView = (WeatherAnimView) view.findViewById(R.id.weather_img);
        animView.setImageDrawable(new SunDrawable(getActivity()));

        return view;
    }
}
