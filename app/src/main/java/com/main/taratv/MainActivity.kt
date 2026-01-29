package com.main.taratv

import android.os.Bundle
import android.widget.Space
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
import com.main.taratv.ui.theme.TaraTVTheme
import com.main.taratv.screens.*
import com.main.taratv.components.NavigationDrawer

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

// -----------------
// App entry + state
// -----------------
@Composable
fun TaraTvApp() {
    var showSplash by remember { mutableStateOf(true) }
    var selectedTab by remember { mutableStateOf(0) }
    var isDrawerOpen by remember { mutableStateOf(false) }
    var showDetailScreen by remember { mutableStateOf(false) }
    var showVideoPlayer by remember { mutableStateOf(false) }
    var selectedMovie by remember { mutableStateOf<Movie?>(null) }

    // Handle system back button
    BackHandler(enabled = showDetailScreen || showVideoPlayer) {
        when {
            showVideoPlayer -> showVideoPlayer = false
            showDetailScreen -> showDetailScreen = false
        }
    }

    if (showSplash) {
        SplashScreenSimple(onSplashComplete = { showSplash = false })
    } else if (showVideoPlayer) {
        VideoPlayerScreen(
            onBackClick = { showVideoPlayer = false },
            onFullscreenClick = { /* Handle fullscreen toggle */ }
        )
    } else if (showDetailScreen) {
        DetailScreen(
            movie = selectedMovie,
            onBackClick = { showDetailScreen = false },
            onPlayClick = { showVideoPlayer = true },
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
                                showVideoPlayer -> showVideoPlayer = false
                                showDetailScreen -> showDetailScreen = false
                                else -> { /* Handle back navigation */ }
                            }
                        },
                        showBackButton = showVideoPlayer || showDetailScreen
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
                             onVideoPlayerClick = { showVideoPlayer = true },
                            onMovieClick = { movie: Movie ->
                                selectedMovie = movie
                                 showDetailScreen = true
                             }
                        )
                        1 -> TvScreen(onPlayClick = { showVideoPlayer = true })
                        2 -> MoviesScreen(
                            onMovieClick = { movie: Movie ->
                                selectedMovie = movie
                                showDetailScreen = true
                            },
                            onPlayClick = { showVideoPlayer = true }
                        )
                        3 ->  RadioScreen(onPlayClick = { showVideoPlayer = true })
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

// -----------------
// Top App Bar
// -----------------
@Composable
fun TopHeader(selectedTab : Int , onMenuClick: () -> Unit, onBackClick: () -> Unit = {}, showBackButton: Boolean = false) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(0.dp, 16.dp, 0.dp, 0.dp),
            /*horizontalArrangement = Arrangement.SpaceBetween,*/
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button or Hamburger menu
            Icon(
                imageVector = if (showBackButton) Icons.AutoMirrored.Filled.ArrowBack else Icons.Default.Menu,
                contentDescription = if (showBackButton) "Back" else "Menu",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { 
                        if (showBackButton) onBackClick() else onMenuClick() 
                    }
            )
            Spacer(modifier = Modifier.width(10.dp))
            when (selectedTab) {
                0 -> Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = null,
                    modifier = Modifier.height(24.dp)
                )
                1 -> Text(
                    text = "Channels",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                2 -> Text(
                    text = "Library",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                3 -> Text(
                    text = "Radio",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                else -> Text(
                    text = "More",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            
            // Search icon
            when (selectedTab) {
                0 -> {
                    BroadCasting()
                    Spacer(modifier = Modifier.width(16.dp))
                    SearchIcon()
                }
                1 ->{
                    BroadCasting()
                    Spacer(modifier = Modifier.width(16.dp))
                    MenuIcon()
                    Spacer(modifier = Modifier.width(16.dp))
                    SearchIcon()
                }

                2 -> {
                    BroadCasting()
                    Spacer(modifier = Modifier.width(16.dp))
                    SearchIcon()
                }
                3 -> {
                    BroadCasting()
                    Spacer(modifier = Modifier.width(16.dp))
                    MenuIcon()
                    Spacer(modifier = Modifier.width(16.dp))
                    SearchIcon()

                }
                else -> {}
            }

        }
    }
}

@Composable
fun BroadCasting(){
    Image(
        painter = painterResource(id = R.drawable.screen_broadcast),
        contentDescription = null,
        modifier = Modifier.height(24.dp)
    )
}

@Composable
fun MenuIcon(){
    Image(
        painter = painterResource(id = R.drawable.menu),
        contentDescription = null,
        modifier = Modifier.height(24.dp)
    )
}

@Composable
fun SearchIcon(){
    Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search",
        tint = Color.White,
        modifier = Modifier
            .size(24.dp)
            .clickable { /* Handle search click */ }
    )
}

// -----------------
// Bottom Navigation
// -----------------
@Composable
fun TopNavigation(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(AppBlack)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationItem(
                icon = if (selectedTab == 0) R.drawable.home else R.drawable.home_,
                label = "Home",
                isSelected = selectedTab == 0,
                onClick = { onTabSelected(0) }
            )
            NavigationItem(
                icon = if (selectedTab == 1) R.drawable.channels else R.drawable.channels_,
                label = "Channels",
                isSelected = selectedTab == 1,
                onClick = { onTabSelected(1) }
            )
            NavigationItem(
                icon = if (selectedTab == 2) R.drawable.library else R.drawable.library_,
                label = "Library",
                isSelected = selectedTab == 2,
                onClick = { onTabSelected(2) }
            )
            NavigationItem(
                icon = if (selectedTab == 3) R.drawable.radio else R.drawable.radio_,
                label = "Radio",
                isSelected = selectedTab == 3,
                onClick = { onTabSelected(3) }
            )
            NavigationItem(
                icon = if (selectedTab == 4) R.drawable.more else R.drawable.more_,
                label = "More",
                isSelected = selectedTab == 4,
                onClick = { onTabSelected(4) }
            )
        }
    }
}

