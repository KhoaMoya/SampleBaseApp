package com.moya.core.domain.model

data class LoggedInInfo(
    val code: Int,
    val message: String,
    val name: String,
    val email: String,
    val token: String
)