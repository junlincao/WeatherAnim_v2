package com.mlog.weather.anim.v2.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v2.weatherItem.MountainBg;
import com.mlog.weather.anim.v2.weatherItem.Rain;
import com.mlog.weather.anim.v2.weatherItem.Snow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Random;

/**
 * 雪
 * Created by cjl on 2015/12/13.
 */
public class SnowDrawable extends WeatherDrawable {

    @IntDef({TYPE_STORM, TYPE_HEAVY, TYPE_MIDDLE, TYPE_LIGHT, TYPE_FEW, TYPE_WITH_RAIN})
    @Retention(RetentionPolicy.SOURCE)
    @interface SnowType {

    }

    public static final int TYPE_STORM = 0;
    public static final int TYPE_HEAVY = 1;
    public static final int TYPE_MIDDLE = 2;
    public static final int TYPE_LIGHT = 3;
    public static final int TYPE_FEW = 4;
    public static final int TYPE_WITH_RAIN = 5;

    // TYPE对应的出现间隔时间
    static final int[] SNOW_INTERVAL = new int[]{100, 100, 150, 200, 150, 150};

    @SnowType
    private int mSnowType = TYPE_MIDDLE;

    private Drawable mountain;

    public SnowDrawable(Context context, @SnowType int type, boolean isNight) {
        this.mSnowType = type;

        mountain = context.getResources().getDrawable(isNight ? R.drawable.v2_anim_bg04n : R.drawable.v2_anim_bg04);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        MountainBg mountainBg = new MountainBg(mountain);
        mountainBg.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(mountainBg);
    }

    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, final Rect rect) {
        final int mountainGroundH = (int) (50f / 339 * mountain.getIntrinsicHeight() * rect.width() / mountain.getIntrinsicWidth());

        final int type1W = (int) (9f * rect.width() / 640);
        final int type2W = (int) (5f * rect.width() / 640);


        final Random random = new Random();
        randomItems.add(new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return SNOW_INTERVAL[mSnowType];
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                Snow snow = new Snow(3000);
                int l = random.nextInt(rect.width() - type1W);
                snow.setBounds(l, rect.top, l + type1W, rect.bottom - mountainGroundH);

                return snow;
            }
        });


        randomItems.add(new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return SNOW_INTERVAL[mSnowType] * 2;
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                Snow snow = new Snow(5000);
                int l = random.nextInt(rect.width() - type2W);
                snow.setBounds(l, rect.top, l + type2W, rect.bottom - mountainGroundH);
                return snow;
            }
        });

        if (mSnowType == TYPE_WITH_RAIN) {
            final float scale = rect.width() / 640f;
            final int minLen = (int) (50 * scale);
            final int maxLen = (int) (120 * scale);
            randomItems.add(new IWeatherRandomItem() {
                @Override
                public int getInterval() {
                    return SNOW_INTERVAL[mSnowType];
                }

                @Override
                public IWeatherItem getRandomWeatherItem() {
                    Rain rain = new Rain();
                    int l = random.nextInt(rect.width() - 1);
                    rain.setShiftAngle(90);
                    rain.setBounds(l, rect.top, l + 1, rect.bottom - mountainGroundH);
                    rain.setLen(minLen, maxLen);
                    return rain;
                }
            });
        }
    }
}
