package com.salmanseifian.plash.features.singlephoto

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.salmanseifian.plash.R
import com.salmanseifian.plash.base.BaseFragment
import com.salmanseifian.plash.extensions.load
import com.salmanseifian.plash.network.response.ApiSinglePhoto
import com.salmanseifian.plash.utils.Utils
import kotlinx.android.synthetic.main.fragment_single_photo.*
import org.jetbrains.anko.support.v4.toast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class SinglePhotoFragment : BaseFragment() {

    private val singlePhotoViewModel: SinglePhotoViewModel by sharedViewModel()
    private val singlePhotoObservable = Observer<ApiSinglePhoto>() {
        onSinglePhotoLoaded(it)
    }

    private lateinit var downloadCompleteReceiver: BroadcastReceiver

    private fun onSinglePhotoLoaded(nullableSinglePhoto: ApiSinglePhoto?) {
        nullableSinglePhoto?.let {
            img_large.load(it.urls?.regular)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single_photo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        arguments?.let {
            val photoId = it[Utils.KEY.BUNDLE_PHOTO_ID] as String
            singlePhotoViewModel.getSinglePhoto(photoId)

        }
        singlePhotoViewModel.photoLiveData.observe(viewLifecycleOwner, singlePhotoObservable)

        super.onActivityCreated(savedInstanceState)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        img_more.setOnClickListener {
            findNavController().navigate(R.id.action_singlePhotoFragment_to_moreDialog)
        }
        registerDownloadReceiver()
    }

    private fun registerDownloadReceiver() {
        val downloadCompleteIntentName = DownloadManager.ACTION_DOWNLOAD_COMPLETE
        val downloadCompleteIntentFilter = IntentFilter(downloadCompleteIntentName)
        downloadCompleteReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {

                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L)
                if (id != singlePhotoViewModel.downloadId) {
                    Timber.i("Ignoring unrelated download $id")
                    return
                }
                toast("Download Completed")
            }

        }

        activity?.registerReceiver(downloadCompleteReceiver, downloadCompleteIntentFilter)
    }


    override fun onDestroyView() {
        activity?.unregisterReceiver(downloadCompleteReceiver)
        super.onDestroyView()
    }


}