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
    private float mMoveDistance;

    private Rect mRect1 = new Rect();
    private Rect mRect2 = new Rect();
    private Rect mRect3 = new Rect();
    private Rect mRect4 = new Rect();

    private Matrix mRevertMatrix = new Matrix();


    private Interpolator mInterpolator = new Interpolator() {
        Interpolator accDecInt = PathInterpolatorCompat.create(0.5f, 0, 0.5f, 1);
        final float pausePg = (ANIM_DURATION / 4f - MOVE_DURATION / 2) / ANIM_DURATION;
        final float movePg = MOVE_DURATION * 1F / ANIM_DURATION;

        @Override
        public float getInterpolation(float input) {
            if (input < pausePg) {
                return 0;
            } else if (input < movePg + pausePg) {
                return accDecInt.getInterpolation((input - pausePg) / movePg);
            } else if (input < movePg + pausePg * 3) {
                return 1;
            } else if (input < 1 - pausePg) {
                return 1 - accDecInt.getInterpolation((input - movePg - pausePg * 3) / movePg);
            }
            return 0;
        }
    };

    public Cloud(Drawable cloud1, Drawable cloud2) {
        mCloud1 = cloud1;
        mCloud2 = cloud2;

        setCloudColor(Color.WHITE);
    }

    public void setCloudColor(int color) {
        mCloud1.setColorFilter(color, PorterDuff.Mode.SRC_IN);
        mCloud2.setColorFilter(color, PorterDuff.Mode.SRC_IN);
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
        int defW1 = mCloud1.getIntrinsicWidth();
        int defH1 = mCloud1.getIntrinsicHeight();
        int defW2 = mCloud2.getIntrinsicWidth();
        int defH2 = mCloud2.getIntrinsicHeight();

        float scale = 217f * mBounds.width() / 520 / defW2;
        mRect1.set(mBounds.left, (int) (mBounds.bottom - defH2 * scale), mBounds.left + (int) (defW2 * scale), mBounds.bottom);

        scale = 274f * mBounds.width() / 520 / defW1;
        int l2 = mBounds.left + (int) (172f * mBounds.width() / 520);
        mRect2.set(l2, mBounds.top, (int) (l2 + scale * defW1), mBounds.bottom);

        scale = 196f * mBounds.width() / 520 / defW1;
        mRect3.set((int) (mBounds.right - scale * defW1), (int) (mBounds.bottom - scale * defH1), mBounds.right, mBounds.bottom);
    }

    private void setMiddleBounds() {
        mRect1.set(mBounds.left, mBounds.top, mBounds.left + (int) (385f * mBounds.width() / 547), mBounds.bottom);
        mRect2.set(mBounds.left + (int) (156f * mBounds.width() / 547), (int) (mBounds.bottom - 75f * mBounds.height() / 133), mBounds.right, mBounds.bottom);
    }

    private void setBottomBounds() {
        int defW1 = mCloud1.getIntrinsicWidth();
        int defH1 = mCloud1.getIntrinsicHeight();
        int defW2 = mCloud2.getIntrinsicWidth();
        int defH2 = mCloud2.getIntrinsicHeight();

        float scale = 150f * mBounds.width() / 673 / defW2;
        mRect1.set(0, (int) (mBounds.bottom - defH2 * scale), (int) (defW2 * scale), mBounds.bottom);

        scale = 240f * mBounds.width() / 673 / defW1;
        int l2 = (int) (76f * mBounds.width() / 673);
        mRect2.set(l2, (int) (mBounds.bottom - defH1 * scale), (int) (l2 + defW1 * scale), mBounds.bottom);

        scale = 356f * mBounds.width() / 673 / defW1;
        int l3 = (int) (128f * mBounds.width() / 673);
        mRect3.set(l3, (int) (mBounds.bottom - defH1 * scale), (int) (l3 + defW1 * scale), mBounds.bottom);

        scale = 308f * mBounds.width() / 673 / defW2;
        int l4 = (int) (368f * mBounds.width() / 673);
        mRect4.set(l4, (int) (mBounds.bottom - defH2 * scale), (int) (l4 + defW2 * scale), mBounds.bottom);
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
        int t = (int) ((time - getStartTime() + MOVE_DELAY * 6) % ANIM_DURATION);

        int moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t * 1f / ANIM_DURATION));

        canvas.save();
        mRevertMatrix.reset();
        mRevertMatrix.postScale(-1, 1);
        mRevertMatrix.postTranslate(mRect1.width() + mBounds.left * 2 + moveX * 2, 0);
        canvas.concat(mRevertMatrix);
        mCloud2.setAlpha(229);
        mCloud2.setBounds(mRect1.left + moveX, mRect1.top, mRect1.right + moveX, mRect1.bottom);
        mCloud2.draw(canvas);
        canvas.restore();

        t = (int) ((time - getStartTime() + MOVE_DELAY * 5) % ANIM_DURATION);
        moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t * 1f / ANIM_DURATION));
        mCloud1.setBounds(mRect2.left + moveX, mRect2.top, mRect2.right + moveX, mRect2.bottom);
        mCloud1.setAlpha(135);
        mCloud1.draw(canvas);

        t = (int) ((time - getStartTime() + MOVE_DELAY * 4) % ANIM_DURATION);
        moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t * 1f / ANIM_DURATION));
        canvas.save();
        mRevertMatrix.reset();
        mRevertMatrix.postScale(-1, 1);
        mRevertMatrix.postTranslate(mRect3.width() + mRect3.left * 2 + moveX * 2, 0);
        canvas.concat(mRevertMatrix);
        mCloud1.setBounds(mRect3.left + moveX, mRect3.top, mRect3.right + moveX, mRect3.bottom);
        mCloud1.setAlpha(255);
        mCloud1.draw(canvas);
        canvas.restore();
    }

    private void drawTypeMiddle(Canvas canvas, Paint paint, long time) {
        int t = (int) ((time + MOVE_DELAY * 3 - getStartTime()) % ANIM_DURATION);
        int moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t * 1f / ANIM_DURATION));

        mCloud1.setBounds(mRect1.left + moveX, mRect1.top, mRect1.right + moveX, mRect1.bottom);
        mCloud1.setAlpha(153);
        mCloud1.draw(canvas);


        t = (int) ((time + MOVE_DELAY * 2 - getStartTime()) % ANIM_DURATION);
        moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t * 1f / ANIM_DURATION));

        mCloud2.setBounds(mRect2.left + moveX, mRect2.top, mRect2.right + moveX, mRect2.bottom);
        mCloud2.setAlpha(229);
        mCloud2.draw(canvas);
    }

    private void drawTypeBottom(Canvas canvas, Paint paint, long time) {
        int t = (int) ((time - getStartTime()) % ANIM_DURATION);

        int moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t * 1f / ANIM_DURATION));

        canvas.save();
        mRevertMatrix.reset();
        mRevertMatrix.postScale(-1, 1);
        mRevertMatrix.postTranslate(mRect1.width() + moveX * 2, 0);
        canvas.concat(mRevertMatrix);
        mCloud2.setAlpha(255);
        mCloud2.setBounds(mRect1.left + moveX, mRect1.top, mRect1.right + moveX, mRect1.bottom);
        mCloud2.draw(canvas);
        canvas.restore();

        t = (t + MOVE_DELAY) % ANIM_DURATION;
        moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t * 1f / ANIM_DURATION));
        mCloud1.setBounds(mRect2.left + moveX, mRect2.top, mRect2.right + moveX, mRect2.bottom);
        mCloud1.setAlpha(204);
        mCloud1.draw(canvas);

        t = (t + MOVE_DELAY) % ANIM_DURATION;
        moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t * 1f / ANIM_DURATION));
        mCloud1.setBounds(mRect3.left + moveX, mRect3.top, mRect3.right + moveX, mRect3.bottom);
        mCloud1.setAlpha(153);
        mCloud1.draw(canvas);

        t = (t + MOVE_DELAY) % ANIM_DURATION;
        moveX = (int) (mMoveDistance * mInterpolator.getInterpolation(t * 1f / ANIM_DURATION));
        mCloud2.setBounds(mRect4.left + moveX, mRect4.top, mRect4.right + moveX, mRect4.bottom);
        mCloud2.setAlpha(229);
        mCloud2.draw(canvas);
    }
}
