package com.example.newsapp.ui.home

import android.util.Log
import com.app.data.remote.NetWorkState
import com.example.base.BaseViewModel
import com.example.domain.NewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase:NewsUseCase) : BaseViewModel()  {
    private val _newsFlow = MutableStateFlow<NetWorkState>(NetWorkState.Loading)
    val prayersFlow = _newsFlow.asSharedFlow()

    fun getNewsHeadLines( page: Int,
                          pageSize: Int,
                          lang: String,
                          apiKey: String,
                          q: String?){
        executeSharedApi(_newsFlow){
            useCase.getHeadLines(apiKey,q).onStart {
                Log.d("islam", "getNewsHeadLines : Loading ")
                _newsFlow.emit(NetWorkState.Loading)
            }
                .catch {
                    Log.d("islam", "getNewsHeadLines : ${it} ")
                    _newsFlow.emit(NetWorkState.Error(it)) }
                .onCompletion {
                    Log.d("islam", "getNewsHeadLines : ${NetWorkState.StopLoading} ")
                    _newsFlow.emit(NetWorkState.StopLoading) }
                .collectLatest {
                    Log.d("islam", "getNewsHeadLines: ${it.articles} ")
                    _newsFlow.emit(NetWorkState.Success(it.articles))
                                    }
        }
    }
}