package com.salmanseifian.plash.features.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.salmanseifian.plash.R
import com.salmanseifian.plash.base.BaseFragment
import com.salmanseifian.plash.network.response.ApiPhoto
import com.salmanseifian.plash.utils.EndlessRecyclerViewScrollListener
import com.salmanseifian.plash.utils.OnBottomReachedListener
import kotlinx.android.synthetic.main.fragment_photos.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosFragment : BaseFragment(), OnBottomReachedListener {

    private val photosViewModel: PhotosViewModel by viewModel()
    private val photosObserver = Observer<List<ApiPhoto>> { photos ->
        updatePhotoList(photos)
    }
    private val loadingObserver = Observer<Boolean> { shouldShow ->
        handleLoading(shouldShow)
    }

    private val photosAdapter by lazy {
        PhotosAdapter(this)
    }

    private var photosLayoutManager: StaggeredGridLayoutManager? =
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


    private val scrollListener by lazy {
        object : EndlessRecyclerViewScrollListener(photosLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
//                photosViewModel.getPhotos()
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(rv_photos) {
            adapter = photosAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
//            layoutManager = photosLayoutManager
//            layoutManager = GridLayoutManager(context, 2)
//            addOnScrollListener(scrollListener)
        }

        srl_photos.setOnRefreshListener {
            photosAdapter.clear()
            photosViewModel.reload()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        photosViewModel.getPhotos()
        photosViewModel.photosLiveData.observe(viewLifecycleOwner, photosObserver)
        photosViewModel.loading.observe(viewLifecycleOwner, loadingObserver)
    }

    private fun updatePhotoList(photos: List<ApiPhoto>?) {
        photos?.let {
            photosAdapter.photos = photos.toMutableList()
        }
    }

    private fun handleLoading(shouldShow: Boolean) {
        srl_photos.isRefreshing = shouldShow
    }

    override fun onBottomReached(position: Int) {
        photosViewModel.getPhotos()
    }
}