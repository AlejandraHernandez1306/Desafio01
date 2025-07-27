package com.example.desafio01

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class SalarioActivity : AppCompatActivity() {

    private lateinit var etNombreEmpleado: EditText
    private lateinit var etSalarioBase: EditText
    private lateinit var btnCalcular: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario)

        inicializarVistas()

        btnCalcular.setOnClickListener {
            calcularSalarioNeto()
        }
    }

    private fun inicializarVistas() {
        etNombreEmpleado = findViewById(R.id.etNombreEmpleado)
        etSalarioBase = findViewById(R.id.etSalarioBase)
        btnCalcular = findViewById(R.id.btnCalcularSalario)
        tvResultado = findViewById(R.id.tvResultadoSalario)
    }

    private fun calcularSalarioNeto() {
        // Validar campos vacíos
        if (!validarCampos()) {
            return
        }

        val nombre = etNombreEmpleado.text.toString()

        try {
            val salarioBase = etSalarioBase.text.toString().toDouble()

            // Validar que el salario sea positivo
            if (salarioBase <= 0) {
                etSalarioBase.setError("El salario debe ser mayor a cero")
                Toast.makeText(this, "El salario debe ser un valor positivo", Toast.LENGTH_SHORT).show()
                return
            }

            // Calcular descuentos
            val descuentoRenta = calcularRenta(salarioBase)
            val descuentoAFP = salarioBase * 0.0725
            val descuentoISSS = salarioBase * 0.03

            val totalDescuentos = descuentoRenta + descuentoAFP + descuentoISSS
            val salarioNeto = salarioBase - totalDescuentos

            mostrarResultado(nombre, salarioBase, descuentoRenta, descuentoAFP,
                descuentoISSS, totalDescuentos, salarioNeto)

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Por favor ingrese un valor numérico válido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validarCampos(): Boolean {
        if (etNombreEmpleado.text.toString().trim().isEmpty()) {
            etNombreEmpleado.setError("Este campo es obligatorio")
            return false
        }

        if (etSalarioBase.text.toString().trim().isEmpty()) {
            etSalarioBase.setError("Este campo es obligatorio")
            return false
        }

        return true
    }

    private fun calcularRenta(salarioBase: Double): Double {
        return when {
            salarioBase <= 472.00 -> 0.0
            salarioBase <= 895.24 -> {
                val exceso = salarioBase - 472.00
                (exceso * 0.10) + 17.67
            }
            salarioBase <= 2038.10 -> {
                val exceso = salarioBase - 895.24
                (exceso * 0.20) + 60.00
            }
            else -> {
                val exceso = salarioBase - 2038.10
                (exceso * 0.30) + 288.57
            }
        }
    }

    private fun mostrarResultado(nombre: String, salarioBase: Double, renta: Double,
                                 afp: Double, isss: Double, totalDescuentos: Double,
                                 salarioNeto: Double) {
        val df = DecimalFormat("#.##")

        val mensaje = """
            DETALLE DE SALARIO
            
            Empleado: $nombre
            
            INGRESOS:
            Salario Base: $${df.format(salarioBase)}
            
            DESCUENTOS:
            Renta: $${df.format(renta)}
            AFP (7.25%): $${df.format(afp)}
            ISSS (3%): $${df.format(isss)}
            Total Descuentos: $${df.format(totalDescuentos)}
            
            SALARIO NETO: $${df.format(salarioNeto)}
        """.trimIndent()

        tvResultado.text = mensaje
        tvResultado.visibility = TextView.VISIBLE

        // También mostrar en AlertDialog
        AlertDialog.Builder(this)
            .setTitle("Cálculo de Salario")
            .setMessage(mensaje)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}