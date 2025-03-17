package com.app.travelsync.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.travelsync.data.SharedPrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Per poder gestionar la lògica de negoci i el seu estat de l'interfície d'usuari a la pantalla "Configuracio de usuari"
 */
@HiltViewModel
class ConfigAccountViewModel @Inject constructor(private val sharedPrefsManager: SharedPrefsManager) : ViewModel() {

    val userName = liveData { emit(sharedPrefsManager.userName) }
    val userSurname = liveData { emit(sharedPrefsManager.userSurname) }
    val userUsername = liveData { emit(sharedPrefsManager.userUsername) }
}