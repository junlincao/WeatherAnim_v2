package com.mlog.weather.anim.v2.weatherItem;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.Interpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

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
    private float moveDistance;

    private Interpolator mInterpolator = new Interpolator() {
        Interpolator accDecInt = PathInterpolatorCompat.create(0.5f, 0, 0.5f, 1);
        final float pausePg = (ANIM_DURATION / 4f - MOVE_DURATION / 2) / ANIM_DURATION;
        final float movePg = MOVE_DURATION * 1F / ANIM_DURATION;

        @Override
        public float getInterpolation(float input) {
            if (input < pausePg) {
                return 0;
            } else if (input < movePg + pausePg) {
                return accDecInt.getInterpolation((input - pausePg) / movePg);
            } else if (input < movePg + pausePg * 3) {
                return 1;
            } else if (input < 1 - pausePg) {
                return 1 - accDecInt.getInterpolation((input - movePg - pausePg * 3) / movePg);
            }
            return 0;
        }
    };

    public Moon(Drawable moonDrawable) {
        mDrawable = moonDrawable;
    }

    public void setMoveDistance(float distance) {
        moveDistance = distance;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }

        if (moveDistance == 0) {
            mDrawable.setBounds(mBounds);
            mDrawable.draw(canvas);
            return;
        }

        final int t = ((int) (time - getStartTime())) % ANIM_DURATION;
        final float deltaY = moveDistance * mInterpolator.getInterpolation(t * 1f / ANIM_DURATION);

        final int left = mBounds.centerX() - mBounds.width() / 2;
        final int top = (int) (mBounds.centerY() - mBounds.height() / 2 - moveDistance / 2 + deltaY);
        mDrawable.setBounds(left, top, left + mBounds.width(), top + mBounds.height());
        mDrawable.draw(canvas);
    }
}
