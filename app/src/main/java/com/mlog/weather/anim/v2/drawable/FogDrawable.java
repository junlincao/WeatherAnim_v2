package com.mlog.weather.anim.v2.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v2.weatherItem.Fog;
import com.mlog.weather.anim.v2.weatherItem.MountainBg;

import java.util.List;

/**
 * 雾霾沙尘暴
 * Created by cjl on 2015/12/13.
 */
public class FogDrawable extends WeatherDrawable {

    private Drawable mountain;
    private Drawable mWindow;

    public FogDrawable(Context context) {
        mountain = context.getResources().getDrawable(R.drawable.v2_anim_bg05);
        mWindow = context.getResources().getDrawable(R.drawable.v2_anim_fog_window);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        MountainBg mountainBg = new MountainBg(mountain);
        mountainBg.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(mountainBg);

        Fog fog = new Fog(mWindow);
        fog.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(fog);
    }
}
