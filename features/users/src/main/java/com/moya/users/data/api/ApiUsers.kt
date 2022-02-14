package com.moya.users.data.api

import com.moya.users.data.api.model.ApiUsersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiUsers {

    @GET("users")
    fun getUsers(
        @Query("page") page: Int,
        @Query("limit") perPage: Int
    ): Call<List<ApiUsersResponse>>

    @GET("users/{id}")
    fun getUserById(
        @Path("id") id: Int
    ): Call<ApiUsersResponse>
}