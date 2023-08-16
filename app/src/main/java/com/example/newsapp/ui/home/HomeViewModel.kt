package com.example.newsapp.ui.home

import android.util.Log
import com.app.data.remote.NetWorkState
import com.example.base.BaseViewModel
import com.example.data.model.NewData
import com.example.domain.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: NewsUseCase) : BaseViewModel() {
    private val _newsFlow = MutableStateFlow<NetWorkState>(NetWorkState.Loading)
    val newsFlow = _newsFlow.asSharedFlow()
    fun getNewsHeadLines(
        page: Int,
        pageSize: Int,
        lang: String,
        apiKey: String,
        q: String?
    ) {
        executeSharedApi(_newsFlow) {
            useCase.getHeadLines(apiKey, q).onStart {
                _newsFlow.emit(NetWorkState.Loading)
//                Log.d("islam", "getNewsHeadLines: ${useCase.loadFromDb()} ")
                _newsFlow.emit(NetWorkState.Success(useCase.loadFromDb()))
            }
                .catch {
                    _newsFlow.emit(NetWorkState.Error(it))
                }
                .onCompletion {
                    _newsFlow.emit(NetWorkState.StopLoading)
                }
                .collectLatest {
//                    _newsFlow.emit(NetWorkState.Success(useCase.loadFromDb()))
                    _newsFlow.emit(NetWorkState.Success(it?.articles?.sortedByDescending { it.publishedAt  }))
                }
        }
    }
    fun clearData(){
        executeSharedApi(_newsFlow)
        { _newsFlow.emit(NetWorkState.Loading) }
    }
}