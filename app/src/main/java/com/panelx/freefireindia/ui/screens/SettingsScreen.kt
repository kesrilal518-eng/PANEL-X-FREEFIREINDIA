package com.panelx.freefireindia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.panelx.freefireindia.ui.components.GamingCard
import com.panelx.freefireindia.ui.components.SectionTitle
import com.panelx.freefireindia.ui.navigation.Screen
import com.panelx.freefireindia.ui.theme.AccentRed
import com.panelx.freefireindia.ui.theme.DarkBackground
import com.panelx.freefireindia.ui.theme.TextMuted
import com.panelx.freefireindia.ui.theme.TextPrimary
import com.panelx.freefireindia.ui.theme.TextSecondary
import com.panelx.freefireindia.viewmodel.MainViewModel

@Composable
fun SettingsScreen(navController: NavController, viewModel: MainViewModel) {
    val darkMode by viewModel.darkMode.collectAsState()
    val language by viewModel.language.collectAsState()
    var showResetDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        SectionTitle("SETTINGS")

        GamingCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Dark Mode", color = TextPrimary, fontWeight = FontWeight.Medium)
                    Text("Gaming dark theme", color = TextMuted, fontSize = 12.sp)
                }
                Switch(
                    checked = darkMode,
                    onCheckedChange = { viewModel.setDarkMode(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = TextPrimary,
                        checkedTrackColor = AccentRed
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        GamingCard {
            Text("Language", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            LanguageOption("English", "en", language) { viewModel.setLanguage("en") }
            LanguageOption("हिन्दी (Hindi)", "hi", language) { viewModel.setLanguage("hi") }
        }

        Spacer(modifier = Modifier.height(12.dp))

        GamingCard {
            Text(
                text = "Saved Profiles",
                color = TextPrimary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(Screen.Profiles.route) }
                    .padding(vertical = 4.dp)
            )
            Text(
                text = "View, edit or delete saved sensitivity profiles",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        GamingCard {
            Text(
                text = "Reset Saved Settings",
                color = AccentRed,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showResetDialog = true }
                    .padding(vertical = 4.dp)
            )
            Text(
                text = "Clear all profiles and preferences",
                color = TextMuted,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        GamingCard {
            Text(
                text = "Disclaimer",
                color = TextPrimary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate(Screen.Disclaimer.route) }
                    .padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        GamingCard {
            Text("About", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            Text("PANEL X FREEFIREINDIA", color = TextSecondary, fontSize = 13.sp)
            Text("Version 1.0.0", color = TextMuted, fontSize = 12.sp)
            Text(
                text = "Unofficial sensitivity & training guide.\nNot affiliated with Garena or Free Fire.",
                color = TextMuted,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 6.dp)
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset All?") },
            text = { Text("This will delete all saved profiles and reset preferences.") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.resetAllSettings()
                    showResetDialog = false
                }) {
                    Text("Reset", color = AccentRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun LanguageOption(
    label: String,
    code: String,
    current: String,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = if (current == code) AccentRed else TextPrimary)
        if (current == code) {
            Text("✓", color = AccentRed, fontWeight = FontWeight.Bold)
        }
    }
}
