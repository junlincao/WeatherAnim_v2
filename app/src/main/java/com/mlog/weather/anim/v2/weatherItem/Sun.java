package com.mlog.weather.anim.v2.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.Interpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

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
    static final int LIGHT2_ANIM_DURATION = 4000;

    final Interpolator lightAlphaInterpolator = new Interpolator() {
        final float ALPHA_DURATION = 2000f;

        @Override
        public float getInterpolation(float input) {
            final float pg = ALPHA_DURATION / LIGHT_ANIM_DURATION;
            if (input < pg) {
                return input / pg;
            } else if (input < pg * 2) {
                return 1;
            } else if (input < pg * 3) {
                return (pg * 3 - input) / pg;
            }
            return 0;
        }
    };
    final Interpolator light2AlphaInterpolator = new Interpolator() {
        final float ALPHA_DURATION = 1000f;

        @Override
        public float getInterpolation(float input) {
            final float pg = ALPHA_DURATION / LIGHT2_ANIM_DURATION;
            if (input < pg) {
                return input / pg;
            } else if (input < pg * 2) {
                return (pg * 2 - input) / pg;
            }
            return 0;
        }
    };
    final Interpolator light2ScaleInterpolator = new Interpolator() {
        final float SCALE_DURATION = 2000f;
        private Interpolator mAccDecInt = PathInterpolatorCompat.create(0.52f, 0.58f, 0.06f, 0.97f);

        @Override
        public float getInterpolation(float input) {
            final float pg = SCALE_DURATION / LIGHT2_ANIM_DURATION;
            if (input < pg) {
                return 0.6f + mAccDecInt.getInterpolation(input / pg) * 0.4f;
            }
            return 0;
        }
    };

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

        // 默认圈圈
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

        // 显示波纹动画
        long startTime = getStartTime();
        int t = (int) ((time - startTime) % LIGHT_ANIM_DURATION);

        int alpha = (int) (255 * lightAlphaInterpolator.getInterpolation(t * 1f / LIGHT_ANIM_DURATION));
        if (alpha > 0) {
            light.setAlpha(alpha);
            light.draw(canvas);
        }

        t = (int) ((time - startTime) % LIGHT2_ANIM_DURATION);
        float scale = light2ScaleInterpolator.getInterpolation(t * 1f / LIGHT2_ANIM_DURATION);
        if (scale != 0) {
            int hw = (int) (width * scale / 2);
            light2.setBounds(centerX - hw, centerY - hw, centerX + hw, centerY + hw);

            int light2Alpha = (int) (255 * light2AlphaInterpolator.getInterpolation(t * 1f / LIGHT2_ANIM_DURATION));
            light2.setAlpha(light2Alpha);
            light2.draw(canvas);
        }
    }
}
