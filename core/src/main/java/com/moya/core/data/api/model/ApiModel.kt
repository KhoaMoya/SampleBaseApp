package com.moya.core.data.api.model

abstract class ApiModel<T> {
    abstract fun toDomainModel(): T
}