// -----------------
// Navigation Item composables
// -----------------
@Composable
fun NavigationItem(
    icon: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .background(Color.Transparent, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = if (isSelected) CustomBlue else Color.White,
                modifier = Modifier.size(25.dp)
            )
        }

        Text(
            text = label,
            color = if (isSelected) CustomBlue else Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

// -----------------
// Home Screen and sections
// -----------------
@Composable
fun HomeScreen(
    onItemClick: () -> Unit,
    onVideoPlayerClick: () -> Unit,
    onMovieClick: (Movie) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            HeroSection(onVideoPlayerClick = onVideoPlayerClick)
        }

        item {
            FeaturedChannelsSection(
                onItemClick = onItemClick
            )
        }
        item {
            MoviesSection(
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
fun HeroSection(onVideoPlayerClick: () -> Unit) {
    val bannerImages = listOf(
        R.drawable.mobile_01,
        R.drawable.mamma_mia_mobile,
        R.drawable.mobile_02
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
                        .clickable { onVideoPlayerClick() },
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

// -----------------
// Channels / content sections
// -----------------
@Composable
fun FeaturedChannelsSection(
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Featured Channels",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .clickable { /* Handle section click */ }
                .padding(bottom = 16.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(getFeaturedChannels()) { channel ->
                ChannelCard(
                    channel = channel,
                    onClick = onItemClick
                )
            }
        }
    }
}

@Composable
fun ChannelCard(
    channel: Channel,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(120.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(AppDarkGray, RoundedCornerShape(8.dp))
                .clickable { onClick() }
        ) {
            // Get the channel image resource ID based on channel name
            val imageResId = getChannelImageResource(channel.name)
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = channel.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "${channel.number} ${channel.name}",
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp).align(Alignment.BottomCenter)
            )
        }

    }
}

// -----------------
// Content / Movies
// -----------------
@Composable
fun ContentCard(
    content: Content,
    onClick: () -> Unit,
    onPlayClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(120.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(AppDarkGray, RoundedCornerShape(8.dp))
                .clickable { onClick() }
        ) {
            Image(
                painter = painterResource(id = R.drawable.movie_img),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            // Dollar sign for premium content
            if (content.isPremium) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(AppRed, CircleShape)
                        .align(Alignment.TopEnd)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // Play button overlay
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(AppRed, CircleShape)
                    .align(Alignment.Center)
                    .clickable { onPlayClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Text(
                text = content.title,
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
            )
        }
        Text(
            text = content.title,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

// Data classes
data class Channel(
    val name: String,
    val number: Int
)

data class Content(
    val title: String,
    val isPremium: Boolean = false
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

fun getAllChannels(): List<Channel> {
    return listOf(
        // News Channels
        Channel("Band News", 1),
        Channel("CANCÃO NOVA", 2),
        Channel("CNN", 3),
        Channel("Record News", 4),
        Channel("TV Justiça", 5),
        // Entertainment Channels
        Channel("A&E", 10),
        Channel("AXN", 11),
        Channel("HBO Plus", 12),
        Channel("HBO", 13),
        Channel("MTV", 14),
        Channel("National Geographic", 15),
        Channel("Paramount", 16),
        Channel("Warner", 17),
        // Sports Channels
        Channel("Band Sports", 20),
        Channel("ESPN International", 21),
        Channel("Fox Sports 2", 22),
        Channel("Fox Sports", 23),
        // Kids Channels
        Channel("Boomerang", 30),
        Channel("Cartoon Network", 31),
        Channel("Disney Junior", 32),
        Channel("Disney", 33)
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

fun getWhatsNewContent(): List<Content> {
    return listOf(
        Content("HBO Original Series", true),
        Content("CNN Breaking News"),
        Content("ESPN Live Sports", true),
        Content("Disney Family Movies"),
        Content("MTV Music Videos"),
        Content("National Geographic Documentary"),
        Content("Cartoon Network Shows"),
        Content("Fox Sports Highlights")
    )
}

@Composable
fun MoviesSection(
    onMovieClick: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Popular Movies",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .clickable { /* Handle section click */ }
                .padding(bottom = 16.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(getAllMovies()) { movie ->
                MovieCard(
                    movie = movie,
                    onMovieClick = onMovieClick
                )
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    onMovieClick: (Movie) -> Unit
) {
    Box(
        modifier = Modifier
            .width(120.dp)
            .clickable { onMovieClick(movie) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(AppDarkGray, RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = movie.imageResource),
                contentDescription = movie.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            
            // Premium indicator
            if (movie.isPremium) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(AppRed, CircleShape)
                        .align(Alignment.TopEnd)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(8.dp)
            )
        }
        Text(
            text = movie.title,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaraTvAppPreview() {
    TaraTVTheme {
        TaraTvApp()
    }
}