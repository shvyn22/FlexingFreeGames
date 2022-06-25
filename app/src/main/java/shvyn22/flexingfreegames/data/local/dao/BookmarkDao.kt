package shvyn22.flexingfreegames.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import shvyn22.flexingfreegames.data.local.model.GameModel

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM Bookmark")
    fun getBookmarks(): Observable<List<GameModel>>

    @Query("SELECT EXISTS (SELECT 1 FROM Bookmark WHERE id = :id)")
    fun isGameBookmarked(id: Int): Single<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBookmark(item: GameModel): Completable

    @Query("DELETE FROM Bookmark WHERE id = :id")
    fun deleteBookmark(id: Int): Completable
}