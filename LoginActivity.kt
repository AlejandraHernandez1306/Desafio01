package com.example.desafio01

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    // Variables locales para nombre y contraseña (como práctica)
    private val usuarioValido = "admin"
    private val passwordValido = "123456"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        inicializarVistas()

        btnLogin.setOnClickListener {
            validarLogin()
        }
    }

    private fun inicializarVistas() {
        etUsuario = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
    }

    private fun validarLogin() {
        val usuario = etUsuario.text.toString().trim()
        val password = etPassword.text.toString().trim()

        // Validar campos vacíos
        if (usuario.isEmpty()) {
            etUsuario.setError("El usuario es obligatorio")
            return
        }

        if (password.isEmpty()) {
            etPassword.setError("La contraseña es obligatoria")
            return
        }

        // Validar credenciales
        if (usuario == usuarioValido && password == passwordValido) {
            Toast.makeText(this, "¡Login exitoso! Bienvenido $usuario", Toast.LENGTH_SHORT).show()

            // Navegar a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Para que no pueda volver al login con el botón atrás

        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            etPassword.text.clear() // Limpiar contraseña por seguridad
        }
    }
}