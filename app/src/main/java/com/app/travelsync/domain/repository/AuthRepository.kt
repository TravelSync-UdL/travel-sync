package com.app.travelsync.domain.repository

import com.app.travelsync.ui.viewmodel.AuthState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface AuthRepository {
    suspend fun login(email: String, password: String): FirebaseUser?
    suspend fun signup(email: String, password: String): FirebaseUser?
}
