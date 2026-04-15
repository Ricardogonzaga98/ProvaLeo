package com.provaleonardo.produtos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.provaleonardo.produtos.data.Product
import com.provaleonardo.produtos.data.ProductDatabase
import com.provaleonardo.produtos.databinding.ActivityCadastroBinding
import kotlinx.coroutines.launch

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarCadastro)

        binding.buttonSalvar.setOnClickListener { salvarProduto() }
        binding.buttonVerLista.setOnClickListener {
            startActivity(Intent(this, ListaProdutosActivity::class.java))
        }
    }

    private fun salvarProduto() {
        val nome = binding.inputNome.text?.toString().orEmpty()
        val codigo = binding.inputCodigo.text?.toString().orEmpty()
        val precoStr = binding.inputPreco.text?.toString().orEmpty()
        val qtdStr = binding.inputQuantidade.text?.toString().orEmpty()

        if (!InputValidation.isNonBlank(nome)) {
            binding.layoutNome.error = getString(R.string.error_required)
            return
        }
        binding.layoutNome.error = null

        if (!InputValidation.isValidProductCode(codigo)) {
            binding.layoutCodigo.error = getString(R.string.error_code_invalid)
            return
        }
        binding.layoutCodigo.error = null

        if (!InputValidation.isValidPrice(precoStr)) {
            binding.layoutPreco.error = getString(R.string.error_price_invalid)
            return
        }
        binding.layoutPreco.error = null

        if (!InputValidation.isValidQuantity(qtdStr)) {
            binding.layoutQuantidade.error = getString(R.string.error_quantity_invalid)
            return
        }
        binding.layoutQuantidade.error = null

        val product = Product(
            name = nome.trim(),
            code = codigo.trim(),
            price = InputValidation.parsePrice(precoStr),
            quantity = InputValidation.parseQuantity(qtdStr)
        )

        val dao = ProductDatabase.getDatabase(this).productDao()
        lifecycleScope.launch {
            dao.insert(product)
            Toast.makeText(
                this@CadastroActivity,
                R.string.toast_saved,
                Toast.LENGTH_SHORT
            ).show()
            limparCampos()
        }
    }

    private fun limparCampos() {
        binding.inputNome.text = null
        binding.inputCodigo.text = null
        binding.inputPreco.text = null
        binding.inputQuantidade.text = null
    }
}
