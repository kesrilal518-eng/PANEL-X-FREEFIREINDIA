package com.panelx.freefireindia.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panelx.freefireindia.ui.components.GamingCard
import com.panelx.freefireindia.ui.components.SectionTitle
import com.panelx.freefireindia.ui.theme.AccentOrange
import com.panelx.freefireindia.ui.theme.AccentRed
import com.panelx.freefireindia.ui.theme.DarkBackground
import com.panelx.freefireindia.ui.theme.TextMuted
import com.panelx.freefireindia.ui.theme.TextPrimary
import com.panelx.freefireindia.ui.theme.TextSecondary

@Composable
fun DisclaimerScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        SectionTitle("DISCLAIMER")

        GamingCard {
            Text(
                text = "PANEL X FREEFIREINDIA",
                color = AccentRed,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "This is an unofficial sensitivity and gaming training guide.",
                color = TextPrimary,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "This application does not modify, inject into, automate, or control any game.",
                color = TextSecondary,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "The gaming panel features marked DEMO MODE are visual simulations only.",
                color = AccentOrange,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "• No aimbot functionality\n• No auto headshot\n• No touch automation outside the app\n• No macros executed in other apps\n• No injector or game memory access\n• No connection to Free Fire or any game",
                color = TextSecondary,
                fontSize = 13.sp,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "This app is not affiliated with Garena or Free Fire.",
                color = TextMuted,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(80.dp))
    }
}
