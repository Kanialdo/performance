package pl.krystiankaniowski.performance.database.usecase

import pl.krystiankaniowski.performance.database.dao.FocusDao
import pl.krystiankaniowski.performance.database.model.FocusEntity
import pl.krystiankaniowski.performance.domain.usecase.SaveFocusUseCase
import pl.krystiankaniowski.performance.model.Focus
import javax.inject.Inject

internal class SaveFocusUseCaseImpl @Inject constructor(private val focusDao: FocusDao) : SaveFocusUseCase {
    override suspend fun invoke(focus: Focus) {
        focusDao.insert(
            FocusEntity(
                uid = 0,
                dateStart = focus.startDate,
                dateEnd = focus.endDate,
            ),
        )
    }
}