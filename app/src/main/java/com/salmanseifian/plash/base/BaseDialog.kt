package com.salmanseifian.plash.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.salmanseifian.plash.R
import kotlinx.android.synthetic.main.dialog_base.*

abstract class BaseDialog : DialogFragment() {

    abstract fun title(): String

    abstract fun contentLayout(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_base, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txt_dialog_title.text = title()

        val container = view.findViewById<ViewGroup>(R.id.layout_container)
        val content = LayoutInflater.from(context).inflate(contentLayout(), null)
        container.addView(content)
    }
}