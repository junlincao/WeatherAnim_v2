package com.example.cjl.weatheranim_v2;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mlog.weather.anim.WeatherAnimView;
import com.mlog.weather.anim.WeatherDrawable;

import java.util.ArrayList;
import java.util.Map;

/**
 * PagerAdapter
 *
 * @author CJL
 * @since 2015-09-12
 */
public class AnimPagerAdapter extends PagerAdapter {

    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<WeatherDrawable> mDrawables = new ArrayList<>();

    private LayoutInflater mInflater;

    public AnimPagerAdapter(LayoutInflater inflater) {
        mInflater = inflater;
    }

    public void updateData(Map<String, WeatherDrawable> drawables) {
        if (drawables == null) {
            return;
        }

        mTitles.clear();
        mDrawables.clear();

        for (Map.Entry<String, WeatherDrawable> entry : drawables.entrySet()) {
            mTitles.add(entry.getKey());
            mDrawables.add(entry.getValue());
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = mInflater.inflate(R.layout.pager_item, container, false);

        WeatherAnimView wav = (WeatherAnimView) v.findViewById(R.id.weatherView);
        wav.setImageDrawable(mDrawables.get(position));

        container.addView(v);

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View v = (View) object;
        container.removeView(v);
    }
}