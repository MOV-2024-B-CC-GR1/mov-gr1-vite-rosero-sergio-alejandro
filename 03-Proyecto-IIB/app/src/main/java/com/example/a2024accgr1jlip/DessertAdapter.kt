package com.example.a2024accgr1jlip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DessertAdapter(
    private val desserts: List<Dessert>,
    private val onItemClick: (Dessert) -> Unit
) : RecyclerView.Adapter<DessertAdapter.DessertViewHolder>() {

    class DessertViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.dessertNameTextView)
        val priceTextView: TextView = view.findViewById(R.id.dessertPriceTextView)
        val imageView: ImageView = view.findViewById(R.id.dessertImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DessertViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dessert, parent, false)
        return DessertViewHolder(view)
    }

    override fun onBindViewHolder(holder: DessertViewHolder, position: Int) {
        val dessert = desserts[position]
        holder.nameTextView.text = dessert.name
        holder.priceTextView.text = "$${dessert.price}"
        holder.imageView.setImageResource(dessert.imageResId)
        holder.itemView.setOnClickListener { onItemClick(dessert) }
    }

    override fun getItemCount() = desserts.size
}