package com.main.taratv.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.main.taratv.R

@Composable
fun NavigationDrawer(
    isOpen: Boolean,
    onClose: () -> Unit,
    onNavigationItemClick: (NavigationItem) -> Unit
) {
    if (isOpen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { onClose() }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(280.dp)
                    .background(Color(0xFF1A1A1A))
                    .padding(8.dp)
            ) {
                LazyColumn {
                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                    item {
                        UserProfileSection()
                    }

                    items(getNavigationItems()) { item ->
                        NavigationDrawerItem(
                            item = item,
                            onClick = { onNavigationItemClick(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UserProfileSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile picture placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)

        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = null,
                modifier = Modifier
                    .height(30.dp)
                    .width(170.dp)
            )
        }
    }
}

@Composable
fun NavigationDrawerItem(
    item: NavigationItem,
    onClick: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (item.hasSubItems) {
                        isExpanded = !isExpanded
                    } else {
                        onClick()
                    }
                }
                .padding(vertical = 12.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (item.iconRes != null) {
                Icon(
                    painter = painterResource(id = item.iconRes),
                    contentDescription = item.title,
                    tint = if (item.isSelected) Color(0xFF00BFFF) else Color.White,
                    modifier = Modifier.size(24.dp)
                )
            } else if (item.icon != null) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = if (item.isSelected) Color(0xFF00BFFF) else Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = item.title,
                color = if (item.isSelected) Color.White else Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp,
                fontWeight = if (item.isSelected) FontWeight.Bold else FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )

            if (item.hasSubItems) {
                Text(
                    text = if (isExpanded) "▼" else "▶",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 12.sp
                )
            }
        }

        // Selection indicator
        if (item.isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color(0xFF00BFFF))
            )
        }

        // Sub-items
        if (item.hasSubItems && isExpanded) {
            item.subItems?.forEach { subItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick() }
                        .padding(start = 40.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (subItem.iconRes != null) {
                        Icon(
                            painter = painterResource(id = subItem.iconRes),
                            contentDescription = subItem.title,
                            tint = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Icon(
                            imageVector = subItem.icon!!,
                            contentDescription = subItem.title,
                            tint = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = subItem.title,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
            }

        }
    }
}


data class NavigationItem(
    val title: String,
    val icon: ImageVector? = null,
    val iconRes: Int? = null,
    val isSelected: Boolean = false,
    val hasSubItems: Boolean = false,
    val subItems: List<SubItem>? = null
)

data class SubItem(
    val title: String,
    val icon: ImageVector? = null,
    val iconRes: Int? = null
)

fun getNavigationItems(): List<NavigationItem> {
    return listOf(
        NavigationItem("Home", iconRes = R.drawable.home, isSelected = true),
        NavigationItem("Channels", iconRes = R.drawable.channels),
        NavigationItem("Programs", iconRes = R.drawable.programs),
        NavigationItem("Radio", iconRes = R.drawable.radio),
        NavigationItem("Movies", iconRes = R.drawable.movies),
        NavigationItem("TV Series", iconRes = R.drawable.tv_shows),
        NavigationItem("My Profile", iconRes = R.drawable.profile),
        NavigationItem("About", iconRes = R.drawable.about),
        NavigationItem("Feedback", iconRes = R.drawable.fed),
        NavigationItem("Settings", iconRes = R.drawable.settings),
        NavigationItem("Terms & Conditions", iconRes = R.drawable.terms),
        NavigationItem("Help", iconRes = R.drawable.help),
    )
}

