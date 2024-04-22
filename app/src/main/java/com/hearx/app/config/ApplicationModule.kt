package com.hearx.app.config

import com.hearx.app.api.RetroFitImplementation
import com.hearx.app.repositories.TestRepository
import com.hearx.app.viewModels.BaseViewModel
import com.hearx.app.viewModels.MainViewModel
import com.hearx.app.viewModels.ResultsViewModel
import com.hearx.app.viewModels.TestViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {
    @Singleton
    @Provides
    fun provideBaseViewModel() = BaseViewModel()
    @Singleton
    @Provides
    fun provideMainViewModel() = MainViewModel()

    @Singleton
    @Provides
    fun provideTestViewModel() = TestViewModel()

    @Singleton
    @Provides
    fun provideResultsViewModel() = ResultsViewModel()

    @Singleton
    @Provides
    fun provideTestRepository() = TestRepository(provideRetroFitImplementation())

    @Singleton
    @Provides
    fun provideRetroFitImplementation() = RetroFitImplementation()
}