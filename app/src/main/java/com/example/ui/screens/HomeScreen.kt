package com.example.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.UserProfile
import com.example.data.model.WalletConnections
import com.example.ui.theme.DogsDarkBackground
import com.example.ui.theme.DogsDarkSurface
import com.example.ui.theme.DogsDarkSurfaceBorder
import com.example.ui.theme.DogsDarkSurfaceElevated
import com.example.ui.theme.DogsGold
import com.example.ui.theme.DogsGoldLight
import com.example.ui.theme.EasyPaisaGreen
import com.example.ui.theme.JazzCashOrange
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TonBlue
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    userProfile: UserProfile,
    walletConnections: WalletConnections,
    dogsPerTon: Double,
    tonToPkr: Double,
    onTapBark: () -> Unit,
    onClaimStreak: () -> Unit,
    onOpenAgeScan: () -> Unit,
    onOpenCashout: (String) -> Unit,
    onNavigateToWallet: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val scale = remember { Animatable(1.0f) }
    var lastBarkReaction by remember { mutableStateOf("Tap Spotty to Bark & Earn!") }

    val tonEquiv = userProfile.dogsBalance / dogsPerTon
    val pkrEquiv = tonEquiv * tonToPkr
    val usdEquiv = tonEquiv * 5.50

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DogsDarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // Hero Visual Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        ) {
            Image(
                painter = painterResource(id = R.drawable.dogs_hero_banner_1790849132475),
                contentDescription = "Dogs Mascot and TON Crypto",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Gradient scrim for contrast
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                DogsDarkBackground.copy(alpha = 0.85f),
                                DogsDarkBackground
                            )
                        )
                    )
            )

            // Overlaid badge
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("\$DOGS COMMUNITY", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(TonBlue)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("TON BLOCKCHAIN", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Main Balance Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "TOTAL SPOTTY BALANCE",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextSecondary,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🦴", fontSize = 28.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = String.format("%,d", userProfile.dogsBalance),
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "\$DOGS",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DogsGold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Fiat & TON equivalents
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(DogsDarkSurfaceElevated)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "≈ ${String.format("%.2f", tonEquiv)} TON",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TonBlue
                )
                Text(" • ", color = TextMuted)
                Text(
                    text = "Rs ${String.format("%,.0f", pkrEquiv)} PKR",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = EasyPaisaGreen
                )
                Text(" • ", color = TextMuted)
                Text(
                    text = "$${String.format("%.2f", usdEquiv)} USD",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Direct Payout Banner (Highlights JazzCash / EasyPaisa / TON)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onNavigateToWallet() },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DogsDarkSurface),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(JazzCashOrange, EasyPaisaGreen, TonBlue)))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "🇵🇰 Direct Payouts & Gas Fees",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Instant JazzCash, EasyPaisa PKR & TON Coin withdrawals",
                        fontSize = 11.sp,
                        color = TextSecondary
                    )
                }
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Open Wallet",
                    tint = DogsGold
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Telegram Account Age Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DogsDarkSurface),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DogsDarkSurfaceBorder))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Telegram Account Verification",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(DogsGold)
                            .clickable { onOpenAgeScan() }
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text("Re-scan", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Account Age", fontSize = 11.sp, color = TextSecondary)
                        Text(
                            text = "${userProfile.accountAgeYears} Years (Since ${userProfile.accountCreatedYear})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Status Tier", fontSize = 11.sp, color = TextSecondary)
                        Text(
                            text = userProfile.ogDogTier,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = DogsGoldLight
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(DogsDarkSurfaceElevated)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = DogsGold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Telegram Premium Pass", fontSize = 11.sp, color = TextPrimary)
                    }
                    Text(
                        text = if (userProfile.isTelegramPremium) "Active (+3,500 DOGS)" else "Inactive",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (userProfile.isTelegramPremium) EasyPaisaGreen else TextMuted
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Interactive Tap to Bark & Earn
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = DogsDarkSurface),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DogsDarkSurfaceBorder))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "TAP SPOTTY TO BARK & EARN",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = DogsGold
                )
                Text(
                    text = lastBarkReaction,
                    fontSize = 11.sp,
                    color = TextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // The Spotty Tap Pad
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .scale(scale.value)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color.White, Color(0xFFDDDDDD), Color(0xFF999999))
                            )
                        )
                        .border(4.dp, DogsGold, CircleShape)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            coroutineScope.launch {
                                scale.animateTo(0.92f, tween(50, easing = FastOutSlowInEasing))
                                scale.animateTo(1.0f, tween(100, easing = FastOutSlowInEasing))
                            }
                            val reactions = listOf(
                                "🐾 Woof! +5 DOGS",
                                "🦴 Spotty fetched a bone! +5 DOGS",
                                "🐶 Good dog! +5 DOGS",
                                "⚡ Bark! Telegram OG Power +5 DOGS",
                                "💎 Spotty smells TON crypto! +5 DOGS"
                            )
                            lastBarkReaction = reactions.random()
                            onTapBark()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Pets,
                            contentDescription = "Tap Spotty",
                            tint = Color.Black,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = "BARK!",
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Total Barks: ${userProfile.totalTaps} • Tap earns +5 \$DOGS per bark",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Daily Streak Bone Tracker
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DogsDarkSurface),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DogsDarkSurfaceBorder))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🔥", fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Daily Bone Streak (Day ${userProfile.streakDays}/7)",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                    Button(
                        onClick = onClaimStreak,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = DogsGold, contentColor = Color.Black),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Text("Claim Day ${userProfile.streakDays + 1}", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 7 days row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    (1..7).forEach { day ->
                        val isDone = day <= userProfile.streakDays
                        val reward = day * 250
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 2.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isDone) DogsGold.copy(alpha = 0.2f) else DogsDarkSurfaceElevated)
                                .border(1.dp, if (isDone) DogsGold else DogsDarkSurfaceBorder, RoundedCornerShape(8.dp))
                                .padding(vertical = 6.dp)
                        ) {
                            Text("D$day", fontSize = 10.sp, color = if (isDone) DogsGold else TextMuted, fontWeight = FontWeight.Bold)
                            Text("🦴", fontSize = 12.sp)
                            Text("+$reward", fontSize = 8.sp, color = TextPrimary)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Action Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { onOpenCashout("JazzCash") },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = JazzCashOrange, contentColor = Color.White)
            ) {
                Text("Cashout JazzCash", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = { onOpenCashout("EasyPaisa") },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = EasyPaisaGreen, contentColor = Color.White)
            ) {
                Text("Cashout EasyPaisa", fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
