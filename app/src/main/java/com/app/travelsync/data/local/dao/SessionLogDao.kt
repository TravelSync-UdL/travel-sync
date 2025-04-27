package com.app.travelsync.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.travelsync.data.local.entity.SessionLogEntity

@Dao
interface SessionLogDao {

    @Insert
    suspend fun insertSessionLog(log: SessionLogEntity)

    @Query("SELECT * FROM session_log")
    suspend fun getAllSessionLogs(): List<SessionLogEntity>
}