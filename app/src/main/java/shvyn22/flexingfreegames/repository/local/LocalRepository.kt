package shvyn22.flexingfreegames.repository.local

import kotlinx.coroutines.flow.Flow
import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.util.Resource

interface LocalRepository {

    fun getBookmarks(): Flow<Resource<List<GameModel>>>

    suspend fun insertBookmark(item: DetailedGameModel)

    suspend fun deleteBookmark(id: Int)
}