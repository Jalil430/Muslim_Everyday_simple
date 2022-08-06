package com.example.muslim_everyday.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.muslim_everyday.ViewModel_rv
import com.example.muslim_everyday.databinding.FragmentQuestionFirstSettingBinding

class QuestionFragment_FirstSetting : Fragment() {
    private lateinit var binding: FragmentQuestionFirstSettingBinding
    private val viewModel: ViewModel_rv by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionFirstSettingBinding.inflate(inflater, container, false)

        binding.btnYes1.setOnClickListener {
            Log.i("MyTag", "Click")
        }
        viewModel.textView.value = binding.tvQuestion

        return binding.root
    }
}