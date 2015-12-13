package com.mlog.weather.anim.v1.drawable;

import android.graphics.Rect;
import android.os.SystemClock;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v1.weatherItem.Cloud;
import com.mlog.weather.anim.v1.weatherItem.RainDrop;
import com.mlog.weather.anim.v1.weatherItem.RainLine;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Random;

/**
 * 下雨（小雨与大雨）
 *
 * @author CJL
 * @since 2015-09-11
 */
public class RainDrawable extends WeatherDrawable {
    @IntDef({RAIN_TYPE_HEAVY, RAIN_TYPE_SMALL})
    @Retention(RetentionPolicy.SOURCE)
    @interface RainType {
    }

    public RainDrawable() {

    }

    public RainDrawable(@RainType int type) {
        setType(type);
    }

    /**
     * 大雨
     */
    public static final int RAIN_TYPE_HEAVY = 0;
    /**
     * 小雨
     */
    public static final int RAIN_TYPE_SMALL = 1;

    int mRainType = RAIN_TYPE_HEAVY;

    /**
     * 设置雨大小类型
     *
     * @param type 类型
     */
    public void setType(@RainType int type) {
        mRainType = type;
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
        int w = rect.width();
        final int mXShift = (int) (57f / 250 * w);
        final int mLineMaxLen = (int) (40f / 250 * w);
        final int mLineMinLen = (int) (15f / 250 * w);

        int rw = (int) (rect.width() * 190f / 250);
        int left = (rect.width() - rw) / 2 + mXShift;
        int top = (int) (rect.width() * 120f / 250);
        final Rect mRainRect = new Rect(left, top, left + rw - mXShift, rect.bottom);
        final int lineWidth = (int) ((mRainType == RAIN_TYPE_HEAVY ? 1.8f : 1) / 115f * rect.width());

        if (mRainType == RAIN_TYPE_HEAVY) {
            final int dropWidth = (int) (2.5f / 115 * rect.width());
            IWeatherRandomItem rainDrop = new IWeatherRandomItem() {
                @Override
                public int getInterval() {
                    return 250;
                }

                @Override
                public IWeatherItem getRandomWeatherItem() {
                    RainDrop rainDrop = new RainDrop();
                    int x = mRainRect.left + random.nextInt(mRainRect.width() - dropWidth);
                    rainDrop.setBounds(x, mRainRect.top, x + dropWidth, mRainRect.bottom);
                    rainDrop.setXShift(mXShift);

                    return rainDrop;
                }
            };
            randomItems.add(rainDrop);
        }

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
