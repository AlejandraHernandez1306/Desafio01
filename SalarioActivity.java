package com.example.desafio01;

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class Salario Activity : AppCompatActivity() {

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
            btnCalcular = findViewById(R.id.btnCalcular)
            tvResultado = findViewById(R.id.tvResultado)
        }

        private fun calcularSalarioNeto() {
            if (!validarCampos()) return

                    val nombre = etNombreEmpleado.text.toString()

            try {
                val salarioBase = etSalarioBase.text.toString().toDouble()

                if (salarioBase <= 0) {
                    etSalarioBase.error = "El salario debe ser positivo"
                    etSalarioBase.requestFocus()
                    return
                }

                val descuentoAFP = salarioBase * 0.0725
                val descuentoISSS = salarioBase * 0.03
                val descuentoRenta = calcularDescuentoRenta(salarioBase)

                val totalDescuentos = descuentoAFP + descuentoISSS + descuentoRenta
                val salarioNeto = salarioBase - totalDescuentos

                mostrarResultados(nombre, salarioBase, descuentoAFP, descuentoISSS, descuentoRenta, totalDescuentos, salarioNeto)

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Ingrese un salario válido", Toast.LENGTH_SHORT).show()
            }
        }

        private fun validarCampos(): Boolean {
            if (etNombreEmpleado.text.toString().trim().isEmpty()) {
                etNombreEmpleado.error = "El nombre es obligatorio"
                etNombreEmpleado.requestFocus()
                return false
            }
            if (etSalarioBase.text.toString().trim().isEmpty()) {
                etSalarioBase.error = "El salario es obligatorio"
                etSalarioBase.requestFocus()
                return false
            }
            return true
        }

        private fun calcularDescuentoRenta(salario: Double): Double {
            return when {
                salario <= 472.00 -> 0.0
                salario <= 895.24 -> (salario - 472.00) * 0.10 + 17.67
                salario <= 2038.10 -> (salario - 895.24) * 0.20 + 60.00
            else -> (salario - 2038.10) * 0.30 + 288.57
            }
        }

        private fun mostrarResultados(nombre: String, salarioBase: Double, afp: Double,
                isss: Double, renta: Double, totalDesc: Double, neto: Double) {
            val df = DecimalFormat("$#,##0.00")

            val resultado = """
            EMPLEADO: $nombre

            SALARIO BASE: ${df.format(salarioBase)}

            DESCUENTOS:
            • AFP (7.25%): ${df.format(afp)}
            • ISSS (3%): ${df.format(isss)}
            • Renta: ${df.format(renta)}

            TOTAL DESCUENTOS: ${df.format(totalDesc)}
            SALARIO NETO: ${df.format(neto)}
        """.trimIndent()

            tvResultado.text = resultado
            tvResultado.visibility = View.VISIBLE
        }
    }

}
