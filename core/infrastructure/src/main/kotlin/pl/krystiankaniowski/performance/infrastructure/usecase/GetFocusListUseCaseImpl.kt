package pl.krystiankaniowski.performance.infrastructure.usecase

import pl.krystiankaniowski.performance.database.dao.FocusDao
import pl.krystiankaniowski.performance.domain.usecase.GetFocusListUseCase
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject

class GetFocusListUseCaseImpl @Inject constructor(private val focusDao: FocusDao) : GetFocusListUseCase {
    override suspend fun invoke(): List<Focus> {
        return focusDao.getAll()
            .map {
                Focus(
                    id = it.uid.toLong(),
                    startDate = it.dateStart,
                    endDate = it.dateEnd,
                )
            }
    }
}