package com.salmanseifian.plash.features.singlephoto

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.salmanseifian.plash.base.BaseRxAndroidViewModel
import com.salmanseifian.plash.features.photos.PhotosRepository
import com.salmanseifian.plash.network.response.ApiSinglePhoto
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class SinglePhotoViewModel(
    application: Application,
    private val photosRepository: PhotosRepository
) : BaseRxAndroidViewModel(application) {

    val photoLiveData: LiveData<ApiSinglePhoto>
        get() = _photoLiveData
    private var _photoLiveData = MutableLiveData<ApiSinglePhoto>()

    var mContext: Context = application.applicationContext

    private lateinit var _photo: ApiSinglePhoto

    var downloadId: Long = 0L

    fun getSinglePhoto(photoId: String) {
        photosRepository.getPhoto(photoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    _photo = it
                    _photoLiveData.value = it
                }
                , onError = {
                    Timber.d(it)
                }
            ).addTo(compositeDisposable)
    }


    fun download() {
        val request = DownloadManager.Request(Uri.parse(_photo.urls?.full))
            .setTitle("Download Image")
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                System.currentTimeMillis().toString()
            )
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        val downloadManager = mContext.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadId = downloadManager.enqueue(request)
    }


}