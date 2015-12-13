package com.mlog.weather.anim.v1.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.animation.AccelerateInterpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 冻雨
 *
 * @author CJL
 * @since 2015-09-15
 */
public class FreezingRainDrop extends SimpleWeatherItem {
    // 下落耗时
    private static final int DROP_TIME = 800;

    private int mDropTime = DROP_TIME;
    private int mAlphaCenter = 600;

    public FreezingRainDrop() {
        mInterpolator = new AccelerateInterpolator();
    }

    public void setDropTime(int dropTime) {
        mDropTime = dropTime;
    }

    public void setAlphaCenter(int centerTime) {
        this.mAlphaCenter = centerTime;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }

        int t = (int) (time - getStartTime());
        if (t > mDropTime) {
            stop();
            return;
        }

        float progress = t * 1f / mDropTime;

        int alpha = progress <= mAlphaCenter ? 255 : (int) (255 - 255f * (t - mAlphaCenter) / (mDropTime - mAlphaCenter));
        paint.setColor(Color.argb(alpha, 255, 255, 255));
        float y = mBounds.top + mBounds.height() * mInterpolator.getInterpolation(progress);
        float x = mBounds.centerX();

        canvas.save();
        canvas.rotate(45, x, y);

        float hRectW = mBounds.width() / 2;
        canvas.drawRect(x - hRectW, y - hRectW, x + hRectW, y + hRectW, paint);

        canvas.restore();

    }
}
