package com.app.travelsync.domain.model

data class Trip (
    val tripId: Int = 0,
    val title: String,
    val destination: String,
    val startDate: String,
    val endDate: String,
    val subTasks: List<Itinerary> = emptyList()
)