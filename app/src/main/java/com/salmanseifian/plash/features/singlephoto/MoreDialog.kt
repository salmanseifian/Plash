package com.salmanseifian.plash.features.singlephoto

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.salmanseifian.plash.R
import kotlinx.android.synthetic.main.dialog_more.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoreDialog : BottomSheetDialogFragment() {

    private val singlePhotoViewModel: SinglePhotoViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txt_download_image.setOnClickListener {
            handleStoragePermission {
                singlePhotoViewModel.download()
                dismiss()
            }

        }
    }

    fun handleStoragePermission(whatToDo: () -> Unit) {
        activity?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    100
                );

            } else {
                whatToDo.invoke()
            }
        }

    }

}