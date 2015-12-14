package com.mlog.weather.anim.v2.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.Interpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 云
 *
 * @author CJL
 * @since 2015-12-11
 */
public class Cloud extends SimpleWeatherItem {
    @IntDef({TYPE_TOP, TYPE_MIDDLE, TYPE_BOTTOM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CloudType {
    }

    public static final int TYPE_TOP = 0;
    public static final int TYPE_MIDDLE = 1;
    public static final int TYPE_BOTTOM = 2;

    static final int ANIM_DURATION = 10000;
    static final int MOVE_DURATION = 3000;
    static final int MOVE_DELAY = 200;

    @CloudType
    private int mCloudType = TYPE_BOTTOM;
    private Drawable mCloud1;
    private Drawable mCloud2;
    private Drawable mCloud3;
    private Drawable mCloud4;
    private float mMoveDistance;

    private Rect mRect1 = new Rect();
    private Rect mRect2 = new Rect();
    private Rect mRect3 = new Rect();
    private Rect mRect4 = new Rect();

    private Matrix mRevertMatrix = new Matrix();


    private CloudInt mInterpolator = new CloudInt();


    private class CloudInt implements Interpolator {

        Interpolator accDecInt = PathInterpolatorCompat.create(0.5f, 0, 0.5f, 1);
        float pauseTime = 0;
        float firstMoveStart = 0;

        /**
         * @param firstMoveStart 开始移动时间点
         * @param pauseTime      移动过去之后暂停时间长
         */
        void setTime(int firstMoveStart, int pauseTime) {
            this.pauseTime = pauseTime;
            this.firstMoveStart = firstMoveStart;
        }

        /**
         * @param input 当前动画进行时间  0~ANIM_DURATION
         * @return 移动百分比，最右边为1，最左边为0
         */
        @Override
        public float getInterpolation(float input) {
            if (input < firstMoveStart / ANIM_DURATION) {
                return 0;
            } else if (input < firstMoveStart + MOVE_DURATION) {
                return accDecInt.getInterpolation((input - firstMoveStart) / MOVE_DURATION);
            } else if (input < firstMoveStart + MOVE_DURATION + pauseTime) {
                return 1;
            } else if (input < firstMoveStart + MOVE_DURATION * 2 + pauseTime) {
                return accDecInt.getInterpolation((firstMoveStart + MOVE_DURATION * 2 + pauseTime - input) / MOVE_DURATION);
            }
            return 0;
        }
    }

    public Cloud(Drawable cloud1, Drawable cloud2, Drawable cloud3, Drawable cloud4) {
        mCloud1 = cloud1;
        mCloud2 = cloud2;
        mCloud3 = cloud3;
        mCloud4 = cloud4;

        setCloudColor(Color.WHITE);
    }

    public void setCloudColor(int color) {
        mCloud1.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        mCloud2.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        mCloud3.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        mCloud4.setColorFilter(color, PorterDuff.Mode.SRC_IN);
    }

    public void setCloudType(@CloudType int cloudType) {
        this.mCloudType = cloudType;
    }

    public void setMoveDistance(float distance) {
        this.mMoveDistance = distance;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        switch (mCloudType) {
            case TYPE_BOTTOM:
                setBottomBounds();
                break;
            case TYPE_MIDDLE:
                setMiddleBounds();
                break;
            case TYPE_TOP:
                setTopBounds();
                break;
        }
    }

    private void setTopBounds() {
        float scale = 61f * mBounds.width() / 137 / mCloud3.getIntrinsicWidth();
        mRect1.set(mBounds.left, (int) (mBounds.bottom - mCloud3.getIntrinsicHeight() * scale), mBounds.left + (int) (mCloud3.getIntrinsicWidth() * scale), mBounds.bottom);

        int l2 = mBounds.left + (int) (45f * mBounds.width() / 137);
        mRect2.set(l2, mBounds.top, (int) (l2 + 81f * mBounds.width() / 137), mBounds.bottom);

        scale = 49f * mBounds.width() / 137 / mCloud2.getIntrinsicWidth();
        mRect3.set((int) (mBounds.right - scale * mCloud2.getIntrinsicWidth()), (int) (mBounds.bottom - scale * mCloud2.getIntrinsicHeight()), mBounds.right, mBounds.bottom);
    }

    private void setMiddleBounds() {
        mRect1.set(mBounds.left, mBounds.top, mBounds.left + (int) (108f * mBounds.width() / 162), mBounds.bottom);
        mRect2.set(mBounds.left + (int) (90f * mBounds.width() / 164), (int) (mBounds.bottom - 20f * mBounds.height() / 38), mBounds.right, mBounds.bottom);
    }

    private void setBottomBounds() {
        float scale = 67f * mBounds.width() / 278 / mCloud1.getIntrinsicWidth();
        mRect1.set(0, (int) (mBounds.bottom - mCloud1.getIntrinsicHeight() * scale), (int) (mCloud1.getIntrinsicWidth() * scale), mBounds.bottom);

        scale = 100f * mBounds.width() / 278 / mCloud2.getIntrinsicWidth();
        int l2 = (int) (76f * mBounds.width() / 673);
        mRect2.set(l2, (int) (mBounds.bottom - mCloud2.getIntrinsicHeight() * scale), (int) (l2 + mCloud2.getIntrinsicWidth() * scale), mBounds.bottom);

        scale = 145f * mBounds.width() / 278 / mCloud1.getIntrinsicWidth();
        int l3 = (int) (128f * mBounds.width() / 673);
        mRect3.set(l3, (int) (mBounds.bottom - mCloud1.getIntrinsicHeight() * scale), (int) (l3 + mCloud1.getIntrinsicWidth() * scale), mBounds.bottom);

        scale = 128f * mBounds.width() / 278 / mCloud3.getIntrinsicWidth();
        int l4 = (int) (151f * mBounds.width() / 278);
        mRect4.set(l4, (int) (mBounds.bottom - mCloud3.getIntrinsicHeight() * scale), (int) (l4 + mCloud3.getIntrinsicWidth() * scale), mBounds.bottom);
    }


    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        switch (mCloudType) {
            case TYPE_BOTTOM:
                drawTypeBottom(canvas, paint, time);
                break;
            case TYPE_MIDDLE:
                drawTypeMiddle(canvas, paint, time);
                break;
            case TYPE_TOP:
                drawTypeTop(canvas, paint, time);
                break;
        }
    }

    private void drawTypeTop(Canvas canvas, Paint paint, long time) {
        int t = (int) ((time - getStartTime()) % ANIM_DURATION);

        mInterpolator.setTime(1200, 1200);
        int moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t));
        canvas.save();
        mRevertMatrix.reset();
        mRevertMatrix.postScale(-1, 1);
        mRevertMatrix.postTranslate(mRect1.width() + mBounds.left * 2 + moveX * 2, 0);
        canvas.concat(mRevertMatrix);
        mCloud3.setAlpha(229);
        mCloud3.setBounds(mRect1.left + moveX, mRect1.top, mRect1.right + moveX, mRect1.bottom);
        mCloud3.draw(canvas);
        canvas.restore();

