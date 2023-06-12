package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.domain.News;
import com.example.newsapp.servise.DownloadService;
import com.example.newsapp.util.NewsOpenHelper;
import com.example.newsapp.util.TimeUtil;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    private News news;
    private Intent intent;
    private Date beginTime;
    private NewsOpenHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView title = findViewById(R.id.tv_detail_title);
        TextView from = findViewById(R.id.tv_detail_from);
        TextView time = findViewById(R.id.tv_detail_time);
        TextView content = findViewById(R.id.tv_detail_content);
        Button download = findViewById(R.id.btn_detail_download);
        Button delete = findViewById(R.id.btn_detail_delete);
        intent = getIntent();
        //接收news对象对象
        news = (News) intent.getSerializableExtra("news");

        title.setText(news.getTitle());
        from.setText(news.getSource());
        time.setText(news.getTime());
        content.setText(news.getContent());

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = news.toString(); // 替换为你要下载的文本
                Intent serviceIntent = new Intent(getApplication(), DownloadService.class);
                serviceIntent.putExtra("text", text);
                serviceIntent.putExtra("title", "新闻" + news.getTitle() + ".txt");
                startService(serviceIntent);

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从数据库获取资源
                //1.创建/打开数据库，数据库名为NewsTable.db
                if (helper == null) {
                    helper = NewsOpenHelper.getInstance(getApplication());
                    helper.openReadLink();
                    helper.openWriteLink();
                }
                helper.deleteById(Math.toIntExact(news.getId()));
                Intent intentSuccess = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intentSuccess);

            }
        });

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
        intent.putExtra("news", news);
        setResult(1, intent);
        DetailActivity.this.finish();
        Log.v("发送", "" + isOverTime + second);
    }

}