package com.mlog.weather.anim.v1.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v1.weatherItem.Cloud;
import com.mlog.weather.anim.v1.weatherItem.Light;
import com.mlog.weather.anim.v1.weatherItem.RainLine;

import java.util.List;
import java.util.Random;

/**
 * 雷阵雨
 *
 * @author CJL
 * @since 2015-09-20
 */
public class ThundersShowerDrawable extends WeatherDrawable {

    Drawable mLightDrawable;

    public ThundersShowerDrawable(Context context) {
        mLightDrawable = context.getResources().getDrawable(R.drawable.lightning);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        int rectWidth = rect.width();

        int cloudLeft = (int) (10f / 250 * rectWidth);
        int cloudTop = -(int) (5f / 250 * rectWidth);
        Cloud cloud = new Cloud();
        cloud.setBounds(cloudLeft, cloudTop, cloudLeft + rectWidth, cloudTop + rectWidth);
        weatherItems.add(cloud);

        int w = (int) (rectWidth / 10f);
        int h = (int) (66f / 250 * rectWidth);

        int l = (int) (137f / 250 * rectWidth);
        int t = (int) (124f / 250 * rectWidth);

        Light light = new Light(mLightDrawable);
        light.setBounds(l, t, l + w, t + h);
        weatherItems.add(light);
    }

    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, Rect rect) {
        final Random random = new Random(System.currentTimeMillis());
        int w = rect.width();
        final int mXShift = (int) (57f / 250 * w);
        final int mLineMaxLen = (int) (40f / 250 * w);
        final int mLineMinLen = (int) (15f / 250 * w);

        int rw = (int) (rect.width() * 190f / 250);
        int left = (rect.width() - rw) / 2 + mXShift;
        int top = (int) (rect.width() * 120f / 250);
        final Rect mRainRect = new Rect(left, top, left + rw - mXShift, rect.bottom);
        final int lineWidth = (int) (1 / 115f * rect.width());

        IWeatherRandomItem rainLine = new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return 300;
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                RainLine rainLine = new RainLine();
                int x = mRainRect.left + random.nextInt(mRainRect.width() - lineWidth);
                rainLine.setXShift(mXShift);
                rainLine.setBounds(x, mRainRect.top, x + lineWidth, mRainRect.bottom);
                rainLine.setLen(mLineMinLen, mLineMaxLen);
                return rainLine;
            }
        };
        randomItems.add(rainLine);
    }
}
