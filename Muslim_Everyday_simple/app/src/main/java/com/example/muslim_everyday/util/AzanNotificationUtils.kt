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
import com.example.muslim_everyday.receiver.AzanNotificationReceiver
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import java.net.URLConnection
import java.text.SimpleDateFormat
import java.util.*


object AzanNotificationUtils {
    // Prayer timings variables
        var country : String? = null
        var state : String? = null
        var city : String? = null
        var location : String? = null
        var date : String? = null

        var mFajr : String? = null
        var mDhuhr : String? = null
        var mAsr : String? = null
        var mMaghrib : String? = null
        var mIsha : String? = null

        private var dateFormat : SimpleDateFormat? = null
        private var fajrDate : Date? = null
        private var dhuhrDate : Date? = null
        private var asrDate : Date? = null
        private var maghribDate : Date? = null
        private var ishaDate : Date? = null

    // Notification
        private var calendar: Calendar? = null
        private var whichPrayerTimeNow = 1


    fun enableNotification(context: Context) {
        DataTask().execute()
        Handler().postDelayed({
            setCalendar(context)

            val intent = Intent(context, AzanNotificationReceiver::class.java).apply {
                putExtra(Constants.PRAYER_TIME_NOW, whichPrayerTimeNow)
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

        if (System.currentTimeMillis() < fajrTime.timeInMillis) {
            calendar?.timeInMillis = fajrTime.timeInMillis
            whichPrayerTimeNow = 1
            Toast.makeText(context, "Fajr - ${fajrTime.time}", Toast.LENGTH_LONG).show()
        }
        if (System.currentTimeMillis() < dhuhrTime.timeInMillis && System.currentTimeMillis() > fajrTime.timeInMillis) {
            calendar?.timeInMillis = dhuhrTime.timeInMillis
            whichPrayerTimeNow = 2
            Toast.makeText(context, "Dhuhr - ${dhuhrTime.time}", Toast.LENGTH_LONG).show()
        }
        if (System.currentTimeMillis() < asrTime.timeInMillis && System.currentTimeMillis() > dhuhrTime.timeInMillis) {
            calendar?.timeInMillis = asrTime.timeInMillis
            whichPrayerTimeNow = 3
            Toast.makeText(context, "Asr - ${asrTime.time}", Toast.LENGTH_LONG).show()
        }
        if (System.currentTimeMillis() < maghribTime.timeInMillis && System.currentTimeMillis() > asrTime.timeInMillis) {
            calendar?.timeInMillis = maghribTime.timeInMillis
            whichPrayerTimeNow = 4
            Toast.makeText(context, "Maghrib - ${maghribTime.time}", Toast.LENGTH_LONG).show()
        }
        if (System.currentTimeMillis() < ishaTime.timeInMillis && System.currentTimeMillis() > maghribTime.timeInMillis) {
            calendar?.timeInMillis = ishaTime.timeInMillis
            whichPrayerTimeNow = 5
            Toast.makeText(context, "Isha - ${ishaTime.time}", Toast.LENGTH_LONG).show()
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
}

@Suppress("DEPRECATION")
class DataTask : AsyncTask<Void?, Void?, JSONObject?>() {
    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Void?): JSONObject? {
        val str = "https://muslimsalat.com/Oyskhara.json?key=084c27252d935e5c202be396026a5adf"
        val urlConn: URLConnection?
        var bufferedReader: BufferedReader? = null
        return try {
            val url = URL(str)
            urlConn = url.openConnection()
            bufferedReader = BufferedReader(InputStreamReader(urlConn.getInputStream()))
            val stringBuffer = StringBuffer()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                stringBuffer.append(line)
            }
            JSONObject(stringBuffer.toString())
        } catch (ex: Exception) {
            Log.e("MyApp", "Ex")
            null
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Log.e("MyApp", "IO")
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(response: JSONObject?) {
        if (response != null) {
            try {
                AzanNotificationUtils.apply {
                    // Get location
                    country = response.get("country").toString()
                    state = response.get("state").toString()
                    city = response.get("city").toString()
                    location = "$country, $state, $city"

                    // get date
                    date = response.getJSONArray("items").getJSONObject(0).get("date_for").toString()

                    // Get namaz timings
                    mFajr = response.getJSONArray("items").getJSONObject(0).get("fajr").toString()
                    mDhuhr = response.getJSONArray("items").getJSONObject(0).get("dhuhr").toString()
                    mAsr = response.getJSONArray("items").getJSONObject(0).get("asr").toString()
                    mMaghrib = response.getJSONArray("items").getJSONObject(0).get("maghrib").toString()
                    mIsha = response.getJSONArray("items").getJSONObject(0).get("isha").toString()
                }
            } catch (ex: JSONException) {
                Log.e("MyApp", "JSON")
            }
        }
    }
}