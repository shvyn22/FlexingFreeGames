package shvyn22.flexingfreegames.presentation.browse

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.PositionAssertions.isCompletelyAbove
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.remote.FakeApiService
import shvyn22.flexingfreegames.data.remote.api.ApiService
import shvyn22.flexingfreegames.di.component.TestSingletonComponent
import shvyn22.flexingfreegames.presentation.adapters.GameAdapter
import shvyn22.flexingfreegames.util.game1
import shvyn22.flexingfreegames.util.game2
import shvyn22.flexingfreegames.util.singletonComponent
import shvyn22.flexingfreegames.util.withItemCount
import javax.inject.Inject

class BrowseFragmentTest {

    @Inject
    lateinit var api: ApiService
    private val fakeApi get() = api as FakeApiService

    @Before
    fun init() {
        (InstrumentationRegistry.getInstrumentation()
            .targetContext.applicationContext
            .singletonComponent as TestSingletonComponent)
            .inject(this)
    }

    @Test
    fun remoteIsAvailable_DefaultFilters_2ItemsAreInView() {
        launchFragmentInContainer<BrowseFragment>(
            themeResId = R.style.Theme_FlexingFreeGames
        )

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
        launchFragmentInContainer<BrowseFragment>(
            themeResId = R.style.Theme_FlexingFreeGames
        )

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
        launchFragmentInContainer<BrowseFragment>(
            themeResId = R.style.Theme_FlexingFreeGames
        )

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

        launchFragmentInContainer<BrowseFragment>(
            themeResId = R.style.Theme_FlexingFreeGames
        )

        onView(withId(R.id.rv_games))
            .check(matches(not(isDisplayed())))
        onView(withId(R.id.btn_retry))
            .check(matches(isDisplayed()))
    }

    @Test
    fun clickOnGame_NavigatedToDetailsFragment() {
        val navController = mock(NavController::class.java)

        launchFragmentInContainer<BrowseFragment>(
            themeResId = R.style.Theme_FlexingFreeGames
        ).onFragment {
            Navigation.setViewNavController(it.requireView(), navController)
        }

        onView(withId(R.id.rv_games))
            .perform(actionOnItemAtPosition<GameAdapter.GameViewHolder>(0, click()))

        verify(navController).navigate(
            BrowseFragmentDirections.actionBrowseToDetails(game2.id)
        )
    }
}