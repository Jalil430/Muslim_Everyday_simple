package com.example.muslim_everyday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.data_class.Settings
import com.example.muslim_everyday.databinding.SettingsMenuBinding
import com.example.muslim_everyday.recycler_view.SettingsMenuRVAdapter

class SettingsMenu : AppCompatActivity() {
    private lateinit var binding: SettingsMenuBinding
    private val settingsList = listOf<Settings> (
        Settings("Уведомлять об утреннем намазе")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_menu)

        binding = SettingsMenuBinding.inflate(layoutInflater)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SettingsMenuRVAdapter(settingsList)
    }
}