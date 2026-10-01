package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "referrals")
data class ReferralFriend(
    @PrimaryKey val id: String,
    val name: String,
    val telegramHandle: String,
    val accountAgeInfo: String,
    val bonusEarned: Long,
    val joinedTimeAgo: String
)
