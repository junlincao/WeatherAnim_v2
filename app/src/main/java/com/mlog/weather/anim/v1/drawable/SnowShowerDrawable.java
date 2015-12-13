package com.mlog.weather.anim.v1.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.v1.weatherItem.FreezingRainDrop;
import com.mlog.weather.anim.v1.weatherItem.SnowDrop;

import java.util.List;
import java.util.Random;

/**
 * 阵雪
 *
 * @author CJL
 * @since 2015-09-19
 */
public class SnowShowerDrawable extends ShowerDrawable {

    private Drawable mDrawable;

    public SnowShowerDrawable(Context context, @TimeType int mode) {
        super(context, mode);
        mDrawable = context.getResources().getDrawable(R.drawable.snow);
    }


    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, Rect rect) {
        final Random random = new Random(System.currentTimeMillis());
        int hw = (int) (rect.width() * 190f / 250);
        int left = (rect.width() - hw) / 4;
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
