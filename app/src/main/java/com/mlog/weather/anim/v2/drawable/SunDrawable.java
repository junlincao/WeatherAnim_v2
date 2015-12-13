package com.mlog.weather.anim.v2.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v2.weatherItem.MountainBg;
import com.mlog.weather.anim.v2.weatherItem.Sun;

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
    private Drawable mountain;

    public SunDrawable(Context context){
        light = context.getResources().getDrawable(R.drawable.v2_anim_light);
        light2 = context.getResources().getDrawable(R.drawable.v2_anim_light2);
        mountain = context.getResources().getDrawable(R.drawable.v2_anim_bg01);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        MountainBg mountainBg = new MountainBg(mountain);
        mountainBg.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(mountainBg);


        Sun sun = new Sun(light, light2);

        int r = (int) (338f * rect.width() / 482 / 2);
        int t = (int) (rect.top + 30f * rect.height() / 400);
        sun.setBounds(rect.centerX() - r, t, rect.centerX() + r, t + 2 * r);

        weatherItems.add(sun);
    }
}
