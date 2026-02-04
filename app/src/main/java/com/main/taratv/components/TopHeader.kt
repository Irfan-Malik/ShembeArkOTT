package com.main.taratv.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.main.taratv.BroadCasting
import com.main.taratv.MenuIcon
import com.main.taratv.R
import com.main.taratv.SearchIcon

@Composable
fun TopHeader(selectedTab : Int , onMenuClick: () -> Unit, onBackClick: () -> Unit = {}, showBackButton: Boolean = false) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(horizontal = 8.dp, vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(0.dp, 16.dp, 0.dp, 0.dp),
            /*horizontalArrangement = Arrangement.SpaceBetween,*/
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button or Hamburger menu
            Icon(
                imageVector = if (showBackButton) Icons.AutoMirrored.Filled.ArrowBack else Icons.Default.Menu,
                contentDescription = if (showBackButton) "Back" else "Menu",
                tint = Color.White,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        if (showBackButton) onBackClick() else onMenuClick()
                    }
            )
            Spacer(modifier = Modifier.width(10.dp))
            when (selectedTab) {
                0 -> Image(
                    painter = painterResource(id = R.drawable.app_logo),
                    contentDescription = null,
                    modifier = Modifier.height(24.dp)
                )
                1 -> Text(
                    text = "Channels",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                2 -> Text(
                    text = "Library",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                3 -> Text(
                    text = "Radio",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                else -> Text(
                    text = "More",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            // Search icon
            when (selectedTab) {
                0 -> {
                    BroadCasting()
                    Spacer(modifier = Modifier.width(16.dp))
                    SearchIcon()
                }
                1 ->{
                    BroadCasting()
                    Spacer(modifier = Modifier.width(16.dp))
                    MenuIcon()
                    Spacer(modifier = Modifier.width(16.dp))
                    SearchIcon()
                }

                2 -> {
                    BroadCasting()
                    Spacer(modifier = Modifier.width(16.dp))
                    SearchIcon()
                }
                3 -> {
                    BroadCasting()
                    Spacer(modifier = Modifier.width(16.dp))
                    MenuIcon()
                    Spacer(modifier = Modifier.width(16.dp))
                    SearchIcon()

                }
                else -> {}
            }

        }
    }
}
