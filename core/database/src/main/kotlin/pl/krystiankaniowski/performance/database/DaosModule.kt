package pl.krystiankaniowski.performance.database

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.database.dao.WorkLogDao

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {

    @Provides
    fun providesWorkLogDao(
        database: PerformanceDatabase,
    ): WorkLogDao = database.workLogDao()
}