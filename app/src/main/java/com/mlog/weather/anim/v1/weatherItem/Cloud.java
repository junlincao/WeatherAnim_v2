package com.mlog.weather.anim.v1.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.animation.Interpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

import java.util.ArrayList;

/**
 * 云
 *
 * @author CJL
 * @since 2015-09-12
 */
public class Cloud extends SimpleWeatherItem {

    // 移动最大距离
    float moveDistance;

    // 向上或者向下移动时间
    static final int MOVE_DURATION = 2000;
    // 动画单周期总时间
    static final int ANIM_DURATION = 5000;

    // 各个圆圈信息
    ArrayList<CircleMsg> mCircleMsg = new ArrayList<>(7);

    public Cloud() {
        mInterpolator = new MCloudInterpolator(2f);
    }

    // 云加速计算器   先加速，再减速，按时间轴对称
    private static class MCloudInterpolator implements Interpolator {
        float mFactor;

        MCloudInterpolator(float factor) {
            this.mFactor = factor * 2;
        }

        @Override
        public float getInterpolation(float input) {
            float result;
            if (input <= 0.5f) {
                result = (float) Math.pow(input / 0.5, mFactor) / 2;
            } else {
                input = 1 - input;
                result = 1 - (float) Math.pow(input / 0.5, mFactor) / 2;
            }
            return result;
        }
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        mCircleMsg.clear();

        // 设置各个圆圈半径与位置  按宽度适应
        int w = mBounds.width();
        moveDistance = 9f / 250 * w;

        //1
        CircleMsg cm = new CircleMsg();
        cm.x = left + 54f / 250 * w;
        cm.y = top + 105f / 250 * w;
        cm.r = 25f / 250 * w;
        cm.color = Color.WHITE;
        mCircleMsg.add(cm);

        //2
        cm = new CircleMsg();
        cm.x = left + 204f / 250 * w;
        cm.y = top + 115f / 250 * w;
        cm.r = 17.5f / 250 * w;
        cm.color = Color.WHITE;
        mCircleMsg.add(cm);

        //3
        cm = new CircleMsg();
        cm.x = left + 178f / 250 * w;
        cm.y = top + 98f / 250 * w;
        cm.r = 35f / 250 * w;
        cm.color = 0xccffffff;
        mCircleMsg.add(cm);

        //4
        cm = new CircleMsg();
        cm.x = left + 92f / 250 * w;
        cm.y = top + 90f / 250 * w;
        cm.r = 42.5f / 250 * w;
        cm.color = 0xccffffff;
        mCircleMsg.add(cm);

        //5
        cm = new CircleMsg();
        cm.x = left + 142f / 250 * w;
        cm.y = top + 104f / 250 * w;
        cm.r = 28.5f / 250 * w;
        cm.color = Color.WHITE;
        mCircleMsg.add(cm);

        //6
        cm = new CircleMsg();
        cm.x = left + 152f / 250 * w;
        cm.y = top + 76f / 250 * w;
        cm.r = 38f / 250 * w;
        cm.color = 0xccffffff;
        mCircleMsg.add(cm);

        //7
        cm = new CircleMsg();
        cm.x = left + 138f / 250 * w;
        cm.y = top + 62f / 250 * w;
        cm.r = 45f / 250 * w;
        cm.color = 0x99ffffff;
        mCircleMsg.add(cm);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }
        int t = ((int) (time - getStartTime())) % ANIM_DURATION;

        for (int i = 0; i < 7; i++) {
            CircleMsg cm = mCircleMsg.get(i);
            float delayTime = 100 * i;

            if (t < delayTime) {
                paint.setColor(cm.color);
                canvas.drawCircle(cm.x, cm.y, cm.r, paint);
            } else if (t < MOVE_DURATION + delayTime) {
                float deltaY = moveDistance * mInterpolator.getInterpolation((t - delayTime) / MOVE_DURATION);
                float y = cm.y - deltaY;

                paint.setColor(cm.color);
                canvas.drawCircle(cm.x, y, cm.r, paint);
            } else if (t < MOVE_DURATION * 2 + delayTime) {
//                float deltaY = moveDistance * mInterpolator.getInterpolation((MOVE_DURATION * 2 + delayTime - t) / MOVE_DURATION);
//                float y = cm.y - deltaY;
                float deltaY = moveDistance * mInterpolator.getInterpolation((t - MOVE_DURATION - delayTime) / MOVE_DURATION);
                float y = cm.y - moveDistance + deltaY;

                paint.setColor(cm.color);
                canvas.drawCircle(cm.x, y, cm.r, paint);
            } else {
                paint.setColor(cm.color);
                canvas.drawCircle(cm.x, cm.y, cm.r, paint);
            }
        }

    }


    private class CircleMsg {
        float x;
        float y;
        float r;
        int color;

        @Override
        public String toString() {
            return "CircleMsg{" +
                    "x=" + x +
                    ", y=" + y +
                    ", r=" + r +
                    ", color=" + color +
                    '}';
        }
    }
}
