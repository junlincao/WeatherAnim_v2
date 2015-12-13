package com.mlog.weather.anim.v1.drawable;

import android.graphics.Rect;

import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v1.weatherItem.Cloud;
import com.mlog.weather.anim.v1.weatherItem.Hail;

import java.util.List;
import java.util.Random;

/**
 * 冰雹天气
 *
 * @author CJL
 * @since 2015-09-11
 */
public class HailDrawable extends WeatherDrawable {
    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        Cloud cloud = new Cloud();
        cloud.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(cloud);
    }

    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, Rect rect) {

        final Random random = new Random(System.currentTimeMillis());

        int hw = (int) (rect.width() * 190f / 250);
        int left = (rect.width() - hw) / 2;
        int top = (int) (rect.width() * 120f / 250);
        final Rect mHailRect = new Rect(left, top, left + hw, rect.bottom);


        IWeatherRandomItem iri = new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return 200;
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                Hail hail = new Hail();
                int hailWidth = (int) (28f / 190 * mHailRect.width());
                int x = mHailRect.left + random.nextInt(mHailRect.width() - hailWidth);
                hail.setBounds(x, mHailRect.top, x + hailWidth, mHailRect.bottom);

                return hail;
            }
        };

        randomItems.add(iri);
    }
}
