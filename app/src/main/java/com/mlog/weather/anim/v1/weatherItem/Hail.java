package com.mlog.weather.anim.v1.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 冰雹
 *
 * @author CJL
 * @since 2015-09-11
 */
public class Hail extends SimpleWeatherItem {
    // 下落耗时
    private static final int DROP_TIME = 800;
    // 落地后飞溅耗时
    private static final int SPLIT_TIME = 400;
    //下落步骤中完全透明
    private static final float FULL_ALPHA_PROGRESS = 0.7f;

    private Interpolator mSplitInterpolator = new DecelerateInterpolator();


    public Hail() {
        mInterpolator = new AccelerateInterpolator();
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }

        int t = (int) (time - getStartTime());

        if (t < DROP_TIME) {
            drawDrop(canvas, paint, t * 1f / DROP_TIME);
        } else if (t < DROP_TIME + SPLIT_TIME) {
            drawSplit(canvas, paint, (t - DROP_TIME) * 1f / SPLIT_TIME);
        } else {
            stop();
        }
    }


    private void drawDrop(Canvas canvas, Paint paint, float progress) {
        int alpha = progress >= FULL_ALPHA_PROGRESS ? 255 : (int) (progress / FULL_ALPHA_PROGRESS * 255);
        paint.setColor(Color.argb(alpha, 255, 255, 255));
        float y = mBounds.top + mBounds.height() * mInterpolator.getInterpolation(progress);
        float x = mBounds.centerX();

        canvas.drawCircle(x, y, mBounds.width() / 8, paint);
    }

    private void drawSplit(Canvas canvas, Paint paint, float progress) {
        float ip = mSplitInterpolator.getInterpolation(progress);
        int alpha = (int) (255 - 255 * ip);
        int w = mBounds.width();
        float bottom = mBounds.bottom - w / 10f;

        paint.setColor(Color.argb(alpha, 255, 255, 255));

        canvas.drawCircle(mBounds.centerX() - w / 3.5f * ip, bottom - w / 3f * ip, w / 20f, paint);
        canvas.drawCircle(mBounds.centerX() + w / 4f * ip, bottom - w / 2.8f * ip, w / 17f, paint);
        canvas.drawCircle(mBounds.centerX(), bottom - w / 2f * ip, w / 14f, paint);
    }

}
