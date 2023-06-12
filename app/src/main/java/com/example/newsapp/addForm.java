package com.example.newsapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.domain.News;
import com.example.newsapp.receiver.MyReceiver;
import com.example.newsapp.util.NewsOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class addForm extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private EditText source;
    private Button addNews;
    private NewsOpenHelper helper;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);


        title = findViewById(R.id.et_add_title);
        content = findViewById(R.id.et_add_content);
        source = findViewById(R.id.et_add_source);
        addNews = findViewById(R.id.btn_addNews);
        addNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //从数据库获取资源
                //1.创建/打开数据库，数据库名为NewsTable.db
                if (helper == null) {
                    helper = NewsOpenHelper.getInstance(getApplication());
                    helper.openReadLink();
                    helper.openWriteLink();
                }
                //时间
                String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                int imageId = R.drawable.ic_launcher_foreground;
                //黑色
                int blackColor = getResources().getColor(R.color.bg_black);
                News news = new News(title.getText().toString(), content.getText().toString(), source.getText().toString(), time, imageId, blackColor);
                if (helper.insert(news) > 0) {
                    //发送自定义广播
                    IntentFilter intentFilter = new IntentFilter("addNews");
                    myReceiver = new MyReceiver();
                    Intent intent = new Intent("addNews");
                    //直接发送广播
                    intent.putExtra("newsInfo", "今天有新的新闻，请查看");
                    registerReceiver(myReceiver, intentFilter);
                    sendBroadcast(intent);
                    Log.d(TAG, "添加数据:" + news);

                    Intent intentSuccess = new Intent(addForm.this, MainActivity.class);
                    startActivity(intentSuccess);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        unregisterReceiver(myReceiver);
    }
}