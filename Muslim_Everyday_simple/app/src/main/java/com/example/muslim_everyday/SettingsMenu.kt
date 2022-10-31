package com.example.muslim_everyday

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.muslim_everyday.databinding.SettingsMenuBinding
import com.example.muslim_everyday.service.NotificationService

class SettingsMenu : AppCompatActivity() {
    private lateinit var binding: SettingsMenuBinding
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

        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }

            onClickRelativeLayoutListener(rlAzan) {
                if (it) {
                    isAzanEnabled = true
                    with(sharedPref.edit()) {
                        this.putBoolean("isAzanEnabled", isAzanEnabled)
                        apply()
                    }

                    Intent(this@SettingsMenu, NotificationService::class.java).also { intent ->
                        startService(intent)
                    }

                    swAzan.isChecked = true
                } else {
                    isAzanEnabled = false
                    with(sharedPref.edit()) {
                        this.putBoolean("isAzanEnabled", isAzanEnabled)
                        apply()
                    }

                    Intent(this@SettingsMenu, NotificationService::class.java).also { intent ->
                        startService(intent)
                    }

                    swAzan.isChecked = false
                }
            }
            onClickRelativeLayoutListener(rlTasbih) {
                if (it) {
                    isTasbihEnabled = true
                    with(sharedPref.edit()) {
                        this.putBoolean("isTasbihEnabled", isAzanEnabled)
                        apply()
                    }

                    Intent(this@SettingsMenu, NotificationService::class.java).also { intent ->
                        startService(intent)
                    }

                    swTasbih.isChecked = true
                } else {
                    isTasbihEnabled = false
                    with(sharedPref.edit()) {
                        this.putBoolean("isTasbihEnabled", isAzanEnabled)
                        apply()
                    }

                    Intent(this@SettingsMenu, NotificationService::class.java).also { intent ->
                        startService(intent)
                    }

                    swTasbih.isChecked = false
                }
            }
            onClickRelativeLayoutListener(rlTasbih) {
                if (it) {
                    isAzkarEnabled = true
                    with(sharedPref.edit()) {
                        this.putBoolean("isAzkarEnabled", isAzanEnabled)
                        apply()
                    }

                    Intent(this@SettingsMenu, NotificationService::class.java).also { intent ->
                        startService(intent)
                    }

                    swAzkar.isChecked = true
                } else {
                    isAzkarEnabled = false
                    with(sharedPref.edit()) {
                        this.putBoolean("isAzkarEnabled", isAzanEnabled)
                        apply()
                    }

                    Intent(this@SettingsMenu, NotificationService::class.java).also { intent ->
                        startService(intent)
                    }

                    swAzkar.isChecked = false
                }
            }
        }
    }

    private fun onClickRelativeLayoutListener(view: RelativeLayout, callback: (Boolean) -> Unit) {
        var isChecked = false
        val onButtonClickAnim = AnimationUtils.loadAnimation(this, R.anim.on_button_click)

        view.setOnClickListener {
            isChecked = !isChecked
            it.startAnimation(onButtonClickAnim)

            callback.invoke(isChecked)
        }
    }
}