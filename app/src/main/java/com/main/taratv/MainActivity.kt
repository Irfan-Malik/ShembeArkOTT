package com.main.taratv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.main.taratv.components.NavigationItem
import com.main.taratv.screens.Movie

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
            Column(modifier = Modifier.fillMaxSize()) {
                // Top Header

                TopHeader(
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

                // Top Navigation Tabs
                TopNavigation(selectedTab = selectedTab, onTabSelected = { selectedTab = it })
                
                // Spacer to ensure separation
//                Spacer(modifier = Modifier.height(8.dp))
                
                // Main Content with proper spacing
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(start = 8.dp, top = 5.dp, end = 8.dp, bottom = 25.dp)
                ) {
                    when (selectedTab) {
                        0 -> HomeScreen(
                            onItemClick = { showDetailScreen = true },
                            onVideoPlayerClick = { showVideoPlayer = true },
                            onMovieClick = { movie ->
                                selectedMovie = movie
                                showDetailScreen = true
                            }
                        )
                        1 -> TvScreen(onPlayClick = { showVideoPlayer = true })
                        2 -> RadioScreen(onPlayClick = { showVideoPlayer = true })
                        3 -> MoviesScreen(
                            onMovieClick = { movie ->
                                selectedMovie = movie
                                showDetailScreen = true
                            },
                            onPlayClick = { showVideoPlayer = true }
                        )
                        4 -> ProfileScreen()
                    }
                }
                
                // Bottom spacer to ensure separation from bottom navigation
                Spacer(modifier = Modifier.height(55.dp))
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
fun TopHeader(onMenuClick: () -> Unit, onBackClick: () -> Unit = {}, showBackButton: Boolean = false) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppRed)
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(0.dp, 16.dp, 0.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button or Hamburger menu
            Icon(
                imageVector = if (showBackButton) Icons.Default.ArrowBack else Icons.Default.Menu,
                contentDescription = if (showBackButton) "Back" else "Menu",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { 
                        if (showBackButton) onBackClick() else onMenuClick() 
                    }
            )
            
            // App logo and name
            Text(
                text = "Tara",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            
            // Search icon
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* Handle search click */ }
            )
        }
    }
}

@Composable
fun TopNavigation(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppRed)
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = selectedTab == 0,
                onClick = { onTabSelected(0) }
            )
            NavigationItemWithDrawable(
                icon = R.drawable.channels_active,
                label = "TV",
                isSelected = selectedTab == 1,
                onClick = { onTabSelected(1) }
            )
            NavigationItemWithDrawable(
                icon = R.drawable.radio_white_home,
                label = "Radio",
                isSelected = selectedTab == 2,
                onClick = { onTabSelected(2) }
            )
            NavigationItemWithDrawable(
                icon = R.drawable.movies_series_active,
                label = "Movies",
                isSelected = selectedTab == 3,
                onClick = { onTabSelected(3) }
            )
            NavigationItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = selectedTab == 4,
                onClick = { onTabSelected(4) }
            )
        }
    }
}

@Composable
fun NavigationItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
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
                .size(40.dp)
                .background(
                    if (isSelected) Color.White else Color.Transparent,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = if (isSelected) AppRed else Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = label,
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
        
        // Selection indicator
        if (isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(2.dp)
                    .background(Color.White, RoundedCornerShape(1.dp))
            )
        }
    }
}

@Composable
fun NavigationItemWithDrawable(
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
                .size(40.dp)
                .background(
                    if (isSelected) Color.White else Color.Transparent,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = if (isSelected) AppRed else Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = label,
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
        
        // Selection indicator
        if (isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(2.dp)
                    .background(Color.White, RoundedCornerShape(1.dp))
            )
        }
    }
}

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
                onItemClick = onItemClick,
//                onItemClick = onItemClick,
                onPlayClick = onVideoPlayerClick
            )
        }
//        item {
//            AllChannelsSection(
//                onItemClick = onItemClick,
//                onPlayClick = onVideoPlayerClick
//            )
//        }
        item {
            MoviesSection(
                onMovieClick = onMovieClick,
                onPlayClick = onVideoPlayerClick
            )
        }
