package com.panelx.freefireindia.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.panelx.freefireindia.data.model.SensitivityProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Query("SELECT * FROM sensitivity_profiles ORDER BY updatedAt DESC")
    fun getAllProfiles(): Flow<List<SensitivityProfile>>

    @Query("SELECT * FROM sensitivity_profiles WHERE id = :id")
    suspend fun getProfileById(id: Long): SensitivityProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: SensitivityProfile): Long

    @Update
    suspend fun update(profile: SensitivityProfile)

    @Delete
    suspend fun delete(profile: SensitivityProfile)

    @Query("DELETE FROM sensitivity_profiles")
    suspend fun deleteAll()
}
