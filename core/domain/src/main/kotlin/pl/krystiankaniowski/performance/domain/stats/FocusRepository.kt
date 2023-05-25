package pl.krystiankaniowski.performance.domain.stats

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import pl.krystiankaniowski.performance.model.Focus

interface FocusRepository {

    suspend fun get(id: Long): Focus

    suspend fun observe(id: Long): Flow<Focus?>

    suspend fun getAll(): Flow<List<Focus>>

    suspend fun getAll(from: Instant, to: Instant): Flow<List<Focus>>

    suspend fun insert(focus: Focus)

    suspend fun upsert(focus: Focus)

    suspend fun delete(id: Long)

    suspend fun deleteAll()
}