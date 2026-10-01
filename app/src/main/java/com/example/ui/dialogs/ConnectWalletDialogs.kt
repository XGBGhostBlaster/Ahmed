package com.example.ui.dialogs

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.ui.theme.DogsDarkSurface
import com.example.ui.theme.DogsDarkSurfaceBorder
import com.example.ui.theme.DogsGold
import com.example.ui.theme.EasyPaisaGreen
import com.example.ui.theme.JazzCashOrange
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TonBlue

@Composable
fun ConnectJazzCashDialog(
    initialNumber: String,
    initialTitle: String,
    onDismiss: () -> Unit,
    onSave: (number: String, title: String) -> Unit
) {
    var number by remember { mutableStateOf(initialNumber) }
    var title by remember { mutableStateOf(initialTitle) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            color = DogsDarkSurface
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(JazzCashOrange),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("JC", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Direct Connect JazzCash", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                            Text("Receive direct PKR & pay gas fees", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Account Holder Name", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DogsGold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("e.g. Ahmad Abbasi") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = JazzCashOrange,
                        unfocusedBorderColor = DogsDarkSurfaceBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("JazzCash Mobile Number", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DogsGold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = number,
                    onValueChange = { number = it },
                    placeholder = { Text("0300-1234567") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = JazzCashOrange,
                        unfocusedBorderColor = DogsDarkSurfaceBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        if (number.isNotBlank() && title.isNotBlank()) {
                            onSave(number, title)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = JazzCashOrange, contentColor = Color.White)
                ) {
                    Icon(Icons.Default.Link, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Link JazzCash Account", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ConnectEasyPaisaDialog(
    initialNumber: String,
    initialTitle: String,
    onDismiss: () -> Unit,
    onSave: (number: String, title: String) -> Unit
) {
    var number by remember { mutableStateOf(initialNumber) }
    var title by remember { mutableStateOf(initialTitle) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            color = DogsDarkSurface
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(EasyPaisaGreen),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("EP", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Direct Connect EasyPaisa", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                            Text("Receive direct PKR & pay gas fees", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Account Holder Name", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DogsGold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("e.g. Ahmad Abbasi") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EasyPaisaGreen,
                        unfocusedBorderColor = DogsDarkSurfaceBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("EasyPaisa Mobile Number", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DogsGold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = number,
                    onValueChange = { number = it },
                    placeholder = { Text("0345-1234567") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EasyPaisaGreen,
                        unfocusedBorderColor = DogsDarkSurfaceBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        if (number.isNotBlank() && title.isNotBlank()) {
                            onSave(number, title)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = EasyPaisaGreen, contentColor = Color.White)
                ) {
                    Icon(Icons.Default.Link, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Link EasyPaisa Account", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun ConnectTonDialog(
    initialAddress: String,
    onDismiss: () -> Unit,
    onSave: (address: String) -> Unit
) {
    var address by remember { mutableStateOf(initialAddress) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            color = DogsDarkSurface
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(TonBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("💎", fontSize = 14.sp)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Connect TON Wallet", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                            Text("Tonkeeper / Telegram Wallet", fontSize = 11.sp, color = TextSecondary)
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("TON Blockchain Address", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = DogsGold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    placeholder = { Text("EQB...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TonBlue,
                        unfocusedBorderColor = DogsDarkSurfaceBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        if (address.isNotBlank()) {
                            onSave(address)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TonBlue, contentColor = Color.White)
                ) {
                    Icon(Icons.Default.Link, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Connect TON Wallet", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
