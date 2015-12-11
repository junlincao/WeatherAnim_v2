package com.mlog.weather.anim.v2.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v2.weatherItem.Cloud;
import com.mlog.weather.anim.v2.weatherItem.Sun;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * 多云，阴天
 *
 * @author CJL
 * @since 2015-12-11
 */
public class CloudyDrawable extends WeatherDrawable {

    @IntDef({TYPE_DAY, TYPE_NIGHT, TYPE_CLOUDY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CloudType {
    }

    public static final int TYPE_DAY = 0;
    public static final int TYPE_NIGHT = 1;
    public static final int TYPE_CLOUDY = 2;

    private Drawable cloud1;
    private Drawable cloud2;

    private Drawable sun1;
    private Drawable sun2;

    @CloudType
    private int mType;

    public CloudyDrawable(Context context, @CloudType int type) {
        cloud1 = context.getResources().getDrawable(R.drawable.cloud_1);
        cloud2 = context.getResources().getDrawable(R.drawable.cloud_2);
        this.mType = type;

        if (mType == TYPE_DAY) {
            sun1 = context.getResources().getDrawable(R.drawable.light);
            sun2 = context.getResources().getDrawable(R.drawable.light2);
        }
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        if (mType == TYPE_DAY) {
            float moveDistance = 48f * rect.width() / 364;



            Sun sun = new Sun(sun1, sun2);
            int l = (int) (78f * rect.width() / 364);
            int top = (int) (74f * rect.width() / 364);
            int w = (int) (216f * rect.width() / 364);
            sun.setBounds(l, top, l + w, top + w);
            sun.setShowWave(false);
            sun.setSunColor(0xfffff2bb);
            weatherItems.add(sun);


            // middle
            Cloud cloudMiddle = new Cloud(cloud1, cloud2);
            cloudMiddle.setCloudType(Cloud.TYPE_MIDDLE);
            cloudMiddle.setMoveDistance(moveDistance);

            w = (int) (162f * rect.width() / 396);
            l = (int) (171f * rect.width() / 396);
            top = (int) (128f * rect.height() / 396);
            int h = (int) (40f * rect.height() / 396);
            cloudMiddle.setBounds(l, top, l + w, top + h);

            weatherItems.add(cloudMiddle);

            // bottom
            Cloud cloud = new Cloud(cloud1, cloud2);
            cloud.setCloudType(Cloud.TYPE_BOTTOM);
            cloud.setMoveDistance(moveDistance);
            w = (int) (284f * rect.width() / 364);
            l = (int) (12f * rect.width() / 396);
            top = (int) (171f * rect.height() / 400);
            h = (int) (60f * rect.height() / 400);
            cloud.setBounds(l, top, l + w, top + h);
            weatherItems.add(cloud);
        }
    }

}
