package com.mlog.weather.anim.v1.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;


import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v1.weatherItem.Cloud;
import com.mlog.weather.anim.v1.weatherItem.Moon;
import com.mlog.weather.anim.v1.weatherItem.Sun;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * 多云
 *
 * @author CJL
 * @since 2015-09-15
 */
public class CloudsDrawable extends WeatherDrawable {

    @IntDef({TYPE_DAY, TYPE_NIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface TimeType {
    }

    public static final int TYPE_DAY = 0;
    public static final int TYPE_NIGHT = 1;

    Drawable mD1;
    Drawable mD2;
    Drawable mD3;
    Drawable mN1;

    @TimeType
    int mMode;

    public CloudsDrawable(Context context, @TimeType int mode) {
        if(mode == TYPE_DAY) {
            mD1 = context.getResources().getDrawable(R.drawable.cloudy_sun_1);
            mD2 = context.getResources().getDrawable(R.drawable.cloudy_sun_1);
            mD3 = context.getResources().getDrawable(R.drawable.cloudy_sun_2);
        } else {
            mN1 = context.getResources().getDrawable(R.drawable.cloudy_moon);
        }
        this.mMode = mode;
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        float w = rect.width();
        float hSunW = 120 / 420f * w; // 太阳/月亮一半宽度
        float sunCX = 180f / 250 * w;
        float sunCY = 110f / 250 * w;
        Rect r = new Rect((int) (sunCX - hSunW), (int) (sunCY - hSunW), (int) (sunCX + hSunW), (int) (sunCY + hSunW));

        if (mMode == TYPE_DAY) {
            Sun sun = new Sun(mD1, mD2, mD3);
            sun.setShowWave(false);
            sun.setRoateSpeed(0, 360f / 30000, -360f / 60000);
            sun.setBounds(r.left, r.top, r.right, r.bottom);
            weatherItems.add(sun);
        } else {
            Moon moon = new Moon(mN1);
            moon.setBounds(r.left, r.top, r.right, r.bottom);
            weatherItems.add(moon);
        }

        Cloud cloud = new Cloud();
        int cloudLeft = rect.left + (int) (-rect.width() * 0.08f);
        int cloudTop = rect.top + (int) (0.25f * rect.width());
        cloud.setBounds(cloudLeft, cloudTop, cloudLeft + rect.width(), cloudTop + rect.height());
        weatherItems.add(cloud);
    }
}
