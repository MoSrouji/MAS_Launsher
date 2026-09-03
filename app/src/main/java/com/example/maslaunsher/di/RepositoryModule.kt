package com.example.maslaunsher.di

import com.example.maslaunsher.data.repository.AppRepositoryImpl
import com.example.maslaunsher.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module to provide the [AppRepository] implementation.
 * 
 * We use [@Binds] instead of [@Provides] because we are simply binding an 
 * implementation to its interface.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppRepository(
        appRepositoryImpl: AppRepositoryImpl
    ): AppRepository
}
