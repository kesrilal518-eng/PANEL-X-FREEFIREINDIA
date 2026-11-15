package com.panelx.freefireindia.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panelx.freefireindia.ui.components.AccentButton
import com.panelx.freefireindia.ui.components.GamingCard
import com.panelx.freefireindia.ui.components.SectionTitle
import com.panelx.freefireindia.ui.theme.AccentCyan
import com.panelx.freefireindia.ui.theme.AccentRed
import com.panelx.freefireindia.ui.theme.DarkBackground
import com.panelx.freefireindia.ui.theme.TextMuted
import com.panelx.freefireindia.ui.theme.TextPrimary
import com.panelx.freefireindia.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun TrainingScreen() {
    val isAnimating = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        SectionTitle("HEADSHOT DRAG TRAINING")
        Text(
            text = "Visual training guide only. No touch events are sent to other apps.",
            color = TextMuted,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        GamingCard {
            Text("Practice Animation", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Text(
                text = "Watch the finger indicator move upward — this simulates a short controlled drag for headshot practice.",
                color = TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 6.dp, bottom = 12.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0D0D14)),
                contentAlignment = Alignment.Center
            ) {
                DragPracticeCanvas(isPlaying = isAnimating.value)
            }

            Spacer(modifier = Modifier.height(12.dp))
            AccentButton(
                text = if (isAnimating.value) "Stop Animation" else "Start Practice Animation",
                onClick = { isAnimating.value = !isAnimating.value },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        GamingCard {
            Text("Short Upward Drag", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Text(
                text = "• Start near the center of the fire button area.\n• Perform a short, controlled upward swipe.\n• Keep the movement smooth and consistent.\n• Practice close-range first, then mid-range.",
                color = TextSecondary,
                fontSize = 13.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        GamingCard {
            Text("Close-Range Practice", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Text(
                text = "Focus on quick, small upward adjustments. The goal is consistency, not speed. Repeat the motion until it feels natural.",
                color = TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        GamingCard {
            Text("Mid-Range Practice", color = TextPrimary, fontWeight = FontWeight.SemiBold)
            Text(
                text = "Slightly longer drag with more control. Anticipate recoil and compensate with a smooth upward motion.",
                color = TextSecondary,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "This is an educational visual guide only. Practice inside this app. Do not use any external automation.",
            color = TextMuted,
            fontSize = 11.sp
        )
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun DragPracticeCanvas(isPlaying: Boolean) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            while (true) {
                progress.snapTo(0f)
                progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(1200, easing = LinearEasing)
                )
                delay(400)
            }
        } else {
            progress.snapTo(0f)
        }
    }

    Canvas(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        val w = size.width
        val h = size.height
        val startY = h * 0.75f
        val endY = h * 0.25f
        val centerX = w / 2

        drawLine(
            color = Color.White.copy(alpha = 0.15f),
            start = Offset(centerX, startY),
            end = Offset(centerX, endY),
            strokeWidth = 3f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 12f))
        )

        drawCircle(
            color = AccentRed.copy(alpha = 0.3f),
            radius = 28f,
            center = Offset(centerX, endY)
        )
        drawCircle(
            color = AccentRed,
            radius = 10f,
            center = Offset(centerX, endY)
        )

        val currentY = startY + (endY - startY) * progress.value
        drawCircle(
            color = AccentCyan.copy(alpha = 0.4f),
            radius = 36f,
            center = Offset(centerX, currentY)
        )
        drawCircle(
            color = AccentCyan,
            radius = 18f,
            center = Offset(centerX, currentY)
        )
        drawCircle(
            color = Color.White,
            radius = 6f,
            center = Offset(centerX, currentY - 4f)
        )
    }
}
