package com.tara.tv.domain.usecase

import com.tara.tv.data.remote.ChannelDto
import com.tara.tv.domain.repository.TvRepository
import javax.inject.Inject

class GetChannelsUseCase @Inject constructor(
    private val repository: TvRepository
) {
    suspend operator fun invoke(): List<ChannelDto> = repository.fetchChannels()
}
