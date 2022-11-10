package com.example.muslim_everyday

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.muslim_everyday.databinding.MainMenuBinding
import com.example.muslim_everyday.fragment.FirstOpenDialog

class MainMenu : AppCompatActivity() {
    private var binding: MainMenuBinding? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        binding = MainMenuBinding.bind(findViewById(R.id.rootMainMenu))

        openDialog()
    }

    @Suppress("DEPRECATION")
    private fun openDialog() {
        Handler().postDelayed({
            val firstOpenDialog = FirstOpenDialog()
            firstOpenDialog.show(supportFragmentManager, "first open dialog")
        }, 500)
    }
}
