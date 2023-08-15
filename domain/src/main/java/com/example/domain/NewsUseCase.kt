package com.example.domain

import com.example.data.repository.NewsRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsUseCase @Inject constructor(private val repo: NewsRepo) {
    suspend fun getHeadLines(
                              apiKey: String,
                              q: String?)=
        repo.getHeadLines(apiKey,q).map { it.body() }

}