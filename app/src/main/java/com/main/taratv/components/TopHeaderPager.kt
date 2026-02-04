package com.main.taratv.components

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.main.taratv.R
import com.main.taratv.ui.theme.AppDarkGray
import com.main.taratv.ui.theme.AppRed
import kotlinx.coroutines.delay


@Composable
fun TopHeaderPager(onVideoPlayerClick: (String?) -> Unit) {
    val bannerImages = listOf(
        R.drawable.mobilehomebanner01,
        R.drawable.mobilehomebanner02,
        R.drawable.mobilehomebanner03,
        R.drawable.mobilehomebanner4
    )

    val pagerState = rememberPagerState(pageCount = { bannerImages.size })

    // Auto-scroll functionality with smooth animation
    LaunchedEffect(Unit) {
        while (true) {
            delay(4000) // Change slide every 4 seconds
            val nextPage = (pagerState.currentPage + 1) % bannerImages.size
            pagerState.animateScrollToPage(
                page = nextPage,
                animationSpec = tween(
                    durationMillis = 1500, // Smooth 1.5 second transition
                    easing = EaseInOutCubic
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppDarkGray, RoundedCornerShape(4.dp))
            ) {
                Image(
                    painter = painterResource(id = bannerImages[page]),
                    contentDescription = "Banner ${page + 1}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient overlay for better text readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.3f),
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                )

                // Play button overlay
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(AppRed, CircleShape)
                        .align(Alignment.Center)
                        .clickable { onVideoPlayerClick(null) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        // Page indicator (centered with 8.dp spacing between dots)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .align(Alignment.BottomCenter)
                .background(color = Color.Black.copy(alpha = 0.5f)),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically

        ) {
            repeat(bannerImages.size) { index ->
                Box(
                    modifier = Modifier
                        .size(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (pagerState.currentPage == index) Color.White else Color.White.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                    )
                }
            }
        }
    }
}