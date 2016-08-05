package com.aloel.maribelajar.util;

import android.annotation.SuppressLint;

@SuppressLint("SdCardPath")
public class Cons {

	public static final String TAG = "Mari Belajar";

	public static final String DBNAME = "mari_belajar.db";
	public static final String DBPATH = "/data/data/com.aloel.maribelajar/";
	public static final String APP_DIR = "/.mari_belajar";
	public static final String IMAGE_DIR = APP_DIR + "/thumbs";

	public static final String PACKAGE_PREFIX = "com.aloel.maribelajar";

	public static final int DB_VERSION = 1;

	// private preferences key name
	public static final String PRIVATE_PREF = "lisa_pref";
	public static final String SHARED_GROUP = "shared_group";
	public static final String DBVER_KEY = "dbversion";
	public static final String APPVER_KEY = "appversion";
	public static final String LASTUPD_KEY = "lastupdate";

	public static final String LANG_ID = "id";
	public static final String LANG_EN = "en";
	public static final String LANG_MY = "my";


	// enable debug
	public static final boolean ENABLE_DEBUG = true;

	// Http connection timeout
	public static final int HTTP_CONNECTION_TIMEOUT = 30000;

	// socket time out
	public static final int HTTP_SOCKET_TIMEOUT = 100000;

	public static final int GPS_ACCURACY = 500;
	public static final int NETWORK_ACCURACY = 3000;

	public static final int RESULT_CLOSE_ALL = 1001;
}
