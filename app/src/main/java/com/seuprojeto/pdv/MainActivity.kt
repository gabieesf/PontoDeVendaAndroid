package com.seuprojeto.pdv

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCadastrar = findViewById<Button>(R.id.btnCadastrarProduto)
        btnCadastrar.setOnClickListener {
            Toast.makeText(this, "Botão clicado!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CadastroProdutoActivity::class.java))
        }
        val btnVerProdutos = findViewById<Button>(R.id.btnVerProdutos)
        btnVerProdutos.setOnClickListener {
            startActivity(Intent(this, VerProdutosActivity::class.java))
        }

        val btnVenda = findViewById<Button>(R.id.btnRegistrarVenda)
        btnVenda.setOnClickListener {
            startActivity(Intent(this, RegistrarVendaActivity::class.java))
        }

        val btnRelatorio = findViewById<Button>(R.id.btnRelatorio)
        btnRelatorio.setOnClickListener {
            startActivity(Intent(this, RelatorioActivity::class.java))
        }

    }
}


