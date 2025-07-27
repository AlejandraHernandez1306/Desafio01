package com.example.desafio01

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class PromedioActivity : AppCompatActivity() {

        private lateinit var etNombre: EditText
        private lateinit var etNota1: EditText
        private lateinit var etNota2: EditText
        private lateinit var etNota3: EditText
        private lateinit var etNota4: EditText
        private lateinit var etNota5: EditText
        private lateinit var etPonderacion1: EditText
        private lateinit var etPonderacion2: EditText
        private lateinit var etPonderacion3: EditText
        private lateinit var etPonderacion4: EditText
        private lateinit var etPonderacion5: EditText
        private lateinit var btnCalcular: Button
        private lateinit var tvResultado: TextView

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_promedio)

            inicializarVistas()

            btnCalcular.setOnClickListener {
                calcularPromedio()
            }
        }

        private fun inicializarVistas() {
            etNombre = findViewById(R.id.etNombre)
            etNota1 = findViewById(R.id.etNota1)
            etNota2 = findViewById(R.id.etNota2)
            etNota3 = findViewById(R.id.etNota3)
            etNota4 = findViewById(R.id.etNota4)
            etNota5 = findViewById(R.id.etNota5)
            etPonderacion1 = findViewById(R.id.etPonderacion1)
            etPonderacion2 = findViewById(R.id.etPonderacion2)
            etPonderacion3 = findViewById(R.id.etPonderacion3)
            etPonderacion4 = findViewById(R.id.etPonderacion4)
            etPonderacion5 = findViewById(R.id.etPonderacion5)
            btnCalcular = findViewById(R.id.btnCalcular)
            tvResultado = findViewById(R.id.tvResultado)
        }

        private fun calcularPromedio() {
            // Validar campos vacíos
            if (!validarCampos()) {
                return
            }

            val nombre = etNombre.text.toString()

            try {
                val nota1 = etNota1.text.toString().toDouble()
                val nota2 = etNota2.text.toString().toDouble()
                val nota3 = etNota3.text.toString().toDouble()
                val nota4 = etNota4.text.toString().toDouble()
                val nota5 = etNota5.text.toString().toDouble()

                val pond1 = etPonderacion1.text.toString().toDouble()
                val pond2 = etPonderacion2.text.toString().toDouble()
                val pond3 = etPonderacion3.text.toString().toDouble()
                val pond4 = etPonderacion4.text.toString().toDouble()
                val pond5 = etPonderacion5.text.toString().toDouble()

                // Validar notas entre 0 y 10
                if (!validarNotas(nota1, nota2, nota3, nota4, nota5)) {
                    return
                }

                // Validar que las ponderaciones sumen 100
                if (!validarPonderaciones(pond1, pond2, pond3, pond4, pond5)) {
                    return
                }

                val promedio = calcularPromedioFinal(nota1, nota2, nota3, nota4, nota5,
                    pond1, pond2, pond3, pond4, pond5)

                mostrarResultado(nombre, promedio)

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Por favor ingrese valores numéricos válidos", Toast.LENGTH_SHORT).show()
            }
        }

        private fun validarCampos(): Boolean {
            val campos = listOf(etNombre, etNota1, etNota2, etNota3, etNota4, etNota5,
                etPonderacion1, etPonderacion2, etPonderacion3, etPonderacion4, etPonderacion5)

            for (campo in campos) {
                if (campo.text.toString().trim().isEmpty()) {
                    campo.setError("Este campo es obligatorio")
                    return false
                }
            }
            return true
        }

        private fun validarNotas(vararg notas: Double): Boolean {
            for (nota in notas) {
                if (nota < 0 || nota > 10) {
                    Toast.makeText(this, "Las notas deben estar entre 0 y 10", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
            return true
        }

        private fun validarPonderaciones(vararg ponderaciones: Double): Boolean {
            val suma = ponderaciones.sum()
            if (suma != 100.0) {
                Toast.makeText(this, "Las ponderaciones deben sumar exactamente 100%", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }

        private fun calcularPromedioFinal(n1: Double, n2: Double, n3: Double, n4: Double, n5: Double,
                                          p1: Double, p2: Double, p3: Double, p4: Double, p5: Double): Double {
            return (n1 * p1 + n2 * p2 + n3 * p3 + n4 * p4 + n5 * p5) / 100.0
        }

        private fun mostrarResultado(nombre: String, promedio: Double) {
            val df = DecimalFormat("#.##")
            val promedioFormateado = df.format(promedio)
            val estado = if (promedio >= 6.0) "APROBADO" else "REPROBADO"
            val color = if (promedio >= 6.0) "#4CAF50" else "#F44336"

            val mensaje = """
            Estudiante: $nombre
            Promedio Final: $promedioFormateado
            Estado: $estado
        """.trimIndent()

            tvResultado.text = mensaje

            // También mostrar en AlertDialog
            AlertDialog.Builder(this)
                .setTitle("Resultado Final")
                .setMessage(mensaje)
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }
