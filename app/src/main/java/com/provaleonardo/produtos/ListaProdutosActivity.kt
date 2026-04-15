package com.provaleonardo.produtos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.provaleonardo.produtos.data.ProductDatabase
import com.provaleonardo.produtos.databinding.ActivityListaBinding

class ListaProdutosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaBinding
    private val adapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarLista)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.recyclerProdutos.layoutManager = LinearLayoutManager(this)
        binding.recyclerProdutos.adapter = adapter

        val dao = ProductDatabase.getDatabase(this).productDao()
        dao.getAllProducts().observe(this) { list ->
            adapter.submitList(list)
            binding.textEmpty.visibility =
                if (list.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        }

        binding.buttonVoltarCadastro.setOnClickListener { finish() }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
