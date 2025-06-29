package com.example.aiproject.di

import com.example.aiproject.data.api.CarApiService
import com.example.aiproject.network.RetrofitHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule {
//
//    private const val BASE_URL = "https://www.carqueryapi.com/"
//
//    @Provides
//    @Singleton
//    fun provideRetrofit(): Retrofit =
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//    @Provides
//    @Singleton
//    fun provideCarQueryApiService(retrofit: Retrofit): CarApiService =
//        retrofit.create(CarApiService::class.java)
//}


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideApi(): CarApiService =
        RetrofitHolder.retrofit.create(CarApiService::class.java)
}