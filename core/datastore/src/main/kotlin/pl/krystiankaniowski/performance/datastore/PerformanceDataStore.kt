package pl.krystiankaniowski.performance.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

private const val USER_PREFERENCES_NAME = "user_preferences"

private val Context.dataStore by preferencesDataStore(
    name = USER_PREFERENCES_NAME,
)

@Singleton
class PerformanceDataStore @Inject constructor(@ApplicationContext context: Context) : AppSettingsRepository {

    private val dataStore = context.dataStore

    override val isDndEnabled = dataStore.readBool(PreferencesKeys.IS_DND_ENABLED)
    override suspend fun updateIsDndEnabled(value: Boolean) {
        dataStore.writeBool(PreferencesKeys.IS_DND_ENABLED, value)
    }
}

internal object PreferencesKeys {
    const val IS_DND_ENABLED = "is_dnd_enabled"
}