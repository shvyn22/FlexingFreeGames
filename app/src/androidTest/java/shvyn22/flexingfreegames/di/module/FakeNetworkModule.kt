package shvyn22.flexingfreegames.di.module

import dagger.Module
import dagger.Provides
import shvyn22.flexingfreegames.data.remote.FakeApiService
import shvyn22.flexingfreegames.data.remote.api.ApiService
import javax.inject.Singleton

@Module
object FakeNetworkModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService = FakeApiService()
}