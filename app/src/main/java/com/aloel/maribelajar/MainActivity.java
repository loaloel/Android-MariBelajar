package com.aloel.maribelajar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aloel.maribelajar.service.ServiceBGM;
import com.aloel.maribelajar.ui.BaseActivity;
import com.aloel.maribelajar.ui.ClassBhsIndoActivity;
import com.aloel.maribelajar.ui.ClassMatematikaActivity;
import com.aloel.maribelajar.ui.QuizActivity;
import com.aloel.maribelajar.ui.SplashScreen;
import com.aloel.maribelajar.ui.widget.PageIndicator;

public class MainActivity extends BaseActivity {

    private View bahasaIndonesiaCv;
    private View matematikaCv;

    private Intent mIntent;

    private PageIndicator mIndicator;
    private ViewPager mViewPager;

    private int[] mResource = {R.drawable.image_slider1, R.drawable.image_slider2};
    private String[] mResourceTitle = {"Bahasa Indonesia", "Matematika"};

    private static final int ANIM_VIEWPAGER_DELAY = 5000;
    private Handler handler;
    private Runnable animateViewPager;
    private boolean stopSliding = false;
    private CustomPagerAdapter mCustomPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enableDatabase();
        doBindService();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mIndicator = (PageIndicator) findViewById(R.id.indicatorHome);
        mViewPager = (ViewPager) findViewById(R.id.pagerBrowseSlider);

        bahasaIndonesiaCv  = findViewById(R.id.cv_bhs_indo);
        matematikaCv       = findViewById(R.id.cv_matematika);

        bahasaIndonesiaCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(getApplicationContext(), ClassBhsIndoActivity.class);
                mIntent.putExtra("subject", "Bahasa Indonesia");
                mIntent.putExtra("class", "Class 1");
                startActivity(mIntent);
            }
        });

        matematikaCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(getApplicationContext(), ClassMatematikaActivity.class);
                mIntent.putExtra("subject", "Matematika");
                mIntent.putExtra("class", "Class 1");
                startActivity(mIntent);
            }
        });

        setupSliderHome();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mServ != null) {
            mServ.resumeMusic();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mServ.pauseMusic();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mServ.pauseMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("DDD", "HERE");
        doUnbindService();
    }

    private void runnable(final int size) {
        handler = new Handler();
        animateViewPager = new Runnable() {
            public void run() {
                if (!stopSliding) {
                    if (mViewPager.getCurrentItem() == size - 1) {
                        mViewPager.setCurrentItem(0);
                    } else {
                        mViewPager.setCurrentItem(
                                mViewPager.getCurrentItem() + 1, true);
                    }
                    handler.postDelayed(animateViewPager, ANIM_VIEWPAGER_DELAY);
                }
            }
        };
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

        runnable(mResource.length);
        handler.postDelayed(animateViewPager,
                ANIM_VIEWPAGER_DELAY);
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
            TextView tvTitle = (TextView) itemView.findViewById(R.id.tv_home_slider);

            ivCoverSlide.setImageResource(mResource[position]);
            tvTitle.setText(mResourceTitle[position]);

            ivCoverSlide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mResourceTitle[position].equals("Bahasa Indonesia")) {
                        mIntent = new Intent(getApplicationContext(), ClassBhsIndoActivity.class);
                        mIntent.putExtra("subject", "Bahasa Indonesia");
                        mIntent.putExtra("class", "Class 1");
                        startActivity(mIntent);
                    } else {
                        mIntent = new Intent(getApplicationContext(), ClassMatematikaActivity.class);
                        mIntent.putExtra("subject", "Bahasa Indonesia");
                        mIntent.putExtra("class", "Class 1");
                        startActivity(mIntent);
                    }
                }
            });

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
}
