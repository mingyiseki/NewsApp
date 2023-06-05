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
        Toast.makeText(context, customValue, Toast.LENGTH_SHORT).show();
    }
}
