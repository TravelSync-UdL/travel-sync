package com.app.travelsync.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey var login: String,
    val username: String,
    val country: String = "undefined",
    val birthdate: String = "undefined",
    val address: String = "undefined",
    val phone: String = "undefined",
    val acceptReceiveEmails: Boolean = true
)