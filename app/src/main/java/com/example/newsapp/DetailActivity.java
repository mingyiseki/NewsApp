package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.domain.News;
import com.example.newsapp.util.TimeUtil;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private News news;
    private Intent intent;
    private Date beginTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView title = findViewById(R.id.tv_detail_title);
        TextView from = findViewById(R.id.tv_detail_from);
        TextView time = findViewById(R.id.tv_detail_time);
        TextView content = findViewById(R.id.tv_detail_content);
        intent = getIntent();
        //接收news对象对象
        news = (News) intent.getSerializableExtra("news");

        title.setText(news.getTitle());
        from.setText(news.getFrom());
        time.setText(news.getTime());
        content.setText(news.getContent());

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("", "start");
        beginTime = new Date();
    }

    @Override
    public void onBackPressed() {
        boolean isOverTime = new TimeUtil().isOverTime(beginTime, 30);
        int second = new TimeUtil().getSecond(beginTime);
        intent.setClass(this, MainActivity.class);
        intent.putExtra("isOverTime", isOverTime);
        intent.putExtra("news",news);
        setResult(1, intent);
        DetailActivity.this.finish();
        Log.v("发送", "" + isOverTime + second);
    }

}