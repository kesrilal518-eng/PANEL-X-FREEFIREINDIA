package com.panelx.freefireindia.ui.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object Sensitivity : Screen("sensitivity")
    data object Training : Screen("training")
    data object Panel : Screen("panel")
    data object Settings : Screen("settings")
    data object Profiles : Screen("profiles")
    data object Disclaimer : Screen("disclaimer")
}
