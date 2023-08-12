package com.example.newsapp.ui.home

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

    fun getNewsHeadLines(country:String,key:String){
        executeSharedApi(_newsFlow){
            useCase.getHeadLines(country,key).onStart {
                _newsFlow.emit(NetWorkState.Loading)
            }
                .catch { _newsFlow.emit(NetWorkState.Error(it)) }
                .onCompletion { _newsFlow.emit(NetWorkState.StopLoading) }
//                .collectLatest { _newsFlow.emit(NetWorkState.Success(it)) }
        }
    }
}