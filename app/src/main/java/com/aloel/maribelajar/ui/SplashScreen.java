package com.aloel.maribelajar.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Toast;

import com.aloel.maribelajar.MainActivity;
import com.aloel.maribelajar.R;
import com.aloel.maribelajar.database.CacheDb;
import com.aloel.maribelajar.model.Quiz;
import com.aloel.maribelajar.service.ServiceBGM;
import com.aloel.maribelajar.util.Cons;
import com.aloel.maribelajar.util.Debug;
import com.aloel.maribelajar.util.Util;
import com.aloel.maribelajar.volley.VolleyRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SplashScreen extends BaseActivity {

    private Intent svc;
    private ArrayList<Quiz> mQuiz = new ArrayList<Quiz>();
    private CacheDb mCacheDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        mCacheDb = new CacheDb(getDatabase());

        Util.createAppDir();
        initDatabase();
        doBindService();

        svc = new Intent(this, ServiceBGM.class);
        startService(svc);

        new CountDownTimer(1000,100) {
            public void onTick(long millisUntilFinished) {}

            public void onFinish() {
                getConstantConnection();
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mServ != null) {
            mServ.resumeMusic();
        }

        mCacheDb.reload(getDatabase());
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

    private void getConstantConnection() {
        String url = "http://aloel.esy.es/question.php";

        VolleyRequest mRequest;
        mRequest = new VolleyRequest(Request.Method.GET, url, null,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Response", response);
                        try {
                            mQuiz = constantParse(response);
                            mCacheDb.update(mQuiz);

                            Intent intent = new Intent(getActivity(), MenuActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", "ERROR, " + error.getMessage());

                        mQuiz = mCacheDb.getQuizAll();
                        if (mQuiz == null) {
                            Toast.makeText(getActivity(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(mRequest);

    }

    public ArrayList<Quiz> constantParse(String stringJson) throws JSONException {
        ArrayList<Quiz> mQuiz = null;

        if (stringJson != null) {
            mQuiz = new ArrayList<Quiz>();
            JSONArray jsonArray = new JSONArray(stringJson);

            for (int i = 0; i < jsonArray.length(); i++) {
                Quiz quiz = new Quiz();

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                quiz.id             = jsonObject.getString("id");
                quiz.subject        = jsonObject.getString("subject");
                quiz.classStudent   = jsonObject.getString("class");
                quiz.type           = jsonObject.getString("type");
                quiz.question       = jsonObject.getString("question");
                quiz.image          = jsonObject.getString("image");
                quiz.option1        = jsonObject.getString("option1");
                quiz.option2        = jsonObject.getString("option2");
                quiz.option3        = jsonObject.getString("option3");
                quiz.option4        = jsonObject.getString("option4");
                quiz.answer         = jsonObject.getString("answer");
                quiz.penjelasan     = jsonObject.getString("penjelasan");
                quiz.penjelasan_image = jsonObject.getString("penjelasan_image");

                mQuiz.add(quiz);
            }
        }

        return mQuiz;
    }
}
