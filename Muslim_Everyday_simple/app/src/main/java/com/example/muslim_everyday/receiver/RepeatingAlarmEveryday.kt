package com.example.muslim_everyday.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.muslim_everyday.util.NotificationUtils

class RepeatingAlarmEveryday : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        NotificationUtils.enableNotification(context!!)
    }
}