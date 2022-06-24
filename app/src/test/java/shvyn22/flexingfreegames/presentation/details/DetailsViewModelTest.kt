package shvyn22.flexingfreegames.presentation.details

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
import shvyn22.flexingfreegames.data.local.dao.FakeBookmarkDao
import shvyn22.flexingfreegames.data.remote.api.FakeApiService
import shvyn22.flexingfreegames.data.util.fromDetailedGameDTOToModel
import shvyn22.flexingfreegames.repository.local.LocalRepositoryImpl
import shvyn22.flexingfreegames.repository.remote.RemoteRepositoryImpl
import shvyn22.flexingfreegames.util.MainCoroutineRule
import shvyn22.flexingfreegames.util.game3

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var dao: FakeBookmarkDao
    private lateinit var api: FakeApiService
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun init() {
        dao = FakeBookmarkDao()
        api = FakeApiService()

        viewModel = DetailsViewModel(
            remoteRepo = RemoteRepositoryImpl(api),
            localRepo = LocalRepositoryImpl(dao)
        )
    }

    @Test
    fun remoteIsAvailable_ReturnsDataState() = runTest {
        viewModel.handleIntent(DetailsIntent.LoadGameIntent(game3.id))
        advanceUntilIdle()

        assertThat(
            viewModel.detailsState.value,
            `is`(instanceOf(DetailsState.DataState::class.java))
        )
        assertThat(
            (viewModel.detailsState.value as DetailsState.DataState).data,
            `is`(fromDetailedGameDTOToModel(game3))
        )
    }

    @Test
    fun remoteIsAvailable_ToggleBookmarkIcon_ReturnsCorrectDataState() = runTest {
        viewModel.handleIntent(DetailsIntent.LoadGameIntent(game3.id))
        advanceUntilIdle()
        assertThat(
            viewModel.detailsState.value,
            `is`(instanceOf(DetailsState.DataState::class.java))
        )
        assertThat(
            (viewModel.detailsState.value as DetailsState.DataState).isFavorite,
            `is`(false)
        )

        viewModel.handleIntent(DetailsIntent.InsertBookmarkIntent(fromDetailedGameDTOToModel(game3)))
        advanceUntilIdle()
        assertThat(
            viewModel.detailsState.value,
            `is`(instanceOf(DetailsState.DataState::class.java))
        )
        assertThat(
            (viewModel.detailsState.value as DetailsState.DataState).isFavorite,
            `is`(true)
        )

        viewModel.handleIntent(DetailsIntent.DeleteBookmarkIntent(game3.id))
        advanceUntilIdle()
        assertThat(
            viewModel.detailsState.value,
            `is`(instanceOf(DetailsState.DataState::class.java))
        )
        assertThat(
            (viewModel.detailsState.value as DetailsState.DataState).isFavorite,
            `is`(false)
        )
    }

    @Test
    fun remoteIsAvailable_FreeToGameIconClick_ReturnsNavigateEvent() = runTest {
        viewModel.handleIntent(DetailsIntent.LoadGameIntent(game3.id))
        viewModel.detailsEvent.test {
            viewModel.handleIntent(DetailsIntent.FreeToGameIconClickIntent(game3.freeToGameUrl))
            advanceUntilIdle()

            val event = awaitItem()

            assertThat(
                event,
                `is`(instanceOf(DetailsEvent.NavigateToFreeToGameEvent::class.java))
            )
            assertThat(
                (event as DetailsEvent.NavigateToFreeToGameEvent).url,
                `is`(game3.freeToGameUrl)
            )
        }
    }

    @Test
    fun remoteIsAvailable_GameIconClick_ReturnsNavigateEvent() = runTest {
        viewModel.handleIntent(DetailsIntent.LoadGameIntent(game3.id))
        viewModel.detailsEvent.test {
            viewModel.handleIntent(DetailsIntent.GameIconClickIntent(game3.gameUrl))
            advanceUntilIdle()

            val event = awaitItem()

            assertThat(
                event,
                `is`(instanceOf(DetailsEvent.NavigateToGameEvent::class.java))
            )
            assertThat(
                (event as DetailsEvent.NavigateToGameEvent).url,
                `is`(game3.gameUrl)
            )
        }
    }

    @Test
    fun remoteIsNotAvailable_ReturnsErrorState() = runTest {
        api.changeFailBehaviour(true)

        viewModel.detailsEvent.test {
            viewModel.handleIntent(DetailsIntent.LoadGameIntent(66))
            advanceUntilIdle()

            assertThat(
                viewModel.detailsState.value,
                `is`(instanceOf(DetailsState.ErrorState::class.java))
            )
            assertThat(
                awaitItem(),
                `is`(instanceOf(DetailsEvent.ShowErrorEvent::class.java))
            )
        }
    }
}