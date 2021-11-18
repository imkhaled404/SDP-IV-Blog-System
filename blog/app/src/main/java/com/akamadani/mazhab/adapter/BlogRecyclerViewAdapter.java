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
import com.akamadani.mazhab.activity.BlogDetailsActivity;
import com.akamadani.mazhab.model.Blog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BlogRecyclerViewAdapter extends RecyclerView.Adapter<BlogRecyclerViewAdapter.ViewHolder> {

    private List<Blog> mBlogs;
    private Context mContext;
    private Blog blog;

    public BlogRecyclerViewAdapter(Context context, List<Blog> blogs) {
        this.mContext = context;
        this.mBlogs = blogs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_blog_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        blog = mBlogs.get(position);

        holder.title.setText(blog.getName());
        Picasso
                .get()
                .load(blog.getImage())
                .error(R.drawable.category_default)
                .into(holder.image);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, BlogDetailsActivity.class);
                intent.putExtra("recipe", mBlogs.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBlogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public RelativeLayout relativeLayout;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.news_title);
            image = view.findViewById(R.id.news_image);
            relativeLayout = view.findViewById(R.id.relativeLayout);
        }
    }
}
