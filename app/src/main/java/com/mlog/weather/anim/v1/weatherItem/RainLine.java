package com.mlog.weather.anim.v1.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 连线状的雨水
 *
 * @author CJL
 * @since 2015-09-12
 */
public class RainLine extends SimpleWeatherItem {
    // 漂移距离
    private int mXShift = 0;
    // 雨水连线最大长度
    private int mMaxLen;
    // 雨水连线最小长度
    private int mMinLen;

    //动画持续时间
    static final int ANIM_DURATION = 1600;
    // 下降持续时间
    static final int DROP_DURATION = 900;
    // 扩展开始时间
    static final int EXPAND_START = 800;
    // 扩展渐隐开始时间
    static final int EXPAND_ALPHA_START = 1200;

    // 下落总长度
    float dropLen;
    // 倾斜角度
    float angle;

    public RainLine() {
        mInterpolator = new AccelerateInterpolator(0.8f);
    }

    /**
     * 设置X轴位置偏移量
     *
     * @param xShift 偏移量,左侧为正，右侧为负
     */
    public void setXShift(int xShift) {
        this.mXShift = xShift;
    }

    /**
     * 设置雨线最大长度
     *
     * @param minLen 最小长度
     * @param maxLen 最大长度
     */
    public void setLen(int minLen, int maxLen) {
        mMaxLen = maxLen;
        mMinLen = minLen;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        int h = mBounds.height();
        dropLen = (float) Math.sqrt(mXShift * mXShift + h * h);
        angle = (float) (90 - Math.toDegrees(Math.atan2(h, mXShift)));
    }

    Interpolator mExpandInterpolator = new DecelerateInterpolator(0.8f);

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }

        int t = (int) (time - getStartTime());

        int w = mBounds.width();
        paint.setStrokeWidth(w);

        if (t > ANIM_DURATION) {
            stop();
            return;
        }

        if (t < DROP_DURATION) {
            float progressDrop = mInterpolator.getInterpolation(t * 1f / DROP_DURATION);
            float len = t < DROP_DURATION ? mMinLen + (mMaxLen - mMinLen) * progressDrop : mMaxLen;
            int alpha = t < DROP_DURATION ? (int) (255 * progressDrop) : 255;

            float cx = mBounds.centerX() - mXShift * progressDrop;
            float cy = mBounds.top - mMinLen / 2 + mBounds.height() * progressDrop;

            paint.setColor(Color.argb(alpha, 255, 255, 255));
            float hw = mBounds.width() / 2f;
            canvas.save();
            canvas.rotate(angle, cx, cy);
            tmpRect.set(cx - hw, cy - len / 2, cx + hw, cy + len / 2);
            canvas.drawRoundRect(tmpRect, hw, hw, paint);
            canvas.restore();
        }
        if (t > EXPAND_START) {
            float pbx = mBounds.centerX() - mXShift;
            int alpha = t < EXPAND_ALPHA_START ? 255 : (int) (255 - 255f * (t - EXPAND_ALPHA_START) / (ANIM_DURATION - EXPAND_ALPHA_START));
            float len = mMaxLen * mExpandInterpolator.getInterpolation((t - EXPAND_START) * 1f / (ANIM_DURATION - EXPAND_START));
            paint.setColor(Color.argb(alpha, 255, 255, 255));

            tmpRect.set(pbx - len / 2, mBounds.bottom - mBounds.width(), pbx + len / 2, mBounds.bottom);
            canvas.drawRoundRect(tmpRect, mBounds.width() / 2, mBounds.width() / 2, paint);
        }
    }

    RectF tmpRect = new RectF();
}
