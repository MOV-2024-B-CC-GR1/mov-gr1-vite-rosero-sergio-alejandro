package com.example.a02_deber

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewClienteActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var nombreTextView: TextView
    private lateinit var superficieTextView: TextView
    private lateinit var independienteTextView: TextView
    private lateinit var fechaFundacionTextView: TextView
    private lateinit var ciudadesRecyclerView: RecyclerView
    private lateinit var addCiudadButton: Button
    private var paisId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_cliente)

        db = DatabaseHelper(this)
        nombreTextView = findViewById(R.id.nombreTextView)
        superficieTextView = findViewById(R.id.superficieTextView)
        independienteTextView = findViewById(R.id.independienteTextView)
        fechaFundacionTextView = findViewById(R.id.fechaFundacionTextView)
        ciudadesRecyclerView = findViewById(R.id.ciudadesRecyclerView)
        addCiudadButton = findViewById(R.id.addCiudadButton)

        paisId = intent.getLongExtra("PAIS_ID", 0)
        loadPaisData()
        loadCiudades()

        addCiudadButton.setOnClickListener {
            val intent = Intent(this, AddServicioActivity::class.java)
            intent.putExtra("PAIS_ID", paisId)
            startActivity(intent)
        }
    }

    private fun loadPaisData() {
        val pais = db.getAllPaises().find { it.id == paisId }
        pais?.let {
            nombreTextView.text = it.nombre
            superficieTextView.text = it.superficie.toString()
            independienteTextView.text = if (it.es_independiente) "Sí" else "No"
            fechaFundacionTextView.text = it.fecha_fundacion.toString()
        }
    }

    private fun loadCiudades() {
        val ciudades = db.getCiudadesByPaisId(paisId)
        val adapter = ServicioAdapter(ciudades)
        ciudadesRecyclerView.layoutManager = LinearLayoutManager(this)
        ciudadesRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadCiudades()
    }
}
