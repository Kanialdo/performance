package pl.krystiankaniowski.performance.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.database.dao.FocusDao

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {

    @Provides
    fun providesFocusDao(
        database: PerformanceDatabase,
    ): FocusDao = database.focusDao()
}