package com.mlog.weather.anim.v1.weatherItem;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 闪电
 *
 * @author CJL
 * @since 2015-09-20
 */
public class Light extends SimpleWeatherItem {
    static final int ANIM_DURATION = 4200;
    static final int LIGHT_DURATION = 450;
    static final int FIRST_LIGHT_START = 800;
    static final int SECOND_LIGHT_START = 1450;
    static final int THIRD_LIGHT_START = 3100;

    Drawable mDrawable;

    public Light(Drawable lightDrawable) {
        mDrawable = lightDrawable;
        mInterpolator = new DecelerateInterpolator(2);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mDrawable.setBounds(left, top, right, bottom);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }
        int t = ((int) (time - getStartTime())) % ANIM_DURATION;

        if ((t >= FIRST_LIGHT_START && t <= FIRST_LIGHT_START + LIGHT_DURATION)
                || (t >= SECOND_LIGHT_START && t <= SECOND_LIGHT_START + LIGHT_DURATION)
                || (t >= THIRD_LIGHT_START && t <= THIRD_LIGHT_START + LIGHT_DURATION)) {
            int tmpTime = t >= THIRD_LIGHT_START ? t - THIRD_LIGHT_START : t >= SECOND_LIGHT_START ? t - SECOND_LIGHT_START : t - FIRST_LIGHT_START;

            int alpha = (int) (255 - 255 * mInterpolator.getInterpolation((float) tmpTime / LIGHT_DURATION));
            mDrawable.setAlpha(alpha);
            mDrawable.draw(canvas);
        }
    }
}
