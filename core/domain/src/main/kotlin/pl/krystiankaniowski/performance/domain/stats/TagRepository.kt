package pl.krystiankaniowski.performance.domain.stats

import kotlinx.coroutines.flow.Flow
import pl.krystiankaniowski.performance.model.Tag

interface TagRepository {

    fun getAll(): Flow<List<Tag>>
}