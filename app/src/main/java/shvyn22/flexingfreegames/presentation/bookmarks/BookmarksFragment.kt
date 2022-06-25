package shvyn22.flexingfreegames.presentation.bookmarks

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.databinding.FragmentBookmarksBinding
import shvyn22.flexingfreegames.presentation.adapters.GameAdapter
import shvyn22.flexingfreegames.presentation.util.MultiViewModelFactory
import shvyn22.flexingfreegames.util.ResourceError
import shvyn22.flexingfreegames.util.singletonComponent
import javax.inject.Inject

class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory

    private val viewModel: BookmarksViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentBookmarksBinding? = null
    private val binding get() = _binding!!

    private val adapter = GameAdapter {
        viewModel.handleIntent(BookmarksIntent.BookmarkClickIntent(it))
    }

    private var disposable: Disposable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.singletonComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBookmarksBinding.bind(view)

        initUI()
        subscribeObservers()
    }

    private fun initUI() {
        binding.apply {
            rvBookmarks.adapter = adapter

            btnRetry.setOnClickListener {
                viewModel.handleIntent(BookmarksIntent.LoadBookmarksIntent)
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.bookmarksState.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }

        disposable = viewModel
            .bookmarksEvent
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                handleEvent(event)
            }
    }

    private fun handleState(state: BookmarksState) {
        binding.apply {
            pbLoading.isVisible = state is BookmarksState.LoadingState
            btnRetry.isVisible = state is BookmarksState.ErrorState
            rvBookmarks.isVisible = state is BookmarksState.DataState
        }

        if (state is BookmarksState.DataState) adapter.submitList(state.data)
    }

    private fun handleEvent(event: BookmarksEvent) {
        when (event) {
            is BookmarksEvent.ShowErrorEvent -> {
                Snackbar
                    .make(
                        binding.root,
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

    override fun onDestroyView() {
        super.onDestroyView()
        disposable?.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}