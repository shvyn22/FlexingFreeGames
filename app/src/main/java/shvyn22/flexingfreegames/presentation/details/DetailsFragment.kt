package shvyn22.flexingfreegames.presentation.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.databinding.FragmentDetailsBinding

@AndroidEntryPoint
class DetailsFragment: Fragment(R.layout.fragment_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsBinding.bind(view)
    }
}