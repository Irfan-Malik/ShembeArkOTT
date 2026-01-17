package com.main.taratv.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
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
fun RadioScreen(onPlayClick: () -> Unit = {}) {
            LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
        item {
            RadioSearchBar()
        }
        
        item {
            NowPlayingSection(onPlayClick = onPlayClick)
        }
        
        item {
            CategorySection(title = "Popular Stations", stations = getPopularStations(), onPlayClick = onPlayClick)
        }
        
        item {
            CategorySection(title = "News & Talk", stations = getNewsStations(), onPlayClick = onPlayClick)
        }
        
        item {
            CategorySection(title = "Music", stations = getMusicStations(), onPlayClick = onPlayClick)
        }
        
        item {
            CategorySection(title = "Sports", stations = getSportsStations(), onPlayClick = onPlayClick)
        }
    }
}

@Composable
fun RadioSearchBar() {
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
                text = "Search radio stations...",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun NowPlayingSection(onPlayClick: () -> Unit = {}) {
    Spacer(modifier = Modifier.height(12.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppRed, RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Now Playing",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "FM 99",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Popular music hits",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.White, CircleShape)
                        .clickable { onPlayClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "⏸",
                        color = AppRed,
                        fontSize = 24.sp
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        text = "Live",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "FM 99",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun CategorySection(title: String, stations: List<RadioStation>, onPlayClick: () -> Unit = {}) {
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
            items(stations) { station ->
                RadioStationCard(station = station, onPlayClick = onPlayClick)
            }
        }
    }
}

@Composable
fun RadioStationCard(station: RadioStation, onPlayClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .clickable { /* Handle station click */ }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(AppDarkGray, RoundedCornerShape(8.dp))
        ) {
            // Get the radio station image resource ID based on station name
            val imageResId = getRadioStationImageResource(station.name)
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = station.name,
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
            text = station.name,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

data class RadioStation(
    val name: String,
    val category: String,
    val isLive: Boolean = false
)

fun getPopularStations(): List<RadioStation> {
    return listOf(
        RadioStation("FM 99", "Music", true),
        RadioStation("FM 90", "Music"),
        RadioStation("FM 105", "Music"),
        RadioStation("FM 101", "News")
    )
}

fun getNewsStations(): List<RadioStation> {
    return listOf(
        RadioStation("AM 600", "News"),
        RadioStation("FM 101", "News"),
        RadioStation("FM 104", "News")
    )
}

fun getMusicStations(): List<RadioStation> {
    return listOf(
        RadioStation("FM 90", "Music"),
        RadioStation("FM 99", "Music"),
        RadioStation("FM 105", "Music"),
        RadioStation("FM 1313", "Music"),
        RadioStation("FM 1510", "Music")
    )
}

fun getSportsStations(): List<RadioStation> {
    return listOf(
        RadioStation("AM 102", "Sports"),
        RadioStation("AM 640", "Sports"),
        RadioStation("AM 1600", "Sports")
    )
}

fun getRadioStationImageResource(stationName: String): Int {
    return when (stationName) {
        "FM 90" -> R.drawable.fm90
        "FM 99" -> R.drawable.fm99
        "FM 105" -> R.drawable.fm105
        "FM 1313" -> R.drawable.fm1313
        "FM 1510" -> R.drawable.fm1510
        "FM 101" -> R.drawable.fm101
        "FM 104" -> R.drawable.fm104
        "AM 600" -> R.drawable.am600
        "AM 102" -> R.drawable.am102
        "AM 640" -> R.drawable.am640
        "AM 1600" -> R.drawable.am1600
        else -> R.drawable.radio // fallback image
    }
} 