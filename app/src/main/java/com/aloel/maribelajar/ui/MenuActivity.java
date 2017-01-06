package com.aloel.maribelajar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aloel.maribelajar.MainActivity;
import com.aloel.maribelajar.R;

/**
 * Created by Aloel on 11/2/2016.
 */
public class MenuActivity extends BaseActivity {
    private Button mMulaiBtn;
    private Button mAboutBtn;
    private Button mPanduanBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBindService();
        setContentView(R.layout.activity_menu);
        doBindService();

        mMulaiBtn = (Button) findViewById(R.id.btn_mulai);
        mAboutBtn = (Button) findViewById(R.id.btn_about);
        mPanduanBtn = (Button) findViewById(R.id.btn_panduan);

        mMulaiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        mAboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });

        mPanduanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PanduanActivity.class);
                startActivity(intent);
            }
        });
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
