package com.app.travelsync.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.app.travelsync.data.SharedPrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Per poder gestionar la lògica de negoci i el seu estat de la interfície d'usuari a la pantalla "Settings", i les SharedPreferences (que ens mantindran una memòria de forma local).
 */
@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPrefsManager: SharedPrefsManager
) : ViewModel() {

    // Variables de estado inicializadas con los valores guardados.
    var language by mutableStateOf(sharedPrefsManager.userLanguage ?: "es")
        private set

    var name by mutableStateOf(sharedPrefsManager.userName ?: "")
        private set

    var surname by mutableStateOf(sharedPrefsManager.userSurname ?: "")
        private set

    var username by mutableStateOf(sharedPrefsManager.userUsername ?: "")
        private set

    var email by mutableStateOf(sharedPrefsManager.userEmail ?: "")
        private set

    var password by mutableStateOf(sharedPrefsManager.userPassword ?: "")
        private set

    fun updateLanguage(newLanguage: String) {
        sharedPrefsManager.userLanguage = newLanguage
        language = newLanguage
    }

    fun updateName(newName: String) {
        sharedPrefsManager.userName = newName
        name = newName
    }

    fun updateSurname(newSurname: String) {
        sharedPrefsManager.userSurname = newSurname
        surname = newSurname
    }

    fun updateUsername(newUsername: String) {
        sharedPrefsManager.userUsername = newUsername
        username = newUsername
    }

    fun updateEmail(newEmail: String) {
        sharedPrefsManager.userEmail = newEmail
        email = newEmail
    }

    fun updatePassword(newPassword: String) {
        sharedPrefsManager.userPassword = newPassword
        password = newPassword
    }

    fun saveSettings(newName: String, newSurname: String, newUsername: String, newEmail: String, newPassword: String) {
        updateName(newName)
        updateSurname(newSurname)
        updateUsername(newUsername)
        updateEmail(newEmail)
        updatePassword(newPassword)
    }
}

