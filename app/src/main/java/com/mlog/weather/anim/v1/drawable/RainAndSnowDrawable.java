package com.mlog.weather.anim.v1.drawable;

import android.content.Context;
import android.graphics.Rect;

import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.v1.weatherItem.RainLine;
import com.mlog.weather.anim.v1.weatherItem.SnowDrop;

import java.util.List;
import java.util.Random;

/**
 * 雨夹雪
 *
 * @author CJL
 * @since 2015-09-19
 */
public class RainAndSnowDrawable extends SnowDrawable {

    public RainAndSnowDrawable(Context context) {
        super(context);
    }


    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, Rect rect) {
        final Random random = new Random(System.currentTimeMillis());
        int w = rect.width();
        int hw = (int) (w * 190f / 250);
        int left = (w - hw) / 2;
        int top = (int) (w * 120f / 250);
        final Rect snowRect = new Rect(left, top, left + hw, rect.bottom);
        final int snowWidth = (int) (15f / 190 * snowRect.width());
        final int lineWidth = (int) (1 / 115f * w);
        final int mLineMaxLen = (int) (40f / 250 * w);
        final int mLineMinLen = (int) (15f / 250 * w);

        IWeatherRandomItem snow = new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return 270;
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                SnowDrop snow = new SnowDrop(mDrawable);
                int x = snowRect.left + random.nextInt(snowRect.width() - snowWidth);
                snow.setBounds(x, snowRect.top, x + snowWidth, snowRect.bottom);

                return snow;
            }
        };

        IWeatherRandomItem rainLine = new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return 300;
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                RainLine rainLine = new RainLine();
                int x = snowRect.left + random.nextInt(snowRect.width() - lineWidth);
                rainLine.setBounds(x, snowRect.top, x + lineWidth, snowRect.bottom);
                rainLine.setLen(mLineMinLen, mLineMaxLen);
                return rainLine;
            }
        };
        randomItems.add(rainLine);
        randomItems.add(snow);
    }
}
