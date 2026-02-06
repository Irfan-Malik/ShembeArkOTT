package com.tara.tv.data.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tara.tv.data.remote.ApiService
import com.tara.tv.data.remote.ChannelDto
import com.tara.tv.domain.repository.TvRepository
import javax.inject.Inject

//
class TvRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val context: Context,
    private val gson: Gson
) : TvRepository {
    override suspend fun fetchChannels(): List<ChannelDto> {
        return try {
            val resp = api.getChannels()
            if (resp.isSuccessful) {
                resp.body()?.channels?.takeIf { it.isNotEmpty() } ?: readChannelsFromAssets()
            } else {
                readChannelsFromAssets()
            }
        } catch (_: Throwable) {
            // fallback to local asset when anything goes wrong
            readChannelsFromAssets()
        }
    }

    private fun readChannelsFromAssets(): List<ChannelDto> {
        return try {
            context.assets.open("channels.json").use { input ->
                val json = input.bufferedReader().readText()
                val type = object : TypeToken<List<ChannelDto>>() {}.type

                // Handle two common shapes of the JSON asset:
                // 1) Top-level array: [ { ... }, { ... } ]
                // 2) Wrapper object: { "section": "...", "channels": [ { ... } ] }
                val trimmed = json.trimStart()
                if (trimmed.startsWith("[")) {
                    // top-level array
                    gson.fromJson<List<ChannelDto>>(json, type) ?: emptyList()
                } else {
                    // try to extract "channels" array from wrapper object
                    try {
                        val jsonObj = com.google.gson.JsonParser.parseString(json).asJsonObject
                        val channelsElement = jsonObj.get("channels")
                        if (channelsElement != null && channelsElement.isJsonArray) {
                            gson.fromJson(channelsElement, type) ?: emptyList()
                        } else {
                            // fallback: try to parse entire json as list
                            gson.fromJson<List<ChannelDto>>(json, type) ?: emptyList()
                        }
                    } catch (_: Throwable) {
                        // parsing failed, return empty list
                        emptyList()
                    }
                }
            }

        } catch (_: Throwable) {
            emptyList()
        }
    }
}
//
