package com.main.taratv.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.main.taratv.data.MockApi
import com.main.taratv.screens.TvChannel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TvViewModel(application: Application) : AndroidViewModel(application) {

    private val _channels = MutableStateFlow<List<TvChannel>>(emptyList())
    val channels: StateFlow<List<TvChannel>> = _channels

    init {
        loadChannels()
    }

    private fun loadChannels() {
        viewModelScope.launch {
            val ctx = getApplication<Application>().applicationContext
            val fetched = MockApi.fetchChannels(ctx)
            if (fetched.isNotEmpty()) {
                _channels.value = fetched
            } else {
                _channels.value = com.main.taratv.screens.getNewsChannels() +
                        com.main.taratv.screens.getEntertainmentChannels() +
                        com.main.taratv.screens.getSportsChannels() +
                        com.main.taratv.screens.getKidsChannels()
            }
        }
    }

    fun refresh() {
        loadChannels()
    }
}

