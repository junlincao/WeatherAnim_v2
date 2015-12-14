package com.mlog.weather.anim.v2.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
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

    private Drawable mWindow;

    public Fog(Drawable window) {
        mWindow = window;
    }

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

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        float scale = mBounds.width() / 1080f;

        int halfW = (int) (mWindow.getIntrinsicWidth() * scale / 2f);
        int h = (int) (mWindow.getIntrinsicHeight() * scale);
        int b = (int) (52f * scale);

        mWindow.setBounds(mBounds.centerX() - halfW, mBounds.bottom - b - h, mBounds.centerX() + halfW, mBounds.bottom - b);
    }

    public void setFogColor(@ColorInt int color) {
        mFogColor = color;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStartTime() < 0) {
            return;
        }
        int t = (int) ((time - getStartTime()) % ANIM_DURATION);
        int alpha = (int) (0.8f * 255 * mFogInt.getInterpolation(t * 1f / ANIM_DURATION));
        canvas.drawColor(Color.argb(alpha, Color.red(mFogColor), Color.green(mFogColor), Color.blue(mFogColor)));

        mWindow.draw(canvas);
    }
}
