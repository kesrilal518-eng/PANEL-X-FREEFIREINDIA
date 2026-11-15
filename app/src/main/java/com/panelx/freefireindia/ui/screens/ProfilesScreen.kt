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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panelx.freefireindia.data.model.SensitivityProfile
import com.panelx.freefireindia.ui.components.GamingCard
import com.panelx.freefireindia.ui.components.SectionTitle
import com.panelx.freefireindia.ui.theme.AccentRed
import com.panelx.freefireindia.ui.theme.DarkBackground
import com.panelx.freefireindia.ui.theme.TextMuted
import com.panelx.freefireindia.ui.theme.TextPrimary
import com.panelx.freefireindia.ui.theme.TextSecondary
import com.panelx.freefireindia.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ProfilesScreen(viewModel: MainViewModel) {
    val profiles by viewModel.profiles.collectAsState()
    var profileToDelete by remember { mutableStateOf<SensitivityProfile?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(16.dp)
    ) {
        SectionTitle("SAVED PROFILES")

        if (profiles.isEmpty()) {
            GamingCard {
                Text(
                    text = "No profiles saved yet.\nGenerate sensitivity settings and tap Save Profile.",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(profiles, key = { it.id }) { profile ->
                    ProfileCard(
                        profile = profile,
                        onLoad = { viewModel.loadProfile(profile) },
                        onDelete = { profileToDelete = profile }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }

    profileToDelete?.let { profile ->
        AlertDialog(
            onDismissRequest = { profileToDelete = null },
            title = { Text("Delete Profile?") },
            text = { Text("Delete \"${profile.name}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteProfile(profile)
                    profileToDelete = null
                }) {
                    Text("Delete", color = AccentRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { profileToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun ProfileCard(
    profile: SensitivityProfile,
    onLoad: () -> Unit,
    onDelete: () -> Unit
) {
    val dateStr = remember(profile.updatedAt) {
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date(profile.updatedAt))
    }

    GamingCard {
        Text(profile.name, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text("Style: ${profile.playStyle}", color = TextSecondary, fontSize = 12.sp)
        Text("Updated: $dateStr", color = TextMuted, fontSize = 11.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "G:${profile.general}  RD:${profile.redDot}  2X:${profile.scope2x}  4X:${profile.scope4x}  S:${profile.sniper}  FL:${profile.freeLook}",
            color = TextSecondary,
            fontSize = 12.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Load",
                color = AccentRed,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onLoad() }
            )
            Text(
                text = "Delete",
                color = TextMuted,
                modifier = Modifier.clickable { onDelete() }
            )
        }
    }
}
