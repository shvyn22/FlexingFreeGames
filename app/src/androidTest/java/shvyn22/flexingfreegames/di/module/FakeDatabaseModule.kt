package shvyn22.flexingfreegames.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import shvyn22.flexingfreegames.data.local.AppDatabase
import shvyn22.flexingfreegames.data.local.dao.BookmarkDao
import javax.inject.Singleton

@Module
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