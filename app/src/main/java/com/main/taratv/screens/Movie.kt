package com.main.taratv.screens

// Shared model for movies used across the project
data class Movie(
    val title: String,
    val year: Int,
    val genre: String,
    val duration: String,
    val imageResource: Int,
    val imagePosterResource: Int,
    val isPremium: Boolean = false
)

