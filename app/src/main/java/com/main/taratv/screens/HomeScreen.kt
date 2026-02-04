package com.main.taratv.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.main.taratv.components.ChannelsSection
import com.main.taratv.components.MoviesSection
import com.main.taratv.components.TopHeaderPager

@Composable
fun HomeScreen(
    onItemClick: () -> Unit,
    onVideoPlayerClick: (String?) -> Unit,
    onChannelPlayClick: (String?) -> Unit,
    onMovieClick: (Movie) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            TopHeaderPager(onVideoPlayerClick = onVideoPlayerClick)
        }

        item {
            ChannelsSection(
                onItemClick = onItemClick,
                onPlayClick = onChannelPlayClick
            )
        }
        item {
            MoviesSection(
                "New & upcoming",
                getAllMovies(),
                onMovieClick = onMovieClick
            )
        }
        item {
            MoviesSection(
                "Recommended For You",
                getAllMovies(),
                onMovieClick = onMovieClick
            )
        }
    }
}