package com.example.testwebapis.rndcats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testwebapis.R
import com.example.testwebapis.databinding.CatImgItemBinding
import com.example.testwebapis.rndcats.network.CatPhoto

class CatPhotosAdapter : ListAdapter<CatPhoto,
        CatPhotosAdapter.CatPhotoViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatPhotoViewHolder {
        return CatPhotoViewHolder(
            CatImgItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CatPhotoViewHolder, position: Int) {
        val catPhoto = getItem(position)
        holder.bind(catPhoto)
    }

    inner class CatPhotoViewHolder(private var binding: CatImgItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(catPhoto: CatPhoto) {
            binding.apply {
                val uri = catPhoto.imgSrcUrl.toUri().buildUpon().scheme("https").build()
                Glide.with(marsImage.context)
                    .load(uri)
                    .apply(
                        RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_baseline_broken_image))
                    .into(marsImage)

                executePendingBindings()        // causes the update to execute immediately.
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CatPhoto>() {
        override fun areContentsTheSame(oldItem: CatPhoto, newItem: CatPhoto): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areItemsTheSame(oldItem: CatPhoto, newItem: CatPhoto): Boolean {
            return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }
    }
}