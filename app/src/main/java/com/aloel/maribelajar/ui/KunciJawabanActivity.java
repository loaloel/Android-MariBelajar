package com.aloel.maribelajar.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.aloel.maribelajar.model.Answer;
import com.aloel.maribelajar.ui.adapter.CustomPagerAdapter;

public class KunciJawabanActivity extends BaseActivity {

    private ImageView mLeftIv;
    private ImageView mRight;
    private Button mPenjelasanBtn;
    private Button mBackBtn;

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
        mPenjelasanBtn      = (Button) findViewById(R.id.btn_penjelasan);
        mBackBtn            = (Button) findViewById(R.id.btn_back);
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

        mPenjelasanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        clearAnswer();
    }

    public void showDialogSoal(ViewPager viewPager) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_daftar_soal, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        Answer answer = getAnswer();
        TextView number1 = (TextView) view.findViewById(R.id.tv_number_1);
        TextView number2 = (TextView) view.findViewById(R.id.tv_number_2);
        TextView number3 = (TextView) view.findViewById(R.id.tv_number_3);
        TextView number4 = (TextView) view.findViewById(R.id.tv_number_4);
        TextView number5 = (TextView) view.findViewById(R.id.tv_number_5);
        TextView number6 = (TextView) view.findViewById(R.id.tv_number_6);
        TextView number7 = (TextView) view.findViewById(R.id.tv_number_7);
        TextView number8 = (TextView) view.findViewById(R.id.tv_number_8);
        TextView number9 = (TextView) view.findViewById(R.id.tv_number_9);
        TextView number10 = (TextView) view.findViewById(R.id.tv_number_10);

        View viewNumber1 = view.findViewById(R.id.rl_number_1);
        View viewNumber2 = view.findViewById(R.id.rl_number_2);
        View viewNumber3 = view.findViewById(R.id.rl_number_3);
        View viewNumber4 = view.findViewById(R.id.rl_number_4);
        View viewNumber5 = view.findViewById(R.id.rl_number_5);
        View viewNumber6 = view.findViewById(R.id.rl_number_6);
        View viewNumber7 = view.findViewById(R.id.rl_number_7);
        View viewNumber8 = view.findViewById(R.id.rl_number_8);
        View viewNumber9 = view.findViewById(R.id.rl_number_9);
        View viewNumber10 = view.findViewById(R.id.rl_number_10);

        if (!answer.number1.equals("")) {
            number1.setText("Sudah dijawab");
            viewNumber1.setBackgroundResource(R.drawable.round_orange);
        }
        if (!answer.number2.equals("")) {
            number2.setText("Sudah dijawab");
            viewNumber2.setBackgroundResource(R.drawable.round_orange);
        }
        if (!answer.number3.equals("")) {
            number3.setText("Sudah dijawab");
            viewNumber3.setBackgroundResource(R.drawable.round_orange);
        }
        if (!answer.number4.equals("")) {
            number4.setText("Sudah dijawab");
            viewNumber4.setBackgroundResource(R.drawable.round_orange);
        }
        if (!answer.number5.equals("")) {
            number5.setText("Sudah dijawab");
            viewNumber5.setBackgroundResource(R.drawable.round_orange);
        }
        if (!answer.number6.equals("")) {
            number6.setText("Sudah dijawab");
            viewNumber6.setBackgroundResource(R.drawable.round_orange);
        }
        if (!answer.number7.equals("")) {
            number7.setText("Sudah dijawab");
            viewNumber7.setBackgroundResource(R.drawable.round_orange);
        }
        if (!answer.number8.equals("")) {
            number8.setText("Sudah dijawab");
            viewNumber8.setBackgroundResource(R.drawable.round_orange);
        }
        if (!answer.number9.equals("")) {
            number9.setText("Sudah dijawab");
            viewNumber9.setBackgroundResource(R.drawable.round_orange);
        }
        if (!answer.number10.equals("")) {
            number10.setText("Sudah dijawab");
            viewNumber10.setBackgroundResource(R.drawable.round_orange);
        }

        viewNumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
                dialog.dismiss();
            }
        });

        viewNumber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
                dialog.dismiss();
            }
        });

        viewNumber3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
                dialog.dismiss();
            }
        });

        viewNumber4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(3);
                dialog.dismiss();
            }
        });

        viewNumber5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(4);
                dialog.dismiss();
            }
        });

        viewNumber6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(5);
                dialog.dismiss();
            }
        });

        viewNumber7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(6);
                dialog.dismiss();
            }
        });

        viewNumber8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(7);
                dialog.dismiss();
            }
        });

        viewNumber9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(8);
                dialog.dismiss();
            }
        });

        viewNumber10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(9);
                dialog.dismiss();
            }
        });

    }
}
