package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallet_connections")
data class WalletConnections(
    @PrimaryKey val id: Int = 1,
    val jazzCashNumber: String = "0301-7654321",
    val jazzCashTitle: String = "Ahmad Abbasi",
    val jazzCashLinked: Boolean = true,
    val easyPaisaNumber: String = "0345-9876543",
    val easyPaisaTitle: String = "Ahmad Abbasi",
    val easyPaisaLinked: Boolean = false,
    val tonWalletAddress: String = "EQB-vW302_d8P9xKL-Z5aD8L99fM1230Q_dogs_ton",
    val tonWalletLinked: Boolean = true
)
