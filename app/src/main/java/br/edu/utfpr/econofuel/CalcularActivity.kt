package br.edu.utfpr.econofuel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CalcularActivity : AppCompatActivity() {

    private lateinit var etFirstConsume : EditText
    private lateinit var etFieldSecoundConsume : EditText
    private lateinit var etValorGasolina : EditText
    private lateinit var etValorAlcool : EditText
    private lateinit var txtResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular)

        etFirstConsume = findViewById( R.id.etFirstConsume )
        etFieldSecoundConsume = findViewById( R.id.etFieldSecoundConsume )
        etValorGasolina = findViewById( R.id.etValorGasolina )
        etValorAlcool = findViewById( R.id.etValorAlcool )
        txtResultado = findViewById(R.id.txtResultado)

        val btnCalcular: Button = findViewById(R.id.btnCalcular)
        btnCalcular.setOnClickListener {
            calcularMelhorCombustivel()
        }
    }

    private fun calcularMelhorCombustivel() {
        val consumoGasolina = etFirstConsume.text.toString()
        val consumoAlcool = etFieldSecoundConsume.text.toString()
        val valorGasolina = etValorGasolina.text.toString()
        val valorAlcool = etValorAlcool.text.toString()

        if (consumoGasolina.isEmpty() || consumoAlcool.isEmpty() || valorGasolina.isEmpty() || valorAlcool.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val consumoGasolinaDouble = consumoGasolina.toDouble()
        val consumoAlcoolDouble = consumoAlcool.toDouble()
        val valorGasolinaDouble = valorGasolina.toDouble()
        val valorAlcoolDouble = valorAlcool.toDouble()

        val custoGasolinaPorKm = valorGasolinaDouble / consumoGasolinaDouble
        val custoAlcoolPorKm = valorAlcoolDouble / consumoAlcoolDouble

        val melhorCombustivel = if (custoGasolinaPorKm < custoAlcoolPorKm) {
            "Melhor combustível: Gasolina (Custo por km: R$ ${"%.2f".format(custoGasolinaPorKm)})"
        } else {
            "Melhor combustível: Álcool (Custo por km: R$ ${"%.2f".format(custoAlcoolPorKm)})"
        }

        txtResultado.text = melhorCombustivel
    }

    fun btListarOnClick(view: View) {
        val intent = Intent(this, ListarActivity::class.java)
        getResult.launch(intent)
    }

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val codRetorno = it.data?.getIntExtra("codRetorno", 0)
            if (codRetorno == 10) {
                if (etFirstConsume.text.isNotEmpty()) {
                    Toast.makeText(this, "Você já selecionou este combustivel no outro campo", Toast.LENGTH_SHORT).show()
                    return@registerForActivityResult
                }
                etFirstConsume.setText("Gasolina: $codRetorno")
            }

            else if (codRetorno == 15) {
                if (etFieldSecoundConsume.text.isNotEmpty()) {
                    Toast.makeText(this, "Você já selecionou este combustivel no outro campo", Toast.LENGTH_SHORT).show()
                    return@registerForActivityResult
                }
                etFieldSecoundConsume.setText("Álcool: $codRetorno")
            }
        }
    }
}