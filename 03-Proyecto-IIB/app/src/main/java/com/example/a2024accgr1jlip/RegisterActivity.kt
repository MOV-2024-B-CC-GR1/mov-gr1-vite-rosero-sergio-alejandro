package com.example.a2024accgr1jlip

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.IOException

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Referencias a los elementos de la vista
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val passwordConfirmEditText = findViewById<EditText>(R.id.passwordconfirm)
        val signUpButton = findViewById<Button>(R.id.signUpButton) // El ID del botón puede cambiar

        // Acción para registrar un usuario
        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val passwordConfirm = passwordConfirmEditText.text.toString().trim()

            // Validar campos vacíos
            if (email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar formato de correo electrónico
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar coincidencia de contraseñas
            if (password != passwordConfirm) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Registrar el usuario
            if (registerUser(email, password)) {
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Registra un usuario nuevo en el archivo JSON.
     *
     * @param email Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return `true` si el registro es exitoso; de lo contrario, `false`.
     */
    private fun registerUser(email: String, password: String): Boolean {
        try {
            val file = File(filesDir, "users.json")
            val usersArray = if (file.exists()) {
                JSONArray(file.readText()) // Leer el archivo si existe
            } else {
                JSONArray() // Crear un nuevo arreglo JSON si no existe
            }

            // Verificar si el usuario ya existe
            for (i in 0 until usersArray.length()) {
                val user = usersArray.getJSONObject(i)
                if (user.getString("email") == email) {
                    return false // Usuario ya registrado
                }
            }

            // Agregar un nuevo usuario
            val newUser = JSONObject()
            newUser.put("email", email)
            newUser.put("password", password) // Nota: Usar hashing para mayor seguridad
            usersArray.put(newUser)

            // Guardar los datos en el archivo
            file.writeText(usersArray.toString())
            return true
        } catch (e: IOException) {
            Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
            return false
        }
    }
}
