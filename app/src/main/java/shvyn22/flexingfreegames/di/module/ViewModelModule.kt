package shvyn22.flexingfreegames.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import shvyn22.flexingfreegames.di.util.ViewModelKey
import shvyn22.flexingfreegames.presentation.bookmarks.BookmarksViewModel
import shvyn22.flexingfreegames.presentation.browse.BrowseViewModel
import shvyn22.flexingfreegames.presentation.details.DetailsViewModel

@Module
interface ViewModelModule {

    @Binds
    @[IntoMap ViewModelKey(BrowseViewModel::class)]
    fun bindBrowseViewModel(browseViewModel: BrowseViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(BookmarksViewModel::class)]
    fun bindBookmarksViewModel(bookmarksViewModel: BookmarksViewModel): ViewModel

    @Binds
    @[IntoMap ViewModelKey(DetailsViewModel::class)]
    fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel
}