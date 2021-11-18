package com.akamadani.mazhab.activity;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akamadani.mazhab.R;
import com.akamadani.mazhab.dbhelper.FavouriteDbHelper;
import com.akamadani.mazhab.model.Blog;
import com.squareup.picasso.Picasso;

public class BlogDetailsActivity extends AppCompatActivity {

    private TextView textViewDescription, textViewDirection;
    private ImageView image;
    private Blog blog;
    private FloatingActionButton floatingActionButton;
    private FavouriteDbHelper favouriteDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);

        favouriteDbHelper = new FavouriteDbHelper(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        if (getIntent().getExtras().containsKey("recipe")) {
            blog = (Blog) getIntent().getExtras().getSerializable("recipe");
            //Toast.makeText(this, recipe.getName(), Toast.LENGTH_LONG).show();
        }

        image = findViewById(R.id.image);
        Picasso
                .get()
                .load(blog.getImage())
                .into(image);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        if (favouriteDbHelper.checkIfExists(blog)) {
            floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorited));
        }

        textViewDescription = findViewById(R.id.textViewDescription);
        textViewDirection = findViewById(R.id.textViewDirection);
        textViewDescription.setText(Html.fromHtml(blog.getIngredients()));
        textViewDirection.setText(Html.fromHtml(blog.getDirection()));

        CollapsingToolbarLayout collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setTitle(blog.getName());

        AppBarLayout mAppBarLayout = findViewById(R.id.appbar);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                } else if (isShow) {
                    isShow = false;
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favouriteDbHelper.addData(blog)) {
                    floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorited));
                    Toast.makeText(getApplicationContext(), "Added to favourites", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
