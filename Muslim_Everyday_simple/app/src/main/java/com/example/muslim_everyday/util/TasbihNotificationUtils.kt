@file:Suppress("DEPRECATION")

package com.example.muslim_everyday.util

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.muslim_everyday.receiver.TasbihNotificationReceiver
import com.example.muslim_everyday.util.AzanNotificationUtils.date
import com.example.muslim_everyday.util.AzanNotificationUtils.mAsr
import com.example.muslim_everyday.util.AzanNotificationUtils.mDhuhr
import com.example.muslim_everyday.util.AzanNotificationUtils.mFajr
import com.example.muslim_everyday.util.AzanNotificationUtils.mIsha
import com.example.muslim_everyday.util.AzanNotificationUtils.mMaghrib
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.text.SimpleDateFormat
import java.util.*

object TasbihNotificationUtils {
        private var dateFormat : SimpleDateFormat? = null
        private var fajrDate : Date? = null
        private var dhuhrDate : Date? = null
        private var asrDate : Date? = null
        private var maghribDate : Date? = null
        private var ishaDate : Date? = null

    // Notification
        private var calendar: Calendar? = null

    fun enableNotification(context: Context) {
        DataTask().execute()
        Handler().postDelayed({
            setCalendar(context)

            val intent = Intent(context, TasbihNotificationReceiver::class.java).apply {
                putExtra(Constants.PRAYER_TIME_NOW, "Tasbih")
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                Utils.getRandomInt(),
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

            if (System.currentTimeMillis() < calendar!!.timeInMillis) {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar!!.timeInMillis,
                    pendingIntent
                )
            }
        }, 1500)
    }

    private fun setCalendar(context: Context) {
        convertStrings()
        calendar = Calendar.getInstance()

        val fajrTime = Calendar.getInstance()
        fajrTime.time = fajrDate!!

        val dhuhrTime = Calendar.getInstance()
        dhuhrTime.time = dhuhrDate!!

        val asrTime = Calendar.getInstance()
        asrTime.time = asrDate!!

        val maghribTime = Calendar.getInstance()
        maghribTime.time = maghribDate!!

        val ishaTime = Calendar.getInstance()
        ishaTime.time = ishaDate!!

        if (System.currentTimeMillis() < fajrTime.timeInMillis + tenMin) {
            calendar?.timeInMillis = fajrTime.timeInMillis + tenMin
            Toast.makeText(context, "Tasbih - ${fajrTime.time}", Toast.LENGTH_LONG).show()
        }
        if (System.currentTimeMillis() < dhuhrTime.timeInMillis + tenMin && System.currentTimeMillis() > fajrTime.timeInMillis + tenMin) {
            calendar?.timeInMillis = dhuhrTime.timeInMillis + tenMin
            Toast.makeText(context, "Tasbih - ${dhuhrTime.time}", Toast.LENGTH_LONG).show()
        }
        if (System.currentTimeMillis() < asrTime.timeInMillis + tenMin && System.currentTimeMillis() > dhuhrTime.timeInMillis + tenMin) {
            calendar?.timeInMillis = asrTime.timeInMillis + tenMin
            Toast.makeText(context, "Tasbih - ${asrTime.time}", Toast.LENGTH_LONG).show()
        }
        if (System.currentTimeMillis() < maghribTime.timeInMillis + tenMin && System.currentTimeMillis() > asrTime.timeInMillis + tenMin) {
            calendar?.timeInMillis = maghribTime.timeInMillis + tenMin
            Toast.makeText(context, "Tasbih - ${maghribTime.time}", Toast.LENGTH_LONG).show()
        }
        if (System.currentTimeMillis() < ishaTime.timeInMillis + tenMin && System.currentTimeMillis() > maghribTime.timeInMillis + tenMin) {
            calendar?.timeInMillis = ishaTime.timeInMillis + tenMin
            Toast.makeText(context, "Tasbih - ${ishaTime.time}", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertStrings() {
        val date12Format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        val date24Format = SimpleDateFormat("HH:mm:ss")

        val cFajr = date12Format.parse(mFajr!!)?.let { date24Format.format(it) }
        val cDhuhr = date12Format.parse(mDhuhr!!)?.let { date24Format.format(it) }
        val cAsr = date12Format.parse(mAsr!!)?.let { date24Format.format(it) }
        val cMaghrib = date12Format.parse(mMaghrib!!)?.let { date24Format.format(it) }
        val cIsha = date12Format.parse(mIsha!!)?.let { date24Format.format(it) }

        val calendarFajrTime = "$date $cFajr"
        val calendarDhuhrTime = "$date $cDhuhr"
        val calendarAsrTime = "$date $cAsr"
        val calendarMaghribTime = "$date $cMaghrib"
        val calendarIshaTime = "$date $cIsha"

        dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        fajrDate = parseDate(calendarFajrTime)
        dhuhrDate = parseDate(calendarDhuhrTime)
        asrDate = parseDate(calendarAsrTime)
        maghribDate = parseDate(calendarMaghribTime)
        ishaDate = parseDate(calendarIshaTime)
    }

    private fun parseDate(source: String) = dateFormat?.parse(source) as Date

    private const val tenMin = 600000
}