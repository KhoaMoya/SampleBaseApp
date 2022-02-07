package com.moya.login.data.api

import com.moya.core.domain.model.LoggedInInfo
import com.moya.login.data.api.model.login.ApiLoginRequestBody
import com.moya.login.data.api.model.registration.ApiRegistrationRequestBody
import com.moya.login.data.api.model.registration.ApiRegistrationResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRegistration {

    @POST(ApiLoginConstants.REGISTRATION_ENDPOINT)
    fun register(@Body apiRegistrationRequestBody: ApiRegistrationRequestBody): Call<ApiRegistrationResponse>

    @POST("")
    fun login(@Body loginBody: ApiLoginRequestBody): Call<LoggedInInfo>
}