package com.aloel.maribelajar.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aloel.maribelajar.R;
import com.aloel.maribelajar.database.CacheDb;
import com.aloel.maribelajar.model.Quiz;

import java.net.URL;
import java.util.ArrayList;

public class Quiz1 extends BaseFragment {

    private CacheDb mCacheDb;

    private LoadCacheTask mLoadCacheTask;

    private int quizNumber = 1;
    private String mSubject = "";
    private String mClass = "";

    private TextView tvQuestionNumber;
    private TextView tvQuestion;
    private TextView tvOptionA;
    private TextView tvOptionB;
    private TextView tvOptionC;
    private TextView tvOptionD;
    private RelativeLayout rlOptionA;
    private RelativeLayout rlOptionB;
    private RelativeLayout rlOptionC;
    private RelativeLayout rlOptionD;
    private RelativeLayout rlD;
    private ImageView mImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_quiz, container, false);
        mCacheDb = new CacheDb(getDatabase());

        quizNumber  = getArguments().getInt("page_position");
        mSubject    = getArguments().getString("subject");
        mClass      = getArguments().getString("class");

        Log.e("FFF", quizNumber + "");

        initialize(root);

        (mLoadCacheTask = new LoadCacheTask()).execute();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCacheDb.reload(getDatabase());
    }

    public void initialize(View root) {
        tvQuestionNumber = (TextView) root.findViewById(R.id.tv_questionNumber);
        tvQuestion = (TextView) root.findViewById(R.id.tv_question);
        tvOptionA = (TextView) root.findViewById(R.id.tv_option_a);
        tvOptionB = (TextView) root.findViewById(R.id.tv_option_b);
        tvOptionC = (TextView) root.findViewById(R.id.tv_option_c);
        tvOptionD = (TextView) root.findViewById(R.id.tv_option_d);
        rlOptionA = (RelativeLayout) root.findViewById(R.id.rl_option_a);
        rlOptionB = (RelativeLayout) root.findViewById(R.id.rl_option_b);
        rlOptionC = (RelativeLayout) root.findViewById(R.id.rl_option_c);
        rlOptionD = (RelativeLayout) root.findViewById(R.id.rl_option_d);
        rlD       = (RelativeLayout) root.findViewById(R.id.rl_d);
        mImage    = (ImageView) root.findViewById(R.id.iv_image);
    }

    public void updateView(Quiz quiz) {
        Log.e("FFF", quiz.question);
        tvQuestionNumber.setText("Soal " + quizNumber);
        tvQuestion.setText(Html.fromHtml(quiz.question));
        tvOptionA.setText(quiz.option1);
        tvOptionB.setText(quiz.option2);
        tvOptionC.setText(quiz.option3);

        if (quiz.classStudent.equals("Class 1") || quiz.classStudent.equals("Class 2")) {
            rlD.setVisibility(View.GONE);
        } else {
            rlD.setVisibility(View.VISIBLE);
            tvOptionD.setText(quiz.option4);
        }

        if (quiz.image.equals("")) {
            mImage.setVisibility(View.GONE);
        }

    }

    public class LoadCacheTask extends AsyncTask<URL, Integer, Long> {
        Quiz mQuiz;

        protected Long doInBackground(URL... urls) {
            long result = 0;

            try {
                Log.e("GGG", "DISINI");
                result = 1;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(Long result) {
            if (mQuiz != null) {
                updateView(mQuiz);
            }
        }
    }
}
