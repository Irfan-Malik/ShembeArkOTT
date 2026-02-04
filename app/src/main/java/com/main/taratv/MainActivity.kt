package com.main.taratv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.main.taratv.ui.theme.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.main.taratv.components.MovieCard
import com.main.taratv.ui.theme.TaraTVTheme
import com.main.taratv.screens.*
import com.main.taratv.components.NavigationDrawer
import com.main.taratv.components.TopHeader
import com.main.taratv.components.TopNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaraTVTheme {
                TaraTvApp()
            }
        }
    }
}

@Composable
fun TaraTvApp() {
    var showSplash by remember { mutableStateOf(true) }
    var selectedTab by remember { mutableStateOf(0) }
    var isDrawerOpen by remember { mutableStateOf(false) }
    var showDetailScreen by remember { mutableStateOf(false) }
    // Replace showVideoPlayer boolean with a nullable currentVideoUrl
    var currentVideoUrl by remember { mutableStateOf<String?>(null) }
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }

    // Handle system back button
    BackHandler(enabled = (currentVideoUrl != null) || showDetailScreen) {
        when {
            currentVideoUrl != null -> currentVideoUrl = null
            showDetailScreen -> showDetailScreen = false
        }
    }

    if (showSplash) {
        SplashScreenSimple(onSplashComplete = { showSplash = false })
    } else if (currentVideoUrl != null) {
        VideoPlayerScreen(
            onBackClick = { currentVideoUrl = null },
            onFullscreenClick = { /* Handle fullscreen toggle */ },
            videoUrl = currentVideoUrl!!
        )
    } else if (showDetailScreen) {
        DetailScreen(
            movie = selectedMovie,
            onBackClick = { showDetailScreen = false },
            onPlayClick = { url -> currentVideoUrl = url ?: MOVIE_STREAM_URL },
            onShareClick = { /* Handle share click */ },
            onAddToListClick = { /* Handle add to list click */ },
            onMovieClick = { movie ->
                selectedMovie = movie
                // Keep the detail screen open but update the movie
            }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBlack)
        ) {
            Scaffold(
                topBar = {
                    TopHeader(
                        selectedTab = selectedTab,
                        onMenuClick = { isDrawerOpen = true },
                        onBackClick = {
                            when {
                                currentVideoUrl != null -> currentVideoUrl = null
                                showDetailScreen -> showDetailScreen = false
                                else -> { /* Handle back navigation */
                                }
                            }
                        },
                        showBackButton = (currentVideoUrl != null) || showDetailScreen
                    )
                },
                bottomBar = {
                    TopNavigation(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
                },
                modifier = Modifier.fillMaxSize(),
                containerColor = AppBlack
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(start = 8.dp, top = 5.dp, end = 8.dp, bottom = 4.dp)
                ) {
                    when (selectedTab) {
                        0 -> HomeScreen(
                            onItemClick = { showDetailScreen = true },
                            onVideoPlayerClick = { url ->
                                currentVideoUrl = url ?: MOVIE_STREAM_URL
                            },
                            onChannelPlayClick = { url ->
                                currentVideoUrl = url ?: LIVE_STREAM_URL
                            },
                            onMovieClick = { movie: Movie ->
                                selectedMovie = movie
                                showDetailScreen = true
                            }
                        )

                        1 -> TvScreen(onPlayClick = { url ->
                            currentVideoUrl = url ?: LIVE_STREAM_URL
                        })

                        2 -> MoviesScreen(
                            onMovieClick = { movie: Movie ->
                                selectedMovie = movie
                                showDetailScreen = true
                            },
                            onPlayClick = { url -> currentVideoUrl = url ?: MOVIE_STREAM_URL }
                        )

                        3 -> RadioScreen(onPlayClick = { url ->
                            currentVideoUrl = url ?: LIVE_STREAM_URL
                        })

                        4 -> ProfileScreen()
                    }
                }
            }

            // Navigation Drawer
            NavigationDrawer(
                isOpen = isDrawerOpen,
                onClose = { isDrawerOpen = false },
                onNavigationItemClick = { item ->
                    isDrawerOpen = false
                    // Handle navigation item clicks
                    when (item.title) {
                        "Home" -> selectedTab = 0
                        "Channels" -> selectedTab = 1
                        "Radio" -> selectedTab = 2
                        "Movies" -> selectedTab = 3
                        "TV Series" -> selectedTab = 3
                    }
                }
            )
        }
    }
}


@Composable
fun BroadCasting() {
    Image(
        painter = painterResource(id = R.drawable.screen_broadcast),
        contentDescription = null,
        modifier = Modifier.height(24.dp)
    )
}

@Composable
fun MenuIcon() {
    Image(
        painter = painterResource(id = R.drawable.menu),
        contentDescription = null,
        modifier = Modifier.height(24.dp)
    )
}

@Composable
fun SearchIcon() {
    Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search",
        tint = Color.White,
        modifier = Modifier
            .size(24.dp)
            .clickable { /* Handle search click */ }
    )
}

// Data classes
data class Channel(
    val name: String,
    val number: Int
)

// Sample data
fun getFeaturedChannels(): List<Channel> {
    return listOf(
        Channel("CNN", 3),
        Channel("HBO", 13),
        Channel("ESPN International", 21),
        Channel("Disney", 33),
        Channel("MTV", 14),
        Channel("National Geographic", 15),
        Channel("Fox Sports", 23),
        Channel("Cartoon Network", 31)
    )
}


fun getChannelImageResource(channelName: String): Int {
    return when (channelName) {
        "A&E" -> R.drawable.a_e
        "AXN" -> R.drawable.axn
        "Band News" -> R.drawable.band_news
        "Band Sports" -> R.drawable.band_sports
        "Boomerang" -> R.drawable.boomerang
        "CANCÃO NOVA" -> R.drawable.cancao_nova
        "Cartoon Network" -> R.drawable.cartoon_network
        "CNN" -> R.drawable.cnn
        "Disney" -> R.drawable.disney
        "Disney Junior" -> R.drawable.disney_junior
        "ESPN International" -> R.drawable.espn_international
        "Fox Sports" -> R.drawable.fox_sports
        "Fox Sports 2" -> R.drawable.fox_sports_2
        "HBO" -> R.drawable.hbo
        "HBO Plus" -> R.drawable.hbo_plus
        "MTV" -> R.drawable.mtv
        "National Geographic" -> R.drawable.national_geographic
        "Paramount" -> R.drawable.paramount
        "Record News" -> R.drawable.record_news
        "TV Justiça" -> R.drawable.tv_justica
        "Warner" -> R.drawable.warner
        else -> R.drawable.channels_img // fallback image
    }
}


@Preview(showBackground = true)
@Composable
fun TaraTvAppPreview() {
    TaraTVTheme {
        TaraTvApp()
    }
}