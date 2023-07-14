package com.adam.suitmedia1.data.retrofit

import com.adam.suitmedia1.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUserList(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<UserResponse>
}