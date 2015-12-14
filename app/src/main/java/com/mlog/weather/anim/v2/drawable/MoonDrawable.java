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
    int mountainH;

    public MoonDrawable(Context context) {
        mMoonDrawable = context.getResources().getDrawable(R.drawable.v2_anim_moon);
        mountain = context.getResources().getDrawable(R.drawable.v2_anim_bg01n);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        MountainBg mountainBg = new MountainBg(mountain);
        mountainBg.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(mountainBg);
        mountainH = (int) (mountain.getIntrinsicHeight() * 1f * rect.width() / mountain.getIntrinsicWidth());

        float scale = rect.width() / 360;
        Moon moon = new Moon(mMoonDrawable);
        int l = (int) (105f * scale);
        int top = (int) (68f * scale);
        int w = (int) (154f * scale);
        moon.setBounds(l, top, l + w, top + w);
        moon.setMoveDistance(w / 9f);
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
                int top = rect.top + random.nextInt(rect.height() - mountainH - w);
                star.setBounds(left, top, left + w, top + w);

                return star;
            }
        };
        randomItems.add(iri);
    }

}
