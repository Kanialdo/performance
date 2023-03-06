package pl.krystiankaniowski.performance.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.BuildConfig
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object InfoModule {

    @Named("appVersion")
    @Provides
    fun provideApplicationVersion() = BuildConfig.VERSION_NAME
}