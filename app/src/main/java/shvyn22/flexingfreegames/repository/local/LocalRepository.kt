package shvyn22.flexingfreegames.repository.local

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.util.Resource

interface LocalRepository {

    fun getBookmarks(): Observable<Resource<List<GameModel>>>

    fun isGameBookmarked(id: Int): Single<Boolean>

    fun insertBookmark(item: DetailedGameModel)

    fun deleteBookmark(id: Int)
}