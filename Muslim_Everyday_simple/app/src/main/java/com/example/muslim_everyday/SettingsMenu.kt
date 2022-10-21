package com.example.muslim_everyday

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.data_class.SettingsList
import com.example.muslim_everyday.databinding.SettingsMenuBinding
import com.example.muslim_everyday.recycler_view.SettingsMenuRVAdapter
import com.example.muslim_everyday.service.NotificationService

class SettingsMenu : AppCompatActivity() {
    private var binding: SettingsMenuBinding? = null
    private var isNotificationEnabled = false

    // Initialize list of settings that contain in this activity
        private val settingsList = SettingsList.getSettingsList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_menu)
        binding = SettingsMenuBinding.inflate(layoutInflater)

        // Initialize recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SettingsMenuRVAdapter(settingsList, this) {
            isNotificationEnabled = it

            Intent(this, NotificationService::class.java).also { intent ->
                startService(intent)
            }
        }
    } // SHARED PREFERENCES
}