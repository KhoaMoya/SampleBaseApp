package com.moya.core.domain.model

data class LoggedInInfo(
    val code: Int,
    val id: Int,
    val message: String,
    val name: String,
    val token: String
)