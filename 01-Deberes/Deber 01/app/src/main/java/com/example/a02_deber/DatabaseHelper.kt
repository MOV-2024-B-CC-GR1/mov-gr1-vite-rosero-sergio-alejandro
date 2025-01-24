package com.example.a02_deber

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "clientes_db"
        private const val DATABASE_VERSION = 1

        // Tablas
        private const val TABLE_PAIS = "clientes"
        private const val TABLE_CIUDAD = "servicios"

        // Columnas de la tabla País
        private const val KEY_PAIS_ID = "id"
        private const val KEY_PAIS_NOMBRE = "nombre"
        private const val KEY_PAIS_SUPERFICIE = "tipo"
        private const val KEY_PAIS_INDEPENDIENTE = "es_cliente_antiguo"
        private const val KEY_PAIS_FECHA_FUNDACION = "fecha_sucripcion"

        // Columnas de la tabla Ciudad
        private const val KEY_CIUDAD_ID = "id"
        private const val KEY_CIUDAD_NOMBRE = "nombre"
        private const val KEY_CIUDAD_POBLACION = "tipo_servicio"
        private const val KEY_CIUDAD_AREA = "suscripcion_familiar"
        private const val KEY_CIUDAD_ES_CAPITAL = "renovar"
        private const val KEY_CIUDAD_FECHA_ESTABLECIMIENTO = "fecha_Suscripcion"
        private const val KEY_CIUDAD_PAIS_ID = "pais_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createPaisTable = ("CREATE TABLE $TABLE_PAIS ($KEY_PAIS_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_PAIS_NOMBRE TEXT, " +
                "$KEY_PAIS_SUPERFICIE REAL, $KEY_PAIS_INDEPENDIENTE INTEGER, $KEY_PAIS_FECHA_FUNDACION TEXT)")

        val createCiudadTable = ("CREATE TABLE $TABLE_CIUDAD ($KEY_CIUDAD_ID INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_CIUDAD_NOMBRE TEXT, " +
                "$KEY_CIUDAD_POBLACION INTEGER, $KEY_CIUDAD_AREA REAL, $KEY_CIUDAD_ES_CAPITAL INTEGER, $KEY_CIUDAD_FECHA_ESTABLECIMIENTO TEXT, " +
                "$KEY_CIUDAD_PAIS_ID INTEGER, FOREIGN KEY($KEY_CIUDAD_PAIS_ID) REFERENCES $TABLE_PAIS($KEY_PAIS_ID))")

        db?.execSQL(createPaisTable)
        db?.execSQL(createCiudadTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PAIS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CIUDAD")
        onCreate(db)
    }

    // Métodos CRUD para País
    fun addPais(cliente: Cliente): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("nombre", cliente.nombre)
            put("Apellido", cliente.superficie)
            put("Cliente antiguo o no", if (cliente.es_independiente) 1 else 0)
            put("fecha_Suscripcion", cliente.fecha_fundacion.toString())  // Asegúrate de almacenar la fecha como texto
        }

        val result = db.insert("Cliente", null, contentValues)
        db.close()
        return result  // Devuelve el ID del nuevo registro insertado o -1 si falla
    }



    fun getAllPaises(): List<Cliente> {
        val paises = mutableListOf<Cliente>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_PAIS", null)
        if (cursor.moveToFirst()) {
            do {
                val cliente = Cliente(
                    cursor.getLong(cursor.getColumnIndexOrThrow(KEY_PAIS_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_PAIS_NOMBRE)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_PAIS_SUPERFICIE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_PAIS_INDEPENDIENTE)) == 1,
                    LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow(KEY_PAIS_FECHA_FUNDACION)))
                )
                paises.add(cliente)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return paises
    }

    fun updatePais(cliente: Cliente): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_PAIS_NOMBRE, cliente.nombre)
            put(KEY_PAIS_SUPERFICIE, cliente.superficie)
            put(KEY_PAIS_INDEPENDIENTE, if (cliente.es_independiente) 1 else 0)
            put(KEY_PAIS_FECHA_FUNDACION, cliente.fecha_fundacion.toString())
        }
        val result = db.update(TABLE_PAIS, values, "$KEY_PAIS_ID = ?", arrayOf(cliente.id.toString()))
        db.close()
        return result
    }

    fun deletePais(paisId: Long): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_PAIS, "$KEY_PAIS_ID = ?", arrayOf(paisId.toString()))
        db.close()
        return result
    }

    // Métodos CRUD para Ciudad
    fun addCiudad(paisId: Long, servicio: Servicio): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_CIUDAD_NOMBRE, servicio.nombre)
            put(KEY_CIUDAD_POBLACION, servicio.poblacion)
            put(KEY_CIUDAD_AREA, servicio.area)
            put(KEY_CIUDAD_ES_CAPITAL, if (servicio.esCapital) 1 else 0)
            put(KEY_CIUDAD_FECHA_ESTABLECIMIENTO, servicio.fechaEstablecimiento.toString())
            put(KEY_CIUDAD_PAIS_ID, paisId)
        }
        val id = db.insert(TABLE_CIUDAD, null, values)
        db.close()
        return id
    }


    fun getCiudadesByPaisId(paisId: Long): List<Servicio> {
        val ciudades = mutableListOf<Servicio>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_CIUDAD WHERE $KEY_CIUDAD_PAIS_ID=?", arrayOf(paisId.toString()))
        if (cursor.moveToFirst()) {
            do {
                val servicio = Servicio(
                    cursor.getLong(cursor.getColumnIndexOrThrow(KEY_CIUDAD_ID)), // Asegúrate de incluir el ID aquí
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_CIUDAD_NOMBRE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CIUDAD_POBLACION)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_CIUDAD_AREA)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CIUDAD_ES_CAPITAL)) == 1,
                    LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CIUDAD_FECHA_ESTABLECIMIENTO)))
                )
                ciudades.add(servicio)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return ciudades
    }


    fun updateCiudad(servicio: Servicio): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_CIUDAD_NOMBRE, servicio.nombre)
            put(KEY_CIUDAD_POBLACION, servicio.poblacion)
            put(KEY_CIUDAD_AREA, servicio.area)
            put(KEY_CIUDAD_ES_CAPITAL, if (servicio.esCapital) 1 else 0)
            put(KEY_CIUDAD_FECHA_ESTABLECIMIENTO, servicio.fechaEstablecimiento.toString())
        }
        val rowsAffected = db.update(TABLE_CIUDAD, values, "$KEY_CIUDAD_ID = ?", arrayOf(servicio.id.toString()))
        db.close()
        return rowsAffected
    }

    fun getCiudadById(ciudadId: Long): Servicio? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_CIUDAD,
            null,
            "$KEY_CIUDAD_ID = ?",
            arrayOf(ciudadId.toString()),
            null,
            null,
            null
        )
        val servicio = if (cursor.moveToFirst()) {
            Servicio(
                cursor.getLong(cursor.getColumnIndexOrThrow(KEY_CIUDAD_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(KEY_CIUDAD_NOMBRE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CIUDAD_POBLACION)),
                cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_CIUDAD_AREA)),
                cursor.getInt(cursor.getColumnIndexOrThrow(KEY_CIUDAD_ES_CAPITAL)) == 1,
                LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow(KEY_CIUDAD_FECHA_ESTABLECIMIENTO)))
            )
        } else {
            null
        }
        cursor.close()
        db.close()
        return servicio
    }


    fun deleteCiudad(ciudadId: Long): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_CIUDAD, "$KEY_CIUDAD_ID = ?", arrayOf(ciudadId.toString()))
        db.close()
        return result
    }
}
