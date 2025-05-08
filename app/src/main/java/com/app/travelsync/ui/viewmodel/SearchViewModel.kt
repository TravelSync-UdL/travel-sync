package com.app.travelsync.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.travelsync.data.SharedPrefsManager
import com.app.travelsync.data.local.entity.ReservationEntity
import com.app.travelsync.domain.model.Hotel
import com.app.travelsync.domain.model.Room
import com.app.travelsync.domain.model.Trip
import com.app.travelsync.domain.repository.HotelRepository
import com.app.travelsync.domain.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val hotelRepository: HotelRepository,
    private val tripRepository: TripRepository,
    private val sharedPrefsManager: SharedPrefsManager
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
                hotelList = hotelRepository.list(groupId)
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
                val response = hotelRepository.searchHotels("G12", startDate, endDate, city)
                hotelList = response
            } catch (e: Exception) {
                println(e.message)
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }


    fun bookRoom(
        hotel: Hotel,
        room: Room,
        startDate: String,
        endDate: String
    ) {
        viewModelScope.launch {
            try {

                val userEmail = sharedPrefsManager.userEmail
                    ?: throw Exception("No s'ha pogut obtenir l'email de l'usuari.")

                Log.d("Login", "User is authenticated: ${userEmail}")

                // 1. Crear el Trip
                val trip = Trip(
                    title = "Viatge a ${hotel.address}",
                    destination = hotel.address,
                    startDate = startDate,
                    endDate = endDate,
                    ownerLogin = userEmail,
                    itinerary = emptyList()
                )
                tripRepository.addTrip(trip)

                // 2. Obtenir l'últim trip creat per aquest usuari
                val trips = tripRepository.getTripsForUser(userEmail)
                val createdTrip = trips.maxByOrNull { it.tripId }
                    ?: throw Exception("No s'ha pogut obtenir el viatge recent creat.")

                // 3. Fer la reserva (la lògica del guardat local ja és dins del repositori)
                hotelRepository.reserveRoom(
                    groupId = "G12",
                    hotelId = hotel.id,
                    roomId = room.id,
                    startDate = startDate,
                    endDate = endDate,
                    tripId = createdTrip.tripId // Pas del tripId al repo
                )

                println("Reserva feta i viatge creat amb ID: ${createdTrip.tripId}")

            } catch (e: Exception) {
                println("Error reservant i creant viatge: ${e.message}")
                errorMessage = e.message
            }
        }
    }



    fun carregarReserves() {
        viewModelScope.launch {
            try {
                val reserves = hotelRepository.getLocalReservations()
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