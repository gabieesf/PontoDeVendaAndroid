package com.seuprojeto.pdv

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegistrarVendaActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private lateinit var editQuantidade: EditText
    private lateinit var btnVender: Button
    private lateinit var db: DatabaseHelper
    private var produtos: List<Produto> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_venda)

        spinner = findViewById(R.id.spinnerProdutos)
        editQuantidade = findViewById(R.id.editQuantidadeVenda)
        btnVender = findViewById(R.id.btnRegistrarVenda)
        db = DatabaseHelper(this)

        produtos = db.listarProdutos()

        val nomes = produtos.map { "${it.nome} (Estoque: ${it.quantidade})" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nomes)
        spinner.adapter = adapter

        btnVender.setOnClickListener {
            val index = spinner.selectedItemPosition
            val produto = produtos[index]
            val qtd = editQuantidade.text.toString().toIntOrNull() ?: 0

            if (qtd > 0 && qtd <= produto.quantidade) {
                db.registrarVenda(produto.id, qtd)
                Toast.makeText(this, "Venda registrada", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Quantidade inválida", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
