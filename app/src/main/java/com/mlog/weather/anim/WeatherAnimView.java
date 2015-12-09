package com.mlog.weather.anim;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mlog.weather.anim.drawable.WeatherDrawable;

/**
 * 天气动画View，添加的背景若是天气动画Drawable，则自动启动动画并且View移除时自动关闭动画
 *
 * @author CJL
 * @since 2015-09-11
 */
public class WeatherAnimView extends ImageView {


    public WeatherAnimView(Context context) {
        super(context);
    }

    public WeatherAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeatherAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public WeatherAnimView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        Drawable d = getDrawable();
        if (d != null && d.equals(drawable)) {
            return;
        }

        if (d != null && d instanceof WeatherDrawable) {
            ((WeatherDrawable) d).stopAnimation();
        }

        super.setImageDrawable(drawable);

        if (drawable != null && drawable instanceof WeatherDrawable && isShown()) {
            ((WeatherDrawable) drawable).startAnimation();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        Drawable d = getDrawable();
        if (d != null && d instanceof WeatherDrawable) {
            ((WeatherDrawable) d).startAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        Drawable d = getDrawable();
        if (d != null && d instanceof WeatherDrawable) {
            ((WeatherDrawable) d).stopAnimation();
        }
    }
}
