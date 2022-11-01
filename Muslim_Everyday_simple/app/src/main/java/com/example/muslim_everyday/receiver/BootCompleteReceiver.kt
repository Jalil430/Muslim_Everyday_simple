package com.example.muslim_everyday.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.muslim_everyday.service.AzanNotificationService

@Suppress("NAME_SHADOWING")
class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            Intent(context, AzanNotificationService::class.java).also { intent ->
                context?.startService(intent)
            }
        }
    }
}