package com.mlog.weather.anim.v2.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 星星
 *
 * @author CJL
 * @since 2015-09-18
 */
public class Star extends SimpleWeatherItem {
    static final int ANIM_DURATION = 4000;

    Interpolator mShowInterpolator = new DecelerateInterpolator(2);
    Interpolator mDismissInterpolator = new AccelerateInterpolator(2);

    public Star() {
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        int t = (int) (time - getStartTime());

        if (t > ANIM_DURATION) {
            stop();
            return;
        }

        int alpha;
        if (t < ANIM_DURATION / 2) {
            alpha = (int) (255 * mShowInterpolator.getInterpolation(t * 2f / ANIM_DURATION));
        } else {
            alpha = (int) (255 - 255 * mDismissInterpolator.getInterpolation((t * 2f - ANIM_DURATION) / ANIM_DURATION));
        }
        paint.setColor(Color.argb(alpha, 255, 255, 255));
        canvas.drawRect(mBounds, paint);
    }
}
