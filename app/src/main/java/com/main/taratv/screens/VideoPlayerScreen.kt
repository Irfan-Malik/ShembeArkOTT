package com.main.taratv.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import android.net.Uri
import android.widget.VideoView
import android.widget.MediaController
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.delay
import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.SurfaceView
import android.view.View
import android.view.Gravity
import android.widget.FrameLayout.LayoutParams
import androidx.activity.compose.BackHandler

@Composable
fun VideoPlayerScreen(
    onBackClick: () -> Unit,
    onFullscreenClick: () -> Unit = {},
    videoUrl: String = "https://stream.mux.com/5xJMCNSfOdyLUfN301IHHU02YmWBvV64jB00jdHyrwiMcY.m3u8"
) {
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    
    // Handle back button press
    BackHandler {
        onBackClick()
    }
    
    // Force landscape orientation
    LaunchedEffect(Unit) {
        val activity = context as? Activity
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
    
    // Reset orientation when leaving
    DisposableEffect(Unit) {
        onDispose {
            val activity = context as? Activity
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
    
    // Handle lifecycle events
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    // Pause video when app goes to background
                }
                Lifecycle.Event.ON_RESUME -> {
                    // Resume video when app comes to foreground
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Full Screen Video Player
        AndroidView(
            factory = { context ->
                FrameLayout(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    
                    val videoView = VideoView(context).apply {
                        layoutParams = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            Gravity.CENTER
                        )

                    }
                    
                    val mediaController = MediaController(context).apply {
                        setAnchorView(videoView)
                        setMediaPlayer(videoView)
                    }
                    
                    videoView.setMediaController(mediaController)
                    
                    // Set up video view listeners
                    videoView.setOnPreparedListener { mp ->
                        isLoading = false
                        
                        // Force video to fill the entire view with no black bars
                        val videoWidth = mp.videoWidth
                        val videoHeight = mp.videoHeight
                        val viewWidth = width
                        val viewHeight = height
                        
                        if (videoWidth > 0 && videoHeight > 0 && viewWidth > 0 && viewHeight > 0) {
                            val videoAspectRatio = videoWidth.toFloat() / videoHeight.toFloat()
                            val viewAspectRatio = viewWidth.toFloat() / viewHeight.toFloat()
                            
                            // Always scale to fill the entire screen, cropping as needed
                            if (videoAspectRatio > viewAspectRatio) {
                                // Video is wider than view, scale to fit height and crop width
                                val scale = viewHeight.toFloat() / videoHeight.toFloat()
                                val scaledWidth = (videoWidth * scale).toInt()
                                val xOffset = (scaledWidth - viewWidth) / 2
                                
                                videoView.layoutParams = FrameLayout.LayoutParams(
                                    scaledWidth,
                                    viewHeight,
                                    Gravity.CENTER
                                ).apply {
                                    leftMargin = -xOffset
                                }
                            } else {
                                // Video is taller than view, scale to fit width and crop height
                                val scale = viewWidth.toFloat() / videoWidth.toFloat()
                                val scaledHeight = (videoHeight * scale).toInt()
                                val yOffset = (scaledHeight - viewHeight) / 2
                                
                                videoView.layoutParams = FrameLayout.LayoutParams(
                                    viewWidth,
                                    scaledHeight,
                                    Gravity.CENTER_VERTICAL
                                ).apply {
                                    topMargin = -yOffset
                                }
                            }
                            
                            // Force the layout to update immediately
                            videoView.requestLayout()
                        }
                        
                        mp.setOnSeekCompleteListener { 
                            // Handle seek completion
                        }
                    }
                    
                    videoView.setOnErrorListener { _, what, extra ->
                        hasError = true
                        isLoading = false
                        true
                    }
                    
                    videoView.setOnInfoListener { _, what, extra ->
                        when (what) {
                            android.media.MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                                isLoading = true
                            }
                            android.media.MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                                isLoading = false
                            }
                        }
                        true
                    }
                    
                    addView(videoView)
                }
            },
            update = { frameLayout ->
                val videoView = frameLayout.getChildAt(0) as VideoView
                try {
                    val uri = Uri.parse(videoUrl)
                    videoView.setVideoURI(uri)
                    videoView.start()
                } catch (e: Exception) {
                    hasError = true
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        
        // Loading Indicator
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        
        // Error State
        if (hasError) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Failed to load video",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Please check your internet connection",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { 
                            hasError = false
                            isLoading = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00BFFF)
                        )
                    ) {
                        Text("Retry")
                    }
                }
            }
        }
        
        // Minimal Back Button (only when needed)
        if (!isLoading && !hasError) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        .align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VideoPlayerControls(
    isPlaying: Boolean,
    onPlayPause: () -> Unit,
    onSeek: (Float) -> Unit,
    currentTime: Long,
    totalDuration: Long,
    onBackClick: () -> Unit,
    onFullscreenClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
    ) {
        // Top controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            IconButton(
                onClick = onFullscreenClick,
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Fullscreen",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
        
        // Center play/pause button
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            IconButton(
                onClick = onPlayPause,
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.Black.copy(alpha = 0.5f), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        
        // Bottom controls
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            // Progress slider
            Slider(
                value = if (totalDuration > 0) (currentTime.toFloat() / totalDuration.toFloat()) else 0f,
                onValueChange = onSeek,
                modifier = Modifier.fillMaxWidth(),
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFF00BFFF),
                    activeTrackColor = Color(0xFF00BFFF),
                    inactiveTrackColor = Color.White.copy(alpha = 0.3f)
                )
            )
            
            // Time display
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatTime(currentTime),
                    color = Color.White,
                    fontSize = 12.sp
                )
                Text(
                    text = formatTime(totalDuration),
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}

private fun formatTime(timeMs: Long): String {
    val totalSeconds = timeMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
} 