//        item {
//            WhatsNewSection(
//                onItemClick = onItemClick,
//                onPlayClick = onVideoPlayerClick
//            )
//        }
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
            delay(4000) // Change slide every 6 seconds (slower)
            val nextPage = (pagerState.currentPage + 1) % bannerImages.size
            pagerState.animateScrollToPage(
                page = nextPage,
                animationSpec = androidx.compose.animation.core.tween(
                    durationMillis = 1500, // Smooth 1.5 second transition
                    easing = androidx.compose.animation.core.EaseInOutCubic
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
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
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
                
                // Movie title overlay
                Text(
                    text = "Tears of Steel - Demo Stream",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                )
                
                // Video player indicator
                Text(
                    text = "HLS Stream",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                )
            }
        }
        
        // Page indicator
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(bannerImages.size) { index ->
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

@Composable
fun FeaturedChannelsSection(
    onItemClick: () -> Unit,
    onPlayClick: () -> Unit
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
                    onClick = onPlayClick,
//                    onClick = onItemClick,
                    onPlayClick = onPlayClick
                )
            }
        }
    }
}

@Composable
fun ChannelCard(
    channel: Channel, 
    onClick: () -> Unit,
    onPlayClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(120.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
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
            
            // Play button overlay
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(AppRed, CircleShape)
                    .align(Alignment.Center)
                    .padding(4.dp)
                    .clickable { onPlayClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Text(
            text = "${channel.number} ${channel.name}",
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun AllChannelsSection(
    onItemClick: () -> Unit,
    onPlayClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "All TV Channels",
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
            items(getAllChannels()) { channel ->
                ChannelCard(
                    channel = channel, 
                    onClick = onItemClick,
                    onPlayClick = onPlayClick
                )
            }
        }
    }
}

@Composable
fun WhatsNewSection(
    onItemClick: () -> Unit,
    onPlayClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "What's New",
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
            items(getWhatsNewContent()) { content ->
                ContentCard(
                    content = content, 
                    onClick = onItemClick,
                    onPlayClick = onPlayClick
                )
            }
        }
    }
}

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

fun getActionMovies(): List<Movie> {
    return listOf(
        Movie("21 Bridges", 2019, "Action", "1h 39m", R.drawable.bridges_21,R.drawable.bridges_21_poster),
        Movie("Mission Impossible: Fallout", 2018, "Action", "2h 27m", R.drawable.mission_impossible_fallout,R.drawable.mission_impossible_fallout_poster),
        Movie("Braven", 2018, "Action", "1h 34m", R.drawable.braven,R.drawable.braven_poster),
        Movie("Underworld: Blood Wars", 2016, "Action", "1h 31m", R.drawable.underworld_blood_wars,R.drawable.underworld_blood_wars_poster, true)
    )
}

fun getLoveMovies(): List<Movie> {
    return listOf(
        Movie("Frozen II", 2019, "Animation", "1h 43m", R.drawable.frozen_ii,R.drawable.frozen_poster),
        Movie("Maleficent", 2014, "Fantasy", "1h 37m", R.drawable.maleficent,R.drawable.maleficent_poster),
        Movie("Aladdin", 2019, "Adventure", "2h 8m", R.drawable.aladdin,R.drawable.aladdin_poster),
        Movie("Aquaman", 2018, "Action", "2h 23m", R.drawable.aquaman,R.drawable.aquaman_poster, true)
    )
}

fun getScienceFictionMovies(): List<Movie> {
    return listOf(
        Movie("Alita: Battle Angel", 2019, "Sci-Fi", "2h 2m", R.drawable.alita_battle_angel,R.drawable.alita_battle_angel_poster),
        Movie("Black Widow", 2021, "Action", "2h 14m", R.drawable.black_widow_poster,R.drawable.black_widow_preview),
        Movie("Focus", 2015, "Crime", "1h 45m", R.drawable.focus,R.drawable.focus_poster),
        Movie("Braven", 2018, "Action", "1h 34m", R.drawable.braven,R.drawable.braven_poster, true)
    )
}

fun getAllMovies(): List<Movie> {
    return getActionMovies() + getLoveMovies() + getScienceFictionMovies()
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
    onMovieClick: (Movie) -> Unit,
    onPlayClick: () -> Unit
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
                    onMovieClick = onMovieClick,
                    onPlayClick = onPlayClick
                )
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    onMovieClick: (Movie) -> Unit,
    onPlayClick: () -> Unit
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