package shvyn22.flexingfreegames.presentation.bookmarks

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import io.reactivex.rxjava3.schedulers.Schedulers
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.local.dao.BookmarkDao
import shvyn22.flexingfreegames.data.util.fromGameDTOToModel
import shvyn22.flexingfreegames.di.component.TestSingletonComponent
import shvyn22.flexingfreegames.presentation.adapters.GameAdapter
import shvyn22.flexingfreegames.util.game1
import shvyn22.flexingfreegames.util.game2
import shvyn22.flexingfreegames.util.singletonComponent
import shvyn22.flexingfreegames.util.withItemCount
import javax.inject.Inject

class BookmarksFragmentTest {

    @Inject
    lateinit var dao: BookmarkDao

    @Before
    fun init() {
        (InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext
            .singletonComponent as TestSingletonComponent)
            .inject(this)
    }

    @Test
    fun populateDaoWith2Items_2ItemsAreInView() {
        dao.insertBookmark(fromGameDTOToModel(game1))
            .subscribeOn(Schedulers.trampoline()).subscribe()
        dao.insertBookmark(fromGameDTOToModel(game2))
            .subscribeOn(Schedulers.trampoline()).subscribe()

        launchFragmentInContainer<BookmarksFragment>(
            themeResId = R.style.Theme_FlexingFreeGames
        )

        onView(withId(R.id.rv_bookmarks))
            .check(matches(isDisplayed()))
            .check(matches(withItemCount(2)))

        onView(withText(game1.title))
            .check(matches(isDisplayed()))

        onView(withText(game2.title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun populateDaoWithNoItems_ErrorStateIsInView() {
        launchFragmentInContainer<BookmarksFragment>(
            themeResId = R.style.Theme_FlexingFreeGames
        )

        onView(withId(R.id.rv_bookmarks))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.btn_retry))
            .check(matches(isDisplayed()))
    }

    @Test
    fun populateDaoWith2Items_Remove1Item_1ItemIsInView() {
        dao.insertBookmark(fromGameDTOToModel(game1))
            .subscribeOn(Schedulers.trampoline()).subscribe()
        dao.insertBookmark(fromGameDTOToModel(game2))
            .subscribeOn(Schedulers.trampoline()).subscribe()

        launchFragmentInContainer<BookmarksFragment>(
            themeResId = R.style.Theme_FlexingFreeGames
        )

        onView(withId(R.id.rv_bookmarks))
            .check(matches(isDisplayed()))
            .check(matches(withItemCount(2)))
        onView(withText(game1.title))
            .check(matches(isDisplayed()))
        onView(withText(game2.title))
            .check(matches(isDisplayed()))

        dao.deleteBookmark(game2.id)
            .subscribeOn(Schedulers.trampoline()).subscribe()
        Thread.sleep(1000)

        onView(withId(R.id.rv_bookmarks))
            .check(matches(isDisplayed()))
            .check(matches(withItemCount(1)))
        onView(withText(game1.title))
            .check(matches(isDisplayed()))
        onView(withText(game2.title))
            .check(doesNotExist())
    }

    @Test
    fun populateDaoWith2Items_RemoveAllItems_ErrorStateIsInView() {
        dao.insertBookmark(fromGameDTOToModel(game1))
            .subscribeOn(Schedulers.trampoline()).subscribe()
        dao.insertBookmark(fromGameDTOToModel(game2))
            .subscribeOn(Schedulers.trampoline()).subscribe()

        launchFragmentInContainer<BookmarksFragment>(
            themeResId = R.style.Theme_FlexingFreeGames
        )

        onView(withId(R.id.rv_bookmarks))
            .check(matches(isDisplayed()))
            .check(matches(withItemCount(2)))
        onView(withText(game1.title))
            .check(matches(isDisplayed()))
        onView(withText(game2.title))
            .check(matches(isDisplayed()))

        dao.deleteBookmark(game1.id)
            .subscribeOn(Schedulers.trampoline()).subscribe()
        dao.deleteBookmark(game2.id)
            .subscribeOn(Schedulers.trampoline()).subscribe()
        Thread.sleep(1000)

        onView(withId(R.id.rv_bookmarks))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.btn_retry))
            .check(matches(isDisplayed()))
    }

    @Test
    fun clickOnBookmark_NavigatedToDetailsFragment() {
        val navController = mock(NavController::class.java)

        dao.insertBookmark(fromGameDTOToModel(game1))
            .subscribeOn(Schedulers.trampoline()).subscribe()

        launchFragmentInContainer<BookmarksFragment>(
            themeResId = R.style.Theme_FlexingFreeGames
        ).onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.rv_bookmarks))
            .perform(actionOnItemAtPosition<GameAdapter.GameViewHolder>(0, click()))

        verify(navController).navigate(
            BookmarksFragmentDirections.actionBookmarksToDetails(game1.id)
        )
    }
}