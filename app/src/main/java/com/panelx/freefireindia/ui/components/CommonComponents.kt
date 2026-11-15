package com.panelx.freefireindia.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.panelx.freefireindia.data.model.FeatureStatus
import com.panelx.freefireindia.ui.theme.AccentOrange
import com.panelx.freefireindia.ui.theme.AccentRed
import com.panelx.freefireindia.ui.theme.DarkCard
import com.panelx.freefireindia.ui.theme.DarkCardElevated
import com.panelx.freefireindia.ui.theme.StatusDemo
import com.panelx.freefireindia.ui.theme.StatusNotConnected
import com.panelx.freefireindia.ui.theme.StatusNotFunctional
import com.panelx.freefireindia.ui.theme.StatusVisualOnly
import com.panelx.freefireindia.ui.theme.TextMuted
import com.panelx.freefireindia.ui.theme.TextPrimary
import com.panelx.freefireindia.ui.theme.TextSecondary

@Composable
fun GamingCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        listOf(DarkCardElevated.copy(alpha = 0.6f), DarkCard)
                    )
                )
                .padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = TextPrimary,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun AccentButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentRed,
            contentColor = TextPrimary,
            disabledContainerColor = AccentRed.copy(alpha = 0.4f)
        )
    ) {
        Text(text = text, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkCardElevated,
            contentColor = TextPrimary
        )
    ) {
        Text(text = text, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun StatusBadge(status: FeatureStatus) {
    val color = when (status) {
        FeatureStatus.DEMO_MODE -> StatusDemo
        FeatureStatus.VISUAL_ONLY -> StatusVisualOnly
        FeatureStatus.NOT_FUNCTIONAL -> StatusNotFunctional
        FeatureStatus.NOT_CONNECTED -> StatusNotConnected
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color.copy(alpha = 0.2f))
            .border(1.dp, color, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = status.label,
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun FeatureToggleCard(
    emoji: String,
    name: String,
    status: FeatureStatus,
    isToggled: Boolean,
    onToggle: () -> Unit
) {
    val scale by animateFloatAsState(
        targetValue = if (isToggled) 1.02f else 1f,
        animationSpec = tween(200),
        label = "scale"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isToggled) AccentRed else Color.Transparent,
        animationSpec = tween(200),
        label = "border"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable { onToggle() }
            .border(1.5.dp, borderColor, RoundedCornerShape(14.dp)),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCard)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = emoji, fontSize = 28.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = name,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    StatusBadge(status = status)
                }
            }
            Box(
                modifier = Modifier
                    .size(48.dp, 28.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isToggled) AccentRed else DarkCardElevated)
                    .padding(3.dp),
                contentAlignment = if (isToggled) Alignment.CenterEnd else Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(TextPrimary)
                )
            }
        }
    }
}

@Composable
fun SensitivitySlider(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, color = TextSecondary, fontSize = 14.sp)
            Text(
                text = value.toString(),
                color = AccentRed,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 10f..200f,
            colors = SliderDefaults.colors(
                thumbColor = AccentRed,
                activeTrackColor = AccentRed,
                inactiveTrackColor = DarkCardElevated
            )
        )
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextMuted, fontSize = 13.sp)
        Text(text = value, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun DemoMessageBanner(message: String, onDismiss: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDismiss() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AccentOrange.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "DEMO MODE",
                color = AccentOrange,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message,
                color = TextPrimary,
                fontSize = 14.sp,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Tap to dismiss",
                color = TextMuted,
                fontSize = 11.sp
            )
        }
    }
}
