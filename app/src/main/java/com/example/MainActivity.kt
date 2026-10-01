package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.DogsTab
import com.example.ui.DogsViewModel
import com.example.ui.components.DogsBottomNav
import com.example.ui.components.DogsTopBar
import com.example.ui.dialogs.AccountAgeScanDialog
import com.example.ui.dialogs.CashoutDialog
import com.example.ui.dialogs.ConnectEasyPaisaDialog
import com.example.ui.dialogs.ConnectJazzCashDialog
import com.example.ui.dialogs.ConnectTonDialog
import com.example.ui.screens.FrensScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LeaderboardScreen
import com.example.ui.screens.TasksScreen
import com.example.ui.screens.WalletScreen
import com.example.ui.theme.DogsCryptoTheme
import com.example.ui.theme.DogsDarkBackground

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogsCryptoTheme {
                DogsApp()
            }
        }
    }
}

@Composable
fun DogsApp(viewModel: DogsViewModel = viewModel()) {
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val walletConnections by viewModel.walletConnections.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    val referrals by viewModel.referrals.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val uiStatus by viewModel.uiStatus.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiStatus.snackbarMessage) {
        uiStatus.snackbarMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.dismissSnackbar()
        }
    }

    val uncompletedTaskCount = tasks.count { !it.isCompleted }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(DogsDarkBackground)
            .windowInsetsPadding(WindowInsets.statusBars),
        topBar = {
            DogsTopBar(
                telegramUsername = userProfile.telegramUsername,
                dogsBalance = userProfile.dogsBalance,
                tonBalance = userProfile.tonBalance,
                streakDays = userProfile.streakDays,
                onAgeClick = { viewModel.openAgeScanner() }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)) {
                DogsBottomNav(
                    currentTab = currentTab,
                    onTabSelected = { viewModel.setTab(it) },
                    taskCount = uncompletedTaskCount
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = DogsDarkBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                DogsTab.HOME -> {
                    HomeScreen(
                        userProfile = userProfile,
                        walletConnections = walletConnections,
                        dogsPerTon = viewModel.dogsPerTon,
                        tonToPkr = viewModel.tonToPkr,
                        onTapBark = { viewModel.tapToBark() },
                        onClaimStreak = { viewModel.claimDailyStreak() },
                        onOpenAgeScan = { viewModel.openAgeScanner() },
                        onOpenCashout = { method -> viewModel.openCashout(method) },
                        onNavigateToWallet = { viewModel.setTab(DogsTab.WALLET) }
                    )
                }
                DogsTab.TASKS -> {
                    TasksScreen(
                        tasks = tasks,
                        onCompleteTask = { task -> viewModel.completeTask(task) },
                        onOpenJazzCash = { viewModel.openConnectJazzCash() },
                        onOpenEasyPaisa = { viewModel.openConnectEasyPaisa() },
                        onOpenTon = { viewModel.openConnectTon() },
                        onOpenAgeScan = { viewModel.openAgeScanner() }
                    )
                }
                DogsTab.WALLET -> {
                    WalletScreen(
                        userProfile = userProfile,
                        walletConnections = walletConnections,
                        transactions = transactions,
                        dogsPerTon = viewModel.dogsPerTon,
                        tonToPkr = viewModel.tonToPkr,
                        onOpenCashout = { method -> viewModel.openCashout(method) },
                        onConnectJazzCash = { viewModel.openConnectJazzCash() },
                        onConnectEasyPaisa = { viewModel.openConnectEasyPaisa() },
                        onConnectTon = { viewModel.openConnectTon() },
                        onShowMessage = { viewModel.showMessage(it) }
                    )
                }
                DogsTab.FRENS -> {
                    FrensScreen(
                        referrals = referrals,
                        telegramUsername = userProfile.telegramUsername,
                        onShowMessage = { viewModel.showMessage(it) }
                    )
                }
                DogsTab.LEADERBOARD -> {
                    LeaderboardScreen(
                        userProfile = userProfile
                    )
                }
            }
        }
    }

    // Cashout & Payout Dialog
    if (uiStatus.showCashoutDialog) {
        CashoutDialog(
            initialMethod = uiStatus.selectedCashoutMethod,
            userProfile = userProfile,
            walletConnections = walletConnections,
            dogsPerTon = viewModel.dogsPerTon,
            tonToPkr = viewModel.tonToPkr,
            isProcessing = uiStatus.isProcessingWithdrawal,
            onDismiss = { viewModel.closeCashout() },
            onConfirm = { method, amountDogs, feeOption ->
                viewModel.executeWithdrawal(method, amountDogs, feeOption)
            }
        )
    }

    // Connect JazzCash Dialog
    if (uiStatus.showJazzCashConnectDialog) {
        ConnectJazzCashDialog(
            initialNumber = walletConnections.jazzCashNumber,
            initialTitle = walletConnections.jazzCashTitle,
            onDismiss = { viewModel.closeConnectJazzCash() },
            onSave = { number, title ->
                viewModel.updateJazzCash(number, title)
            }
        )
    }

    // Connect EasyPaisa Dialog
    if (uiStatus.showEasyPaisaConnectDialog) {
        ConnectEasyPaisaDialog(
            initialNumber = walletConnections.easyPaisaNumber,
            initialTitle = walletConnections.easyPaisaTitle,
            onDismiss = { viewModel.closeConnectEasyPaisa() },
            onSave = { number, title ->
                viewModel.updateEasyPaisa(number, title)
            }
        )
    }

    // Connect TON Dialog
    if (uiStatus.showTonConnectDialog) {
        ConnectTonDialog(
            initialAddress = walletConnections.tonWalletAddress,
            onDismiss = { viewModel.closeConnectTon() },
            onSave = { address ->
                viewModel.updateTonWallet(address)
            }
        )
    }

    // Telegram Account Age Scanner Dialog
    if (uiStatus.showAgeScanDialog) {
        AccountAgeScanDialog(
            userProfile = userProfile,
            isScanning = uiStatus.isScanningAge,
            onDismiss = { viewModel.closeAgeScanner() },
            onScan = { handle, years, isPremium ->
                viewModel.scanAccountAge(handle, years, isPremium)
            }
        )
    }
}
