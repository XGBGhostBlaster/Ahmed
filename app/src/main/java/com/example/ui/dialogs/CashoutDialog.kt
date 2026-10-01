package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalAtm
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.UserProfile
import com.example.data.model.WalletConnections
import com.example.ui.theme.DogsDarkBackground
import com.example.ui.theme.DogsDarkSurface
import com.example.ui.theme.DogsDarkSurfaceBorder
import com.example.ui.theme.DogsDarkSurfaceElevated
import com.example.ui.theme.DogsGold
import com.example.ui.theme.EasyPaisaGreen
import com.example.ui.theme.JazzCashOrange
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TonBlue

@Composable
fun CashoutDialog(
    initialMethod: String,
    userProfile: UserProfile,
    walletConnections: WalletConnections,
    dogsPerTon: Double,
    tonToPkr: Double,
    isProcessing: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (method: String, amountDogs: Long, feeOption: String) -> Unit
) {
    var selectedMethod by remember { mutableStateOf(initialMethod) }
    var dogsAmountText by remember {
        mutableStateOf(minOf(10000L, userProfile.dogsBalance).toString())
    }
    var feePaymentOption by remember { mutableStateOf("TON_FEE") } // TON_FEE, JAZZCASH_FEE, EASYPAISA_FEE, NO_FEE

    val enteredDogs = dogsAmountText.toLongOrNull() ?: 0L
    val tonEquivalent = enteredDogs / dogsPerTon
    val pkrEquivalent = tonEquivalent * tonToPkr

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(20.dp),
            color = DogsDarkSurface,
            tonalElevation = 12.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Direct Cashout & Payout",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = "Receive TON Coins or PKR via JazzCash/EasyPaisa",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Method Selector Tabs
                Text(
                    text = "SELECT RECEIVING METHOD",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DogsGold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MethodSelectCard(
                        title = "JazzCash",
                        subtitle = "Instant PKR",
                        color = JazzCashOrange,
                        isSelected = selectedMethod == "JazzCash",
                        modifier = Modifier.weight(1f),
                        onClick = { selectedMethod = "JazzCash" }
                    )
                    MethodSelectCard(
                        title = "EasyPaisa",
                        subtitle = "Instant PKR",
                        color = EasyPaisaGreen,
                        isSelected = selectedMethod == "EasyPaisa",
                        modifier = Modifier.weight(1f),
                        onClick = { selectedMethod = "EasyPaisa" }
                    )
                    MethodSelectCard(
                        title = "TON Wallet",
                        subtitle = "TON Coins",
                        color = TonBlue,
                        isSelected = selectedMethod == "TON Wallet",
                        modifier = Modifier.weight(1f),
                        onClick = { selectedMethod = "TON Wallet" }
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Destination account details
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = DogsDarkSurfaceElevated),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DogsDarkSurfaceBorder))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "DESTINATION ACCOUNT",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        when (selectedMethod) {
                            "JazzCash" -> {
                                Text(
                                    text = "📱 ${walletConnections.jazzCashNumber}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = JazzCashOrange
                                )
                                Text(
                                    text = "Title: ${walletConnections.jazzCashTitle} (Auto-Linked)",
                                    fontSize = 11.sp,
                                    color = TextPrimary
                                )
                            }
                            "EasyPaisa" -> {
                                Text(
                                    text = "📱 ${walletConnections.easyPaisaNumber}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EasyPaisaGreen
                                )
                                Text(
                                    text = "Title: ${walletConnections.easyPaisaTitle} (Auto-Linked)",
                                    fontSize = 11.sp,
                                    color = TextPrimary
                                )
                            }
                            else -> {
                                Text(
                                    text = "💎 TON Address:",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TonBlue
                                )
                                Text(
                                    text = walletConnections.tonWalletAddress,
                                    fontSize = 11.sp,
                                    color = TextPrimary,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Amount of DOGS to cashout
                Text(
                    text = "ENTER \$DOGS AMOUNT TO CASHOUT",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DogsGold
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = dogsAmountText,
                    onValueChange = { dogsAmountText = it.filter { c -> c.isDigit() } },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = DogsGold,
                        unfocusedBorderColor = DogsDarkSurfaceBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    trailingIcon = {
                        Text(
                            text = "\$DOGS",
                            fontWeight = FontWeight.Bold,
                            color = DogsGold,
                            modifier = Modifier.padding(end = 12.dp)
                        )
                    }
                )

                // Quick percentage pills
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf(0.25, 0.50, 0.75, 1.0).forEach { fraction ->
                        val label = if (fraction == 1.0) "MAX" else "${(fraction * 100).toInt()}%"
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(DogsDarkSurfaceElevated)
                                .clickable {
                                    val calc = (userProfile.dogsBalance * fraction).toLong()
                                    dogsAmountText = calc.toString()
                                }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = label,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Real-time Conversion Result Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = DogsDarkBackground),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(DogsDarkSurfaceBorder))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("You will receive:", fontSize = 12.sp, color = TextSecondary)
                            Text("Market Rate:", fontSize = 11.sp, color = TextMuted)
                        }
                        Spacer(modifier = Modifier.height(6.dp))

                        if (selectedMethod == "TON Wallet") {
                            Text(
                                text = "${String.format("%.2f", tonEquivalent)} TON Coins",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = TonBlue
                            )
                            Text(
                                text = "≈ Rs ${String.format("%,.0f", pkrEquivalent)} PKR (~$${String.format("%.2f", tonEquivalent * 5.50)} USD)",
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        } else {
                            Text(
                                text = "Rs ${String.format("%,.0f", pkrEquivalent)} PKR",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedMethod == "JazzCash") JazzCashOrange else EasyPaisaGreen
                            )
                            Text(
                                text = "Converted from ${String.format("%.2f", tonEquivalent)} TON Coins at live exchange rate",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // TRANSACTION & GAS FEES SECTION (User requested feature!)
                Text(
                    text = "TRANSACTION / GAS FEE OPTION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DogsGold
                )
                Text(
                    text = "Choose how to pay blockchain verification and gateway transfer fee:",
                    fontSize = 11.sp,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))

                FeeOptionRow(
                    title = "Pay Gas via JazzCash",
                    feeSubtitle = "Rs 80 PKR deducted directly from your JazzCash",
                    isSelected = feePaymentOption == "JAZZCASH_FEE",
                    tag = "Mobile",
                    onClick = { feePaymentOption = "JAZZCASH_FEE" }
                )
                FeeOptionRow(
                    title = "Pay Gas via EasyPaisa",
                    feeSubtitle = "Rs 80 PKR deducted directly from your EasyPaisa",
                    isSelected = feePaymentOption == "EASYPAISA_FEE",
                    tag = "Mobile",
                    onClick = { feePaymentOption = "EASYPAISA_FEE" }
                )
                FeeOptionRow(
                    title = "Pay Gas in TON Coins",
                    feeSubtitle = "0.05 TON (~Rs 77 PKR) network validator fee",
                    isSelected = feePaymentOption == "TON_FEE",
                    tag = "Crypto",
                    onClick = { feePaymentOption = "TON_FEE" }
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Confirm Action Button
                Button(
                    onClick = {
                        if (enteredDogs > 0) {
                            onConfirm(selectedMethod, enteredDogs, feePaymentOption)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (selectedMethod) {
                            "JazzCash" -> JazzCashOrange
                            "EasyPaisa" -> EasyPaisaGreen
                            else -> TonBlue
                        },
                        contentColor = Color.White
                    ),
                    enabled = !isProcessing && enteredDogs > 0 && enteredDogs <= userProfile.dogsBalance
                ) {
                    if (isProcessing) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "Confirm & Send to $selectedMethod",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MethodSelectCard(
    title: String,
    subtitle: String,
    color: Color,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) color.copy(alpha = 0.2f) else DogsDarkSurfaceElevated)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) color else DogsDarkSurfaceBorder,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) color else TextPrimary
            )
            Text(
                text = subtitle,
                fontSize = 9.sp,
                color = TextSecondary
            )
        }
    }
}

@Composable
fun FeeOptionRow(
    title: String,
    feeSubtitle: String,
    isSelected: Boolean,
    tag: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) DogsDarkSurfaceElevated else DogsDarkBackground)
            .border(
                1.dp,
                if (isSelected) DogsGold else DogsDarkSurfaceBorder,
                RoundedCornerShape(10.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = DogsGold)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = feeSubtitle,
                fontSize = 10.sp,
                color = TextSecondary
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(DogsDarkSurfaceBorder)
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(tag, fontSize = 9.sp, color = TextPrimary)
        }
    }
}
