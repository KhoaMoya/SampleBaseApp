package com.moya.core.data.cache.model

abstract class CacheModel<T> {
    abstract fun toDomainModel(): T
}