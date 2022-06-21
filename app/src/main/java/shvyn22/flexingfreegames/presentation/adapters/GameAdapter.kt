package shvyn22.flexingfreegames.presentation.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.databinding.ItemGameBinding
import shvyn22.flexingfreegames.util.defaultRequests
import shvyn22.flexingfreegames.util.toggleVisibility

class GameAdapter(
    private val onClick: (Int) -> Unit,
): ListAdapter<GameModel, GameAdapter.GameViewHolder>(gameDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        return GameViewHolder(
            ItemGameBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class GameViewHolder(
        private val binding: ItemGameBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GameModel) {
            binding.apply {
                root.setOnClickListener { onClick(item.id) }

                Glide.with(itemView)
                    .load(item.thumbnail)
                    .defaultRequests()
                    .into(ivThumbnail)

                tvTitle.text = item.title
                tvPublisher.text = item.publisher
                tvReleaseDate.text = item.releaseDate
                panelDescription.apply {
                    tvDescription.text = item.description

                    tvShowDescription.setOnClickListener {
                        tvDescription.toggleVisibility(binding.root, Gravity.BOTTOM)
                    }
                }
            }
        }
    }

    companion object {
        private val gameDiffUtil = object : DiffUtil.ItemCallback<GameModel>() {
            override fun areItemsTheSame(oldItem: GameModel, newItem: GameModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GameModel, newItem: GameModel): Boolean =
                oldItem == newItem
        }
    }
}