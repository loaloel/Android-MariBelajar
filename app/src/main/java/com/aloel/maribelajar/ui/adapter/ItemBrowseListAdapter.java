package com.aloel.maribelajar.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.aloel.maribelajar.R;
import com.aloel.maribelajar.model.StoreCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemBrowseListAdapter extends RecyclerView.Adapter<ItemBrowseListAdapter.ItemViewHolderCategoryList> {

    private List<StoreCategory> mStoreCategoryList;
    private Context mContext;
    private int lastPosition = -1;

    public ItemBrowseListAdapter(List<StoreCategory> categori, Context context) {
        this.mStoreCategoryList = categori;

        this.mContext = context;
    }

    @Override
    public ItemViewHolderCategoryList onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_browse_list_parralax, parent, false);

        return new ItemViewHolderCategoryList(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolderCategoryList holder, int position) {
        StoreCategory categories = mStoreCategoryList.get(position);

        String title = categories.getTitle();
        int image = 0;

        holder.tvTitle.setText(categories.getTitle());

        if (title.equals("Kelas 1")) {
            image = R.drawable.image_slider1;
        } else if (title.equals("Kelas 2")) {
            image = R.drawable.image_slider2;
        } else if (title.equals("Kelas 3")) {
            image = R.drawable.image_slider2;
        } else if (title.equals("Kelas 4")) {
            image = R.drawable.image_slider1;
        } else if (title.equals("Kelas 5")) {
            image = R.drawable.image_slider1;
        } else if (title.equals("Kelas 6")) {
            image = R.drawable.image_slider2;
        }

        Picasso.with(mContext)
                .load(image)
                .error(R.drawable.no_image)
                .into(holder.ivCover);
    }

    @Override
    public int getItemCount() {
        return mStoreCategoryList.size();
    }

    class ItemViewHolderCategoryList extends RecyclerView.ViewHolder {

        private ImageView ivCover;
        private TextView tvTitle;
        private CardView mCardView;

        public ItemViewHolderCategoryList(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cv_category_parallax);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title_category_list);
            ivCover = (ImageView) itemView.findViewById(R.id.iv_category_list);
        }
    }
}
