package com.main.taratv.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.main.taratv.ui.theme.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.main.taratv.R

@Composable
fun TvScreen(onPlayClick: () -> Unit = {}) {
            LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
        item {
            TvSearchBar()
        }
        
        item {
            CategorySection(title = "News Channels", channels = getNewsChannels(), onPlayClick = onPlayClick)
        }
        
        item {
            CategorySection(title = "Entertainment", channels = getEntertainmentChannels(), onPlayClick = onPlayClick)
        }
        
        item {
            CategorySection(title = "Sports", channels = getSportsChannels(), onPlayClick = onPlayClick)
        }
        
        item {
            CategorySection(title = "Kids", channels = getKidsChannels(), onPlayClick = onPlayClick)
        }
    }
}

@Composable
fun TvSearchBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppLightGray, RoundedCornerShape(8.dp))
            .padding(8.dp)
            .clickable { /* Handle search click */ }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Search TV channels...",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun CategorySection(title: String, channels: List<TvChannel>, onPlayClick: () -> Unit = {}) {
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
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(channels) { channel ->
                TvChannelCard(channel = channel, onPlayClick = onPlayClick)
            }
        }
    }
}

@Composable
fun TvChannelCard(channel: TvChannel, onPlayClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable { /* Handle channel click */ }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(AppDarkGray, RoundedCornerShape(8.dp))
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