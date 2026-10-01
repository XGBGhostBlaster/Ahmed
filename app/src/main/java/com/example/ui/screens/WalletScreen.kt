package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TransactionRecord
import com.example.data.model.UserProfile
import com.example.data.model.WalletConnections
import com.example.ui.components.MethodBadge
import com.example.ui.theme.DogsDarkBackground
import com.example.ui.theme.DogsDarkSurface
import com.example.ui.theme.DogsDarkSurfaceBorder
import com.example.ui.theme.DogsDarkSurfaceElevated
import com.example.ui.theme.DogsGold
import com.example.ui.theme.DogsGoldLight
import com.example.ui.theme.EasyPaisaGreen
import com.example.ui.theme.JazzCashOrange
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningYellow
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TonBlue
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WalletScreen(
    userProfile: UserProfile,
    walletConnections: WalletConnections,
    transactions: List<TransactionRecord>,
    dogsPerTon: Double,
    tonToPkr: Double,
    onOpenCashout: (String) -> Unit,
    onConnectJazzCash: () -> Unit,
    onConnectEasyPaisa: () -> Unit,
    onConnectTon: () -> Unit,
    onShowMessage: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedFilterIndex by remember { mutableIntStateOf(0) }
    val filters = listOf("All", "Withdrawals", "Gas Fees", "TON Crypto")

    val filteredTransactions = transactions.filter { tx ->
        when (selectedFilterIndex) {
            1 -> tx.type == "WITHDRAW"
            2 -> tx.type == "FEE_PAYMENT"
            3 -> tx.method == "TON Wallet"
            else -> true
        }
    }

    val tonEquiv = userProfile.dogsBalance / dogsPerTon
    val pkrEquiv = tonEquiv * tonToPkr

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DogsDarkBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            // Section Header
            Text(
                text = "Wallet & Cashout Hub",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextPrimary
            )
            Text(
                text = "Direct JazzCash, EasyPaisa & TON coin transactions",
                fontSize = 12.sp,
                color = TextSecondary
            )
        }

        // Crypto & Fiat Asset Balances Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DogsDarkSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(JazzCashOrange, EasyPaisaGreen, TonBlue)))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "TOTAL ESTIMATED PORTFOLIO",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextSecondary,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Rs ${String.format("%,.0f", pkrEquiv + (userProfile.tonBalance * tonToPkr))} PKR",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Divider(color = DogsDarkSurfaceBorder)
                    Spacer(modifier = Modifier.height(14.dp))

                    // 3 column sub balances
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Dogs
                        Column(modifier = Modifier.weight(1f)) {
                            Text("\$DOGS Tokens", fontSize = 11.sp, color = TextSecondary)
                            Text(
                                text = String.format("%,d", userProfile.dogsBalance),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DogsGold
                            )
                            Text(
                                text = "≈ ${String.format("%.1f", tonEquiv)} TON",
                                fontSize = 10.sp,
                                color = TextMuted
                            )
                        }

                        // TON
                        Column(modifier = Modifier.weight(1f)) {
                            Text("TON Coins", fontSize = 11.sp, color = TextSecondary)
                            Text(
                                text = "${String.format("%.2f", userProfile.tonBalance)} TON",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TonBlue
                            )
                            Text(
                                text = "≈ Rs ${String.format("%,.0f", userProfile.tonBalance * tonToPkr)} PKR",
                                fontSize = 10.sp,
                                color = TextMuted
                            )
                        }

                        // PKR
                        Column(modifier = Modifier.weight(1f)) {
                            Text("PKR Available", fontSize = 11.sp, color = TextSecondary)
                            Text(
                                text = "Rs ${String.format("%,.0f", pkrEquiv)}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = EasyPaisaGreen
                            )
                            Text(
                                text = "Instant Payout",
                                fontSize = 10.sp,
                                color = TextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onOpenCashout("JazzCash") },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = JazzCashOrange, contentColor = Color.White)
                        ) {
                            Text("Cashout PKR", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { onOpenCashout("TON Wallet") },
                            modifier = Modifier
                                .weight(1f)
                                .height(44.dp),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = TonBlue, contentColor = Color.White)
                        ) {
                            Text("Receive TON", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // CONNECTED ACCOUNTS HUB (User's specific core feature)
        item {
            Text(
                text = "DIRECT CONNECT ACCOUNTS & GATEWAYS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = DogsGold,
                letterSpacing = 1.sp
            )
        }

        // 1. JazzCash Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DogsDarkSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(JazzCashOrange.copy(alpha = 0.5f)))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(JazzCashOrange),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("JC", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("JazzCash Direct Connect", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    if (walletConnections.jazzCashLinked) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(SuccessGreen.copy(alpha = 0.2f))
                                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                        ) {
                                            Text("LINKED", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                                        }
                                    }
                                }
                                Text("Pakistan Mobile Account Payout & Fee Gateway", fontSize = 11.sp, color = TextSecondary)
                            }
                        }
                        IconButton(onClick = onConnectJazzCash) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit JazzCash", tint = DogsGold, modifier = Modifier.size(18.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(DogsDarkSurfaceElevated)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Account: ${walletConnections.jazzCashNumber}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("Title: ${walletConnections.jazzCashTitle}", fontSize = 11.sp, color = TextSecondary)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(JazzCashOrange.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("Auto-Deposit On", fontSize = 10.sp, color = JazzCashOrange, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onOpenCashout("JazzCash") },
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = JazzCashOrange, contentColor = Color.White)
                        ) {
                            Text("Withdraw to JazzCash", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                onShowMessage("⚡ Gas Fee of Rs 80 PKR settled via JazzCash for upcoming TON transfer!")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, JazzCashOrange)
                        ) {
                            Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = JazzCashOrange, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Pay Gas via JC", fontSize = 11.sp, color = JazzCashOrange, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 2. EasyPaisa Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DogsDarkSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(EasyPaisaGreen.copy(alpha = 0.5f)))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(EasyPaisaGreen),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("EP", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("EasyPaisa Direct Connect", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    if (walletConnections.easyPaisaLinked) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(SuccessGreen.copy(alpha = 0.2f))
                                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                        ) {
                                            Text("LINKED", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                                        }
                                    }
                                }
                                Text("Telenor Microfinance Instant Payouts", fontSize = 11.sp, color = TextSecondary)
                            }
                        }
                        IconButton(onClick = onConnectEasyPaisa) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit EasyPaisa", tint = DogsGold, modifier = Modifier.size(18.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(DogsDarkSurfaceElevated)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Account: ${walletConnections.easyPaisaNumber}", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text("Title: ${walletConnections.easyPaisaTitle}", fontSize = 11.sp, color = TextSecondary)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(EasyPaisaGreen.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("Auto-Deposit On", fontSize = 10.sp, color = EasyPaisaGreen, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onOpenCashout("EasyPaisa") },
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = EasyPaisaGreen, contentColor = Color.White)
                        ) {
                            Text("Withdraw to EasyPaisa", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                onShowMessage("⚡ Gas Fee of Rs 80 PKR settled via EasyPaisa for upcoming TON transfer!")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, EasyPaisaGreen)
                        ) {
                            Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = EasyPaisaGreen, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Pay Gas via EP", fontSize = 11.sp, color = EasyPaisaGreen, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // 3. TON Blockchain Wallet Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = DogsDarkSurface),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(TonBlue.copy(alpha = 0.5f)))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(TonBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("💎", fontSize = 16.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("TON Blockchain Wallet", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(TonBlue.copy(alpha = 0.2f))
                                            .padding(horizontal = 4.dp, vertical = 2.dp)
                                    ) {
                                        Text("TONKEEPER", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TonBlue)
                                    }
                                }
                                Text("Direct Telegram @Wallet & Non-Custodial", fontSize = 11.sp, color = TextSecondary)
                            }
                        }
                        IconButton(onClick = onConnectTon) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit TON", tint = DogsGold, modifier = Modifier.size(18.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(DogsDarkSurfaceElevated)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Connected TON Address", fontSize = 10.sp, color = TextSecondary)
                            Text(
                                text = walletConnections.tonWalletAddress,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TonBlue,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        IconButton(
                            onClick = {
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("TON Address", walletConnections.tonWalletAddress)
                                clipboard.setPrimaryClip(clip)
                                onShowMessage("📋 TON Wallet Address copied to clipboard!")
                            }
                        ) {
                            Icon(Icons.Default.ContentCopy, contentDescription = "Copy", tint = TextMuted, modifier = Modifier.size(16.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onOpenCashout("TON Wallet") },
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = TonBlue, contentColor = Color.White)
                        ) {
                            Text("Transfer TON Coins", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }

                        OutlinedButton(
                            onClick = {
                                onShowMessage("💎 TON Network Status: Active | Validator Gas: 0.05 TON (~Rs 80 PKR)")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, TonBlue)
                        ) {
                            Text("Network Fee Info", fontSize = 11.sp, color = TonBlue, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // TRANSACTION HISTORY SECTION
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TRANSACTION HISTORY",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = DogsGold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "${filteredTransactions.size} Records",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Filter Tabs
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                filters.forEachIndexed { index, filter ->
                    val isSelected = selectedFilterIndex == index
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) DogsGold else DogsDarkSurfaceElevated)
                            .clickable { selectedFilterIndex = index }
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = filter,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.Black else TextSecondary
                        )
                    }
                }
            }
        }

        // Transaction list items
        if (filteredTransactions.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No transactions in this filter", fontSize = 12.sp, color = TextMuted)
                }
            }
        } else {
            items(filteredTransactions, key = { it.id }) { tx ->
                TransactionItemRow(tx = tx, onCopyTx = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("TX Hash", tx.txHash))
                    onShowMessage("TX Hash copied: ${tx.txHash}")
                })
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun TransactionItemRow(
    tx: TransactionRecord,
    onCopyTx: () -> Unit
) {
    val dateFormat = remember { SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = DogsDarkSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DogsDarkSurfaceBorder))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        when (tx.type) {
                            "WITHDRAW" -> Color(0xFF331614)
                            "FEE_PAYMENT" -> Color(0xFF2E2413)
                            "DEPOSIT" -> Color(0xFF142733)
                            else -> Color(0xFF1B2E1D)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (tx.type) {
                        "WITHDRAW" -> Icons.Default.ArrowUpward
                        "FEE_PAYMENT" -> Icons.Default.ElectricBolt
                        "DEPOSIT" -> Icons.Default.ArrowDownward
                        else -> Icons.Default.Check
                    },
                    contentDescription = null,
                    tint = when (tx.type) {
                        "WITHDRAW" -> JazzCashOrange
                        "FEE_PAYMENT" -> DogsGold
                        "DEPOSIT" -> TonBlue
                        else -> SuccessGreen
                    },
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = tx.title,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    MethodBadge(method = tx.method)
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = dateFormat.format(Date(tx.timestamp)),
                        fontSize = 10.sp,
                        color = TextMuted
                    )
                    Text(" • ", fontSize = 10.sp, color = TextMuted)
                    Text(
                        text = tx.txHash.take(12) + "...",
                        fontSize = 10.sp,
                        color = TonBlue,
                        modifier = Modifier.clickable { onCopyTx() }
                    )
                }

                if (tx.feeDetails.isNotBlank()) {
                    Text(
                        text = tx.feeDetails,
                        fontSize = 10.sp,
                        color = TextSecondary
                    )
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = tx.amountFiatOrTon,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = when (tx.type) {
                        "WITHDRAW" -> JazzCashOrange
                        "FEE_PAYMENT" -> WarningYellow
                        else -> SuccessGreen
                    }
                )
                if (tx.amountDogs > 0) {
                    Text(
                        text = "${String.format("%,d", tx.amountDogs)} DOGS",
                        fontSize = 10.sp,
                        color = TextMuted
                    )
                }
            }
        }
    }
}
