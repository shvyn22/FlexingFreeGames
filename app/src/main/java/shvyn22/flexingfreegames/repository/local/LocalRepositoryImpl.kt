package shvyn22.flexingfreegames.repository.local

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import shvyn22.flexingfreegames.data.local.dao.BookmarkDao
import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.data.util.fromDetailedGameToGame
import shvyn22.flexingfreegames.util.Resource
import shvyn22.flexingfreegames.util.ResourceError

class LocalRepositoryImpl(
    private val dao: BookmarkDao,
) : LocalRepository {

    override fun getBookmarks(): Observable<Resource<List<GameModel>>> = Observable.create { sub ->
        sub.onNext(Resource.Loading())

        dao
            .getBookmarks()
            .subscribeOn(Schedulers.io())
            .subscribe {
                if (it.isEmpty())
                    sub.onNext(Resource.Error(ResourceError.NoBookmarks))
                else
                    sub.onNext(Resource.Success(it))
            }
    }

    override fun isGameBookmarked(id: Int): Single<Boolean> =
        dao
            .isGameBookmarked(id)
            .subscribeOn(Schedulers.io())

    override fun insertBookmark(item: DetailedGameModel) {
        dao
            .insertBookmark(fromDetailedGameToGame(item))
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    override fun deleteBookmark(id: Int) {
        dao
            .deleteBookmark(id)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}