        mInterpolator.setTime(1000, 1600);
        moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t));
        mCloud1.setBounds(mRect2.left + moveX, mRect2.top, mRect2.right + moveX, mRect2.bottom);
        mCloud1.setAlpha(135);
        mCloud1.draw(canvas);

        mInterpolator.setTime(800, 2000);
        moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t));
        canvas.save();
        mRevertMatrix.reset();
        mRevertMatrix.postScale(-1, 1);
        mRevertMatrix.postTranslate(mRect3.width() + mRect3.left * 2 + moveX * 2, 0);
        canvas.concat(mRevertMatrix);
        mCloud2.setBounds(mRect3.left + moveX, mRect3.top, mRect3.right + moveX, mRect3.bottom);
        mCloud2.setAlpha(255);
        mCloud2.draw(canvas);
        canvas.restore();
    }

    private void drawTypeMiddle(Canvas canvas, Paint paint, long time) {
        int t = (int) ((time - getStartTime()) % ANIM_DURATION);

        mInterpolator.setTime(600, 1600);
        int moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t));
        mCloud2.setBounds(mRect1.left + moveX, mRect1.top, mRect1.right + moveX, mRect1.bottom);
        mCloud2.setAlpha(153);
        mCloud2.draw(canvas);

        mInterpolator.setTime(400, 2000);
        moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t));
        mCloud4.setBounds(mRect2.left + moveX, mRect2.top, mRect2.right + moveX, mRect2.bottom);
        mCloud4.setAlpha(229);
        mCloud4.draw(canvas);
    }

    private void drawTypeBottom(Canvas canvas, Paint paint, long time) {
        int t = (int) ((time - getStartTime()) % ANIM_DURATION);

        mInterpolator.setTime(600, 1400);
        int moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t));
        canvas.save();
        mRevertMatrix.reset();
        mRevertMatrix.postScale(-1, 1);
        mRevertMatrix.postTranslate(mRect1.width() + moveX * 2, 0);
        canvas.concat(mRevertMatrix);
        mCloud4.setAlpha(255);
        mCloud4.setBounds(mRect1.left + moveX, mRect1.top, mRect1.right + moveX, mRect1.bottom);
        mCloud4.draw(canvas);
        canvas.restore();

        mInterpolator.setTime(400, 1800);
        moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t));
        mCloud2.setBounds(mRect2.left + moveX, mRect2.top, mRect2.right + moveX, mRect2.bottom);
        mCloud2.setAlpha(204);
        mCloud2.draw(canvas);

        mInterpolator.setTime(200, 2200);
        moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t));
        mCloud1.setBounds(mRect3.left + moveX, mRect3.top, mRect3.right + moveX, mRect3.bottom);
        mCloud1.setAlpha(153);
        mCloud1.draw(canvas);

        mInterpolator.setTime(0, 2600);
        moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t));
        mCloud3.setBounds(mRect4.left + moveX, mRect4.top, mRect4.right + moveX, mRect4.bottom);
        mCloud3.setAlpha(229);
        mCloud3.draw(canvas);
    }
}
