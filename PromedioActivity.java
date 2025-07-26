package com.example.desafio01;

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

public static class PromedioActivity : AppCompatActivity() {
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
            if (!validarCampos()) return

                    val nombre = etNombre.text.toString()

            try {
                val notas = arrayOf(
                        etNota1.text.toString().toDouble(),
                        etNota2.text.toString().toDouble(),
                        etNota3.text.toString().toDouble(),
                        etNota4.text.toString().toDouble(),
                        etNota5.text.toString().toDouble()
                )

                val ponderaciones = arrayOf(
                        etPonderacion1.text.toString().toDouble(),
                        etPonderacion2.text.toString().toDouble(),
                        etPonderacion3.text.toString().toDouble(),
                        etPonderacion4.text.toString().toDouble(),
                        etPonderacion5.text.toString().toDouble()
                )

                if (!validarNotas(notas)) return

                        val sumaPonderaciones = ponderaciones.sum()
                if (sumaPonderaciones != 100.0) {
                    Toast.makeText(this, "Las ponderaciones deben sumar 100%", Toast.LENGTH_SHORT).show()
                    return
                }

                val promedio = calcularPromedioFinal(notas, ponderaciones)
                val estado = if (promedio >= 6.0) "APROBADO" else "REPROBADO"

                val df = DecimalFormat("#.##")
                val resultado = """
                Estudiante: $nombre

                Promedio Final: ${df.format(promedio)}
                Estado: $estado
            """.trimIndent()

                tvResultado.text = resultado
                tvResultado.visibility = View.VISIBLE

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Ingrese valores numéricos válidos", Toast.LENGTH_SHORT).show()
            }
        }

        private fun validarCampos(): Boolean {
            val campos = arrayOf(etNombre, etNota1, etNota2, etNota3, etNota4, etNota5,
                    etPonderacion1, etPonderacion2, etPonderacion3, etPonderacion4, etPonderacion5)

            for (campo in campos) {
                if (campo.text.toString().trim().isEmpty()) {
                    campo.error = "Este campo es obligatorio"
                    campo.requestFocus()
                    return false
                }
            }
            return true
        }

        private fun validarNotas(notas: Array<Double>): Boolean {
            for (nota in notas) {
                if (nota < 0 || nota > 10) {
                    Toast.makeText(this, "Las notas deben estar entre 0 y 10", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
            return true
        }

        private fun calcularPromedioFinal(notas: Array<Double>, ponderaciones: Array<Double>): Double {
            var suma = 0.0
            for (i in notas.indices) {
                suma += notas[i] * (ponderaciones[i] / 100.0)
            }
            return suma
        }
    }


