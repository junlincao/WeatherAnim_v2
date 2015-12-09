package com.mlog.weather.anim.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
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

    static final int ANIM_DURATION = 8000;

    static final int SCALE_CENTER = 2500;

    static final int ALPHA_DURATION = 2000;

    final Interpolator scaleI1 = new AccelerateInterpolator(3);
    final Interpolator scaleI2 = new DecelerateInterpolator(3);


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
    public void onDraw(Canvas canvas, Paint paint, long time) {
        int centerX = mBounds.centerX();
        int centerY = mBounds.centerY();
        int width = mBounds.width();

        //默认圈圈
        paint.setColor(defColor);
        paint.setAlpha(255);
        canvas.drawCircle(centerX, centerY, 75f / width, paint);
        paint.setAlpha(127);
        canvas.drawCircle(centerX, centerY, 78.75f / width, paint);
        canvas.drawCircle(centerX, centerY, 82.75f / width, paint);
        paint.setAlpha(51);
        canvas.drawCircle(centerX, centerY, 90f / width, paint);

        if (!showWave || light == null || light2 == null) {
            return;
        }

        //显示波纹动画
        long startTime = getStartTime();


    }
}
