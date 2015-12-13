package com.mlog.weather.anim.v1.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v1.weatherItem.Cloud;
import com.mlog.weather.anim.v1.weatherItem.FreezingRainDrop;
import com.mlog.weather.anim.v1.weatherItem.SnowDrop;

import java.util.List;
import java.util.Random;

/**
 * Created by dongqi on 2015/9/13.
 *
 * @since 2015.09.13
 */
public class SnowDrawable extends WeatherDrawable {
    Drawable mDrawable;

    public SnowDrawable(Context context) {
        mDrawable = context.getResources().getDrawable(R.drawable.snow);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        Cloud cloud = new Cloud();
        cloud.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(cloud);
    }

    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, Rect rect) {
        final Random random = new Random(System.currentTimeMillis());
        int hw = (int) (rect.width() * 190f / 250);
        int left = (rect.width() - hw) / 2;
        int top = (int) (rect.width() * 120f / 250);
        final Rect snowRect = new Rect(left, top, left + hw, rect.bottom);
        final int snowWidth = (int) (15f / 190 * snowRect.width());
        final Interpolator mFreezingInterpolator = new LinearInterpolator();
        final int freezingWidth = (int) (4.46f / 190 * snowRect.width());

        IWeatherRandomItem snow = new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return 300;
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                SnowDrop snow = new SnowDrop(mDrawable);
                int x = snowRect.left + random.nextInt(snowRect.width() - snowWidth);
                snow.setBounds(x, snowRect.top, x + snowWidth, snowRect.bottom);

                return snow;
            }
        };

        IWeatherRandomItem freezing = new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return 350;
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                FreezingRainDrop frd = new FreezingRainDrop();
                int left = snowRect.left + random.nextInt(snowRect.width() - freezingWidth);
                frd.setBounds(left, snowRect.top, left + freezingWidth, snowRect.bottom);
                frd.setAlphaCenter(1700);
                frd.setDropTime(2000);
                frd.setInterpolator(mFreezingInterpolator);

                return frd;
            }
        };
        randomItems.add(snow);
        randomItems.add(freezing);
    }
}
