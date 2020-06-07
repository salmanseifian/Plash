package com.salmanseifian.plash.utils

object Utils {

    const val NUMBERS_BASE_URL = "http://numbersapi.com/"
    const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"
    const val UNSPLASH_BASE_URL_VERSION = "v1"
    const val DATABASE_NAME = "mandala-app"
    const val MAX_RETRIES = 3L
    const val TIMEOUT = 20L
    private const val INITIAL_BACKOFF = 2000L


    fun getBackoffDelay(attempt: Long) = INITIAL_BACKOFF * (attempt + 1)


    object KEY {
        const val RC_SIGN_IN = 1000
        const val BUNDLE_PHOTO_ID = "photoId"
    }
}