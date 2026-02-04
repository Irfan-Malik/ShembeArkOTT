package com.tara.tv.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tara.tv.data.remote.ChannelDto
import com.tara.tv.domain.usecase.GetChannelsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getChannelsUseCase: GetChannelsUseCase
) : ViewModel() {

    private val _channels = MutableStateFlow<List<ChannelDto>>(emptyList())
    val channels: StateFlow<List<ChannelDto>> = _channels

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadChannels()
    }

    fun loadChannels() {
        viewModelScope.launch {
            _loading.value = true
            _channels.value = getChannelsUseCase()
            _loading.value = false
        }
    }
}
