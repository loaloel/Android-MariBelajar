package com.aloel.maribelajar.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.aloel.maribelajar.MainActivity;
import com.aloel.maribelajar.R;
import com.aloel.maribelajar.service.ServiceBGM;
import com.aloel.maribelajar.util.Cons;
import com.aloel.maribelajar.util.Debug;
import com.aloel.maribelajar.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SplashScreen extends BaseActivity {

    Intent svc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Util.createAppDir();
        initDatabase();
        doBindService();

        svc = new Intent(this, ServiceBGM.class);
        startService(svc);

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
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
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();
    }

    protected void initDatabase() {
        Util.createAppDir();

        String database = Cons.DBPATH + Cons.DBNAME;
        int currVersion	= mSharedPref.getInt(Cons.DBVER_KEY, 0);

        try {
            Debug.i(Cons.TAG, "Current database version is " + String.valueOf(currVersion));

            if (Cons.DB_VERSION > currVersion) {
                File databaseFile  = new File(database);

                if (databaseFile.exists()) {
                    Debug.i(Cons.TAG, "Deleting current database " + Cons.DBNAME);

                    databaseFile.delete();
                }

                InputStream is	= getResources().getAssets().open(Cons.DBNAME);
                OutputStream os = new FileOutputStream(database);

                byte[] buffer	= new byte[1024];
                int length;

                Debug.i(Cons.TAG, "Start copying new database " + database + " version " + String.valueOf(Cons.DB_VERSION));

                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }

                os.flush();
                os.close();
                is.close();

                SharedPreferences.Editor editor = mSharedPref.edit();

                editor.putInt(Cons.DBVER_KEY, Cons.DB_VERSION);
                editor.commit();
            } else {
                if (Cons.ENABLE_DEBUG) {
                    InputStream is	= new FileInputStream(database);
                    OutputStream os = new FileOutputStream(Util.getAppDir() + "/mari_belajar.db");

                    byte[] buffer	= new byte[1024];
                    int length;

                    Debug.i(Cons.TAG, "[DEVONLY] Copying db " + database + " to " + Util.getAppDir() + "/mari_belajar.db");

                    while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }

                    os.flush();
                    os.close();
                    is.close();
                }
            }
        } catch (SecurityException ex) {
            Debug.e(Cons.TAG, "Failed to delete current database " + Cons.DBNAME, ex);
        } catch (IOException ex) {
            Debug.e(Cons.TAG, "Failed to copy new database " + Cons.DBNAME + " version " + String.valueOf(Cons.DB_VERSION), ex);
        }

        enableDatabase();
    }
}
