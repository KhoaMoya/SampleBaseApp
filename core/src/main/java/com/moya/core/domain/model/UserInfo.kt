package com.moya.core.domain.model

data class UserInfo(
    val id: Int,
    val name: String,
    val avatar: String,
    val location: String,
    val createAt: Long,
    val bio: String
)
