package com.example.muslim_everyday.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.InitializerViewModelFactoryBuilder
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.muslim_everyday.service.NotificationService
import com.example.muslim_everyday.view_model.ViewModelNotifications
import kotlin.reflect.KProperty

@Suppress("NAME_SHADOWING")
class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            Intent(context, NotificationService::class.java).also { intent ->
                context?.startService(intent)
            }
        }
    }
}