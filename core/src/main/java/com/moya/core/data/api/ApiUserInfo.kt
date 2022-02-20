package com.moya.core.data.api

import com.moya.core.data.api.model.ApiUserInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiUserInfo {

    @GET("users/{id}")
    fun getUserById(
        @Path("id") id: Int
    ): Call<ApiUserInfoResponse>
}