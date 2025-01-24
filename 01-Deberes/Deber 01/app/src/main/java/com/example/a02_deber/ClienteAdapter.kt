package com.example.a02_deber

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ClienteAdapter(context: Context, private val paises: List<Cliente>) :
    ArrayAdapter<Cliente>(context, 0, paises) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val pais = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = pais?.nombre
        return view
    }
}
