package shvyn22.flexingfreegames.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import shvyn22.flexingfreegames.di.module.AppModule
import shvyn22.flexingfreegames.presentation.bookmarks.BookmarksFragment
import shvyn22.flexingfreegames.presentation.browse.BrowseFragment
import shvyn22.flexingfreegames.presentation.details.DetailsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface SingletonComponent {

    fun inject(browseFragment: BrowseFragment)

    fun inject(bookmarksFragment: BookmarksFragment)

    fun inject(detailsFragment: DetailsFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): SingletonComponent
    }
}