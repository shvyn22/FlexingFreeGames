package shvyn22.flexingfreegames.presentation.browse

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.databinding.FragmentBrowseBinding

@AndroidEntryPoint
class BrowseFragment: Fragment(R.layout.fragment_browse) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBrowseBinding.bind(view)
    }
}