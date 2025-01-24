package com.example.a02_deber

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

class EditServicioActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var nombreEditText: EditText
    private lateinit var poblacionEditText: EditText
    private lateinit var areaEditText: EditText
    private lateinit var esCapitalEditText: EditText
    private lateinit var fechaEstablecimientoEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: Button
    private var ciudadId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_servicio)

        db = DatabaseHelper(this)
        nombreEditText = findViewById(R.id.edittext_nombre_ciudad)
        poblacionEditText = findViewById(R.id.edittext_poblacion)
        areaEditText = findViewById(R.id.edittext_area)
        esCapitalEditText = findViewById(R.id.edittext_es_capital)
        fechaEstablecimientoEditText = findViewById(R.id.edittext_fecha_establecimiento)
        saveButton = findViewById(R.id.button_save_ciudad)
        backButton = findViewById(R.id.button_back)

        ciudadId = intent.getLongExtra("CIUDAD_ID", 0)
        loadCiudadData()

        saveButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val poblacion = poblacionEditText.text.toString().toIntOrNull() ?: 0
            val area = areaEditText.text.toString().toDoubleOrNull() ?: 0.0
            val esCapital = esCapitalEditText.text.toString().toBoolean()
            val fechaEstablecimiento = LocalDate.parse(fechaEstablecimientoEditText.text.toString())

            val servicio = Servicio(ciudadId, nombre, poblacion, area, esCapital, fechaEstablecimiento)
            db.updateCiudad(servicio)
            finish()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun loadCiudadData() {
        val ciudad = db.getCiudadById(ciudadId)
        if (ciudad != null) {
            nombreEditText.setText(ciudad.nombre)
            poblacionEditText.setText(ciudad.poblacion.toString())
            areaEditText.setText(ciudad.area.toString())
            esCapitalEditText.setText(ciudad.esCapital.toString())
            fechaEstablecimientoEditText.setText(ciudad.fechaEstablecimiento.toString())
        }
    }
}
