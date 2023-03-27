package pl.krystiankaniowski.performance.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.BuildConfig
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Named("appVersion")
    @Provides
    fun provideAndroidNavigator() = BuildConfig.VERSION_NAME
}