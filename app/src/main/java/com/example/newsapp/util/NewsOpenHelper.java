package com.example.newsapp.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.newsapp.domain.News;

import java.util.ArrayList;
import java.util.List;

public class NewsOpenHelper extends SQLiteOpenHelper {

    //定义表,表名：NewsTable
    public static final String CREATE_NEWS = "create table if not exists NewsTable(" +
            "id integer primary key autoincrement," +
            "title text," + "content text," + "source text," +
            "time text," + "image text," + "is_read text);";
    private static final String DB_NAME = "main.db";
    private static final String TABLE_NAME = "NewsTable";
    private static final int VERSION = 2;
    private static NewsOpenHelper helper;
    private SQLiteDatabase mRDB;
    private SQLiteDatabase mWDB;

    public NewsOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }


    public long insert(News news) {
        ContentValues values = new ContentValues();
        values.put("image", news.getImage());
        values.put("title", news.getTitle());
        values.put("content", news.getContent());
        values.put("source", news.getSource());
        values.put("time", news.getTime());
        values.put("source", news.getSource());
        values.put("is_read", news.getIsRead());
        return mWDB.insert(TABLE_NAME, null, values);
    }

    public long updateById(News news) {
        ContentValues values = new ContentValues();
        values.put("id", news.getId());
        values.put("image", news.getImage());
        values.put("title", news.getTitle());
        values.put("content", news.getContent());
        values.put("source", news.getSource());
        values.put("time", news.getTime());
        values.put("source", news.getSource());
        values.put("is_read", news.getIsRead());
        return mWDB.update(TABLE_NAME, values, "id = ?", new String[]{String.valueOf(news.getId())});
    }

    public long deleteById(int id) {
        return mWDB.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
    }

    public List<News> queryAll() {
        List<News> list = new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            News news = new News();
            news.setId(cursor.getLong(0));
            news.setTitle(cursor.getString(1));
            news.setContent(cursor.getString(2));
            news.setSource(cursor.getString(3));
            news.setTime(cursor.getString(4));
            news.setImage(cursor.getInt(5));
            news.setIsRead(cursor.getInt(6));
            list.add(news);
        }
        return list;
    }

    public News queryById(long id) {
        Cursor cursor = mRDB.query(TABLE_NAME, null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);

        News news = new News();
        while (cursor.moveToNext()){
            news.setId(cursor.getLong(0));
            news.setTitle(cursor.getString(1));
            news.setContent(cursor.getString(2));
            news.setSource(cursor.getString(3));
            news.setTime(cursor.getString(4));
            news.setImage(cursor.getInt(5));
            news.setIsRead(cursor.getInt(6));
        }
        return news;
    }

    public NewsOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static NewsOpenHelper getInstance(Context context) {
        if (helper == null) {
            helper = new NewsOpenHelper(context);
        }
        return helper;
    }

    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = helper.getReadableDatabase();
        }
        return mRDB;
    }

    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = helper.getWritableDatabase();
        }
        return mWDB;
    }

    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mRDB.close();
            mRDB = null;
        }
        if (mWDB != null && mWDB.isOpen()) {
            mWDB.close();
            mWDB = null;
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NEWS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


}
