package pl.krystiankaniowski.performance.database.usecase

import pl.krystiankaniowski.performance.database.dao.FocusDao
import pl.krystiankaniowski.performance.database.utils.toDomain
import pl.krystiankaniowski.performance.domain.usecase.GetHistoryEntryUseCase
import javax.inject.Inject

internal class GetHistoryEntryUseCaseImpl @Inject constructor(private val focusDao: FocusDao) : GetHistoryEntryUseCase {

    override suspend operator fun invoke(id: Long) = focusDao.get(id).toDomain()
}