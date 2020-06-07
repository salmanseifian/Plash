package com.salmanseifian.plash.base

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import org.jetbrains.anko.AnkoLogger

abstract class BaseFragment : Fragment(), AnkoLogger {

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }
}