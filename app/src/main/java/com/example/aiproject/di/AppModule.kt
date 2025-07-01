package com.example.aiproject.di

import com.example.aiproject.aicamera.data.repository.AIRepositoryImpl
import com.example.aiproject.aicamera.domain.repository.AIRepository
import com.example.aiproject.data.repository.CarRepositoryImpl
import com.example.aiproject.domain.repository.CarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindCarRepository(
        carRepositoryImpl: CarRepositoryImpl
    ): CarRepository

    @Binds
    @Singleton
    abstract fun bindAIRepository(
        aiRepositoryImpl: AIRepositoryImpl
    ): AIRepository
}
