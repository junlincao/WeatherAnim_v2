package com.mlog.weather.anim.v2.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.Interpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 雨
 * Created by cjl on 2015/12/12.
 */
public class Rain extends SimpleWeatherItem {

    static final int ANIM_DURATION = 2000;

    private Interpolator mAlphaInt = new Interpolator() {
        final int ALPHA_DURATION = 250;

        @Override
        public float getInterpolation(float input) {
            if (input < ALPHA_DURATION * 1f / ANIM_DURATION) {
                return 1 * input * ANIM_DURATION / ALPHA_DURATION;
            }
            return 1;
        }
    };

    private Interpolator mLengthInt = new Interpolator() {
        @Override
        public float getInterpolation(float input) {
            if (input > 1) {
                return 1;
            }
            return input;
        }
    };

    private Interpolator mYInt = PathInterpolatorCompat.create(0.587f, 0, 0.788f, 1);

    private int mMinLen;
    private int mMaxLen;
    private int mXShift;
    private float angle;

    public void setXShift(int xShift) {
        this.mXShift = xShift;
        angle = (float) (90 - Math.toDegrees(Math.atan2(mBounds.height() + mMinLen, mXShift)));
    }

    public void setLen(int minLen, int maxLen) {
        this.mMinLen = minLen;
        this.mMaxLen = maxLen;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        angle = (float) (90 - Math.toDegrees(Math.atan2(mBounds.height(), mXShift)));
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (time - getStartTime() > ANIM_DURATION) {
            stop();
            return;
        }
        float pg = (time - getStartTime()) * 1f / ANIM_DURATION;

        paint.setColor(Color.WHITE);
        paint.setAlpha((int) (255 * 0.2f * mAlphaInt.getInterpolation(pg)));

        int len = (int) (mMinLen + (mMaxLen - mMinLen) * mLengthInt.getInterpolation(pg));
        int top = (int) (-mBounds.height() / 2 + (mBounds.height() * 2) * mYInt.getInterpolation(pg));
        int left = (int) (mBounds.left - mXShift * mYInt.getInterpolation(pg));

        if (mBounds.width() > 1) {
            paint.setStrokeCap(Paint.Cap.ROUND);
        }

        if (mXShift == 0) {
            canvas.drawLine(mBounds.left, top, mBounds.right, top + len, paint);
        } else {
            canvas.save();
            canvas.rotate(angle, top + mBounds.width() / 2, top);
            canvas.drawLine(left, top, left + mBounds.width(), top + len, paint);
            canvas.restore();
        }
    }
}
