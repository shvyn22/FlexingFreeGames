package shvyn22.flexingfreegames.repository.remote

import io.reactivex.rxjava3.core.Observable
import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.util.Resource

interface RemoteRepository {

    fun getGames(
        platform: String,
        sort: String,
        category: String?,
    ): Observable<Resource<List<GameModel>>>

    fun getGameDetails(
        id: Int,
    ): Observable<Resource<DetailedGameModel>>
}