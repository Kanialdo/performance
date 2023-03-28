package pl.krystiankaniowski.performance.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.krystiankaniowski.performance.domain.navigation.Navigator
import pl.krystiankaniowski.performance.navigation.AndroidNavigator


@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun AndroidNavigator.bindAndroidNavigator(): Navigator
}