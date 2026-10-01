package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionRecord(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val method: String, // "JazzCash", "EasyPaisa", "TON Wallet", "Airdrop Claim", "Tap Earn"
    val amountDogs: Long,
    val amountFiatOrTon: String, // e.g. "Rs 2,500 PKR", "2.5 TON", "Fee: Rs 80"
    val type: String, // "WITHDRAW", "DEPOSIT", "REWARD", "FEE_PAYMENT"
    val status: String, // "COMPLETED", "PROCESSING"
    val txHash: String,
    val feeDetails: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
