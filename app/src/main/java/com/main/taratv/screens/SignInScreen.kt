package com.main.taratv.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.main.taratv.ui.theme.AppRed

@Composable
fun SignInScreen(
    onSignInComplete: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit
) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBlack)
            .padding(24.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Row (

                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){

                Text(
                    text = "SHEMBE",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,

                )
                Text(
                    text = "ARK",
                    color = Color.White,
                    fontSize = 24.sp,
                    letterSpacing = 4.sp,

                )
            }


            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Enter email / Phone number",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                placeholder = { Text(text = "Enter email / phone number", color = Color.Gray) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Enter your password",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                placeholder = { Text(text = "Enter password", color = Color.Gray) },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Forgot password?",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onForgotClick() }
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = { onSignInComplete() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppRed),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "LOG IN TO MY ACCOUNT", color = Color.White, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "OR",
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(18.dp))

            SocialButton(text = "Continue with Google")
            Spacer(modifier = Modifier.height(12.dp))
            SocialButton(text = "Continue with Facebook", background = Color(0xFF2D6AEF))
            Spacer(modifier = Modifier.height(12.dp))
            SocialButton(text = "Continue with Apple")

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Don’t have an account? ", color = Color.Gray)
                TextButton(onClick = { onSignUpClick() }) {
                    Text(text = "Sign up", color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun SocialButton(text: String, background: Color = Color.White) {
    Card(
        colors = CardDefaults.cardColors(containerColor = background),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = if (background == Color.White) Color.Black else Color.White
            )
        }
    }
}

@Composable
fun SignInScreenPreview() {
    SignInScreen(onSignInComplete = {}, onSignUpClick = {}, onForgotClick = {})
}
