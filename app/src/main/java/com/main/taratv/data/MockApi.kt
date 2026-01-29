package com.main.taratv.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.main.taratv.R
import com.main.taratv.screens.Movie
import com.main.taratv.screens.TvChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

internal data class MovieDto(
    val title: String,
    val year: Int,
    val genre: String,
    val duration: String,
    val imageName: String?,
    val posterName: String?,
    val isPremium: Boolean? = false
)

internal data class ChannelDto(
    val name: String,
    val number: Int,
    val category: String? = ""
)

object MockApi {

    private val gson = Gson()

    suspend fun fetchMovies(context: Context): List<Movie> = withContext(Dispatchers.IO) {
        try {
            val json = context.assets.open("movies.json").bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<MovieDto>>() {}.type
            val dtos: List<MovieDto> = gson.fromJson(json, listType)
            return@withContext dtos.map { dto ->
                val imageRes = resolveDrawable(context, dto.imageName) ?: R.drawable.movie_img
                val posterRes = resolveDrawable(context, dto.posterName) ?: R.drawable.movie_img
                Movie(
                    title = dto.title,
                    year = dto.year,
                    genre = dto.genre,
                    duration = dto.duration,
                    imageResource = imageRes,
                    imagePosterResource = posterRes,
                    isPremium = dto.isPremium ?: false
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return@withContext emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext emptyList()
        }
    }

    suspend fun fetchChannels(context: Context): List<TvChannel> = withContext(Dispatchers.IO) {
        try {
            val json = context.assets.open("channels.json").bufferedReader().use { it.readText() }
            val listType = object : TypeToken<List<ChannelDto>>() {}.type
            val dtos: List<ChannelDto> = gson.fromJson(json, listType)
            return@withContext dtos.map { dto ->
                TvChannel(
                    name = dto.name,
                    number = dto.number,
                    category = dto.category ?: ""
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return@withContext emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext emptyList()
        }
    }

    private fun resolveDrawable(context: Context, name: String?): Int? {
        if (name.isNullOrBlank()) return null
        val res = context.resources.getIdentifier(name, "drawable", context.packageName)
        return if (res != 0) res else null
    }
}

