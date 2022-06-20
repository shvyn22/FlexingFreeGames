package shvyn22.flexingfreegames.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shvyn22.flexingfreegames.data.local.dao.BookmarkDao
import shvyn22.flexingfreegames.data.remote.api.ApiService
import shvyn22.flexingfreegames.repository.local.LocalRepository
import shvyn22.flexingfreegames.repository.local.LocalRepositoryImpl
import shvyn22.flexingfreegames.repository.remote.RemoteRepository
import shvyn22.flexingfreegames.repository.remote.RemoteRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLocalRepository(
        dao: BookmarkDao
    ): LocalRepository = LocalRepositoryImpl(dao)

    @Singleton
    @Provides
    fun provideRemoteRepository(
        api: ApiService
    ): RemoteRepository = RemoteRepositoryImpl(api)
}