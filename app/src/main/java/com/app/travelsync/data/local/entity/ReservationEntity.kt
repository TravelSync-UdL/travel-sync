package com.app.travelsync.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservation")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val reservationId: String,
    val hotelName: String,
    val roomType: String,
    val price: Int,
    val startDate: String,
    val endDate: String,
    val userEmail: String,
    val tripId: Int
)

