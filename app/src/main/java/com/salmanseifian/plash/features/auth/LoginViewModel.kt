package com.salmanseifian.plash.features.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    val successLogin: LiveData<Boolean>
        get() = _successLogin

    private var _successLogin = MutableLiveData(false)


    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            userRepository.login(email, password)
            _successLogin.postValue(true)
        }

        //TODO fix it for production
//        firebaseAuth(email, password)
    }

    private fun firebaseAuth(email: String, password: String) {
        auth.signInWithEmailAndPassword(
            email,
            password
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    _successLogin.postValue(true)
                } else {
                    _successLogin.postValue(false)
                }
            }
    }
}