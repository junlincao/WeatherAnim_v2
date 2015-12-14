package com.mlog.weather.anim.v2.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.IntDef;
import android.view.animation.Interpolator;

import com.mlog.weather.anim.SimpleWeatherItem;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 闪电层
 * Created by cjl on 2015/12/13.
 */
public class Light extends SimpleWeatherItem {

    @IntDef({TYPE_BACKGROUND, TYPE_FOREGROUND})
    @Retention(RetentionPolicy.SOURCE)
    @interface LIGHT_TYPE {

    }

    public static final int TYPE_FOREGROUND = 0;
    public static final int TYPE_BACKGROUND = 1;

    @LIGHT_TYPE
    int mType;

    public Light(@LIGHT_TYPE int type) {
        this.mType = type;
    }

    static final int ANIM_DURATION = 6000;

    private Interpolator mAlphaInt = new Interpolator() {

        final float[] step = new float[]{0f, 120f / ANIM_DURATION, 720f / ANIM_DURATION, 840f / ANIM_DURATION,
                1440F / ANIM_DURATION, 3000F / ANIM_DURATION, 3120F / ANIM_DURATION, 3720F / ANIM_DURATION, 1};

        @Override
        public float getInterpolation(float input) {
            if (input < step[1]) {
                return (input - step[0]) / (step[1] - step[0]);
            } else if (input < step[2]) {
                return (step[2] - input) / (step[2] - step[1]);
            } else if (input < step[3]) {
                return (input - step[2]) / (step[3] - step[2]);
            } else if (input < step[4]) {
                return (step[4] - input) / (step[4] - step[3]);
            } else if (input < step[5]) {
                return 0;
            } else if (input < step[6]) {
                return (input - step[5]) / (step[6] - step[5]);
            } else if (input < step[7]) {
                return (step[7] - input) / (step[7] - step[6]);
            } else {
                return 0;
            }
        }
    };

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        final int defAlpha = (int) (255 * (mType == TYPE_BACKGROUND ? 1 : 0.4f));
        int t = (int) ((time - getStartTime()) % ANIM_DURATION);
        int alpha = (int) (defAlpha * mAlphaInt.getInterpolation(t * 1f / ANIM_DURATION));

        canvas.save();
        canvas.clipRect(mBounds);
        canvas.drawColor(Color.argb(alpha, 255, 255, 255));
        canvas.restore();
    }
}
