package pl.krystiankaniowski.performance.infrastructure.usecase

import pl.krystiankaniowski.performance.database.dao.FocusDao
import pl.krystiankaniowski.performance.domain.usecase.GetFocusListUseCase
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject

class GetFocusListUseCaseImpl @Inject constructor(private val focusDao: FocusDao) : GetFocusListUseCase {
    override suspend fun invoke() = focusDao.getAll().map { Focus(startDate = it.dateStart, endDate = it.dateEnd) }
}