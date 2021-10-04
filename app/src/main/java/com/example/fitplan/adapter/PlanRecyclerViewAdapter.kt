package com.example.fitplan.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitplan.R
import com.example.fitplan.databinding.ItemPlanBinding
import com.example.fitplan.manager.SharedPreferencesManager
import com.example.fitplan.model.Plan


class PlanRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: ArrayList<Plan> = arrayListOf()
    private lateinit var binding: ItemPlanBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        binding = ItemPlanBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is BaseViewHolder) {
            viewHolder.bindView()
        }
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        private val cardBackground: ImageView = view.findViewById(R.id.cardBackground)
        private val itemCard: CardView = view.findViewById(R.id.itemCard)
        override fun bindView() {
            val item = list[adapterPosition]
            if (SharedPreferencesManager(itemView.context).showingImageState) {
                cardBackground.visibility = View.VISIBLE
            }
            binding.plan = list[adapterPosition]
            itemCard.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id", item.id)
                Navigation.findNavController(itemView)
                    .navigate(R.id.action_planListFragment_to_planDetailsFragment, bundle)
            }
        }
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindView()
    }
}