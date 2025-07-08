package com.seuprojeto.pdv

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class RelatorioActivity : AppCompatActivity() {

    private lateinit var btnInicio: Button
    private lateinit var btnFim: Button
    private lateinit var btnGerar: Button
    private lateinit var listRelatorio: ListView
    private lateinit var db: DatabaseHelper

    private var dataInicio: Long = 0
    private var dataFim: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relatorio)

        btnInicio = findViewById(R.id.btnInicio)
        btnFim = findViewById(R.id.btnFim)
        btnGerar = findViewById(R.id.btnGerarRelatorio)
        listRelatorio = findViewById(R.id.listRelatorio)

        db = DatabaseHelper(this)

        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        btnInicio.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(this, { _, ano, mes, dia ->
                c.set(ano, mes, dia, 0, 0)
                dataInicio = c.timeInMillis
                btnInicio.text = "Início: ${formato.format(Date(dataInicio))}"
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnFim.setOnClickListener {
            val c = Calendar.getInstance()
            DatePickerDialog(this, { _, ano, mes, dia ->
                c.set(ano, mes, dia, 23, 59)
                dataFim = c.timeInMillis
                btnFim.text = "Fim: ${formato.format(Date(dataFim))}"
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show()
        }

        btnGerar.setOnClickListener {
            if (dataInicio == 0L || dataFim == 0L) {
                Toast.makeText(this, "Selecione as datas", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val vendas = db.listarVendasPorPeriodo(dataInicio, dataFim)
            val listaFormatada = vendas.map {
                "Produto: ${it.nome}\nQtd: ${it.quantidade}\nData: ${formato.format(Date(it.data.toLong()))}"
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaFormatada)
            listRelatorio.adapter = adapter
        }
    }
}
