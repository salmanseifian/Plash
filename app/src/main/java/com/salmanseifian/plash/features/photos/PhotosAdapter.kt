package com.salmanseifian.plash.features.photos

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.salmanseifian.plash.R
import com.salmanseifian.plash.extensions.inflate
import com.salmanseifian.plash.extensions.load
import com.salmanseifian.plash.network.response.ApiPhoto
import com.salmanseifian.plash.utils.OnBottomReachedListener
import com.salmanseifian.plash.utils.Utils

class PhotosAdapter(private val onBottomReachedListener: OnBottomReachedListener) :
    RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {

    var photos: MutableList<ApiPhoto> = mutableListOf()
        set(value) {
            val oldfield = field
            field.addAll(value)
            notifyItemRangeInserted(oldfield.size, value.size)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(parent.inflate(R.layout.item_photo))
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bindTo(photos[position])

        if (position == photos.size - 1) {
            onBottomReachedListener.onBottomReached(position)
        }
    }

    fun clear() {
        photos.clear()
        notifyDataSetChanged()
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgSmall = itemView.findViewById<ImageView>(R.id.img_small)
        private val llPhotoBg = itemView.findViewById<ViewGroup>(R.id.ll_photo_bg)

        fun bindTo(apiPhoto: ApiPhoto) {
            imgSmall.load(apiPhoto.urls.thumb)

            itemView.setOnClickListener {
                val bundle = bundleOf(Utils.KEY.BUNDLE_PHOTO_ID to apiPhoto.id)
                itemView.findNavController()
                    .navigate(R.id.action_navigation_photos_to_singlePhotoFragment, bundle)
            }

//            llPhotoBg.setBackgroundColor(Color.parseColor(apiPhoto.color))
        }

    }


}