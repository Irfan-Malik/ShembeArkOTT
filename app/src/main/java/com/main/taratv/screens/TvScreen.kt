package com.main.taratv.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.main.taratv.ui.theme.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.main.taratv.R
import com.main.taratv.viewmodel.TvViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.main.taratv.LIVE_STREAM_URL

@Composable
fun TvScreen(onPlayClick: (String?) -> Unit = {}) {
    val tvViewModel: TvViewModel = viewModel()
    val channelsState by tvViewModel.channels.collectAsState()

    // local currently-playing stream URL (so PlayerHeader can react)
    var currentStream by remember { mutableStateOf(LIVE_STREAM_URL) }
    // key to force reload even if url is same
    var currentStreamKey by remember { mutableStateOf(0L) }

    // Group channels by category before entering LazyColumn (avoid DSL scoping issues)
    val grouped = channelsState.groupBy { it.category.ifBlank { "Misc" } }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
             PlayerHeader(url = currentStream, reloadKey = currentStreamKey)
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("HD", color = Color.White)
                    Icon(Icons.Default.PlayArrow, null, tint = Color.White)
                    Icon(Icons.Default.PushPin, null, tint = Color.Blue)
                    Icon(Icons.Default.Tune, null, tint = Color.White)
                }
            }
        }
        item {
//            TvSearchBar()
            // pass a wrapper that updates local player and also notifies parent
            FeaturedChannelsSection(onPlayClick = { url ->
                // ensure we always cause player to recreate even if url is same
                currentStream = url ?: LIVE_STREAM_URL
                currentStreamKey += 1L
                // notify parent callback as well
//                onPlayClick(url)
            })
        }

        // Display grouped sections
//        grouped.forEach { entry ->
//            item {
//                CategorySection(title = entry.key, channels = entry.value, onPlayClick = { url ->
//                    // when category items are clicked, also update the player and force reload
//                    currentStream = url ?: LIVE_STREAM_URL
//                    currentStreamKey += 1L
//                    // notify parent
//                    onPlayClick(url)
//                })
//            }
//        }
    }
}
@Composable
fun FeaturedChannelsSection(onPlayClick: (String?) -> Unit = {}) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Featured Channels (2)",
            color = Color.White,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        // The featured images to show (ctn, gear, ctn, mtv)
        val featuredImages = listOf(
            R.drawable.ctn,
            R.drawable.gear,
            R.drawable.cnn,
            R.drawable.mtv
        )

        // selected index local to featured section
        var selectedIndex by remember { mutableStateOf<Int?>(null) }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(featuredImages) { index, imageRes ->
                val isSelected = selectedIndex == index
                ChannelItem(imageResId = imageRes, isSelected = isSelected) {
                    selectedIndex = index
                    onPlayClick(LIVE_STREAM_URL)
                }
            }
        }
    }
}
@Composable
fun ChannelItem(imageResId: Int = R.drawable.ctn, isSelected: Boolean = false, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Black)
            .then(
                if (isSelected) Modifier.border(width = 2.dp, color = CustomBlue, shape = RoundedCornerShape(12.dp))
                else Modifier
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(imageResId),
            contentDescription = null,
            modifier = Modifier.size(45.dp),
            contentScale = ContentScale.Fit
        )
    }
}
@Composable
fun PlayerHeader(url: String, reloadKey: Long = 0L) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        // use the provided url so header updates when selection changes
        VideoPlayer(url = url, reloadKey = reloadKey)

        // Top bar

        // Bottom controls

    }
}

@Composable
fun VideoPlayer(url: String, reloadKey: Long = 0L) {
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    // Recreate ExoPlayer when URL or reloadKey changes so it starts the new media item
    val exoPlayer = remember(url, reloadKey) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
            prepare()
            playWhenReady = true
        }
    }

    // register listener and release player when composable leaves
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onIsLoadingChanged(isLoadingNow: Boolean) {
                // no-op here
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                isLoading = when (playbackState) {
                    Player.STATE_BUFFERING -> true
                    else -> false
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    useController = false
                }
            },
            update = { playerView ->
                playerView.player = exoPlayer
            },
            modifier = Modifier.fillMaxSize()
        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
    }
}

// TvSearchBar was removed because it's unused in the current UI. Reintroduce as needed.

@Composable
fun CategorySection(title: String, channels: List<TvChannel>, onPlayClick: (String?) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Keep selected channel state local to this CategorySection so each category
        // can have its own selection. We'll track by channel name.
        val (selectedChannel, setSelectedChannel) = remember { mutableStateOf<String?>(null) }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(channels) { channel ->
                val isSelected = selectedChannel == channel.name
                TvChannelCard(
                    channel = channel,
                    isSelected = isSelected,
                    onClick = {
                        // mark selection and start playing the live stream
                        setSelectedChannel(channel.name)
                        onPlayClick(LIVE_STREAM_URL)
                    }
                )
            }
        }
    }
}

@Composable
fun TvChannelCard(channel: TvChannel, isSelected: Boolean = false, onPlayClick: (String?) -> Unit = {}, onClick: (() -> Unit)? = null) {
    // Backwards-compatible overload: if onClick provided we'll use it, else use onPlayClick
    val clickAction: () -> Unit = onClick ?: { onPlayClick(LIVE_STREAM_URL) }

    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable { clickAction() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(AppDarkGray)
                .then(
                    if (isSelected) Modifier.border(width = 2.dp, color = CustomBlue, shape = RoundedCornerShape(8.dp))
                    else Modifier
                )
        ) {
            // Get the channel image resource ID based on channel name
            val imageResId = getChannelImageResource(channel.name)
            // Center the image inside the box and give consistent size
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = channel.name,
                    modifier = Modifier.size(48.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Play button overlay (small)
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(AppRed, CircleShape)
                    .align(Alignment.Center)
                    .clickable { clickAction() },
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

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = channel.name,
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 4.dp)
        )
    }
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


data class TvChannel(
    val name: String,
    val number: Int,
    val category: String
)

fun getNewsChannels(): List<TvChannel> {
    return listOf(
        TvChannel("Band News", 1, "News"),
        TvChannel("CANCÃO NOVA", 2, "News"),
        TvChannel("CNN", 3, "News"),
        TvChannel("Record News", 4, "News"),
        TvChannel("TV Justiça", 5, "News")
    )
}

fun getEntertainmentChannels(): List<TvChannel> {
    return listOf(
        TvChannel("A&E", 10, "Entertainment"),
        TvChannel("AXN", 11, "Entertainment"),
        TvChannel("HBO Plus", 12, "Entertainment"),
        TvChannel("HBO", 13, "Entertainment"),
        TvChannel("MTV", 14, "Entertainment"),
        TvChannel("National Geographic", 15, "Entertainment"),
        TvChannel("Paramount", 16, "Entertainment"),
        TvChannel("Warner", 17, "Entertainment")
    )
}

fun getSportsChannels(): List<TvChannel> {
    return listOf(
        TvChannel("Band Sports", 20, "Sports"),
        TvChannel("ESPN International", 21, "Sports"),
        TvChannel("Fox Sports 2", 22, "Sports"),
        TvChannel("Fox Sports", 23, "Sports")
    )
}

fun getKidsChannels(): List<TvChannel> {
    return listOf(
        TvChannel("Boomerang", 30, "Kids"),
        TvChannel("Cartoon Network", 31, "Kids"),
        TvChannel("Disney Junior", 32, "Kids"),
        TvChannel("Disney", 33, "Kids")
    )
}
