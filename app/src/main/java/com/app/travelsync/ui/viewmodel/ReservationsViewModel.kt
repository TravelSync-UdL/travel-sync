package com.app.travelsync.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.travelsync.data.SharedPrefsManager
import com.app.travelsync.domain.model.Reservation
import com.app.travelsync.domain.repository.HotelRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationsViewModel @Inject constructor(
    private val repo: HotelRepository,
    sharedPrefsManager: SharedPrefsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReservationsUiState())
    val uiState: StateFlow<ReservationsUiState> = _uiState

    val guestEmail = sharedPrefsManager.userEmail

    fun load() = viewModelScope.launch {
        _uiState.update { it.copy(loading = true) }

        //IMPORTANTE USANDO EL GROUP ID!!!
        //AQUI tambien buscar el correo del usuario authenticado!!

        val res = repo.getGroupReservations("G12", guestEmail)
        _uiState.value = ReservationsUiState(false, res)
    }

    fun cancel(r: Reservation) = viewModelScope.launch {
        Log.d("viewmodel", "canceling: ${r.id}")
        repo.cancelById(r.id)
        _uiState.update { it.copy(reservations = it.reservations - r) } // quita de la lista
        load()
    }
}


data class ReservationsUiState(
    val loading: Boolean = true,
    val reservations:  List<Reservation> = emptyList()
)