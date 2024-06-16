package com.example.hi_ponic.data.retrofit

import com.example.hi_ponic.data.Response.LoginResponse
import com.example.hi_ponic.data.Response.PredictConditionResponse
import com.example.hi_ponic.data.Response.SensorResponse
import com.example.hi_ponic.data.response.ErrorResponse
import com.example.hi_ponic.data.response.ListTanamanResponse
import com.example.hi_ponic.data.response.TambahTanamanResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        @Field("password") password: String,
        @Field("email") email: String,
        @Field("username") name: String
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

    @GET("")
    suspend fun getEverageSensorData(): SensorResponse

    @Multipart
    @POST("plants") // update the endpoint accordingly
    suspend fun addPlant(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("date_added") date_added: RequestBody,
        @Part image: MultipartBody.Part
    ): TambahTanamanResponse

    @GET("Plants")
    suspend fun getPlan(
        @Header("Authorization") token: String
    ): ListTanamanResponse

    @POST("/password/reset-password")
    suspend fun resetPassword(
        @Field("code") code : String,
        @Field("newPassword") newPassword: String
    ): ErrorResponse

    @POST("/password/forgot-password")
    suspend fun forgotPassword(
        @Field("email") email: String
    ):ErrorResponse

    @POST("/auth/change-username")
    suspend fun changeUsername(
        @Field("newUsername") username : String
    ): ErrorResponse

    @POST("/auth/change-password")
    suspend fun changePassword(
        @Field("newPassword") newPassword : String,
        @Field("currentPassword") currentPassword : String
    ): ErrorResponse
}
