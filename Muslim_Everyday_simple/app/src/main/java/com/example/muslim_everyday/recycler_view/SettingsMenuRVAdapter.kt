package com.example.muslim_everyday.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.databinding.StringItemBinding

class SettingsMenuRVAdapter : RecyclerView.Adapter<MyViewHolder>() {
    private lateinit var binding : StringItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = StringItemBinding.inflate(LayoutInflater.from(parent.context))

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 6
    }
}

class MyViewHolder(private val binding: StringItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {

    }
}