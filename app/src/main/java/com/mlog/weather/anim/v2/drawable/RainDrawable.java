package com.mlog.weather.anim.v2.drawable;

import android.graphics.Rect;
import android.support.annotation.IntDef;

import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v2.weatherItem.Rain;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Random;

/**
 * 雨
 * Created by cjl on 2015/12/12.
 */
public class RainDrawable extends WeatherDrawable {

    @IntDef({TYPE_STORM, TYPE_HEAVY, TYPE_MIDDLE, TYPE_LIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface RainType {

    }

    public static final int TYPE_STORM = 0;
    public static final int TYPE_HEAVY = 1;
    public static final int TYPE_MIDDLE = 2;
    public static final int TYPE_LIGHT = 3;

    // TYPE_STORM, TYPE_HEAVY ， TYPE_MIDDLE， TYPE_LIGHT对应的出现间隔时间
    static final int[] RAIN_INTERVAL = new int[]{30, 50, 75, 100};

    @RainType
    private int mRainType = TYPE_HEAVY;

    public RainDrawable(@RainType int rainType) {
        this.mRainType = rainType;
    }

    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, final Rect rect) {
        final float scale = rect.width() / 640f;
        final int xShift = (int) (200 * scale);
        final int rainWidth = rect.width() + xShift / 2;
        final int minLen = (int) (50 * scale);
        final int maxLen = (int) (120 * scale);

        final Random random = new Random();
        randomItems.add(new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return RAIN_INTERVAL[mRainType];
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                Rain rain = new Rain();
                int l = xShift / 2 + random.nextInt(rainWidth);
                rain.setXShift(xShift);
                rain.setBounds(l, rect.top, l + 1, rect.bottom);
                rain.setLen(minLen, maxLen);
                return rain;
            }
        });

    }
}
