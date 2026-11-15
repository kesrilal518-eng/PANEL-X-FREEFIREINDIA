package com.panelx.freefireindia.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.panelx.freefireindia.ui.components.DemoMessageBanner
import com.panelx.freefireindia.ui.components.FeatureToggleCard
import com.panelx.freefireindia.ui.components.SectionTitle
import com.panelx.freefireindia.ui.theme.AccentOrange
import com.panelx.freefireindia.ui.theme.DarkBackground
import com.panelx.freefireindia.ui.theme.TextMuted
import com.panelx.freefireindia.viewmodel.MainViewModel

@Composable
fun PanelScreen(viewModel: MainViewModel) {
    val features by viewModel.panelFeatures.collectAsState()
    val demoMessage by viewModel.demoMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        SectionTitle("PANEL X FEATURES")
        Text(
            text = "All features below are visual simulations only.",
            color = AccentOrange,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "No game interaction, injection, memory access, or automation is performed.",
            color = TextMuted,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        AnimatedVisibility(
            visible = demoMessage != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            demoMessage?.let { msg ->
                DemoMessageBanner(
                    message = msg,
                    onDismiss = { viewModel.clearDemoMessage() }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            features.forEach { feature ->
                FeatureToggleCard(
                    emoji = feature.iconEmoji,
                    name = feature.name,
                    status = feature.status,
                    isToggled = feature.isToggled,
                    onToggle = { viewModel.toggleFeature(feature.id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "DEMO MODE — These toggles only change the UI state inside this application. They do not interact with Free Fire or any other game.",
            color = TextMuted,
            fontSize = 11.sp,
            lineHeight = 16.sp
        )
        Spacer(modifier = Modifier.height(80.dp))
    }
}
