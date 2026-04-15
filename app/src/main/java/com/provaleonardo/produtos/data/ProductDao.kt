package com.provaleonardo.produtos.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: Product): Long

    @Query("SELECT * FROM products ORDER BY name COLLATE NOCASE ASC")
    fun getAllProducts(): LiveData<List<Product>>
}
