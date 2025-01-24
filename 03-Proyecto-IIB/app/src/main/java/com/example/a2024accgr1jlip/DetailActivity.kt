package com.example.a2024accgr1jlip

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val dessertId = intent.getIntExtra("DESSERT_ID", -1)
        val dessertName = intent.getStringExtra("DESSERT_NAME") ?: ""
        val dessertDescription = intent.getStringExtra("DESSERT_DESCRIPTION") ?: ""
        val dessertPrice = intent.getDoubleExtra("DESSERT_PRICE", 0.0)
        val dessertImageResId = intent.getIntExtra("DESSERT_IMAGE", 0)

        val nameTextView = findViewById<TextView>(R.id.dessertNameTextView)
        val descriptionTextView = findViewById<TextView>(R.id.dessertDescriptionTextView)
        val priceTextView = findViewById<TextView>(R.id.dessertPriceTextView)
        val imageView = findViewById<ImageView>(R.id.dessertImageView)
        val addToCartButton = findViewById<Button>(R.id.addToCartButton)

        nameTextView.text = dessertName
        descriptionTextView.text = dessertDescription
        priceTextView.text = "$$dessertPrice"
        imageView.setImageResource(dessertImageResId)

        addToCartButton.setOnClickListener {
            // Implement add to cart logic
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show()
        }
    }
}