package com.aloel.maribelajar.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.aloel.maribelajar.R;

/**
 * Created by Aloel on 11/2/2016.
 */
public class AboutActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBindService();
        setContentView(R.layout.activity_about);
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
}
