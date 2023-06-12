package com.example.newsapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: 收到广播");
        if ("addNews".equals(intent.getAction())) {
            String newsInfo = intent.getStringExtra("newsInfo");
            Toast.makeText(context, newsInfo, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "添加新闻成功,广播: " + newsInfo);
        }
        if ("download".equals(intent.getAction())) {
            String downPath = intent.getStringExtra("downPath");
            Toast.makeText(context, downPath, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "下载成功,广播: " + downPath);
        }
    }
}
