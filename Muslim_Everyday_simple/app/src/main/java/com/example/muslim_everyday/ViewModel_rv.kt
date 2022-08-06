package com.example.muslim_everyday

import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ViewModel_rv : ViewModel() {
    val textView: MutableLiveData<TextView> by lazy {
        MutableLiveData<TextView>()
    }

}