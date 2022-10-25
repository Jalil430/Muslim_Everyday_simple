package com.example.muslim_everyday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        val adapter = MyViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
    }
}
