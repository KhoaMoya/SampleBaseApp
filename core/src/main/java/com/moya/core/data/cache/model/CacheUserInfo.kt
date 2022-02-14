package com.moya.core.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moya.core.domain.model.UserInfo

@Entity(tableName = "users")
data class CacheUserInfo(
    @PrimaryKey
    val id: Int,
    val name: String,
    val avatar: String,
    val location: String,
    val createAt: Long,
    val bio: String
) : CacheModel<UserInfo>() {

    override fun toDomainModel(): UserInfo {
        return UserInfo(
            id = this.id,
            name = this.name,
            avatar = this.avatar,
            location = this.location,
            createAt = this.createAt,
            bio = this.bio
        )
    }

    companion object {
        fun fromDomain(userInfo: UserInfo): CacheUserInfo {
            return CacheUserInfo(
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
