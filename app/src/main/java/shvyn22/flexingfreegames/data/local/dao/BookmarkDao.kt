package shvyn22.flexingfreegames.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import shvyn22.flexingfreegames.data.local.model.GameModel

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM Bookmark")
    fun getBookmarks(): Flow<List<GameModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(item: GameModel)

    @Query("DELETE FROM Bookmark WHERE id = :id")
    suspend fun deleteBookmark(id: Int)
}