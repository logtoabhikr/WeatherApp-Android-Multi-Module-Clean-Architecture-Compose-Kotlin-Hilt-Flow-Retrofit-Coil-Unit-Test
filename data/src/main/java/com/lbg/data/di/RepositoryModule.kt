package com.lbg.data.di

import com.lbg.data.repository.LBGRepositoryImpl
import com.lbg.domain.repository.LBGRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun binLBGRepository(lbgRepositoryImpl: LBGRepositoryImpl): LBGRepository
}