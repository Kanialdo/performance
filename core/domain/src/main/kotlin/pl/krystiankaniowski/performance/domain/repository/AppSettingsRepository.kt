package pl.krystiankaniowski.performance.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {

    val isDndEnabled: Flow<Boolean>

    suspend fun updateIsDndEnabled(value: Boolean)
}