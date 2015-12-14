package com.mlog.weather.anim.v2.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.util.Log;
import android.view.animation.Interpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 雨
 * Created by cjl on 2015/12/12.
 */
public class Rain extends SimpleWeatherItem {

    static final int ANIM_DURATION = 1000;

    private static Interpolator mAlphaInt = new Interpolator() {
        final float ALPHA_PG = 0.5f;

        @Override
        public float getInterpolation(float input) {
            if (input < ALPHA_PG) {
                return input / ALPHA_PG;
            }
            return 1;
        }
    };

    private static Interpolator mYInt = PathInterpolatorCompat.create(0.587f, 0, 0.788f, 1);

    private int mMinLen;
    private int mMaxLen;
    private int mXShift;

    public void setXShift(int xShift) {
        this.mXShift = xShift;
    }

    public void setLen(int minLen, int maxLen) {
        this.mMinLen = minLen;
        this.mMaxLen = maxLen;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (time - getStartTime() > ANIM_DURATION) {
            stop();
            return;
        }
        float pg = (time - getStartTime()) * 1f / ANIM_DURATION;

        if (mBounds.width() > 1) {
            paint.setStrokeCap(Paint.Cap.ROUND);
        }
        paint.setColor(Color.WHITE);
        paint.setAlpha((int) (128f * mAlphaInt.getInterpolation(pg)));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mBounds.width());

        float ypg = mYInt.getInterpolation(pg);
        float len = (int) (mMinLen + (mMaxLen - mMinLen) * ypg);
        float ty = (int) (mBounds.top - mMinLen + (mBounds.height() + mMinLen) * ypg);

        canvas.save();
        canvas.clipRect(mBounds.left - mXShift, mBounds.top, mBounds.right, mBounds.bottom);

        if (mXShift == 0) {
            float x = mBounds.centerX();
            canvas.drawLine(x, ty, x, ty + len, paint);
        } else {
            float tx = (int) (mBounds.left - mXShift * ypg);
            float bx = (float) (tx - len * mXShift / Math.sqrt(mXShift * mXShift + mBounds.height() * mBounds.height()));
            float by = (float) (ty + len * mBounds.height() / Math.sqrt(mXShift * mXShift + mBounds.height() * mBounds.height()));

            canvas.drawLine(bx, by, tx, ty, paint);
        }

        canvas.restore();
    }
}
