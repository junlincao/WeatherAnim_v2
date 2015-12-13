package com.mlog.weather.anim.v1.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;


import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v1.weatherItem.Sun;

import java.util.List;

/**
 * 晴天
 *
 * @author CJL
 * @since 2015-09-15
 */
public class SunDrawable extends WeatherDrawable {

    Drawable mD1;
    Drawable mD2;
    Drawable mD3;

    public SunDrawable(Context context) {
        mD1 = context.getResources().getDrawable(R.drawable.sunny_1);
        mD2 = context.getResources().getDrawable(R.drawable.sunny_2);
        mD3 = context.getResources().getDrawable(R.drawable.sunny_3);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        Sun sun = new Sun(mD1, mD2, mD3);
        sun.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        sun.setShowWave(false);
        weatherItems.add(sun);
    }


//    Drawable mDrawable;
//
//    public SunDrawable(Context context) {
//        mDrawable = context.getResources().getDrawable(R.drawable.sunny_2);
//    }
//
//    @Override
//    void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
//        Sun2 sun = new Sun2(mDrawable);
//        sun.setBounds(rect.left, rect.top, rect.right, rect.bottom);
//        sun.setShowWave(true);
//        weatherItems.add(sun);
//    }
}
