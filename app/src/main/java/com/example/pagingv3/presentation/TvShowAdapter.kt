package com.example.pagingv3.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pagingv3.data.model.TvShow
import com.example.pagingv3.databinding.FieldTvshowItemBinding

class TvShowAdapter : PagingDataAdapter<TvShow, TvShowAdapter.TvShowVH>(TvShowDiffCallback()) {

    var listener: ITvShowCallBack? = null

    override fun onBindViewHolder(holder: TvShowVH, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowVH {
        return TvShowVH(
            FieldTvshowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class TvShowVH(private val binding: FieldTvshowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

            init {
                binding.root.setOnClickListener {
                    getItem(bindingAdapterPosition)?.id?.let { it1 -> listener?.onSelect(it1) }
                }
            }

        fun onBind(data: TvShow) {

            binding.cbFieldTvShow.isChecked = data.isFavourite

            Glide.with(binding.root).load(data.imageThumbnailPath)
                .into(binding.ivFieldTvShowThumbnail)
            binding.tvFieldTvShowName.text = data.name
            binding.tvFieldTvShowCountry.text = data.country
        }
    }

    class TvShowDiffCallback : DiffUtil.ItemCallback<TvShow>() {
        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem.isFavourite == newItem.isFavourite
        }
    }

    interface ITvShowCallBack{
        fun onSelect(id: Int)
    }
}