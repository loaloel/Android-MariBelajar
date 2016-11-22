package com.aloel.maribelajar.ui;

import android.content.Intent;
import android.os.Bundle;
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
        setContentView(R.layout.activity_menu);

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
}
