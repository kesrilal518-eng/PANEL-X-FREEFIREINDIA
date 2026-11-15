package com.panelx.freefireindia.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.view.WindowManager
import com.panelx.freefireindia.data.model.DeviceInfo

object DeviceDetector {

    fun detect(context: Context): DeviceInfo {
        val metrics = context.resources.displayMetrics
        val refreshRate = getRefreshRate(context)
        val ramInfo = getRamInfo(context)

        return DeviceInfo(
            manufacturer = Build.MANUFACTURER?.replaceFirstChar { it.uppercase() } ?: "Unknown",
            brand = Build.BRAND?.replaceFirstChar { it.uppercase() } ?: "Unknown",
            model = Build.MODEL ?: "Unknown",
            androidVersion = "Android ${Build.VERSION.RELEASE}",
            sdkVersion = Build.VERSION.SDK_INT,
            screenWidthPx = metrics.widthPixels,
            screenHeightPx = metrics.heightPixels,
            densityDpi = metrics.densityDpi,
            density = metrics.density,
            refreshRate = refreshRate,
            totalRamMb = ramInfo.first,
            availableRamMb = ramInfo.second
        )
    }

    private fun getRefreshRate(context: Context): Float? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context.display?.refreshRate
            } else {
                @Suppress("DEPRECATION")
                val wm = context.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
                wm?.defaultDisplay?.refreshRate
            }
        } catch (e: Exception) {
            null
        }
    }

    private fun getRamInfo(context: Context): Pair<Long?, Long?> {
        return try {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
            val memInfo = ActivityManager.MemoryInfo()
            am?.getMemoryInfo(memInfo)
            val totalMb = memInfo.totalMem / (1024 * 1024)
            val availMb = memInfo.availMem / (1024 * 1024)
            Pair(totalMb, availMb)
        } catch (e: Exception) {
            Pair(null, null)
        }
    }

    fun getDeviceCategory(info: DeviceInfo): String {
        val dpi = info.densityDpi
        val width = info.screenWidthPx
        return when {
            width >= 1440 || dpi >= 480 -> "High-end"
            width >= 1080 || dpi >= 320 -> "Mid-range"
            else -> "Entry-level"
        }
    }
}
