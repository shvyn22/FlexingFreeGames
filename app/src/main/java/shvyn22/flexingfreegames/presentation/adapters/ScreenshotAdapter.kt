package shvyn22.flexingfreegames.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import shvyn22.flexingfreegames.data.local.model.ScreenshotModel
import shvyn22.flexingfreegames.databinding.ItemScreenshotBinding
import shvyn22.flexingfreegames.util.defaultRequests

class ScreenshotAdapter :
    ListAdapter<ScreenshotModel, ScreenshotAdapter.ScreenshotViewHolder>(screenshotDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotViewHolder {
        return ScreenshotViewHolder(
            ItemScreenshotBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ScreenshotViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ScreenshotViewHolder(
        private val binding: ItemScreenshotBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScreenshotModel) {
            binding.apply {
                Glide.with(itemView)
                    .load(item.image)
                    .defaultRequests()
                    .into(ivScreenshot)

                ivPrevious.isVisible = adapterPosition != 0
                ivNext.isVisible = adapterPosition != currentList.lastIndex
            }
        }
    }

    companion object {
        private val screenshotDiffUtil = object : DiffUtil.ItemCallback<ScreenshotModel>() {
            override fun areItemsTheSame(
                oldItem: ScreenshotModel,
                newItem: ScreenshotModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ScreenshotModel,
                newItem: ScreenshotModel
            ): Boolean = oldItem == newItem
        }
    }
}