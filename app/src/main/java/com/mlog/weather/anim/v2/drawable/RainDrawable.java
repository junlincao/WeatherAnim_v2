package com.mlog.weather.anim.v2.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v2.weatherItem.Light;
import com.mlog.weather.anim.v2.weatherItem.MountainBg;
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

    @IntDef({TYPE_STORM, TYPE_HEAVY, TYPE_MIDDLE, TYPE_LIGHT, TYPE_THUNDER, TYPE_THUNDER_HAIL})
    @Retention(RetentionPolicy.SOURCE)
    @interface RainType {

    }

    public static final int TYPE_STORM = 0; // 暴雨
    public static final int TYPE_HEAVY = 1; // 大雨
    public static final int TYPE_MIDDLE = 2; // 中雨
    public static final int TYPE_LIGHT = 3; // 小雨
    public static final int TYPE_THUNDER = 4; // 雷阵雨
    public static final int TYPE_THUNDER_HAIL = 5; // 雷阵雨伴有冰雹

    // TYPE_STORM, TYPE_HEAVY ， TYPE_MIDDLE， TYPE_LIGHT对应的出现间隔时间
    static final int[] RAIN_INTERVAL = new int[]{30, 50, 75, 100, 50, 75};

    @RainType
    private int mRainType = TYPE_HEAVY;
    private Drawable mountain;

    public RainDrawable(Context context, @RainType int rainType, boolean isNight) {
        this.mRainType = rainType;

        mountain = context.getResources().getDrawable(isNight ? R.drawable.bg03n : R.drawable.bg03);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        if (mRainType == TYPE_THUNDER || mRainType == TYPE_THUNDER_HAIL) {
            Light light = new Light(Light.TYPE_BACKGROUND);
            weatherItems.add(light);
        }

        MountainBg mountainBg = new MountainBg(mountain);
        mountainBg.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(mountainBg);

        if (mRainType == TYPE_THUNDER || mRainType == TYPE_THUNDER_HAIL) {
            Light light = new Light(Light.TYPE_FOREGROUND);
            weatherItems.add(light);
        }
    }

    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, final Rect rect) {
        final float scale = rect.width() / 640f;
        final int xShift = (int) (200 * scale);
        final int rainWidth = rect.width() + xShift;
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
                int l = random.nextInt(rainWidth);
                rain.setXShift(xShift);
                rain.setBounds(l, rect.top, l + 1, rect.bottom);
                rain.setLen(minLen, maxLen);
                return rain;
            }
        });

    }
}
