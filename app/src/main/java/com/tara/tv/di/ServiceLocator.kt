//package com.tara.tv.di
//
//import com.google.gson.Gson
//import com.google.gson.GsonBuilder
//import com.tara.tv.data.remote.ApiService
//import com.tara.tv.data.repository.TvRepositoryImpl
//import com.tara.tv.domain.repository.TvRepository
//import com.tara.tv.domain.usecase.GetChannelsUseCase
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.tara.tv.presentation.home.HomeViewModel
//
//object ServiceLocator {
//
//    private const val BASE_URL = "https://api.example.com/"
//
//    // Provide Gson
//    fun provideGson(): Gson = GsonBuilder().create()
//
//    // Provide OkHttp
//    fun provideLogging(): HttpLoggingInterceptor {
//        val logger = HttpLoggingInterceptor()
//        logger.level = HttpLoggingInterceptor.Level.BASIC
//        return logger
//    }
//
//    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
//        .addInterceptor(provideLogging())
//        .callTimeout(30, TimeUnit.SECONDS)
//        .build()
//
//    // Provide Retrofit
//    fun provideRetrofit(): Retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .client(provideOkHttp())
//        .addConverterFactory(GsonConverterFactory.create(provideGson()))
//        .build()
//
//    fun provideApiService(): ApiService = provideRetrofit().create(ApiService::class.java)
//
//    // Repository
//    fun provideTvRepository(): TvRepository = TvRepositoryImpl(provideApiService())
//
//    // UseCase
//    fun provideGetChannelsUseCase(): GetChannelsUseCase = GetChannelsUseCase(provideTvRepository())
//
//    // ViewModel factory
//    fun provideHomeViewModelFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
//                @Suppress("UNCHECKED_CAST")
//                return HomeViewModel(provideGetChannelsUseCase()) as T
//            }
//            throw IllegalArgumentException("Unknown ViewModel class")
//        }
//    }
//}
//
