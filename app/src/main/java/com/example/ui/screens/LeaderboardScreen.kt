package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.theme.DogsDarkBackground
import com.example.ui.theme.DogsDarkSurface
import com.example.ui.theme.DogsDarkSurfaceBorder
import com.example.ui.theme.DogsDarkSurfaceElevated
import com.example.ui.theme.DogsGold
import com.example.ui.theme.DogsGoldLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TonBlue

data class LeaderboardItem(
    val rank: Int,
    val name: String,
    val handle: String,
    val tier: String,
    val dogsBalance: Long
)

@Composable
fun LeaderboardScreen(
    userProfile: UserProfile
) {
    val topDogs = listOf(
        LeaderboardItem(1, "Pavel Durov", "@durov", "Founder Mastiff", 18500000L),
        LeaderboardItem(2, "Ton Whaler", "@ton_whale", "Mythic Alpha", 9420000L),
        LeaderboardItem(3, "Crypto Sultan PK", "@sultan_crypto", "Legendary OG", 7150000L),
        LeaderboardItem(4, "Dogs Keeper", "@spotty_king", "Legendary OG", 5900000L),
        LeaderboardItem(5, "Ahmad Pak Doge", "@ahmad_dogs", "Legendary OG", 4800000L),
        LeaderboardItem(6, "Hamza TON", "@hamza_ton", "Veteran Hound", 3750000L),
        LeaderboardItem(7, "Sasha Plotvinov", "@sasha_not", "Veteran Hound", 3100000L),
        LeaderboardItem(8, "Ali EasyPaisa", "@ali_ep", "Veteran Hound", 2400000L),
        LeaderboardItem(9, "Karachi Crypto", "@khi_trader", "Certified Pup", 1950000L),
        LeaderboardItem(10, "Lahore Whale", "@lhr_whale", "Certified Pup", 1450000L)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DogsDarkBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Global Dogs Hall of Fame",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
                Text(
                    text = "Top spotty dogs ranked by total tokens and Telegram age",
                    fontSize = 12.sp,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            itemsIndexed(topDogs) { index, dog ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = DogsDarkSurface),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(
                            when (dog.rank) {
                                1 -> DogsGold
                                2 -> Color(0xFFC0C0C0)
                                3 -> Color(0xFFCD7F32)
                                else -> DogsDarkSurfaceBorder
                            }
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Rank badge
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(
                                    when (dog.rank) {
                                        1 -> DogsGold
                                        2 -> Color(0xFFC0C0C0)
                                        3 -> Color(0xFFCD7F32)
                                        else -> DogsDarkSurfaceElevated
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "#${dog.rank}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (dog.rank <= 3) Color.Black else TextSecondary
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(dog.name, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("${dog.handle} • ${dog.tier}", fontSize = 10.sp, color = TextSecondary)
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = String.format("%,d", dog.dogsBalance),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = DogsGoldLight
                            )
                            Text("\$DOGS", fontSize = 9.sp, color = TextMuted)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        // Pinned User Ranking Card at Bottom
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DogsDarkSurfaceElevated),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DogsGold))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(DogsGold),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.EmojiEvents, contentDescription = null, tint = Color.Black, modifier = Modifier.size(20.dp))
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Your Global Rank", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DogsGold)
                    Text("#14,291 (Top 0.5%)", fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = TextPrimary)
                    Text("@${userProfile.telegramUsername} • ${userProfile.ogDogTier}", fontSize = 10.sp, color = TextSecondary)
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = String.format("%,d", userProfile.dogsBalance),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text("\$DOGS", fontSize = 9.sp, color = DogsGold)
                }
            }
        }
    }
}
