package com.example.data.repository

import com.example.data.db.DogsDao
import com.example.data.model.ReferralFriend
import com.example.data.model.TaskItem
import com.example.data.model.TransactionRecord
import com.example.data.model.UserProfile
import com.example.data.model.WalletConnections
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class DogsRepository(private val dogsDao: DogsDao) {

    val userProfile: Flow<UserProfile?> = dogsDao.getUserProfile()
    val walletConnections: Flow<WalletConnections?> = dogsDao.getWalletConnections()
    val tasks: Flow<List<TaskItem>> = dogsDao.getAllTasks()
    val transactions: Flow<List<TransactionRecord>> = dogsDao.getAllTransactions()
    val referrals: Flow<List<ReferralFriend>> = dogsDao.getAllReferrals()

    suspend fun initializeDefaultDataIfEmpty() {
        val initialProfile = UserProfile(
            id = 1,
            telegramUsername = "SpottyDog_OG",
            telegramUserId = 1894928194L,
            accountAgeYears = 4.5,
            accountCreatedYear = 2021,
            isTelegramPremium = true,
            ogDogTier = "Legendary OG Mastiff",
            dogsBalance = 38500L,
            tonBalance = 6.25,
            streakDays = 5,
            lastClaimTimestamp = System.currentTimeMillis() - 86400000L,
            hasCompletedOnboarding = true,
            totalTaps = 120
        )
        dogsDao.insertOrUpdateProfile(initialProfile)

        val initialWallet = WalletConnections(
            id = 1,
            jazzCashNumber = "0301-7654321",
            jazzCashTitle = "Ahmad Abbasi",
            jazzCashLinked = true,
            easyPaisaNumber = "0345-9876543",
            easyPaisaTitle = "Ahmad Abbasi",
            easyPaisaLinked = true,
            tonWalletAddress = "EQB-vW302_d8P9xKL-Z5aD8L99fM1230Q_dogs_ton",
            tonWalletLinked = true
        )
        dogsDao.insertOrUpdateWallet(initialWallet)

        val defaultTasks = listOf(
            TaskItem(
                id = "task_jazzcash",
                title = "Connect JazzCash Account",
                description = "Link your 03XX JazzCash mobile account for direct PKR payouts and gas fee payments",
                category = "Pakistan Payouts",
                rewardDogs = 1500L,
                isCompleted = true,
                actionType = "LINK_JAZZCASH"
            ),
            TaskItem(
                id = "task_easypaisa",
                title = "Connect EasyPaisa Account",
                description = "Link your 03XX EasyPaisa mobile wallet for fast PKR cashouts and instant transfers",
                category = "Pakistan Payouts",
                rewardDogs = 1500L,
                isCompleted = true,
                actionType = "LINK_EASYPAISA"
            ),
            TaskItem(
                id = "task_ton_wallet",
                title = "Link TON Blockchain Wallet",
                description = "Connect your TON wallet (Tonkeeper / Telegram Wallet) to receive TON Coins directly",
                category = "TON & Crypto",
                rewardDogs = 2500L,
                isCompleted = true,
                actionType = "LINK_TON"
            ),
            TaskItem(
                id = "task_ton_fee",
                title = "Pay Gas Fee via JazzCash / EasyPaisa",
                description = "Execute a live TON blockchain transfer using Pakistani mobile wallet to cover network fee",
                category = "Pakistan Payouts",
                rewardDogs = 2000L,
                isCompleted = false,
                actionType = "PAY_FEE"
            ),
            TaskItem(
                id = "task_age_calc",
                title = "Check Telegram Account Age",
                description = "Analyze your Telegram account age and claim your initial Spotty OG drop",
                category = "Telegram & Dogs",
                rewardDogs = 8500L,
                isCompleted = true,
                actionType = "CHECK_AGE"
            ),
            TaskItem(
                id = "task_channel",
                title = "Join Official DOGS Telegram Channel",
                description = "Get announcements, token burn alerts and new airdrop notifications",
                category = "Telegram & Dogs",
                rewardDogs = 1000L,
                isCompleted = false,
                actionType = "TELEGRAM_CHANNEL"
            ),
            TaskItem(
                id = "task_frens",
                title = "Invite 3 Telegram Frens",
                description = "Share your referral link and earn 10% commission on every friend's drops",
                category = "Telegram & Dogs",
                rewardDogs = 3000L,
                isCompleted = false,
                actionType = "INVITE_FRENS"
            )
        )
        dogsDao.insertTasks(defaultTasks)

        val defaultTx = listOf(
            TransactionRecord(
                title = "JazzCash Direct Cashout",
                method = "JazzCash",
                amountDogs = 12000L,
                amountFiatOrTon = "Rs 1,860 PKR",
                type = "WITHDRAW",
                status = "COMPLETED",
                txHash = "JC-PK-982149817",
                feeDetails = "Gas Fee: Rs 0 (Free Promotion)",
                timestamp = System.currentTimeMillis() - 7200000L
            ),
            TransactionRecord(
                title = "TON Gas Fee Paid via EasyPaisa",
                method = "EasyPaisa",
                amountDogs = 0L,
                amountFiatOrTon = "Rs 80 PKR (Fee)",
                type = "FEE_PAYMENT",
                status = "COMPLETED",
                txHash = "EP-GAS-881230491",
                feeDetails = "Paid for 0.05 TON Network Gas",
                timestamp = System.currentTimeMillis() - 25200000L
            ),
            TransactionRecord(
                title = "Received TON Coins in Wallet",
                method = "TON Wallet",
                amountDogs = 20000L,
                amountFiatOrTon = "+2.00 TON (~$11.00 USD)",
                type = "DEPOSIT",
                status = "COMPLETED",
                txHash = "EQB_49208a0d72ef8c1190bc",
                feeDetails = "Blockchain confirmation: 12 blocks",
                timestamp = System.currentTimeMillis() - 86400000L
            ),
            TransactionRecord(
                title = "OG Telegram Account Airdrop",
                method = "Dogs Earn",
                amountDogs = 18500L,
                amountFiatOrTon = "+18,500 DOGS",
                type = "REWARD",
                status = "COMPLETED",
                txHash = "OG-AIRDROP-2021",
                feeDetails = "Account age: 4.5 Years + Premium",
                timestamp = System.currentTimeMillis() - 172800000L
            )
        )
        for (tx in defaultTx) {
            dogsDao.insertTransaction(tx)
        }

        val defaultReferrals = listOf(
            ReferralFriend(
                id = "ref_1",
                name = "Hamza Crypto",
                telegramHandle = "@hamza_pk",
                accountAgeInfo = "5.1 Years (OG)",
                bonusEarned = 1850L,
                joinedTimeAgo = "2 days ago"
            ),
            ReferralFriend(
                id = "ref_2",
                name = "Bilal Tariq",
                telegramHandle = "@bilalt_ton",
                accountAgeInfo = "3.2 Years (Veteran)",
                bonusEarned = 1200L,
                joinedTimeAgo = "4 days ago"
            ),
            ReferralFriend(
                id = "ref_3",
                name = "Zainab Ali",
                telegramHandle = "@zainab_crypto",
                accountAgeInfo = "2.0 Years (Pup)",
                bonusEarned = 950L,
                joinedTimeAgo = "1 week ago"
            )
        )
        dogsDao.insertReferrals(defaultReferrals)
    }

    suspend fun saveProfile(profile: UserProfile) = dogsDao.insertOrUpdateProfile(profile)

    suspend fun saveWalletConnections(wallet: WalletConnections) = dogsDao.insertOrUpdateWallet(wallet)

    suspend fun completeTask(taskId: String, rewardDogs: Long, currentProfile: UserProfile) {
        val updatedProfile = currentProfile.copy(
            dogsBalance = currentProfile.dogsBalance + rewardDogs
        )
        dogsDao.insertOrUpdateProfile(updatedProfile)

        val completedTask = TaskItem(
            id = taskId,
            title = "",
            description = "",
            category = "",
            rewardDogs = rewardDogs,
            isCompleted = true,
            actionType = ""
        )
        // Find task and mark completed
        val tx = TransactionRecord(
            title = "Task Reward Claimed",
            method = "Dogs Earn",
            amountDogs = rewardDogs,
            amountFiatOrTon = "+$rewardDogs \$DOGS",
            type = "REWARD",
            status = "COMPLETED",
            txHash = "TASK-${UUID.randomUUID().toString().take(8).uppercase()}",
            feeDetails = "Instant Task Payout",
            timestamp = System.currentTimeMillis()
        )
        dogsDao.insertTransaction(tx)
    }

    suspend fun markTaskDone(task: TaskItem, currentProfile: UserProfile) {
        val updatedTask = task.copy(isCompleted = true)
        dogsDao.updateTask(updatedTask)

        val updatedProfile = currentProfile.copy(
            dogsBalance = currentProfile.dogsBalance + task.rewardDogs
        )
        dogsDao.insertOrUpdateProfile(updatedProfile)

        val tx = TransactionRecord(
            title = "Quest Completed: ${task.title}",
            method = "Dogs Earn",
            amountDogs = task.rewardDogs,
            amountFiatOrTon = "+${task.rewardDogs} \$DOGS",
            type = "REWARD",
            status = "COMPLETED",
            txHash = "QUEST-${UUID.randomUUID().toString().take(8).uppercase()}",
            feeDetails = "Completed task category: ${task.category}",
            timestamp = System.currentTimeMillis()
        )
        dogsDao.insertTransaction(tx)
    }

    suspend fun addTapRewards(taps: Int, dogsGained: Long, currentProfile: UserProfile) {
        val updatedProfile = currentProfile.copy(
            dogsBalance = currentProfile.dogsBalance + dogsGained,
            totalTaps = currentProfile.totalTaps + taps
        )
        dogsDao.insertOrUpdateProfile(updatedProfile)
    }

    suspend fun claimDailyStreak(streakDays: Int, rewardDogs: Long, currentProfile: UserProfile) {
        val updatedProfile = currentProfile.copy(
            dogsBalance = currentProfile.dogsBalance + rewardDogs,
            streakDays = streakDays,
            lastClaimTimestamp = System.currentTimeMillis()
        )
        dogsDao.insertOrUpdateProfile(updatedProfile)

        val tx = TransactionRecord(
            title = "Day $streakDays Daily Bone Claim",
            method = "Dogs Earn",
            amountDogs = rewardDogs,
            amountFiatOrTon = "+$rewardDogs \$DOGS",
            type = "REWARD",
            status = "COMPLETED",
            txHash = "STREAK-D$streakDays-${UUID.randomUUID().toString().take(6).uppercase()}",
            feeDetails = "Streak maintained: $streakDays days",
            timestamp = System.currentTimeMillis()
        )
        dogsDao.insertTransaction(tx)
    }

    suspend fun executeCashout(
        method: String, // "JazzCash", "EasyPaisa", "TON Wallet"
        dogsAmount: Long,
        pkrAmount: Double,
        tonAmount: Double,
        feePaymentOption: String, // "TON_FEE", "JAZZCASH_FEE", "EASYPAISA_FEE", "NO_FEE"
        feeText: String,
        currentProfile: UserProfile,
        accountDetail: String
    ): Boolean {
        if (currentProfile.dogsBalance < dogsAmount) return false

        var newDogs = currentProfile.dogsBalance - dogsAmount
        var newTon = currentProfile.tonBalance

        if (method == "TON Wallet") {
            newTon += tonAmount
        }

        // Deduct fee if paid in TON
        if (feePaymentOption == "TON_FEE" && newTon >= 0.05) {
            newTon -= 0.05
        }

        dogsDao.insertOrUpdateProfile(
            currentProfile.copy(
                dogsBalance = newDogs,
                tonBalance = if (newTon < 0.0) 0.0 else newTon
            )
        )

        val amountDisplay = when (method) {
            "TON Wallet" -> "+${String.format("%.2f", tonAmount)} TON"
            else -> "Rs ${String.format("%,.0f", pkrAmount)} PKR"
        }

        val tx = TransactionRecord(
            title = "$method Direct Transfer",
            method = method,
            amountDogs = dogsAmount,
            amountFiatOrTon = amountDisplay,
            type = "WITHDRAW",
            status = "COMPLETED",
            txHash = "${method.take(2).uppercase()}-${UUID.randomUUID().toString().take(10).uppercase()}",
            feeDetails = "Fee: $feeText | Destination: $accountDetail",
            timestamp = System.currentTimeMillis()
        )
        dogsDao.insertTransaction(tx)

        // If fee was paid via JazzCash / EasyPaisa separately:
        if (feePaymentOption == "JAZZCASH_FEE" || feePaymentOption == "EASYPAISA_FEE") {
            val feeProvider = if (feePaymentOption == "JAZZCASH_FEE") "JazzCash" else "EasyPaisa"
            val feeTx = TransactionRecord(
                title = "TON Gas Fee Paid via $feeProvider",
                method = feeProvider,
                amountDogs = 0L,
                amountFiatOrTon = "Rs 80 PKR",
                type = "FEE_PAYMENT",
                status = "COMPLETED",
                txHash = "GAS-${UUID.randomUUID().toString().take(8).uppercase()}",
                feeDetails = "TON Network Gas for tx $accountDetail",
                timestamp = System.currentTimeMillis()
            )
            dogsDao.insertTransaction(feeTx)
        }

        return true
    }

    suspend fun reevaluateAccountAge(
        years: Double,
        createdYear: Int,
        isPremium: Boolean,
        bonusAwarded: Long,
        currentProfile: UserProfile
    ) {
        val tier = when {
            years >= 5.0 -> "Mythic Alpha Dog (5+ Yrs)"
            years >= 3.0 -> "Legendary OG Mastiff (3+ Yrs)"
            years >= 1.0 -> "Veteran Hound (1-3 Yrs)"
            else -> "Certified OG Pup (<1 Yr)"
        }

        val updatedProfile = currentProfile.copy(
            accountAgeYears = years,
            accountCreatedYear = createdYear,
            isTelegramPremium = isPremium,
            ogDogTier = tier,
            dogsBalance = currentProfile.dogsBalance + bonusAwarded
        )
        dogsDao.insertOrUpdateProfile(updatedProfile)

        val tx = TransactionRecord(
            title = "OG Age Recalculation Drop",
            method = "Dogs Earn",
            amountDogs = bonusAwarded,
            amountFiatOrTon = "+$bonusAwarded \$DOGS",
            type = "REWARD",
            status = "COMPLETED",
            txHash = "AGE-SCAN-${UUID.randomUUID().toString().take(8).uppercase()}",
            feeDetails = "Evaluated: $years yrs Telegram presence + Premium bonus",
            timestamp = System.currentTimeMillis()
        )
        dogsDao.insertTransaction(tx)
    }
}
