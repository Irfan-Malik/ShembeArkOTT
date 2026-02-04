package com.tara.tv.data.repository

import com.tara.tv.data.remote.ApiService
import com.tara.tv.data.remote.ChannelDto
import com.tara.tv.domain.repository.TvRepository

//
class TvRepositoryImpl constructor(
    private val api: ApiService
) : TvRepository {
    override suspend fun fetchChannels(): List<ChannelDto> {
        val resp = api.getChannels()
        return if (resp.isSuccessful) {
            resp.body()?.channels ?: emptyList()
        } else {
            emptyList()
        }
    }
}
//
