package shvyn22.flexingfreegames.data.remote.api

import shvyn22.flexingfreegames.data.remote.dto.DetailedGameDTO
import shvyn22.flexingfreegames.data.remote.dto.GameDTO
import shvyn22.flexingfreegames.util.game1
import shvyn22.flexingfreegames.util.game2
import shvyn22.flexingfreegames.util.game3
import java.lang.IllegalArgumentException

class FakeApiService(
    private var shouldFail: Boolean = false
) : ApiService {

    fun changeFailBehaviour(shouldFail: Boolean) {
        this.shouldFail = shouldFail
    }

    override suspend fun getGames(
        platform: String,
        sort: String,
        category: String?
    ): List<GameDTO> {
        if (!shouldFail) {
            if (platform == "all" || platform == "pc") {
                if (category == "shooter")
                    return listOf(game1)
                else if (category == "mmorpg")
                    return listOf(game2)

                if (sort == "popularity")
                    return listOf(game1, game2)
                else if (sort == "release-date")
                    return listOf(game2, game1)
            } else return emptyList()
        }
        throw IllegalArgumentException()
    }

    override suspend fun getGameDetails(id: Int): DetailedGameDTO {
        if (!shouldFail)
            return game3
        throw IllegalArgumentException()
    }
}