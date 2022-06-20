package shvyn22.flexingfreegames.repository.remote

import kotlinx.coroutines.flow.Flow
import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.util.Resource

interface RemoteRepository {

    fun getGames(
        platform: String,
        sort: String,
        category: String?,
    ): Flow<Resource<List<GameModel>>>

    fun getGameDetails(
        id: Int,
    ): Flow<Resource<DetailedGameModel>>
}