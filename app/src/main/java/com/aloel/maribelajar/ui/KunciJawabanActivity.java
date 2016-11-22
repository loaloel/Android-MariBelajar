package com.aloel.maribelajar.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloel.maribelajar.R;
import com.aloel.maribelajar.database.CacheDb;
import com.aloel.maribelajar.model.Answer;
import com.aloel.maribelajar.model.Quiz;
import com.aloel.maribelajar.ui.adapter.CustomPagerAdapter;
import com.aloel.maribelajar.ui.adapter.KunciJawabanAdapter;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class KunciJawabanActivity extends BaseActivity {

    private ImageView mLeftIv;
    private ImageView mRight;
    private Button mPenjelasanBtn;
    private Button mBackBtn;

    private ViewPager mViewPager;

    private KunciJawabanAdapter mAdapter;

    private ArrayList<Quiz> quizArrayList = new ArrayList<Quiz>();
    private CacheDb mCacheDb;

    private LoadCacheTask mLoadCacheTask;
    private String mSubject;
    private String mClass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kunci_jawaban);
        enableDatabase();
        mCacheDb = new CacheDb(getDatabase());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        (mLoadCacheTask = new LoadCacheTask()).execute();

        mLeftIv             = (ImageView) findViewById(R.id.iv_left);
        mRight              = (ImageView) findViewById(R.id.iv_right);
        mPenjelasanBtn      = (Button) findViewById(R.id.btn_penjelasan);
        mBackBtn            = (Button) findViewById(R.id.btn_back);
        mViewPager          = (ViewPager) findViewById(R.id.viewpager);

        mSubject = getIntent().getExtras().getString("subject");
        mClass = getIntent().getExtras().getString("class");

        mAdapter = new KunciJawabanAdapter(getSupportFragmentManager(), this, mSubject, mClass);
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

        mPenjelasanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPenjelasan();
            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCacheDb.reload(getDatabase());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        clearAnswer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        clearAnswer();
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

    public void showDialogPenjelasan () {
        int nilai = 0;
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_penjelasan, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView mPenjelasanTv = (TextView) view.findViewById(R.id.tv_penjelasan);
        ImageView mPenjelasanIv = (ImageView) view.findViewById(R.id.iv_penjelasan);
        Button mMengertiBtn = (Button) view.findViewById(R.id.btn_mengerti);

        Quiz quiz = quizArrayList.get(mViewPager.getCurrentItem());

        if (!quiz.penjelasan.equals("")) {
            mPenjelasanTv.setText(quizArrayList.get(mViewPager.getCurrentItem()).penjelasan);
        } else {
            Picasso.with(getActivity())
                    .load((quiz.penjelasan_image.equals("")) ? null : quiz.penjelasan_image)
                    .placeholder(R.drawable.no_image)
                    .error(R.drawable.no_image)
                    .into(mPenjelasanIv);
        }

        mMengertiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public class LoadCacheTask extends AsyncTask<URL, Integer, Long> {
        Quiz mQuiz;

        protected Long doInBackground(URL... urls) {
            long result = 0;

            try {
                Log.e("GGG", "DISINI");
                quizArrayList = mCacheDb.getQuizAll(mSubject, mClass);
                result = 1;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
        }
    }
}
