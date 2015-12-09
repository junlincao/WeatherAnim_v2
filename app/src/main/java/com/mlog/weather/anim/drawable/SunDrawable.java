package com.mlog.weather.anim.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.weatherItem.IWeatherItem;
import com.mlog.weather.anim.weatherItem.Sun;

import java.util.List;

/**
 * 晴天
 *
 * @author CJL
 * @since 2015-09-15
 */
public class SunDrawable extends WeatherDrawable {

    private Drawable light;
    private Drawable light2;

    SunDrawable(Context context){
        light = context.getResources().getDrawable(R.drawable.light);
        light2 = context.getResources().getDrawable(R.drawable.light2);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        weatherItems.add(new Sun());
    }
}
