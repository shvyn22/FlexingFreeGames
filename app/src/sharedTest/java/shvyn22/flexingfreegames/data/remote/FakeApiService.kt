package shvyn22.flexingfreegames.data.remote

import io.reactivex.rxjava3.core.Single
import shvyn22.flexingfreegames.data.remote.api.ApiService
import shvyn22.flexingfreegames.data.remote.dto.DetailedGameDTO
import shvyn22.flexingfreegames.data.remote.dto.GameDTO
import shvyn22.flexingfreegames.util.game1
import shvyn22.flexingfreegames.util.game2
import shvyn22.flexingfreegames.util.game3

class FakeApiService(
    private var shouldFail: Boolean = false
) : ApiService {

    fun changeFailBehaviour(shouldFail: Boolean) {
        this.shouldFail = shouldFail
    }

    override fun getGames(
        platform: String,
        sort: String,
        category: String?
    ): Single<List<GameDTO>> {
        if (!shouldFail) {
            if (platform == "all" || platform == "pc") {
                if (category == "shooter")
                    return Single.just(listOf(game1))
                else if (category == "mmorpg")
                    return Single.just(listOf(game2))

                if (sort == "popularity")
                    return Single.just(listOf(game1, game2))
                else if (sort == "release-date")
                    return Single.just(listOf(game2, game1))
            } else return Single.just(emptyList())
        }
        throw IllegalArgumentException()
    }

    override fun getGameDetails(id: Int): Single<DetailedGameDTO> {
        if (!shouldFail)
            return Single.just(game3)
        throw IllegalArgumentException()
    }
}