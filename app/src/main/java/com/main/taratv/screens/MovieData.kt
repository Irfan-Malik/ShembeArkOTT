package com.main.taratv.screens

import com.main.taratv.R

fun getActionMovies(): List<Movie> {
    return listOf(
        Movie("21 Bridges", 2019, "Action", "1h 39m", R.drawable.bridges_21, R.drawable.bridges_21_poster),
        Movie("Mission Impossible: Fallout", 2018, "Action", "2h 27m", R.drawable.mission_impossible_fallout, R.drawable.mission_impossible_fallout_poster),
        Movie("Braven", 2018, "Action", "1h 34m", R.drawable.braven, R.drawable.braven_poster),
        Movie("Underworld: Blood Wars", 2016, "Action", "1h 31m", R.drawable.underworld_blood_wars, R.drawable.underworld_blood_wars_poster, true)
    )
}

fun getLoveMovies(): List<Movie> {
    return listOf(
        Movie("Frozen II", 2019, "Animation", "1h 43m", R.drawable.frozen_ii, R.drawable.frozen_poster),
        Movie("Maleficent", 2014, "Fantasy", "1h 37m", R.drawable.maleficent, R.drawable.maleficent_poster),
        Movie("Aladdin", 2019, "Adventure", "2h 8m", R.drawable.aladdin, R.drawable.aladdin_poster),
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

