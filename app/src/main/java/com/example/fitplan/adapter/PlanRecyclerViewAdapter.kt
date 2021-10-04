package com.example.fitplan.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitplan.R
import com.example.fitplan.databinding.ItemPlanBinding
import com.example.fitplan.manager.SharedPreferencesManager
import com.example.fitplan.model.Plan
import javax.inject.Inject

class PlanRecyclerViewAdapter @Inject constructor(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: ArrayList<Plan> = arrayListOf()
    private lateinit var binding: ItemPlanBinding
    var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPlanBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is BaseViewHolder) {
            viewHolder.bindView()
        }
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        override fun bindView() {
            val item = list[adapterPosition]
            if (SharedPreferencesManager(itemView.context).showingImageState) {
                binding.cardBackground.visibility = View.VISIBLE
            }
            binding.plan = item
            binding.itemCard.setOnClickListener {
                onItemClickListener?.invoke(item.id)
            }
        }
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindView()
    }
}