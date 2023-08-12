package com.example.data.repository

import dagger.Provides
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface NewsRepo {

        suspend fun getHeadLines(country :String, apiKey :String):Flow<Response<Any>>

}