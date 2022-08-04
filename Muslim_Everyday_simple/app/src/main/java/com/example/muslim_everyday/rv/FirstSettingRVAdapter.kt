package com.example.muslim_everyday.rv

import android.annotation.SuppressLint
import android.graphics.Color.YELLOW
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.R
import com.example.muslim_everyday.databinding.FirstSettingRvBinding

class FirstSettingRVAdapter(private val questionsList: List<Question>) : RecyclerView.Adapter<MyViewHolder>() {
    private lateinit var binding : FirstSettingRvBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = FirstSettingRvBinding.inflate(LayoutInflater.from(parent.context))

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val question = questionsList[position]
        holder.bind(question)
    }

    override fun getItemCount(): Int {
        return questionsList.size
    }
}

class MyViewHolder(private val binding : FirstSettingRvBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(questions: Question) {
        binding.apply {
            tvQuestion.text = questions.q
            btnYes.setOnClickListener {
                btnYes.isClickable = false
                btnYes.isVisible = false
                btnNo.isClickable = false
                btnNo.isVisible = false
                Log.i("MyTag", "disndsunf")
            }
        }
    }
}
