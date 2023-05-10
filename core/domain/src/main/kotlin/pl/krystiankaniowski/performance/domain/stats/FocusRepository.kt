package pl.krystiankaniowski.performance.domain.stats

import kotlinx.coroutines.flow.Flow
import pl.krystiankaniowski.performance.model.Focus

interface FocusRepository {

    suspend fun get(id: Long): Focus

    fun getAll(): Flow<List<Focus>>

    suspend fun upsert(focus: Focus)

    suspend fun delete(id: Long)

    suspend fun deleteAll()
}