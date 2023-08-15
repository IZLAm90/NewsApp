package com.example.domain

import android.util.Log
import com.app.data.remote.Constants
import com.example.data.model.BaseEndPointResponse
import com.example.data.model.BaseResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.transform
import retrofit2.Response

inline fun <T, R> Flow<Response<BaseResponse<T>>>.transformResponseData(
    crossinline onSuccess: suspend FlowCollector<R>.(T) -> Unit
): Flow<R> {
    return transform { response ->
        Log.d("islam", "transformResponseData errorBody:  ${response.errorBody()}")
        Log.d("islam", "transformResponseData  body :  ${response.body()}")

        when {
            response.isSuccessful && response.body() != null && response.code() in 200..299 -> {
                Log.d("islam", "transformResponseData 200 : ${response.body()?.data} ")
                onSuccess(response.body()!!.data!!)
            }
            response.isSuccessful && response.body() != null && response.body()!!.code !in 200..299 -> {
                Log.d("islam", "transformResponseData : ${response.body()} ")
                onSuccess(response.body()!!.data!! as T)
            }
            response.code() == 401 -> {
                Log.d("islam", "transformResponseData : ${response.code()} ")
                throw Throwable(Constants.ERROR_API.UNAUTHRIZED)
            }
            response.code() in 401..499 && response.errorBody() == null ->
                throw Throwable(Constants.ERROR_API.BAD_REQUEST)
            response.code() in 500..599 -> throw Throwable(Constants.ERROR_API.SERVER_ERROR)
            else -> throw Exception()
        }
    }
}


