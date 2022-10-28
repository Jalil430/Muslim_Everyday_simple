package com.example.muslim_everyday.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.Interface.RowType
import com.example.muslim_everyday.data_class.Settings
import com.example.muslim_everyday.databinding.StringItemBinding
import com.example.muslim_everyday.databinding.TittleItemBinding
import com.example.muslim_everyday.item.StringRowType
import com.example.muslim_everyday.item.TittleRowType


class SettingsMenuRVAdapter(private val settingsList: List<Settings>,
                            private val context: Context,
                            private val callback: (isNotificationEnabled: Boolean) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var tittleItemBinding : TittleItemBinding
    private lateinit var stringItemBinding : StringItemBinding

    private var dataSet: List<RowType>? = null

    fun multipleTypesAdapter(dataSet: List<RowType>?) {
        this.dataSet = dataSet
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataSet?.get(position) is TittleRowType) {
            RowType.TITTLE_ROW_TYPE
        } else if (dataSet?.get(position) is StringRowType) {
            RowType.STRING_ROW_TYPE
        } else {
            -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        tittleItemBinding = TittleItemBinding.inflate(LayoutInflater.from(parent.context))
        stringItemBinding = StringItemBinding.inflate(LayoutInflater.from(parent.context))

        return if (viewType == RowType.TITTLE_ROW_TYPE) {
            TittleViewHolder(tittleItemBinding, context)
        } else (if (viewType == RowType.STRING_ROW_TYPE) {
            StringViewHolder(stringItemBinding, context, callback)
        } else {
            null
        })!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val settings = settingsList[position]

        if (holder is TittleViewHolder) {
            holder.bind(settings)
        } else if (holder is StringViewHolder) {
            holder.bind(settings)
        }
    }

    override fun getItemCount(): Int {
        return settingsList.size
    }
}


class TittleViewHolder(private val binding: TittleItemBinding,
                       private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(setting: Settings) {
        binding.apply {

        }
    }
}

class StringViewHolder(private val binding: StringItemBinding,
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