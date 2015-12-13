package com.mlog.weather.anim.v1.drawable;

import android.content.Context;
import android.graphics.Rect;

import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.v1.weatherItem.Cloud;
import com.mlog.weather.anim.v1.weatherItem.Moon;
import com.mlog.weather.anim.v1.weatherItem.RainLine;
import com.mlog.weather.anim.v1.weatherItem.Sun;

import java.util.List;
import java.util.Random;

/**
 * 阵雨
 *
 * @author CJL
 * @since 2015-09-19
 */
public class ShowerDrawable extends CloudsDrawable {

    public ShowerDrawable(Context context, @TimeType int mode) {
        super(context, mode);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        float w = rect.width();
        float hSunW = 120 / 420f * w; // 太阳/月亮一半宽度
        float sunCX = 180f / 250 * w;
        float top = -10f / 250 * w;
        Rect r = new Rect((int) (sunCX - hSunW), (int) top, (int) (sunCX + hSunW), (int) (hSunW * 2 + top));

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
        int cloudTop = rect.top;
        cloud.setBounds(cloudLeft, cloudTop, cloudLeft + rect.width(), cloudTop + rect.height());
        weatherItems.add(cloud);
    }

    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, Rect rect) {
        final Random random = new Random(System.currentTimeMillis());
        int w = rect.width();
        final int mLineMaxLen = (int) (40f / 250 * w);
        final int mLineMinLen = (int) (15f / 250 * w);

        int rw = (int) (rect.width() * 190f / 250);
        int left = (rect.width() - rw) / 4;
        int top = (int) (rect.width() * 128f / 250);
        final Rect mRainRect = new Rect(left, top, left + rw, rect.bottom);
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
                rainLine.setBounds(x, mRainRect.top, x + lineWidth, mRainRect.bottom);
                rainLine.setLen(mLineMinLen, mLineMaxLen);
                return rainLine;
            }
        };
        randomItems.add(rainLine);
    }
}
