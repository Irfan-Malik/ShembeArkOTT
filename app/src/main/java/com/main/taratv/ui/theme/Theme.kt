package com.main.taratv.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AppRed,
    secondary = AppDarkGray,
    tertiary = AppLightGray,
    background = AppBlack,
    surface = AppBlack,
    onPrimary = AppWhite,
    onSecondary = AppWhite,
    onTertiary = AppWhite,
    onBackground = AppWhite,
    onSurface = AppWhite
)

private val LightColorScheme = lightColorScheme(
    primary = AppRed,
    secondary = AppDarkGray,
    tertiary = AppLightGray,
    background = AppBlack,
    surface = AppBlack,
    onPrimary = AppWhite,
    onSecondary = AppWhite,
    onTertiary = AppWhite,
    onBackground = AppWhite,
    onSurface = AppWhite
)

@Composable
fun TaraTVTheme(
    darkTheme: Boolean = true, // Force dark theme for forjiflix
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disable dynamic color to maintain our custom theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}