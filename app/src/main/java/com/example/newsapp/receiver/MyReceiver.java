package com.example.newsapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("cc", "收到自定义广播");
        String customValue = intent.getStringExtra("extraKey");
        //收到指定广播
        Log.d("cc", customValue);
    }
}
