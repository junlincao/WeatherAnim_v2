package com.mlog.weather.anim.v1.weatherItem;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.mlog.weather.anim.SimpleWeatherItem;

/**
 * 尘埃
 *
 * @author CJL
 * @since 2015-09-20
 */
public class Dust extends SimpleWeatherItem {

    int halfSize;
    float speed;
    int alphaDisappearLen;
    int alphaShowLen;

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);

        // 若宽度大于高度，则表示从左往右移动，否则从下往上移动
        halfSize = Math.min(mBounds.width(), mBounds.height());
        speed = halfSize * 15f / 1000;

        alphaDisappearLen = (int) (Math.max(mBounds.width(), mBounds.height()) * 0.1f);
        alphaShowLen = (int) (alphaDisappearLen * 0.8f);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint, long time) {
        if (getStatus() == STATUS_NOT_START) {
            return;
        }
        int t = (int) (time - getStartTime());

        int centerX, centerY;
        int alpha = 255;
        if (mBounds.width() > mBounds.height()) {
            centerY = mBounds.centerY();
            centerX = mBounds.left + (int) (-halfSize + speed * t);
            if (centerX > mBounds.right) {
                stop();
                return;
            }
            if (centerX - mBounds.left > alphaDisappearLen) {
                alpha = (int) (255 - 255f * (centerX - mBounds.left - alphaDisappearLen) / (mBounds.width() - alphaDisappearLen));
            } else if(centerX - mBounds.left < alphaShowLen){
                alpha = (int) (255f * (centerX - mBounds.left) / alphaShowLen);
            }
        } else {
            centerX = mBounds.centerX();
            centerY = (int) (mBounds.bottom + halfSize - speed * t);
            if (centerY < mBounds.top) {
                stop();
                return;
            }
            if (mBounds.bottom - centerY > alphaDisappearLen) {
                alpha = (int) (255 - 255f * (mBounds.bottom - centerY - alphaDisappearLen) / (mBounds.height() - alphaDisappearLen));
            } else if(mBounds.bottom - centerY < alphaShowLen){
                alpha = (int) (255f * (mBounds.bottom - centerY) / alphaShowLen);
            }
        }
        paint.setColor(Color.argb(alpha < 0 ? 0 : alpha, 255, 255, 255));

        canvas.save();
        canvas.rotate(45, centerX, centerY);
        canvas.drawRect(centerX - halfSize, centerY - halfSize, centerX + halfSize, centerY + halfSize, paint);
        canvas.restore();
    }
}
