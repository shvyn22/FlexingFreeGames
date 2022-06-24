package shvyn22.flexingfreegames.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import shvyn22.flexingfreegames.data.local.AppDatabase
import shvyn22.flexingfreegames.data.local.dao.BookmarkDao
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
object FakeDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): AppDatabase =
        Room
            .inMemoryDatabaseBuilder(
                app,
                AppDatabase::class.java
            )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideBookmarkDao(db: AppDatabase): BookmarkDao =
        db.bookmarkDao()
}