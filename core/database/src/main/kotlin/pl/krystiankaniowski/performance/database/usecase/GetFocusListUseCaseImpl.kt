package pl.krystiankaniowski.performance.database.usecase

import pl.krystiankaniowski.performance.database.dao.FocusDao
import pl.krystiankaniowski.performance.database.utils.toDomain
import pl.krystiankaniowski.performance.domain.usecase.GetFocusListUseCase
import javax.inject.Inject

internal class GetFocusListUseCaseImpl @Inject constructor(private val focusDao: FocusDao) : GetFocusListUseCase {

    override suspend fun invoke() = focusDao.getAll().toDomain()
}