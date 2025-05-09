package com.app.travelsync.data.local.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trip")
data class TripEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val ownerLogin: String,
    val images: String
)