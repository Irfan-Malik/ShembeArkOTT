package com.main.taratv.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.main.taratv.ui.theme.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.main.taratv.R
import androidx.compose.foundation.Image

@Composable
fun DetailScreen(
    movie: Movie? = null,
    onBackClick: () -> Unit,
    onPlayClick: () -> Unit,
    onShareClick: () -> Unit,
    onAddToListClick: () -> Unit,
    onMovieClick: (Movie) -> Unit = {}
) {

    BackHandler {
        onBackClick()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                HeroSection(
                    movie = movie,
                    onBackClick = onBackClick,
                    onPlayClick = onPlayClick,
                    onShareClick = onShareClick
                )
            }
            
            item {
                DetailsSection(movie = movie, onAddToListClick = onAddToListClick)
            }
            
            item {
                SynopsisSection(movie = movie)
            }
            
            item {
                SuggestionsSection(movie = movie, onMovieClick = onMovieClick)
            }
            
//            item {
//                TabSection()
//            }
            
//            item {
//                EpisodesSection()
//            }
        }
    }
}

@Composable
fun HeroSection(
    movie: Movie?,
    onBackClick: () -> Unit,
    onPlayClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.Black)
    ) {
        // Hero image with background from drawable
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Background image
            Image(
                painter = painterResource(id = movie?.imagePosterResource ?: R.drawable.movie_img),
                contentDescription = movie?.title ?: "Background",
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
            Spacer(modifier = Modifier.height(10.dp))
            // Back button
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .clickable { onBackClick() }
            )
            
            // Share button
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(24.dp)
                    .clickable { onShareClick() }
            )
            
            // Play button overlay
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFF00BFFF), CircleShape)
                    .align(Alignment.Center)
                    .clickable { onPlayClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
            
            // Title and info overlay
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = movie?.title ?: "Movie Title",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "${movie?.genre ?: "Action"} | ${movie?.year ?: 2024} | ${movie?.duration ?: "2h 0m"}",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                
                // Star rating
//                Row(
//                    modifier = Modifier.padding(vertical = 4.dp)
//                ) {
//                    repeat(5) { index ->
//                        Icon(
//                            imageVector = Icons.Default.Star,
//                            contentDescription = "Star",
//                            tint = if (index < 4) Color(0xFF00BFFF) else Color.Gray,
//                            modifier = Modifier.size(20.dp)
//                        )
//                    }
//                }
            }
            
            // Thumbnail on the right
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp)
                    .size(80.dp, 120.dp)
                    .background(AppDarkGray, RoundedCornerShape(8.dp))
            ) {
                // Movie image
                Image(
                    painter = painterResource(id = movie?.imageResource ?: R.drawable.movie_img),
                    contentDescription = movie?.title ?: "Movie",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                )
                            )
                        )
                )
                
                // Premium/Free text overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (movie?.isPremium == true) {
                        Text(
                            text = "PREMIUM",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = "FREE",
                            color = Color.White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailsSection(movie: Movie?, onAddToListClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Add to list button
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFF00BFFF), CircleShape)
                    .clickable { onAddToListClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add to List",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Details list
            Row(
                modifier = Modifier.weight(1f)
            ) {
                DetailItem("Genre", movie?.genre ?: "Action")
                Spacer(modifier = Modifier.width(20.dp))
                DetailItem("Year", movie?.year?.toString() ?: "2024")
                Spacer(modifier = Modifier.width(20.dp))
                DetailItem("Duration", movie?.duration ?: "2h 0m")
//                DetailItem("Quality", if (movie?.isPremium == true) "Premium" else "Standard")
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 12.sp
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun SynopsisSection(movie: Movie?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = getSynopsis(movie),
                color = Color.White,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}

fun getSynopsis(movie: Movie?): String {
    return when (movie?.title) {
        "21 Bridges" -> "An embattled NYPD detective is thrust into a citywide manhunt for a pair of cop killers after uncovering a massive and unexpected conspiracy."
        "Mission Impossible: Fallout" -> "Ethan Hunt and his IMF team, along with some familiar allies, race against time after a mission gone wrong."
        "Gemini Man" -> "An over-the-hill hitman faces off against a younger, faster, cloned version of himself."
        "Underworld: Blood Wars" -> "Vampire death dealer Selene fends off brutal attacks from both the Lycan clan and the Vampire faction that betrayed her."
        "Frozen II" -> "Anna, Elsa, Kristoff, Olaf and Sven leave Arendelle to travel to an ancient, autumn-bound forest of an enchanted land."
        "Maleficent" -> "A vengeful fairy is driven to curse an infant princess, only to discover that the child may be the one person who can restore peace to their troubled land."
        "Aladdin" -> "A kind-hearted street urchin and a power-hungry Grand Vizier compete for a magic lamp that has the power to make their deepest wishes come true."
        "Aquaman" -> "Arthur Curry, the human-born heir to the underwater kingdom of Atlantis, goes on a quest to prevent a war between the worlds of ocean and land."
        "Alita: Battle Angel" -> "A deactivated female cyborg is revived, but cannot remember anything of her past life and goes on a quest to find out who she is."
        "Black Widow" -> "Natasha Romanoff confronts the darker parts of her ledger when a dangerous conspiracy with ties to her past arises."
        "Focus" -> "In the midst of veteran con man Nicky's latest scheme, a woman from his past - now an accomplished femme fatale - shows up and throws his plans for a loop."
        "Braven" -> "A logger defends his family from a group of dangerous drug runners."
        else -> "An exciting adventure that will keep you on the edge of your seat. Experience the thrill and drama in this captivating story."
    }
}

@Composable
fun SuggestionsSection(movie: Movie?, onMovieClick: (Movie) -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "More Like This",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getSuggestionsForMovie(movie)) { suggestionMovie ->
                    SuggestionMovieCard(
                        movie = suggestionMovie,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}

@Composable
fun SuggestionMovieCard(movie: Movie, onMovieClick: (Movie) -> Unit = {}) {
    Column(
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
//            if (movie.isPremium) {
//                Box(
//                    modifier = Modifier
//                        .size(24.dp)
//                        .background(AppRed, CircleShape)
//                        .align(Alignment.TopEnd)
//                        .padding(4.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "$",
//                        color = Color.White,
//                        fontSize = 12.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                }
//            }
            
            // Play button overlay
//            Box(
//                modifier = Modifier
//                    .size(40.dp)
//                    .background(AppRed, CircleShape)
//                    .align(Alignment.Center)
//                    .clickable { onMovieClick(movie) },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Default.PlayArrow,
//                    contentDescription = "Play",
//                    tint = Color.White,
//                    modifier = Modifier.size(20.dp)
//                )
//            }
        }
        
        Text(
            text = movie.title,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

fun getSuggestionsForMovie(movie: Movie?): List<Movie> {
    if (movie == null) return emptyList()
    
    return when (movie.genre.lowercase()) {
        "action" -> getActionMovies().filter { it.title != movie.title }.take(4)
        "animation" -> getLoveMovies().filter { it.title != movie.title }.take(4)
        "fantasy" -> getLoveMovies().filter { it.title != movie.title }.take(4)
        "adventure" -> getLoveMovies().filter { it.title != movie.title }.take(4)
        "sci-fi" -> getScienceFictionMovies().filter { it.title != movie.title }.take(4)
        "crime" -> getScienceFictionMovies().filter { it.title != movie.title }.take(4)
        else -> getActionMovies().filter { it.title != movie.title }.take(4)
    }
}

@Composable
fun TabSection() {
    var selectedTab by remember { mutableStateOf(0) }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TabButton(
                    text = "Episodes",
                    isSelected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                TabButton(
                    text = "Suggestions",
                    isSelected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
            }
            
            // Season selector
            if (selectedTab == 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Season 1",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = "10 Episodes",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                    
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Select Season",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = text,
            color = if (isSelected) Color(0xFF00BFFF) else Color.White,
            fontSize = 16.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
        
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color(0xFF00BFFF))
            )
        }
    }
}

@Composable
fun EpisodesSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            getEpisodes().forEach { episode ->
                EpisodeCard(episode = episode)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun EpisodeCard(episode: Episode) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle episode click */ }
    ) {
        // Episode thumbnail
        Box(
            modifier = Modifier
                .size(80.dp, 60.dp)
                .background(AppDarkGray, RoundedCornerShape(8.dp)),
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
        
        // Episode details
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = episode.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            
            Text(
                text = episode.duration,
                color = Color.Gray,
                fontSize = 12.sp
            )
            
            Text(
                text = episode.description,
                color = Color.Gray,
                fontSize = 12.sp,
                maxLines = 2
            )
        }
    }
}

data class Episode(
    val title: String,
    val duration: String,
    val description: String
)

fun getEpisodes(): List<Episode> {
    return listOf(
        Episode(
            "Winter Is Coming",
            "58 min",
            "Lord Stark is torn between his family and an old friend when asked to serve at the side of King Robert Baratheon."
        ),
        Episode(
            "The Kingsroad",
            "56 min",
            "While Bran recovers from his fall, Ned takes only his daughters to King's Landing."
        ),
        Episode(
            "Lord Snow",
            "57 min",
            "Jon begins his training with the Night's Watch."
        ),
        Episode(
            "Cripples, Bastards, and Broken Things",
            "55 min",
            "Eddard investigates Jon Arryn's murder. Jon befriends Samwell Tarly."
        ),
        Episode(
            "The Wolf and the Lion",
            "54 min",
            "Catelyn has captured Tyrion and plans to bring him to her sister, Lysa Arryn."
        )
    )
} 