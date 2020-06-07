package com.salmanseifian.plash.network.api

import com.salmanseifian.plash.network.response.ApiPhoto
import com.salmanseifian.plash.network.response.ApiSinglePhoto
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {

    @GET("/photos")
    fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("order_by") orderBy: String = "latest"
    ): Observable<List<ApiPhoto>>


    @GET("/photos/{photoId}")
    fun getSinglePhoto(@Path("photoId") photoId: String): Observable<ApiSinglePhoto>
}