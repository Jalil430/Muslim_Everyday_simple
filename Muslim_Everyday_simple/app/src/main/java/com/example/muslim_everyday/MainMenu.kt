package com.example.muslim_everyday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.muslim_everyday.databinding.MainMenuBinding

class MainMenu : AppCompatActivity() {
    private var binding: MainMenuBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        binding = MainMenuBinding.inflate(layoutInflater)

    }
}