package com.aloel.maribelajar.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.net.URL;
import java.util.ArrayList;

public class QuizActivity extends BaseActivity {

    private CacheDb mCacheDb;

    private LoadCacheTask mLoadCacheTask;

    private ImageView mLeftIv;
    private ImageView mRight;
    private Button mKumpulkanBtn;
    private Button mDaftarSoalBtn;

    private ViewPager mViewPager;

    private CustomPagerAdapter mAdapter;

    private String mSubject;
    private String mClass;

    private ArrayList<Quiz> quizArrayList = new ArrayList<Quiz>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBindService();
        setContentView(R.layout.activity_quiz);
        enableDatabase();

        mCacheDb = new CacheDb(getDatabase());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mLeftIv             = (ImageView) findViewById(R.id.iv_left);
        mRight              = (ImageView) findViewById(R.id.iv_right);
        mKumpulkanBtn       = (Button) findViewById(R.id.btn_kumpulkan);
        mDaftarSoalBtn      = (Button) findViewById(R.id.btn_daftar_soal);
        mViewPager          = (ViewPager) findViewById(R.id.viewpager);

        mSubject = getIntent().getExtras().getString("subject");
        mClass = getIntent().getExtras().getString("class");

        (mLoadCacheTask = new LoadCacheTask()).execute();

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
                showDialogKumpulkan();
            }
        });

        mDaftarSoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSoal();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCacheDb.reload(getDatabase());
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

        clearAnswer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("DDD", "HERE");
        doUnbindService();

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

    public void showDialogKumpulkan () {
        int nilai = 0;
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_kumpulkan, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                clearAnswer();
                finish();
            }
        });
        dialog.show();

        TextView mNilaiTv = (TextView) view.findViewById(R.id.tv_nilai);
        Button mUlangiBtn = (Button) view.findViewById(R.id.btn_ulangi);
        Button mKunciJawaban = (Button) view.findViewById(R.id.btn_kunci_jawaan);

        Answer answer = getAnswer();

        Log.e("RightAnswer", quizArrayList.get(0).answer + "");
        Log.e("RightAnswer", quizArrayList.get(1).answer + "");
        Log.e("RightAnswer", quizArrayList.get(2).answer + "");
        Log.e("RightAnswer", quizArrayList.get(3).answer + "");
        Log.e("RightAnswer", quizArrayList.get(4).answer + "");
        Log.e("RightAnswer", quizArrayList.get(5).answer + "");
        Log.e("RightAnswer", quizArrayList.get(6).answer + "");
        Log.e("RightAnswer", quizArrayList.get(7).answer + "");
        Log.e("RightAnswer", quizArrayList.get(8).answer + "");
        Log.e("RightAnswer", quizArrayList.get(9).answer + "");

        if (answer.number1.equals(quizArrayList.get(0).answer)) {
            nilai++;
        }
        if (answer.number2.equals(quizArrayList.get(1).answer)) {
            nilai++;
        }
        if (answer.number3.equals(quizArrayList.get(2).answer)) {
            nilai++;
        }
        if (answer.number4.equals(quizArrayList.get(3).answer)) {
            nilai++;
        }
        if (answer.number5.equals(quizArrayList.get(4).answer)) {
            nilai++;
        }
        if (answer.number6.equals(quizArrayList.get(5).answer)) {
            nilai++;
        }
        if (answer.number7.equals(quizArrayList.get(6).answer)) {
            nilai++;
        }
        if (answer.number8.equals(quizArrayList.get(7).answer)) {
            nilai++;
        }
        if (answer.number9.equals(quizArrayList.get(8).answer)) {
            nilai++;
        }
        if (answer.number10.equals(quizArrayList.get(9).answer)) {
            nilai++;
        }

        mNilaiTv.setText(String.valueOf(nilai));

        if (answer.number1.equals("") || answer.number2.equals("") || answer.number3.equals("") ||
                answer.number4.equals("") || answer.number5.equals("") || answer.number6.equals("") ||
                answer.number7.equals("") || answer.number8.equals("") || answer.number9.equals("") ||
                answer.number10.equals("")) {
        } else {
            dialog.show();
        }

        mUlangiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("GGG", "DISINI");
                Intent mIntent = new Intent(getActivity(), QuizActivity.class);
                mIntent.putExtra("subject", mSubject);
                mIntent.putExtra("class", mClass);
                startActivity(mIntent);

                clearAnswer();
                dialog.dismiss();
                finish();
            }
        });

        mKunciJawaban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("QQQ", "DISINI");
                Intent mIntent = new Intent(getActivity(), KunciJawabanActivity.class);
                mIntent.putExtra("subject", mSubject);
                mIntent.putExtra("class", mClass);
                startActivity(mIntent);
                clearAnswer();
                finish();

                dialog.dismiss();
            }
        });
    }

    public void showDialogSoal() {
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
