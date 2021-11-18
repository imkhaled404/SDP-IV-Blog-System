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

public class FavoriteRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecyclerViewAdapter.ViewHolder> {

    private List<Blog> favorites;
    private Context context;
    private Blog favorite;

    public FavoriteRecyclerViewAdapter(Context context, List<Blog> favorites) {
        this.favorites = favorites;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_favorite_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        favorite = favorites.get(position);

        holder.title.setText(favorite.getName());
        Picasso
                .get()
                .load(favorite.getImage())
                .error(R.drawable.cat_default)
                .into(holder.image);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Blog f = favorites.get(position);
                Intent intent = new Intent(context, BlogDetailsActivity.class);
                intent.putExtra("recipe", f);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
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
