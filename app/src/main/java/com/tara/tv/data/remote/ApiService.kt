//package com.tara.tv.data.remote
//
//import retrofit2.Response
//import retrofit2.http.GET
//
//data class ChannelDto(
//    val id: String,
//    val title: String,
//    val logoUrl: String,
//    val category: String
//)
//
//data class ChannelsResponse(
//    val channels: List<ChannelDto>
//)
//
//interface ApiService {
//    @GET("channels")
//    suspend fun getChannels(): Response<ChannelsResponse>
//
//    // ... add other endpoints ...
//}
//
