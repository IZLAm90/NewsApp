package com.example.data.repository

import com.app.data.remote.UserApi
import com.example.data.cashe.dp.NewsDB
import com.example.data.model.NewData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class NewsRepoImpl @Inject constructor(private val api:UserApi , private val room: NewsDB):NewsRepo {
    override suspend fun getHeadLines(
        apiKey: String,
        q: String?,
        sortedBy:String?
    )= flow {
        emit(api.getHeadLines( apiKey,q,sortedBy))
    }

    override suspend fun saveToDB(array: List<NewData>) {
        room.articleDao().insert(array)
    }


    override suspend fun getFromDB()=room.articleDao().getAllArticle()

    override suspend fun resetDB() =room.articleDao().clearTable()

}