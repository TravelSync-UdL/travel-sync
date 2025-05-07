package com.app.travelsync.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.travelsync.domain.model.Hotel
import com.app.travelsync.domain.model.Room
import com.app.travelsync.domain.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: HotelRepository
) : ViewModel() {

    var hotelList by mutableStateOf<List<Hotel>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        loadHotels("G12")
    }

    fun loadHotels(groupId: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                hotelList = repository.list(groupId)
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun searchHotels(city: String, startDate: String, endDate: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = repository.searchHotels("G12", startDate, endDate, city)
                hotelList = response
            } catch (e: Exception) {
                println(e.message)
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }


    fun bookRoom(hotel: Hotel, room: Room, startDate: String, endDate: String) {
        viewModelScope.launch {
            try {
                // Mostrar la informació de la reserva que s'envia
                println("Reserva: Room ID: ${room.id}, Start Date: $startDate, End Date: $endDate")

                val reservation =
                    repository.reserveRoom("G12", hotel.id, room.id, startDate, endDate)
                // Mostrar la resposta de la reserva
                println("Reserva creat amb ID: ${reservation.reservation.id}")
            } catch (e: Exception) {
                // Capturar i mostrar l'error
                println("Error reservant: ${e.message}")
            }
        }
    }

    fun carregarReserves() {
        viewModelScope.launch {
            try {
                val reserves = repository.getLocalReservations()
                reserves.forEach {
                    Log.d(
                        "Reserva",
                        "Hotel: ${it.hotelName}, Room: ${it.roomType}, Dates: ${it.startDate} - ${it.endDate}, Email: ${it.userEmail}"
                    )
                }
            } catch (e: Exception) {
                Log.e("Reserva", "Error llegint reserves: ${e.message}")
            }

        }
    }
}