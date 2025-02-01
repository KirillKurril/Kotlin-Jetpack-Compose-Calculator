package com.example.calculator.di

import com.example.calculator.domain.usecase.CalculateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideCalculateUseCase(): CalculateUseCase {
        return CalculateUseCase()
    }
}