package shvyn22.flexingfreegames.presentation.browse

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import shvyn22.flexingfreegames.data.preferences.FakePreferencesManager
import shvyn22.flexingfreegames.data.remote.api.FakeApiService
import shvyn22.flexingfreegames.data.util.fromGameDTOToModel
import shvyn22.flexingfreegames.repository.remote.RemoteRepositoryImpl
import shvyn22.flexingfreegames.util.MainCoroutineRule
import shvyn22.flexingfreegames.util.game1
import shvyn22.flexingfreegames.util.game2

@ExperimentalCoroutinesApi
class BrowseViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var preferences: FakePreferencesManager
    private lateinit var api: FakeApiService
    private lateinit var viewModel: BrowseViewModel

    @Before
    fun init() {
        preferences = FakePreferencesManager()
        api = FakeApiService()

        viewModel = BrowseViewModel(
            preferences = preferences,
            remoteRepo = RemoteRepositoryImpl(api)
        )
    }

    @Test
    fun remoteIsAvailable_DefaultFilters_ReturnsDataState() = runTest {
        viewModel.handleIntent(BrowseIntent.LoadGamesIntent(0, 0, 0))
        advanceUntilIdle()

        assertThat(
            viewModel.browseState.value,
            `is`(instanceOf(BrowseState.DataState::class.java))
        )
        assertThat(
            (viewModel.browseState.value as BrowseState.DataState).data,
            `is`(fromGameDTOToModel(listOf(game2, game1)))
        )
    }

    @Test
    fun remoteIsAvailable_FilterBySort_ReturnsDataStateAndFilterEvent() = runTest {
        viewModel.browseEvent.test {
            viewModel.handleIntent(BrowseIntent.LoadGamesIntent(0, 1, 0))
            advanceUntilIdle()

            assertThat(
                viewModel.browseState.value,
                `is`(instanceOf(BrowseState.DataState::class.java))
            )
            assertThat(
                (viewModel.browseState.value as BrowseState.DataState).data,
                `is`(fromGameDTOToModel(listOf(game1, game2)))
            )

            val event = expectMostRecentItem()
            assertThat(
                event,
                `is`(instanceOf(BrowseEvent.UpdateFiltersEvent::class.java))
            )
            assertThat(
                (event as BrowseEvent.UpdateFiltersEvent).sort,
                `is`(1)
            )
        }
    }

    @Test
    fun remoteIsAvailable_FilterByCategory_ReturnsDataStateAndFilterEvent() = runTest {
        viewModel.browseEvent.test {
            viewModel.handleIntent(BrowseIntent.LoadGamesIntent(0, 0, 2))
            advanceUntilIdle()

            assertThat(
                viewModel.browseState.value,
                `is`(instanceOf(BrowseState.DataState::class.java))
            )
            assertThat(
                (viewModel.browseState.value as BrowseState.DataState).data,
                `is`(fromGameDTOToModel(listOf(game1)))
            )

            val event = expectMostRecentItem()
            assertThat(
                event,
                `is`(instanceOf(BrowseEvent.UpdateFiltersEvent::class.java))
            )
            assertThat(
                (event as BrowseEvent.UpdateFiltersEvent).category,
                `is`(2)
            )
        }
    }

    @Test
    fun remoteIsNotAvailable_DefaultFilters_ReturnsErrorState() = runTest {
        api.changeFailBehaviour(true)

        viewModel.browseEvent.test {
            viewModel.handleIntent(BrowseIntent.LoadGamesIntent(0, 0, 0))
            advanceUntilIdle()

            assertThat(
                viewModel.browseState.value,
                `is`(instanceOf(BrowseState.ErrorState::class.java))
            )
            assertThat(
                expectMostRecentItem(),
                `is`(instanceOf(BrowseEvent.ShowErrorEvent::class.java))
            )
        }
    }

    @Test
    fun clickOnGame_ReturnsNavigateEvent() = runTest {
        viewModel.browseEvent.test {
            viewModel.handleIntent(BrowseIntent.GameClickIntent(game1.id))
            advanceUntilIdle()

            val event = awaitItem()

            assertThat(
                event,
                `is`(instanceOf(BrowseEvent.NavigateToDetailsEvent::class.java))
            )
            assertThat(
                (event as BrowseEvent.NavigateToDetailsEvent).id,
                `is`(game1.id)
            )

            cancelAndIgnoreRemainingEvents()
        }
    }
}