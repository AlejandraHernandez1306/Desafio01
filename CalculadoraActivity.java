package com.example.desafio01;

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow
import kotlin.math.sqrt
public class CalculadoraActivity {
    private lateinit var etNumero1: EditText
    private lateinit var etNumero2: EditText
    private lateinit var btnSuma: Button
    private lateinit var btnResta: Button
    private lateinit var btnMultiplicacion: Button
    private lateinit var btnDivision: Button
    private lateinit var btnExponente: Button
    private lateinit var btnRaiz: Button
    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        inicializarVistas()
        configurarEventos()
    }

    private fun inicializarVistas() {
        etNumero1 = findViewById(R.id.etNumero1)
        etNumero2 = findViewById(R.id.etNumero2)
        btnSuma = findViewById(R.id.btnSuma)
        btnResta = findViewById(R.id.btnResta)
        btnMultiplicacion = findViewById(R.id.btnMultiplicacion)
        btnDivision = findViewById(R.id.btnDivision)
        btnExponente = findViewById(R.id.btnExponente)
        btnRaiz = findViewById(R.id.btnRaiz)
        tvResultado = findViewById(R.id.tvResultado)
    }

    private fun configurarEventos() {
        btnSuma.setOnClickListener { realizarOperacion("suma") }
        btnResta.setOnClickListener { realizarOperacion("resta") }
        btnMultiplicacion.setOnClickListener { realizarOperacion("multiplicacion") }
        btnDivision.setOnClickListener { realizarOperacion("division") }
        btnExponente.setOnClickListener { realizarOperacion("exponente") }
        btnRaiz.setOnClickListener { realizarOperacion("raiz") }
    }

    private fun realizarOperacion(operacion: String) {
        try {
            when (operacion) {
                "raiz" -> {
                    if (etNumero1.text.toString().trim().isEmpty()) {
                        etNumero1.error = "Ingrese un número"
                        etNumero1.requestFocus()
                        return
                    }
                    val numero = etNumero1.text.toString().toDouble()
                    if (numero < 0) {
                        Toast.makeText(this, "No se puede calcular raíz de número negativo", Toast.LENGTH_SHORT).show()
                        return
                    }
                    val resultado = sqrt(numero)
                    tvResultado.text = "√$numero = $resultado"
                    tvResultado.visibility = View.VISIBLE
                }
                else -> {
                    if (!validarDosNumeros()) return

                            val num1 = etNumero1.text.toString().toDouble()
                    val num2 = etNumero2.text.toString().toDouble()

                    val resultado = when (operacion) {
                        "suma" -> num1 + num2
                        "resta" -> num1 - num2
                        "multiplicacion" -> num1 * num2
                        "division" -> {
                            if (num2 == 0.0) {
                                Toast.makeText(this, "No se puede dividir entre cero", Toast.LENGTH_SHORT).show()
                                return
                            }
                            num1 / num2
                        }
                        "exponente" -> num1.pow(num2)
                        else -> 0.0
                    }

                    val simbolo = when (operacion) {
                        "suma" -> "+"
                        "resta" -> "−"
                        "multiplicacion" -> "×"
                        "division" -> "÷"
                        "exponente" -> "^"
                        else -> ""
                    }

                    tvResultado.text = "$num1 $simbolo $num2 = $resultado"
                    tvResultado.visibility = View.VISIBLE
                }
            }
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Ingrese números válidos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validarDosNumeros(): Boolean {
        if (etNumero1.text.toString
}
