package com.mlog.weather.anim.v1.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v1.weatherItem.Fog;

import java.util.List;

/**
 * 雾
 *
 * @author CJL
 * @since 2015-09-20
 */
public class FogDrawable extends WeatherDrawable {

    Drawable mBg;
    Drawable mFog;

    public FogDrawable(Context context) {
        mBg = context.getResources().getDrawable(R.drawable.fog_bg);
        mFog = context.getResources().getDrawable(R.drawable.fog);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        Fog fog = new Fog(mBg, mFog);
        fog.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(fog);
    }
}
