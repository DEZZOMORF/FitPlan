package com.example.fitplan.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fitplan.R
import com.example.fitplan.model.Plan

class PlanRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var list: ArrayList<Plan> = arrayListOf()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_plan, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is BaseViewHolder) {
            viewHolder.bindView()
        }
    }

    override fun getItemCount() = list.size

    inner class ViewHolder(view: View) : BaseViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.title)
        private val cardBackground: ImageView = view.findViewById(R.id.cardBackground)
        private val itemCard: CardView = view.findViewById(R.id.itemCard)
        override fun bindView() {
            val item = list[adapterPosition]
            Glide.with(itemView.context).load(item.imageUrl).into(cardBackground)
            title.text = "${item.name}\n${item.athleteFirstName} ${item.athleteLastName}"
            itemCard.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id", item.id)
                Navigation.findNavController(itemView).navigate(R.id.action_planListFragment_to_planDetailsFragment, bundle)
            }
        }
    }

    abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindView()
    }
}