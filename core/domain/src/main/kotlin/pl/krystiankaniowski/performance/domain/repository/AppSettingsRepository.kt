package pl.krystiankaniowski.performance.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {

    fun getBoolean(key: String): Flow<Boolean>

    suspend fun updateBoolean(key: String, value: Boolean)
}