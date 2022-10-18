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
import com.example.muslim_everyday.receiver.BootCompleteReceiver
import com.example.muslim_everyday.receiver.RepeatingAlarmEveryday
import com.example.muslim_everyday.recycler_view.FirstSettingRVAdapter
import com.example.muslim_everyday.util.Utils
import java.util.*


class FirstSetting : AppCompatActivity() {
    // Initialize list of questions this activity gives
    private val questionsList = QuestionsList.getQuestionsList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_setting)

        enableNotificationAfterRebootingDevice()
        setRepeatingAlarm()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FirstSettingRVAdapter(questionsList)

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            val intent = Intent(this, SettingsMenu::class.java)
            startActivity(intent)
        }
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
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val intent = Intent(this, RepeatingAlarmEveryday::class.java)

        val repeatingPendingIntent = PendingIntent.getBroadcast(
            this,
            Utils.getRandomInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val repeatingAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (System.currentTimeMillis() < calendar.timeInMillis) {
            repeatingAlarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                repeatingPendingIntent
            )
        }
    }
}