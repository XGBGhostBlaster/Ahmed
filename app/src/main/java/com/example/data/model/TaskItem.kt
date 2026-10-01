package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskItem(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val category: String, // "Pakistan Payouts", "TON & Crypto", "Telegram & Dogs"
    val rewardDogs: Long,
    val isCompleted: Boolean = false,
    val actionType: String // "LINK_JAZZCASH", "LINK_EASYPAISA", "LINK_TON", "CHECK_AGE", "TELEGRAM_CHANNEL", "INVITE_FRENS"
)
