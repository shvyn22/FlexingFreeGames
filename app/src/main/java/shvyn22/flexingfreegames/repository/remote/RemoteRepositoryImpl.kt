package shvyn22.flexingfreegames.repository.remote

import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
    ): Flow<Resource<List<GameModel>>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.getGames(platform, sort, category)
            emit(Resource.Success(fromGameDTOToModel(response)))
        } catch (e: JsonDataException) {
            emit(Resource.Error(ResourceError.Fetching))
        } catch (e: Exception) {
            emit(Resource.Error(ResourceError.Specified(e.localizedMessage ?: "")))
        }
    }

    override fun getGameDetails(id: Int): Flow<Resource<DetailedGameModel>> = flow {
        emit(Resource.Loading())

        try {
            val response = api.getGameDetails(id)
            emit(Resource.Success(fromDetailedGameDTOToModel(response)))
        } catch (e: JsonDataException) {
            emit(Resource.Error(ResourceError.Fetching))
        } catch (e: Exception) {
            emit(Resource.Error(ResourceError.Specified(e.localizedMessage ?: "")))
        }
    }
}