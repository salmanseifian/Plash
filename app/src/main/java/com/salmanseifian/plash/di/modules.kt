package com.salmanseifian.plash.di

import androidx.room.Room
import com.salmanseifian.plash.BuildConfig
import com.salmanseifian.plash.features.auth.LoginViewModel
import com.salmanseifian.plash.features.auth.UserRepository
import com.salmanseifian.plash.features.photos.PhotosRepository
import com.salmanseifian.plash.features.photos.PhotosViewModel
import com.salmanseifian.plash.features.singlephoto.SinglePhotoViewModel
import com.salmanseifian.plash.network.api.UnsplashApi
import com.salmanseifian.plash.room.db.MandalaDatabase
import com.salmanseifian.plash.utils.Secrets
import com.salmanseifian.plash.utils.Utils
import com.squareup.moshi.Moshi
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { PhotosViewModel(get()) }
    viewModel { SinglePhotoViewModel(get(), get()) }
}

val repositoryModule = module {
    single { UserRepository(get()) }
    single { PhotosRepository(get()) }
}

val apiModule = module {

    single {
        OkHttpClient.Builder().apply {
            readTimeout(Utils.TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(Utils.TIMEOUT, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(this)
                }
            }
            addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Client-ID ${Secrets.UNSPLASH_CLIENT_ID}")
                    .addHeader("Accept-Version", Utils.UNSPLASH_BASE_URL_VERSION)
                    .build()
                chain.proceed(request)
            }

        }.build()
    }

//    single<Retrofit> {
//        Retrofit.Builder()
//            .baseUrl(Utils.NUMBERS_BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create())
//            .callFactory { get<OkHttpClient>().newCall(it) }
//            .build()
//    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(Utils.UNSPLASH_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
            .build()
    }

//    single<NumbersApi> {
//        get<Retrofit>().create(NumbersApi::class.java)
//    }

    single {
        get<Retrofit>().create(UnsplashApi::class.java)
    }

    single<Moshi> {
        Moshi.Builder().build()
    }

}

val dbModule = module {

    single {
        Room.databaseBuilder(androidContext(), MandalaDatabase::class.java, Utils.DATABASE_NAME)
            .build()
    }

    single {
        get<MandalaDatabase>().numberDao
    }

    single {
        get<MandalaDatabase>().userDao
    }

    single {
        get<MandalaDatabase>().noteDao
    }
}