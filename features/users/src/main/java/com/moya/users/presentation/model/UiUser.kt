package com.moya.users.presentation.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.moya.core.domain.model.UserInfo
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UiUser(
    val id: Int,
    val name: String,
    val avatar: String,
    val location: String,
    val createAt: Long,
    val bio: String
) : Parcelable {
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
