package com.example.android.guideviewer.core.di

import com.example.android.guideviewer.core.network.GuideApi
import com.example.android.guideviewer.data.repository.GuideRepository
import com.example.android.guideviewer.data.repository.GuideRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideGuideRepository(
        guideApi: GuideApi
    ): GuideRepository = GuideRepositoryImpl(guideApi)
}