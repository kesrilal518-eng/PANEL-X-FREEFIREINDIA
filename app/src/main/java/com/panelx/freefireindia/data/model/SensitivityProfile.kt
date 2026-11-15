package com.panelx.freefireindia.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensitivity_profiles")
data class SensitivityProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val playStyle: String = "Balanced",
    val general: Int = 100,
    val redDot: Int = 100,
    val scope2x: Int = 100,
    val scope4x: Int = 100,
    val sniper: Int = 100,
    val freeLook: Int = 100,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

enum class PlayStyle(val label: String) {
    LOW("Low"),
    BALANCED("Balanced"),
    HIGH("High"),
    CUSTOM("Custom")
}

data class SensitivityRecommendation(
    val general: Int,
    val redDot: Int,
    val scope2x: Int,
    val scope4x: Int,
    val sniper: Int,
    val freeLook: Int,
    val notes: String = "These are recommendations only. Results may vary depending on the player and device."
)
