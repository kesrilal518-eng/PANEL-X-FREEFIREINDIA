package com.panelx.freefireindia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.panelx.freefireindia.ui.components.AccentButton
import com.panelx.freefireindia.ui.components.GamingCard
import com.panelx.freefireindia.ui.components.InfoRow
import com.panelx.freefireindia.ui.components.SectionTitle
import com.panelx.freefireindia.ui.navigation.Screen
import com.panelx.freefireindia.ui.theme.AccentRed
import com.panelx.freefireindia.ui.theme.DarkBackground
import com.panelx.freefireindia.ui.theme.TextMuted
import com.panelx.freefireindia.ui.theme.TextPrimary
import com.panelx.freefireindia.ui.theme.TextSecondary
import com.panelx.freefireindia.viewmodel.MainViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    val deviceInfo by viewModel.deviceInfo.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(text = "Welcome", color = TextMuted, fontSize = 14.sp)
        Text(text = "PANEL X", color = TextPrimary, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "FREEFIREINDIA",
            color = AccentRed,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 2.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        SectionTitle("YOUR DEVICE")
        GamingCard {
            Text(
                text = deviceInfo.displayName,
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow("Manufacturer", deviceInfo.manufacturer)
            InfoRow("Brand", deviceInfo.brand)
            InfoRow("Model", deviceInfo.model)
            InfoRow("Android", deviceInfo.androidVersion)
            InfoRow("SDK", deviceInfo.sdkVersion.toString())
            InfoRow("Resolution", deviceInfo.resolutionText)
            InfoRow("Density", deviceInfo.densityText)
            InfoRow("Refresh Rate", deviceInfo.refreshRateText)
            InfoRow("RAM", deviceInfo.ramText)
        }

        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle("QUICK ACCESS")
        GamingCard {
            Text(text = "Sensitivity Finder", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Text(
                text = "Get recommended sensitivity values based on your device.",
                color = TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )
            AccentButton(
                text = "Open Sensitivity",
                onClick = { navController.navigate(Screen.Sensitivity.route) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        GamingCard {
            Text(text = "Headshot Training", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Text(
                text = "Visual drag training guide — practice inside the app only.",
                color = TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )
            AccentButton(
                text = "Open Training",
                onClick = { navController.navigate(Screen.Training.route) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        GamingCard {
            Text(text = "Gaming Panel Demo", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Text(
                text = "All features are visual simulations only. No game interaction.",
                color = TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )
            AccentButton(
                text = "Open Panel",
                onClick = { navController.navigate(Screen.Panel.route) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Unofficial sensitivity & training guide. Not affiliated with Garena.",
            color = TextMuted,
            fontSize = 11.sp
        )
        Spacer(modifier = Modifier.height(80.dp))
    }
}
