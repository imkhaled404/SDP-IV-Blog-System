package com.akamadani.mazhab.activity;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.akamadani.mazhab.R;
import com.akamadani.mazhab.adapter.BlogRecyclerViewAdapter;
import com.akamadani.mazhab.model.Blog;

import java.util.ArrayList;
import java.util.List;

public class BlogsByCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BlogRecyclerViewAdapter blogRecyclerViewAdapter;
    private RelativeLayout relativeLayout;
    List<Blog> blogs = new ArrayList<Blog>();
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private String category, categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs_by_category);
        if (getIntent().getExtras().containsKey("category")) {
            category = getIntent().getExtras().getString("category");
        }
        if (getIntent().getExtras().containsKey("category_name")) {
            categoryName = getIntent().getExtras().getString("category_name");
        }
        initToolbar();
        progressDialog = new ProgressDialog(this);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("categories").child(category);

        getRecipes();

        initRecyclerView();
    }

    private void getRecipes() {
        blogs.clear();
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot1 : dataSnapshot.child("posts").getChildren()) {
                        Blog blog = new Blog(snapshot1.getKey(), snapshot1.child("name").getValue().toString(),snapshot1.child("details").getValue().toString(),
                                snapshot1.child("keypoint").getValue().toString(), snapshot1.child("image").getValue().toString());

                        blogs.add(blog);
                    }


                blogRecyclerViewAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(BlogsByCategoryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new GridLayoutManager(BlogsByCategoryActivity.this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        blogRecyclerViewAdapter = new BlogRecyclerViewAdapter(BlogsByCategoryActivity.this, blogs);
        recyclerView.setAdapter(blogRecyclerViewAdapter);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Category: " + categoryName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
