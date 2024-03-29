package com.akamadani.mazhab.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akamadani.mazhab.R;
import com.akamadani.mazhab.activity.BlogsByCategoryActivity;
import com.akamadani.mazhab.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    Category mCategory;
    private Context mContext;
    private List<Category> mCategories;

    public CategoryRecyclerViewAdapter(Context context, List<Category> categories) {
        this.mContext = context;
        this.mCategories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_category_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        mCategory = mCategories.get(position);

        holder.title.setText(mCategory.getName());
        Picasso
                .get()
                .load(mCategory.getImageUrl())
                .error(R.drawable.cat_default)
                .into(holder.image);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BlogsByCategoryActivity.class);
                intent.putExtra("category", mCategories.get(position).getKey());
                intent.putExtra("category_name", mCategories.get(position).getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView image;
        public RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.category_title);
            image = view.findViewById(R.id.category_image);
            relativeLayout = view.findViewById(R.id.relativeLayout);
        }
    }
}
