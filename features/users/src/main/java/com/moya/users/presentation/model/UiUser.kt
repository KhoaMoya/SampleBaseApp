package com.moya.users.presentation.model

import com.moya.core.domain.model.UserInfo

data class UiUser(
    val id: Int,
    val name: String,
    val avatar: String,
    val location: String,
    val createAt: Long,
    val bio: String
) {
    companion object {
        fun fromDomain(userInfo: UserInfo): UiUser {
            return UiUser(
                id = userInfo.id,
                name = userInfo.name,
                avatar = userInfo.avatar,
                location = userInfo.location,
                createAt = userInfo.createAt,
                bio = userInfo.bio
            )
        }
    }
}
