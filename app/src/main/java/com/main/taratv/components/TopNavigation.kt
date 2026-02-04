package com.main.taratv.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.main.taratv.R
import com.main.taratv.ui.theme.AppBlack
import com.main.taratv.ui.theme.CustomBlue

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