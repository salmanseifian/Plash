package com.salmanseifian.plash.features.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.salmanseifian.plash.MainActivity
import com.salmanseifian.plash.R
import com.salmanseifian.plash.base.BaseFragment
import com.salmanseifian.plash.extensions.getInputOrError
import com.salmanseifian.plash.utils.Utils.KEY.RC_SIGN_IN
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.support.v4.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    private val loginViewModel: LoginViewModel by viewModel()

    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private val providers by lazy {
        arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.successLogin.observe(viewLifecycleOwner, Observer {
            if (it) {
                startActivity<MainActivity>()
            }
        })

        btn_next.setOnClickListener {
            if (edit_text_username.getInputOrError()
                    .isNotEmpty() && edit_text_password.getInputOrError().isNotEmpty()
            ) {
                loginViewModel.login(
                    edit_text_username.getInputOrError(),
                    edit_text_password.getInputOrError()
                )
            }
        }

        btn_other_ways.setOnClickListener {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN
            )
        }
    }

}