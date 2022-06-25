package shvyn22.flexingfreegames.presentation.bookmarks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import shvyn22.flexingfreegames.repository.local.LocalRepository
import shvyn22.flexingfreegames.util.Resource
import shvyn22.flexingfreegames.util.ResourceError
import javax.inject.Inject

class BookmarksViewModel @Inject constructor(
    private val localRepo: LocalRepository,
) : ViewModel() {

    private val _bookmarksState = MutableLiveData<BookmarksState>(BookmarksState.LoadingState)
    val bookmarksState: LiveData<BookmarksState> get() = _bookmarksState

    private val _bookmarksEvent = PublishSubject.create<BookmarksEvent>()
    val bookmarksEvent: Observable<BookmarksEvent>
        get() = _bookmarksEvent.flatMap { Observable.just(it) }

    init {
        handleIntent(BookmarksIntent.LoadBookmarksIntent)
    }

    fun handleIntent(intent: BookmarksIntent) {
        when (intent) {
            is BookmarksIntent.LoadBookmarksIntent -> loadBookmarks()
            is BookmarksIntent.BookmarkClickIntent -> emitNavigateEvent(intent.id)
        }
    }

    private fun loadBookmarks() {
        localRepo
            .getBookmarks()
            .subscribeOn(Schedulers.io())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Success ->
                        _bookmarksState.postValue(BookmarksState.DataState(resource.data))
                    is Resource.Loading ->
                        _bookmarksState.postValue(BookmarksState.LoadingState)
                    is Resource.Error -> {
                        _bookmarksState.postValue(BookmarksState.ErrorState)
                        emitShowErrorEvent(resource.error)
                    }
                }
            }
    }

    private fun emitShowErrorEvent(error: ResourceError) {
        _bookmarksEvent.onNext(BookmarksEvent.ShowErrorEvent(error))
    }

    private fun emitNavigateEvent(id: Int) {
        _bookmarksEvent.onNext(BookmarksEvent.NavigateToDetailsEvent(id))
    }
}