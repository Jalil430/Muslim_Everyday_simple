package com.example.muslim_everyday

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.data_class.QuestionsList
import com.example.muslim_everyday.databinding.FirstSettingBinding
import com.example.muslim_everyday.receiver.BootCompleteReceiver
import com.example.muslim_everyday.receiver.RepeatingAlarmEveryday
import com.example.muslim_everyday.recycler_view.FirstSettingRVAdapter
import com.example.muslim_everyday.util.Utils
import java.util.*

class FirstSetting : AppCompatActivity() {
    // Initialize list of questions this activity gives
    private val questionsList = QuestionsList.getQuestionsList()
    private var binding: FirstSettingBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_setting)
        binding = FirstSettingBinding.bind(findViewById(R.id.rootFirstSetting))

        binding?.apply {
            enableNotificationAfterRebootingDevice()
            setRepeatingAlarm()

            button.setOnClickListener {
                val intent = Intent(this@FirstSetting, MainMenu::class.java)
                startActivity(intent)
                finish()
            }

            recyclerView.layoutManager = LinearLayoutManager(this@FirstSetting)
            recyclerView.adapter = FirstSettingRVAdapter(questionsList)

            btnNext.setOnClickListener {
                val intent = Intent(this@FirstSetting, SettingsMenu::class.java)
                startActivity(intent)
                finish()
            }
        }
//        val preferences = getPreferences(MODE_PRIVATE)
//        val hasVisited = preferences.getBoolean(HAS_VISITED, false)

//        if (hasVisited) {
//            val intent = Intent(this, SettingsMenu::class.java)
//            startActivity(intent)
//            finish()
//        } else {
//            val e = preferences.edit()
//            e.putBoolean(HAS_VISITED, true)
//            e.apply()


//        }
    }

    private fun enableNotificationAfterRebootingDevice() {
        val receiver = ComponentName(applicationContext, BootCompleteReceiver::class.java)

        applicationContext.packageManager?.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun setRepeatingAlarm() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 1)

        val intent = Intent(this, RepeatingAlarmEveryday::class.java)

        val repeatingPendingIntent = PendingIntent.getBroadcast(
            this,
            Utils.getRandomInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val repeatingAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        repeatingAlarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            repeatingPendingIntent
        )
    }

    companion object {
        const val HAS_VISITED = "hasVisited"
    }
}