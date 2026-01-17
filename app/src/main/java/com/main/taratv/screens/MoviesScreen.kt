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
fun MoviesScreen(onMovieClick: (Movie) -> Unit = {}, onPlayClick: () -> Unit = {}) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        item {
            MovieSearchBar()
        }
        
        item {
            FeaturedMovieSection(onMovieClick = onMovieClick, onPlayClick = onPlayClick)
        }
        
        item {
            CategorySection(title = "Action Movies", movies = getActionMovies(), onMovieClick = onMovieClick, onPlayClick = onPlayClick)
        }
        
        item {
            CategorySection(title = "Love & Romance", movies = getLoveMovies(), onMovieClick = onMovieClick, onPlayClick = onPlayClick)
        }
        
        item {
            CategorySection(title = "Science Fiction", movies = getScienceFictionMovies(), onMovieClick = onMovieClick, onPlayClick = onPlayClick)
        }
    }
}

@Composable
fun MovieSearchBar() {
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
                text = "Search movies and TV series...",
                color = Color.Gray,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}

@Composable
fun FeaturedMovieSection(onMovieClick: (Movie) -> Unit = {}, onPlayClick: () -> Unit = {}) {
    val featuredMovie = Movie("Underworld: Blood Wars", 2016, "Action", "1h 31m", R.drawable.underworld_blood_wars,R.drawable.underworld_blood_wars_poster, true)
    Spacer(modifier = Modifier.height(16.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(AppDarkGray, RoundedCornerShape(12.dp))
            .padding(8.dp)
            .clickable { onMovieClick(featuredMovie) }
    ) {
        Image(
            painter = painterResource(id = R.drawable.underworld_blood_wars),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Featured",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Underworld: Blood Wars",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Action • 2016 • 1h 31m",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(AppRed, CircleShape)
                        .clickable { onPlayClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = "Watch Now",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CategorySection(title: String, movies: List<Movie>, onMovieClick: (Movie) -> Unit = {}, onPlayClick: () -> Unit = {}) {
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
            items(movies) { movie ->
                MovieCard(movie = movie, onMovieClick = onMovieClick, onPlayClick = onPlayClick)
            }
        }
    }
}

@Composable
fun MovieCard(movie: Movie, onMovieClick: (Movie) -> Unit = {}, onPlayClick: () -> Unit = {}) {
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
//        Text(
//            text = movie.title,
//            fontSize = 12.sp,
//            modifier = Modifier.padding(top = 4.dp)
//        )
    }
}

data class Movie(
    val title: String,
    val year: Int,
    val genre: String,
    val duration: String,
    val imageResource: Int,
    val imagePosterResource: Int,
    val isPremium: Boolean = false
)

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
        Movie("Frozen II", 2019, "Animation", "1h 43m", R.drawable.frozen_ii ,R.drawable.frozen_poster),
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