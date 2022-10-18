package com.example.muslim_everyday.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.muslim_everyday.data_class.Question

open class ViewModelNotifications : ViewModel() {
    val isNotificationEnabled: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
}