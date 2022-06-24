package shvyn22.flexingfreegames.presentation.bookmarks

import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.local.dao.BookmarkDao
import shvyn22.flexingfreegames.data.util.fromGameDTOToModel
import shvyn22.flexingfreegames.presentation.adapters.GameAdapter
import shvyn22.flexingfreegames.util.game1
import shvyn22.flexingfreegames.util.game2
import shvyn22.flexingfreegames.util.launchFragmentInHiltContainer
import shvyn22.flexingfreegames.util.withItemCount
import javax.inject.Inject

@HiltAndroidTest
class BookmarksFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dao: BookmarkDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun populateDaoWith2Items_2ItemsAreInView() {
        runBlocking {
            dao.insertBookmark(fromGameDTOToModel(game1))
            dao.insertBookmark(fromGameDTOToModel(game2))
        }

        launchFragmentInHiltContainer<BookmarksFragment>()

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
        launchFragmentInHiltContainer<BookmarksFragment>()

        onView(withId(R.id.rv_bookmarks))
            .check(matches(not(isDisplayed())))

        onView(withId(R.id.btn_retry))
            .check(matches(isDisplayed()))
    }

    @Test
    fun populateDaoWith2Items_Remove1Item_1ItemIsInView() {
        runBlocking {
            dao.insertBookmark(fromGameDTOToModel(game1))
            dao.insertBookmark(fromGameDTOToModel(game2))
        }

        launchFragmentInHiltContainer<BookmarksFragment>()

        onView(withId(R.id.rv_bookmarks))
            .check(matches(isDisplayed()))
            .check(matches(withItemCount(2)))
        onView(withText(game1.title))
            .check(matches(isDisplayed()))
        onView(withText(game2.title))
            .check(matches(isDisplayed()))

        runBlocking {
            dao.deleteBookmark(game2.id)
            delay(1500)
        }

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
        runBlocking {
            dao.insertBookmark(fromGameDTOToModel(game1))
            dao.insertBookmark(fromGameDTOToModel(game2))
        }

        launchFragmentInHiltContainer<BookmarksFragment>()

        onView(withId(R.id.rv_bookmarks))
            .check(matches(isDisplayed()))
            .check(matches(withItemCount(2)))
        onView(withText(game1.title))
            .check(matches(isDisplayed()))
        onView(withText(game2.title))
            .check(matches(isDisplayed()))

        runBlocking {
            dao.deleteBookmark(game1.id)
            dao.deleteBookmark(game2.id)
            delay(1500)
        }

        onView(withId(R.id.rv_bookmarks))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.btn_retry))
            .check(matches(isDisplayed()))
    }

    @Test
    fun clickOnBookmark_NavigatedToDetailsFragment() {
        val navController = mock(NavController::class.java)

        runBlocking { dao.insertBookmark(fromGameDTOToModel(game1)) }

        launchFragmentInHiltContainer<BookmarksFragment>(
            navController = navController
        )

        onView(withId(R.id.rv_bookmarks))
            .perform(actionOnItemAtPosition<GameAdapter.GameViewHolder>(0, click()))

        verify(navController).navigate(
            BookmarksFragmentDirections.actionBookmarksToDetails(game1.id)
        )
    }
}