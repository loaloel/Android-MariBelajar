package com.aloel.maribelajar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aloel.maribelajar.service.ServiceBGM;
import com.aloel.maribelajar.ui.BaseActivity;
import com.aloel.maribelajar.ui.QuizActivity;
import com.aloel.maribelajar.ui.SplashScreen;

public class MainActivity extends BaseActivity {

    private Button bahasaIndonesiaBtn;
    private Button matematikaBtn;

    private Intent svc;
    private Intent mIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enableDatabase();
        doBindService();

        bahasaIndonesiaBtn  = (Button) findViewById(R.id.bt_bhs_indonesia);
        matematikaBtn       = (Button) findViewById(R.id.bt_matematika);

        bahasaIndonesiaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(getApplicationContext(), QuizActivity.class);
                mIntent.putExtra("subject", "Bahasa Indonesia");
                mIntent.putExtra("class", "Class 1");
                startActivity(mIntent);
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
