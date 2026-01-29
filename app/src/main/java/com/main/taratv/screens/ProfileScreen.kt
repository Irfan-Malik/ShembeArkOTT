package com.main.taratv.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.main.taratv.R

@Composable
fun ProfileScreen() {
            LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {

//        item {
//            UserProfileSection()
//        }
//
//        item {
//            SubscriptionSection()
//        }
        
//        item {
//            SettingsSection()
//        }
        
        item {
//            SupportSection()
            DrawerScreen()
        }
    }
}
@Composable
fun DrawerMenu(items: List<DrawerItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        items.forEach { item ->
            DrawerMenuItem(item)
        }
    }
}


data class DrawerItem(
    val title: String,
    val icon: ImageVector? = null,
    val iconRes: Int? = null
)

@Composable
fun DrawerMenuItem(item: DrawerItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ }
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (item.iconRes != null) {
            Icon(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.title,
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        } else if (item.icon != null) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = item.title,
            color = Color.White,
            fontSize = 16.sp
        )
    }

    HorizontalDivider(color = Color.DarkGray, thickness = 0.5.dp)
}


@Composable
fun DrawerScreen() {
    val items = listOf(
        DrawerItem("Podcasts", iconRes = R.drawable.podcast),
        DrawerItem("Academy", iconRes = R.drawable.academy),
        DrawerItem("e-Books", iconRes = R.drawable.ebooks),
        DrawerItem("Foundation", iconRes = R.drawable.foundation),
        DrawerItem("Shopping", iconRes = R.drawable.shopping),
        DrawerItem("Finance", iconRes = R.drawable.finance)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color(0xFF1A1A1A)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            // Profile Image (placeholder)


            Text(
                text = "Bilal Hassan",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Sydney, Australia",
                color = Color.Gray,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF00BFFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }


            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(color = Color.DarkGray)

            DrawerMenu(items)
        }
    }
}


@Composable
fun UserProfileSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile picture
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color(0xFF00BFFF), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "John Doe",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = "john.doe@example.com",
            fontSize = 14.sp,
            color = Color.Gray
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Premium Member",
            fontSize = 12.sp,
                            color = Color(0xFF00BFFF),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SubscriptionSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Subscription",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Premium Plan",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Unlimited access to all content",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                    
                    Text(
                        text = "$9.99/month",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00BFFF)
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Next billing: March 15, 2024",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun SettingsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Settings",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        SettingsItem(
            icon = Icons.Default.Notifications,
            title = "Notifications",
            subtitle = "Manage notification preferences"
        )
        
        SettingsItem(
            icon = Icons.Default.Settings,
            title = "Language",
            subtitle = "English"
        )
        
        SettingsItem(
            icon = Icons.Default.Settings,
            title = "Dark Mode",
            subtitle = "Off"
        )
        
        SettingsItem(
            icon = Icons.Default.Settings,
            title = "Download Quality",
            subtitle = "HD"
        )
        
        SettingsItem(
            icon = Icons.Default.Settings,
            title = "Privacy & Security",
            subtitle = "Manage your privacy settings"
        )
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
     title: String,
     subtitle: String
 ) {
     Row(
         modifier = Modifier
             .fillMaxWidth()
             .clickable { /* Handle settings click */ }
             .padding(vertical = 12.dp),
         verticalAlignment = Alignment.CenterVertically
     ) {
         Icon(
             imageVector = icon,
             contentDescription = title,
             tint = Color.Gray,
             modifier = Modifier.size(24.dp)
         )

         Spacer(modifier = Modifier.width(16.dp))

         Column(
             modifier = Modifier.weight(1f)
         ) {
             Text(
                 text = title,
                 fontSize = 16.sp,
                 fontWeight = FontWeight.Medium
             )
             Text(
                 text = subtitle,
                 fontSize = 14.sp,
                 color = Color.Gray
             )
         }

         Icon(
             imageVector = Icons.Default.ArrowForward,
             contentDescription = "Navigate",
             tint = Color.Gray,
             modifier = Modifier.size(20.dp)
         )
     }
 }

@Composable
fun SupportSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Support",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        SettingsItem(
            icon = Icons.Default.Settings,
            title = "Help Center",
            subtitle = "Get help and support"
        )
        
        SettingsItem(
            icon = Icons.Default.Email,
            title = "Contact Us",
            subtitle = "Send us a message"
        )
        
        SettingsItem(
            icon = Icons.Default.Star,
            title = "Rate App",
            subtitle = "Rate us on Google Play"
        )
        
        SettingsItem(
            icon = Icons.Default.Share,
            title = "Share App",
            subtitle = "Share with friends"
        )
        
        SettingsItem(
            icon = Icons.Default.Info,
            title = "About",
            subtitle = "Version 1.0.0"
        )
    }
}
