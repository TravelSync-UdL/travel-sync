package com.app.travelsync.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.travelsync.data.local.entity.ReservationEntity
import com.app.travelsync.domain.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val hotelRepository: HotelRepository
) : ViewModel() {

    // Canviar de mutableStateOf a StateFlow per a poder fer collectAsState()
    private val _reservation = MutableStateFlow<ReservationEntity?>(null)
    val reservation: StateFlow<ReservationEntity?> = _reservation

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _roomImageUrl = MutableStateFlow<String?>(null)
    val roomImageUrl: StateFlow<String?> = _roomImageUrl

    // Funció per carregar la reserva
    fun loadReservation(resId: Int) {
        viewModelScope.launch {
            try {
                val result = hotelRepository.getReservationById(resId)
                if (result != null) {
                    _reservation.value = result
                } else {
                    _error.value = "No s'ha trobat cap reserva amb ID $resId"
                }
            } catch (e: Exception) {
                _error.value = "Error carregant reserva: ${e.localizedMessage}"
            }
        }
    }

    fun loadRoomImageUrl(reservationId: String) {
        viewModelScope.launch {
            _roomImageUrl.value = hotelRepository.getRoomImageUrl(reservationId)
        }
    }
}
