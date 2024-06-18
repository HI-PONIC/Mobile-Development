// UserRepository.kt
package com.example.hi_ponic.data

import android.util.Log
import com.example.hi_ponic.data.Response.LoginResponse
import com.example.hi_ponic.data.Response.PredictConditionResponse
import com.example.hi_ponic.data.Response.SensorResponse
import com.example.hi_ponic.data.pref.UserModel
import com.example.hi_ponic.data.pref.UserPreference
import com.example.hi_ponic.data.response.AverageResponse
import com.example.hi_ponic.data.response.ErrorResponse
import com.example.hi_ponic.data.response.ListTanamanResponse
import com.example.hi_ponic.data.response.TambahTanamanResponse
import com.example.hi_ponic.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun getName(): Flow<String> {
        return userPreference.getName()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun login(identifier: String, password: String): LoginResponse {
        return apiService.login(identifier, password)
    }

    suspend fun register(password: String, email: String, username: String): ErrorResponse {
        return apiService.register(username, email, password)
    }

    suspend fun resetPassword(code : String, newPassword : String):ErrorResponse{
        return apiService.resetPassword(code, newPassword)
    }
    suspend fun forgotPassword(email: String): ErrorResponse{
        return apiService.forgotPassword(email)
    }

    suspend fun changeUsername(token: String, newUsername: String): ErrorResponse {
        return apiService.changeUsername(token, newUsername)
    }

    suspend fun changePassword(token: String, newPassword: String, currentPassword: String): ErrorResponse {
        return apiService.changePassword(token, newPassword, currentPassword)
    }

    suspend fun addPlant(token: String, name: RequestBody, date_added: RequestBody, image: MultipartBody.Part) {
        apiService.addPlant(token, name, date_added, image)
    }

    suspend fun deletePlant(token: String,id: Int):TambahTanamanResponse{
        return apiService.deletePlant(token, id)
    }

    suspend fun getPlant(token: String):ListTanamanResponse{
        return apiService.getPlan(token)
    }

    fun observeSensorValues(): Flow<SensorResponse> = flow {
        while (true) {
            try {
                val sensorData = apiService.getSensorData()
                emit(sensorData)
                Log.d("UserRepository", "Successfully fetched sensor data")
            } catch (e: Exception) {
                Log.e("UserRepository", "Failed to fetch sensor data", e)
            }
            delay(120000)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getAverageSensor():AverageResponse{
        return apiService.getAverageSensorData()
    }

    suspend fun uploadImage(file: MultipartBody.Part): PredictConditionResponse {
        return apiService.uploadImage(file)
    }

    companion object {
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ) = UserRepository(userPreference, apiService)
    }
}