package com.moya.login.data.api

import com.moya.login.data.api.model.registration.ApiRegistrationRequestBody
import com.moya.login.data.api.model.registration.ApiRegistrationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRegistration {

    @POST("login")
    fun register(@Body apiRegistrationRequestBody: ApiRegistrationRequestBody): Call<ApiRegistrationResponse>

}