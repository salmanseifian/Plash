package com.salmanseifian.plash.extensions

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.textfield.TextInputEditText
import com.salmanseifian.plash.base.Progress
import com.salmanseifian.plash.base.Result
import com.salmanseifian.plash.utils.Utils
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import okhttp3.Call
import okhttp3.Request
import retrofit2.Retrofit
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


fun today(): String {
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("en"))
    val today = Date()
    return formatter.format(today)
}

fun getDay(): Int {
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    return calendar[Calendar.DAY_OF_MONTH]
}


fun getMonth(): Int {
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    return calendar[Calendar.MONTH]
}


@PublishedApi
internal inline fun Retrofit.Builder.callFactory(crossinline body: (Request) -> Call) =
    callFactory(object : Call.Factory {
        override fun newCall(request: Request): Call =
            body(request)
    })

fun <T : Any> Flow<Result<T>>.applyCommonSideEffetcts() =
    retryWhen { cause, attempt ->
        when {
            (cause is IOException && attempt < Utils.MAX_RETRIES) -> {
                delay(Utils.getBackoffDelay(attempt))
                true
            }
            else -> {
                false
            }
        }
    }
        .onStart { emit(Progress(isLoading = true)) }
        .onCompletion { emit(Progress(isLoading = false)) }

fun Job?.cancelIfActive() {
    if (this?.isActive == true) {
        cancel()
    }
}

fun TextInputEditText.getInputOrError(): String {
    return if (text.toString().isNotEmpty()) {
        text.toString()
    } else {
        setError("Field shouldn't be empty")
        ""
    }
}

fun <T : Activity> Fragment.startActivityByClass(cls: Class<T>) {
    val intent = Intent(activity, cls)
    startActivity(intent)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun ImageView.load(string: String?) {
    Glide.with(context).load(string)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(64)))
        .into(this)
}