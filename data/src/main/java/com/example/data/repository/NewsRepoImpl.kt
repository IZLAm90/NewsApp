package com.example.data.repository

import com.app.data.remote.UserApi
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsRepoImpl @Inject constructor(private val api:UserApi):NewsRepo {
    override suspend fun getHeadLines(country: String, apiKey: String) = flow { emit(api.getHeadLines(apiKey))  }
}