package com.example.muslim_everyday

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.muslim_everyday.databinding.SettingsMenuBinding
import com.example.muslim_everyday.service.NotificationService

class SettingsMenu : AppCompatActivity() {
    private var binding: SettingsMenuBinding? = null
    private var isNotificationEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_menu)
        binding = SettingsMenuBinding.inflate(LayoutInflater.from(this))

        val sharedPref = getSharedPreferences("Notifications", Context.MODE_PRIVATE) ?: return

        val swAzan = findViewById<Switch>(R.id.swAzan)
        swAzan.setOnClickListener {
            if (swAzan.isChecked) {
                isNotificationEnabled = true
                with(sharedPref.edit()) {
                    this.putBoolean("isNotificationEnabled", isNotificationEnabled)
                    apply()
                }

                Intent(this@SettingsMenu, NotificationService::class.java).also { intent ->
                    startService(intent)
                }
            } else {
                isNotificationEnabled = false
                with(sharedPref.edit()) {
                    this.putBoolean("isNotificationEnabled", isNotificationEnabled)
                    apply()
                }

                Intent(this@SettingsMenu, NotificationService::class.java).also { intent ->
                    startService(intent)
                }
            }
        }
    }
}