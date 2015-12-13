package com.mlog.weather.anim.v2.weatherItem;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 山背景
 * Created by cjl on 2015/12/13.
 */
public class MountainBg extends SimpleWeatherItem {

    private Drawable mDrawable;

    public MountainBg(Drawable mountain) {
        mDrawable = mountain;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        int dw = mDrawable.getIntrinsicWidth();
        int dh = mDrawable.getIntrinsicHeight();

        int h = (int) (dw * 1f * dh / mBounds.width());
        mDrawable.setBounds(0, bottom - h, right, bottom);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        mDrawable.draw(canvas);
    }
}
