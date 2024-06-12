package com.example.hi_ponic.data.retrofit

import com.example.hi_ponic.data.Response.LoginResponse
import com.example.hi_ponic.data.Response.PredictConditionResponse
import com.example.hi_ponic.data.Response.SensorResponse
import com.example.hi_ponic.data.response.ErrorResponse
import com.example.hi_ponic.data.response.TambahTanamanResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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

    @Multipart
    @POST("predict/plant/condition")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): PredictConditionResponse

    @GET("iot/device/allSensor")
    suspend fun getSensorData(): SensorResponse

    @Multipart
    @POST("")
    suspend fun addPlant(
        @Field("name") name: String,
        @Field("date_added") date_added: String,
        @Field("image") image: MultipartBody.Part
    ):TambahTanamanResponse
}
