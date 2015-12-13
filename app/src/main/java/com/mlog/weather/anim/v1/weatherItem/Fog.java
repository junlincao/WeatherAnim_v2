package com.mlog.weather.anim.v1.weatherItem;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 雾
 *
 * @author CJL
 * @since 2015-09-20
 */
public class Fog extends SimpleWeatherItem {

    final int ANIM_DURATION = 5000;
    final int ANIM_DELTA = 2000;
    final int ALPHA_CENTER = 1000;

    Drawable mBg;
    Drawable mFog;

    int mFogMinHeight;

    public Fog(Drawable bg, Drawable fog) {
        this.mBg = bg;
        this.mFog = fog;
        mInterpolator = new DecelerateInterpolator(0.7f);
    }

    Interpolator mShowInterpolator = new AccelerateInterpolator(2);

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        int bgH = mBg.getIntrinsicHeight() * mBounds.width() / mBg.getIntrinsicWidth();
        mBg.setBounds(left, top + mBounds.height() - bgH, right, bottom);

        mFogMinHeight = (int) (70f / 250 * mBounds.width());

    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }

        mBg.draw(canvas);

        int t = (int) (time - getStartTime());

        final int maxLayerCount = ANIM_DURATION / ANIM_DELTA;
        int newLayerTime = t % ANIM_DELTA;

        for (int i = 0; i <= maxLayerCount; i++) {
            int layerTime = newLayerTime + i * ANIM_DELTA;
            if (layerTime <= 0) {
                return;
            }

            int deltaH = 0;
            int alpha;
            if (layerTime > ALPHA_CENTER) {
                float progress = mInterpolator.getInterpolation((layerTime - ALPHA_CENTER) * 1f / (ANIM_DURATION - ALPHA_CENTER));
                alpha = (int) (255 - 255 * progress);
                deltaH = (int) ((mBounds.height() - mFogMinHeight) * progress);
            } else {
                alpha = (int) (255f * mShowInterpolator.getInterpolation(1f * layerTime / ALPHA_CENTER));
            }

            mFog.setAlpha(alpha);
            mFog.setBounds(mBounds.left, mBounds.height() - mFogMinHeight - deltaH, mBounds.right, mBounds.bottom);
            mFog.draw(canvas);
        }
    }
}
