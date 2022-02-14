package com.moya.core.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moya.core.data.cache.daos.UserDao
import com.moya.core.data.cache.model.CacheUserInfo

@Database(
    entities = [CacheUserInfo::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}