package com.moya.login.data.api.model.login

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiLoginRequestBody(
    @Json(name = "email") val email: String,
    @Json(name = "password") val password: String
)
