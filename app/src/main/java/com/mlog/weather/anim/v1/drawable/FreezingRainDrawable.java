package com.mlog.weather.anim.v1.drawable;

import android.graphics.Rect;
import android.os.SystemClock;
import android.support.annotation.Nullable;


import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v1.weatherItem.Cloud;
import com.mlog.weather.anim.v1.weatherItem.FreezingRainDrop;
import com.mlog.weather.anim.v1.weatherItem.RainLine;

import java.util.List;
import java.util.Random;

/**
 * 冻雨
 *
 * @author CJL
 * @since 2015-09-15
 */
public class FreezingRainDrawable extends WeatherDrawable {

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        Cloud cloud = new Cloud();
        cloud.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(cloud);
    }

    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, Rect rect) {
        int w = rect.width();
        final int mLineMaxLen = (int) (40f / 250 * w);
        final int mLineMinLen = (int) (15f / 250 * w);

        int rw = (int) (rect.width() * 190f / 250);
        int left = (rect.width() - rw) / 2;
        int top = (int) (rect.width() * 120f / 250);
        final Rect mRainRect = new Rect(left, top, left + rw, rect.bottom);
        final int mDropWidth = (int) (2.23f / 115 * rect.width());
        final int mLineWidth = (int) (1f / 115 * rect.width());
        final Random random = new Random(System.currentTimeMillis());

        IWeatherRandomItem rainDrop = new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return 350;
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                FreezingRainDrop rainDrop = new FreezingRainDrop();
                int x = mRainRect.left + random.nextInt(mRainRect.width() - mDropWidth);
                rainDrop.setBounds(x, mRainRect.top, x + mDropWidth, mRainRect.bottom);

                return rainDrop;
            }
        };
        randomItems.add(rainDrop);


        IWeatherRandomItem rainLine = new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return 200;
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                RainLine rainLine = new RainLine();

                int x = mRainRect.left + random.nextInt(mRainRect.width() - mLineWidth);
                rainLine.setBounds(x, mRainRect.top, x + mLineWidth, mRainRect.bottom);
                rainLine.setLen(mLineMinLen, mLineMaxLen);

                return rainLine;
            }
        };
        randomItems.add(rainLine);
    }
}
