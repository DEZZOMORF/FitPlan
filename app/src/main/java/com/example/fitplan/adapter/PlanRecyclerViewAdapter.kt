package com.example.fitplan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fitplan.databinding.ItemPlanBinding
import com.example.fitplan.manager.SharedPreferencesManager
import com.example.fitplan.model.Plan
import javax.inject.Inject

class PlanRecyclerViewAdapter @Inject constructor(
    private var sharedPreferencesManager: SharedPreferencesManager
): RecyclerView.Adapter<PlanRecyclerViewAdapter.ViewHolder>() {

    var list: ArrayList<Plan> = arrayListOf()
    var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPlanBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindView()
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(private val binding: ItemPlanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView() {
            binding.imageVisibility = sharedPreferencesManager.showingImageState
            binding.plan = list[adapterPosition]
            binding.itemCard.setOnClickListener {
                onItemClickListener?.invoke(list[adapterPosition].id)
            }
            binding.executePendingBindings()
        }
    }
}