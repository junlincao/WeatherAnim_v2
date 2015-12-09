package com.mlog.weather.anim.drawable;

import com.mlog.weather.anim.weatherItem.IWeatherItem;

/**
 * 自动生成随机天气元素
 *
 * @author CJL
 * @since 2015-09-18
 */
interface IWeatherRandomItem {
    /**
     * 产生时间间隔
     *
     * @return 时间间隔
     */
    int getInterval();

    /**
     * 获取天气元素
     * <p>
     * 该方法在动画进行中时，每隔 getInterval 时间调用一次，获取天气对象画出
     *
     * @return 天气元素
     */
    IWeatherItem getRandomWeatherItem();
}
