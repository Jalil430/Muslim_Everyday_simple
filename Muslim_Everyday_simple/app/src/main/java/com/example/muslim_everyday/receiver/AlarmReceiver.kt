package com.example.muslim_everyday.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.muslim_everyday.SettingsMenu
import com.example.muslim_everyday.view_model.ViewModel_rv

class AlarmReceiver() : BroadcastReceiver(), ViewModelStoreOwner {
    private var viewModel = ViewModelProvider(this)[ViewModel_rv::class.java]
    private val settingsMenuRef: SettingsMenu = SettingsMenu()

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Win!!!", Toast.LENGTH_LONG).show()
//        settingsMenuRef.setNotification()
//        settingsMenuRef.createNotificationChannel()
//        settingsMenuRef.startFajrNotification()
    }

    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }
}