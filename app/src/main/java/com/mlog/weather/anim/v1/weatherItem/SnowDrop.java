package com.mlog.weather.anim.v1.weatherItem;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.animation.LinearInterpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 雪花除了下落外也要自转
 * Created by dongqi on 2015/9/13.
 */
public class SnowDrop extends SimpleWeatherItem {
    //动画持续时间 ms
    static final int ANIM_DURATION = 2000;
    // 透明度100中间点
    static final int ALPHA_CENTER = 1700;
    private Drawable mSnow;

    public SnowDrop(Drawable drawable) {
        mSnow = drawable;
        mInterpolator = new LinearInterpolator();
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }
        int t = (int) (time - getStartTime());

        if (t < ANIM_DURATION) {
            int alpha;
            if (t < ALPHA_CENTER) {
                alpha = 255;
            } else {
                alpha = (int) (255f - 255f * (t - ALPHA_CENTER) / (ANIM_DURATION - ALPHA_CENTER));
            }
            mSnow.setAlpha(alpha);
            float progress = mInterpolator.getInterpolation((float) t / ANIM_DURATION);
            float x = mBounds.centerX();
            float y = mBounds.top + mBounds.height() * progress;

            int hw = mSnow.getIntrinsicWidth() / 2;
            int hh = mSnow.getIntrinsicHeight() / 2;
            mSnow.setBounds((int) x - hw, (int) y - hh, (int) x + hw, (int) y + hh);
            canvas.save();
            canvas.rotate(360 * progress, x, y);
            mSnow.draw(canvas);
            canvas.restore();
        } else {
            stop();
        }
    }
}
