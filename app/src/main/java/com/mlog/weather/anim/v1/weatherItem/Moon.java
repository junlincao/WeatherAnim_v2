package com.mlog.weather.anim.v1.weatherItem;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import com.mlog.weather.anim.SimpleWeatherItem;
import com.mlog.weather.anim.v1.drawable.SymAccDecInterpolator;

/**
 * 月亮
 *
 * @author CJL
 * @since 2015-09-18
 */
public class Moon extends SimpleWeatherItem {
    // 动画周期
    static final int ANIM_DURATION = 6000;
    // 向上移动和向下移动时间
    static final int MOVE_DURATION = 2000;

    private Drawable mDrawable;
    // 移动最大距离
    float moveDistance;

    int mHalfWidth;
    int mHalfHeight;

    public Moon(Drawable moonDrawable) {
        mDrawable = moonDrawable;
        mInterpolator = new SymAccDecInterpolator(1);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        int w = mBounds.width();
        moveDistance = 9f / 250 * w;

        float scale = 162f * mBounds.width() / (250 * mDrawable.getIntrinsicWidth());
        mHalfWidth = (int) (mDrawable.getIntrinsicWidth() * scale * 0.5f);
        mHalfHeight = (int) (mDrawable.getIntrinsicHeight() * scale * 0.5f);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }
        int t = ((int) (time - getStartTime())) % ANIM_DURATION;
        float stopTime = ANIM_DURATION * 0.5f - MOVE_DURATION;

        int centerY;
        if (t < stopTime) {
            centerY = mBounds.centerY();
        } else if (t < MOVE_DURATION + stopTime) {
            float deltaY = moveDistance * mInterpolator.getInterpolation((t - stopTime) / MOVE_DURATION);
            centerY = (int) (mBounds.centerY() - deltaY);
        } else if (t < MOVE_DURATION + stopTime * 2) {
            centerY = (int) (mBounds.centerY() - moveDistance);
        } else {
            float deltaY = moveDistance * mInterpolator.getInterpolation((t - MOVE_DURATION - stopTime * 2) / MOVE_DURATION);
            centerY = (int) (mBounds.centerY() - moveDistance + deltaY);
        }
        mDrawable.setBounds(mBounds.centerX() - mHalfWidth, centerY - mHalfHeight, mBounds.centerX() + mHalfWidth, centerY + mHalfHeight);
        mDrawable.draw(canvas);
    }
}
