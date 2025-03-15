package com.app.travelsync.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.travelsync.domain.model.Trip
import com.app.travelsync.domain.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TripViewModel @Inject constructor(
    private val repository: TripRepository
): ViewModel(){

    private val _trips = mutableStateListOf<Trip>()
    val trips: List<Trip> get() = _trips

    init {
        loadTrip()
    }

    private fun loadTrip() {
        _trips.clear()
        _trips.addAll(repository.getTrip())
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