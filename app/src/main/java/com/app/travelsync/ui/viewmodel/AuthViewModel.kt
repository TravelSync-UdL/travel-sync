package com.app.travelsync.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.app.travelsync.data.local.dao.SessionLogDao
import com.app.travelsync.data.local.dao.UserDao
import com.app.travelsync.data.local.entity.SessionLogEntity
import com.app.travelsync.data.local.entity.UserEntity
import com.app.travelsync.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository, // Injectem el repositori d'autenticació
    private val auth: FirebaseAuth, // Injectem FirebaseAuth per gestionar l'autenticació
    private val sessionLogDao: SessionLogDao,
    private val userDao: UserDao
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


    fun signup(email: String, password: String, userEntity: UserEntity) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        _authState.value = AuthState.Loading

        viewModelScope.launch {
            try {
                // Comprovem si el nom d'usuari ja existeix a la base de dades local
                val usernameTaken = userDao.isUsernameTaken(userEntity.username)
                if (usernameTaken) {
                    _authState.value = AuthState.Error("Username is already taken")
                    return@launch
                }

                // Registre a Firebase
                val user = authRepository.signup(email, password)
                if (user != null) {
                    // Enviar l'email de verificació
                    sendEmailVerification(user)

                    // Desar l'usuari a la base de dades local
                    userEntity.login = email  // Asegura que el login sigui l'email
                    userDao.insertUser(userEntity)

                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error("Registration failed")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Error: ${e.message}")
            }
        }
    }



    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthViewModel", "Verification email sent.")
                } else {
                    Log.e("AuthViewModel", "Failed to send verification email.")
                }
            }
    }

    fun getAuthenticatedUserEmail(): String? {
        return auth.currentUser?.email
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthViewModel", "Verification email sent.")
                } else {
                    Log.e("AuthViewModel", "Failed to send verification email.")
                }
            }
    }


    fun signout(navController: NavController) {
        Log.d(TAG, "Signing out user: ${auth.currentUser?.email}")
        auth.signOut() // Desconnecta l'usuari

        // Comprovar l'estat d'usuari després de fer logout
        Log.d(TAG, "Current user after sign out: ${auth.currentUser?.email}") // Ha de ser null

        _authState.value = AuthState.Unauthenticated // Actualitza l'estat d'autenticació

        // Forçar la navegació a LoginScreen després de fer logout
        navController.navigate("login")
    }




    fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("AuthViewModel", "Password reset email sent to $email")
                } else {
                    Log.e("AuthViewModel", "Failed to send password reset email to $email")
                }
            }
    }

    private fun registerLogin(userId: String) {
        viewModelScope.launch {
            sessionLogDao.insertSessionLog(
                SessionLogEntity(
                    userId = userId,
                    timestamp = System.currentTimeMillis(),
                    action = "login"
                )
            )
        }
    }

    private fun registerLogout(userId: String) {
        viewModelScope.launch {
            sessionLogDao.insertSessionLog(
                SessionLogEntity(
                    userId = userId,
                    timestamp = System.currentTimeMillis(),
                    action = "logout"
                )
            )
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
