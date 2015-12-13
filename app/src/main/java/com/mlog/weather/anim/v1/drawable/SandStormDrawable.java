package com.mlog.weather.anim.v1.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v1.weatherItem.Dust;
import com.mlog.weather.anim.v1.weatherItem.FogBg;

import java.util.List;
import java.util.Random;


/**
 * 沙尘暴
 *
 * @author CJL
 * @since 2015-09-20
 */
public class SandStormDrawable extends WeatherDrawable {
    private Drawable mFogBg;

    public SandStormDrawable(Context context) {
        mFogBg = context.getResources().getDrawable(R.drawable.fog_bg);
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, final Rect rect) {
        FogBg fogBg = new FogBg(mFogBg);
        fogBg.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(fogBg);
    }

    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, final Rect rect) {
        final int rectWidth = rect.width();

        final Random random = new Random();
        final int[] size = new int[]{
                (int) (2.5f / 250 * rectWidth), (int) (1.75f / 250 * rectWidth), (int) (1f / 250 * rectWidth)
        };

        IWeatherRandomItem iri = new IWeatherRandomItem() {
            @Override
            public int getInterval() {
                return 450;
            }

            @Override
            public IWeatherItem getRandomWeatherItem() {
                Dust dust = new Dust();
                int dsize = size[random.nextInt(size.length)];
                int top = rect.top + random.nextInt(rect.height());
                dust.setBounds(rect.left, top, rect.right, top + dsize);
                return dust;
            }
        };

        randomItems.add(iri);
    }
}
