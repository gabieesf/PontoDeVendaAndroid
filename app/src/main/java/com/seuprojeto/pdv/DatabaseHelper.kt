package com.seuprojeto.pdv

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class VendaDetalhada(val nome: String, val quantidade: Int, val data: String)

data class Produto(
    val id: Int,
    val nome: String,
    val preco: Double,
    val quantidade: Int
)


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "pdv.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE produtos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT,
                preco REAL,
                quantidade INTEGER
            )
        """.trimIndent())

        db.execSQL("""
            CREATE TABLE vendas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                id_produto INTEGER,
                quantidade INTEGER,
                data TEXT
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS produtos")
        db.execSQL("DROP TABLE IF EXISTS vendas")
        onCreate(db)
    }

    fun registrarVenda(idProduto: Int, quantidadeVendida: Int) {
        val db = writableDatabase

        // Atualiza estoque
        db.execSQL("UPDATE produtos SET quantidade = quantidade - ? WHERE id = ?", arrayOf(quantidadeVendida, idProduto))

        // Registra venda
        val values = ContentValues().apply {
            put("id_produto", idProduto)
            put("quantidade", quantidadeVendida)
            put("data", System.currentTimeMillis().toString())
        }
        db.insert("vendas", null, values)

        db.close()
    }


    fun listarVendasPorPeriodo(inicio: Long, fim: Long): List<VendaDetalhada> {
        val lista = mutableListOf<VendaDetalhada>()
        val db = readableDatabase

        val query = """
        SELECT v.quantidade, v.data, p.nome
        FROM vendas v
        JOIN produtos p ON v.id_produto = p.id
        WHERE v.data BETWEEN ? AND ?
        ORDER BY v.data
    """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(inicio.toString(), fim.toString()))

        if (cursor.moveToFirst()) {
            do {
                val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                val qtd = cursor.getInt(cursor.getColumnIndexOrThrow("quantidade"))
                val data = cursor.getString(cursor.getColumnIndexOrThrow("data"))
                lista.add(VendaDetalhada(nome, qtd, data))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lista
    }
    fun listarProdutos(): List<Produto> {
        val lista = mutableListOf<Produto>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM produtos", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
                val preco = cursor.getDouble(cursor.getColumnIndexOrThrow("preco"))
                val quantidade = cursor.getInt(cursor.getColumnIndexOrThrow("quantidade"))

                lista.add(Produto(id, nome, preco, quantidade))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lista
    }
    fun adicionarProduto(nome: String, preco: Double, quantidade: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("nome", nome)
            put("preco", preco)
            put("quantidade", quantidade)
        }
        db.insert("produtos", null, values)
        db.close()
    }


}
