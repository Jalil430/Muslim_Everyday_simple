package com.example.muslim_everyday

import androidx.lifecycle.ViewModel
import com.example.muslim_everyday.data_class.Question

open class ViewModel_rv : ViewModel() {
    lateinit var question: List<Question>

}