package com.example.desafio01

    import android.os.Bundle
    import android.widget.Button
    import android.widget.EditText
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import kotlin.math.pow
    import kotlin.math.sqrt

    class CalculadoraActivity : AppCompatActivity() {

        private lateinit var etNumero1: EditText
        private lateinit var etNumero2: EditText
        private lateinit var btnSuma: Button
        private lateinit var btnResta: Button
        private lateinit var btnMultiplicacion: Button
        private lateinit var btnDivision: Button
        private lateinit var btnExponente: Button
        private lateinit var btnRaizCuadrada: Button
        private lateinit var btnLimpiar: Button
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
            btnRaizCuadrada = findViewById(R.id.btnRaizCuadrada)
            btnLimpiar = findViewById(R.id.btnLimpiar)
            tvResultado = findViewById(R.id.tvResultadoCalculadora)
        }

        private fun configurarEventos() {
            btnSuma.setOnClickListener {
                realizarOperacion("suma")
            }

            btnResta.setOnClickListener {
                realizarOperacion("resta")
            }

            btnMultiplicacion.setOnClickListener {
                realizarOperacion("multiplicacion")
            }

            btnDivision.setOnClickListener {
                realizarOperacion("division")
            }

            btnExponente.setOnClickListener {
                realizarOperacion("exponente")
            }

            btnRaizCuadrada.setOnClickListener {
                realizarRaizCuadrada()
            }

            btnLimpiar.setOnClickListener {
                limpiarCampos()
            }
        }

        private fun realizarOperacion(operacion: String) {
            if (!validarCampos(operacion)) {
                return
            }

            try {
                val numero1 = etNumero1.text.toString().toDouble()
                val numero2 = if (operacion != "raiz") etNumero2.text.toString().toDouble() else 0.0

                val resultado = when (operacion) {
                    "suma" -> sumar(numero1, numero2)
                    "resta" -> restar(numero1, numero2)
                    "multiplicacion" -> multiplicar(numero1, numero2)
                    "division" -> dividir(numero1, numero2)
                    "exponente" -> exponente(numero1, numero2)
                    else -> 0.0
                }

                if (resultado != Double.NaN && resultado != Double.POSITIVE_INFINITY && resultado != Double.NEGATIVE_INFINITY) {
                    mostrarResultado(numero1, numero2, operacion, resultado)
                }

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Por favor ingrese números válidos", Toast.LENGTH_SHORT).show()
            }
        }

        private fun realizarRaizCuadrada() {
            if (!validarCampos("raiz")) {
                return
            }

            try {
                val numero1 = etNumero1.text.toString().toDouble()

                if (numero1 < 0) {
                    Toast.makeText(this, "No se puede calcular la raíz cuadrada de un número negativo", Toast.LENGTH_SHORT).show()
                    return
                }

                val resultado = raizCuadrada(numero1)
                mostrarResultado(numero1, 0.0, "raiz", resultado)

            } catch (e: NumberFormatException) {
                Toast.makeText(this, "Por favor ingrese un número válido", Toast.LENGTH_SHORT).show()
            }
        }

        private fun validarCampos(operacion: String): Boolean {
            if (etNumero1.text.toString().trim().isEmpty()) {
                etNumero1.setError("Este campo es obligatorio")
                Toast.makeText(this, "Por favor ingrese el primer número", Toast.LENGTH_SHORT).show()
                return false
            }

            if (operacion != "raiz" && etNumero2.text.toString().trim().isEmpty()) {
                etNumero2.setError("Este campo es obligatorio")
                Toast.makeText(this, "Por favor ingrese el segundo número", Toast.LENGTH_SHORT).show()
                return false
            }

            return true
        }

        private fun sumar(a: Double, b: Double): Double = a + b

        private fun restar(a: Double, b: Double): Double = a - b

        private fun multiplicar(a: Double, b: Double): Double = a * b

        private fun dividir(a: Double, b: Double): Double {
            if (b == 0.0) {
                Toast.makeText(this, "Error: No se puede dividir entre cero", Toast.LENGTH_LONG).show()
                return Double.NaN
            }
            return a / b
        }

        private fun exponente(base: Double, exponente: Double): Double = base.pow(exponente)

        private fun raizCuadrada(numero: Double): Double = sqrt(numero)

        private fun mostrarResultado(num1: Double, num2: Double, operacion: String, resultado: Double) {
            val operadorTexto = when (operacion) {
                "suma" -> "+"
                "resta" -> "-"
                "multiplicacion" -> "×"
                "division" -> "÷"
                "exponente" -> "^"
                "raiz" -> "√"
                else -> ""
            }

            val expresion = if (operacion == "raiz") {
                "√$num1"
            } else {
                "$num1 $operadorTexto $num2"
            }

            val resultadoFormateado = if (resultado == resultado.toInt().toDouble()) {
                resultado.toInt().toString()
            } else {
                String.format("%.6f", resultado).trimEnd('0').trimEnd('.')
            }

            val textoResultado = """
            Operación: $expresion
            Resultado: $resultadoFormateado
        """.trimIndent()

            tvResultado.text = textoResultado
            tvResultado.visibility = TextView.VISIBLE
        }

        private fun limpiarCampos() {
            etNumero1.text.clear()
            etNumero2.text.clear()
            tvResultado.text = ""
            tvResultado.visibility = TextView.GONE
            etNumero1.clearFocus()
            etNumero2.clearFocus()
        }
    }
