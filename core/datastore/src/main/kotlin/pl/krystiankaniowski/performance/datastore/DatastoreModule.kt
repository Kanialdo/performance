package pl.krystiankaniowski.performance.datastore

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.domain.repository.AppSettingsRepository

@Module
@InstallIn(SingletonComponent::class)
interface DatastoreModule {

    @Binds
    fun bindPerformanceDataStore(impl: PerformanceDataStore): AppSettingsRepository
}
