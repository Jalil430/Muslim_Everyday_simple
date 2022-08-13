package com.example.muslim_everyday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muslim_everyday.databinding.SettingsMenuBinding
import com.example.muslim_everyday.recycler_view.SettingsMenuRVAdapter

class SettingsMenu : AppCompatActivity() {
    private lateinit var binding: SettingsMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SettingsMenuBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = SettingsMenuRVAdapter()
    }
}