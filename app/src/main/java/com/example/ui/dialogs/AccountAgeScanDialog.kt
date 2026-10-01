package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.UserProfile
import com.example.ui.theme.DogsDarkBackground
import com.example.ui.theme.DogsDarkSurface
import com.example.ui.theme.DogsDarkSurfaceBorder
import com.example.ui.theme.DogsDarkSurfaceElevated
import com.example.ui.theme.DogsGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun AccountAgeScanDialog(
    userProfile: UserProfile,
    isScanning: Boolean,
    onDismiss: () -> Unit,
    onScan: (handle: String, estimatedYears: Double, isPremium: Boolean) -> Unit
) {
    var handle by remember { mutableStateOf(userProfile.telegramUsername) }
    var estimatedYears by remember { mutableDoubleStateOf(userProfile.accountAgeYears) }
    var isPremium by remember { mutableStateOf(userProfile.isTelegramPremium) }

    val estimatedReward = ((estimatedYears * 2200L) + (if (isPremium) 3500L else 1000L)).toLong()

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            color = DogsDarkSurface
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Pets, contentDescription = null, tint = Color.Black, modifier = Modifier.size(22.dp))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Telegram Age Scanner", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("OG Drops based on Telegram presence", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Telegram Username / ID", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DogsGold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = handle,
                    onValueChange = { handle = it },
                    placeholder = { Text("@username") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DogsGold,
                        unfocusedBorderColor = DogsDarkSurfaceBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Telegram Account Age", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                    Text("${String.format("%.1f", estimatedYears)} Years", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = DogsGold)
                }

                Slider(
                    value = estimatedYears.toFloat(),
                    onValueChange = { estimatedYears = it.toDouble() },
                    valueRange = 0.5f..11.0f,
                    steps = 20,
                    colors = SliderDefaults.colors(
                        thumbColor = DogsGold,
                        activeTrackColor = DogsGold,
                        inactiveTrackColor = DogsDarkSurfaceBorder
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Telegram Premium Switch
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(DogsDarkSurfaceElevated)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = DogsGold, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Telegram Premium", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("+3,500 Bonus \$DOGS", fontSize = 10.sp, color = TextSecondary)
                        }
                    }
                    Switch(
                        checked = isPremium,
                        onCheckedChange = { isPremium = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = DogsGold,
                            checkedTrackColor = DogsGold.copy(alpha = 0.4f)
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Calculation Result
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = DogsDarkBackground),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DogsDarkSurfaceBorder))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("ESTIMATED SPOTTY REWARD", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("+$estimatedReward DOGS", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = DogsGold)
                        Text("Tier: ${if (estimatedYears >= 5) "Mythic Alpha" else if (estimatedYears >= 3) "Legendary OG" else "Veteran Hound"}", fontSize = 11.sp, color = TextSecondary)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        onScan(handle, estimatedYears, isPremium)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                    enabled = !isScanning
                ) {
                    if (isScanning) {
                        CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(22.dp))
                    } else {
                        Icon(Icons.Default.Verified, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Verify & Claim OG Drop", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
