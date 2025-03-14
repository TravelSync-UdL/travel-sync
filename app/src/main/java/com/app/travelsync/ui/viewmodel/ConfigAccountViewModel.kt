package com.app.travelsync.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.travelsync.data.SharedPrefsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConfigAccountViewModel @Inject constructor(private val sharedPrefsManager: SharedPrefsManager) : ViewModel() {

    val userName = liveData { emit(sharedPrefsManager.userName) }
    val userSurname = liveData { emit(sharedPrefsManager.userSurname) }
    val userUsername = liveData { emit(sharedPrefsManager.userUsername) }
}