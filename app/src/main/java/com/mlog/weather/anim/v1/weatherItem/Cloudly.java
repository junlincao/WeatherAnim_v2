package com.mlog.weather.anim.v1.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import com.mlog.weather.anim.SimpleWeatherItem;
import com.mlog.weather.anim.v1.drawable.SymAccDecInterpolator;

import java.util.ArrayList;

/**
 * 阴天
 *
 * @author CJL
 * @since 2015-09-19
 */
public class Cloudly extends SimpleWeatherItem {

    // 移动最大距离
    float moveDistance;

    // 向上或者向下移动时间
    static final int MOVE_DURATION = 3000;
    // 动画单周期总时间
    static final int ANIM_DURATION = 8000;
    static final int MOVE_DELTA = 100;
    // 各个圆圈信息
    ArrayList<CircleMsg> mCircleMsg = new ArrayList<>(7);

    public Cloudly() {
        mInterpolator = new SymAccDecInterpolator(2f);
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
        cm.x = left + 29f / 250 * w;
        cm.y = top + 159f / 250 * w;
        cm.r = 11.5f / 250 * w;
        cm.color = Color.WHITE;
        mCircleMsg.add(cm);

        //2
        cm = new CircleMsg();
        cm.x = left + 137f / 250 * w;
        cm.y = top + 139f / 250 * w;
        cm.r = 36f / 250 * w;
        cm.color = Color.WHITE;
        mCircleMsg.add(cm);

        //3
        cm = new CircleMsg();
        cm.x = left + 93f / 250 * w;
        cm.y = top + 149f / 250 * w;
        cm.r = 25f / 250 * w;
        cm.color = 0xccffffff;
        mCircleMsg.add(cm);

        //4
        cm = new CircleMsg();
        cm.x = left + 205f / 250 * w;
        cm.y = top + 158f / 250 * w;
        cm.r = 17.5f / 250 * w;
        cm.color = 0xccffffff;
        mCircleMsg.add(cm);

        //5
        cm = new CircleMsg();
        cm.x = left + 60f / 250 * w;
        cm.y = top + 144f / 250 * w;
        cm.r = 28.5f / 250 * w;
        cm.color = Color.WHITE;
        mCircleMsg.add(cm);

        //6
        cm = new CircleMsg();
        cm.x = left + 177f / 250 * w;
        cm.y = top + 147f / 250 * w;
        cm.r = 28.5f / 250 * w;
        cm.color = 0xccffffff;
        mCircleMsg.add(cm);

        //7
        cm = new CircleMsg();
        cm.x = left + 98f / 250 * w;
        cm.y = top + 119f / 250 * w;
        cm.r = 38.5f / 250 * w;
        cm.color = 0x99ffffff;
        mCircleMsg.add(cm);

        //8
        cm = new CircleMsg();
        cm.x = left + 167f / 250 * w;
        cm.y = top + 123f / 250 * w;
        cm.r = 27f / 250 * w;
        cm.color = 0x99ffffff;
        mCircleMsg.add(cm);

        //9
        cm = new CircleMsg();
        cm.x = left + 145f / 250 * w;
        cm.y = top + 100f / 250 * w;
        cm.r = 37.5f / 250 * w;
        cm.color = 0x99ffffff;
        mCircleMsg.add(cm);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }
        float stopTime = ANIM_DURATION / 2f - MOVE_DURATION;

        int count = mCircleMsg.size();
        for (int i = 0; i < count; i++) {
            float delayTime = MOVE_DELTA * i;
            int t = ((int) (time - getStartTime() - delayTime)) % ANIM_DURATION;

            CircleMsg cm = mCircleMsg.get(i);

            if (t < stopTime) {
                paint.setColor(cm.color);
                canvas.drawCircle(cm.x, cm.y, cm.r, paint);
            } else if (t < MOVE_DURATION + stopTime) {
                float deltaX = moveDistance * mInterpolator.getInterpolation((t - stopTime) / MOVE_DURATION);
                float x = cm.x + deltaX;

                paint.setColor(cm.color);
                canvas.drawCircle(x, cm.y, cm.r, paint);
            } else if (t < MOVE_DURATION + stopTime * 2) {
                paint.setColor(cm.color);
                canvas.drawCircle(cm.x + moveDistance, cm.y, cm.r, paint);
            } else {
                float deltaX = moveDistance * mInterpolator.getInterpolation((t - MOVE_DURATION - stopTime * 2) / MOVE_DURATION);
                float x = cm.x + moveDistance - deltaX;

                paint.setColor(cm.color);
                canvas.drawCircle(x, cm.y, cm.r, paint);
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
