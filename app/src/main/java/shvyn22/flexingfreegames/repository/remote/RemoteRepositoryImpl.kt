package shvyn22.flexingfreegames.repository.remote

import com.squareup.moshi.JsonDataException
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.data.remote.api.ApiService
import shvyn22.flexingfreegames.data.util.fromDetailedGameDTOToModel
import shvyn22.flexingfreegames.data.util.fromGameDTOToModel
import shvyn22.flexingfreegames.util.Resource
import shvyn22.flexingfreegames.util.ResourceError

class RemoteRepositoryImpl(
    private val api: ApiService
) : RemoteRepository {

    override fun getGames(
        platform: String,
        sort: String,
        category: String?
    ): Observable<Resource<List<GameModel>>> = Observable.create { sub ->
        sub.onNext(Resource.Loading())

        try {
            api
                .getGames(platform, sort, category)
                .subscribeOn(Schedulers.io())
                .subscribe { response ->
                    sub.onNext(Resource.Success(fromGameDTOToModel(response)))
                }
        } catch (e: JsonDataException) {
            sub.onNext(Resource.Error(ResourceError.Fetching))
        } catch (e: Exception) {
            sub.onNext(Resource.Error(ResourceError.Specified(e.localizedMessage ?: "")))
        }
    }

    override fun getGameDetails(
        id: Int
    ): Observable<Resource<DetailedGameModel>> = Observable.create { sub ->
        sub.onNext(Resource.Loading())

        try {
            api
                .getGameDetails(id)
                .subscribeOn(Schedulers.io())
                .subscribe { response ->
                    sub.onNext(Resource.Success(fromDetailedGameDTOToModel(response)))
                }
        } catch (e: JsonDataException) {
            sub.onNext(Resource.Error(ResourceError.Fetching))
        } catch (e: Exception) {
            sub.onNext(Resource.Error(ResourceError.Specified(e.localizedMessage ?: "")))
        }
    }
}