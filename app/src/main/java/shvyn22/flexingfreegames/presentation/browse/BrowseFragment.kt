package shvyn22.flexingfreegames.presentation.browse

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
import shvyn22.flexingfreegames.databinding.FragmentBrowseBinding
import shvyn22.flexingfreegames.presentation.adapters.GameAdapter
import shvyn22.flexingfreegames.presentation.adapters.NoFilterArrayAdapter
import shvyn22.flexingfreegames.presentation.util.MultiViewModelFactory
import shvyn22.flexingfreegames.util.ResourceError
import shvyn22.flexingfreegames.util.singletonComponent
import shvyn22.flexingfreegames.util.toggleVisibility
import javax.inject.Inject

class BrowseFragment : Fragment(R.layout.fragment_browse) {

    @Inject
    lateinit var viewModelFactory: MultiViewModelFactory

    private val viewModel: BrowseViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    private val gameAdapter = GameAdapter {
        viewModel.handleIntent(BrowseIntent.GameClickIntent(it))
    }

    private lateinit var platforms: List<String>
    private lateinit var sortTypes: List<String>
    private lateinit var categories: List<String>

    private var disposable: Disposable? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.singletonComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBrowseBinding.bind(view)

        platforms = resources.getStringArray(R.array.platforms).toList()
        sortTypes = resources.getStringArray(R.array.sort_types).toList()
        categories = resources.getStringArray(R.array.categories).toList()

        initUI()
        subscribeObservers()
    }

    private fun initUI() {
        binding.apply {
            rvGames.adapter = gameAdapter

            val platformAdapter = NoFilterArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                platforms
            )
            val sortAdapter = NoFilterArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                sortTypes
            )
            val categoryAdapter = NoFilterArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                categories
            )

            panelFilter.apply {
                actvPlatform.setAdapter(platformAdapter)
                actvSortBy.setAdapter(sortAdapter)
                actvCategory.setAdapter(categoryAdapter)

                tvShowFilter.setOnClickListener { toggleFiltersVisibility() }

                val clickAction: (View) -> Unit = {
                    searchGame(
                        platform = platformAdapter.getPosition(actvPlatform.text.toString()),
                        sort = sortAdapter.getPosition(actvSortBy.text.toString()),
                        category = categoryAdapter.getPosition(actvCategory.text.toString())
                    )
                }

                btnSearch.setOnClickListener(clickAction)
                btnRetry.setOnClickListener(clickAction)
            }
        }
    }

    private fun searchGame(
        platform: Int,
        sort: Int,
        category: Int,
    ) {
        viewModel.handleIntent(BrowseIntent.LoadGamesIntent(platform, sort, category))
        toggleFiltersVisibility()
    }

    private fun toggleFiltersVisibility() {
        binding.panelFilter.apply {
            groupFilters.toggleVisibility(root)
            if (groupFilters.isVisible) {
                tvShowFilter.text = getString(R.string.text_hide)
                tvShowFilter.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_arrow_up, 0, R.drawable.ic_arrow_up, 0
                )
            } else {
                tvShowFilter.text = getString(R.string.text_filters)
                tvShowFilter.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_arrow_down, 0, R.drawable.ic_arrow_down, 0
                )
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.browseState.observe(viewLifecycleOwner) { state ->
            handleState(state)
        }

        disposable = viewModel
            .browseEvent
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                handleEvent(event)
            }
    }

    private fun handleState(state: BrowseState) {
        binding.apply {
            pbLoading.isVisible = state is BrowseState.LoadingState
            btnRetry.isVisible = state is BrowseState.ErrorState
            rvGames.isVisible = state is BrowseState.DataState
        }

        if (state is BrowseState.DataState)
            gameAdapter.submitList(state.data) {
                binding.rvGames.scrollToPosition(0)
            }
    }

    private fun handleEvent(event: BrowseEvent) {
        when (event) {
            is BrowseEvent.ShowErrorEvent -> {
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
            is BrowseEvent.UpdateFiltersEvent -> {
                binding.panelFilter.apply {
                    actvPlatform.setText(platforms[event.platform], false)
                    actvSortBy.setText(sortTypes[event.sort], false)
                    actvCategory.setText(categories[event.category], false)
                }
            }
            is BrowseEvent.NavigateToDetailsEvent -> {
                findNavController().navigate(
                    BrowseFragmentDirections.actionBrowseToDetails(event.id)
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