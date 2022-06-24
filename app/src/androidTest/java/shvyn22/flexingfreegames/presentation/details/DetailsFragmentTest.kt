package shvyn22.flexingfreegames.presentation.details

import android.content.Context
import android.os.Bundle
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.remote.api.ApiService
import shvyn22.flexingfreegames.data.remote.api.FakeApiService
import shvyn22.flexingfreegames.data.util.fromDetailedGameDTOToModel
import shvyn22.flexingfreegames.util.*
import javax.inject.Inject

@HiltAndroidTest
class DetailsFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var api: ApiService
    private val fakeApi get() = api as FakeApiService

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun remoteIsAvailable_GameInformationIsInView() {
        val res = ApplicationProvider.getApplicationContext<Context>().resources

        launchFragmentInHiltContainer<DetailsFragment>(
            fragmentArgs = Bundle().apply { putInt("id", game3.id) }
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
        launchFragmentInHiltContainer<DetailsFragment>(
            fragmentArgs = Bundle().apply { putInt("id", game3.id) }
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

        launchFragmentInHiltContainer<DetailsFragment>(
            fragmentArgs = Bundle().apply { putInt("id", game3.id) }
        )

        onView(withId(R.id.btn_retry))
            .check(matches(isDisplayed()))
    }
}