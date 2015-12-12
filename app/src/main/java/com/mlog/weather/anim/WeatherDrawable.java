package com.mlog.weather.anim;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 天气动画基类
 *
 * @author CJL
 * @since 2015-09-11
 */
public abstract class WeatherDrawable extends Drawable {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private ArrayList<IWeatherItem> mWeatherItems = new ArrayList<>();
    private LinkedList<IWeatherItem> mWeatherRandomItems = new LinkedList<>();

    private ArrayList<IWeatherRandomItem> mRandomItems = new ArrayList<>();

    protected boolean mIsRunning = false;

    /**
     * 启动动画
     * <p/>
     * 除非调用stopAnimation，否则不会停止
     */
    public void startAnimation() {
        if (mIsRunning || (mWeatherItems.size() == 0 && mRandomItems.size() == 0)) {
            return;
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                invalidateSelf();
                mHandler.post(this);
            }
        });

        final long time = SystemClock.elapsedRealtime();
        for (IWeatherItem wi : mWeatherItems) {
            wi.start(time);
        }

        for (final IWeatherRandomItem wri : mRandomItems) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (!mIsRunning) {
                        return;
                    }

                    IWeatherItem iwi = wri.getRandomWeatherItem();
                    mWeatherRandomItems.add(iwi);
                    iwi.start(SystemClock.elapsedRealtime());
                    mHandler.postDelayed(this, wri.getInterval());
                }
            });
        }

        final int allocateDelay = 3000;
        // 每隔一定时间清理已经无效的随机天气元素
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!mIsRunning) {
                    return;
                }
                for (Iterator<IWeatherItem> it = mWeatherRandomItems.iterator(); it.hasNext(); ) {
                    if (it.next().getStatus() == IWeatherItem.STATUS_NOT_START) {
                        it.remove();
                    }
                }
                mHandler.postDelayed(this, allocateDelay);
            }
        }, allocateDelay);
        mIsRunning = true;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        stopAnimation();
        mWeatherItems.clear();
        mRandomItems.clear();
        addWeatherItem(mWeatherItems, getBounds());
        addRandomItem(mRandomItems, getBounds());
        startAnimation();
    }

    @Override
    public void draw(Canvas canvas) {
        long time = SystemClock.elapsedRealtime();
        for (int i = 0, size = mWeatherItems.size(); i < size; i++) {
            IWeatherItem wi = mWeatherItems.get(i);
            wi.onDraw(canvas, mPaint, time);
        }
        for (IWeatherItem wi : mWeatherRandomItems) {
            if (wi.getStatus() == IWeatherItem.STATUS_RUNNING) {
                wi.onDraw(canvas, mPaint, time);
            }
        }
    }

    public void stopAnimation() {
        mHandler.removeCallbacksAndMessages(null);
        for (IWeatherItem wi : mWeatherItems) {
            wi.stop();
        }
        mIsRunning = false;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    protected Handler getHandler() {
        return mHandler;
    }

    protected List<IWeatherItem> getWeatherItems() {
        return mWeatherItems;
    }

    /**
     * 添加天气动态组件
     *
     * @param weatherItems 组件集合
     * @param rect         Drawable Bounds
     */
    protected void addWeatherItem(List<IWeatherItem> weatherItems, Rect rect) {

    }

    /**
     * 添加自动随机生成天气元素
     *
     * @param randomItems 自动生成类
     * @param rect        Drawable Bounds
     */
    protected void addRandomItem(List<IWeatherRandomItem> randomItems, Rect rect) {
    }

}
