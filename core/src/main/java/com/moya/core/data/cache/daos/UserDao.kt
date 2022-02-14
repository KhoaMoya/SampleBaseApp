package com.moya.core.data.cache.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.moya.core.data.cache.model.CacheUserInfo

@Dao
abstract class UserDao {

    @Query("SELECT * FROM users")
    abstract fun getCachedUsers(): List<CacheUserInfo>

    @Query("SELECT * FROM users WHERE id IS :id")
    abstract fun getUser(id: Int): CacheUserInfo

    @Delete
    abstract fun deleteUser(userInfo: CacheUserInfo)

    @Insert
    abstract fun insertUser(userInfo: CacheUserInfo)

    @Transaction
    open fun cacheUsers(users: List<CacheUserInfo>) {
        // delete all cached users
        getCachedUsers().forEach { deleteUser(it) }

        // insert all new users
        users.forEach { insertUser(it) }
    }

}