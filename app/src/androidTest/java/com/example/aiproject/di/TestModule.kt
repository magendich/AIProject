package com.example.aiproject.di

import com.example.aiproject.domain.usecase.GetCarDetailedInfoUseCase
import com.example.aiproject.domain.usecase.GetCarModelsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent

//@Module
//@TestInstallIn(
//    components = [SingletonComponent::class],
//    replaces = [ProductionUseCaseModule::class]
//)
//object TestModule {
//
//    @Provides
//    fun provideGetCarModelsUseCase(): GetCarModelsUseCase = FakeGetCarModelsUseCase()
//
//    @Provides
//    fun provideGetCarDetailedInfoUseCase(): GetCarDetailedInfoUseCase = FakeGetCarDetailedInfoUseCase()
//}