package com.moya.login.data.api

import com.moya.login.data.api.model.login.ApiLoginRequestBody
import com.moya.login.data.api.model.login.ApiLoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiLogin {

    @POST(ApiLoginConstants.LOGIN_ENDPOINT)
    fun login(@Body apiLoginRequestBody: ApiLoginRequestBody): Call<ApiLoginResponse>
}