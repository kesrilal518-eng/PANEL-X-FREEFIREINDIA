package com.panelx.freefireindia.data.repository

import com.panelx.freefireindia.data.local.ProfileDao
import com.panelx.freefireindia.data.model.SensitivityProfile
import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val dao: ProfileDao) {

    fun getAllProfiles(): Flow<List<SensitivityProfile>> = dao.getAllProfiles()

    suspend fun getProfileById(id: Long): SensitivityProfile? = dao.getProfileById(id)

    suspend fun saveProfile(profile: SensitivityProfile): Long {
        return if (profile.id == 0L) {
            dao.insert(profile)
        } else {
            dao.update(profile.copy(updatedAt = System.currentTimeMillis()))
            profile.id
        }
    }

    suspend fun deleteProfile(profile: SensitivityProfile) {
        dao.delete(profile)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}
