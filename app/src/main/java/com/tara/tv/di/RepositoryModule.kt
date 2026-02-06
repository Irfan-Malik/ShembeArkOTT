package com.tara.tv.di

import com.tara.tv.data.repository.TvRepositoryImpl
import com.tara.tv.domain.repository.TvRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTvRepository(impl: TvRepositoryImpl): TvRepository
}
