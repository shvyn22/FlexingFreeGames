package shvyn22.flexingfreegames.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import shvyn22.flexingfreegames.data.local.dao.BookmarkDao
import shvyn22.flexingfreegames.data.local.model.GameModel

@Database(
    entities = [GameModel::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao
}