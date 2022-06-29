package shvyn22.flexingfreegames.presentation.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.google.accompanist.pager.ExperimentalPagerApi
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
import shvyn22.flexingfreegames.util.game3
import javax.inject.Inject

@ExperimentalPagerApi
@HiltAndroidTest
class DetailsScreenTest {

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
    fun remoteIsAvailable_GameInformationIsInView() {
        composeRule.apply {
            setContent {
                DetailsScreen(id = game3.id, onShowError = {}, modifier = Modifier.fillMaxSize())
            }

            waitUntil {
                onAllNodesWithText(game3.title).fetchSemanticsNodes().isNotEmpty()
            }

            onNodeWithContentDescription(activity.getString(R.string.text_accessibility_thumb))
                .assertIsDisplayed()

            onNodeWithText(game3.title)
                .assertIsDisplayed()

            onNodeWithText(game3.genre)
                .assertIsDisplayed()

            onNodeWithText(game3.publisher)
                .assertIsDisplayed()

            onNodeWithText(game3.developer)
                .assertIsDisplayed()

            onNodeWithText(game3.releaseDate)
                .assertIsDisplayed()

            onNodeWithText(game3.platform)
                .assertIsDisplayed()

            onNodeWithText(game3.detailedDescription)
                .assertIsDisplayed()

            val requirements = game3.systemRequirements

            requirements?.let {
                onNodeWithText(activity.getString(R.string.text_storage, requirements.storage))
                    .performScrollTo()

                onNodeWithText(activity.getString(R.string.text_os, requirements.os))
                    .assertIsDisplayed()

                onNodeWithText(activity.getString(R.string.text_processor, requirements.processor))
                    .assertIsDisplayed()

                onNodeWithText(activity.getString(R.string.text_graphics, requirements.graphics))
                    .assertIsDisplayed()

                onNodeWithText(activity.getString(R.string.text_memory, requirements.memory))
                    .assertIsDisplayed()

                onNodeWithText(activity.getString(R.string.text_storage, requirements.storage))
                    .assertIsDisplayed()
            }
        }
    }

    @Test
    fun remoteIsNotAvailable_ErrorStateIsInView() {
        fakeApi.changeFailBehaviour(true)

        composeRule.apply {
            setContent {
                DetailsScreen(id = game3.id, onShowError = {})
            }

            onNodeWithText(activity.getString(R.string.text_action_retry))
                .assertIsDisplayed()
        }
    }
}