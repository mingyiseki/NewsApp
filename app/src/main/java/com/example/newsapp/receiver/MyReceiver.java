package com.example.newsapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String customValue = intent.getStringExtra("extraKey");
        //收到指定广播
        Log.d("cc", customValue);
    }
}
