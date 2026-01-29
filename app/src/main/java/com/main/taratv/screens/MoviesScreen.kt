package com.main.taratv.screens

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import com.main.taratv.R
import com.main.taratv.MovieCard
import com.main.taratv.data.MockApi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.main.taratv.viewmodel.MoviesViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun MoviesScreen(
    onMovieClick: (Movie) -> Unit = {},
    onPlayClick: () -> Unit = {}
) {
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    val moviesViewModel: MoviesViewModel = viewModel()
    val moviesState by moviesViewModel.movies.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)
    ) {
        MoviesTabRow(selectedTab = selectedTab, onTabSelected = { selectedTab = it })

        if(selectedTab == 0){
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Popular Movies",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
            )


            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(moviesState) { movie ->
                    MovieCard(
                        movie = movie,
                        onMovieClick = onMovieClick,
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Recommended For You",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
            )


            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(getScienceFictionMovies()) { movie ->
                    MovieCard(
                        movie = movie,
                        onMovieClick = onMovieClick,
                    )
                }
            }

        } else {
            TrendingNowSection(
                items = trendingItems,
                onItemClick = { series -> /* navigate to detail */ }
            )
        }

    }
}

@Composable
fun MoviesTabRow(selectedTab: Int, onTabSelected:  (Int) -> Unit) {
    val tabs = listOf(R.drawable.tabimage, R.drawable.movies_series_active)

    TabRow(
        selectedTabIndex = selectedTab,
        containerColor = Color.Transparent,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                color = Color(0xFF00BFFF)
            )
        }
    ) {
        tabs.forEachIndexed { index, resId ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                modifier = Modifier.height(64.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 8.dp)
                        .width(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if(index == 0) "Movies" else "Series",
                        color = if (selectedTab == index) Color(0xFF00BFFF) else Color.Gray,
                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier
                            .clickable { onTabSelected(index) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesScreenPreview() {
    MoviesScreen()
}
@Composable
fun TrendingNowSection(
    items: List<SeriesItem>,
    onItemClick: (SeriesItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        // Title + "Trending Now"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Trending Now",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                // Optional smaller subtitle / category
                Text(
                    text = "New Series • Drama • Recommended",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            // Optional "See All" button
            TextButton(onClick = { /* navigate to full list */ }) {
                Text("See All")
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ------------------ Horizontal carousel ------------------
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items, key = { it.id }) { item ->
                SeriesPosterCard(
                    item = item,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@Composable
fun SeriesPosterCard(
    item: SeriesItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(140.dp)               // typical poster width
            .aspectRatio(2f / 3f)        // classic vertical poster ratio
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        // Poster image (use Coil / Glide / AsyncImage)
        AsyncImage(
            model = item.imageUrl,
            contentDescription = item.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient overlay at bottom (for text readability)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.6f to Color.Transparent,
                        1f to Color.Black.copy(alpha = 0.75f)
                    )
                )
        )

        // Bottom labels / tags
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)
        ) {
            item.subtitle?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                item.label?.let { label ->
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier
                            .background(
                                Color.Black.copy(alpha = 0.6f),
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                // Optional badge like "New" / "01"
                if (item.title.contains("01")) {
                    Text(
                        text = "New",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color.Red.copy(alpha = 0.9f), CircleShape)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AsyncImage(
    model: String,
    contentDescription: String,
    modifier: Modifier,
    contentScale: ContentScale,

) {
    if(model.isNullOrEmpty()){
        Image(
            painter = painterResource(id = R.drawable.bridges_21),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    } else {
        // Here you would typically use an image loading library like Coil or Glide
        // For this example, we'll just use a placeholder
        Image(
            painter = painterResource(id = R.drawable.bridges_21),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrendingNowPagerSection(items: List<SeriesItem>) {
    Column {
        // same title as above...

        val pagerState = rememberPagerState(pageCount = { items.size })

        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 48.dp),   // → peek next/prev
            pageSpacing = 12.dp
        ) { index ->
            val item = items[index]
//            SeriesPosterCard(
//                item = item,
//                onClick = { onItemClick(item) }
//            )
        }
    }
}
val trendingItems = listOf(
    SeriesItem(1, "New Headway 01", "Short Soulfull Drama Serial", "https://...", "English Subtitle"),
    SeriesItem(2, "For Young Lee", "New Drama Series", "https://...", "For Young"),
    // ...
)



data class SeriesItem(
    val id: Int,
    val title: String,           // e.g. "New Headway 01"
    val subtitle: String?,       // e.g. "Short Soulfull Drama Serial"
    val imageUrl: String,        // poster URL
    val label: String? = null    // e.g. "For Young", "English Subtitle"
)
