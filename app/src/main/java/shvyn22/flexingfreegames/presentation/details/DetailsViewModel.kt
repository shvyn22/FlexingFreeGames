package shvyn22.flexingfreegames.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.repository.local.LocalRepository
import shvyn22.flexingfreegames.repository.remote.RemoteRepository
import shvyn22.flexingfreegames.util.Resource
import shvyn22.flexingfreegames.util.ResourceError
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val remoteRepo: RemoteRepository,
    private val localRepo: LocalRepository
) : ViewModel() {

    private val _detailsState = MutableLiveData<DetailsState>(DetailsState.LoadingState)
    val detailsState: LiveData<DetailsState> get() = _detailsState

    private val _detailsEvent = PublishSubject.create<DetailsEvent>()
    val detailsEvent: Observable<DetailsEvent>
        get() = _detailsEvent.flatMap { Observable.just(it) }

    fun handleIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.LoadGameIntent ->
                loadGame(intent.id)
            is DetailsIntent.InsertBookmarkIntent ->
                insertBookmark(intent.item)
            is DetailsIntent.DeleteBookmarkIntent ->
                deleteBookmark(intent.id)
            is DetailsIntent.FreeToGameIconClickIntent ->
                emitNavigateToFreeToGameEvent(intent.url)
            is DetailsIntent.GameIconClickIntent ->
                emitNavigateToGameEvent(intent.url)
        }
    }

    private fun loadGame(id: Int) {
        remoteRepo
            .getGameDetails(id)
            .subscribeOn(Schedulers.io())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Success -> {
                        localRepo
                            .isGameBookmarked(resource.data.id)
                            .subscribe { isBookmarked ->
                                _detailsState.postValue(
                                    DetailsState.DataState(resource.data, isBookmarked)
                                )
                            }
                    }
                    is Resource.Loading ->
                        _detailsState.postValue(DetailsState.LoadingState)
                    is Resource.Error -> {
                        _detailsState.postValue(DetailsState.ErrorState)
                        emitShowErrorEvent(resource.error)
                    }
                }
            }
    }

    private fun insertBookmark(item: DetailedGameModel) {
        localRepo.insertBookmark(item)
        toggleBookmarkIcon()
    }

    private fun deleteBookmark(id: Int) {
        localRepo.deleteBookmark(id)
        toggleBookmarkIcon()
    }

    private fun toggleBookmarkIcon() {
        _detailsState.value.let { state ->
            if (state is DetailsState.DataState)
                state.copy(isFavorite = !state.isFavorite).also {
                    _detailsState.value = it
                }
        }
    }

    private fun emitShowErrorEvent(error: ResourceError) {
        _detailsEvent.onNext(DetailsEvent.ShowErrorEvent(error))
    }

    private fun emitNavigateToFreeToGameEvent(url: String) {
        _detailsEvent.onNext(DetailsEvent.NavigateToFreeToGameEvent(url))
    }

    private fun emitNavigateToGameEvent(url: String) {
        _detailsEvent.onNext(DetailsEvent.NavigateToGameEvent(url))
    }
}