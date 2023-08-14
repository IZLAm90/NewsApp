package com.example.data.repository

import com.example.data.model.BaseEndPointResponse
import com.example.data.model.NewAppData
import com.example.data.model.NewData
import dagger.Provides
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface NewsRepo {

        suspend fun getHeadLines( apiKey: String,q:String?= null):Flow<Response<BaseEndPointResponse<NewAppData>>>

//        suspend fun getHeadLines(page: Int,pageSize:Int,lang:String, apiKey: String,q:String?= null):
//                Flow<Response<BaseEndPointResponse<NewAppData>>>

}