package shvyn22.flexingfreegames.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import shvyn22.flexingfreegames.data.remote.dto.DetailedGameDTO
import shvyn22.flexingfreegames.data.remote.dto.GameDTO

interface ApiService {

    @GET("games")
    suspend fun getGames(
        @Query("platform") platform: String,
        @Query("sort-by") sort: String,
        @Query("category") category: String? = null,
    ): List<GameDTO>

    @GET("game")
    suspend fun getGameDetails(
        @Query("id") id: Int,
    ): DetailedGameDTO
}