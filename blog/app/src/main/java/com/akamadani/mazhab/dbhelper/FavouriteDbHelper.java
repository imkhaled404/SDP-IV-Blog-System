package com.akamadani.mazhab.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.akamadani.mazhab.model.Blog;

import java.util.ArrayList;
import java.util.List;

public class FavouriteDbHelper extends SQLiteOpenHelper {

    private final static String TABLE = "my_posts";
    private Context context;

    public FavouriteDbHelper(Context context) {
        super(context, TABLE, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE + " (id TEXT PRIMARY KEY, name TEXT, keypoint TEXT, details TEXT, image TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public boolean addData(Blog blog) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", blog.getKey());
        contentValues.put("name", blog.getName());
        contentValues.put("keypoint", blog.getDirection());
        contentValues.put("details", blog.getIngredients());
        contentValues.put("image", blog.getImage());

        long result = db.insert(TABLE, null, contentValues);

        return result != -1;
    }

    public List<Blog> getAll() {
        List<Blog> blogs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE;
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String direction = cursor.getString(2);
            String ingredients = cursor.getString(3);
            String image = cursor.getString(4);
            Blog blog = new Blog(id, name, direction, ingredients, image);
            blogs.add(blog);
        }

        cursor.close();

        return blogs;
    }

    public boolean checkIfExists(Blog blog) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE + " WHERE id = " + blog.getKey();
        try {
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.getCount() <= 0) {
                cursor.close();
                return false;
            }

            cursor.close();
        } catch (Exception e) {
            Log.d("FavouriteDbHelper", e.getMessage());
            return false;
        }

        return true;
    }

    public boolean deleteById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TABLE, "id=?", new String[]{id}) > 0;
    }
}
