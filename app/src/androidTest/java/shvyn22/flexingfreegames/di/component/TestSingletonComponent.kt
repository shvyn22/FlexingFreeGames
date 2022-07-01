package shvyn22.flexingfreegames.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import shvyn22.flexingfreegames.di.module.FakeAppModule
import shvyn22.flexingfreegames.presentation.bookmarks.BookmarksFragment
import shvyn22.flexingfreegames.presentation.bookmarks.BookmarksFragmentTest
import shvyn22.flexingfreegames.presentation.browse.BrowseFragment
import shvyn22.flexingfreegames.presentation.browse.BrowseFragmentTest
import shvyn22.flexingfreegames.presentation.details.DetailsFragment
import shvyn22.flexingfreegames.presentation.details.DetailsFragmentTest
import javax.inject.Singleton

@Singleton
@Component(
    modules = [FakeAppModule::class]
)
interface TestSingletonComponent : SingletonComponent {

    override fun inject(browseFragment: BrowseFragment)

    override fun inject(bookmarksFragment: BookmarksFragment)

    override fun inject(detailsFragment: DetailsFragment)

    fun inject(browseFragmentTest: BrowseFragmentTest)

    fun inject(bookmarksFragmentTest: BookmarksFragmentTest)

    fun inject(detailsFragmentTest: DetailsFragmentTest)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): TestSingletonComponent
    }
}