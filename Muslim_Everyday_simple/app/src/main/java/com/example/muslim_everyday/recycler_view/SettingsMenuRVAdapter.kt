package com.example.muslim_everyday.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.data_class.Settings
import com.example.muslim_everyday.databinding.StringItemBinding

class SettingsMenuRVAdapter(private val settingsList: List<Settings>) : RecyclerView.Adapter<MyViewHolder>() {
    private lateinit var binding : StringItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = StringItemBinding.inflate(LayoutInflater.from(parent.context))

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val settings = settingsList[position]
        holder.bind(settings)
    }

    override fun getItemCount(): Int {
        return settingsList.size
    }
}

class MyViewHolder(private val binding: StringItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(setting: Settings) {
        binding.apply {
            tvSetting.text = setting.s
//            switch1.setOnClickListener {
//                if(switch1.isChecked) {
//                    when(adapterPosition) {
//                        1 ->
//                    }
//                }else {
//
//                }
//            }
        }
    }
}
