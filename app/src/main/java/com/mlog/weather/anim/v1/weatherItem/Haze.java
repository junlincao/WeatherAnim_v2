package com.mlog.weather.anim.v1.weatherItem;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 霾
 *
 * @author CJL
 * @since 2015-09-20
 */
public class Haze extends SimpleWeatherItem {

    final int ANIM_DURATION = 5000;
    final int ANIM_DELTA = 2000;
    final int SPEED_CENTER = 2000;

    Drawable mBg;

    int mRectBottom;
    int mRectMaxHeight;
    int mRectScHeight;

    public Haze(Drawable bg) {
        this.mBg = bg;
        mInterpolator = new DecelerateInterpolator(0.8f);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        int bgH = mBg.getIntrinsicHeight() * mBounds.width() / mBg.getIntrinsicWidth();
        mBg.setBounds(left, top + mBounds.height() - bgH, right, bottom);

        mRectBottom = mBounds.bottom - (int) (15f / 250 * mBounds.width());
        mRectMaxHeight = (int) (190f / 250 * mBounds.width());

        mRectScHeight = mRectBottom - mBounds.centerY();
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

            int h;
            if (layerTime < SPEED_CENTER) {
                h = (int) (mRectScHeight * (float) layerTime / SPEED_CENTER);
            } else {
                h = (int) (mRectScHeight + (mRectMaxHeight - mRectScHeight) *
                        mInterpolator.getInterpolation((float) (layerTime - SPEED_CENTER) / (ANIM_DURATION - SPEED_CENTER)));
            }

            int alpha = (int) (255 * 0.3f * (1 - 1f * layerTime / ANIM_DURATION));
            paint.setColor(Color.argb(alpha, 255, 255, 255));
            canvas.drawRect(mBounds.left, mRectBottom - h, mBounds.right, mRectBottom, paint);
        }
    }
}
