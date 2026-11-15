package com.panelx.freefireindia.data.model

data class DeviceInfo(
    val manufacturer: String = "Unknown",
    val brand: String = "Unknown",
    val model: String = "Unknown",
    val androidVersion: String = "Unknown",
    val sdkVersion: Int = 0,
    val screenWidthPx: Int = 0,
    val screenHeightPx: Int = 0,
    val densityDpi: Int = 0,
    val density: Float = 1f,
    val refreshRate: Float? = null,
    val totalRamMb: Long? = null,
    val availableRamMb: Long? = null
) {
    val displayName: String
        get() = when {
            model.isNotBlank() && model != "Unknown" -> model
            brand.isNotBlank() && brand != "Unknown" -> brand
            else -> "Android Device"
        }

    val resolutionText: String
        get() = if (screenWidthPx > 0 && screenHeightPx > 0) {
            "${screenWidthPx} × ${screenHeightPx}"
        } else "N/A"

    val densityText: String
        get() = if (densityDpi > 0) "${densityDpi} dpi" else "N/A"

    val refreshRateText: String
        get() = refreshRate?.let { "${it.toInt()} Hz" } ?: "N/A"

    val ramText: String
        get() = when {
            totalRamMb != null && availableRamMb != null ->
                "${availableRamMb} / ${totalRamMb} MB"
            totalRamMb != null -> "${totalRamMb} MB"
            else -> "N/A"
        }
}
