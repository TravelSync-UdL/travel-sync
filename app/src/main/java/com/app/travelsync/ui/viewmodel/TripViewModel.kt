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

/**
 * Per poder gestionar la lògica de negoci i el seu estat de l'interfície d'usuari a la pantalla "Trip"
 */
@HiltViewModel
class TripViewModel @Inject constructor(
    private val repository: TripRepository
): ViewModel(){

    private val _trips = mutableStateListOf<Trip>()
    val trips: List<Trip> get() = _trips

    init {
        loadTripOld()
    }

    private fun loadTripOld() {
        _trips.clear()
        viewModelScope.launch(Dispatchers.IO) {
            _trips.addAll(repository.getTrip())
        }
    }

    private fun loadTrip() {
        viewModelScope.launch {
            // Fetch the tasks on IO thread
            val tripFromDb = withContext(Dispatchers.IO) { repository.getTrip() }
            // Update state on main thread
            _trips.clear()
            _trips.addAll(tripFromDb)
        }
    }

    fun addTrip(trip: Trip) {
        viewModelScope.launch {
            repository.addTrip(trip)
            loadTrip()
        }
    }

    fun deleteTrip(tripId: Int) {
        viewModelScope.launch {
            repository.deleteTrip(tripId)
            loadTrip()
        }
    }

    fun editTrip(trip: Trip) {
        viewModelScope.launch {
            repository.editTrip(trip)
            loadTrip()
        }
    }

}