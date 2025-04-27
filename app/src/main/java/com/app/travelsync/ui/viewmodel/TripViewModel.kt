package com.app.travelsync.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.travelsync.domain.model.Trip
import com.app.travelsync.domain.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class TripViewModel @Inject constructor(
    private val repository: TripRepository
): ViewModel(){

    private val _trips = mutableStateListOf<Trip>()
    val trips: List<Trip> get() = _trips


    fun loadTripsForUser(login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val userTrips = repository.getTripsForUser(login)
            withContext(Dispatchers.Main) {
                _trips.clear()
                _trips.addAll(userTrips)
            }
        }
    }

    fun addTrip(trip: Trip, login: String, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.addTrip(trip)
                loadTripsForUser(login)
            } catch (e: IllegalArgumentException) {
                onError(e.message ?: "Error desconegut")
            }
        }
    }


    fun deleteTrip(tripId: Int, login: String) {
        viewModelScope.launch {
            repository.deleteTrip(tripId)
            loadTripsForUser(login)
        }
    }

    fun editTrip(trip: Trip, login: String) {
        viewModelScope.launch {
            repository.editTrip(trip)
            loadTripsForUser(login)
        }
    }


}