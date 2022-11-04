package com.example.muslim_everyday

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.muslim_everyday.databinding.SettingsMenuBinding
import com.example.muslim_everyday.service.AzanNotificationService
import com.example.muslim_everyday.service.AzkarNotificationService
import com.example.muslim_everyday.service.TasbihNotificationService

class SettingsMenu : AppCompatActivity() {
    private var binding: SettingsMenuBinding? = null
    private var isAzanEnabled = false
    private var isTasbihEnabled = false
    private var isAzkarEnabled = false

    @SuppressLint("MissingInflatedId", "UseSwitchCompatOrMaterialCode")
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_menu)
        binding = SettingsMenuBinding.bind(findViewById(R.id.rootSettingsMenu))

        val sharedPref = getSharedPreferences("Notifications", Context.MODE_PRIVATE) ?: return
        val onButtonClickAnim = AnimationUtils.loadAnimation(this@SettingsMenu, R.anim.on_button_click)

        binding?.apply {
            swAzan.isClickable = false
            swTasbih.isClickable = false
            swAzkar.isClickable = false

            isAzanEnabled = sharedPref.getBoolean("isAzanEnabled?", false)
            swAzan.isChecked = sharedPref.getBoolean("isAzanEnabled?", false)

            isTasbihEnabled = sharedPref.getBoolean("isTasbihEnabled?", false)
            swTasbih.isChecked = sharedPref.getBoolean("isTasbihEnabled?", false)

            isAzkarEnabled = sharedPref.getBoolean("isAzkarEnabled?", false)
            swAzkar.isChecked = sharedPref.getBoolean("isAzkarEnabled?", false)

            btnBack.setOnClickListener {
                onBackPressed()
            }

            rlAzan.setOnClickListener {
                isAzanEnabled = !isAzanEnabled

                with (sharedPref.edit()) {
                    putBoolean("isAzanEnabled?", isAzanEnabled)
                    apply()
                }
                rlAzan.startAnimation(onButtonClickAnim)

                swAzan.isChecked = !swAzan.isChecked

                Intent(this@SettingsMenu, AzanNotificationService::class.java).also {
                    startService(it)
                }
            }

            rlTasbih.setOnClickListener {
                isTasbihEnabled = !isTasbihEnabled

                with (sharedPref.edit()) {
                    putBoolean("isTasbihEnabled?", isTasbihEnabled)
                    apply()
                }
                rlTasbih.startAnimation(onButtonClickAnim)

                swTasbih.isChecked = !swTasbih.isChecked

                Intent(this@SettingsMenu, TasbihNotificationService::class.java).also {
                    startService(it)
                }
            }

            rlAzkar.setOnClickListener {
                isAzkarEnabled = !isAzkarEnabled

                with (sharedPref.edit()) {
                    putBoolean("isAzkarEnabled?", isAzkarEnabled)
                    apply()
                }
                rlAzkar.startAnimation(onButtonClickAnim)

                swAzkar.isChecked = !swAzkar.isChecked

                Intent(this@SettingsMenu, AzkarNotificationService::class.java).also {
                    startService(it)
                }
            }
        }
    }
}