package com.example.domain

import com.example.data.model.NewAppData
import com.example.data.model.NewData
import com.example.data.repository.NewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val repo: NewsRepo) {
    suspend fun getHeadLines(
                              apiKey: String,
                              q: String?) : Flow<NewAppData?> {
        val responce=repo.getHeadLines(apiKey, q).map {
            it.body()
        }
        responce.collect{
            repo.resetDB()
            it?.articles?.let { it1 -> repo.saveToDB(it1) }
        }
       return responce

    }

    suspend fun loadFromDb(): List<NewData> = withContext(Dispatchers.IO){
        repo.getFromDB()
    }


}