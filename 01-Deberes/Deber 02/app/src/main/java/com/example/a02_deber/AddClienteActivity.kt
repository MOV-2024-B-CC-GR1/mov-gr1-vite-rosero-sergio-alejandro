package com.example.a02_deber

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

class AddClienteActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var nombreEditText: EditText
    private lateinit var superficieEditText: EditText
    private lateinit var independienteEditText: EditText
    private lateinit var fechaFundacionEditText: EditText
    private lateinit var addButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cliente)

        db = DatabaseHelper(this)
        nombreEditText = findViewById(R.id.nombreEditText)
        superficieEditText = findViewById(R.id.superficieEditText)
        independienteEditText = findViewById(R.id.independienteEditText)
        fechaFundacionEditText = findViewById(R.id.fechaFundacionEditText)
        addButton = findViewById(R.id.addButton)

        addButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val superficie = superficieEditText.text.toString().toDouble()
            val es_idependiente = independienteEditText.text.toString().toInt() == 1
            val fecha_fundacion = LocalDate.parse(fechaFundacionEditText.text.toString())

            val cliente = Cliente(0, nombre, superficie, es_idependiente, fecha_fundacion)
            db.addPais(cliente)

            // Finaliza la actividad después de guardar el país
            finish()
        }


        backButton = findViewById(R.id.button_back)


        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
