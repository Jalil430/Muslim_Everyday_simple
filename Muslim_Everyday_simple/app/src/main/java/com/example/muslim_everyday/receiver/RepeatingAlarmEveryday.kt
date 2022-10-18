package com.example.muslim_everyday.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.muslim_everyday.service.NotificationService

@Suppress("NAME_SHADOWING")
class RepeatingAlarmEveryday : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "repeatingAlarm", Toast.LENGTH_LONG).show()
        Intent(context, NotificationService::class.java).also { intent ->
            context?.startService(intent)
        }
    }
}