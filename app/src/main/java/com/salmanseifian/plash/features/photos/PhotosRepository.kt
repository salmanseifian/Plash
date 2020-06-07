package com.salmanseifian.plash.features.photos

import com.salmanseifian.plash.network.api.UnsplashApi
import com.salmanseifian.plash.network.response.ApiPhoto
import com.salmanseifian.plash.network.response.ApiSinglePhoto
import io.reactivex.rxjava3.core.Observable

class PhotosRepository(private val unsplashApi: UnsplashApi) {


    fun getPhotos(page: Int = 1): Observable<List<ApiPhoto>> {
        val observableFromApi = getPhotosFromApi(page)
//        val observableFromDb = getPhotosFromDb()
//        return Observable.concatArrayEager(observableFromApi, observableFromDb)
        return observableFromApi

    }


    private fun getPhotosFromApi(page: Int = 1): Observable<List<ApiPhoto>> {
        return unsplashApi.getPhotos(page)
    }

    fun getPhoto(photoId: String): Observable<ApiSinglePhoto> {
        val observbleFromApi = getSinglePhotoFromApi(photoId)

        return observbleFromApi

    }

    private fun getSinglePhotoFromApi(photoId: String) = unsplashApi.getSinglePhoto(photoId)


}


