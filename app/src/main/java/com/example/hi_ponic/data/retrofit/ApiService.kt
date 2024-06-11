package com.example.hi_ponic.data.retrofit

import com.example.hi_ponic.data.Response.LoginResponse
import com.example.hi_ponic.data.Response.SensorResponse
import com.example.hi_ponic.data.response.ErrorResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("username") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): ErrorResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("identifier") identifier: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("sensor")
    suspend fun getSensorData(): SensorResponse
}
