package com.seuprojeto.pdv

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class CadastroProdutoActivity : AppCompatActivity() {

    private lateinit var editNome: EditText
    private lateinit var editPreco: EditText
    private lateinit var editQuantidade: EditText
    private lateinit var btnSalvar: Button
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_produto)

        // Inicializa componentes da tela
        editNome = findViewById(R.id.editNome)
        editPreco = findViewById(R.id.editPreco)
        editQuantidade = findViewById(R.id.editQuantidade)
        btnSalvar = findViewById(R.id.btnSalvar)

        // Inicializa o banco de dados
        db = DatabaseHelper(this)

        btnSalvar.setOnClickListener {
            val nome = editNome.text.toString()
            val preco = editPreco.text.toString().toDoubleOrNull() ?: 0.0
            val qtd = editQuantidade.text.toString().toIntOrNull() ?: 0

            db.adicionarProduto(nome, preco, qtd)

            Toast.makeText(this, "Produto cadastrado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
