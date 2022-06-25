package shvyn22.flexingfreegames.data.remote.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query
import shvyn22.flexingfreegames.data.remote.dto.DetailedGameDTO
import shvyn22.flexingfreegames.data.remote.dto.GameDTO

interface ApiService {

    @GET("games")
    fun getGames(
        @Query("platform") platform: String,
        @Query("sort-by") sort: String,
        @Query("category") category: String? = null,
    ): Single<List<GameDTO>>

    @GET("game")
    fun getGameDetails(
        @Query("id") id: Int,
    ): Single<DetailedGameDTO>
}