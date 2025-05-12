package com.app.travelsync.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.travelsync.data.local.entity.ReservationEntity
import com.app.travelsync.domain.repository.HotelRepository
import com.app.travelsync.domain.repository.ReservationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val repo: ReservationRepository,
    private val hotelRepository: HotelRepository
) : ViewModel() {

    private val _reservation = MutableStateFlow<ReservationEntity?>(null)
    val reservation: StateFlow<ReservationEntity?> = _reservation


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _roomImageUrl = MutableStateFlow<List<String>?>(null)
    val roomImageUrl: StateFlow<List<String>?> = _roomImageUrl



    fun loadRoomImageUrl(reservationId: String) {
        viewModelScope.launch {
            try {
                val reservation = hotelRepository.getReservationById(reservationId)
                val images = reservation.room?.images
                if (!images.isNullOrEmpty()) {
                    _roomImageUrl.value = images
                } else {
                    _roomImageUrl.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e("ReservationViewModel", "Error carregant la imatge de l'habitació: ${e.localizedMessage}")
                _roomImageUrl.value = emptyList()
            }
        }
    }



    fun loadReservation(resId: Int) {
        viewModelScope.launch {
            try {
                Log.d("ReservationDetails VM", resId.toString())
                val result = repo.getReservation(resId)
                _reservation.value = result
                Log.d("ReservationDetails VM", result.toString())
            } catch (e: Exception) {
                _error.value = "Error carregant reserva: ${e.localizedMessage}"
            }
        }
    }

}
