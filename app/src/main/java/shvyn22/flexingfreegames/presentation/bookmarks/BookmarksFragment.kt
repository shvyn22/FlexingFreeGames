package shvyn22.flexingfreegames.presentation.bookmarks

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.databinding.FragmentBookmarksBinding
import shvyn22.flexingfreegames.presentation.adapters.GameAdapter
import shvyn22.flexingfreegames.util.ResourceError
import shvyn22.flexingfreegames.util.collectOnLifecycle

@AndroidEntryPoint
class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {

    private val viewModel: BookmarksViewModel by viewModels()

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    private val adapter = GameAdapter {
        viewModel.handleIntent(BookmarksIntent.BookmarkClickIntent(it))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBookmarksBinding.bind(view)

        initUI()
        subscribeObservers()
    }

    private fun initUI() {
        binding.rvBookmarks.adapter = adapter
    }

    private fun subscribeObservers() {
        viewModel.bookmarksState.collectOnLifecycle(viewLifecycleOwner) { state ->
            handleState(state)
        }

        viewModel.bookmarksEvent.collectOnLifecycle(viewLifecycleOwner) { event ->
            handleEvent(event)
        }
    }

    private fun handleState(state: BookmarksState) {
        binding.pbLoading.isVisible = state is BookmarksState.LoadingState

        if (state is BookmarksState.DataState) adapter.submitList(state.data)
    }

    private fun handleEvent(event: BookmarksEvent) {
        when (event) {
            is BookmarksEvent.ShowErrorEvent -> {
                Snackbar
                    .make(
                        requireView(),
                        when (event.error) {
                            is ResourceError.Fetching -> getString(R.string.text_error_fetching)
                            is ResourceError.NoBookmarks -> getString(R.string.text_error_no_bookmarks)
                            is ResourceError.Specified -> event.error.msg
                        },
                        Snackbar.LENGTH_LONG
                    )
                    .show()
            }
            is BookmarksEvent.NavigateToDetailsEvent -> {
                findNavController().navigate(
                    BookmarksFragmentDirections.actionBookmarksToDetails(event.id)
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}