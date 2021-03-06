package shvyn22.flexingfreegames.presentation.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.databinding.FragmentDetailsBinding
import shvyn22.flexingfreegames.presentation.adapters.ScreenshotAdapter
import shvyn22.flexingfreegames.util.ResourceError
import shvyn22.flexingfreegames.util.collectOnLifecycle
import shvyn22.flexingfreegames.util.defaultRequests

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {

    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val screenshotAdapter = ScreenshotAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailsBinding.bind(view)

        viewModel.handleIntent(DetailsIntent.LoadGameIntent(args.id))

        initUI()
        subscribeObservers()
    }

    private fun initUI() {
        binding.apply {
            vpScreenshots.adapter = screenshotAdapter

            btnRetry.setOnClickListener {
                viewModel.handleIntent(DetailsIntent.LoadGameIntent(args.id))
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.detailsState.collectOnLifecycle(viewLifecycleOwner) { state ->
            handleState(state)
        }

        viewModel.detailsEvent.collectOnLifecycle(viewLifecycleOwner) { event ->
            handleEvent(event)
        }
    }

    private fun handleState(state: DetailsState) {
        binding.apply {
            pbLoading.isVisible = state is DetailsState.LoadingState
            btnRetry.isVisible = state is DetailsState.ErrorState
            groupContent.isVisible = state is DetailsState.DataState

            if (state is DetailsState.DataState) {
                val game = state.data

                Glide.with(requireView())
                    .load(game.thumbnail)
                    .defaultRequests()
                    .into(ivThumbnail)

                tvTitle.text = game.title
                tvGenre.text = game.genre
                tvPublisher.text = game.publisher
                tvDeveloper.text = game.developer
                tvReleaseDate.text = game.releaseDate
                tvPlatform.text = game.platform
                tvDescription.text = game.detailedDescription

                screenshotAdapter.submitList(game.screenshots)

                if (game.systemRequirements != null) {
                    panelRequirements.apply {
                        tvOs.text = getString(R.string.text_os, game.systemRequirements.os)
                        tvProcessor.text =
                            getString(R.string.text_processor, game.systemRequirements.processor)
                        tvGraphics.text =
                            getString(R.string.text_graphics, game.systemRequirements.graphics)
                        tvMemory.text =
                            getString(R.string.text_memory, game.systemRequirements.memory)
                        tvStorage.text =
                            getString(R.string.text_storage, game.systemRequirements.storage)
                    }
                } else panelRequirements.root.isVisible = false

                ivFreetogame.setOnClickListener {
                    viewModel.handleIntent(
                        DetailsIntent.FreeToGameIconClickIntent(
                            game.freeToGameUrl
                        )
                    )
                }

                ivGame.setOnClickListener {
                    viewModel.handleIntent(DetailsIntent.GameIconClickIntent(game.gameUrl))
                }

                ivBookmark.apply {
                    setImageResource(
                        if (state.isBookmarked) R.drawable.ic_bookmarked
                        else R.drawable.ic_not_bookmarked
                    )

                    setOnClickListener {
                        viewModel.handleIntent(
                            if (state.isBookmarked) DetailsIntent.DeleteBookmarkIntent(game.id)
                            else DetailsIntent.InsertBookmarkIntent(game)
                        )
                    }
                }
            }
        }
    }

    private fun handleEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.ShowErrorEvent -> {
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
            is DetailsEvent.NavigateToFreeToGameEvent ->
                Intent(Intent.ACTION_VIEW, Uri.parse(event.url)).also { startActivity(it) }
            is DetailsEvent.NavigateToGameEvent ->
                Intent(Intent.ACTION_VIEW, Uri.parse(event.url)).also { startActivity(it) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}