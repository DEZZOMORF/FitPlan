package com.example.fitplan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitplan.databinding.SettingsItemDefaultBinding
import com.example.fitplan.databinding.SettingsItemSwitchBinding
import com.example.fitplan.model.SettingsItem
import com.example.fitplan.model.SettingsType

class SettingsRecyclerViewAdapter(
    private val clickBlock: (SettingsItem, Int) -> Unit
) : RecyclerView.Adapter<SettingsRecyclerViewAdapter.BaseSettingViewHolder>() {

    private var settingsList: List<SettingsItem>? = null

    fun update(list: List<SettingsItem>) {
        settingsList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseSettingViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        return when (viewType) {
            0 -> DefaultViewHolder(SettingsItemDefaultBinding.inflate(inflater, viewGroup, false))
            else -> SwitchViewHolder(SettingsItemSwitchBinding.inflate(inflater, viewGroup, false))
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

    inner class DefaultViewHolder(val binding: SettingsItemDefaultBinding) :
        BaseSettingViewHolder(binding.root) {

        override fun bindView(item: SettingsItem) {
            binding.item = item
            binding.setting.setOnClickListener {
                clickBlock.invoke(item, adapterPosition)
            }
        }
    }

    inner class SwitchViewHolder(val binding: SettingsItemSwitchBinding) :
        BaseSettingViewHolder(binding.root) {

        override fun bindView(item: SettingsItem) {
            binding.item = item
            binding.setting.isChecked = item.switched ?: false
            binding.setting.setOnCheckedChangeListener { _, _ ->
                clickBlock.invoke(item, adapterPosition)
            }
        }
    }

    abstract class BaseSettingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindView(item: SettingsItem)
    }
}