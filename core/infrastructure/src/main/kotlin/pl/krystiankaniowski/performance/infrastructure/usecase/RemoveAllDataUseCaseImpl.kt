package pl.krystiankaniowski.performance.infrastructure.usecase

import pl.krystiankaniowski.performance.database.dao.FocusDao
import pl.krystiankaniowski.performance.domain.RemoveAllDataUseCase
import javax.inject.Inject

class RemoveAllDataUseCaseImpl @Inject constructor(
    private val focusDao: FocusDao,
) : RemoveAllDataUseCase {

    override suspend fun invoke() {
        focusDao.deleteAll()
    }
}