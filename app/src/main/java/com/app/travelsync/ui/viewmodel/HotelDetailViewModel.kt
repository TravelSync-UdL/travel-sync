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
import com.app.travelsync.domain.model.ReserveRequest
import com.app.travelsync.domain.model.Room
import com.app.travelsync.domain.model.Trip
import com.app.travelsync.domain.repository.HotelRepository
import com.app.travelsync.domain.repository.ReservationRepository
import com.app.travelsync.domain.repository.TripRepository
import com.app.travelsync.utils.ErrorUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HotelDetailViewModel @Inject constructor(
    private val repo: HotelRepository,
    private val tripRepo: TripRepository,
    private val reservationRepository: ReservationRepository,
    sharedPrefsManager: SharedPrefsManager
) : ViewModel() {

    val guestEmail: String = sharedPrefsManager.userEmail ?: ""

    private val _uiState = MutableStateFlow(HotelDetailUiState())
    val uiState: StateFlow<HotelDetailUiState> = _uiState

    var showImageDialog by mutableStateOf(false)
    fun showImageDialog() { showImageDialog = true }
    fun hideImageDialog() { showImageDialog = false }

    fun selectRoom(room: Room) {
        _uiState.value = _uiState.value.copy(selectedRoom = room)
    }

    private lateinit var groupId: String
    private lateinit var start: String
    private lateinit var end: String

    /* -------- load hotel & free rooms -------- */
    fun load(hotelId: String, gid: String, s: String, e: String) {
        if (uiState.value.hotel != null) return   // already loaded
        groupId = gid; start = s; end = e
        viewModelScope.launch {
            val hotel = repo.getHotels(gid).first { it.id == hotelId }
            val freeRooms = repo.getAvailability(gid, s, e)
                .first { it.id == hotelId }.rooms
            _uiState.value = HotelDetailUiState(false, hotel, freeRooms)
        }
    }

    fun reserveRoom(room: Room) = viewModelScope.launch {

        Log.d("reserveRoom called", "room: $room")

        val req = ReserveRequest(
            hotelId = uiState.value.hotel!!.id,
            roomId  = room.id,
            startDate = start,
            endDate   = end,
            guestName = "Gerard",
            guestEmail = guestEmail
        )

        try {

            val trip = Trip(
                title = uiState.value.hotel?.address ?: "Reserva",
                destination = uiState.value.hotel?.address ?: "Desconegut",
                startDate = start,
                endDate = end,
                ownerLogin = guestEmail
            )

            val hotel = uiState.value.hotel
            val room = uiState.value.selectedRoom

            val tripId = tripRepo.addTrip(trip) // <-- ara retorna Int



            if (hotel != null && room != null) {
                Log.d("ReservationError", "Creant la reserva...")
                val reservation = ReservationEntity(
                    hotelId = hotel.id,
                    hotelName = hotel.name,
                    roomId = room.id,
                    roomType = room.roomType,
                    pricePerNight = room.price,
                    totalPrice = 0.0f,
                    tripId = tripId
                )
                Log.d("ReservationError", "Reserva creada: $reservation")
                reservationRepository.insertReservation(reservation)
            }else{
                Log.d("ReservationError", "NO FUNCIONA")
            }

            repo.reserve(groupId, req)

        } catch (e: HttpException) {
            val decodedError = ErrorUtils.extractErrorMessage(e)
            Log.e("BookViewModel", "HTTP error: ${decodedError}  $e")

        } catch (e: Exception) {
            Log.e("BookViewModel", "Error: ${e.localizedMessage}")
        }

    }
}

data class HotelDetailUiState(
    val loading: Boolean = true,
    val hotel: Hotel? = null,
    val rooms: List<Room>? = emptyList(),
    val selectedRoom: Room? = null,
    val showImageDialog: Boolean = false,
)