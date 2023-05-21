package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView title = findViewById(R.id.tv_detail_title);
        TextView from = findViewById(R.id.tv_detail_from);
        TextView time = findViewById(R.id.tv_detail_time);
        TextView content = findViewById(R.id.tv_detail_content);
        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        from.setText(intent.getStringExtra("from"));
        time.setText(intent.getStringExtra("time"));
        content.setText(intent.getStringExtra("content"));

    }
}