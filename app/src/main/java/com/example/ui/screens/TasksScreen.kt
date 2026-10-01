package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TaskItem
import com.example.ui.theme.DogsDarkBackground
import com.example.ui.theme.DogsDarkSurface
import com.example.ui.theme.DogsDarkSurfaceBorder
import com.example.ui.theme.DogsDarkSurfaceElevated
import com.example.ui.theme.DogsGold
import com.example.ui.theme.DogsGoldLight
import com.example.ui.theme.EasyPaisaGreen
import com.example.ui.theme.JazzCashOrange
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TonBlue

@Composable
fun TasksScreen(
    tasks: List<TaskItem>,
    onCompleteTask: (TaskItem) -> Unit,
    onOpenJazzCash: () -> Unit,
    onOpenEasyPaisa: () -> Unit,
    onOpenTon: () -> Unit,
    onOpenAgeScan: () -> Unit
) {
    val categories = listOf("All", "Pakistan Payouts", "TON & Crypto", "Telegram & Dogs")
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }

    val filteredTasks = tasks.filter {
        if (selectedCategoryIndex == 0) true
        else it.category == categories[selectedCategoryIndex]
    }

    val completedCount = tasks.count { it.isCompleted }
    val totalCount = tasks.size

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DogsDarkBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Spotty Missions & Quests",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
            )
            Text(
                text = "Complete quests to earn extra \$DOGS & unlock withdrawal tiers",
                fontSize = 12.sp,
                color = TextSecondary
            )
        }

        // Progress Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DogsDarkSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DogsDarkSurfaceBorder))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("QUEST COMPLETION", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$completedCount of $totalCount Quests Done",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(DogsGold)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "${if (totalCount > 0) (completedCount * 100 / totalCount) else 0}%",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        // Category Pills
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.forEachIndexed { index, cat ->
                    val isSelected = selectedCategoryIndex == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) DogsGold else DogsDarkSurfaceElevated)
                            .clickable { selectedCategoryIndex = index }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cat.replace(" & ", "\n"),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.Black else TextSecondary,
                            maxLines = 2
                        )
                    }
                }
            }
        }

        // Task Items
        items(filteredTasks, key = { it.id }) { task ->
            TaskRowItem(
                task = task,
                onAction = {
                    when (task.actionType) {
                        "LINK_JAZZCASH" -> onOpenJazzCash()
                        "LINK_EASYPAISA" -> onOpenEasyPaisa()
                        "LINK_TON" -> onOpenTon()
                        "CHECK_AGE" -> onOpenAgeScan()
                        else -> onCompleteTask(task)
                    }
                },
                onClaim = {
                    onCompleteTask(task)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TaskRowItem(
    task: TaskItem,
    onAction: () -> Unit,
    onClaim: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = DogsDarkSurface),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(
                if (task.isCompleted) DogsDarkSurfaceBorder else DogsGold.copy(alpha = 0.3f)
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Category icon
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        when (task.category) {
                            "Pakistan Payouts" -> JazzCashOrange.copy(alpha = 0.15f)
                            "TON & Crypto" -> TonBlue.copy(alpha = 0.15f)
                            else -> DogsGold.copy(alpha = 0.15f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = when (task.category) {
                        "Pakistan Payouts" -> "🇵🇰"
                        "TON & Crypto" -> "💎"
                        else -> "🐕"
                    },
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = task.title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = task.description,
                    fontSize = 10.sp,
                    color = TextSecondary,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "+${String.format("%,d", task.rewardDogs)} \$DOGS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DogsGoldLight
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (task.isCompleted) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SuccessGreen.copy(alpha = 0.2f))
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = SuccessGreen,
                        modifier = Modifier.size(16.dp)
                    )
                }
            } else {
                Button(
                    onClick = {
                        if (task.actionType.startsWith("LINK_") || task.actionType == "CHECK_AGE") {
                            onAction()
                        } else {
                            onClaim()
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DogsGold,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text(
                        text = if (task.actionType.startsWith("LINK_")) "Connect" else "Claim",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
