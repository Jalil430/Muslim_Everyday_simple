package com.example.muslim_everyday.recycler_view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.data_class.Question
import com.example.muslim_everyday.databinding.FirstSettingRvBinding
import com.example.muslim_everyday.databinding.FragmentQuestionFirstSettingBinding

class FirstSettingRVAdapter(private val questionsList: List<Question>) : RecyclerView.Adapter<MyViewHolder>() {
    private lateinit var binding : FragmentQuestionFirstSettingBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = FragmentQuestionFirstSettingBinding.inflate(LayoutInflater.from(parent.context))

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

class MyViewHolder(private val binding : FragmentQuestionFirstSettingBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(questions: Question) {
        binding.cardView.offsetTopAndBottom(100)
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
