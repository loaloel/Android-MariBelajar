package com.aloel.maribelajar;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aloel.maribelajar.ui.BaseActivity;
import com.aloel.maribelajar.ui.ClassActivity;
import com.aloel.maribelajar.ui.QuizActivity;
import com.aloel.maribelajar.ui.widget.PageIndicator;

public class MainActivity extends BaseActivity {

    private View bahasaIndonesiaCv;
    private View matematikaCv;
    private View mNext;
    private View mPrev;

    private TextView mKelasTv;

    private int kelasIndex = 1;

    private Intent mIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enableDatabase();
        doBindService();
        initialize();
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

    private void initialize() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        bahasaIndonesiaCv   = findViewById(R.id.rl_bahasa);
        matematikaCv        = findViewById(R.id.rl_matematika);
        mNext               = findViewById(R.id.iv_next);
        mPrev               = findViewById(R.id.iv_prev);

        mKelasTv            = (TextView) findViewById(R.id.tv_kelas);

        mKelasTv.setText("Kelas 1");
        mPrev.setVisibility(View.INVISIBLE);

        bahasaIndonesiaCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(getApplicationContext(), QuizActivity.class);
                mIntent.putExtra("subject", "Bahasa Indonesia");
                mIntent.putExtra("class", String.valueOf(kelasIndex));
                startActivity(mIntent);
            }
        });

        matematikaCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(getApplicationContext(), QuizActivity.class);
                mIntent.putExtra("subject", "Matematika");
                mIntent.putExtra("class", String.valueOf(kelasIndex));
                startActivity(mIntent);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kelasIndex++;

                if (kelasIndex == 7) {
                    mNext.setVisibility(View.INVISIBLE);
                    mPrev.setVisibility(View.VISIBLE);
                    return;
                } else {
                    mNext.setVisibility(View.VISIBLE);
                    mPrev.setVisibility(View.VISIBLE);
                }

                mKelasTv.setText("Kelas " + kelasIndex);
            }
        });

        mPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kelasIndex--;

                if (kelasIndex == 0) {
                    mNext.setVisibility(View.VISIBLE);
                    mPrev.setVisibility(View.INVISIBLE);
                    return;
                } else {
                    mNext.setVisibility(View.VISIBLE);
                    mPrev.setVisibility(View.VISIBLE);
                }

                mKelasTv.setText("Kelas " + kelasIndex);
            }
        });
    }
}
