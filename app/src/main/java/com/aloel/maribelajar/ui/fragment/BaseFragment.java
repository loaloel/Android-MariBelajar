package com.aloel.maribelajar.ui.fragment;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.aloel.maribelajar.ui.BaseActivity;

public class BaseFragment extends Fragment {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public SQLiteDatabase getDatabase() {		
		return ((BaseActivity) getActivity()).getDatabase();
	}
	
	public SharedPreferences getSharedPreferences() {
		return ((BaseActivity) getActivity()).getSharedPreferences();
	}

	public boolean isOnline() {
		return ((BaseActivity) getActivity()).isOnline();
	}
	
	public String getLatestUpdate() {
		return ((BaseActivity) getActivity()).getLatestUpdate();
	}
	
	public void logout() {
		((BaseActivity) getActivity()).logout();
	}
	
	public void back() {
		((BaseActivity) getActivity()).back();
	}

	public boolean buildVersion() {
		return ((BaseActivity) getActivity()).buildVersion();
	}
}
