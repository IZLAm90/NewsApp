package com.app.data.remote

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface UserApi {
    @GET("top-headlines")
    suspend fun getHeadLines(
        @Query("country")  country :String,
        @Query("apiKey")  apiKey :String
    ):Response<Any>
}