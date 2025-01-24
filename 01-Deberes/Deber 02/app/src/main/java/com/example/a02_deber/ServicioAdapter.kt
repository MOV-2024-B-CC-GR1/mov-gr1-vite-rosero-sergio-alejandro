package com.example.a02_deber

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ServicioAdapter(private val ciudades: List<Servicio>) :
    RecyclerView.Adapter<ServicioAdapter.CiudadViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CiudadViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return CiudadViewHolder(view)
    }

    override fun onBindViewHolder(holder: CiudadViewHolder, position: Int) {
        val ciudad = ciudades[position]
        holder.bind(ciudad)
    }

    override fun getItemCount(): Int {
        return ciudades.size
    }

    inner class CiudadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val text1: TextView = itemView.findViewById(android.R.id.text1)
        private val text2: TextView = itemView.findViewById(android.R.id.text2)

        fun bind(servicio: Servicio) {
            text1.text = servicio.nombre
            text2.text = "Suscripciones: ${servicio.poblacion}, Total: ${servicio.area}"
        }
    }
}
