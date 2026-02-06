package com.main.taratv.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
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
fun ForgotPasswordScreen(onSend: () -> Unit) {
    val emailPhone = remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(AppBlack).padding(24.dp)) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(text = "SHEMBE ARK", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Forgot Password", color = Color.White, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Enter your registered Email Address or Phone Number", color = AppLightGray, fontSize = 14.sp, modifier = Modifier.padding(horizontal = 8.dp))

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Enter email / Phone number", color = Color.White, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = emailPhone.value, onValueChange = { emailPhone.value = it }, placeholder = { Text(text = "Enter email / phone number", color = AppLightGray) }, singleLine = true, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(8.dp))

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "We'll send you an email / SMS with instruction on how to access your account.", color = AppLightGray, modifier = Modifier.padding(horizontal = 8.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center)

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { onSend() }, modifier = Modifier.fillMaxWidth().height(56.dp), colors = ButtonDefaults.buttonColors(containerColor = AppRed), shape = RoundedCornerShape(12.dp)) {
                Text(text = "SEND", color = Color.White)
            }
        }
    }
}

@Composable
fun ForgotPasswordPreview() {
    ForgotPasswordScreen(onSend = {})
}

