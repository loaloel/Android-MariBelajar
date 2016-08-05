package com.aloel.maribelajar.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.aloel.maribelajar.R;
import com.aloel.maribelajar.service.ServiceBGM;
import com.aloel.maribelajar.util.Cons;
import com.aloel.maribelajar.util.Debug;
import com.aloel.maribelajar.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class BaseActivity extends AppCompatActivity {
	protected SQLiteDatabase mSqLite;
	protected SharedPreferences mSharedPref;
	private boolean mIsDbOpen = false;
	private boolean mEnableDb = false;

	protected boolean mIsBound = false;
	protected ServiceBGM mServ;
	protected ServiceConnection Scon =new ServiceConnection(){

		public void onServiceConnected(ComponentName name, IBinder
				binder) {
			mServ = ((ServiceBGM.ServiceBinder)binder).getService();
			mServ.resumeMusic();
		}

		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
	};

	public void doBindService(){
		bindService(new Intent(this,ServiceBGM.class),
				Scon, Context.BIND_AUTO_CREATE);
		mIsBound = true;
	}

	public void doUnbindService()
	{
		if(mIsBound)
		{
			unbindService(Scon);
			mIsBound = false;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSharedPref = getSharedPreferences(Cons.PRIVATE_PREF,
				Context.MODE_PRIVATE);
	}

	@Override
	protected void onPause() {
		if (mEnableDb && mIsDbOpen) {
			closeDatabase();
		}

		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();

		mSharedPref = getSharedPreferences(Cons.PRIVATE_PREF,
				Context.MODE_PRIVATE);

		if (mEnableDb && !mIsDbOpen) {
			openDatabase();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void back() {
		setResult(RESULT_OK);
		finish();
	}

	public boolean buildVersion() {
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			return true;
		}else {
			return false;
		}
	}

	public static final int getColorModify(Context context, int id) {
		final int version = Build.VERSION.SDK_INT;
		if (version >= 21) {
			return ContextCompat.getColor(context, id);
		} else {
			return context.getResources().getColor(id);
		}
	}


	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}

	/*******************************************************************
	 * Till here for about Oauth and SharedPreferences User
	 * 
	 * *****************************************************************
	 */
	
	public SQLiteDatabase getDatabase() {
		return mSqLite;
	}
	
	public SharedPreferences getSharedPreferences() {
		return mSharedPref;
	}
	
	public int getDeviceType() {
		return (Util.isHoneycombTablet(this)) ? 3 : 2;
	}
	
	public String getOS() {
		return Build.VERSION.RELEASE;
	}
	
	public String getLatestUpdate() {
		
		return mSharedPref.getString(Cons.LASTUPD_KEY, "2014-01-29 00:00:00");
	}
	
	public BaseActivity getActivity() {
		return this;
	}

	protected void enableDatabase() {
		mEnableDb = true;

		openDatabase();
	}
	
	public void logout() {
		Editor editor = mSharedPref.edit();
    	
		//editor.putBoolean(Cons.PM_KEEP_LOGIN, 	false);
    
    	editor.commit();    	
	}

	// This for open Koneksi to Database
	private void openDatabase() {
		if (mIsDbOpen) {
			Debug.i(Cons.TAG, "Database already open");
			return;
		}

		String db = Cons.DBPATH + Cons.DBNAME;

		try {
			mSqLite = SQLiteDatabase.openDatabase(db, null,
					SQLiteDatabase.NO_LOCALIZED_COLLATORS);
			mIsDbOpen = mSqLite.isOpen();

			Debug.i(Cons.TAG, "Database open");
		} catch (SQLiteException e) {
			Debug.e(Cons.TAG, "Can not open database " + db, e);
		}
	}

	// This for Close Koneksi to Database
	private void closeDatabase() {
		if (!mIsDbOpen)
			return;

		mSqLite.close();

		mIsDbOpen = false;

		Debug.i(Cons.TAG, "Database closed");
	}

	public void openGPS(Context context) {
		context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	}

	// This for Resize Image File
	public File saveBitmapToFile(File file){
		try {

			// BitmapFactory options to downsize the image
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			o.inSampleSize = 6;
			// factor of downsizing the image

			FileInputStream inputStream = new FileInputStream(file);
			//Bitmap selectedBitmap = null;
			BitmapFactory.decodeStream(inputStream, null, o);
			inputStream.close();

			// The new size we want to scale to
			final int REQUIRED_SIZE=75;

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
					o.outHeight / scale / 2 >= REQUIRED_SIZE) {
				scale *= 2;
			}

			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			inputStream = new FileInputStream(file);

			Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
			inputStream.close();

			// here i override the original image file
			file.createNewFile();
			FileOutputStream outputStream = new FileOutputStream(file);

			selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , outputStream);

			return file;
		} catch (Exception e) {
			return null;
		}
	}

}
