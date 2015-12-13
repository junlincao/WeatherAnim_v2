package com.example.cjl.weatheranim_v2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v1.drawable.CloudlyDrawable;
import com.mlog.weather.anim.v1.drawable.CloudsDrawable;
import com.mlog.weather.anim.v1.drawable.FogDrawable;
import com.mlog.weather.anim.v1.drawable.FreezingRainDrawable;
import com.mlog.weather.anim.v1.drawable.HailDrawable;
import com.mlog.weather.anim.v1.drawable.HazeDrawable;
import com.mlog.weather.anim.v1.drawable.MoonDrawable;
import com.mlog.weather.anim.v1.drawable.RainAndSnowDrawable;
import com.mlog.weather.anim.v1.drawable.RainDrawable;
import com.mlog.weather.anim.v1.drawable.SandStormDrawable;
import com.mlog.weather.anim.v1.drawable.ShowerDrawable;
import com.mlog.weather.anim.v1.drawable.SnowDrawable;
import com.mlog.weather.anim.v1.drawable.SnowShowerDrawable;
import com.mlog.weather.anim.v1.drawable.SunDrawable;
import com.mlog.weather.anim.v1.drawable.ThundersShowerDrawable;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 旧版动画
 * Created by cjl on 2015/12/13.
 */
public class OldFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.old_fragment, container, false);


        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        AnimPagerAdapter mAdapter = new AnimPagerAdapter(inflater);
        mAdapter.updateData(generateData());

        mViewPager.setAdapter(mAdapter);

        return view;
    }


    private Map<String, WeatherDrawable> generateData() {
        LinkedHashMap<String, WeatherDrawable> datas = new LinkedHashMap<>();

        datas.put("晴白天", new SunDrawable(getContext()));
        datas.put("晴夜晚", new MoonDrawable(getContext()));
        datas.put("白天多云", new CloudsDrawable(getContext(), CloudsDrawable.TYPE_DAY));
        datas.put("晚上多云", new CloudsDrawable(getContext(), CloudsDrawable.TYPE_NIGHT));
        datas.put("阴天", new CloudlyDrawable());
        datas.put("白天阵雨", new ShowerDrawable(getContext(), CloudsDrawable.TYPE_DAY));
        datas.put("晚上阵雨", new ShowerDrawable(getContext(), CloudsDrawable.TYPE_NIGHT));
        datas.put("雷阵雨", new ThundersShowerDrawable(getContext()));
        datas.put("冰雹", new HailDrawable());
        datas.put("雨夹雪", new RainAndSnowDrawable(getContext()));
        datas.put("小雨", new RainDrawable(RainDrawable.RAIN_TYPE_SMALL));
        datas.put("大雨", new RainDrawable(RainDrawable.RAIN_TYPE_HEAVY));
        datas.put("白天阵雪", new SnowShowerDrawable(getContext(), CloudsDrawable.TYPE_DAY));
        datas.put("晚上阵雪", new SnowShowerDrawable(getContext(), CloudsDrawable.TYPE_NIGHT));
        datas.put("雪", new SnowDrawable(getContext()));
        datas.put("雾", new FogDrawable(getContext()));
        datas.put("冻雨", new FreezingRainDrawable());
        datas.put("霾", new HazeDrawable(getContext()));
        datas.put("沙尘暴", new SandStormDrawable(getContext()));

        return datas;
    }
}
