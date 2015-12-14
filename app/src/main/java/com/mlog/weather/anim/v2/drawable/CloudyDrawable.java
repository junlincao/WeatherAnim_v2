package com.mlog.weather.anim.v2.drawable;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;

import com.example.cjl.weatheranim_v2.R;
import com.mlog.weather.anim.IWeatherItem;
import com.mlog.weather.anim.IWeatherRandomItem;
import com.mlog.weather.anim.WeatherDrawable;
import com.mlog.weather.anim.v2.weatherItem.Cloud;
import com.mlog.weather.anim.v2.weatherItem.Moon;
import com.mlog.weather.anim.v2.weatherItem.MountainBg;
import com.mlog.weather.anim.v2.weatherItem.Star;
import com.mlog.weather.anim.v2.weatherItem.Sun;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Random;

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

    private Drawable mountain;

    private Drawable cloud1;
    private Drawable cloud2;
    private Drawable cloud3;
    private Drawable cloud4;

    private Drawable sun1;
    private Drawable sun2;
    private Drawable moon;

    @CloudType
    private int mType;

    public CloudyDrawable(Context context, @CloudType int type) {
        cloud1 = context.getResources().getDrawable(R.drawable.v2_anim_cloud_1);
        cloud2 = context.getResources().getDrawable(R.drawable.v2_anim_cloud_2);
        cloud3 = context.getResources().getDrawable(R.drawable.v2_anim_cloud_3);
        cloud4 = context.getResources().getDrawable(R.drawable.v2_anim_cloud_4);
        this.mType = type;

        if (mType == TYPE_DAY) {
            mountain = context.getResources().getDrawable(R.drawable.v2_anim_bg01);
            sun1 = context.getResources().getDrawable(R.drawable.v2_anim_light);
            sun2 = context.getResources().getDrawable(R.drawable.v2_anim_light2);
        } else if (mType == TYPE_NIGHT) {
            mountain = context.getResources().getDrawable(R.drawable.v2_anim_bg01n);
            moon = context.getResources().getDrawable(R.drawable.v2_anim_moon);
        } else {
            mountain = context.getResources().getDrawable(R.drawable.v2_anim_bg02);
        }
    }

    @Override
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {
        MountainBg mountainBg = new MountainBg(mountain);
        mountainBg.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        weatherItems.add(mountainBg);

        if (mType == TYPE_DAY || mType == TYPE_NIGHT) {
            int color = mType == TYPE_NIGHT ? 0xff87b4c6 : Color.WHITE;

            // top
            float scale = rect.width() / 360;
            int w = (int) (174f * scale);
            int l = (int) (50f * scale);
            int top = (int) (92f * scale);
            int h = (int) (32f * scale);
            Cloud cloudTop = new Cloud(cloud1, cloud2, cloud3, cloud4);
            cloudTop.setCloudType(Cloud.TYPE_TOP);
            cloudTop.setBounds(l, top, l + w, top + h);
            cloudTop.setMoveDistance(20f * scale);
            cloudTop.setCloudColor(color);
            weatherItems.add(cloudTop);

            if (mType == TYPE_NIGHT) {
                //moon
                Moon sun = new Moon(moon);
                l = (int) (105f * scale);
                top = (int) (68f * scale);
                w = (int) (154f * scale);
                sun.setBounds(l, top, l + w, top + w);
                weatherItems.add(sun);
            } else {
                //sun
                Sun sun = new Sun(sun1, sun2);
                l = (int) (78f * scale);
                top = (int) (55f * scale);
                w = (int) (216f * scale);
                sun.setBounds(l, top, l + w, top + w);
                sun.setShowWave(false);
                sun.setSunColor(0xfffff2bb);
                weatherItems.add(sun);
            }

            // middle
            w = (int) (162f * scale);
            l = (int) (171f * scale);
            top = (int) (128f * scale);
            h = (int) (40f * scale);
            Cloud cloudMiddle = new Cloud(cloud1, cloud2, cloud3, cloud4);
            cloudMiddle.setCloudType(Cloud.TYPE_MIDDLE);
            cloudMiddle.setBounds(l, top, l + w, top + h);
            cloudMiddle.setMoveDistance(30f * scale);
            cloudMiddle.setCloudColor(color);
            weatherItems.add(cloudMiddle);

            // bottom
            w = (int) (284f * scale);
            l = (int) (12f * scale);
            top = (int) (171f * scale);
            h = (int) (60f * scale);
            Cloud cloudBottom = new Cloud(cloud1, cloud2, cloud3, cloud4);
            cloudBottom.setCloudType(Cloud.TYPE_BOTTOM);
            cloudBottom.setMoveDistance(40 * scale);
            cloudBottom.setBounds(l, top, l + w, top + h);
            cloudBottom.setCloudColor(color);
            weatherItems.add(cloudBottom);
        } else {
            int color = 0xffdddddd;

            // top
            float scale = rect.width() / 360;
            int w = (int) (174f * scale);
            int l = (int) (30f * scale);
            int top = (int) (70f * scale);
            int h = (int) (32f * scale);
            Cloud cloudTop = new Cloud(cloud1, cloud2, cloud3, cloud4);
            cloudTop.setCloudType(Cloud.TYPE_TOP);
            cloudTop.setBounds(l, top, l + w, top + h);
            cloudTop.setMoveDistance(20f * scale);
            cloudTop.setCloudColor(color);
            weatherItems.add(cloudTop);

            // middle
            w = (int) (162f * scale);
            l = (int) (146f * scale);
            top = (int) (108f * scale);
            h = (int) (40f * scale);
            Cloud cloudMiddle = new Cloud(cloud1, cloud2, cloud3, cloud4);
            cloudMiddle.setCloudType(Cloud.TYPE_MIDDLE);
            cloudMiddle.setBounds(l, top, l + w, top + h);
            cloudMiddle.setMoveDistance(30f * scale);
            cloudMiddle.setCloudColor(color);
            weatherItems.add(cloudMiddle);

            // bottom
            w = (int) (340f * scale);
            l = (int) (-10f * scale);
            top = (int) (169f * scale);
            h = (int) (60f * scale);
            Cloud cloudBottom = new Cloud(cloud1, cloud2, cloud3, cloud4);
            cloudBottom.setCloudType(Cloud.TYPE_BOTTOM);
            cloudBottom.setMoveDistance(40 * scale);
            cloudBottom.setBounds(l, top, l + w, top + h);
            cloudBottom.setCloudColor(color);
            weatherItems.add(cloudBottom);
        }
    }


    @Override
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, final Rect rect) {
        if (mType == TYPE_NIGHT) {
            final int mountainH = (int) (mountain.getIntrinsicHeight() * 1f * rect.width() / mountain.getIntrinsicWidth());
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

}
