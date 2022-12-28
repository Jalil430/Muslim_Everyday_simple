package com.example.muslim_everyday.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ViewModelNotifications : ViewModel() {
    val isNotificationEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}