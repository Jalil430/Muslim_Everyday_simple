package com.example.muslim_everyday

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        val btnBtn = findViewById<Button>(R.id.button2)
        btnBtn.setOnClickListener {
            startActivity(Intent(this, SettingsMenu::class.java))
        }
    }
}
