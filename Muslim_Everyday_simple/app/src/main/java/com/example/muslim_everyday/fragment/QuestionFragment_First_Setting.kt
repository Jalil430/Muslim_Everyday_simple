package com.example.muslim_everyday.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.muslim_everyday.R
import com.example.muslim_everyday.databinding.FragmentQuestionFirstSettingBinding

class QuestionFragment_First_Setting : Fragment() {
    private lateinit var binding : FragmentQuestionFirstSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestionFirstSettingBinding.inflate(inflater, container, false)

        binding.btnYes.setOnClickListener {
//            it.findNavController().navigate(R.id.action_questionFragment_First_Setting_to_blAnswerFragment_First_Setting)
            Toast.makeText(activity, "BtnClick", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }
}