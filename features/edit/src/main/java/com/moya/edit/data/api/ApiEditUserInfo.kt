package com.moya.edit.data.api

import com.moya.edit.data.api.mode.ApiEditUserRequest
import com.moya.edit.data.api.mode.ApiEditUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiEditUserInfo {

    @PUT("users/{id}")
    fun updateUserInfo(
        @Path("id") userId: Int,
        @Body info: ApiEditUserRequest
    ): Call<ApiEditUserResponse>
}