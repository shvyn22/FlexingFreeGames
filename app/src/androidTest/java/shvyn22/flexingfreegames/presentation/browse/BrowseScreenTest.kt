package shvyn22.flexingfreegames.presentation.browse

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineScope
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.remote.api.ApiService
import shvyn22.flexingfreegames.data.remote.api.FakeApiService
import shvyn22.flexingfreegames.di.tearDownPreferencesDependencies
import shvyn22.flexingfreegames.presentation.MainActivity
import shvyn22.flexingfreegames.util.game1
import shvyn22.flexingfreegames.util.game2
import javax.inject.Inject

@HiltAndroidTest
class BrowseScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

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
        composeRule.apply {
            onNodeWithText(game1.title)
                .assertIsDisplayed()

            onNodeWithText(game2.title)
                .assertIsDisplayed()
        }
    }

    @Test
    fun remoteIsAvailable_FilterBySort_2ItemsInCorrectOrderAreInView() {
        composeRule.apply {
            onNodeWithText(activity.getString(R.string.text_filters))
                .performClick()

            onNodeWithText("Release-date")
                .performClick()
            onNodeWithText("Popularity")
                .performClick()
            onNodeWithText(activity.getString(R.string.text_action_search))
                .performClick()

            onNodeWithText(game1.title)
                .assertIsDisplayed()
            onNodeWithText(game2.title)
                .assertIsDisplayed()

            /*
            TODO: assert that the node with text "game2.title" is on top of the node with text
             "game1.title" (lack of such assertion options in 1.1.1)
             */
        }
    }

    @Test
    fun remoteIsNotAvailable_DefaultFilters_ErrorStateIsInView() {
        fakeApi.changeFailBehaviour(true)

        composeRule.apply {
            onNodeWithText(game1.title)
                .assertDoesNotExist()

            onNodeWithText(game2.title)
                .assertDoesNotExist()

            onNodeWithText(activity.getString(R.string.text_action_retry))
                .assertIsDisplayed()
        }
    }
}