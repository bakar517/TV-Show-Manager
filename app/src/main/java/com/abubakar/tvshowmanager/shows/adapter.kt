package com.abubakar.tvshowmanager.shows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.abubakar.tvshowmanager.R
import com.abubakar.tvshowmanager.databinding.ItemShowBinding


class ShowAdapter : ListAdapter<MovieItem, MovieViewHolder>(MovieDiffUtil) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_show,
                parent,
                false
            )
        )
    }

}

class MovieViewHolder(
    private val binding: ItemShowBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieItem?) {
        binding.lblTitle.text = item?.title
        binding.lblReleaseDate.text = item?.releaseDate
        binding.lblSeasons.text = item?.seasons.toString()
    }
}


object MovieDiffUtil : DiffUtil.ItemCallback<MovieItem>() {
    override fun areItemsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
        oldItem.title == newItem.title

    override fun areContentsTheSame(oldItem: MovieItem, newItem: MovieItem): Boolean =
        oldItem == newItem
}