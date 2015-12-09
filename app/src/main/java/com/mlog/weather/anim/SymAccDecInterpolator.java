package com.mlog.weather.anim;

import android.view.animation.Interpolator;

/**
 * 先加速后减速
 * <p>
 * 加减速对称
 *
 * @author CJL
 * @since 2015-09-19
 */
public class SymAccDecInterpolator implements Interpolator {
    float mFactor;

    public SymAccDecInterpolator(float factor) {
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