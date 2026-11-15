package com.panelx.freefireindia.data.model

enum class FeatureStatus(val label: String) {
    DEMO_MODE("DEMO MODE"),
    VISUAL_ONLY("VISUAL ONLY"),
    NOT_FUNCTIONAL("NOT FUNCTIONAL"),
    NOT_CONNECTED("NOT CONNECTED")
}

data class PanelFeature(
    val id: String,
    val name: String,
    val iconEmoji: String,
    val status: FeatureStatus,
    val description: String,
    val isToggled: Boolean = false
)

object PanelFeatures {
    val defaultFeatures = listOf(
        PanelFeature(
            id = "aimbot",
            name = "AIMBOT",
            iconEmoji = "🎯",
            status = FeatureStatus.DEMO_MODE,
            description = "VISUAL DEMO ONLY — No game interaction."
        ),
        PanelFeature(
            id = "auto_headshot",
            name = "AUTO HEADSHOT",
            iconEmoji = "🎯",
            status = FeatureStatus.DEMO_MODE,
            description = "VISUAL DEMO ONLY."
        ),
        PanelFeature(
            id = "touch_automation",
            name = "TOUCH AUTOMATION",
            iconEmoji = "👆",
            status = FeatureStatus.DEMO_MODE,
            description = "Simulated touch-path animation inside the app only. No external automation."
        ),
        PanelFeature(
            id = "macros",
            name = "MACROS",
            iconEmoji = "⚡",
            status = FeatureStatus.DEMO_MODE,
            description = "Sample visual sequence inside the application only."
        ),
        PanelFeature(
            id = "injector",
            name = "INJECTOR",
            iconEmoji = "🛡️",
            status = FeatureStatus.NOT_FUNCTIONAL,
            description = "Demo UI only. No injection or game modification is performed."
        ),
        PanelFeature(
            id = "game_access",
            name = "GAME ACCESS",
            iconEmoji = "🎮",
            status = FeatureStatus.NOT_CONNECTED,
            description = "Does not access Free Fire or any other game."
        )
    )
}
