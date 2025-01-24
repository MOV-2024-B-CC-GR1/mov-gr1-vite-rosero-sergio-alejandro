package com.example.a02_deber

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHelper
    private lateinit var paisesListView: ListView
    private lateinit var addPaisButton: Button
    private var selectedPaisId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DatabaseHelper(this)
        paisesListView = findViewById(R.id.paisesListView)
        addPaisButton = findViewById(R.id.addButton)

        loadPaises()

        addPaisButton.setOnClickListener {
            val intent = Intent(this, AddClienteActivity::class.java)
            startActivity(intent)
        }

        registerForContextMenu(paisesListView)
    }

    private fun loadPaises() {
        val paises = db.getAllPaises()
        val adapter = ClienteAdapter(this, paises)
        paisesListView.adapter = adapter

        paisesListView.setOnItemClickListener { _, _, position, _ ->
            val selectedPais = paises[position]
            selectedPaisId = selectedPais.id
            val intent = Intent(this, ViewClienteActivity::class.java)
            intent.putExtra("PAIS_ID", selectedPaisId)
            startActivity(intent)
        }
    }


    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu_cliente, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        selectedPaisId = info.id

        return when (item.itemId) {
            R.id.read -> {
                val intent = Intent(this, ServicioActivity::class.java)
                intent.putExtra("PAIS_ID", selectedPaisId)
                startActivity(intent)
                true
            }
            R.id.edit -> {
                val intent = Intent(this, EditServicioActivity::class.java)
                intent.putExtra("PAIS_ID", selectedPaisId)
                startActivity(intent)
                true
            }
            R.id.delete -> {
                db.deletePais(selectedPaisId)
                loadPaises()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    override fun onResume() {
        super.onResume()
        loadPaises()
    }
}
