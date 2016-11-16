package com.aloel.maribelajar.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.aloel.maribelajar.ui.fragment.KunciJawabanFragment;
import com.aloel.maribelajar.ui.fragment.Quiz1;

/**
 * Created by rurygaddafi on 8/5/16.
 */
public class KunciJawabanAdapter extends FragmentPagerAdapter {

    protected Context mContext;
    protected String mSubject;
    protected String mClass;

    public KunciJawabanAdapter(FragmentManager fm, Context context, String subject, String classStudent) {
        super(fm);
        mContext = context;
        mSubject = subject;
        mClass   = classStudent;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
//            case 0:
//                fragment = new Quiz1();
//            case 1:
//                fragment = new Quiz2();
//            case 2:
//                fragment = new Quiz3();

            default:
                fragment = new KunciJawabanFragment();

        }

        Bundle args = new Bundle();
        args.putInt("page_position", position + 1);
        args.putString("subject", mSubject);
        args.putString("class", mClass);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public int getCount() {
        return 10;
    }
}
