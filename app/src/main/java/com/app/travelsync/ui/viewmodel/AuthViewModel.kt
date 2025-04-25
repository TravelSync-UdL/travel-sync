package com.app.travelsync.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.travelsync.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository, // Injectem el repositori d'autenticació
    private val auth: FirebaseAuth // Injectem FirebaseAuth per gestionar l'autenticació
) : ViewModel() {

    private val TAG = "AuthViewModel"

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> get() = _authState

    init {
        Log.d(TAG, "ViewModel initialized, checking auth status")
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Log.d(TAG, "User is not authenticated")
            _authState.value = AuthState.Unauthenticated
        } else {
            Log.d(TAG, "User is authenticated: ${currentUser.email}")
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String) {
        Log.d(TAG, "Attempting login with email: $email")

        if (email.isEmpty() || password.isEmpty()) {
            val error = "Email or password can't be empty"
            Log.e(TAG, error)
            _authState.value = AuthState.Error(error)
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val user = authRepository.login(email, password)
                if (user != null) {
                    Log.d(TAG, "Login successful: ${user.email}")
                    _authState.value = AuthState.Authenticated
                } else {
                    val error = "Incorrect credentials"
                    Log.e(TAG, error)
                    _authState.value = AuthState.Error(error)
                }
            } catch (e: Exception) {
                val error = "Login error: ${e.message}"
                Log.e(TAG, error, e)
                _authState.value = AuthState.Error("Error: ${e.message}")
            }
        }
    }


    // Funció de registre amb coroutines i gestió de l'estat
    /*fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        _authState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val user = authRepository.signup(email, password)
                if (user != null) {
                    _authState.value = AuthState.Authenticated
                    sendEmailVerification() // Enviem un correu de verificació
                } else {
                    _authState.value = AuthState.Error("Registration failed")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Error: ${e.message}")
            }
        }
    }*/

    fun signout() {
        Log.d(TAG, "Signing out user: ${auth.currentUser?.email}")
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email verification sent to ${user.email}")
                } else {
                    Log.e(TAG, "Failed to send verification email")
                }
            }
    }
}

// Estat d'autenticació
sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}
