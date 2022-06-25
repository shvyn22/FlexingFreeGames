package shvyn22.flexingfreegames.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import shvyn22.flexingfreegames.data.local.AppDatabase
import shvyn22.flexingfreegames.data.local.dao.BookmarkDao
import shvyn22.flexingfreegames.util.DATABASE_NAME
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app: Application): AppDatabase =
        Room
            .databaseBuilder(
                app,
                AppDatabase::class.java,
                DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideBookmarkDao(db: AppDatabase): BookmarkDao =
        db.bookmarkDao()
}