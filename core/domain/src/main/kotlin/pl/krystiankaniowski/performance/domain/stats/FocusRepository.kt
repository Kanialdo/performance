package pl.krystiankaniowski.performance.domain.stats

import pl.krystiankaniowski.performance.model.Focus

interface FocusRepository {

    suspend fun get(id: Long): Focus
    
    suspend fun getAll(): List<Focus>

    suspend fun add(focus: Focus)

    suspend fun delete(id: Long)

    suspend fun deleteAll()
}