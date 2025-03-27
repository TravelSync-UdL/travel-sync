package com.app.travelsync.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "itinerary",
    foreignKeys = [ForeignKey(
        entity = TripEntity::class,
        parentColumns = ["id"],
        childColumns = ["trip_Id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ItineraryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val trip_Id: Int,
    val title: String,
    val date: String,
    val time: String,
    val location: String,
    val notes: String
)