package com.example.di

import com.example.data.repository.NewsRepo
import com.example.data.repository.NewsRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    abstract fun providesNewRepo(repo: NewsRepoImpl): NewsRepo
}
