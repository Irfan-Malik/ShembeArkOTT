package com.main.taratv.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                    
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileSection()
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
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFF424242)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = "Nicole Elison",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "Maidenhead, United Kingdom",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 14.sp
        )
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
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = if (item.isSelected) Color(0xFF00BFFF) else Color.White,
                modifier = Modifier.size(24.dp)
            )
            
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
                    Icon(
                        imageVector = subItem.icon,
                        contentDescription = subItem.title,
                        tint = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp)
                    )
                    
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

@Composable
fun ProfileSection() {
    Column {
        Text(
            text = "My Profile",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        getProfileItems().forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Handle profile item click */ }
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Text(
                    text = item.title,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
            }
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val isSelected: Boolean = false,
    val hasSubItems: Boolean = false,
    val subItems: List<SubItem>? = null
)

data class SubItem(
    val title: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

fun getNavigationItems(): List<NavigationItem> {
    return listOf(
        NavigationItem("Home", Icons.Default.Home, isSelected = true),
        NavigationItem("Channels", Icons.Default.PlayArrow),
        NavigationItem("Programs", Icons.Default.List),
        NavigationItem("Radio", Icons.Default.PlayArrow),
        NavigationItem("Movies", Icons.Default.PlayArrow),
        NavigationItem("TV Series", Icons.Default.PlayArrow)
    )
}

fun getProfileItems(): List<SubItem> {
    return listOf(
        SubItem("Packages", Icons.Default.List),
        SubItem("Settings", Icons.Default.Settings),
        SubItem("About", Icons.Default.Info),
        SubItem("Feedback", Icons.Default.Star),
        SubItem("Terms & Conditions", Icons.Default.List)
    )
} 