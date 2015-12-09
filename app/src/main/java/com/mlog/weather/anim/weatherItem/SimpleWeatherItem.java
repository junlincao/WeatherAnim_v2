package com.mlog.weather.anim.weatherItem;

import android.graphics.Rect;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

/**
 * 处理IWeatherItem通用方法
 *
 * @author CJL
 * @since 2015-09-11
 */
public abstract class SimpleWeatherItem implements IWeatherItem {

    protected IWeatherItemCallback mCallBack;

    protected Rect mBounds = new Rect();

    protected Interpolator mInterpolator = new AccelerateInterpolator();

    /**
     * 动画开始时间
     **/
    private long mStartTime = -1;

    private int mStatus = STATUS_NOT_START;


    @Override
    public void setCallback(IWeatherItemCallback callback) {
        mCallBack = callback;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        mBounds.set(left, top, right, bottom);
    }

    @Override
    public void start(long time) {
        mStartTime = time;
        mStatus = STATUS_RUNNING;
    }

    @Override
    public void stop() {
        mStartTime = -1;

        mStatus = STATUS_NOT_START;
        if (mCallBack != null) {
            mCallBack.onAnimFinish(this);
        }
    }

    protected long getStartTime(){
        return mStartTime;
    }

    @Override
    public int getStatus() {
        return mStatus;
    }

    @Override
    public void setInterpolator(Interpolator intercepter) {
        if (intercepter != null) {
            mInterpolator = intercepter;
        }
    }
}
