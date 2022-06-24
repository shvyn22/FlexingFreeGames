package shvyn22.flexingfreegames.presentation.bookmarks

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import shvyn22.flexingfreegames.data.local.dao.FakeBookmarkDao
import shvyn22.flexingfreegames.data.util.fromGameDTOToModel
import shvyn22.flexingfreegames.repository.local.LocalRepositoryImpl
import shvyn22.flexingfreegames.util.MainCoroutineRule
import shvyn22.flexingfreegames.util.game1
import shvyn22.flexingfreegames.util.game2

@ExperimentalCoroutinesApi
class BookmarksViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var dao: FakeBookmarkDao
    private lateinit var viewModel: BookmarksViewModel

    @Before
    fun init() {
        dao = FakeBookmarkDao()
        viewModel = BookmarksViewModel(
            localRepo = LocalRepositoryImpl(dao)
        )
    }

    @Test
    fun populateDaoWith2Items_ReturnsDataStateWithItems() = runTest {
        dao.insertBookmark(fromGameDTOToModel(game1))
        dao.insertBookmark(fromGameDTOToModel(game2))

        viewModel.handleIntent(BookmarksIntent.LoadBookmarksIntent)
        advanceUntilIdle()

        assertThat(
            viewModel.bookmarksState.value,
            `is`(instanceOf(BookmarksState.DataState::class.java))
        )
        assertThat(
            (viewModel.bookmarksState.value as BookmarksState.DataState).data,
            `is`(fromGameDTOToModel(listOf(game1, game2)))
        )
    }

    @Test
    fun populateDaoWithNoItems_ReturnsErrorState() = runTest {
        val event = viewModel.bookmarksEvent.first()

        viewModel.handleIntent(BookmarksIntent.LoadBookmarksIntent)
        advanceUntilIdle()

        assertThat(
            viewModel.bookmarksState.value,
            `is`(instanceOf(BookmarksState.ErrorState::class.java))
        )
        assertThat(
            event,
            `is`(instanceOf(BookmarksEvent.ShowErrorEvent::class.java))
        )
    }

    @Test
    fun populateDaoWith2Items_Remove1Item_ReturnsDataStateWithItem() = runTest {
        dao.insertBookmark(fromGameDTOToModel(game1))
        dao.insertBookmark(fromGameDTOToModel(game2))
        viewModel.handleIntent(BookmarksIntent.LoadBookmarksIntent)
        advanceUntilIdle()

        dao.deleteBookmark(game1.id)
        viewModel.handleIntent(BookmarksIntent.LoadBookmarksIntent)
        advanceUntilIdle()

        assertThat(
            viewModel.bookmarksState.value,
            `is`(instanceOf(BookmarksState.DataState::class.java))
        )
        assertThat(
            (viewModel.bookmarksState.value as BookmarksState.DataState).data,
            `is`(fromGameDTOToModel(listOf(game2)))
        )
    }

    @Test
    fun populateDaoWith2Items_RemoveAllItems_ReturnsErrorState() = runTest {
        val event = viewModel.bookmarksEvent.first()

        dao.insertBookmark(fromGameDTOToModel(game1))
        dao.insertBookmark(fromGameDTOToModel(game2))
        viewModel.handleIntent(BookmarksIntent.LoadBookmarksIntent)
        advanceUntilIdle()

        dao.deleteBookmark(game1.id)
        dao.deleteBookmark(game2.id)
        viewModel.handleIntent(BookmarksIntent.LoadBookmarksIntent)
        advanceUntilIdle()

        assertThat(
            viewModel.bookmarksState.value,
            `is`(instanceOf(BookmarksState.ErrorState::class.java))
        )
        assertThat(
            event,
            `is`(instanceOf(BookmarksEvent.ShowErrorEvent::class.java))
        )
    }

    @Test
    fun clickOnBookmark_ReturnsNavigateEvent() = runTest {
        viewModel.bookmarksEvent.test {
            viewModel.handleIntent(BookmarksIntent.BookmarkClickIntent(game1.id))
            advanceUntilIdle()

            val event = awaitItem()

            assertThat(
                event,
                `is`(instanceOf(BookmarksEvent.NavigateToDetailsEvent::class.java))
            )
            assertThat(
                (event as BookmarksEvent.NavigateToDetailsEvent).id,
                `is`(game1.id)
            )

            cancelAndIgnoreRemainingEvents()
        }
    }
}