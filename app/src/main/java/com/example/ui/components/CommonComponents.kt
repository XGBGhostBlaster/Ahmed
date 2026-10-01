package com.example.ui.components

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.DogsTab
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

@Composable
fun DogsTopBar(
    telegramUsername: String,
    dogsBalance: Long,
    tonBalance: Double,
    streakDays: Int,
    onAgeClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DogsDarkBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(DogsDarkSurface)
                .clickable { onAgeClick() }
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Pets,
                    contentDescription = "Dogs Mascot",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "@$telegramUsername",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "🔥 $streakDays Day Streak",
                    fontSize = 10.sp,
                    color = DogsGoldLight
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // TON chip
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(DogsDarkSurfaceElevated)
                    .border(1.dp, TonBlue.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(TonBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text("💎", fontSize = 9.sp)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${String.format("%.2f", tonBalance)} TON",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        }
    }
}

@Composable
fun DogsBottomNav(
    currentTab: DogsTab,
    onTabSelected: (DogsTab) -> Unit,
    taskCount: Int = 0
) {
    NavigationBar(
        containerColor = DogsDarkSurface,
        tonalElevation = 8.dp,
        modifier = Modifier.border(
            width = 1.dp,
            color = DogsDarkSurfaceBorder,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        )
    ) {
        val items = listOf(
            Triple(DogsTab.HOME, "Dogs", Icons.Default.Pets),
            Triple(DogsTab.TASKS, "Tasks", Icons.Default.ListAlt),
            Triple(DogsTab.WALLET, "Wallet & PKR", Icons.Default.AccountBalanceWallet),
            Triple(DogsTab.FRENS, "Frens", Icons.Default.Group),
            Triple(DogsTab.LEADERBOARD, "Rank", Icons.Default.EmojiEvents)
        )

        items.forEach { (tab, label, icon) ->
            val isSelected = currentTab == tab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                icon = {
                    if (tab == DogsTab.TASKS && taskCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge(
                                    containerColor = DogsGold,
                                    contentColor = Color.Black
                                ) {
                                    Text(taskCount.toString(), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        ) {
                            Icon(icon, contentDescription = label)
                        }
                    } else {
                        Icon(icon, contentDescription = label)
                    }
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = DogsGold,
                    indicatorColor = DogsGold,
                    unselectedIconColor = TextMuted,
                    unselectedTextColor = TextMuted
                )
            )
        }
    }
}

@Composable
fun MethodBadge(
    method: String,
    modifier: Modifier = Modifier
) {
    val (bgColor, textColor, label) = when {
        method.contains("JazzCash", ignoreCase = true) -> Triple(JazzCashOrange, Color.White, "JazzCash")
        method.contains("EasyPaisa", ignoreCase = true) -> Triple(EasyPaisaGreen, Color.White, "EasyPaisa")
        method.contains("TON", ignoreCase = true) -> Triple(TonBlue, Color.White, "TON Coin")
        else -> Triple(DogsDarkSurfaceElevated, TextPrimary, method)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
