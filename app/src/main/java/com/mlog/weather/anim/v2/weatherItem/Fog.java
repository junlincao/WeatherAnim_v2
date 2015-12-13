package com.mlog.weather.anim.v2.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.view.animation.Interpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 雾，霾，沙尘暴
 * <p/>
 * Created by cjl on 2015/12/13.
 */
public class Fog extends SimpleWeatherItem {
    static final int ANIM_DURATION = 7000;
    private int mFogColor = 0xff808080;

    private Interpolator mFogInt = new Interpolator() {
        @Override
        public float getInterpolation(float input) {
            if (input < 2f / 7) {
                return input * 7 / 2f;
            } else if (input < 4f / 7) {
                return 1;
            } else if (input < 6f / 7) {
                return 3 - input * 7f / 2;
            }
            return 0;
        }
    };

    public void setFogColor(@ColorInt int color) {
        mFogColor = color;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        int t = (int) ((time - getStartTime()) % ANIM_DURATION);
        int alpha = (int) (0.8f * 255 * mFogInt.getInterpolation(t * 1f / ANIM_DURATION));
        canvas.drawColor(Color.argb(alpha, Color.red(mFogColor), Color.green(mFogColor), Color.blue(mFogColor)));
    }
}
