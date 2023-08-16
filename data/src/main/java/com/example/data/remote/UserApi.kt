package com.app.data.remote

import com.example.data.model.BaseEndPointResponse
import com.example.data.model.BaseResponse
import com.example.data.model.NewAppData
import com.example.data.model.NewData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface UserApi {
    @GET("everything")
    suspend fun getHeadLines(
        @Query("apiKey") apiKey: String,
        @Query("q") searchValue: String?= null,
        @Query("sortBy") sortBy: String?=null,
    ):Response<NewAppData>
    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query("country")  country:String,
        @Query("apiKey")  apiKey:String
    ):Response<BaseEndPointResponse<NewAppData>>
}