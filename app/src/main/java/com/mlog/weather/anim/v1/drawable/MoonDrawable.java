package com.mlog.weather.anim.v1.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v1.weatherItem.Moon;
import com.mlog.weather.anim.v1.weatherItem.Star;

import java.util.List;
import java.util.Random;

/**
 * 晴夜晚
 *
 * @author CJL
 * @since 2015-09-18
 */
public class MoonDrawable extends WeatherDrawable {

    Drawable mMoonDrawable;

    public MoonDrawable(Context context) {
        mMoonDrawable = context.getResources().getDrawable(R.drawable.moon);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        Moon moon = new Moon(mMoonDrawable);
        moon.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(moon);
    }

    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, final Rect rect) {
        final int mMinStarWidth = (int) (0.5f / 250 * rect.width());
        final int mMaxStarWidth = (int) (3f / 250 * rect.width());
        final Random random = new Random();

        IWeatherRandomItem iri = new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return 1200;
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                Star star = new Star();
                int w = mMinStarWidth + random.nextInt(mMaxStarWidth - mMinStarWidth);
                int left = rect.left + random.nextInt(rect.width() - w);
                int top = rect.top + random.nextInt(rect.height() - w);
                star.setBounds(left, top, left + w, top + w);

                return star;
            }
        };
        randomItems.add(iri);
    }

}
