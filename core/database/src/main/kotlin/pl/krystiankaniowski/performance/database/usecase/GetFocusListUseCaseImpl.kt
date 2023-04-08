package pl.krystiankaniowski.performance.database.usecase

import kotlinx.coroutines.flow.map
import pl.krystiankaniowski.performance.database.dao.FocusDao
import pl.krystiankaniowski.performance.database.utils.toDomain
import pl.krystiankaniowski.performance.domain.stats.GetFocusListUseCase
import javax.inject.Inject

internal class GetFocusListUseCaseImpl @Inject constructor(private val focusDao: FocusDao) : GetFocusListUseCase {

    override suspend fun invoke() = focusDao.getAll().map { it.toDomain() }
}