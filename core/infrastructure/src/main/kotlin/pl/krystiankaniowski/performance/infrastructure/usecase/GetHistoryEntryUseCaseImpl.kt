package pl.krystiankaniowski.performance.infrastructure.usecase

import pl.krystiankaniowski.performance.database.dao.FocusDao
import pl.krystiankaniowski.performance.domain.usecase.GetHistoryEntryUseCase
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject

class GetHistoryEntryUseCaseImpl @Inject constructor(private val focusDao: FocusDao) : GetHistoryEntryUseCase {

    override suspend operator fun invoke(id: Long): Focus {
        return focusDao.get(id).let {
            Focus(id = id, startDate = it.dateStart, endDate = it.dateEnd)
        }
    }
}