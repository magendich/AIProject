package com.example.aiproject.aicamera.di

import com.example.aiproject.aicamera.data.api.AIApiService
import com.example.aiproject.network.RetrofitHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideAIApi(): AIApiService =
        RetrofitHolder.openRouterRetrofit.create(AIApiService::class.java)
}