package com.lbg.techtest.di

import com.lbg.techtest.domain.repository.LBGRepository
import com.lbg.techtest.domain.repository.LBGRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun providesRepository(impl: LBGRepositoryImpl): LBGRepository

}