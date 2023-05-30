package com.example.itv_citas.services.storage

import com.github.michaelbull.result.Result

interface StorageService<T, ERR> {
    fun save(element: T, filePath: String): Result<T, ERR>
    fun saveAll(elements: List<T>, filePath: String): Result<List<T>, ERR>
    fun loadAll(filePath: String): Result<List<T>, ERR>
}