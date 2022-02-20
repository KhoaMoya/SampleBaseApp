package com.moya.edit.data.api.mode

import com.moya.core.domain.model.UserInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiEditUserRequest(
    @Json(name = "name") val name: String? = null,
    @Json(name = "avatar") val avatar: String? = null,
    @Json(name = "location") val location: String? = null,
    @Json(name = "bio") val bio: String? = null
) {

    companion object {
        fun fromDomainModel(userInfo: UserInfo): ApiEditUserRequest {
            return ApiEditUserRequest(
                name = userInfo.name,
                avatar = userInfo.avatar,
                location = userInfo.location,
                bio = userInfo.bio
            )
        }
    }
}
