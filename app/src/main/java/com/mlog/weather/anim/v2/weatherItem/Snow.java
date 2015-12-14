package com.mlog.weather.anim.v2.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 雪
 * Created by cjl on 2015/12/13.
 */
public class Snow extends SimpleWeatherItem {

    static int ALPHA_CENTER = 500;

    private int mAnimDuration = 3000;

    public Snow(int duration) {
        this.mAnimDuration = duration;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        int t = (int) (time - getStartTime());
        if (t > mAnimDuration) {
            stop();
            return;
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        if (t < ALPHA_CENTER) {
            paint.setAlpha((int) (255f * t / ALPHA_CENTER));
        }

        int h = mBounds.height() + mBounds.width() * 2;

        int centerY = mBounds.top - mBounds.width() / 2 + (int) (h * t / mAnimDuration);
        int centerX = mBounds.centerX();
        int halfSize = mBounds.width() / 2;

        canvas.save();
        canvas.clipRect(mBounds);
        canvas.rotate(45, centerX, centerY);
        canvas.drawRect(centerX - halfSize, centerY - halfSize, centerX + halfSize, centerY + halfSize, paint);
        canvas.restore();

    }
}
