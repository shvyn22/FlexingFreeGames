package shvyn22.flexingfreegames.presentation.bookmarks

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.local.dao.BookmarkDao
import shvyn22.flexingfreegames.data.util.fromGameDTOToModel
import shvyn22.flexingfreegames.di.tearDownPreferencesDependencies
import shvyn22.flexingfreegames.presentation.MainActivity
import shvyn22.flexingfreegames.util.game1
import shvyn22.flexingfreegames.util.game2
import javax.inject.Inject

@HiltAndroidTest
class BookmarksScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var dao: BookmarkDao

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
    fun populateDaoWith2Items_2ItemsAreInView() {
        runBlocking {
            dao.insertBookmark(fromGameDTOToModel(game1))
            dao.insertBookmark(fromGameDTOToModel(game2))
        }

        composeRule.apply {
            setContent {
                BookmarksScreen(onShowError = {}, onNavigateToDetails = {})
            }

            onNodeWithText(game1.title)
                .assertIsDisplayed()

            onNodeWithText(game2.title)
                .assertIsDisplayed()
        }
    }

    @Test
    fun populateDaoWithNoItems_ErrorStateIsInView() {
        composeRule.apply {
            setContent {
                BookmarksScreen(onShowError = {}, onNavigateToDetails = {})
            }

            onNodeWithText(game1.title)
                .assertDoesNotExist()

            onNodeWithText(game2.title)
                .assertDoesNotExist()

            onNodeWithText(activity.getString(R.string.text_action_retry))
                .assertIsDisplayed()
        }
    }

    @Test
    fun populateDaoWith2Items_Remove1Item_1ItemIsInView() {
        runBlocking {
            dao.insertBookmark(fromGameDTOToModel(game1))
            dao.insertBookmark(fromGameDTOToModel(game2))
        }

        composeRule.apply {
            setContent {
                BookmarksScreen(onShowError = {}, onNavigateToDetails = {})
            }

            onNodeWithText(game1.title)
                .assertIsDisplayed()
            onNodeWithText(game2.title)
                .assertIsDisplayed()

            runBlocking {
                dao.deleteBookmark(game2.id)
            }

            onNodeWithText(game1.title)
                .assertIsDisplayed()
            onNodeWithText(game2.title)
                .assertDoesNotExist()
        }
    }

    @Test
    fun populateDaoWith2Items_RemoveAllItems_ErrorStateIsInView() {
        runBlocking {
            dao.insertBookmark(fromGameDTOToModel(game1))
            dao.insertBookmark(fromGameDTOToModel(game2))
        }

        composeRule.apply {
            setContent {
                BookmarksScreen(onShowError = {}, onNavigateToDetails = {})
            }

            onNodeWithText(game1.title)
                .assertIsDisplayed()
            onNodeWithText(game2.title)
                .assertIsDisplayed()

            runBlocking {
                dao.deleteBookmark(game1.id)
                dao.deleteBookmark(game2.id)
            }

            waitUntil {
                onAllNodesWithText(game1.title)
                    .fetchSemanticsNodes().isEmpty()
            }

            onNodeWithText(game1.title)
                .assertDoesNotExist()
            onNodeWithText(game2.title)
                .assertDoesNotExist()
            onNodeWithText(activity.getString(R.string.text_action_retry))
                .assertIsDisplayed()
        }
    }
}