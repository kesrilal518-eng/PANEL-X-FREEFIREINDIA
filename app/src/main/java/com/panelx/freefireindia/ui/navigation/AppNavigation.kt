package com.panelx.freefireindia.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.panelx.freefireindia.ui.screens.DisclaimerScreen
import com.panelx.freefireindia.ui.screens.HomeScreen
import com.panelx.freefireindia.ui.screens.PanelScreen
import com.panelx.freefireindia.ui.screens.ProfilesScreen
import com.panelx.freefireindia.ui.screens.SensitivityScreen
import com.panelx.freefireindia.ui.screens.SettingsScreen
import com.panelx.freefireindia.ui.screens.SplashScreen
import com.panelx.freefireindia.ui.screens.TrainingScreen
import com.panelx.freefireindia.ui.theme.AccentRed
import com.panelx.freefireindia.ui.theme.DarkBackground
import com.panelx.freefireindia.ui.theme.DarkCard
import com.panelx.freefireindia.ui.theme.TextMuted
import com.panelx.freefireindia.ui.theme.TextPrimary
import com.panelx.freefireindia.viewmodel.MainViewModel

private data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)

private val bottomNavItems = listOf(
    BottomNavItem(Screen.Home, "Home", Icons.Default.Home),
    BottomNavItem(Screen.Sensitivity, "Sensitivity", Icons.Default.Tune),
    BottomNavItem(Screen.Training, "Training", Icons.Default.School),
    BottomNavItem(Screen.Panel, "Panel", Icons.Default.SportsEsports),
    BottomNavItem(Screen.Settings, "Settings", Icons.Default.Settings)
)

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val showBottomBar = currentDestination?.route in bottomNavItems.map { it.screen.route }

    Scaffold(
        containerColor = DarkBackground,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = DarkCard,
                    contentColor = TextPrimary
                ) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any {
                            it.route == item.screen.route
                        } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(item.label, fontSize = 10.sp)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = AccentRed,
                                selectedTextColor = AccentRed,
                                unselectedIconColor = TextMuted,
                                unselectedTextColor = TextMuted,
                                indicatorColor = AccentRed.copy(alpha = 0.15f)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(
                    onFinished = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(navController, viewModel)
            }
            composable(Screen.Sensitivity.route) {
                SensitivityScreen(viewModel)
            }
            composable(Screen.Training.route) {
                TrainingScreen()
            }
            composable(Screen.Panel.route) {
                PanelScreen(viewModel)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(navController, viewModel)
            }
            composable(Screen.Profiles.route) {
                ProfilesScreen(viewModel)
            }
            composable(Screen.Disclaimer.route) {
                DisclaimerScreen()
            }
        }
    }
}
