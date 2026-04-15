package com.provaleonardo.produtos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.provaleonardo.produtos.data.Product
import com.provaleonardo.produtos.databinding.ItemProductBinding
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductViewHolder>(DIFF) {

    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.textNome.text = product.name
            binding.textCodigo.text = binding.root.context.getString(
                R.string.label_codigo_list,
                product.code
            )
            binding.textPreco.text = currencyFormat.format(product.price)
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }
}
