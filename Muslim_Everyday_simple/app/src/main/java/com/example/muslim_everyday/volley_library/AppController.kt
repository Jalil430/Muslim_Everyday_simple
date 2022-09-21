package com.example.muslim_everyday.volley_library

import android.app.Application

class AppController : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: AppController
            private set
    }
}