package com.example.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {
//    @Binds
//    abstract fun providesAuthRepo(repo: DataRepoImp): DataRepo
}
