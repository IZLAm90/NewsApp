package com.example.data.model

import com.google.gson.annotations.SerializedName

data class BaseEndPointResponse<T>(
    @SerializedName("code") var code: Int? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("data") var data: T? = null
)
data class BaseResponse<T>(
    val code: Int? = null,
    val msg: String? = "",
    val data: T? = null
)
