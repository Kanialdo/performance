package pl.krystiankaniowski.performance.database.provider

import kotlinx.coroutines.flow.map
import pl.krystiankaniowski.performance.database.dao.FocusDao
import pl.krystiankaniowski.performance.database.utils.toDatabase
import pl.krystiankaniowski.performance.database.utils.toDomain
import pl.krystiankaniowski.performance.domain.stats.FocusRepository
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject

internal class FocusRepositoryImpl @Inject constructor(private val focusDao: FocusDao) : FocusRepository {

    override suspend fun get(id: Long) = focusDao.get(id).toDomain()

    override suspend fun getAll() = focusDao.getAll().map { it.toDomain() }

    override suspend fun insert(focus: Focus) = focusDao.insert(focus.toDatabase())

    override suspend fun upsert(focus: Focus) = focusDao.upsert(focus.toDatabase())

    override suspend fun delete(id: Long) = focusDao.delete(id)

    override suspend fun deleteAll() = focusDao.deleteAll()
}