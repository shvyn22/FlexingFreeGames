package shvyn22.flexingfreegames.repository.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import shvyn22.flexingfreegames.data.local.dao.BookmarkDao
import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.data.util.fromDetailedGameToGame
import shvyn22.flexingfreegames.util.Resource
import shvyn22.flexingfreegames.util.ResourceError

class LocalRepositoryImpl(
    private val dao: BookmarkDao,
) : LocalRepository {

    override fun getBookmarks(): Flow<Resource<List<GameModel>>> = dao.getBookmarks().transform {
        emit(Resource.Loading())

        if (it.isEmpty())
            emit(Resource.Error(ResourceError.NoBookmarks))
        else
            emit(Resource.Success(it))
    }

    override suspend fun insertBookmark(item: DetailedGameModel) {
        dao.insertBookmark(fromDetailedGameToGame(item))
    }

    override suspend fun deleteBookmark(id: Int) {
        dao.deleteBookmark(id)
    }
}