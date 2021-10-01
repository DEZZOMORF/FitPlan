package com.example.fitplan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fitplan.R
import com.example.fitplan.manager.SharedPreferencesManager
import com.example.fitplan.model.SettingsItem
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsRecyclerViewAdapter(private val settingsList: Set<SettingsItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val defaultItemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.settings_item_default, viewGroup, false)
        val switchItemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.settings_item_switch, viewGroup, false)
        return when (viewType) {
            0 ->  DefaultViewHolder(defaultItemView)
            else ->  SwitchViewHolder(switchItemView)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is BaseViewHolder) {
            viewHolder.bindView()
        }
    }

    override fun getItemCount() = settingsList.size

    override fun getItemViewType(position: Int): Int {
        return when (settingsList.elementAt(position).switch) {
            true -> 1
            false -> 0
        }
    }

    inner class DefaultViewHolder(view: View) : BaseViewHolder(view) {
        private val item: TextView = view.findViewById(R.id.defaultSetting)
        override fun bindView() {
            val name = settingsList.elementAt(adapterPosition).name
            item.text = name
            item.setOnClickListener { setFun(name, itemView, null) }
        }
    }

    inner class SwitchViewHolder(view: View) : BaseViewHolder(view) {
        private val item: SwitchMaterial = view.findViewById(R.id.switchSetting)
        override fun bindView() {
            val name = settingsList.elementAt(adapterPosition).name
            item.text = name
            item.isChecked = SharedPreferencesManager(itemView.context).showingImageState!!
            item.setOnClickListener { setFun(name, itemView, item.isChecked) }
        }
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindView()
    }

    private fun setFun(name: String, view: View, switchState: Boolean?) {
        when (name) {
            "Logout" -> {
                SharedPreferencesManager(view.context).userAccessToken = null
                Navigation.findNavController(view).navigate(R.id.planListFragment)
                Toast.makeText(view.context, "logout", Toast.LENGTH_LONG).show()
            }
            "Enable showing image" -> {
                if (switchState != null) SharedPreferencesManager(view.context).showingImageState = switchState
            }
        }
    }

}