package com.example.cjl.weatheranim_v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.mlog.weather.anim.WeatherAnimView;
import com.mlog.weather.anim.v2.drawable.CloudyDrawable;

/**
 * 多云，阴天
 * Created by cjl on 2015/12/11.
 */
public class CloudyFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    WeatherAnimView animView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cloudy_weather_view, container, false);

        animView = (WeatherAnimView) view.findViewById(R.id.weather_img);

        RadioGroup rg = (RadioGroup) view.findViewById(R.id.type);
        rg.setOnCheckedChangeListener(this);

        onCheckedChanged(rg, R.id.day);

        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.day) {
            animView.setImageDrawable(new CloudyDrawable(getActivity(), CloudyDrawable.TYPE_DAY));
        } else if (checkedId == R.id.night) {
            animView.setImageDrawable(new CloudyDrawable(getActivity(), CloudyDrawable.TYPE_NIGHT));
        } else if (checkedId == R.id.cloudy) {
            animView.setImageDrawable(new CloudyDrawable(getActivity(), CloudyDrawable.TYPE_CLOUDY));
        }
    }
}
