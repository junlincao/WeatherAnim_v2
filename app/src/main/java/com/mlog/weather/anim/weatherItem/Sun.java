package com.mlog.weather.anim.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * 太阳
 *
 * @author CJL
 * @since 2015-09-15
 */
public class Sun extends SimpleWeatherItem {
    private Drawable light;
    private Drawable light2;

    static final int LIGHT_ANIM_DURATION = 8000;
    static final int LIGHT_ALPHA_DURATION = 2000;

    static final int LIGHT2_ALPHA_DURATION = 1000;
    static final int LIGHT2_SCALE_DURATION = 2000;

    final Interpolator mInterpolator = new AccelerateDecelerateInterpolator();


    /**
     * 是否显示波纹
     **/
    private boolean showWave;

    private int defColor = Color.WHITE;

    public Sun() {
        showWave = false;
        defColor = 0xffe3dbac;
    }

    public Sun(Drawable light, Drawable light2) {
        showWave = true;
        this.light = light;
        this.light2 = light2;
        defColor = Color.WHITE;
    }

    public void setShowWave(boolean showWave) {
        this.showWave = showWave;
    }

    public void setSunColor(int color) {
        this.defColor = color;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        if (light != null && light2 != null) {
            light.setBounds(left, top, right, bottom);
        }
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        int centerX = mBounds.centerX();
        int centerY = mBounds.centerY();
        int width = mBounds.width();

        //默认圈圈
        paint.setColor(defColor);
        paint.setAlpha(255);
        canvas.drawCircle(centerX, centerY, 51f * width / 165, paint);
        paint.setAlpha(127);
        canvas.drawCircle(centerX, centerY, 53f * width / 165, paint);
        canvas.drawCircle(centerX, centerY, 55.5f * width / 165, paint);
        paint.setAlpha(51);
        canvas.drawCircle(centerX, centerY, 61.5f * width / 165, paint);

        if (!showWave || light == null || light2 == null) {
            return;
        }

        //显示波纹动画
        long startTime = getStartTime();
        int t = (int) ((time - startTime) % LIGHT_ANIM_DURATION);

        if (t < LIGHT_ALPHA_DURATION * 2) {
            if (t < LIGHT_ALPHA_DURATION) {
                light.setAlpha((int) (t * 255f / LIGHT_ALPHA_DURATION));
                light.draw(canvas);
            } else {
                light.setAlpha(255);
            }
        } else {
            light.setAlpha(0);
        }
        light.draw(canvas);

        t = t % (LIGHT_ANIM_DURATION / 2);
        if (t < LIGHT2_SCALE_DURATION) {
            float scale = 0.6f + mInterpolator.getInterpolation(t * 1f / LIGHT2_SCALE_DURATION) * 0.4f;
            int hw = (int) (width * scale / 2);
            light2.setBounds(centerX - hw, centerY - hw, centerX + hw, centerY + hw);
        } else {
            return;
        }
        if (t < LIGHT2_ALPHA_DURATION) {
            light2.setAlpha((int) (255f * t / LIGHT2_ALPHA_DURATION));
        } else {
            light2.setAlpha((int) (255f * (LIGHT2_ALPHA_DURATION * 2 - t) / LIGHT2_ALPHA_DURATION));
        }
        light2.draw(canvas);
    }
}
