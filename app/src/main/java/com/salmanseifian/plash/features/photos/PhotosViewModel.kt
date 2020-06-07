package com.salmanseifian.plash.features.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.salmanseifian.plash.base.BaseRxViewModel
import com.salmanseifian.plash.network.response.ApiPhoto
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class PhotosViewModel(private val photosRepository: PhotosRepository) : BaseRxViewModel() {

    val photosLiveData: LiveData<List<ApiPhoto>>
        get() = _photosLiveData

    private var _photosLiveData = MutableLiveData<List<ApiPhoto>>()

    val status: LiveData<PhotosStatusViewState>
        get() = _status
    private val _status = MutableLiveData<PhotosStatusViewState>()

    val loading: LiveData<Boolean>
        get() = _loading
    private val _loading = MutableLiveData<Boolean>()

    private var _page = 1

    fun getPhotos() {
        photosRepository.getPhotos(_page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _loading.postValue(true)
            }
            .doOnError {
                _loading.postValue(false)
            }
            .doOnComplete {
                _loading.postValue(false)
            }
            .doOnNext {
                _page++
            }
            .subscribeBy(onError = { throwable ->
                throwable.printStackTrace()
            }, onNext = { photos ->
                _photosLiveData.postValue(photos)
            })
            .addTo(compositeDisposable)

    }

    fun reload() {
        _page = 1
        getPhotos()
    }
}