package com.main.taratv.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.main.taratv.ui.theme.AppBlack
import com.main.taratv.ui.theme.AppLightGray
import com.main.taratv.ui.theme.AppRed

@Composable
fun VerificationCodeScreen(onVerify: () -> Unit) {
    val code1 = remember { mutableStateOf("") }
    val code2 = remember { mutableStateOf("") }
    val code3 = remember { mutableStateOf("") }
    val code4 = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBlack)
            .padding(24.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(text = "SHEMBE ARK", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Verification Code", color = Color.White, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Please enter the verification code sent to your email and phone", color = AppLightGray, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 24.dp),)

            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth().padding(horizontal = 36.dp)) {
                VerificationBox(text = "4")
                VerificationBox(text = "2")
                VerificationBox(text = "1")
                VerificationBox(text = "6")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Didn’t receive an OTP?", color = AppLightGray)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "RESEND", color = Color.White)

            Spacer(modifier = Modifier.height(40.dp))

            Button(onClick = { onVerify() }, modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = AppRed), shape = RoundedCornerShape(12.dp)) {
                Text(text = "VERIFY", color = Color.White)
            }
        }
    }
}

@Composable
fun VerificationBox(text: String) {
    Box(modifier = Modifier.size(72.dp).background(Color(0xFF424244), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
        Text(text = text, color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun VerificationPreview() {
    VerificationCodeScreen(onVerify = {})
}

