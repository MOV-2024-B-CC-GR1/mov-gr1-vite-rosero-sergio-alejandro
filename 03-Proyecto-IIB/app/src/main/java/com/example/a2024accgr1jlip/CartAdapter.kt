package com.example.a2024accgr1jlip

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartAdapter(private val cartItems: List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.itemNameTextView)
        val priceTextView: TextView = view.findViewById(R.id.itemPriceTextView)
        val quantityTextView: TextView = view.findViewById(R.id.itemQuantityTextView)
        val imageView: ImageView = view.findViewById(R.id.itemImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.nameTextView.text = cartItem.dessert.name
        holder.priceTextView.text = "$${cartItem.dessert.price * cartItem.quantity}"
        holder.quantityTextView.text = "x${cartItem.quantity}"
        holder.imageView.setImageResource(cartItem.dessert.imageResId)
    }

    override fun getItemCount() = cartItems.size
}