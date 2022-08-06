package com.example.muslim_everyday.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.data_class.Question
import com.example.muslim_everyday.databinding.FirstSettingRvBinding

class FirstSettingRVAdapter(private val questionsList: List<Question>, private val textView: TextView?) : RecyclerView.Adapter<MyViewHolder>() {
    private lateinit var binding: FirstSettingRvBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = FirstSettingRvBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding, textView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val question = questionsList[position]
        holder.bind(question)
    }

    override fun getItemCount(): Int {
        return questionsList.size
    }
}

class MyViewHolder(private val binding: FirstSettingRvBinding, private val textView: TextView?) : RecyclerView.ViewHolder(binding.root) {
    fun bind(questions: Question) {

    }
}
