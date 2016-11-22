package com.aloel.maribelajar.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aloel.maribelajar.MainActivity;
import com.aloel.maribelajar.R;
import com.aloel.maribelajar.ui.widget.PageIndicator;

/**
 * Created by Aloel on 11/2/2016.
 */
public class PanduanActivity extends BaseActivity {

    private PageIndicator mIndicator;
    private ViewPager mViewPager;

    private int[] mResource = {R.drawable.step1, R.drawable.step2, R.drawable.step3, R.drawable.step4};

    private static final int ANIM_VIEWPAGER_DELAY = 5000;
    private CustomPagerAdapter mCustomPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panduan);

        mIndicator = (PageIndicator) findViewById(R.id.indicatorHome);
        mViewPager = (ViewPager) findViewById(R.id.pagerBrowseSlider);

        setupSliderHome();
    }

    private void setupSliderHome() {
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity());

        mViewPager.setAdapter(mCustomPagerAdapter);
        mIndicator.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResource.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.home_slider, container, false);

            ImageView ivCoverSlide = (ImageView) itemView.findViewById(R.id.iv_home_slider);

            ivCoverSlide.setImageResource(mResource[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
}
