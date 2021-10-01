package com.example.fitplan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fitplan.R
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
        val defaultItemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.settings_item_default, viewGroup, false)
        val switchItemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.settings_item_switch, viewGroup, false)
        return when (viewType) {
            0 ->  DefaultViewHolder(defaultItemView)
            else ->  SwitchViewHolder(switchItemView)
        }
    }

    override fun onBindViewHolder(viewHolder: BaseSettingViewHolder, position: Int) {
        settingsList?.elementAt(position)?.let {
            viewHolder.bindView(it)
        }
    }

    override fun getItemCount() = settingsList?.size?: 0

    override fun getItemViewType(position: Int): Int {
        return when (settingsList?.elementAt(position)?.type) {
            SettingsType.SWITCH -> 1
            else -> 0
        }
    }

    inner class DefaultViewHolder(view: View) : BaseSettingViewHolder(view)

    inner class SwitchViewHolder(view: View) : BaseSettingViewHolder(view) {
        override val settingView: SwitchCompat = view.findViewById(R.id.setting)

        override fun bindView(item: SettingsItem) {
            settingView.text = item.name
            settingView.isChecked = item.switched?: false
            settingView.setOnCheckedChangeListener { _, _ ->
                clickBlock.invoke(item, adapterPosition)
            }
        }
    }

    abstract inner class BaseSettingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        protected open val settingView: TextView = view.findViewById(R.id.setting)

        open fun bindView(item: SettingsItem) {
            settingView.text = item.name

            settingView.setOnClickListener {
                clickBlock.invoke(item, adapterPosition)
            }
        }
    }

}