package com.salmanseifian.plash.features.photos

import com.salmanseifian.plash.common.Status

class PhotosStatusViewState(val status: Status) {

    fun isLoading() = status is Status.Loading

    fun getErrorMessage() = if (status is Status.Error) status.exception.message else ""

    fun shouldShowErrorMessage() = status is Status.Error
}