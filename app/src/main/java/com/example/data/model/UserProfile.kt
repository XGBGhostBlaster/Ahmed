package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val telegramUsername: String = "SpottyDog_OG",
    val telegramUserId: Long = 1894928194L,
    val accountAgeYears: Double = 4.5,
    val accountCreatedYear: Int = 2021,
    val isTelegramPremium: Boolean = true,
    val ogDogTier: String = "Legendary OG Mastiff",
    val dogsBalance: Long = 34500L,
    val tonBalance: Double = 5.20,
    val streakDays: Int = 4,
    val lastClaimTimestamp: Long = 0L,
    val hasCompletedOnboarding: Boolean = true,
    val totalTaps: Int = 48
)
