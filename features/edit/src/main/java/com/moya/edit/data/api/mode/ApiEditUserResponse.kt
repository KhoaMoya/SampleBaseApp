package com.moya.edit.data.api.mode

import com.moya.core.AppConstants
import com.moya.core.data.api.model.ApiModel
import com.moya.core.domain.model.UserInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

@JsonClass(generateAdapter = true)
data class ApiEditUserResponse(
    @Json(name = "id") val id: Int? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "avatar") val avatar: String? = null,
    @Json(name = "location") val location: String? = null,
    @Json(name = "createAt") val createAt: String? = null,
    @Json(name = "bio") val bio: String? = null
) : ApiModel<UserInfo>() {

    override fun toDomainModel(): UserInfo {
        val createTime = if (createAt != null) {
            try {
                val dateFormat =
                    SimpleDateFormat("yyyy-MM-ddTHH:mm:ss.SSSZ", Locale.getDefault())
                dateFormat.parse(createAt)?.time ?: AppConstants.DEFAULT_LONG
            } catch (ex: ParseException) {
                AppConstants.DEFAULT_LONG
            }
        } else {
            AppConstants.DEFAULT_LONG
        }

        return UserInfo(
            id = id ?: AppConstants.DEFAULT_INT,
            name = name ?: AppConstants.DEFAULT_STRING,
            avatar = avatar ?: AppConstants.DEFAULT_STRING,
            location = location ?: AppConstants.DEFAULT_STRING,
            createAt = createTime,
            bio = bio ?: AppConstants.DEFAULT_STRING
        )
    }
}
