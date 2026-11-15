package com.panelx.freefireindia.utils

import com.panelx.freefireindia.data.model.DeviceInfo
import com.panelx.freefireindia.data.model.PlayStyle
import com.panelx.freefireindia.data.model.SensitivityRecommendation

object SensitivityEngine {

    fun recommend(
        deviceInfo: DeviceInfo,
        playStyle: PlayStyle
    ): SensitivityRecommendation {
        val category = DeviceDetector.getDeviceCategory(deviceInfo)
        val base = when (category) {
            "High-end" -> 110
            "Mid-range" -> 100
            else -> 90
        }

        val refreshBonus = when {
            (deviceInfo.refreshRate ?: 60f) >= 120f -> 8
            (deviceInfo.refreshRate ?: 60f) >= 90f -> 4
            else -> 0
        }

        val densityAdj = when {
            deviceInfo.densityDpi >= 480 -> -5
            deviceInfo.densityDpi >= 400 -> -2
            else -> 0
        }

        val styleMultiplier = when (playStyle) {
            PlayStyle.LOW -> 0.75f
            PlayStyle.BALANCED -> 1.0f
            PlayStyle.HIGH -> 1.25f
            PlayStyle.CUSTOM -> 1.0f
        }

        fun scale(value: Int): Int {
            val scaled = ((base + refreshBonus + densityAdj) * styleMultiplier * (value / 100f)).toInt()
            return scaled.coerceIn(10, 200)
        }

        return SensitivityRecommendation(
            general = scale(100),
            redDot = scale(95),
            scope2x = scale(90),
            scope4x = scale(80),
            sniper = scale(60),
            freeLook = scale(85),
            notes = "These are recommendations only. Results may vary depending on the player and device."
        )
    }

    fun formatForShare(
        deviceName: String,
        general: Int,
        redDot: Int,
        scope2x: Int,
        scope4x: Int,
        sniper: Int,
        freeLook: Int
    ): String {
        return buildString {
            appendLine("PANEL X FREEFIREINDIA — Sensitivity Settings")
            appendLine("DEVICE: $deviceName")
            appendLine()
            appendLine("GENERAL: $general")
            appendLine("RED DOT: $redDot")
            appendLine("2X: $scope2x")
            appendLine("4X: $scope4x")
            appendLine("SNIPER: $sniper")
            appendLine("FREE LOOK: $freeLook")
            appendLine()
            appendLine("Recommendations only. Results may vary.")
        }
    }
}
