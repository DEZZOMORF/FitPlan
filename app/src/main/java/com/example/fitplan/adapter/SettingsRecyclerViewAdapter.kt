package com.example.fitplan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitplan.databinding.ItemSettingsDefaultBinding
import com.example.fitplan.databinding.ItemSettingsSwitchBinding
import com.example.fitplan.model.SettingsItem
import com.example.fitplan.model.SettingsType
import javax.inject.Inject

class SettingsRecyclerViewAdapter @Inject constructor() :
    RecyclerView.Adapter<SettingsRecyclerViewAdapter.BaseSettingViewHolder>() {

    private var settingsList: List<SettingsItem>? = null
    var clickBlock: ((SettingsItem, Int) -> Unit?)? = null

    fun update(list: List<SettingsItem>) {
        settingsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseSettingViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            0 -> DefaultViewHolder(ItemSettingsDefaultBinding.inflate(inflater, viewGroup, false))
            else -> SwitchViewHolder(ItemSettingsSwitchBinding.inflate(inflater, viewGroup, false))
        }
    }

    override fun onBindViewHolder(viewHolder: BaseSettingViewHolder, position: Int) {
        settingsList?.elementAt(position)?.let {
            viewHolder.bindView(it)
        }
    }

    override fun getItemCount() = settingsList?.size ?: 0

    override fun getItemViewType(position: Int): Int {
        return when (settingsList?.elementAt(position)?.type) {
            SettingsType.SWITCH -> 1
            else -> 0
        }
    }

    inner class DefaultViewHolder(val binding: ItemSettingsDefaultBinding) :
        BaseSettingViewHolder(binding.root) {

        override fun bindView(item: SettingsItem) {
            binding.item = item
            binding.setting.setOnClickListener {
                clickBlock?.invoke(item, adapterPosition)
            }
        }
    }

    inner class SwitchViewHolder(val binding: ItemSettingsSwitchBinding) :
        BaseSettingViewHolder(binding.root) {

        override fun bindView(item: SettingsItem) {
            binding.item = item
            binding.setting.isChecked = item.switched ?: false
            binding.setting.setOnCheckedChangeListener { _, _ ->
                clickBlock?.invoke(item, adapterPosition)
            }
        }
    }

    abstract class BaseSettingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindView(item: SettingsItem)
    }
}