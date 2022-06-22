package shvyn22.flexingfreegames.presentation.browse

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.databinding.FragmentBrowseBinding
import shvyn22.flexingfreegames.presentation.adapters.GameAdapter
import shvyn22.flexingfreegames.util.ResourceError
import shvyn22.flexingfreegames.util.collectOnLifecycle
import shvyn22.flexingfreegames.util.toggleVisibility

@AndroidEntryPoint
class BrowseFragment : Fragment(R.layout.fragment_browse) {

    private val viewModel: BrowseViewModel by viewModels()

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    private val gameAdapter = GameAdapter {
        viewModel.handleIntent(BrowseIntent.GameClickIntent(it))
    }

    private var platformAdapter: ArrayAdapter<String>? = null
    private var sortAdapter: ArrayAdapter<String>? = null
    private var categoryAdapter: ArrayAdapter<String>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentBrowseBinding.bind(view)

        initUI()
        subscribeObservers()
    }

    private fun initUI() {
        binding.apply {
            rvGames.adapter = gameAdapter

            val platforms = resources.getStringArray(R.array.platforms).toList()
            val sortTypes = resources.getStringArray(R.array.sort_types).toList()
            val categories = resources.getStringArray(R.array.categories).toList()

            platformAdapter =
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    platforms
                )
            sortAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                sortTypes
            )
            categoryAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                categories
            )

            panelFilter.apply {
                actvPlatform.setAdapter(platformAdapter)
                actvSortBy.setAdapter(sortAdapter)
                actvCategory.setAdapter(categoryAdapter)

                tvShowFilter.setOnClickListener {
                    groupFilters.toggleVisibility(root)
                }

                btnSearch.setOnClickListener {
                    viewModel.handleIntent(
                        BrowseIntent.LoadGamesIntent(
                            platform = platformAdapter
                                ?.getPosition(actvPlatform.text.toString()) ?: 0,
                            sort = sortAdapter?.getPosition(actvSortBy.text.toString()) ?: 0,
                            category = categoryAdapter
                                ?.getPosition(actvCategory.text.toString()) ?: 0
                        )
                    )
                }
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.browseState.collectOnLifecycle(viewLifecycleOwner) { state ->
            handleState(state)
        }

        viewModel.browseEvent.collectOnLifecycle(viewLifecycleOwner) { event ->
            handleEvent(event)
        }
    }

    private fun handleState(state: BrowseState) {
        binding.pbLoading.isVisible = state is BrowseState.LoadingState

        if (state is BrowseState.DataState) gameAdapter.submitList(state.data)
    }

    private fun handleEvent(event: BrowseEvent) {
        when (event) {
            is BrowseEvent.ShowErrorEvent -> {
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
            is BrowseEvent.UpdateFiltersEvent -> {
                binding.panelFilter.apply {
                    actvPlatform.setSelection(event.platform)
                    actvSortBy.setSelection(event.sort)
                    actvCategory.setSelection(event.category)
                }
            }
            is BrowseEvent.NavigateToDetailsEvent -> {
                findNavController().navigate(
                    BrowseFragmentDirections.actionBrowseToDetails(event.id)
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}