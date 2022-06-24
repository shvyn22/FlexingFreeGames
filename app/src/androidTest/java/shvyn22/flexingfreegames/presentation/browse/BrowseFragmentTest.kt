package shvyn22.flexingfreegames.presentation.browse

import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineScope
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.remote.api.ApiService
import shvyn22.flexingfreegames.data.remote.api.FakeApiService
import shvyn22.flexingfreegames.di.tearDownPreferencesDependencies
import shvyn22.flexingfreegames.presentation.adapters.GameAdapter
import shvyn22.flexingfreegames.util.game1
import shvyn22.flexingfreegames.util.game2
import shvyn22.flexingfreegames.util.launchFragmentInHiltContainer
import shvyn22.flexingfreegames.util.withItemCount
import javax.inject.Inject

@HiltAndroidTest
class BrowseFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var api: ApiService
    private val fakeApi get() = api as FakeApiService

    @Inject
    lateinit var scope: CoroutineScope

    @Before
    fun init() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        tearDownPreferencesDependencies(scope)
    }

    @Test
    fun remoteIsAvailable_DefaultFilters_2ItemsAreInView() {
        launchFragmentInHiltContainer<BrowseFragment>()

        onView(withId(R.id.rv_games))
            .check(matches(isDisplayed()))
            .check(matches(withItemCount(2)))

        onView(withText(game1.title))
            .check(matches(isDisplayed()))

        onView(withText(game2.title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun remoteIsAvailable_FilterBySort_2ItemsInCorrectOrderAreInView() {
        launchFragmentInHiltContainer<BrowseFragment>()

        onView(withId(R.id.tv_show_filter))
            .perform(click())
        onView(withId(R.id.actv_platform))
            .perform(replaceText("All"))
        onView(withId(R.id.actv_sort_by))
            .perform(replaceText("Popularity"))
        onView(withId(R.id.actv_category))
            .perform(replaceText("All"))
        onView(withId(R.id.btn_search))
            .perform(click())

        onView(withId(R.id.rv_games))
            .check(matches(isDisplayed()))
            .check(matches(withItemCount(2)))
        onView(withText(game1.title))
            .check(matches(isDisplayed()))
        onView(withText(game2.title))
            .check(matches(isDisplayed()))
        onView(withText(game1.title))
            .check(isCompletelyAbove(withText(game2.title)))
    }

    @Test
    fun remoteIsAvailable_FilterByCategory_1ItemIsInView() {
        launchFragmentInHiltContainer<BrowseFragment>()

        onView(withId(R.id.tv_show_filter))
            .perform(click())
        onView(withId(R.id.actv_platform))
            .perform(replaceText("All"))
        onView(withId(R.id.actv_sort_by))
            .perform(replaceText("Release-date"))
        onView(withId(R.id.actv_category))
            .perform(replaceText("Shooter"))
        onView(withId(R.id.btn_search))
            .perform(click())

        onView(withId(R.id.rv_games))
            .check(matches(isDisplayed()))
            .check(matches(withItemCount(1)))
        onView(withText(game1.title))
            .check(matches(isDisplayed()))
        onView(withText(game2.title))
            .check(doesNotExist())
    }

    @Test
    fun remoteIsNotAvailable_DefaultFilters_ErrorStateIsInView() {
        fakeApi.changeFailBehaviour(true)

        launchFragmentInHiltContainer<BrowseFragment>()

        onView(withId(R.id.rv_games))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.btn_retry))
            .check(matches(isDisplayed()))
    }

    @Test
    fun clickOnGame_NavigatedToDetailsFragment() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<BrowseFragment>(
            navController = navController
        )

        onView(withId(R.id.rv_games))
            .perform(actionOnItemAtPosition<GameAdapter.GameViewHolder>(0, click()))

        verify(navController).navigate(
            BrowseFragmentDirections.actionBrowseToDetails(game2.id)
        )
    }
}