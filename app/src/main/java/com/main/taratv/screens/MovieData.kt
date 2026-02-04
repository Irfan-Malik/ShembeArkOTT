package com.main.taratv.screens

import com.main.taratv.R

fun getActionMovies(): List<Movie> {
    return listOf(
        Movie("Godi", 2019, "Action", "1h 39m", R.drawable.godi_movie, R.drawable.godi_movie),
        Movie("Christ In you", 2018, "Action", "2h 27m", R.drawable.christ_movie, R.drawable.christ_movie),
        Movie("Black Ashes", 2018, "Action", "1h 34m", R.drawable.ashes_movie, R.drawable.ashes_movie),
        Movie("Underworld: Blood Wars", 2016, "Action", "1h 31m", R.drawable.underworld_blood_wars, R.drawable.underworld_blood_wars_poster, true)
    )
}

fun getLoveMovies(): List<Movie> {
    return listOf(
        Movie("After Death", 2019, "Animation", "1h 43m", R.drawable.death_movie, R.drawable.death_movie),
        Movie("The Passion of Christ", 2014, "Fantasy", "1h 37m", R.drawable.passion_movie, R.drawable.passion_movie),
        Movie("Silence", 2019, "Adventure", "2h 8m", R.drawable.silence_movie, R.drawable.silence_movie),
        Movie("Aquaman", 2018, "Action", "2h 23m", R.drawable.aquaman, R.drawable.aquaman_poster, true)
    )
}

fun getScienceFictionMovies(): List<Movie> {
    return listOf(
        Movie("Alita: Battle Angel", 2019, "Sci-Fi", "2h 2m", R.drawable.alita_battle_angel, R.drawable.alita_battle_angel_poster),
        Movie("Black Widow", 2021, "Action", "2h 14m", R.drawable.black_widow_poster, R.drawable.black_widow_preview),
        Movie("Focus", 2015, "Crime", "1h 45m", R.drawable.focus, R.drawable.focus_poster),
        Movie("Braven", 2018, "Action", "1h 34m", R.drawable.braven, R.drawable.braven_poster, true)
    )
}

fun getAllMovies(): List<Movie> {
    return getActionMovies() + getLoveMovies() + getScienceFictionMovies()
}

