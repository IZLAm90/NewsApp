package com.example.data.repository

import com.app.data.remote.UserApi
import com.example.data.model.NewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepoImpl @Inject constructor(private val api:UserApi):NewsRepo {
    override suspend fun getHeadLines(
        apiKey: String,
        q: String?
    )= flow {
        emit(api.getHeadLines( apiKey,q))
//        emit(api.getTopHeadLines( "EG",apiKey))
    }
//            Flow<Response<List<NewData>>> {
//       return flow {
//           emit(api.getHeadLines( apiKey))
//           emit(api.getTopHeadLines( "EG",apiKey))
//           emit(api.getHeadLines(page,pageSize,lang, apiKey,q?:"bitcoin"))
//       }
//    }


}