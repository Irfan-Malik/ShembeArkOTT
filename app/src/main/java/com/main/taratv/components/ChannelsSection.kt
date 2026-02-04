package com.main.taratv.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.main.taratv.Channel
import com.main.taratv.getChannelImageResource
import com.main.taratv.getFeaturedChannels
import com.main.taratv.ui.theme.AppDarkGray


@Composable
fun ChannelsSection(
    onItemClick: () -> Unit,
    onPlayClick: (String?) -> Unit
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
                    onClick = onItemClick,
                    onPlayClick = { url -> onPlayClick(url) }
                )
            }
        }
    }
}
@Composable
fun ChannelCard(
    channel: Channel,
    onClick: () -> Unit,
    onPlayClick: (String?) -> Unit
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
                .clickable { onPlayClick(null) }
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