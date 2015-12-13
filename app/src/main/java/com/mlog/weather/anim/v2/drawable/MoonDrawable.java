package com.mlog.weather.anim.v2.drawable;

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v2.weatherItem.Moon;
import com.mlog.weather.anim.v2.weatherItem.MountainBg;
import com.mlog.weather.anim.v2.weatherItem.Star;

/**
 * 晴夜晚
 *
 * @author CJL
 * @since 2015-09-18
 */
public class MoonDrawable extends WeatherDrawable {

    Drawable mMoonDrawable;
    Drawable mountain;

    public MoonDrawable(Context context) {
        mMoonDrawable = context.getResources().getDrawable(R.drawable.moon);
        mountain = context.getResources().getDrawable(R.drawable.bg01n);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        MountainBg mountainBg = new MountainBg(mountain);
        mountainBg.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(mountainBg);


        Moon moon = new Moon(mMoonDrawable);

        int hmw = (int) (203f * rect.width() / 482 / 2);

        moon.setBounds(rect.centerX() - hmw, rect.centerY() - hmw, rect.centerX() + hmw, rect.centerY() + hmw);
        moon.setMoveDistance(hmw / 4f);
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
                return 700;
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
