package com.mlog.weather.anim.v1.weatherItem;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.mlog.weather.anim.SimpleWeatherItem;


/**
 * 雾背景
 *
 * @author CJL
 * @since 2015-09-20
 */
public class FogBg extends SimpleWeatherItem {

    Drawable mFogBg;

    public FogBg(Drawable drawable) {
        mFogBg = drawable;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mFogBg.setBounds(left, top, right, bottom);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }
        mFogBg.draw(canvas);
    }
}
