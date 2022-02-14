package com.moya.login.data.api.model.registration

import com.moya.core.AppConstants
import com.moya.core.data.api.model.ApiModel
import com.moya.core.domain.model.LoggedInInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiRegistrationResponse(
    @Json(name = "code") val code: Int? = null,
    @Json(name = "message") val message: String? = null,
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "accessToken") val token: String? = null
) : ApiModel<LoggedInInfo>() {

    override fun toDomainModel(): LoggedInInfo {
        return LoggedInInfo(
            code = code ?: AppConstants.DEFAULT_INT,
            id = id ?: AppConstants.DEFAULT_INT,
            message = message ?: AppConstants.DEFAULT_STRING,
            name = name ?: AppConstants.DEFAULT_STRING,
            token = token ?: AppConstants.DEFAULT_STRING,
        )
    }
}