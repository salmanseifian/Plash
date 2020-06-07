package com.salmanseifian.plash.features.auth

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.salmanseifian.plash.R
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    private val br: BroadcastReceiver = MyBroadcastReceiver()

    private val filter by lazy {
        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        registerReceiver(br, filter)
    }

    override fun onDestroy() {
        unregisterReceiver(br)
        super.onDestroy()
    }
}