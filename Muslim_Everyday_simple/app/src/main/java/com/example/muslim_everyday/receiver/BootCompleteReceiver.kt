package com.example.muslim_everyday.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.muslim_everyday.util.NotificationUtils


class BootCompleteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            NotificationUtils.enableNotification(context!!)
        }
    }
}