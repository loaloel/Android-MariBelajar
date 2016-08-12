package com.aloel.maribelajar.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.aloel.maribelajar.R;
import com.aloel.maribelajar.model.StoreCategory;
import com.aloel.maribelajar.ui.adapter.CustomPagerAdapter;
import com.aloel.maribelajar.ui.adapter.ItemBrowseListAdapter;
import com.aloel.maribelajar.ui.fragment.Quiz1;
import com.aloel.maribelajar.ui.widget.ItemClickSupport;

import java.util.ArrayList;

public class ClassMatematikaActivity extends BaseActivity {

    private RelativeLayout mLayoutSlider;
    private RecyclerView mRecyclerView;
    private ArrayList<StoreCategory> mCategories = new ArrayList<>();
    private ItemBrowseListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        enableDatabase();

        String[] data = {"Kelas 1", "Kelas 2", "Kelas 3", "Kelas 4", "Kelas 5", "Kelas 6"};

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_class);

        for (int i = 0; i < data.length; i++) {
            StoreCategory storeCategory = new StoreCategory();
            storeCategory.setTitle(data[i]);

            mCategories.add(storeCategory);
        }

        setupRecyclerView();

    }

    private void setupRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ItemBrowseListAdapter(mCategories, getActivity());
        mRecyclerView.setAdapter(mAdapter);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                StoreCategory categories = mCategories.get(position);

                String kelas = categories.getTitle();

                Intent mIntent = new Intent(getApplicationContext(), QuizActivity.class);
                mIntent.putExtra("subject", "Matematika");
                mIntent.putExtra("class", kelas);
                startActivity(mIntent);
            }
        });

        mAdapter.notifyDataSetChanged();
    }
}
