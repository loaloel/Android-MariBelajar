package com.aloel.maribelajar.ui;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.aloel.maribelajar.R;
import com.aloel.maribelajar.model.Quiz;
import com.aloel.maribelajar.ui.adapter.CustomPagerAdapter;

import java.net.URL;
import java.util.ArrayList;

public class QuizActivity extends BaseActivity {

    private ImageView mLeftIv;
    private ImageView mRight;
    private Button mKumpulkanBtn;
    private Button mDaftarSoalBtn;

    private ViewPager mViewPager;

    private CustomPagerAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        enableDatabase();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLeftIv             = (ImageView) findViewById(R.id.iv_left);
        mRight              = (ImageView) findViewById(R.id.iv_right);
        mKumpulkanBtn       = (Button) findViewById(R.id.btn_kumpulkan);
        mDaftarSoalBtn      = (Button) findViewById(R.id.btn_daftar_soal);
        mViewPager          = (ViewPager) findViewById(R.id.viewpager);

        String mSubject = getIntent().getExtras().getString("subject");
        String mClass = getIntent().getExtras().getString("class");

        mAdapter = new CustomPagerAdapter(getSupportFragmentManager(), this, mSubject, mClass);
        mViewPager.setOffscreenPageLimit(10);
        mViewPager.setAdapter(mAdapter);

        mLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prev();
            }
        });

        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        mKumpulkanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void next() {
        int currentItem = mViewPager.getCurrentItem();
        Log.e("Current Item", currentItem + "");

        if (currentItem == 9) {
            mViewPager.setCurrentItem(0);
        } else {
            currentItem++;
            mViewPager.setCurrentItem(currentItem);
        }
    }

    public void prev() {
        int currentItem = mViewPager.getCurrentItem();
        Log.e("Current Item", currentItem + "");

        if (currentItem == 0) {
            mViewPager.setCurrentItem(9);
        } else {
            currentItem--;
            mViewPager.setCurrentItem(currentItem);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        clearAnswer();
    }
}
