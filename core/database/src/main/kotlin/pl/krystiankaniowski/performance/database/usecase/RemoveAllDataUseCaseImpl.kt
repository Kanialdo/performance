package pl.krystiankaniowski.performance.database.usecase

import pl.krystiankaniowski.performance.database.dao.FocusDao
import pl.krystiankaniowski.performance.domain.stats.RemoveAllDataUseCase
import javax.inject.Inject

internal class RemoveAllDataUseCaseImpl @Inject constructor(
    private val focusDao: FocusDao,
) : RemoveAllDataUseCase {

    override suspend fun invoke() {
        focusDao.deleteAll()
    }
}