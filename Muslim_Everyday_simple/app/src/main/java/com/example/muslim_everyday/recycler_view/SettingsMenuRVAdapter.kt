package com.example.muslim_everyday.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.data_class.Settings
import com.example.muslim_everyday.databinding.StringItemBinding

class SettingsMenuRVAdapter(private val settingsList: List<Settings>,
                            private val context: Context,
                            private val callback: (isNotificationEnabled: Boolean) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {

    private lateinit var binding : StringItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = StringItemBinding.inflate(LayoutInflater.from(parent.context))

        return MyViewHolder(binding, context, callback)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val settings = settingsList[position]
        holder.bind(settings)
    }

    override fun getItemCount(): Int {
        return settingsList.size
    }
}

class MyViewHolder(private val binding: StringItemBinding,
                            private val context: Context,
                            private val callback: (isNotificationEnabled: Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    private var isNotificationEnabled: Boolean = false

    fun bind(setting: Settings) {
        binding.apply {
            tvSetting.text = setting.s

            val sharedPref = context.getSharedPreferences("Notifications", Context.MODE_PRIVATE) ?: return

            switch1.setOnClickListener {
                if (switch1.isChecked) {
                    when (adapterPosition) {
                        0 -> {
                            isNotificationEnabled = true
                            with(sharedPref.edit()) {
                                this.putBoolean("isNotificationEnabled", isNotificationEnabled)
                                apply()
                            }
                            callback.invoke(isNotificationEnabled)
                        }
                    }
                } else {
                    when (adapterPosition) {
                        0 -> {
                            isNotificationEnabled = false
                            with(sharedPref.edit()) {
                                this.putBoolean("isNotificationEnabled", isNotificationEnabled)
                                apply()
                            }
                            callback.invoke(isNotificationEnabled)
                        }
                    }
                }
            }
        }
    }
}