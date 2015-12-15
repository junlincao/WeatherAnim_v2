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

    @IntDef({TYPE_STORM, TYPE_HEAVY, TYPE_MIDDLE, TYPE_LIGHT, TYPE_THUNDER, TYPE_THUNDER_HAIL, TYPE_FROZEN})
    @Retention(RetentionPolicy.SOURCE)
    @interface RainType {

    }

    public static final int TYPE_STORM = 0; // 暴雨
    public static final int TYPE_HEAVY = 1; // 大雨
    public static final int TYPE_MIDDLE = 2; // 中雨 & 阵雨
    public static final int TYPE_LIGHT = 3; // 小雨
    public static final int TYPE_THUNDER = 4; // 雷阵雨
    public static final int TYPE_THUNDER_HAIL = 5; // 雷阵雨伴有冰雹
    public static final int TYPE_FROZEN = 6; // 冻雨

    // TYPE_STORM, TYPE_HEAVY ， TYPE_MIDDLE， TYPE_LIGHT对应的出现间隔时间
    static final int[] RAIN_INTERVAL = new int[]{30, 50, 75, 100, 50, 75, 100};

    @RainType
    private int mRainType = TYPE_HEAVY;
    private Drawable mountain;
    private int mountainGroundH;

    private int mTitleHeight;

    public RainDrawable(Context context, @RainType int rainType, boolean isNight) {
        this.mRainType = rainType;

        mountain = context.getResources().getDrawable(isNight ? R.drawable.v2_anim_bg03n : R.drawable.v2_anim_bg03);

        mTitleHeight = (int) (context.getResources().getDisplayMetrics().density * 70);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        mountainGroundH = (int) (50f / 339 * mountain.getIntrinsicHeight() * rect.width() / mountain.getIntrinsicWidth());

        if (mRainType == TYPE_THUNDER || mRainType == TYPE_THUNDER_HAIL) {
            Light light = new Light(Light.TYPE_BACKGROUND);
            light.setBounds(rect.left, rect.top, rect.right, rect.bottom - mountainGroundH);
            weatherItems.add(light);
        }

        MountainBg mountainBg = new MountainBg(mountain);
        mountainBg.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(mountainBg);

        if (mRainType == TYPE_THUNDER || mRainType == TYPE_THUNDER_HAIL) {
            Light light = new Light(Light.TYPE_FOREGROUND);
            light.setBounds(rect.left, rect.top, rect.right, rect.bottom - mountainGroundH);
            weatherItems.add(light);
        }
    }

    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, final Rect rect) {
        final float scale = rect.width() / 360f;
        final int xShift = mRainType == TYPE_FROZEN ? 0 : (int) (200 * scale);
        final double xShiftAngle = mRainType == TYPE_FROZEN ? 90 : 65;
        final int rainWidth = rect.width() + xShift;
        final int minLen = (int) (50 * scale);
        final int maxLen = (int) (120 * scale);

        int tempTop = rect.top;
        if (mRainType == TYPE_THUNDER || mRainType == TYPE_THUNDER_HAIL) {
            tempTop += mTitleHeight;
        }
        final int top = tempTop;

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
                rain.setLen(minLen, maxLen);
                rain.setShiftAngle(xShiftAngle);
                rain.setBounds(l, top, l + 1, rect.bottom - mountainGroundH);
                return rain;
            }
        });
    }
}
