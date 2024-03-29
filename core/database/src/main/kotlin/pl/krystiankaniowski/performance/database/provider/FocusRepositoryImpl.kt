package pl.krystiankaniowski.performance.database.provider

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import pl.krystiankaniowski.performance.database.dao.FocusDao
import pl.krystiankaniowski.performance.database.utils.toDatabase
import pl.krystiankaniowski.performance.database.utils.toDomain
import pl.krystiankaniowski.performance.domain.stats.FocusRepository
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject

internal class FocusRepositoryImpl @Inject constructor(private val focusDao: FocusDao) : FocusRepository {

    override suspend fun get(id: Long) = focusDao.get(id).toDomain()

    override suspend fun observe(id: Long): Flow<Focus?> = focusDao.observe(id).map { it?.toDomain() }

    override suspend fun getAll() = focusDao.getAll().map { it.toDomain() }

    override suspend fun getAll(from: Instant, to: Instant): Flow<List<Focus>> {
        return focusDao.getAll(from, to).map { it.toDomain() }
    }

    override suspend fun insert(focus: Focus) = focusDao.insert(focus.toDatabase())

    override suspend fun upsert(focus: Focus) = focusDao.upsert(focus.toDatabase())

    override suspend fun delete(id: Long) = focusDao.delete(id)

    override suspend fun deleteAll() = focusDao.deleteAll()
}