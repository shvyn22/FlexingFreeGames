package shvyn22.flexingfreegames.presentation.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.remote.FakeApiService
import shvyn22.flexingfreegames.data.remote.api.ApiService
import shvyn22.flexingfreegames.data.util.fromDetailedGameDTOToModel
import shvyn22.flexingfreegames.di.component.TestSingletonComponent
import shvyn22.flexingfreegames.util.game3
import shvyn22.flexingfreegames.util.singletonComponent
import shvyn22.flexingfreegames.util.withImageDrawable
import shvyn22.flexingfreegames.util.withItemCount
import javax.inject.Inject

class DetailsFragmentTest {

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
    fun remoteIsAvailable_GameInformationIsInView() {
        val res = ApplicationProvider.getApplicationContext<Context>().resources

        launchFragmentInContainer<DetailsFragment>(
            fragmentArgs = Bundle().apply { putInt("id", game3.id) },
            themeResId = R.style.Theme_FlexingFreeGames
        )

        onView(withContentDescription(R.string.text_accessibility_thumb))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tv_title))
            .check(matches(withText(game3.title)))

        onView(withId(R.id.tv_genre))
            .check(matches(withText(game3.genre)))

        onView(withId(R.id.tv_publisher))
            .check(matches(withText(game3.publisher)))

        onView(withId(R.id.tv_developer))
            .check(matches(withText(game3.developer)))

        onView(withId(R.id.tv_release_date))
            .check(matches(withText(game3.releaseDate)))

        onView(withId(R.id.tv_platform))
            .check(matches(withText(game3.platform)))

        onView(withId(R.id.tv_description))
            .check(matches(withText(game3.detailedDescription)))

        onView(withId(R.id.vp_screenshots))
            .check(matches(withItemCount(fromDetailedGameDTOToModel(game3).screenshots.size)))

        val requirements = game3.systemRequirements

        onView(withId(R.id.panel_requirements))
            .perform(scrollTo())

        onView(withId(R.id.panel_requirements))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tv_os))
            .check(matches(withText(res.getString(R.string.text_os, requirements?.os))))

        onView(withId(R.id.tv_processor))
            .check(
                matches(withText(res.getString(R.string.text_processor, requirements?.processor)))
            )

        onView(withId(R.id.tv_graphics))
            .check(matches(withText(res.getString(R.string.text_graphics, requirements?.graphics))))

        onView(withId(R.id.tv_memory))
            .check(matches(withText(res.getString(R.string.text_memory, requirements?.memory))))

        onView(withId(R.id.tv_storage))
            .check(matches(withText(res.getString(R.string.text_storage, requirements?.storage))))
    }

    @Test
    fun remoteIsAvailable_ToggleBookmarkIcon_RightStateIsInView() {
        launchFragmentInContainer<DetailsFragment>(
            fragmentArgs = Bundle().apply { putInt("id", game3.id) },
            themeResId = R.style.Theme_FlexingFreeGames
        )

        onView(withId(R.id.iv_bookmark))
            .check(matches(withImageDrawable(R.drawable.ic_not_bookmarked)))

        onView(withId(R.id.iv_bookmark))
            .perform(click())
        onView(withId(R.id.iv_bookmark))
            .check(matches(withImageDrawable(R.drawable.ic_bookmarked)))

        onView(withId(R.id.iv_bookmark))
            .perform(click())
        onView(withId(R.id.iv_bookmark))
            .check(matches(withImageDrawable(R.drawable.ic_not_bookmarked)))
    }

    @Test
    fun remoteIsNotAvailable_ErrorStateIsInView() {
        fakeApi.changeFailBehaviour(true)

        launchFragmentInContainer<DetailsFragment>(
            fragmentArgs = Bundle().apply { putInt("id", game3.id) },
            themeResId = R.style.Theme_FlexingFreeGames
        )

        onView(withId(R.id.btn_retry))
            .check(matches(isDisplayed()))
    }
}