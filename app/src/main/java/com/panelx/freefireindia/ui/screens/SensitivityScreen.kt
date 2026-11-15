package com.panelx.freefireindia.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panelx.freefireindia.data.model.PlayStyle
import com.panelx.freefireindia.ui.components.AccentButton
import com.panelx.freefireindia.ui.components.GamingCard
import com.panelx.freefireindia.ui.components.SecondaryButton
import com.panelx.freefireindia.ui.components.SectionTitle
import com.panelx.freefireindia.ui.components.SensitivitySlider
import com.panelx.freefireindia.ui.theme.AccentRed
import com.panelx.freefireindia.ui.theme.DarkBackground
import com.panelx.freefireindia.ui.theme.DarkCardElevated
import com.panelx.freefireindia.ui.theme.TextMuted
import com.panelx.freefireindia.ui.theme.TextPrimary
import com.panelx.freefireindia.ui.theme.TextSecondary
import com.panelx.freefireindia.viewmodel.MainViewModel

@Composable
fun SensitivityScreen(viewModel: MainViewModel) {
    val playStyle by viewModel.playStyle.collectAsState()
    val general by viewModel.customGeneral.collectAsState()
    val redDot by viewModel.customRedDot.collectAsState()
    val scope2x by viewModel.custom2x.collectAsState()
    val scope4x by viewModel.custom4x.collectAsState()
    val sniper by viewModel.customSniper.collectAsState()
    val freeLook by viewModel.customFreeLook.collectAsState()
    val deviceInfo by viewModel.deviceInfo.collectAsState()
    val context = LocalContext.current

    var showSaveDialog by remember { mutableStateOf(false) }
    var profileName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        SectionTitle("SENSITIVITY FINDER")
        Text(text = "Device: ${deviceInfo.displayName}", color = TextSecondary, fontSize = 13.sp)

        Spacer(modifier = Modifier.height(12.dp))

        GamingCard {
            Text("Play Style", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                PlayStyle.entries.forEach { style ->
                    FilterChip(
                        selected = playStyle == style,
                        onClick = { viewModel.setPlayStyle(style) },
                        label = { Text(style.label, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = AccentRed,
                            selectedLabelColor = TextPrimary,
                            containerColor = DarkCardElevated,
                            labelColor = TextSecondary
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        GamingCard {
            Text("Recommended Values", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))
            SensitivitySlider("General", general) { viewModel.updateCustomValue("general", it) }
            SensitivitySlider("Red Dot", redDot) { viewModel.updateCustomValue("redDot", it) }
            SensitivitySlider("2X Scope", scope2x) { viewModel.updateCustomValue("2x", it) }
            SensitivitySlider("4X Scope", scope4x) { viewModel.updateCustomValue("4x", it) }
            SensitivitySlider("Sniper Scope", sniper) { viewModel.updateCustomValue("sniper", it) }
            SensitivitySlider("Free Look", freeLook) { viewModel.updateCustomValue("freeLook", it) }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "These are recommendations only. Results may vary depending on the player and device.",
            color = TextMuted,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AccentButton(
                text = "Generate",
                onClick = { viewModel.generateRecommendation() },
                modifier = Modifier.weight(1f)
            )
            SecondaryButton(
                text = "Reset",
                onClick = { viewModel.resetSensitivity() },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AccentButton(
                text = "Save Profile",
                onClick = { showSaveDialog = true },
                modifier = Modifier.weight(1f)
            )
            SecondaryButton(
                text = "Share",
                onClick = {
                    val text = viewModel.getShareText()
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, text)
                    }
                    context.startActivity(Intent.createChooser(intent, "Share Settings"))
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        SectionTitle("DPI & FIRE BUTTON GUIDE")
        DpiGuideSection(viewModel)

        Spacer(modifier = Modifier.height(80.dp))
    }

    if (showSaveDialog) {
        AlertDialog(
            onDismissRequest = { showSaveDialog = false },
            title = { Text("Save Profile") },
            text = {
                OutlinedTextField(
                    value = profileName,
                    onValueChange = { profileName = it },
                    label = { Text("Profile name") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AccentRed,
                        focusedLabelColor = AccentRed
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.saveProfile(profileName)
                    profileName = ""
                    showSaveDialog = false
                }) {
                    Text("Save", color = AccentRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { showSaveDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun DpiGuideSection(viewModel: MainViewModel) {
    val dpiPref by viewModel.dpiPreference.collectAsState()

    GamingCard {
        Text("Preference", color = TextPrimary, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Low", "Balanced", "High").forEach { pref ->
                FilterChip(
                    selected = dpiPref == pref,
                    onClick = { viewModel.setDpiPreference(pref) },
                    label = { Text(pref, fontSize = 12.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AccentRed,
                        selectedLabelColor = TextPrimary,
                        containerColor = DarkCardElevated,
                        labelColor = TextSecondary
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = viewModel.getDpiSuggestion(),
            color = TextSecondary,
            fontSize = 13.sp,
            lineHeight = 20.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "This guide does not change system DPI. Adjust manually in developer options if desired.",
            color = TextMuted,
            fontSize = 11.sp
        )
    }
}
