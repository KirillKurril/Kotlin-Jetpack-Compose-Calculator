package com.example.calculator.di

import com.example.calculator.data.api.CalculationsFetchingService
import com.example.calculator.data.repository.CalculationFireBaseRepository
import com.example.calculator.domain.repository.CalculationRepository
import com.example.calculator.domain.usecase.GetCalculationsUseCase
import com.example.calculator.domain.usecase.SaveCalculationsUseCase
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
    fun provideGetCalculationsUseCase(
        repository: CalculationRepository
    ): GetCalculationsUseCase {
        return GetCalculationsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveCalculationsUseCase(
        repository: CalculationRepository
    ): SaveCalculationsUseCase {
        return SaveCalculationsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCalculationFetchingService(): CalculationsFetchingService {
        return CalculationsFetchingService()
    }

    @Provides
    @Singleton
    fun provideCalculationRepository(
        fetchingService: CalculationsFetchingService
    ): CalculationRepository {
        return CalculationFireBaseRepository(fetchingService) // Предоставляем CalculationRepository
    }

    @Provides
    @Singleton
    fun provideCalculationFireBaseRepository(
        fetchingService: CalculationsFetchingService
    ): CalculationFireBaseRepository {
        return CalculationFireBaseRepository(fetchingService)
    }
}