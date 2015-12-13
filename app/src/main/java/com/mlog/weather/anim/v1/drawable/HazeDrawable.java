package com.mlog.weather.anim.v1.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v1.weatherItem.Dust;
import com.mlog.weather.anim.v1.weatherItem.Haze;

import java.util.List;
import java.util.Random;

/**
 * 霾
 *
 * @author CJL
 * @since 2015-09-20
 */
public class HazeDrawable extends WeatherDrawable {

    Drawable mBg;

    public HazeDrawable(Context context) {
        mBg = context.getResources().getDrawable(R.drawable.fog_bg);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        Haze haze = new Haze(mBg);
        haze.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(haze);
    }

    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, Rect rect) {
        final int rectWidth = rect.width();
        final int bottom = (int) (rect.bottom - 15f / 250 * rectWidth);
        final int top = rect.top;
        final Random random = new Random();
        final int[] size = new int[]{
                (int) (2.5f / 250 * rectWidth), (int) (1.75f / 250 * rectWidth), (int) (1f / 250 * rectWidth)
        };

        IWeatherRandomItem iri = new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return 700;
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                Dust dust = new Dust();
                int dsize = size[random.nextInt(size.length)];
                int left = random.nextInt(rectWidth - dsize);
                dust.setBounds(left, top, left + dsize, bottom);
                return dust;
            }
        };

        randomItems.add(iri);
    }
}
