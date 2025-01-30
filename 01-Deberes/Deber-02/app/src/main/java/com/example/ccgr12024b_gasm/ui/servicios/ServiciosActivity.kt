package com.example.ccgr12024b_gasm.ui.servicios

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ccgr12024b_gasm.R
import com.example.ccgr12024b_gasm.database.DatabaseHelper
import com.example.ccgr12024b_gasm.model.Servicios
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class ServiciosActivity : AppCompatActivity() {

    /**
     * Actividad para gestionar los pedidos de un cliente específico.
     * Permite visualizar, agregar, actualizar y eliminar pedidos.
     */
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerViewPedidos: RecyclerView
    private lateinit var adapter: ServiciosAdapter
    private val pedidosList = mutableListOf<Servicios>()
    private var clienteId: Int = 0

    /**
     * Método llamado al crear la actividad.
     * Configura el RecyclerView, el botón flotante y carga los datos del cliente.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicio)

        dbHelper = DatabaseHelper(this)
        recyclerViewPedidos = findViewById(R.id.recyclerViewPedidos)
        val fabAddPedido: ExtendedFloatingActionButton = findViewById(R.id.fabAddPedido)

        // Obtener el ID del cliente desde el Intent
        clienteId = intent.getIntExtra("clienteId", 0)

        setupRecyclerView()
        // Configurar el botón flotante para agregar un nuevo pedido
        fabAddPedido.setOnClickListener {
            val intent = Intent(this, ServiciosFormActivity::class.java)
            intent.putExtra("clienteId", clienteId) // Pasar el ID del cliente al formulario
            startActivity(intent)
        }
    }

    /**
     * Configura el RecyclerView para mostrar la lista de pedidos.
     */
    private fun setupRecyclerView() {
        adapter = ServiciosAdapter(pedidosList) { pedido ->
            mostrarOpcionesPedido(pedido)
        }
        recyclerViewPedidos.layoutManager = LinearLayoutManager(this)
        recyclerViewPedidos.adapter = adapter
    }

    /**
     * Método llamado cuando la actividad vuelve a primer plano.
     * Carga los pedidos desde la base de datos y actualiza la lista.
     */
    override fun onResume() {
        super.onResume()
        cargarPedidos()
    }

    /**
     * Carga los pedidos del cliente desde la base de datos y actualiza el RecyclerView.
     */
    private fun cargarPedidos() {
        pedidosList.clear()
        val cursor = dbHelper.obtenerPedidosPorCliente(clienteId)
        if (cursor.moveToFirst()) {
            do {
                val servicios = Servicios(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.PEDIDO_ID)),
                    cliente_id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.PEDIDO_CLIENTE_ID)),
                    descripcion = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PEDIDO_DESCRIPCION)),
                    estado = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PEDIDO_ESTADO)),
                    fechaPedido = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.PEDIDO_FECHA_PEDIDO))
                )
                pedidosList.add(servicios)
            } while (cursor.moveToNext())
        }
        cursor.close()
        adapter.notifyDataSetChanged()
    }

    /**
     * Muestra un cuadro de diálogo con opciones para un pedido seleccionado.
     * @param servicios Pedido seleccionado.
     */
    private fun mostrarOpcionesPedido(servicios: Servicios) {
        val opciones = arrayOf("Actualizar", "Eliminar")
        AlertDialog.Builder(this)
            .setTitle("Opciones para el pedido")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> actualizarPedido(servicios)
                    1 -> eliminarPedido(servicios)
                }
            }
            .show()
    }

    /**
     * Inicia la actividad de formulario para actualizar un pedido.
     * @param servicios Pedido que se desea actualizar.
     */
    private fun actualizarPedido(servicios: Servicios) {
        val intent = Intent(this, ServiciosFormActivity::class.java)
        intent.putExtra("pedidoId", servicios.id)
        intent.putExtra("clienteId", servicios.cliente_id)
        startActivity(intent)
    }

    /**
     * Muestra un cuadro de diálogo para confirmar la eliminación de un pedido.
     * @param servicios Pedido que se desea eliminar.
     */
    private fun eliminarPedido(servicios: Servicios) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de eliminar este pedido?")
            .setPositiveButton("Sí") { _, _ ->
                dbHelper.eliminarPedido(servicios.id)
                cargarPedidos()
                Toast.makeText(this, "Pedido eliminado", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }
}
