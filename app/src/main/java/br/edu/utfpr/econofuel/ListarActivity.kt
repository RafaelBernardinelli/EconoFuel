package br.edu.utfpr.econofuel

import android.os.Bundle
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ListarActivity : AppCompatActivity() {

    private lateinit var lvCombustiveis : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)

        lvCombustiveis = findViewById( R.id.lvCombustiveis)

        lvCombustiveis.setOnItemClickListener{ parent, view, position, id ->
            val itemSelecionadoId = if (position == 0) 10 else 15

            intent.putExtra("codRetorno", itemSelecionadoId)

            setResult(RESULT_OK, intent)

            finish()

        }
    }
}