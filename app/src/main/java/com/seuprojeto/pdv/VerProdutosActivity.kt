package com.seuprojeto.pdv

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class VerProdutosActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_produtos)

        listView = findViewById(R.id.listProdutos)
        db = DatabaseHelper(this)

        val produtos = db.listarProdutos()
        val listaFormatada = produtos.map { "Produto: ${it.nome}\nPreço: R$ ${it.preco}\nQtd: ${it.quantidade}" }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaFormatada)
        listView.adapter = adapter

        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish() // fecha a tela atual e volta para o menu
        }

    }

}
