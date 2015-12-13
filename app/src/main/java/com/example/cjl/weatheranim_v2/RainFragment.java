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

/**
 * 多云，阴天
 * Created by cjl on 2015/12/11.
 */
public class RainFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    WeatherAnimView animView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rain_weather_view, container, false);

        animView = (WeatherAnimView) view.findViewById(R.id.weather_img);

        RadioGroup rg = (RadioGroup) view.findViewById(R.id.type);
        rg.setOnCheckedChangeListener(this);

        onCheckedChanged(rg, R.id.heavy);

        return view;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.storm) {
            animView.setImageDrawable(new RainDrawable(getContext(), RainDrawable.TYPE_STORM, false));
        } else if (checkedId == R.id.heavy) {
            animView.setImageDrawable(new RainDrawable(getContext(), RainDrawable.TYPE_HEAVY, false));
        } else if (checkedId == R.id.middle) {
            animView.setImageDrawable(new RainDrawable(getContext(), RainDrawable.TYPE_MIDDLE, false));
        } else if (checkedId == R.id.light) {
            animView.setImageDrawable(new RainDrawable(getContext(), RainDrawable.TYPE_LIGHT, false));
        } else if (checkedId == R.id.thunder) {
            animView.setImageDrawable(new RainDrawable(getContext(), RainDrawable.TYPE_THUNDER, false));
        } else if (checkedId == R.id.frozen) {
            animView.setImageDrawable(new RainDrawable(getContext(), RainDrawable.TYPE_FROZEN, false));
        }
    }
}
