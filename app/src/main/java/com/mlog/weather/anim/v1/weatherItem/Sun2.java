package com.mlog.weather.anim.v1.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 太阳
 *
 * @author CJL
 * @since 2015-09-15
 */
public class Sun2 extends SimpleWeatherItem {
    // 扩散波形持续时间
    static final int WAVE_DURATION = 4000;
    // 扩散波形
    static final int WAVE_DELTA = 5000;
    // 第2个扩散波形延迟
    static final int WAVE_DELAY = 500;

    // 第一张图片旋转速度 度每毫秒
    static final float SPEED_P1 = -360f / 60000;
    // 第一张图片旋转速度 度每毫秒
    static final float SPEED_P2 = 360f / 30000;

    private Drawable mDrawable;

    private float mSp1 = SPEED_P1;
    private float mSp2 = SPEED_P2;


    private boolean mShowWave = true;

    public Sun2(Drawable d) {
        this.mDrawable = d;

        mInterpolator = new AccelerateDecelerateInterpolator();
    }

    /**
     * 设置旋转速度 负值代表逆时针转动, 为0则不显示
     *
     * @param sp1 第一张图片旋转速度
     * @param sp2 第二张图片旋转速度
     */
    public void setRoateSpeed(float sp1, float sp2) {
        this.mSp1 = sp1;
        this.mSp2 = sp2;
    }

    /**
     * 设置是否显示波纹
     */
    public void setShowWave(boolean showWave) {
        mShowWave = showWave;
    }

    Rect mRect1 = new Rect();
    Rect mRect2 = new Rect();

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        float scale = (130f / 250) * mBounds.width() / mDrawable.getIntrinsicWidth();

        final int centerX = mBounds.centerX();
        final int centerY = mBounds.centerY();

        int hDW = (int) (mDrawable.getIntrinsicWidth() * scale * 0.5f);
        int hDH = (int) (mDrawable.getIntrinsicHeight() * scale * 0.5f);
        mRect1.set(centerX - hDW, centerY - hDH, centerX + hDW, centerY + hDH);
        hDW *= 1.1f;
        hDH *= 1.1f;
        mRect2.set(centerX - hDW, centerY - hDH, centerX + hDW, centerY + hDH);

        centerCircleR = 46f / 250 * mBounds.width();
        outerCircleR = 58f / 250 * mBounds.width();
        waveCircleR = 112.5f / 250 * mBounds.width();
    }

    private float centerCircleR;
    private float outerCircleR;
    private float waveCircleR;

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }

        int t = (int) (time - getStartTime());

        float centerX = mBounds.centerX();
        float centerY = mBounds.centerY();

        if (mSp1 != 0) {
            canvas.save();
            canvas.rotate(t * mSp1, centerX, centerY);
            mDrawable.setBounds(mRect1);
            mDrawable.setAlpha(255);
            mDrawable.draw(canvas);
            canvas.restore();
        }

        if (mSp2 != 0) {
            canvas.save();
            canvas.rotate(t * mSp2 + 30, centerX, centerY);
            mDrawable.setBounds(mRect2);
            mDrawable.setAlpha(127);
            mDrawable.draw(canvas);
            canvas.restore();
        }

        paint.setColor(Color.WHITE);
        canvas.drawCircle(centerX, centerY, centerCircleR, paint);

        paint.setColor(0x99ffffff);
        canvas.drawCircle(centerX, centerY, outerCircleR, paint);

        if (mShowWave) {
            t %= WAVE_DELTA;
            for (int i = 0; i < 2; i++) {
                t -= WAVE_DELAY * i;
                if (t > 0 && t < WAVE_DURATION) {
                    float progress = mInterpolator.getInterpolation(t * 1f / WAVE_DURATION);
                    int alpha = (int) (255 * (1 - progress));
                    paint.setColor(Color.argb(alpha, 255, 255, 255));

                    canvas.drawCircle(centerX, centerY, waveCircleR * progress, paint);
                }
            }
        }
    }
}
