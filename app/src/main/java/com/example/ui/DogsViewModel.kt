package com.example.ui

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.DogsDatabase
import com.example.data.model.ReferralFriend
import com.example.data.model.TaskItem
import com.example.data.model.TransactionRecord
import com.example.data.model.UserProfile
import com.example.data.model.WalletConnections
import com.example.data.repository.DogsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class DogsTab {
    HOME,
    TASKS,
    WALLET,
    FRENS,
    LEADERBOARD
}

data class DogsUiStatus(
    val snackbarMessage: String? = null,
    val showAgeScanDialog: Boolean = false,
    val showCashoutDialog: Boolean = false,
    val selectedCashoutMethod: String = "JazzCash", // "JazzCash", "EasyPaisa", "TON Wallet"
    val showJazzCashConnectDialog: Boolean = false,
    val showEasyPaisaConnectDialog: Boolean = false,
    val showTonConnectDialog: Boolean = false,
    val isScanningAge: Boolean = false,
    val ageScanResultBonus: Long = 0L,
    val isProcessingWithdrawal: Boolean = false
)

class DogsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DogsRepository

    init {
        val db = DogsDatabase.getDatabase(application)
        repository = DogsRepository(db.dogsDao())
        viewModelScope.launch {
            repository.initializeDefaultDataIfEmpty()
        }
    }

    val userProfile: StateFlow<UserProfile> = repository.userProfile
        .map { it ?: UserProfile() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserProfile()
        )

    val walletConnections: StateFlow<WalletConnections> = repository.walletConnections
        .map { it ?: WalletConnections() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WalletConnections()
        )

    val tasks: StateFlow<List<TaskItem>> = repository.tasks
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val transactions: StateFlow<List<TransactionRecord>> = repository.transactions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val referrals: StateFlow<List<ReferralFriend>> = repository.referrals
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _currentTab = MutableStateFlow(DogsTab.HOME)
    val currentTab: StateFlow<DogsTab> = _currentTab.asStateFlow()

    private val _uiStatus = MutableStateFlow(DogsUiStatus())
    val uiStatus: StateFlow<DogsUiStatus> = _uiStatus.asStateFlow()

    // Real-time market rates for $DOGS, TON, and PKR
    val dogsPerTon: Double = 10000.0 // 10,000 $DOGS = 1 TON
    val tonPriceUsd: Double = 5.50
    val usdToPkr: Double = 278.50
    val tonToPkr: Double = 1530.0 // 1 TON = ~1,530 PKR
    val tonGasFee: Double = 0.05 // 0.05 TON network fee
    val pkrGasFee: Double = 80.0 // Rs 80 PKR via JazzCash / EasyPaisa

    fun setTab(tab: DogsTab) {
        _currentTab.value = tab
    }

    fun dismissSnackbar() {
        _uiStatus.value = _uiStatus.value.copy(snackbarMessage = null)
    }

    fun showMessage(msg: String) {
        _uiStatus.value = _uiStatus.value.copy(snackbarMessage = msg)
    }

    // Tap to Bark & Earn mechanics with device haptics
    fun tapToBark() {
        triggerHapticFeedback()
        val current = userProfile.value
        val tapReward = 5L // +5 DOGS per playful bark tap
        viewModelScope.launch {
            repository.addTapRewards(1, tapReward, current)
        }
    }

    private fun triggerHapticFeedback() {
        try {
            val context = getApplication<Application>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(
                    VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                @Suppress("DEPRECATION")
                vibrator?.vibrate(30)
            }
        } catch (_: Exception) {}
    }

    fun claimDailyStreak() {
        val current = userProfile.value
        val nextStreak = current.streakDays + 1
        val reward = 250L * nextStreak
        viewModelScope.launch {
            repository.claimDailyStreak(nextStreak, reward, current)
            showMessage("🎉 Daily Bone Claimed! +$reward \$DOGS added to balance!")
        }
    }

    fun completeTask(task: TaskItem) {
        if (task.isCompleted) return
        val current = userProfile.value
        viewModelScope.launch {
            repository.markTaskDone(task, current)
            showMessage("✅ Task Completed: ${task.title}! +${task.rewardDogs} \$DOGS")
        }
    }

    // Open/Close dialogs
    fun openAgeScanner() {
        _uiStatus.value = _uiStatus.value.copy(showAgeScanDialog = true)
    }

    fun closeAgeScanner() {
        _uiStatus.value = _uiStatus.value.copy(showAgeScanDialog = false)
    }

    fun openCashout(method: String = "JazzCash") {
        _uiStatus.value = _uiStatus.value.copy(
            showCashoutDialog = true,
            selectedCashoutMethod = method
        )
    }

    fun closeCashout() {
        _uiStatus.value = _uiStatus.value.copy(showCashoutDialog = false)
    }

    fun openConnectJazzCash() {
        _uiStatus.value = _uiStatus.value.copy(showJazzCashConnectDialog = true)
    }

    fun closeConnectJazzCash() {
        _uiStatus.value = _uiStatus.value.copy(showJazzCashConnectDialog = false)
    }

    fun openConnectEasyPaisa() {
        _uiStatus.value = _uiStatus.value.copy(showEasyPaisaConnectDialog = true)
    }

    fun closeConnectEasyPaisa() {
        _uiStatus.value = _uiStatus.value.copy(showEasyPaisaConnectDialog = false)
    }

    fun openConnectTon() {
        _uiStatus.value = _uiStatus.value.copy(showTonConnectDialog = true)
    }

    fun closeConnectTon() {
        _uiStatus.value = _uiStatus.value.copy(showTonConnectDialog = false)
    }

    // Account Age Re-Scan Simulator
    fun scanAccountAge(handle: String, estimatedYears: Double, isPremium: Boolean) {
        viewModelScope.launch {
            _uiStatus.value = _uiStatus.value.copy(isScanningAge = true)
            kotlinx.coroutines.delay(1800) // realistic scanning delay

            val bonus = ((estimatedYears * 2200L) + (if (isPremium) 3500L else 1000L)).toLong()
            val createdYear = 2026 - estimatedYears.toInt().coerceAtLeast(0)

            repository.reevaluateAccountAge(
                years = estimatedYears,
                createdYear = createdYear,
                isPremium = isPremium,
                bonusAwarded = bonus,
                currentProfile = userProfile.value
            )

            _uiStatus.value = _uiStatus.value.copy(
                isScanningAge = false,
                ageScanResultBonus = bonus,
                showAgeScanDialog = false
            )
            showMessage("🏆 Certified OG Dog! Dropped +$bonus \$DOGS for $estimatedYears yrs age!")
        }
    }

    // Connect JazzCash
    fun updateJazzCash(number: String, title: String) {
        val current = walletConnections.value
        viewModelScope.launch {
            repository.saveWalletConnections(
                current.copy(
                    jazzCashNumber = number,
                    jazzCashTitle = title,
                    jazzCashLinked = true
                )
            )
            closeConnectJazzCash()
            showMessage("🟧 JazzCash account $number connected successfully!")
        }
    }

    // Connect EasyPaisa
    fun updateEasyPaisa(number: String, title: String) {
        val current = walletConnections.value
        viewModelScope.launch {
            repository.saveWalletConnections(
                current.copy(
                    easyPaisaNumber = number,
                    easyPaisaTitle = title,
                    easyPaisaLinked = true
                )
            )
            closeConnectEasyPaisa()
            showMessage("🟩 EasyPaisa account $number connected successfully!")
        }
    }

    // Connect TON Wallet
    fun updateTonWallet(address: String) {
        val current = walletConnections.value
        viewModelScope.launch {
            repository.saveWalletConnections(
                current.copy(
                    tonWalletAddress = address,
                    tonWalletLinked = true
                )
            )
            closeConnectTon()
            showMessage("💎 TON Wallet connected: ${address.take(8)}...${address.takeLast(6)}")
        }
    }

    // Perform Cashout / Withdrawal with fee calculation
    fun executeWithdrawal(
        method: String,
        dogsToWithdraw: Long,
        feeOption: String // "TON_FEE", "JAZZCASH_FEE", "EASYPAISA_FEE", "NO_FEE"
    ) {
        val profile = userProfile.value
        val wallet = walletConnections.value

        if (dogsToWithdraw <= 0 || dogsToWithdraw > profile.dogsBalance) {
            showMessage("⚠️ Insufficient \$DOGS balance!")
            return
        }

        val tonEquivalent = dogsToWithdraw / dogsPerTon
        val pkrEquivalent = tonEquivalent * tonToPkr

        val accountTarget = when (method) {
            "JazzCash" -> "JazzCash: ${wallet.jazzCashNumber} (${wallet.jazzCashTitle})"
            "EasyPaisa" -> "EasyPaisa: ${wallet.easyPaisaNumber} (${wallet.easyPaisaTitle})"
            else -> "TON Address: ${wallet.tonWalletAddress.take(12)}..."
        }

        val feeText = when (feeOption) {
            "TON_FEE" -> "0.05 TON Gas"
            "JAZZCASH_FEE" -> "Rs 80 PKR via JazzCash"
            "EASYPAISA_FEE" -> "Rs 80 PKR via EasyPaisa"
            else -> "0 Free"
        }

        viewModelScope.launch {
            _uiStatus.value = _uiStatus.value.copy(isProcessingWithdrawal = true)
            kotlinx.coroutines.delay(1200)

            val success = repository.executeCashout(
                method = method,
                dogsAmount = dogsToWithdraw,
                pkrAmount = pkrEquivalent,
                tonAmount = tonEquivalent,
                feePaymentOption = feeOption,
                feeText = feeText,
                currentProfile = profile,
                accountDetail = accountTarget
            )

            _uiStatus.value = _uiStatus.value.copy(
                isProcessingWithdrawal = false,
                showCashoutDialog = false
            )

            if (success) {
                if (method == "TON Wallet") {
                    showMessage("🚀 Transferred ${String.format("%.2f", tonEquivalent)} TON to your TON Wallet!")
                } else {
                    showMessage("💸 Paid Rs ${String.format("%,.0f", pkrEquivalent)} PKR directly to $method ($accountTarget)!")
                }
            } else {
                showMessage("❌ Transaction failed! Check your balance and fee selection.")
            }
        }
    }
}
