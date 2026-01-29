package com.main.taratv.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2000),
        label = "Splash Alpha Animation"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000L)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Main logo container with semi-transparent background
        Image(
            painter = androidx.compose.ui.res.painterResource(id = com.main.taratv.R.drawable.splash),
            contentDescription = "Splash Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
//        Box(
//            modifier = Modifier
//                .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
//                .padding(14.dp),
//            contentAlignment = Alignment.Center
//        ) {
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                // TARA Logo
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.spacedBy(4.dp)
//                ) {
//                    // Letter T - Vibrant purple/magenta
//                    Text(
//                        text = "T",
//                        fontSize = 36.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFF8B008B)
//                    )
//
//                    // Letter A - Bright orange
//                    Text(
//                        text = "A",
//                        fontSize = 36.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFFFF8C00)
//                    )
//
//                    // Letter R - Pinkish-red
//                    Text(
//                        text = "R",
//                        fontSize = 36.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFFFF69B4)
//                    )
//
//                    // Letter A - Light blue/cyan
//                    Text(
//                        text = "A",
//                        fontSize = 36.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFF00BFFF)
//                    )
//
//                    Spacer(modifier = Modifier.width(8.dp))
//
//                    // Circular play button icon
//                    Box(
//                        modifier = Modifier
//                            .size(32.dp)
//                            .clip(CircleShape)
//                            .background(Color(0xFF32CD32)),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Text(
//                            text = "▶",
//                            color = Color.White,
//                            fontSize = 16.sp
//                        )
//                    }
//                }
//            }
//        }
    }
}

@Composable
fun SplashScreenWithImage(onSplashComplete: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2000),
        label = "Splash Alpha Animation"
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000L)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        // Main logo container with semi-transparent background
        Box(
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(16.dp))
                .padding(14.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // TARA Logo
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Letter T - Vibrant purple/magenta
                    Text(
                        text = "T",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B008B)
                    )

                    // Letter A - Bright orange
                    Text(
                        text = "A",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF8C00)
                    )

                    // Letter R - Pinkish-red
                    Text(
                        text = "R",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF69B4)
                    )

                    // Letter A - Light blue/cyan
                    Text(
                        text = "A",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00BFFF)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Circular play button icon
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF32CD32)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "▶",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreenSimple(onSplashComplete: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(3000L)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E3A8A),
                        Color(0xFF3B82F6)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = androidx.compose.ui.res.painterResource(id = com.main.taratv.R.drawable.splash_img),
            contentDescription = "Splash Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
        // Simple TARA logo
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            // TARA Logo
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(4.dp)
//            ) {
//                // Letter T - Vibrant purple/magenta
//                Text(
//                    text = "T",
//                    fontSize = 48.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF8B008B)
//                )
//
//                // Letter A - Bright orange
//                Text(
//                    text = "A",
//                    fontSize = 48.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFFFF8C00)
//                )
//
//                // Letter R - Pinkish-red
//                Text(
//                    text = "R",
//                    fontSize = 48.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFFFF69B4)
//                )
//
//                // Letter A - Light blue/cyan
//                Text(
//                    text = "A",
//                    fontSize = 48.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF00BFFF)
//                )
//
//                Spacer(modifier = Modifier.width(12.dp))
//
//                // Circular play button icon
//                Box(
//                    modifier = Modifier
//                        .size(40.dp)
//                        .clip(CircleShape)
//                        .background(Color(0xFF32CD32)),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "▶",
//                        color = Color.White,
//                        fontSize = 20.sp
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Text(
//                text = "TV & Radio App",
//                color = Color.White,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Medium
//            )
//        }
    }
}

@Composable
fun GeometricPatterns() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top right patterns
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(32.dp)
        ) {
            // Diagonal lines
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFFE8F5E8), RoundedCornerShape(8.dp))
            )

            // Small dots
            Row(
                modifier = Modifier
                    .offset(x = 40.dp, y = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF90EE90))
                    )
                }
            }

            // Rectangle
            Box(
                modifier = Modifier
                    .offset(x = 20.dp, y = 40.dp)
                    .size(20.dp, 12.dp)
                    .background(Color(0xFF98FB98), RoundedCornerShape(4.dp))
            )
        }

        // Bottom left patterns
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(32.dp)
        ) {
            // Diagonal lines
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFFE8F5E8), RoundedCornerShape(8.dp))
            )

            // Small dots
            Row(
                modifier = Modifier
                    .offset(x = 30.dp, y = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                repeat(4) {
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF90EE90))
                    )
                }
            }

            // Rectangle
            Box(
                modifier = Modifier
                    .offset(x = 15.dp, y = 30.dp)
                    .size(16.dp, 10.dp)
                    .background(Color(0xFF98FB98), RoundedCornerShape(3.dp))
            )
        }
    }
}